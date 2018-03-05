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
	private JButton server_launch_btn = new JButton("���� ����");
	private JButton server_stop_btn = new JButton("���� ����");

	// ��Ʈ��ũ �ڿ�
	private ServerSocket server_socket;
	private Socket socket;
	private int port = 810;
	private Vector user_vc = new Vector();
	private Vector room_vc = new Vector();
	// ��Ÿ �ڿ�
	private StringTokenizer st;
	private int filenum = 1;

	Server()// ������
	{
		init();// ȭ�� ���� �޼ҵ�
		start();// ������ ���� �޼ҵ�
	}

	private void start() {//�׼Ǹ����� Ȱ��
		server_launch_btn.addActionListener(this);
		server_stop_btn.addActionListener(this);
	}

	private void init() {// ȭ�鱸��
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

		this.setVisible(true); // ȭ���� ���̰� �ϴ� �뵵 true�� ���� false�� �Ⱥ���
	}

	private void Server_start() {
		try {
			server_socket = new ServerSocket(port);//���� ����
		} catch (IOException e) {
			//�Ȱ��� IP���� �Ȱ��� port�� ������ �������� ����ó��
			JOptionPane.showMessageDialog(null, "�̹� ������� ��Ʈ�Դϴ�", "�˸�", JOptionPane.ERROR_MESSAGE);
		} 

		if (server_socket != null) {
			Connection();
		}
	}

	private void Connection() {//������ �������� ����

		// 1���� �����忡���� 1������ �ϸ� ó���Ҽ�����
		Thread th = new Thread(new Runnable() {
			public void run() {
				while (true) {

					try {
						textArea.append("����� ���� �����\n");
						socket = server_socket.accept();// ����� ���� ���Ѵ��
						textArea.append("����� ����\n");

						UserInfo user = new UserInfo(socket);

						user.start();// ��ü�� ������ ����

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
	public void actionPerformed(ActionEvent e) {//�׼� ������ó��
		if (e.getSource() == server_launch_btn) {
			System.out.println("�����ư Ŭ��");
			Server_start();
			server_launch_btn.setEnabled(false);
			server_stop_btn.setEnabled(true);
		} else if (e.getSource() == server_stop_btn) {
			try {
				server_socket.close();
				user_vc.removeAllElements();
				room_vc.removeAllElements();
				server_launch_btn.setEnabled(true);
				textArea.append("���� ����\n");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "���� ���� ����", "�˸�", JOptionPane.ERROR_MESSAGE);
			}
			System.out.println("������ư Ŭ��");
		}
	}// �׼� �̺�Ʈ ��

	class UserInfo extends Thread {//���� ������ ���� ���� ����
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

		UserInfo(Socket socket) {// ������ �޼ҵ�
			this.user_socket = socket;

			UserNetwork();

		}

		private void UserNetwork() {
			try {
				is = user_socket.getInputStream();
				dis = new DataInputStream(is);
				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);
				//��Ʈ�� ����

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Stream ���� ����", "�˸�", JOptionPane.ERROR_MESSAGE);
			}

		}

		public void run() {// ������ ����

			while (true) {
				try {
					String msg = dis.readUTF();
					textArea.append(Nickname + "����ڷκ��� ���� �޼��� " + msg + "\n");
					InMessage(msg);
				} catch (IOException e) {
					//����� ������ �����
					textArea.append(Nickname + "����� ���� ������\n");
					try {
						dos.close();
						dis.close();
						//��Ʈ�� �ݰ�
						user_socket.close();
						//���� ���ϴݰ�
						for (int i = 0; i < room_vc.size(); i++) {
							RoomInfo r = (RoomInfo) room_vc.elementAt(i);
							r.Room_User_vc.remove(this);
						}//�濡�� �̸�����
						Broadcast("UserOut/" + this.Nickname);//�� ������ �����ٴ°��� �˸�
						Broadcast("user_list_update/ok");
						user_vc.remove(this);
					} catch (IOException e1) {
					}
					break;

				} // �޼��� ����
			}
		}// run�޼ҵ� ��

		private void InMessage(String str) {// Ŭ���̾�Ʈ �κ��� ������ �޼��� ó��
			st = new StringTokenizer(str, "/");
			String protocol = st.nextToken();
			String message = st.nextToken();
			System.out.println("��������" + protocol);
			System.out.println("�޼���" + message);

			if (protocol.equals("Chatting")) {//ä�ýÿ� ��������
				String msg = st.nextToken();
				for (int i = 0; i < room_vc.size(); i++) {// �ش�� ã��
					RoomInfo r = (RoomInfo) room_vc.elementAt(i);
					if (r.Room_name.equals(message)) {// �ش���� ã������
						r.BroadCast_Room("Chatting/" + Nickname + ":/" + msg);
					}
				}
			} else if (protocol.equals("JoinRoom")) {//�� ����� ��������
				String ps = st.nextToken();
				System.out.println(ps);
				for (int i = 0; i < room_vc.size(); i++) {//������ ���� ���� �̸����� ���� ã�´�
					RoomInfo r = (RoomInfo) room_vc.elementAt(i);
					if (r.Room_name.equals(message)) {
						if (r.Room_Password.equals(ps)) {//���� ��й�ȣ Ȯ��
							// ���ο� ����� �˸�
							r.BroadCast_Room("Chating/ /****" + Nickname + "���� �����ϼ̽��ϴ�****");
							send_Message("JoinRoom/" + message);
							r.BroadCast_Room("contact_user/" + this.Nickname);
							// ����� �߰�
							r.Add_User(this);
							for (int j = 0; j < r.Room_User_vc.size(); j++) {
								//���� ����� ��� �����ֱ�
								UserInfo u = (UserInfo) r.Room_User_vc.elementAt(j);
								send_Message("contact_user/" + u.Nickname);
							}
						} else
							send_Message("JoinFail/sorry");
					}
				}
			} else if (protocol.equals("connect")) {//���� ������
				send_Message("connect/ok");
			} else if (protocol.equals("register")) {//ȸ�����Խ�
				ID = message;
				Nickname = st.nextToken();
				System.out.println(ID+Nickname+st.nextToken());
				if (Filewriter(ID, Password, Nickname)) {//ȸ������ ������
					send_Message("Register/ok");
				} else {//ȸ������ ���н�
					send_Message("RegisterFail/sorry");
				}
			} else if (protocol.equals("login")) {//�α��ν�
				ID = message;
				Password=st.nextToken();
				boolean go = true;
				for (int i = 0; i < user_vc.size(); i++) {//����� �ߺ�Ȯ��
					UserInfo u = (UserInfo) user_vc.elementAt(i);
					if (ID.equals(u.ID)) {
						send_Message("login_fail/ok");
						go = false;
					}
				}
				if (go) {//�ߺ��� �ƴҽ�
					if (Filereader(ID, Password)) {//ȸ�������� �´ٸ� 
						send_Message("login_ok/" + Nickname);
						//������ �˸�
						Broadcast("New User/" + Nickname);
						for (int i = 0; i < user_vc.size(); i++) {//���� ������ �����ֱ�
							UserInfo u = (UserInfo) user_vc.elementAt(i);
							send_Message("OldUser/" + u.Nickname);
						}
						for (int i = 0; i < room_vc.size(); i++) {//���� ���� �����ֱ�
							RoomInfo r = (RoomInfo) room_vc.elementAt(i);
							send_Message("OldRoom/" + r.Room_name);
						}
						user_vc.add(this);
						Broadcast("user_list_update/ok");
						Broadcast("room_list_update/ok");
					} else if (Filereader(ID, Password)) {//ȸ�������� �����ʾ��� ��
						send_Message("login_fail/ok");
					}
				}
				System.out.println(Filereader(ID, Password));
				System.out.println(ID + Password);
			} else if (protocol.equals("CreateRoom")) {//�������
				String password = st.nextToken();
				// 1.���� ���� ���� �����ϴ��� Ȯ���Ѵ�
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo) room_vc.elementAt(i);
					if (r.Room_name.equals(message)) {// ������� �ϴ¹��� �̹� �����ҋ�
						send_Message("CreateRoomFail/ok");
						RoomCh = false;
						break;
					}
				} // for��
				if (RoomCh) {
					RoomInfo new_room = new RoomInfo(message, this, password);
					room_vc.add(new_room);// ��ü �� ���Ϳ� ���� �߰�
					send_Message("CreateRoom/" + message);
					send_Message("contact_user/" + this.Nickname);
					//���� �����ڵ鿡�� �˸�
					Broadcast("New_Room/" + message);
					Broadcast("room_list_update/ok");
				}
				RoomCh = true;
			} else if (protocol.equals("inviteRoom")) {//������ �ʴ��
				String roomname = st.nextToken();
				for (int i = 0; i < user_vc.size(); i++) {//�ʴ��ϴ»���� ���� �ʴ��һ���� �г������� �������߿��� ã��
					UserInfo u = (UserInfo) user_vc.elementAt(i);
					if (u.Nickname.equals(message)) {//ã����
						for (int j = 0; j < room_vc.size(); j++) {
							RoomInfo r = (RoomInfo) room_vc.elementAt(j);//�ʴ뺸������� ������ �濡 �ִ��� Ȯ��
							for (int k = 0; k < r.Room_User_vc.size(); k++) {
								UserInfo u1 = (UserInfo) r.Room_User_vc.elementAt(k);
								if (this.Nickname.equals(u1.Nickname)) {
									if (r.Room_name.equals(roomname)) {//�ʴ뺸�� ����� ������ �濡 ������ �ʴ�
										u.send_Message("Invite/" + r.Room_name + "/" + r.Room_Password);
										break;
									} else if (!this.Nickname.equals(u1.Nickname)) {//������ �ʴ����
										send_Message("InviteFail/ok");
									}
								}
							}
						}
					}
				}
			} else if (protocol.equals("RoomOut")) {//�濡�� ��������
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo) room_vc.elementAt(i);
					for (int j = 0; j < r.Room_User_vc.size(); j++) {
						UserInfo u = (UserInfo) r.Room_User_vc.elementAt(j);
						if (this.Nickname.equals(u.Nickname)) {//���� ������ ��Ͽ��� ����� ����
							r.BroadCast_Room("Out_User/" + u.Nickname);
						}
					}
					r.Room_User_vc.remove(this);
				}
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo) room_vc.elementAt(i);
					if (r.Room_User_vc.size() == 0) {//�濡 �����ڰ� ������ �� ����
						Broadcast("RemoveRoom/" + r.Room_name);
						room_vc.removeElement(r);
					}
				}
			}

		}

		private void Broadcast(String str) {//������ ������ڿ��� �޼��� ������
			for (int i = 0; i < user_vc.size(); i++) {
				UserInfo u = (UserInfo) user_vc.elementAt(i);
				u.send_Message(str);
			}
		}

		private void send_Message(String str) {//�� ���������� ����ڿ��� �޼��� ������
			try {
				dos.writeUTF(str);
			} catch (IOException e) {

			}
		}

		private boolean Filereader(String ID, String Password) {//������ �о�鿩 �Է��� ȸ�������� ����Ǿ��ִ��� Ȯ��
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

		private boolean Filewriter(String ID, String Nickname, String Password) {//ȸ�����Խ� ȸ������ �����ϱ�
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

	}// UsreInfoŬ���� ��

	class RoomInfo {//������ 
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
