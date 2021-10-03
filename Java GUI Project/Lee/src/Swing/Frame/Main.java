package Swing.Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Swing.Frame.Login;

public class Main extends JFrame implements MouseListener, ActionListener {

	 private JPanel pBase, pFind, pUser, pTab, pMyPage;
	 private JTextField tfFind,mytfFind;
	 private JTable tbFashion,tbFood,tbDomesticAppliances,tbTemp,tbEtc,tbMyWriting;
	 private JScrollPane tsFashion, tsFood, tsDomesticAppliances,tsEtc;
	 private JTabbedPane tabPane;
	 private String[] strFind = {"제목","작성자"}, MystrFind= {"제목"};
	 private String[][] strFashion = new String[100][3],
			 			strFood = new String[100][3],
			 			strDomesticAppliances= new String[100][3],
			 			strEtc=new String[100][3],
			 			strMywriting=new String[100][4];
	 private String[] cT = {"제목","작성자","날짜"},MyWritingcT = {"섹션","제목","작성자","날짜"};
	 private JComboBox<String> cbFind,MycbFind;
	 private ImageIcon imgUser = new ImageIcon("image/user.png"), imgReturn=new ImageIcon("image/return.png");
	 private JLabel lblImgUser, lblReturn;
	 private ResultSet rs;
	 int writeindex;
	 private String sql;
	 private JButton logout, Delete, search, write, Mysearch;
	 DefaultTableModel tbFSmodel,tbFDmodel,tbDAmodel,tbETCmodel, myWritingModel;
	 static int flag;
	 static String temp;
	 static boolean mainflag=true, mypflag=false;
	 DefaultTableCellRenderer RednderCenter;
	 
	 public Main(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocation(100,100); //프레임의 생성 위치 조절
//		setLocationRelativeTo(this); 상대적인 위치에 생김.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//JFrame.속성값 < 속성값에 마우스 가져가면 번호보임.
		// 프로그램 종료시 다 종료
		
		//레이아웃
		setLayout(new BorderLayout());
		
		//판베이스는 메인페이지를 보여주기 위한 것이고 피마이페이지는 마이페이지를 보여주기 위한것으로 
		//내부전환을 사용했습니다.
		createPBase();
		createPMyPage();
		add(pMyPage);
		add(pBase);
		
		setVisible(true);
	}
	
	private void createPMyPage() {
		pMyPage = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		pMyPage.setLayout(null);
		pMyPage.setBackground(Color.WHITE);
		pMyPage.setBounds(0, 0, 800, 550);
		
		createMyInfo(Login.getA());
		
		lblReturn = new JLabel(imgReturn);
		lblReturn.setBounds(430, 10, 40, 40);
		lblReturn.addMouseListener(this);
		pMyPage.add(lblReturn);
		
		Delete=new JButton("삭제");
		Delete.setBounds(690, 470, 60, 30);
		Delete.addActionListener(this);
		pMyPage.add(Delete);
		
		MycbFind=new JComboBox<String>(MystrFind);
		MycbFind.setBounds(50, 235, 100, 30);
		mytfFind = new JTextField(15);
		mytfFind.setBounds(150, 235, 150, 31);
		
		Mysearch = new JButton("검색");
		Mysearch.setBounds(300, 235, 60, 30);
		Mysearch.addActionListener(this);
		
		pMyPage.add(Mysearch);
		pMyPage.add(MycbFind);
		pMyPage.add(mytfFind);
		
		pMyPage.setVisible(mypflag);
	}

//-------------------------------------마이페이지---------------------------------------
	//로그인 된 회원 정보를 보여주기 위한 함수로 sql문을 이용하여 멤버테이블에 있는 로그인된 아이디 칼럼전체를 변수에
	//저장하는 함수
	private void createMyInfo(String ID) {
		writeindex=0;
		String name="", phone="", email="",pw="";
		sql = "SELECT * FROM MEMBER WHERE ID='"+ID+"'";
		System.out.println(sql);
		rs = DB.getResultSet(sql);
		try {
			while(rs.next()) {
				pw=rs.getString(2);
				name=rs.getString(3);
				phone=rs.getString(4);
				email=rs.getString(5);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//------------------------정보 패널 생성---------------------------
		JPanel pInfo = new JPanel();
		pInfo.setLayout(new GridLayout(3,2));
		pInfo.setBounds(50, 100, 700, 100);
		
		JLabel lblName = new JLabel("이름     :  "+name,JLabel.LEFT);
		lblName.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblName);
		
		JLabel lblPhone = new JLabel("연락처 :  "+phone);
		lblPhone.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblPhone);
		
		JLabel lblId = new JLabel("아이디 :  "+ID);
		lblId.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblId);
	
