package SErver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JTextArea textArea = new JTextArea();
	private JButton server_launch_btn = new JButton("서버 실행");
	private JButton server_stop_btn = new JButton("서버 중지");

	// 네트워크 자원
	private ServerSocket server_socket;
	private Socket socket;
	private int port = 810;
	private Vector user_vc = new Vector();
	private Vector room_vc = new Vector();
	// 기타 자원
	private StringTokenizer st;
	private int filenum = 1;

	Server()// 생성자
	{
		init();// 화면 생성 메소드
		start();// 리스너 실행 메소드
	}

	private void start() {//액션리스너 활성
		server_launch_btn.addActionListener(this);
		server_stop_btn.addActionListener(this);
	}

	private void init() {// 화면구성
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 325, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 285, 232);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);

		server_launch_btn.setBounds(12, 295, 139, 23);
		contentPane.add(server_launch_btn);

		server_stop_btn.setBounds(150, 295, 147, 23);
		contentPane.add(server_stop_btn);
		server_stop_btn.setEnabled(false);

		this.setVisible(true); // 화면이 보이게 하는 용도 true면 보임 false면 안보임
	}

	private void Server_start() {
		try {
			server_socket = new ServerSocket(port);//서버 가동
		} catch (IOException e) {
			//똑같은 IP에서 똑같은 port로 서버를 열었을시 에러처리
			JOptionPane.showMessageDialog(null, "이미 사용중인 포트입니다", "알림", JOptionPane.ERROR_MESSAGE);
		} 

		if (server_socket != null) {
			Connection();
		}
	}

	private void Connection() {//각각의 유저소켓 생성

		// 1가지 스레드에서는 1가지의 일만 처리할수있음
		Thread th = new Thread(new Runnable() {
			public void run() {
				while (true) {

					try {
						textArea.append("사용자 접속 대기중\n");
						socket = server_socket.accept();// 사용자 정보 무한대기
						textArea.append("사용자 접속\n");

						UserInfo user = new UserInfo(socket);

						user.start();// 객체의 스레드 실행

					} catch (IOException e) {
						break;
					}
				}
			}
		});

		th.start();
	}

	public static void main(String[] args) {

		new Server();
	}

	@Override
	public void actionPerformed(ActionEvent e) {//액션 리스너처리
		if (e.getSource() == server_launch_btn) {
			System.out.println("실행버튼 클릭");
			Server_start();
			server_launch_btn.setEnabled(false);
			server_stop_btn.setEnabled(true);
		} else if (e.getSource() == server_stop_btn) {
			try {
				server_socket.close();
				user_vc.removeAllElements();
				room_vc.removeAllElements();
				server_launch_btn.setEnabled(true);
				textArea.append("서버 중지\n");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "서버 중지 실패", "알림", JOptionPane.ERROR_MESSAGE);
			}
			System.out.println("중지버튼 클릭");
		}
	}// 액션 이벤트 끝

	class UserInfo extends Thread {//유저 정보및 유저 소켓 저장
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;

		private String ID;
		private String Password="";
		private String Nickname = "";
		private String fr_Nickname = "";

		private Socket user_socket;

		private boolean RoomCh = true;

		UserInfo(Socket socket) {// 생성자 메소드
			this.user_socket = socket;

			UserNetwork();

		}

		private void UserNetwork() {
			try {
				is = user_socket.getInputStream();
				dis = new DataInputStream(is);
				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);
				//스트림 설정

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Stream 설정 에러", "알림", JOptionPane.ERROR_MESSAGE);
			}

		}

		public void run() {// 쓰레드 생성

			while (true) {
				try {
					String msg = dis.readUTF();
					textArea.append(Nickname + "사용자로부터 들어온 메세지 " + msg + "\n");
					InMessage(msg);
				} catch (IOException e) {
					//사용자 접속이 끊길시
					textArea.append(Nickname + "사용자 접속 끊어짐\n");
					try {
						dos.close();
						dis.close();
						//스트림 닫고
						user_socket.close();
						//유저 소켓닫고
						for (int i = 0; i < room_vc.size(); i++) {
							RoomInfo r = (RoomInfo) room_vc.elementAt(i);
							r.Room_User_vc.remove(this);
						}//방에서 이름뺴고
						Broadcast("UserOut/" + this.Nickname);//이 유저가 나갔다는것을 알림
						Broadcast("user_list_update/ok");
						user_vc.remove(this);
					} catch (IOException e1) {
					}
					break;

				} // 메세지 수신
			}
		}// run메소드 끝

		private void InMessage(String str) {// 클라이언트 로부터 들어오는 메세지 처리
			st = new StringTokenizer(str, "/");
			String protocol = st.nextToken();
			String message = st.nextToken();
			System.out.println("프로토콜" + protocol);
			System.out.println("메세지" + message);

			if (protocol.equals("Chatting")) {//채팅시에 프로토콜
				String msg = st.nextToken();
				for (int i = 0; i < room_vc.size(); i++) {// 해당방 찾기
					RoomInfo r = (RoomInfo) room_vc.elementAt(i);
					if (r.Room_name.equals(message)) {// 해당방을 찾았을떄
						r.BroadCast_Room("Chatting/" + Nickname + ":/" + msg);
					}
				}
			} else if (protocol.equals("JoinRoom")) {//방 입장시 프로토콜
				String ps = st.nextToken();
				System.out.println(ps);
				for (int i = 0; i < room_vc.size(); i++) {//유저가 보낸 방의 이름으로 방을 찾는다
					RoomInfo r = (RoomInfo) room_vc.elementAt(i);
					if (r.Room_name.equals(message)) {
						if (r.Room_Password.equals(ps)) {//방의 비밀번호 확인
							// 새로운 사용자 알림
							r.BroadCast_Room("Chating/ /****" + Nickname + "님이 입장하셨습니다****");
							send_Message("JoinRoom/" + message);
							r.BroadCast_Room("contact_user/" + this.Nickname);
							// 사용자 추가
							r.Add_User(this);
							for (int j = 0; j < r.Room_User_vc.size(); j++) {
								//기존 사용자 목록 보내주기
								UserInfo u = (UserInfo) r.Room_User_vc.elementAt(j);
								send_Message("contact_user/" + u.Nickname);
							}
						} else
							send_Message("JoinFail/sorry");
					}
				}
			} else if (protocol.equals("connect")) {//접속 성공시
				send_Message("connect/ok");
			} else if (protocol.equals("register")) {//회원가입시
				ID = message;
				Nickname = st.nextToken();
				System.out.println(ID+Nickname+st.nextToken());
				if (Filewriter(ID, Password, Nickname)) {//회원가입 성공시
					send_Message("Register/ok");
				} else {//회원가입 실패시
					send_Message("RegisterFail/sorry");
				}
			} else if (protocol.equals("login")) {//로그인시
				ID = message;
				Password=st.nextToken();
				boolean go = true;
				for (int i = 0; i < user_vc.size(); i++) {//사용자 중복확인
					UserInfo u = (UserInfo) user_vc.elementAt(i);
					if (ID.equals(u.ID)) {
						send_Message("login_fail/ok");
						go = false;
					}
				}
				if (go) {//중복이 아닐시
					if (Filereader(ID, Password)) {//회원정보가 맞다면 
						send_Message("login_ok/" + Nickname);
						//접속자 알림
						Broadcast("New User/" + Nickname);
						for (int i = 0; i < user_vc.size(); i++) {//기존 접속자 보내주기
							UserInfo u = (UserInfo) user_vc.elementAt(i);
							send_Message("OldUser/" + u.Nickname);
						}
						for (int i = 0; i < room_vc.size(); i++) {//기존 방목록 보내주기
							RoomInfo r = (RoomInfo) room_vc.elementAt(i);
							send_Message("OldRoom/" + r.Room_name);
						}
						user_vc.add(this);
						Broadcast("user_list_update/ok");
						Broadcast("room_list_update/ok");
					} else if (Filereader(ID, Password)) {//회원정보가 맞지않았을 시
						send_Message("login_fail/ok");
					}
				}
				System.out.println(Filereader(ID, Password));
				System.out.println(ID + Password);
			} else if (protocol.equals("CreateRoom")) {//방생성시
				String password = st.nextToken();
				// 1.현재 같은 방이 존재하는지 확인한다
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo) room_vc.elementAt(i);
					if (r.Room_name.equals(message)) {// 만들고자 하는방이 이미 존재할떄
						send_Message("CreateRoomFail/ok");
						RoomCh = false;
						break;
					}
				} // for끝
				if (RoomCh) {
					RoomInfo new_room = new RoomInfo(message, this, password);
					room_vc.add(new_room);// 전체 방 벡터에 방을 추가
					send_Message("CreateRoom/" + message);
					send_Message("contact_user/" + this.Nickname);
					//방을 접속자들에게 알림
					Broadcast("New_Room/" + message);
					Broadcast("room_list_update/ok");
				}
				RoomCh = true;
			} else if (protocol.equals("inviteRoom")) {//접속자 초대시
				String roomname = st.nextToken();
				for (int i = 0; i < user_vc.size(); i++) {//초대하는사람이 보낸 초대할사람의 닉네임으로 접속자중에서 찾기
					UserInfo u = (UserInfo) user_vc.elementAt(i);
					if (u.Nickname.equals(message)) {//찾으면
						for (int j = 0; j < room_vc.size(); j++) {
							RoomInfo r = (RoomInfo) room_vc.elementAt(j);//초대보낸사람이 접속할 방에 있는지 확인
							for (int k = 0; k < r.Room_User_vc.size(); k++) {
								UserInfo u1 = (UserInfo) r.Room_User_vc.elementAt(k);
								if (this.Nickname.equals(u1.Nickname)) {
									if (r.Room_name.equals(roomname)) {//초대보낸 사람이 접속할 방에 있으면 초대
										u.send_Message("Invite/" + r.Room_name + "/" + r.Room_Password);
										break;
									} else if (!this.Nickname.equals(u1.Nickname)) {//없으면 초대안함
										send_Message("InviteFail/ok");
									}
								}
							}
						}
					}
				}
			} else if (protocol.equals("RoomOut")) {//방에서 나갔을시
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo) room_vc.elementAt(i);
					for (int j = 0; j < r.Room_User_vc.size(); j++) {
						UserInfo u = (UserInfo) r.Room_User_vc.elementAt(j);
						if (this.Nickname.equals(u.Nickname)) {//방의 접속자 목록에서 사용자 제거
							r.BroadCast_Room("Out_User/" + u.Nickname);
						}
					}
					r.Room_User_vc.remove(this);
				}
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo) room_vc.elementAt(i);
					if (r.Room_User_vc.size() == 0) {//방에 접속자가 없을시 방 제거
						Broadcast("RemoveRoom/" + r.Room_name);
						room_vc.removeElement(r);
					}
				}
			}

		}

		private void Broadcast(String str) {//접속한 모든사용자에게 메세지 날리기
			for (int i = 0; i < user_vc.size(); i++) {
				UserInfo u = (UserInfo) user_vc.elementAt(i);
				u.send_Message(str);
			}
		}

		private void send_Message(String str) {//이 유저소켓의 사용자에게 메세지 날리기
			try {
				dos.writeUTF(str);
			} catch (IOException e) {

			}
		}

		private boolean Filereader(String ID, String Password) {//파일을 읽어들여 입력한 회원정보가 저장되어있는지 확인
			boolean a = false;
			try {
				BufferedReader br = new BufferedReader(new FileReader("c:\\userinfo.txt"));
				BufferedReader br1 = new BufferedReader(new FileReader("c:\\userinfo.txt"));
				int num = 0;
				while (true) {
					num++;
					try {
						if (br.readLine().equals("")) {
							break;
						}
					} catch (NullPointerException e) {
						break;
					}
				}
				br.close();
				for (int i = 0; i < num - 1; i++) {
					System.out.println(num);
					String line = br1.readLine();
					System.out.println(line);
					st = new StringTokenizer(line, ":");
					if (ID.equals(st.nextToken())) {
						if (Password.equals(st.nextToken())) {
							Nickname = st.nextToken();
							a = true;
						}
					}
				}

				br1.close();
			} catch (IOException e) {

			}
			return a;
		}

		private boolean Filewriter(String ID, String Nickname, String Password) {//회원가입시 회원정보 저장하기
			boolean r = true;
			try {
				FileWriter fw2 = new FileWriter("c:\\userinfo.txt", true);
				if (Filereader(ID, Password)) {
					if (Filereader(ID, Nickname)) {
						String data = ID + ":" + Password + ":" + Nickname + "\r\n";
						fw2.write(data);
						fw2.close();
					}
				} else {
					System.out.println("fail");
					fw2.close();
					r = false;
				}

			} catch (IOException e) {
			}
			return r;
		}

	}// UsreInfo클레스 끝

	class RoomInfo {//방정보 
		private String Room_name = "";
		private Vector Room_User_vc = new Vector();
		private String Room_Password = "";

		RoomInfo(String str, UserInfo u, String Password) {
			this.Room_name = str;
			this.Room_User_vc.add(u);
			this.Room_Password = Password;
		}

		public void BroadCast_Room(String str) {
			for (int i = 0; i < Room_User_vc.size(); i++) {
				UserInfo u = (UserInfo) Room_User_vc.elementAt(i);
				u.send_Message(str);
			}
		}

		private void Add_User(UserInfo u) {
			this.Room_User_vc.add(u);
		}
	}
}
