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
	
	
	public Login(String title, int width, int height) { //패널 보여줌.
		setTitle(title);
		setSize(width, height);
		setLocation(100,100); //프레임의 생성 위치 조절
		
		//레이아웃
		setLayout(new BorderLayout());
		
		createPanBase();
		add(panBase);
		
		setVisible(true);

	}
	
	private void createPanBase() { //메인 패널 생성
		panBase = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panBase.setLayout(new BorderLayout());
		panBase.setBackground(Color.WHITE);
		
		createPanCenter();
		//크리에이트판센터함수를 이용해서 판 센터를 만들고 판베이스에 붙임.
		panBase.add(panCenter,BorderLayout.NORTH);
		
		createPanSouth();
		//크리에이트판사우스함수를 이용해서 판 사우스를 만들고 판베이스에 붙임.
		panBase.add(panSouth,BorderLayout.CENTER);
		
	}
	
	private void createPanSouth() { //로그인 버튼, 패스워드 버튼
		panSouth = new JPanel();
		panSouth.setBackground(Color.WHITE);
		
		btnLogin = new JButton(" 로그인");
		btnLogin.addActionListener(this);
		panSouth.add(btnLogin);
		
		btnRegister = new JButton("회원가입");
		btnRegister.addActionListener(this);
		panSouth.add(btnRegister);
	}

	private void createPanCenter() { //로그인 틀, 패스워드 틀
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
	
	private boolean checkIDPW(String id, String pw) { //아이디 패스워드 확인 함수
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
	
	public static String getA() { //다른 클래스에 아이디를 넘기기 위한 함수.
        return loginID;
    }

	public static void main(String[] args) { //설정창
		DB.init();
		new Login("Login", 600, 150); //타이틀, 너비, 높이를 매개변수로 받음
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
				new Lobby("로비", 800,600).CLIENT(id+"님이 접속하셨습니다.");
				
				String sql = "INSERT INTO LOGIN(ID) VALUES('"+id+"')";
				DB.executeQuery(sql);
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
			new Register("회원가입", 480, 500);
		}
	}
}
