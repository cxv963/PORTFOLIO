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
		setLocation(100,100); //프레임의 생성 위치 조절
		
		//레이아웃
		setLayout(new BorderLayout());
		
		createPanBase();
		add(panBase);
		
		setVisible(true);
	}
	
	private void createPanBase() {
		panBase = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panBase.setLayout(new BorderLayout());
		panBase.setBackground(Color.WHITE);
		
		lbllogo=new JLabel(logoImg);
		panBase.add(lbllogo,BorderLayout.NORTH);
		createPanCenter();
		//크리에이트판센터함수를 이용해서 판 센터를 만들고 판베이스에 붙임.
		panBase.add(panCenter,BorderLayout.CENTER);
		createPanSouth();
		//크리에이트판사우스함수를 이용해서 판 사우스를 만들고 판베이스에 붙임.
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
		
		//로그인 이미지 변경
		btnLogin.setRolloverIcon(Loginimgs[1]); 
		btnLogin.setPressedIcon(Loginimgs[2]);
		
		panSouth.add(btnLogin);
		
		btnRegister = new JButton(Regimgs[0]);
		btnRegister.setBorderPainted(false);
		btnRegister.setFocusPainted(false);
		btnRegister.setContentAreaFilled(false);
		btnRegister.addActionListener(this);
		
		//회원가입 이미지변경
		btnRegister.setRolloverIcon(Regimgs[1]); 
		btnRegister.setPressedIcon(Regimgs[2]);
		
		panSouth.add(btnRegister);
	}

	public static void main(String[] args) {
		DB.init();
		new Login("Login", 800, 600); //타이틀, 너비, 높이를 매개변수로 받음
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnLogin) {
			String id = tfID.getText();
			String pw = tfPW.getText();
			
			//체크아이디패스워드 함수를 통해 받아온 값을 변수 체크에 넣고 변수 체크의 값이 트루고 아이디가 어드민과 같다면
			//관리자페이지를 보여주고 아니면 메인페이지를 보여줌. 만약 체크 값이 true가 아니면 로그인 실패 메세지를 띄움
			
			boolean check = checkIDPW(id, pw);
			if(check) {
				if(id.equals("admin")) { 
					System.out.println("관리자 로그인");
					new Admin("관리페이지", 900, 550);
					dispose();
				}else {
					System.out.println("로그인 성공");	
					new Main("알고사자", 800, 550); //타이틀, 너비, 높이를 매개변수로 받음
					this.dispose();	
				}
			}else {
				System.out.println("로그인 실패");
				JOptionPane.showMessageDialog(null, "로그인이 실패했습니다.");
				tfID.setText("");
				tfPW.setText("");
				tfID.requestFocus();
			}
		}
		
		// 회원가입 버튼이 눌리면 회원가입 창을 띄움
		if(obj==btnRegister) {
			new Register("회원가입", 350, 500); //타이틀, 너비, 높이를 매개변수로 받음
		}
	}

	//체크아이디패스워드함수는 sql문으로 멤버테이블에 있는 값중에 내가 입력한 아이디와 비밀번호가 있으면
	//스태틱변수 로그인아이디에 입력한 아이디 값을 저장하고 변수체크에 true값을 저장하고 리턴, 아니면 false값을 저장하고 리턴
	//로그인아이디는 겟에이함수를 통해 메인클래스로 넘어가기 위한 것.
	private boolean checkIDPW(String id, String pw) {
		boolean check = false;
		String sql = "SELECT * FROM MEMBER WHERE ID = '"+id+"' AND PW='"+pw+"'";
		ResultSet rs = DB.getResultSet(sql);
		try {
			if(rs.next()) { // 아이디 비밀번호 확인
				System.out.println(rs.getString(1));
				loginID=rs.getString(1);
				System.out.println(loginID);
				check = true;
			}else {
				System.out.println("아이디나 비밀번호를 확인해 주시기 바랍니다.");
				check = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//로그인 된 아이디를 메인으로 보내기 위한 함수
		getA();
		return check;
	}
	public static String getA() {
        return loginID;
    }
}
