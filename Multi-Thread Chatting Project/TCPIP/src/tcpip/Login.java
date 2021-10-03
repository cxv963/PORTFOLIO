package tcpip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tcpip.DB;

public class Login extends JFrame implements ActionListener {
	private JPanel panBase, panCenter, panSouth;
	private JLabel lblID, lblPW;
	private JTextField tfID;
	private JPasswordField tfPW;
	private JButton btnLogin, btnRegister;
	private static String loginID;
	
	
	public Login(String title, int width, int height) { //�г� ������.
		setTitle(title);
		setSize(width, height);
		setLocation(100,100); //�������� ���� ��ġ ����
		
		//���̾ƿ�
		setLayout(new BorderLayout());
		
		createPanBase();
		add(panBase);
		
		setVisible(true);

	}
	
	private void createPanBase() { //���� �г� ����
		panBase = new JPanel(); // JPanel�� �⺻������ �÷ο�� �ٲٴ°���.
		panBase.setLayout(new BorderLayout());
		panBase.setBackground(Color.WHITE);
		
		createPanCenter();
		//ũ������Ʈ�Ǽ����Լ��� �̿��ؼ� �� ���͸� ����� �Ǻ��̽��� ����.
		panBase.add(panCenter,BorderLayout.NORTH);
		
		createPanSouth();
		//ũ������Ʈ�ǻ�콺�Լ��� �̿��ؼ� �� ��콺�� ����� �Ǻ��̽��� ����.
		panBase.add(panSouth,BorderLayout.CENTER);
		
	}
	
	private void createPanSouth() { //�α��� ��ư, �н����� ��ư
		panSouth = new JPanel();
		panSouth.setBackground(Color.WHITE);
		
		btnLogin = new JButton(" �α���");
		btnLogin.addActionListener(this);
		panSouth.add(btnLogin);
		
		btnRegister = new JButton("ȸ������");
		btnRegister.addActionListener(this);
		panSouth.add(btnRegister);
	}

	private void createPanCenter() { //�α��� Ʋ, �н����� Ʋ
		panCenter = new JPanel();
		panCenter.setBackground(Color.WHITE);
		panCenter.setLayout(new GridLayout(2,2));
		panCenter.setBorder(BorderFactory.createEmptyBorder(10,10,10,100));
		
		lblID = new JLabel("ID : ",JLabel.CENTER);
		tfID = new JTextField("cxv963",20);
		
		lblPW = new JLabel("PW : ",JLabel.CENTER);
		tfPW = new JPasswordField("1234",20);
		
		panCenter.add(lblID);
		panCenter.add(tfID);		
		panCenter.add(lblPW);
		panCenter.add(tfPW);	
	}
	
	private boolean checkIDPW(String id, String pw) { //���̵� �н����� Ȯ�� �Լ�
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
	
	public static String getA() { //�ٸ� Ŭ������ ���̵� �ѱ�� ���� �Լ�.
        return loginID;
    }

	public static void main(String[] args) { //����â
		DB.init();
		new Login("Login", 600, 150); //Ÿ��Ʋ, �ʺ�, ���̸� �Ű������� ����
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnLogin) {
			String id = tfID.getText();
			String pw = tfPW.getText();
			
			boolean check = checkIDPW(id, pw);
			if(check)
			{
				this.dispose();
				new Lobby("�κ�", 800,600).CLIENT(id+"���� �����ϼ̽��ϴ�.");
				
				String sql = "INSERT INTO LOGIN(ID) VALUES('"+id+"')";
				DB.executeQuery(sql);
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
			new Register("ȸ������", 480, 500);
		}
	}
}
