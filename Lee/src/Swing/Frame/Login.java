package Swing.Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

	private JPanel panBase, panCenter, panSouth;
	private JButton btnLogin, btnRegister;
	private JLabel lblID, lblPW;
	private JTextField tfID;
	private JPasswordField tfPW;
	private ImageIcon[] Loginimgs = {new ImageIcon("image/login.png"),
									new ImageIcon("image/login2.png"),
									new ImageIcon("image/login3.png")};
	
	private ImageIcon[] Regimgs = {new ImageIcon("image/register.png"),
									new ImageIcon("image/register2.png"),
									new ImageIcon("image/register3.png")};
	
	private ImageIcon logoImg = new ImageIcon("image/logo.png") ;
	private JLabel lbllogo;
	private static String loginID;
	
	public Login(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocation(100,100); //�������� ���� ��ġ ����
		
		//���̾ƿ�
		setLayout(new BorderLayout());
		
		createPanBase();
		add(panBase);
		
		setVisible(true);
	}
	
	private void createPanBase() {
		panBase = new JPanel(); // JPanel�� �⺻������ �÷ο�� �ٲٴ°���.
		panBase.setLayout(new BorderLayout());
		panBase.setBackground(Color.WHITE);
		
		lbllogo=new JLabel(logoImg);
		panBase.add(lbllogo,BorderLayout.NORTH);
		createPanCenter();
		//ũ������Ʈ�Ǽ����Լ��� �̿��ؼ� �� ���͸� ����� �Ǻ��̽��� ����.
		panBase.add(panCenter,BorderLayout.CENTER);
		createPanSouth();
		//ũ������Ʈ�ǻ�콺�Լ��� �̿��ؼ� �� ��콺�� ����� �Ǻ��̽��� ����.
		panBase.add(panSouth,BorderLayout.SOUTH);
	}

	private void createPanCenter() {
		panCenter = new JPanel();
		panCenter.setBackground(Color.WHITE);
		panCenter.setLayout(new GridLayout(2,2));
		panCenter.setBorder(BorderFactory.createEmptyBorder(10,10,10,100));
		
		lblID = new JLabel("ID : ",JLabel.CENTER);
		tfID = new JTextField("cxv963",15);
		
		lblPW = new JLabel("PW : ",JLabel.CENTER);
		tfPW = new JPasswordField("1234",15);
		
		panCenter.add(lblID);
		panCenter.add(tfID);		
		panCenter.add(lblPW);
		panCenter.add(tfPW);		
	}

	private void createPanSouth() {
		panSouth = new JPanel();
		panSouth.setBackground(Color.WHITE);
		
		btnLogin = new JButton(Loginimgs[0]);
		btnLogin.setBorderPainted(false);
		btnLogin.setFocusPainted(false);
		btnLogin.setContentAreaFilled(false);
		btnLogin.addActionListener(this);
		
		//�α��� �̹��� ����
		btnLogin.setRolloverIcon(Loginimgs[1]); 
		btnLogin.setPressedIcon(Loginimgs[2]);
		
		panSouth.add(btnLogin);
		
		btnRegister = new JButton(Regimgs[0]);
		btnRegister.setBorderPainted(false);
		btnRegister.setFocusPainted(false);
		btnRegister.setContentAreaFilled(false);
		btnRegister.addActionListener(this);
		
		//ȸ������ �̹�������
		btnRegister.setRolloverIcon(Regimgs[1]); 
		btnRegister.setPressedIcon(Regimgs[2]);
		
		panSouth.add(btnRegister);
	}

	public static void main(String[] args) {
		DB.init();
		new Login("Login", 800, 600); //Ÿ��Ʋ, �ʺ�, ���̸� �Ű������� ����
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnLogin) {
			String id = tfID.getText();
			String pw = tfPW.getText();
			
			//üũ���̵��н����� �Լ��� ���� �޾ƿ� ���� ���� üũ�� �ְ� ���� üũ�� ���� Ʈ��� ���̵� ���ΰ� ���ٸ�
			//�������������� �����ְ� �ƴϸ� ������������ ������. ���� üũ ���� true�� �ƴϸ� �α��� ���� �޼����� ���
			
			boolean check = checkIDPW(id, pw);
			if(check) {
				if(id.equals("admin")) { 
					System.out.println("������ �α���");
					new Admin("����������", 900, 550);
					dispose();
				}else {
					System.out.println("�α��� ����");	
					new Main("�˰����", 800, 550); //Ÿ��Ʋ, �ʺ�, ���̸� �Ű������� ����
					this.dispose();	
				}
			}else {
				System.out.println("�α��� ����");
				JOptionPane.showMessageDialog(null, "�α����� �����߽��ϴ�.");
				tfID.setText("");
				tfPW.setText("");
				tfID.requestFocus();
			}
		}
		
		// ȸ������ ��ư�� ������ ȸ������ â�� ���
		if(obj==btnRegister) {
			new Register("ȸ������", 350, 500); //Ÿ��Ʋ, �ʺ�, ���̸� �Ű������� ����
		}
	}

	//üũ���̵��н������Լ��� sql������ ������̺� �ִ� ���߿� ���� �Է��� ���̵�� ��й�ȣ�� ������
	//����ƽ���� �α��ξ��̵� �Է��� ���̵� ���� �����ϰ� ����üũ�� true���� �����ϰ� ����, �ƴϸ� false���� �����ϰ� ����
	//�α��ξ��̵�� �ٿ����Լ��� ���� ����Ŭ������ �Ѿ�� ���� ��.
	private boolean checkIDPW(String id, String pw) {
		boolean check = false;
		String sql = "SELECT * FROM MEMBER WHERE ID = '"+id+"' AND PW='"+pw+"'";
		ResultSet rs = DB.getResultSet(sql);
		try {
			if(rs.next()) { // ���̵� ��й�ȣ Ȯ��
				System.out.println(rs.getString(1));
				loginID=rs.getString(1);
				System.out.println(loginID);
				check = true;
			}else {
				System.out.println("���̵� ��й�ȣ�� Ȯ���� �ֽñ� �ٶ��ϴ�.");
				check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//�α��� �� ���̵� �������� ������ ���� �Լ�
		getA();
		return check;
	}
	public static String getA() {
        return loginID;
    }
}
