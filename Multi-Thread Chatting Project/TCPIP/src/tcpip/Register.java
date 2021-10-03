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
		
		//레이아웃
		setLayout(new BorderLayout());
		
		
		createPanBase();
		add(panBase);
		
		setVisible(true);
	}
	

	private void createPanBase() {
		panBase = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panBase.setLayout(null);
		panBase.setBackground(Color.WHITE);
		
		lblLogo = new JLabel("회원 가입",JLabel.CENTER);
		lblLogo.setFont(new Font("",Font.BOLD,30));
		lblLogo.setBounds(55, -70, 300, 200);
		panBase.add(lblLogo);
		
		
		//이름
		lblNAME = new JLabel("이름");
		lblNAME.setFont(new Font("", Font.BOLD, 15));
		lblNAME.setBounds(45, 15, 100, 200);
		panBase.add(lblNAME);
		
		tfNAME = new JTextField();
		tfNAME.setBounds(130, 100, 200, 30);
		panBase.add(tfNAME);
		
		//아이디
		lblID = new JLabel("아이디",JLabel.LEFT);
		lblID.setFont(new Font("", Font.BOLD, 15));
		lblID.setBounds(45, 55, 100, 200);
		panBase.add(lblID);
		
		tfID = new JTextField();
		tfID.setBounds(130, 140, 200, 30);
		panBase.add(tfID);
		
		btCheckID = new JButton("중복체크");
		btCheckID.setBounds(340, 140, 90, 29);
		btCheckID.setBorderPainted(true);
		btCheckID.setFocusPainted(false);
		btCheckID.setContentAreaFilled(false);
		btCheckID.addActionListener(this);
		
		btCheckID.addActionListener(this);
		panBase.add(btCheckID);
		
		//비밀번호
		lblPW = new JLabel("비밀번호",JLabel.LEFT);
		lblPW.setFont(new Font("", Font.BOLD, 15));
		lblPW.setBounds(45, 95, 100, 200);
		panBase.add(lblPW);
		
		tfPW = new JPasswordField();
		tfPW.setBounds(130, 180, 200, 30);
		panBase.add(tfPW);
		
		//비밀번호 재확인
		lblPW2 = new JLabel("재확인",JLabel.LEFT);
		lblPW2.setFont(new Font("", Font.BOLD, 15));
		lblPW2.setBounds(45, 135, 100, 200);
		panBase.add(lblPW2);
		
		tfPW2 = new JPasswordField();
		tfPW2.setBounds(130, 220, 200, 30);
		panBase.add(tfPW2);
		
		//폰번호
		lblPHONE = new JLabel("전화번호",JLabel.LEFT);
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
		
		//이메일
		lblEMAIL = new JLabel("이메일",JLabel.LEFT);
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
		
		//버튼
		//btOk = new JButton(okimgs[0]);
		btOk=new JButton("가입");
		btOk.setBounds(105, 400, 100, 30);
		btOk.setBorderPainted(true);
		btOk.setFocusPainted(false);
		btOk.setContentAreaFilled(false);
		btOk.addActionListener(this);
		
		/*
		//가입 이미지 변경
		btOk.setRolloverIcon(okimgs[1]); 
		btOk.setPressedIcon(okimgs[2]);
		*/
		
		panBase.add(btOk);
		
		//btCancle = new JButton(cancleimgs[0]);
		btCancle = new JButton("취소");
		btCancle.setBounds(215, 400, 100, 30);
		btCancle.setBorderPainted(true);
		btCancle.setFocusPainted(false);
		btCancle.setContentAreaFilled(false);
		btCancle.addActionListener(this);
		/*
		//취소 이미지 변경
		btCancle.setRolloverIcon(cancleimgs[1]); 
		btCancle.setPressedIcon(cancleimgs[2]);
		*/
		
		panBase.add(btCancle);
	}

	public static void main(String[] args) {
		DB.init();
		new Register("회원가입", 450,500); //타이틀, 너비, 높이를 매개변수로 받음
		
		
	}
	
	//sql문으로 멤버테이블에 똑같은 아이디가 있는지 검색해서 있으면 false, 없고 공백이면 false,
	//만약 전부 아니면 true를 리턴
	private boolean checkID(String id) {
		boolean check = false;
		String sql = "SELECT * FROM MEMBER WHERE ID = '"+id+"'";
		ResultSet rs = DB.getResultSet(sql);
		try {
			if(rs.next()) {
				JOptionPane.showMessageDialog(null, "이미 사용중인 아이디입니다.");
				check = false;
			}else {
				if(id.equals("")) {
					check = false;
					JOptionPane.showMessageDialog(null, "공백입니다.");
				}else {
					JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
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
		
		//아이디 중복체크
		//변수 아이디에 아이디 텍스트 필드에 있는 값을 저장하고 체크 아이디 함수로 보냄.
		if(obj==btCheckID)
		{
			String id = tfID.getText();;
			System.out.println(id);
				check = checkID(id);
		}
		
		if(obj==btOk)
		{
			//입력한 값들을 변수에 저장함
			//콤보박스와 텍스트 필드의 값들을 합치는 작업
			String phone=cbPhone.getItemAt(cbPhone.getSelectedIndex());
			phone=phone + tfPHONE2.getText()+tfPHONE3.getText();

			String email=tfEMAIL.getText()+"@"+cbEmail.getItemAt(cbEmail.getSelectedIndex());
			
			//비밀번호 텍스트 필드랑, 재확인 텍스트필드가 같고
			if(tfPW.getText().equals(tfPW2.getText()))
			{
				//아이디 중복검사 함수 값이 트루면 멤버테이블에 정보를 넣고 가입을 축하합니다 메세지를 보냄 아니면 오류메세지를 보냄
				if(check==true) {
					String insertSQL = 
							"INSERT INTO MEMBER(ID, PW, NAME, PHONE, EMAIL) "
						  + "VALUES('"+tfID.getText()+"','"+tfPW.getText()+"', '"+tfNAME.getText()+"', '"+phone+"','"+email+"')";
					DB.executeQuery(insertSQL);
					JOptionPane.showMessageDialog(null, "가입을 축하드립니다.");
					dispose();
				}else
				{
					JOptionPane.showMessageDialog(null, "아이디 중복검사를 해주십시오.");
				}
				
			}else
			{
				JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.");
			}
		}
		//취소 버튼을 누르면 dispose()함
		if(obj==btCancle)
		{
			dispose();
		}
	}
}