		JLabel lblPw = new JLabel("비밀번호 :  "+pw);
		lblPw.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblPw);
		
		JLabel lblemail = new JLabel("이메일 :  "+email);
		lblemail.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblemail);
		
		pMyPage.add(pInfo);
		
		//--------------------내 게시글 보기 패널 생성---------------------------
		JPanel pMywriting = new JPanel();
		pMywriting.setLayout(new GridLayout());
		pMywriting.setBounds(50, 270, 700, 200);
		
		
		
		//함수를 통해 strMywriting에 각 테이블에 자신이 쓴 글을 뽑아옴.
		createMyWriting("FASHION",ID);
		createMyWriting("FOOD",ID);
		createMyWriting("DOMESTICAPPLIANCES",ID);
		createMyWriting("ETCC",ID);
		
		RednderCenter = new DefaultTableCellRenderer();
		RednderCenter.setHorizontalAlignment(SwingConstants.CENTER);
		
		//테이블모델을 생성하고 값을 변경하지 못하게 하는 코드
		myWritingModel = new DefaultTableModel(strMywriting,MyWritingcT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//여쪽에 있는 코드는 테이블의 크기조절을 하기 위한 코드
		tbMyWriting=new JTable(myWritingModel);
		tbMyWriting.getColumnModel().getColumn(0).setMaxWidth(600);
		tbMyWriting.getColumnModel().getColumn(1).setMaxWidth(2000);
		tbMyWriting.getColumnModel().getColumn(2).setMaxWidth(200);
		tbMyWriting.getColumnModel().getColumn(2).setCellRenderer(RednderCenter);
		tbMyWriting.getColumnModel().getColumn(3).setMaxWidth(400);
		tbMyWriting.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
		tbMyWriting.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
		tbMyWriting.addMouseListener(this);
		JScrollPane tsMyWriting=new JScrollPane(tbMyWriting);
		
		pMywriting.add(tsMyWriting);
		pMyPage.add(pMywriting);
	}
	//플레그 변수는 스태틱 변수로 마이페이지에 있는 검색란이 비어 있다면 0이고 검색란에 내용이 있다면 1로
	//검색여부를 통해서 sql문을 다르게 작동시켜 테이블의 내용을 표시하는 함수
	private void createMyWriting(String table, String ID) {
		if(flag==0)sql = "SELECT * FROM "+table+" WHERE ID='"+ID+"' ORDER BY DAY desc";
		if(flag==1)sql="SELECT * FROM "+table+" WHERE TITLE LIKE '%"+temp+"%' ORDER BY DAY desc";
		System.out.println(sql);
		rs = DB.getResultSet(sql);
		try {
			while(rs.next()) {
				strMywriting[writeindex][0]=table;
				strMywriting[writeindex][1]=rs.getString(1);
				strMywriting[writeindex][2]=rs.getString(2);
				strMywriting[writeindex][3]=rs.getString(3);
				writeindex=writeindex+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//------------------------------------메인페이지-----------------------------------------
	private void createPBase() {
		pBase = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		pBase.setLayout(null);
		pBase.setBackground(Color.WHITE);
		
		crateUser();
		crateFind();
		createTabbedPane();
		
		write = new JButton("글작성");
		write.setBounds(660, 475, 100, 30);
		write.addActionListener(this);
		pBase.add(write);
		
		pBase.add(pTab);
		pBase.add(pFind);
		add(pUser);
	}

	private void crateUser() {
		pUser = new JPanel();
		pUser.setBounds(470, 10, 300, 40);
		pUser.setLayout(new GridLayout(1, 3));
		pUser.setBackground(Color.WHITE);
		
		lblImgUser = new JLabel(imgUser,JLabel.RIGHT);
		lblImgUser.addMouseListener(this);
		pUser.add(lblImgUser);
	
		
		JLabel UserName = new JLabel(Login.getA(),JLabel.CENTER);
		UserName.setFont(new Font("맑은고딕",Font.BOLD,14));
		pUser.add(UserName);
		
		logout = new JButton("로그아웃"); //버튼을 누르면 로그인 페이지로 가야함.
		logout.addActionListener(this);
		pUser.add(logout);
	}
	
	//탭
	private void createTabbedPane() {
		pTab = new JPanel();
		pTab.setLayout(new BorderLayout());
		pTab.setBackground(Color.WHITE);
		pTab.setBounds(10, 100, 750, 370);

		//패션 테이블 생성
		createTable("FASHION",strFashion);
		tbFSmodel = new DefaultTableModel(strFashion,cT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		//푸드 테이블 생성
		createTable("FOOD",strFood);
		tbFDmodel = new DefaultTableModel(strFood,cT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		//가전제품 테이블 생성
		createTable("DOMESTICAPPLIANCES",strDomesticAppliances);
		tbDAmodel = new DefaultTableModel(strDomesticAppliances,cT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		//기타 테이블 생성
		createTable("ETCC",strEtc);
		tbETCmodel = new DefaultTableModel(strEtc,cT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tbFashion=new JTable(tbFSmodel);
		tbFashion.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbFashion.getColumnModel().getColumn(1).setMaxWidth(300);
		tbFashion.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbFashion.getColumnModel().getColumn(2).setMaxWidth(350);
		tbFashion.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
		tbFashion.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
		tsFashion=new JScrollPane(tbFashion);
		tbFashion.addMouseListener(this);
		

		tbFood=new JTable(tbFDmodel);
		tbFood.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbFood.getColumnModel().getColumn(1).setMaxWidth(300);
		tbFood.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbFood.getColumnModel().getColumn(2).setMaxWidth(350);
		tbFood.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
		tbFood.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
		tsFood=new JScrollPane(tbFood);
		tbFood.addMouseListener(this);
		
		tbDomesticAppliances=new JTable(tbDAmodel);
		tbDomesticAppliances.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbDomesticAppliances.getColumnModel().getColumn(1).setMaxWidth(300);
		tbDomesticAppliances.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbDomesticAppliances.getColumnModel().getColumn(2).setMaxWidth(350);
		tbDomesticAppliances.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
		tbDomesticAppliances.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
		tsDomesticAppliances=new JScrollPane(tbDomesticAppliances);
		tbDomesticAppliances.addMouseListener(this);
		
		tbEtc=new JTable(tbETCmodel);
		tbEtc.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbEtc.getColumnModel().getColumn(1).setMaxWidth(300);
		tbEtc.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbEtc.getColumnModel().getColumn(2).setMaxWidth(350);
		tbEtc.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
		tbEtc.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
		tsEtc=new JScrollPane(tbEtc);
		tbEtc.addMouseListener(this);
		
		tabPane = new JTabbedPane(JTabbedPane.LEFT);
		tabPane.addTab("",new ImageIcon("image/fashion.png"),tsFashion); // 패션 아이콘	
		tabPane.addTab("",new ImageIcon("image/food.png"),tsFood); // 음식 아이콘	
		tabPane.addTab("",new ImageIcon("image/home app.png"),tsDomesticAppliances); // 가전제품 아이콘	
		tabPane.addTab("",new ImageIcon("image/temp.png"),tsEtc); // 기타 아이콘
		tabPane.addMouseListener(this);
		pTab.add(tabPane);
		
	}
	
	//메인페이지에 테이블을 생성하기위한 코드로 플래그가 0이면 모든 글을 테이블에 넣고, 
	//플래그가 1이면 제목으로 검색한 결과를 테이블에 넣어줌
	//플래그가 2면 아이디로 검색한 결과를 테이블 넣어줌.
	private void createTable(String tName, String[][] tStr) {
		if(flag==0)sql = "SELECT * FROM "+tName+" ORDER BY DAY desc";
		if(flag==1)sql="SELECT * FROM "+tName+" WHERE TITLE LIKE '%"+temp+"%'";
		if(flag==2)sql="SELECT * FROM "+tName+" WHERE ID LIKE '%"+temp+"%'";
		rs = DB.getResultSet(sql);
		int i=0;
		try {
			while(rs.next())
			{
						tStr[i][0]=rs.getString(1);
						tStr[i][1]=rs.getString(2);
						tStr[i][2]=rs.getString(3);
						i=i+1;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//검색기능
	private void crateFind() {		
		pFind=new JPanel();
		pFind.setBounds(100, 60, 400, 40);
		pFind.setLayout(null);
		pFind.setBackground(Color.WHITE);
		
		cbFind=new JComboBox<String>(strFind);
		cbFind.setBounds(0, 0, 100, 30);
		tfFind = new JTextField(15);
		tfFind.setBounds(100, 0, 150, 31);
		
		search = new JButton("검색");
		search.setBounds(250, 0, 60, 30);
		search.addActionListener(this);
		
		pFind.add(search);
		pFind.add(cbFind);
		pFind.add(tfFind);
	}

	public static void main(String[] args) {
		DB.init();
		new Main("알고사자", 800,550); //타이틀, 너비, 높이를 매개변수로 받음
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if(obj==lblImgUser)
		{
			pBase.setVisible(false);
			pMyPage.setVisible(true);
			setTitle("마이페이지");
		}
		if(obj==tbFashion)
		{
			if(e.getClickCount()==2) 
			{
				//패션테이블에서 빈 컬럼을 선택했을때는 게시글이 없다. 아니면 글보기창을 띄움
				if(strFashion[tbFashion.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "게시글이 없습니다.");
				else createRead(strFashion,tbFashion,"FASHION");
			}	
		}
		if(obj==tbFood)
		{
			if(e.getClickCount()==2) 
			{
				if(strFood[tbFood.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "게시글이 없습니다.");
				else createRead(strFood,tbFood,"FOOD");
			}	
		}
		if(obj==tbDomesticAppliances)
		{
			if(e.getClickCount()==2) 
			{
				if(strDomesticAppliances[tbDomesticAppliances.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "게시글이 없습니다.");
				else createRead(strDomesticAppliances,tbDomesticAppliances,"DOMESTICAPPLIANCES");
			}	
		}
		if(obj==tbEtc)
		{
			if(e.getClickCount()==2) 
			{
				if(strEtc[tbEtc.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "게시글이 없습니다.");
				else createRead(strEtc,tbEtc,"ETCC");
			}	
		}
		if(obj==tbMyWriting)
		{
			if(e.getClickCount()==2)
			{
				String title, id, day, content = null, section;
				title=strMywriting[tbMyWriting.getSelectedRow()][1];
				id=strMywriting[tbMyWriting.getSelectedRow()][2];
				day=strMywriting[tbMyWriting.getSelectedRow()][3];
				section=strMywriting[tbMyWriting.getSelectedRow()][0];;
				sql="SELECT CONTENT"+
						" FROM "+section+
						" WHERE TITLE='"+title+"' AND ID='"+id+"' AND DAY='"+day+"'";
				System.out.println(sql);
				rs = DB.getResultSet(sql);
				try {
					while(rs.next())
					{
						content=rs.getString(1);
					}
				} catch (SQLException e1) {
					System.out.println("존재하지 않는 게시글입니다.");
					e1.printStackTrace();
				}
				new Read(title, id, day, content,section);
			}
		}
		if(obj==lblReturn)
		{
			mainflag=true;
			mypflag=false;
			pMyPage.setVisible(false);
			pBase.setVisible(true);
			setTitle("알고사자");
		}
	}

	//리드 불러오기
	//타이틀 아이디 데이 컨텐트 섹션 변수에 선택한 컬럼의 정보를 넣고 sql문에 변수를 이용하여 내용을 뽑아온 후
	//해당 변수들을 멤버변수로 리드를 호출함
	//리드는 해당 변수들을 통해 작성된 내용을 틀에 맞춰서 표시함.
	private void createRead(String[][] str, JTable tb, String Section) {
		String title, id, day, content = null, section;
		title=str[tb.getSelectedRow()][0];
		id=str[tb.getSelectedRow()][1];
		day=str[tb.getSelectedRow()][2];
		section=Section;
		sql="SELECT CONTENT"+
				" FROM "+section+
				" WHERE TITLE='"+title+"' AND ID='"+id+"' AND DAY='"+day+"'";
		System.out.println(sql);
		rs = DB.getResultSet(sql);
		try {
			while(rs.next())
			{
				content=rs.getString(1);
			}
		} catch (SQLException e1) {
			System.out.println("존재하지 않는 게시글입니다.");
			e1.printStackTrace();
		}
		new Read(title, id, day, content,section);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==write)
		{
		  new Write("글 작성", 400, 480); //타이틀, 너비, 높이를 매개변수로 받음
		  dispose();
		}
		if(e.getSource()==logout)
		{
			dispose();
			new Login("Login", 800, 600);
		}
		//메인페이지 검색
		//검색버튼이 눌렸을 때 검색란이 비어있다면 플레그 0을줘서 작성된 모든 글들을 보여주고,
		//비어있지않고 검색창에 있는 콤보박스가 제목과 같다면 플레그 1을 줘서 제목으로 검색한 글들을 보여주고
		//비어있지않고 검색창에 있는 콤보박스가 제목과 같지 않다면 플레그 2를 줘서 id로 검색한 값을 보여줌
		if(e.getSource()==search)
		{	
			if(tfFind.getText().isEmpty())
			{
				flag=0;
				dispose();
				temp=tfFind.getText();
				new Main("알고사자", 800,550);
			}else {
				if(cbFind.getItemAt(cbFind.getSelectedIndex())==cbFind.getItemAt(0))
				{
					flag=1;
					dispose();
					temp=tfFind.getText();
					new Main("알고사자", 800,550);
				}else{
					flag=2;
					dispose();
					temp=tfFind.getText();
					new Main("알고사자", 800,550);
				}
			}
		}
		//마이페이지 검색
		if(e.getSource()==Mysearch)
		{
			//메인플래그와 마이피플레그는 스태틱변수로 마이페이지에서 검색을 눌렀을 때 마이페이지가 계속해서 보여지기 위한 변수
			//템프변수는 스태틱변수로 마이페이지에서 검색한 값을 전달해주기위한 변수 
			//마이페이지의 검색란이 비어있다면 메인플래그와 마이피플레그에 각각 펄스와 트루를 보내고, 플레그를 0으로 줘서
			//내가 작성한 글 전부를 보는 것
			//검색란이 비어있지 않으면 메인플래그와 마이피플레그에 펄스와 트루를 보내고, 플래그를 1로 줘서 검색한 글만 보는 것
			
			if(mytfFind.getText().isEmpty())
			{
				mainflag=false;
				mypflag=true;
				flag=0;
				dispose();
				temp=mytfFind.getText();
				new Main("알고사자", 800,550);
			}else {
					mainflag=false;
					mypflag=true;
					flag=1;
					dispose();
					temp=mytfFind.getText();
					new Main("알고사자", 800,550);
			}
		}
		//삭제버튼을 누르면 마이페이지 선택한 컬럼의 값들을 전부 변수에 저장한다음 그 변수를 바탕으로 db에 검색하여 지우고
		//메인플레그와 마이피플레그를 펄스와 트루로 줘서 마이페이지를 계속 볼 수 있게 하는 것
		if(e.getSource()==Delete)
		{
			String section, title, day;
			section=strMywriting[tbMyWriting.getSelectedRow()][0];
			title=strMywriting[tbMyWriting.getSelectedRow()][1];
			day=strMywriting[tbMyWriting.getSelectedRow()][3];
			
			String deleteSql = "DELETE FROM "+section+" WHERE title='"+title+"' and day='"+day+"'";
			DB.executeQuery(deleteSql);
			dispose();
			mainflag=false;
			mypflag=true;
			new Main("알고사자", 800,550);
		}
	}
}
