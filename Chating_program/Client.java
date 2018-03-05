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
	private JButton invite_btn = new JButton("ģ�� �ʴ�");
	private JButton create_room_btn = new JButton("�� �����");
	private JButton join_room_btn = new JButton("�� ����");
	private JList Room_list = new JList();
	// chat GUI Field
	private JFrame Chat_GUI = new JFrame();
	private JPanel ChatPane = new JPanel();
	private JTextField Chat_tf = new JTextField();
	private JButton send_btn = new JButton("����");
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
	private JButton register_btn = new JButton("ȸ������");
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

	// GUI���� �޼ҵ�
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
		lblNewLabel_1.setFont(new Font("���� �ٰռ��� M", Font.PLAIN, 40));
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
		lblRegister.setFont(new Font("���� �ٰռ��� M", Font.PLAIN, 40));
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

	Client() {// ������
		Login_init();// �α���ȭ�� ���� �޼ҵ�
		F_R_init();// �����ڹ� �渮��Ʈ ���� �޼ҵ�
		Register_init();// ȸ������ ���� �޼ҵ�
		start();
	}

	private void send_message(String str) {// ������ �޼��� ������ �޼ҵ�

		try {
			dos.writeUTF(str);
		} catch (IOException e) {

		}
	}

	private void start() {// ���α׷� ���۽� �׼Ǹ����� Ȱ��
		login_btn.addActionListener(this);
		go_rg.addActionListener(this);
		invite_btn.addActionListener(this);
		create_room_btn.addActionListener(this);
		join_room_btn.addActionListener(this);
		register_btn.addActionListener(this);
		send_btn.addActionListener(this);
		Chat_tf.addKeyListener(this);
	}

	private void Connection() {// ������ ���Ӽ����� �޼ҵ�
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "���� ����", "�˸�", JOptionPane.ERROR_MESSAGE);// ����ó��
																							// �κ�
		} // ��Ʈ�� ������
		send_message("connect/ok");
		// ���� Ȯ��
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						String msg = dis.readUTF();
						System.out.println("�����κ��� ���ŵ� �޼���" + msg);
						inmessage(msg);
					} catch (IOException e) {
						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
							JOptionPane.showMessageDialog(null, "�������� ���� ������", "�˸�", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e1) {
						}
						break;
					}
				}
			}

		});

		th.start();
	}

	private void Network() {// ������ �����ϴ� �޼ҵ�
		try {
			port = 810;
			socket = new Socket(IP, port);

			if (socket != null) {
				Connection();
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "���� ����", "�˸�", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "���� ����", "�˸�", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void inmessage(String str) {// ������ �޼��� ó��
		st = new StringTokenizer(str, "/");

		String protocol = st.nextToken();
		String Message = st.nextToken();

		System.out.println("��������" + protocol);
		System.out.println("����" + Message);
		if (protocol.equals("connect")) {// ���� ������
			System.out.println("���� ����");
		} else if (protocol.equals("login_ok")) {// �α��� ������
			Nickname = Message;
			Login_GUI.setVisible(false);
			F_R_GUI.setVisible(true);
			fr_List.add(Nickname);
			fr_list.setListData(fr_List);
		} else if (protocol.equals("login_fail")) {// �α��� ���н�
			JOptionPane.showMessageDialog(null, "�α��� ����", "�˸�", JOptionPane.ERROR_MESSAGE);
		} else if (protocol.equals("Register")) {// ȸ������ ������
			JOptionPane.showMessageDialog(null, "ȸ������ �Ǽ̽��ϴ�", "�˸�", JOptionPane.INFORMATION_MESSAGE);
			Rg_GUI.setVisible(false);
		} else if (protocol.equals("RegisterFail")) {// ȸ������ ���н�
			JOptionPane.showMessageDialog(null, "�̹� �Ȱ��� ID�� �ֽ��ϴ�", "�˸�", JOptionPane.ERROR_MESSAGE);
		} else if (protocol.equals("JoinRoom")) {// ������ ������
			Contact_user_list.removeAllElements();
			Chatting_area.setText("");
			Chat_init(Message);// ä��â ���� �޼ҵ�
			Chat_GUI.setVisible(true);

		} else if (protocol.equals("CreateRoom")) {// ����� ������
			Contact_user_list.removeAllElements();
			Chatting_area.setText("");
			Chat_init(Message);// ä��â ���� �޼ҵ�
			Chat_GUI.setVisible(true);
		} else if (protocol.equals("New_Room")) {// ���ο� ��˸�
			room_List.add(Message);
			Room_list.setListData(room_List);
		} else if (protocol.equals("Chatting")) {// ä�� ó��
			String msg = st.nextToken();
			Chatting_area.append(Message + msg);
		} else if (protocol.equals("OldUser")) {// ���� ������� �޾ƿ���
			fr_List.add(Message);
		} else if (protocol.equals("New User")) {// ���ο� ������
			fr_List.add(Message);
		} else if (protocol.equals("OldRoom")) {// ���� ���� �޾ƿ���
			room_List.add(Message);
		} else if (protocol.equals("room_list_update")) {// ���� GUI����
			Room_list.setListData(room_List);
		} else if (protocol.equals("user_list_update")) {// �����ڸ�� GUI����
			fr_list.setListData(fr_List);
		} else if (protocol.equals("InviteFail")) {// �ʴ� ���н�
			JOptionPane.showMessageDialog(null, "�ʴ� ����", "�˸�", JOptionPane.ERROR_MESSAGE);
		} else if (protocol.equals("RemoveRoom")) {// ���� ���ŵɽ�
			room_List.remove(Message);
			Room_list.setListData(room_List);
		} else if (protocol.equals("Invite")) {// �ʴ븦 �޾�����
			int ask = JOptionPane.showConfirmDialog(null, "ä�ù濡 �ʴ븦 �޾ҽ��ϴ� �����Ͻðڽ��ϱ�?(����ä�ù濡�� ������ �˴ϴ�)", Message + "�κ���",
					JOptionPane.YES_NO_OPTION);
			if (ask == JOptionPane.YES_OPTION) {
				String password = st.nextToken();
				send_message("RoomOut/ok");
				send_message("JoinRoom/" + Message + "/" + password);
			}
		} else if (protocol.equals("contact_user")) {// �濡 ���ӽ� ���� ���ӵ� ���� �����ڵ� ���
														// �޾ƿ���
			Contact_user_list.add(Message);
			contact_user_list.setListData(Contact_user_list);
		} else if (protocol.equals("Out_User")) {// �濡�� �����ڰ� ��������
			Contact_user_list.remove(Message);
			contact_user_list.setListData(Contact_user_list);
		} else if (protocol.equals("UserOut")) {// �����ڰ� ������ ��������
			fr_List.remove(Message);
		}
	}

	public static void main(String[] args) {
		new Client();
	}

	public void actionPerformed(ActionEvent e) {// �׼Ǹ����� ó��
		if (e.getSource() == login_btn) {// �α��� ��ư ������
			IP = IP_tf.getText().trim();
			ID = Login_ID_tf.getText().trim();
			Password = String.valueOf(Loginpf.getPassword());// ȸ������ ����
			Network();
			send_message("login/" + ID + "/" + Password);
		} else if (e.getSource() == go_rg) {// ȸ������â ����
			Rg_GUI.setVisible(true);
		} else if (e.getSource() == register_btn) {// ȸ������ ��ư ������
			IP = rg_server_IP.getText().trim();
			Network();
			ID = rg_ID_tf.getText().trim();
			Nickname = rg_nickname_tf.getText().trim();// ȸ�������� ����ڷκ��� �޾ƿ�
			Password = String.valueOf(rg_pf_1.getPassword()).trim();
			System.out.println(Password);
			send_message("register/" + ID + "/" + Nickname+"/"+Password);
		} // ȸ������ ����

		else if (e.getSource() == create_room_btn) {// ����� ��ư ������
			String roomname = JOptionPane.showInputDialog("�� �̸�");
			String roompassword = JOptionPane.showInputDialog("�� ��й�ȣ");// ������
																		// �޾ƿ���
			if (roomname != null) {
				send_message("RoomOut/ok");// ������ �������ִ� �� ���ͼ�
				send_message("CreateRoom/" + roomname + "/" + roompassword);// ������
																			// �����ְ�
			} else {
				JOptionPane.showMessageDialog(null, "���̸��� �����ּ���", "�˸�", JOptionPane.ERROR_MESSAGE);// ���̸�
																									// ����������
			}
		} else if (e.getSource() == invite_btn) {// �ʴ� ��ư ������
			String fr = (String) fr_list.getSelectedValue();
			String room = JOptionPane.showInputDialog("���̸�");// ������ �޾ƿ���
			if (fr != null && room != null) {
				send_message("inviteRoom/" + fr + "/" + room);// �ʴ��� ģ�� �̸��̶� ������
																// ������
			}
		} else if (e.getSource() == send_btn) {// ���� ��ư ������
			String roomname = Chat_GUI.getTitle();
			String message = Chat_tf.getText();// ���� ä�ù� ������ �޼�������
			send_message("Chatting/" + roomname + "/" + message + "\n");// ����
																		// ������
			Chat_tf.setText("");
			Chat_tf.requestFocus();
		} else if (e.getSource() == join_room_btn) {// �� ���� ��ư ������
			String roomname = (String) Room_list.getSelectedValue();
			String Password = JOptionPane.showInputDialog("�� ��й�ȣ");// ������
																	// ����ڷκ���
																	// �޾ƿ���
			send_message("RoomOut/ok");// ������ �ִ� �濡�� ������
			send_message("JoinRoom/" + roomname + "/" + Password);// ���� �õ�
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {// ä��â���� ���ʹ����� ���۹�ư �����Ŷ� �Ȱ��� ȿ������ �ҷ��� ��
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
