package tcpip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JFrame implements ActionListener {
	private JPanel panBase;
	private JLabel lblLogo, lblemail, lblNAME, lblID, lblPW, lblPW2, lblPHONE, lblEMAIL;
	private JTextField tfNAME, tfID, tfPW, tfPW2, tfPHONE2,tfPHONE3, tfEMAIL;
	private JButton btCheckID, btOk, btCancle;
	private String[] phone1 = {"010","011"};
	private String[] email = {"naver.com","daum.com"};
	private ImageIcon[] okimgs = {new ImageIcon("image/ok.png"),
									new ImageIcon("image/ok2.png"),
									new ImageIcon("image/ok3.png")};
	private ImageIcon[] cancleimgs = {new ImageIcon("image/cancle.png"),
									new ImageIcon("image/cancle2.png"),
									new ImageIcon("image/cancle3.png")};
	private JComboBox<String> cbPhone;
	private JComboBox<String> cbEmail;
	
	private boolean check;

	public Register(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocation(500,500);
		
		//���̾ƿ�
		setLayout(new BorderLayout());
		
		
		createPanBase();
		add(panBase);
		
		setVisible(true);
	}
	

	private void createPanBase() {
		panBase = new JPanel(); // JPanel�� �⺻������ �÷ο�� �ٲٴ°���.
		panBase.setLayout(null);
		panBase.setBackground(Color.WHITE);
		
		lblLogo = new JLabel("ȸ�� ����",JLabel.CENTER);
		lblLogo.setFont(new Font("",Font.BOLD,30));
		lblLogo.setBounds(55, -70, 300, 200);
		panBase.add(lblLogo);
		
		
		//�̸�
		lblNAME = new JLabel("�̸�");
		lblNAME.setFont(new Font("", Font.BOLD, 15));
		lblNAME.setBounds(45, 15, 100, 200);
		panBase.add(lblNAME);
		
		tfNAME = new JTextField();
		tfNAME.setBounds(130, 100, 200, 30);
		panBase.add(tfNAME);
		
		//���̵�
		lblID = new JLabel("���̵�",JLabel.LEFT);
		lblID.setFont(new Font("", Font.BOLD, 15));
		lblID.setBounds(45, 55, 100, 200);
		panBase.add(lblID);
		
		tfID = new JTextField();
		tfID.setBounds(130, 140, 200, 30);
		panBase.add(tfID);
		
		btCheckID = new JButton("�ߺ�üũ");
		btCheckID.setBounds(340, 140, 90, 29);
		btCheckID.setBorderPainted(true);
		btCheckID.setFocusPainted(false);
		btCheckID.setContentAreaFilled(false);
		btCheckID.addActionListener(this);
		
		btCheckID.addActionListener(this);
		panBase.add(btCheckID);
		
		//��й�ȣ
		lblPW = new JLabel("��й�ȣ",JLabel.LEFT);
		lblPW.setFont(new Font("", Font.BOLD, 15));
		lblPW.setBounds(45, 95, 100, 200);
		panBase.add(lblPW);
		
		tfPW = new JPasswordField();
		tfPW.setBounds(130, 180, 200, 30);
		panBase.add(tfPW);
		
		//��й�ȣ ��Ȯ��
		lblPW2 = new JLabel("��Ȯ��",JLabel.LEFT);
		lblPW2.setFont(new Font("", Font.BOLD, 15));
		lblPW2.setBounds(45, 135, 100, 200);
		panBase.add(lblPW2);
		
		tfPW2 = new JPasswordField();
		tfPW2.setBounds(130, 220, 200, 30);
		panBase.add(tfPW2);
		
		//����ȣ
		lblPHONE = new JLabel("��ȭ��ȣ",JLabel.LEFT);
		lblPHONE.setFont(new Font("", Font.BOLD, 15));
		lblPHONE.setBounds(45, 175, 100, 200);
		panBase.add(lblPHONE);
		
		cbPhone = new JComboBox<String>(phone1);
		cbPhone.setBounds(130, 260, 48, 29);
		cbPhone.addActionListener(this);
		panBase.add(cbPhone);
		
		tfPHONE2 = new JTextField();
		tfPHONE2.setBounds(190, 260, 70, 30);
		panBase.add(tfPHONE2);
		
		tfPHONE3 = new JTextField();
		tfPHONE3.setBounds(270, 260, 70, 30);
		panBase.add(tfPHONE3);
		
		//�̸���
		lblEMAIL = new JLabel("�̸���",JLabel.LEFT);
		lblEMAIL.setFont(new Font("", Font.BOLD, 15));
		lblEMAIL.setBounds(45, 215, 100, 200);
		panBase.add(lblEMAIL);
		
		tfEMAIL = new JTextField();
		tfEMAIL.setBounds(130, 300, 100, 30);
		panBase.add(tfEMAIL);
		
		lblemail = new JLabel("@");
		lblemail.setBounds(237,300, 50, 30);
		panBase.add(lblemail);
		
		cbEmail = new JComboBox<String>(email);
		cbEmail.setBounds(260, 300, 100, 29);
		cbEmail.addActionListener(this);
		panBase.add(cbEmail);
		
		//��ư
		//btOk = new JButton(okimgs[0]);
		btOk=new JButton("����");
		btOk.setBounds(105, 400, 100, 30);
		btOk.setBorderPainted(true);
		btOk.setFocusPainted(false);
		btOk.setContentAreaFilled(false);
		btOk.addActionListener(this);
		
		/*
		//���� �̹��� ����
		btOk.setRolloverIcon(okimgs[1]); 
		btOk.setPressedIcon(okimgs[2]);
		*/
		
		panBase.add(btOk);
		
		//btCancle = new JButton(cancleimgs[0]);
		btCancle = new JButton("���");
		btCancle.setBounds(215, 400, 100, 30);
		btCancle.setBorderPainted(true);
		btCancle.setFocusPainted(false);
		btCancle.setContentAreaFilled(false);
		btCancle.addActionListener(this);
		/*
		//��� �̹��� ����
		btCancle.setRolloverIcon(cancleimgs[1]); 
		btCancle.setPressedIcon(cancleimgs[2]);
		*/
		
		panBase.add(btCancle);
	}

	public static void main(String[] args) {
		DB.init();
		new Register("ȸ������", 450,500); //Ÿ��Ʋ, �ʺ�, ���̸� �Ű������� ����
		
		
	}
	
	//sql������ ������̺� �Ȱ��� ���̵� �ִ��� �˻��ؼ� ������ false, ���� �����̸� false,
	//���� ���� �ƴϸ� true�� ����
	private boolean checkID(String id) {
		boolean check = false;
		String sql = "SELECT * FROM MEMBER WHERE ID = '"+id+"'";
		ResultSet rs = DB.getResultSet(sql);
		try {
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "�̹� ������� ���̵��Դϴ�.");
				check = false;
			}else {
				if(id.equals("")) {
					check = false;
					JOptionPane.showMessageDialog(null, "�����Դϴ�.");
				}else {
					JOptionPane.showMessageDialog(null, "��� ������ ���̵��Դϴ�.");
					check = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj =e.getSource();
		
		//���̵� �ߺ�üũ
		//���� ���̵� ���̵� �ؽ�Ʈ �ʵ忡 �ִ� ���� �����ϰ� üũ ���̵� �Լ��� ����.
		if(obj==btCheckID)
		{
			String id = tfID.getText();;
			System.out.println(id);
				check = checkID(id);
		}
		
		if(obj==btOk)
		{
			//�Է��� ������ ������ ������
			//�޺��ڽ��� �ؽ�Ʈ �ʵ��� ������ ��ġ�� �۾�
			String phone=cbPhone.getItemAt(cbPhone.getSelectedIndex());
			phone=phone + tfPHONE2.getText()+tfPHONE3.getText();

			String email=tfEMAIL.getText()+"@"+cbEmail.getItemAt(cbEmail.getSelectedIndex());
			
			//��й�ȣ �ؽ�Ʈ �ʵ��, ��Ȯ�� �ؽ�Ʈ�ʵ尡 ����
			if(tfPW.getText().equals(tfPW2.getText()))
			{
				//���̵� �ߺ��˻� �Լ� ���� Ʈ��� ������̺� ������ �ְ� ������ �����մϴ� �޼����� ���� �ƴϸ� �����޼����� ����
				if(check==true) {
					String insertSQL = 
							"INSERT INTO MEMBER(ID, PW, NAME, PHONE, EMAIL) "
						  + "VALUES('"+tfID.getText()+"','"+tfPW.getText()+"', '"+tfNAME.getText()+"', '"+phone+"','"+email+"')";
					DB.executeQuery(insertSQL);
					JOptionPane.showMessageDialog(null, "������ ���ϵ帳�ϴ�.");
					dispose();
				}else
				{
					JOptionPane.showMessageDialog(null, "���̵� �ߺ��˻縦 ���ֽʽÿ�.");
				}
				
			}else
			{
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�.");
			}
		}
		//��� ��ư�� ������ dispose()��
		if(obj==btCancle)
		{
			dispose();
		}
	}
}
