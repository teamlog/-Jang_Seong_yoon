package CLient;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument.Content;

public class Client extends JFrame implements ActionListener, KeyListener, WindowListener {
	// Login GUI Field
	private JFrame Login_GUI = new JFrame();
	private JTextField IP_tf = new JTextField();
	private JTextField Login_ID_tf = new JTextField();
	private JPasswordField Loginpf = new JPasswordField();
	private JButton login_btn = new JButton("\uC811\uC18D");
	private JButton go_rg = new JButton("\uD68C\uC6D0\uAC00\uC785");
	// Friends&Room GUI Field
	private JFrame F_R_GUI = new JFrame();
	private JPanel F_RPane;
	private JList fr_list = new JList();
	private JButton invite_btn = new JButton("친구 초대");
	private JButton create_room_btn = new JButton("방 만들기");
	private JButton join_room_btn = new JButton("방 참가");
	private JList Room_list = new JList();
	// chat GUI Field
	private JFrame Chat_GUI = new JFrame();
	private JPanel ChatPane = new JPanel();
	private JTextField Chat_tf = new JTextField();
	private JButton send_btn = new JButton("전송");
	private JList contact_user_list = new JList();
	private JTextArea Chatting_area = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane();
	private JScrollPane scrollPane_1 = new JScrollPane();

	// Register GUI Field
	private JFrame Rg_GUI = new JFrame();
	private JPanel rg_Pane;
	private JTextField rg_ID_tf = new JTextField();
	private JPasswordField rg_pf_1 = new JPasswordField();
	private JTextField rg_nickname_tf = new JTextField();
	private JTextField rg_server_IP = new JTextField();
	private JButton register_btn = new JButton("회원가입");
	// Network Field
	private Socket socket;
	private String IP;
	private int port;
	private String ID = "basic";
	private String Nickname;
	private String Password;
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	// Other Field
	private StringTokenizer st;
	Vector fr_List = new Vector();
	Vector room_List = new Vector();
	Vector Contact_user_list = new Vector();

	// GUI설정 메소드
	public void Login_init() {
		Login_GUI.setTitle("Login Window");
		Login_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login_GUI.setBounds(100, 100, 273, 332);
		Login_GUI.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("IP");
		lblNewLabel.setBounds(12, 106, 63, 32);
		Login_GUI.getContentPane().add(lblNewLabel);

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(12, 161, 63, 32);
		Login_GUI.getContentPane().add(lblId);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(12, 214, 63, 32);
		Login_GUI.getContentPane().add(lblPassword);

		IP_tf.setBounds(87, 112, 145, 21);
		Login_GUI.getContentPane().add(IP_tf);
		IP_tf.setColumns(10);

		Login_ID_tf.setColumns(10);
		Login_ID_tf.setBounds(87, 167, 145, 21);
		Login_GUI.getContentPane().add(Login_ID_tf);

		Loginpf.setBounds(87, 220, 145, 21);
		Login_GUI.getContentPane().add(Loginpf);

		JLabel lblNewLabel_1 = new JLabel("Login");
		lblNewLabel_1.setFont(new Font("한컴 바겐세일 M", Font.PLAIN, 40));
		lblNewLabel_1.setBounds(12, 10, 220, 75);
		Login_GUI.getContentPane().add(lblNewLabel_1);

		login_btn.setBounds(12, 256, 97, 23);
		Login_GUI.getContentPane().add(login_btn);

		go_rg.setBounds(135, 256, 97, 23);
		Login_GUI.getContentPane().add(go_rg);
		Login_GUI.setVisible(true);
	}

	public void F_R_init() {
		F_R_GUI.setTitle("Friends&Room Window");
		F_R_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		F_R_GUI.setBounds(100, 100, 506, 367);
		F_RPane = new JPanel();
		F_RPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		F_R_GUI.setContentPane(F_RPane);
		F_RPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 10, 196, 248);
		F_RPane.add(scrollPane);

		scrollPane.setViewportView(fr_list);

		invite_btn.setBounds(62, 282, 97, 23);
		F_RPane.add(invite_btn);

		create_room_btn.setBounds(259, 282, 97, 23);
		F_RPane.add(create_room_btn);

		join_room_btn.setBounds(358, 282, 97, 23);
		F_RPane.add(join_room_btn);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(259, 10, 196, 248);
		F_RPane.add(scrollPane_1);

		scrollPane_1.setViewportView(Room_list);
		F_R_GUI.setVisible(false);
	}

	private void Chat_init(String Title) {
		Chat_GUI.setTitle(Title);
		Chat_GUI.addWindowListener(this);
		Chat_GUI.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Chat_GUI.setBounds(100, 100, 486, 523);
		ChatPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Chat_GUI.setContentPane(ChatPane);
		ChatPane.setLayout(null);

		scrollPane.setBounds(104, 10, 354, 409);
		ChatPane.add(scrollPane);

		Chatting_area.setEditable(false);
		scrollPane.setViewportView(Chatting_area);

		Chat_tf.setBounds(12, 430, 366, 46);
		ChatPane.add(Chat_tf);
		Chat_tf.setColumns(10);

		send_btn.setBounds(387, 429, 71, 46);
		ChatPane.add(send_btn);

		scrollPane_1.setBounds(12, 10, 82, 409);
		ChatPane.add(scrollPane_1);

		scrollPane_1.setViewportView(contact_user_list);
	}

	public void Register_init() {
		Rg_GUI.setTitle("Register Window");
		Rg_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Rg_GUI.setBounds(100, 100, 334, 388);
		rg_Pane = new JPanel();
		rg_Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Rg_GUI.setContentPane(rg_Pane);
		rg_Pane.setLayout(null);

		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Aparajita", Font.PLAIN, 20));
		lblId.setBounds(12, 170, 108, 31);
		rg_Pane.add(lblId);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Aparajita", Font.PLAIN, 20));
		lblPassword.setBounds(12, 203, 108, 44);
		rg_Pane.add(lblPassword);

		JLabel lblRegister = new JLabel("Register");
		lblRegister.setFont(new Font("한컴 바겐세일 M", Font.PLAIN, 40));
		lblRegister.setBounds(26, 10, 250, 75);
		rg_Pane.add(lblRegister);

		JLabel lblServerip = new JLabel("ServerIP");
		lblServerip.setFont(new Font("Aparajita", Font.PLAIN, 20));
		lblServerip.setBounds(12, 95, 108, 31);
		rg_Pane.add(lblServerip);

		rg_ID_tf.setBounds(160, 174, 116, 21);
		rg_Pane.add(rg_ID_tf);
		rg_ID_tf.setColumns(10);

		register_btn.setBounds(26, 302, 250, 38);
		rg_Pane.add(register_btn);

		rg_pf_1.setBounds(160, 214, 116, 21);
		rg_Pane.add(rg_pf_1);

		JLabel lblNickname = new JLabel("Nickname");
		lblNickname.setFont(new Font("Aparajita", Font.PLAIN, 20));
		lblNickname.setBounds(12, 131, 108, 31);
		rg_Pane.add(lblNickname);

		rg_nickname_tf.setColumns(10);
		rg_nickname_tf.setBounds(160, 135, 116, 21);
		rg_Pane.add(rg_nickname_tf);
		Rg_GUI.setVisible(false);

		rg_server_IP.setColumns(10);
		rg_server_IP.setBounds(160, 95, 116, 21);
		rg_Pane.add(rg_server_IP);
	}

	Client() {// 생성자
		Login_init();// 로그인화면 구성 메소드
		F_R_init();// 접속자및 방리스트 구성 메소드
		Register_init();// 회원가입 구성 메소드
		start();
	}

	private void send_message(String str) {// 서버에 메세지 보내는 메소드

		try {
			dos.writeUTF(str);
		} catch (IOException e) {

		}
	}

	private void start() {// 프로그램 시작시 액션리스너 활성
		login_btn.addActionListener(this);
		go_rg.addActionListener(this);
		invite_btn.addActionListener(this);
		create_room_btn.addActionListener(this);
		join_room_btn.addActionListener(this);
		register_btn.addActionListener(this);
		send_btn.addActionListener(this);
		Chat_tf.addKeyListener(this);
	}

	private void Connection() {// 서버와 접속성공시 메소드
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);// 에러처리
																							// 부분
		} // 스트림 설정끝
		send_message("connect/ok");
		// 접속 확인
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						String msg = dis.readUTF();
						System.out.println("서버로부터 수신된 메세지" + msg);
						inmessage(msg);
					} catch (IOException e) {
						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
							JOptionPane.showMessageDialog(null, "서버와의 접속 끊어짐", "알림", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e1) {
						}
						break;
					}
				}
			}

		});

		th.start();
	}

	private void Network() {// 서버와 접속하는 메소드
		try {
			port = 810;
			socket = new Socket(IP, port);

			if (socket != null) {
				Connection();
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void inmessage(String str) {// 들어오는 메세지 처리
		st = new StringTokenizer(str, "/");

		String protocol = st.nextToken();
		String Message = st.nextToken();

		System.out.println("프로토콜" + protocol);
		System.out.println("내용" + Message);
		if (protocol.equals("connect")) {// 접속 성공시
			System.out.println("접속 성공");
		} else if (protocol.equals("login_ok")) {// 로그인 성공시
			Nickname = Message;
			Login_GUI.setVisible(false);
			F_R_GUI.setVisible(true);
			fr_List.add(Nickname);
			fr_list.setListData(fr_List);
		} else if (protocol.equals("login_fail")) {// 로그인 실패시
			JOptionPane.showMessageDialog(null, "로그인 실패", "알림", JOptionPane.ERROR_MESSAGE);
		} else if (protocol.equals("Register")) {// 회원가입 성공시
			JOptionPane.showMessageDialog(null, "회원가입 되셨습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
			Rg_GUI.setVisible(false);
		} else if (protocol.equals("RegisterFail")) {// 회원가입 실패시
			JOptionPane.showMessageDialog(null, "이미 똑같은 ID가 있습니다", "알림", JOptionPane.ERROR_MESSAGE);
		} else if (protocol.equals("JoinRoom")) {// 방접속 성공시
			Contact_user_list.removeAllElements();
			Chatting_area.setText("");
			Chat_init(Message);// 채팅창 구성 메소드
			Chat_GUI.setVisible(true);

		} else if (protocol.equals("CreateRoom")) {// 방생성 성공시
			Contact_user_list.removeAllElements();
			Chatting_area.setText("");
			Chat_init(Message);// 채팅창 구성 메소드
			Chat_GUI.setVisible(true);
		} else if (protocol.equals("New_Room")) {// 새로운 방알림
			room_List.add(Message);
			Room_list.setListData(room_List);
		} else if (protocol.equals("Chatting")) {// 채팅 처리
			String msg = st.nextToken();
			Chatting_area.append(Message + msg);
		} else if (protocol.equals("OldUser")) {// 기존 유저목록 받아오기
			fr_List.add(Message);
		} else if (protocol.equals("New User")) {// 새로운 접속자
			fr_List.add(Message);
		} else if (protocol.equals("OldRoom")) {// 기존 방목록 받아오기
			room_List.add(Message);
		} else if (protocol.equals("room_list_update")) {// 방목록 GUI갱신
			Room_list.setListData(room_List);
		} else if (protocol.equals("user_list_update")) {// 접속자목록 GUI갱신
			fr_list.setListData(fr_List);
		} else if (protocol.equals("InviteFail")) {// 초대 실패시
			JOptionPane.showMessageDialog(null, "초대 실패", "알림", JOptionPane.ERROR_MESSAGE);
		} else if (protocol.equals("RemoveRoom")) {// 방이 제거될시
			room_List.remove(Message);
			Room_list.setListData(room_List);
		} else if (protocol.equals("Invite")) {// 초대를 받았을시
			int ask = JOptionPane.showConfirmDialog(null, "채팅방에 초대를 받았습니다 수락하시겠습니까?(현재채팅방에선 나가게 됩니다)", Message + "로부터",
					JOptionPane.YES_NO_OPTION);
			if (ask == JOptionPane.YES_OPTION) {
				String password = st.nextToken();
				send_message("RoomOut/ok");
				send_message("JoinRoom/" + Message + "/" + password);
			}
		} else if (protocol.equals("contact_user")) {// 방에 접속시 방의 접속된 기존 접속자들 목록
														// 받아오기
			Contact_user_list.add(Message);
			contact_user_list.setListData(Contact_user_list);
		} else if (protocol.equals("Out_User")) {// 방에서 접속자가 나갔을때
			Contact_user_list.remove(Message);
			contact_user_list.setListData(Contact_user_list);
		} else if (protocol.equals("UserOut")) {// 접속자가 접속을 끊었을시
			fr_List.remove(Message);
		}
	}

	public static void main(String[] args) {
		new Client();
	}

	public void actionPerformed(ActionEvent e) {// 액션리스너 처리
		if (e.getSource() == login_btn) {// 로그인 버튼 누를시
			IP = IP_tf.getText().trim();
			ID = Login_ID_tf.getText().trim();
			Password = String.valueOf(Loginpf.getPassword());// 회원정보 전송
			Network();
			send_message("login/" + ID + "/" + Password);
		} else if (e.getSource() == go_rg) {// 회원가입창 열기
			Rg_GUI.setVisible(true);
		} else if (e.getSource() == register_btn) {// 회원가입 버튼 누를시
			IP = rg_server_IP.getText().trim();
			Network();
			ID = rg_ID_tf.getText().trim();
			Nickname = rg_nickname_tf.getText().trim();// 회원정보를 사용자로부터 받아옴
			Password = String.valueOf(rg_pf_1.getPassword()).trim();
			System.out.println(Password);
			send_message("register/" + ID + "/" + Nickname+"/"+Password);
		} // 회원정보 전송

		else if (e.getSource() == create_room_btn) {// 방생성 버튼 누를시
			String roomname = JOptionPane.showInputDialog("방 이름");
			String roompassword = JOptionPane.showInputDialog("방 비밀번호");// 방정보
																		// 받아오고
			if (roomname != null) {
				send_message("RoomOut/ok");// 기존에 접속해있던 방 나와서
				send_message("CreateRoom/" + roomname + "/" + roompassword);// 방정보
																			// 보내주고
			} else {
				JOptionPane.showMessageDialog(null, "방이름을 적어주세요", "알림", JOptionPane.ERROR_MESSAGE);// 방이름
																									// 안적었을떄
			}
		} else if (e.getSource() == invite_btn) {// 초대 버튼 누를시
			String fr = (String) fr_list.getSelectedValue();
			String room = JOptionPane.showInputDialog("방이름");// 방정보 받아오기
			if (fr != null && room != null) {
				send_message("inviteRoom/" + fr + "/" + room);// 초대할 친구 이름이랑 방정보
																// 보내기
			}
		} else if (e.getSource() == send_btn) {// 전송 버튼 누를시
			String roomname = Chat_GUI.getTitle();
			String message = Chat_tf.getText();// 보낼 채팅방 정보와 메세지정보
			send_message("Chatting/" + roomname + "/" + message + "\n");// 정보
																		// 보내기
			Chat_tf.setText("");
			Chat_tf.requestFocus();
		} else if (e.getSource() == join_room_btn) {// 방 참가 버튼 누를시
			String roomname = (String) Room_list.getSelectedValue();
			String Password = JOptionPane.showInputDialog("방 비밀번호");// 방정보
																	// 사용자로부터
																	// 받아오고
			send_message("RoomOut/ok");// 기존에 있던 방에서 나오고
			send_message("JoinRoom/" + roomname + "/" + Password);// 접속 시도
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {// 채팅창에서 엔터누를때 전송버튼 누른거랑 똑같은 효과나게 할려구 씀
		if (e.getKeyCode() == 10) {
			String roomname = Chat_GUI.getTitle();
			String message = Chat_tf.getText();
			send_message("Chatting/" + roomname + "/" + message + "\n");
			Chat_tf.setText("");
			Chat_tf.requestFocus();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		send_message("RoomOut/ok");

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
