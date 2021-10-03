package Swing.Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Admin extends JFrame implements ActionListener, MouseListener {
	
	private JPanel panBase, panUserWriteSearch;
	private String[] strUserCT = {"ID","PW","NAME","PHONE","E-MAIL"},
					 strUserWriteSearchCT= {"TITLE","ID","DAY"},
					 strUserWriteCT= {"SECTION","TITLE","ID","DAY"};
	private String[][] strUser= new String[500][5],
					   strUserWrite=new String[500][4];
	private JTable tbUser, tbUserWrite;
	DefaultTableModel tbuserModel, tbUserWriteModel;
	DefaultTableCellRenderer RednderCenter;
	private JButton User_search, UserWrite_search, User_delete, User_update, UserWrite_delete;
	private JComboBox<String> cbUser, cbUserWrite;
	private String sql;
	private ResultSet rs;
	private JLabel lblID, lblPW, lblNAME, lblPHONE, lblEMAIL;
	private JTextField tfUser, tfUserWrite, tfID, tfPW, tfNAME, tfPHONE, tfEMAIL;
	static int flag=0, saveRow, wflag=0;
	static String savetfUser,savecbUser, saveID, savecbUserWrite;
	private int UserWriteIndex=0;
	
	 
	
	public Admin(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocation(200,200); //프레임의 생성 위치 조절
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		createpanBase();
		add(panBase);
	
		setVisible(true);
	}
	
	private void createpanBase() {
		panBase = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panBase.setLayout(null);
		panBase.setBackground(Color.WHITE);
		
		
		//유저테이블 검색
		cbUser = new JComboBox<String>(strUserCT);
		cbUser.setBounds(45, 10, 80, 25);
		panBase.add(cbUser);
		
		tfUser = new JTextField();
		tfUser.setBounds(127, 10, 650, 26);
		panBase.add(tfUser);
		
		User_search = new JButton("검색");
		User_search.setBounds(780, 10, 65, 25);
		User_search.addActionListener(this);
		panBase.add(User_search);
		
		//유저테이블
		createUser();
		RednderCenter = new DefaultTableCellRenderer();
		RednderCenter.setHorizontalAlignment(SwingConstants.CENTER);
		
		tbuserModel = new DefaultTableModel(strUser,strUserCT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tbUser=new JTable(tbuserModel);
		tbUser.getColumnModel().getColumn(0).setCellRenderer(RednderCenter);
		tbUser.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbUser.getColumnModel().getColumn(2).setCellRenderer(RednderCenter);
		tbUser.getColumnModel().getColumn(3).setCellRenderer(RednderCenter);
		tbUser.getColumnModel().getColumn(4).setCellRenderer(RednderCenter);
		JScrollPane tsUser=new JScrollPane(tbUser);
		tsUser.setBounds(45, 40, 800, 200);
		tbUser.addMouseListener(this);
		panBase.add(tsUser);
		
		
		//유저테이블 정보 가져오기 및 수정 삭제
		lblID = new JLabel("아이디");
		lblID.setFont(new Font("", Font.BOLD, 12));
		lblID.setBounds(45, 250, 40, 25);
		panBase.add(lblID);
		
		tfID = new JTextField();
		tfID.setBounds(90, 250, 100, 25);
		panBase.add(tfID);
		
		lblPW = new JLabel("비밀번호");
		lblPW.setFont(new Font("", Font.BOLD, 12));
		lblPW.setBounds(200, 250, 50, 25);
		panBase.add(lblPW);
		
		tfPW = new JTextField();
		tfPW.setBounds(255, 250, 100, 25);
		panBase.add(tfPW);
		
		lblNAME = new JLabel("회원이름");
		lblNAME.setFont(new Font("", Font.BOLD, 12));
		lblNAME.setBounds(360, 250, 50, 25);
		panBase.add(lblNAME);
		
		tfNAME = new JTextField();
		tfNAME.setBounds(415, 250, 100, 25);
		panBase.add(tfNAME);
		
		lblPHONE = new JLabel("전화번호");
		lblPHONE.setFont(new Font("", Font.BOLD, 12));
		lblPHONE.setBounds(530, 250, 50, 25);
		panBase.add(lblPHONE);
		
		tfPHONE = new JTextField();
		tfPHONE.setBounds(585, 250, 100, 25);
		panBase.add(tfPHONE);
		
		lblEMAIL = new JLabel("E-MAIL");
		lblEMAIL.setFont(new Font("", Font.BOLD, 12));
		lblEMAIL.setBounds(700, 250, 50, 25);
		panBase.add(lblEMAIL);
		
		tfEMAIL = new JTextField();
		tfEMAIL.setBounds(750, 250, 100, 25);
		panBase.add(tfEMAIL);
		
		//수정, 삭제 버튼
		User_update = new JButton("수정");
		User_update.setBounds(585, 280, 100, 30);
		User_update.addActionListener(this);
		panBase.add(User_update);
		
		User_delete = new JButton("삭제");
		User_delete.setBounds(750, 280, 100, 30);
		User_delete.addActionListener(this);
		panBase.add(User_delete);
		
		
		createpanUserWriteSearch();
		panUserWriteSearch.setBounds(585, 330, 265, 60);
		panBase.add(panUserWriteSearch);
	}

	//유저검색
	private void createpanUserWriteSearch() {
		panUserWriteSearch = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panUserWriteSearch.setLayout(null);
		panUserWriteSearch.setBackground(Color.WHITE);
		
		//회원 게시글 검색
		cbUserWrite = new JComboBox<String>(strUserWriteSearchCT);
		cbUserWrite.setBounds(0, 0, 80, 25);
		panUserWriteSearch.add(cbUserWrite);
		
		tfUserWrite = new JTextField();
		tfUserWrite.setBounds(80, 0, 130, 26);
		panUserWriteSearch.add(tfUserWrite);
		
		UserWrite_search = new JButton("검색");
		UserWrite_search.setBounds(205, 0, 60, 25);
		UserWrite_search.addActionListener(this);
		panUserWriteSearch.add(UserWrite_search);
		
		UserWrite_delete = new JButton("삭제");
		UserWrite_delete.setBounds(0, 30, 265, 30);
		UserWrite_delete.addActionListener(this);
		panUserWriteSearch.add(UserWrite_delete);
		
		panUserWriteSearch.setVisible(false);
	}
	
	//유저str생성
	//플레그 스태틱변수로 검색란의 입력 유무를 판단함
	//플레그가 0이면 모든 내용을 표시하고, 플레그가 1이면 검색된 내용을 표시함.
	private void createUser() {
		if(flag==0)sql = "SELECT * FROM MEMBER";
		if(flag==1)sql = "SELECT * FROM MEMBER WHERE "+savecbUser+" LIKE '%"+savetfUser+"%'";
		System.out.println(sql);
		rs = DB.getResultSet(sql);
		int i=0;
		try {
			while(rs.next()) {
				strUser[i][0]=rs.getString(1);
				strUser[i][1]=rs.getString(2);
				strUser[i][2]=rs.getString(3);
				strUser[i][3]=rs.getString(4);
				strUser[i][4]=rs.getString(5);
				i=i+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DB.init();
		new Admin("관리 페이지", 900, 550); //타이틀, 너비, 높이를 매개변수로 받음
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//유저 검색
		//검색버튼이 눌렸을 때 공백이면 플레그 0을주고 디스포즈 후 새로 창을 띄워서 모든 회원을 표시
		//공백이 아니면 플레그 1을 주고 스태틱변수 세이브에프유저에 검색한 내용을 저장하고, 세이브콤보유저를 이용해
		//검색콤보박스 내용을 저장함
		if(e.getSource()==User_search)
		{
			if(tfUser.getText().isEmpty()) {
				flag=0;
				dispose();
				new Admin("관리 페이지", 900, 550);
			}else {
				flag=1;
				dispose();
				savetfUser=tfUser.getText();
				savecbUser=cbUser.getItemAt(cbUser.getSelectedIndex());
				new Admin("관리 페이지", 900, 550);
			}
		}
		//수정버튼이 눌렸을 때
		//중앙에 위치한 텍스트 필드들의 값을 변수에 저장해서 sql문에 넣어 내용을 수정하고 디스포즈후 새로운 창을 띄움
		if(e.getSource()==User_update)
		{
			String ID, PW, NAME, PHONE, EMAIL;
			ID=tfID.getText();
			PW=tfPW.getText();
			NAME=tfNAME.getText();
			PHONE=tfPHONE.getText();
			EMAIL=tfEMAIL.getText();
			
			//ID는 바뀌면 안됨.
			sql="UPDATE MEMBER "+
					"SET PW='"+PW+"', NAME='"+NAME+"', PHONE='"+PHONE+"', EMAIL='"+EMAIL+"'"+
					" WHERE ID='"+ID+"'";
			DB.executeQuery(sql);
			dispose();
			new Admin("관리 페이지", 900, 550);
		}
		
		//중앙에 위치한 아이디텍스트필드를 이용해 선택한 아이디 값을 받아 삭제한 후,
		//UserSectionDataDelate를 통해 모든 섹션에 작성된 글들을 지움
		if(e.getSource()==User_delete)
		{
			String ID;
			ID=tfID.getText();
			sql="DELETE FROM MEMBER WHERE ID='"+ID+"'";
			DB.executeQuery(sql);
			UserSectionDataDelate("FASHION",ID);
			UserSectionDataDelate("FOOD",ID);
			UserSectionDataDelate("DOMESTICAPPLIANCES",ID);
			UserSectionDataDelate("ETCC",ID);
			
			dispose();
			new Admin("관리 페이지", 900, 550);
		}
		//회원이 작성한 글 검색
		//스태틱변수인 더블유플레그를 이용해서 검색란이 비어있으면 0을 줘서 모든 내용을 표시하고 디스포즈후 새창을 띄우고
		//검색란에 값이 있다면 더블유 플레그에 2를 주고 디스포즈후 콤보박스내용과 검색한내용을 스태틱변수에 저장한 후 새창을 띄움
		if(e.getSource()==UserWrite_search)
		{
			if(tfUserWrite.getText().isEmpty()) {
				wflag=0;
				dispose();
				new Admin("관리 페이지", 900, 550);
				System.out.println(wflag);
			}else {
				wflag=2;
				dispose();
				savetfUser=tfUserWrite.getText();
				savecbUser=cbUserWrite.getItemAt(cbUserWrite.getSelectedIndex());
				new Admin("관리 페이지", 900, 550);
				System.out.println(wflag);
			}
		}
		//회원이 작성한 글 삭제
		//선택한 컬럼의 섹션,제목,날짜를 이용하여 sql문을 통해 db에서 찾아내어 삭제하고 디스포즈후 새창을 띄우는 코드
		if(e.getSource()==UserWrite_delete)
		{
			sql="DELETE FROM "+strUserWrite[tbUserWrite.getSelectedRow()][0]+
					" WHERE TITLE='"+strUserWrite[tbUserWrite.getSelectedRow()][1]+"'"+
							"AND DAY='"+strUserWrite[tbUserWrite.getSelectedRow()][3]+"'";
			DB.executeQuery(sql);
			dispose();
			new Admin("관리 페이지", 900, 550);
		}
	}

	private void UserSectionDataDelate(String section, String ID) {
		sql="DELETE FROM "+section+" WHERE ID='"+ID+"'";
		DB.executeQuery(sql);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==tbUser)
		{
			tfID.setText(strUser[tbUser.getSelectedRow()][0]);
			tfPW.setText(strUser[tbUser.getSelectedRow()][1]);
			tfNAME.setText(strUser[tbUser.getSelectedRow()][2]);
			tfPHONE.setText(strUser[tbUser.getSelectedRow()][3]);
			tfEMAIL.setText(strUser[tbUser.getSelectedRow()][4]);
			
			//스태틱변수 세이브로우는 컬럼의 위치를 저장하기 위한 변수로
			//이전에 선택한 컬럼과 현재 선택한 컬럼이 다르면 디스포즈후 새창을 띄우고 선택된컬럼의 로우값을 저장한다
			//만약 현재선택한 컬럼과 이전에 선택한 컬럼의 값이 같다면 중앙에 위치한 아이디 텍스트필드 값을 
			//스태틱변수 세이브아이디에 저장하고 테이블에 들어갈 배열을 만듬
			//그 후 테이블에 배열을 넣어서 회원이 작성한 글을 나타냄
			System.out.println("저장된 로우:"+saveRow+" 현재 로우:"+tbUser.getSelectedRow());
			if(tbUser.getSelectedRow()!=saveRow)
			{
				dispose();
				new Admin("관리 페이지", 900, 550);
				saveRow=tbUser.getSelectedRow();
			}else {
				UserWriteIndex=0;
				saveID=tfID.getText();
				createstrUserWrite("FASHION",saveID);
				createstrUserWrite("FOOD",saveID);
				createstrUserWrite("DOMESTICAPPLIANCES",saveID);
				createstrUserWrite("ETCC",saveID);
				createUserWrite();
				saveRow=tbUser.getSelectedRow();
			}	
		}
		if(e.getSource()==tbUserWrite)
		{
			if(e.getClickCount()==2)
			{
				if(strUserWrite[tbUserWrite.getSelectedRow()][0].isEmpty())JOptionPane.showMessageDialog(null, "게시글이 없습니다.");
				else createRead(strUserWrite,tbUserWrite);
			}
		}
	}
	
	//리드 불러오기
	//타이틀 아이디 데이 컨텐트 섹션 변수에 선택한 컬럼의 정보를 넣고 sql문에 변수를 이용하여 내용을 뽑아온 후
	//해당 변수들을 멤버변수로 리드를 호출함
	//리드는 해당 변수들을 통해 작성된 내용을 틀에 맞춰서 표시함.
	private void createRead(String[][] str, JTable tb) {
		String title, id, day, content = null, section;
		section=str[tb.getSelectedRow()][0];
		title=str[tb.getSelectedRow()][1];
		id=str[tb.getSelectedRow()][2];
		day=str[tb.getSelectedRow()][3];
		
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
	
	private void createUserWrite() {
		//회원 게시글 테이블
				tbUserWriteModel = new DefaultTableModel(strUserWrite,strUserWriteCT) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				
				tbUserWrite=new JTable(tbUserWriteModel);
				tbUserWrite.getColumnModel().getColumn(0).setMaxWidth(300);
				tbUserWrite.getColumnModel().getColumn(1).setMaxWidth(2000);
				tbUserWrite.getColumnModel().getColumn(2).setMaxWidth(50);
				tbUserWrite.getColumnModel().getColumn(2).setCellRenderer(RednderCenter);
				tbUserWrite.getColumnModel().getColumn(3).setMaxWidth(700);
				tbUserWrite.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
				tbUserWrite.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
				JScrollPane tsUserWrite=new JScrollPane(tbUserWrite);
				tsUserWrite.setBounds(45, 290, 500, 200);
				tbUserWrite.addMouseListener(this);
				panBase.add(tsUserWrite);
				
				panUserWriteSearch.setVisible(true);
	}

	//검색란이 비어있으면 회원이 작성한 모든 글을 표시하고,
	//검색란에 값이 있으면 회원이 작성한 글 중 검색한 내용만 표기하게 하는 함수이다.
	private void createstrUserWrite(String section, String id) {
		if(wflag==0)sql ="SELECT * FROM "+section+" WHERE ID='"+id+"'";
		if(wflag==2)sql = "SELECT * FROM "+section+" WHERE "+savecbUser+" LIKE '%"+savetfUser+"%'";
		System.out.println(sql);
		rs = DB.getResultSet(sql);
		try {
			while(rs.next()) {
				strUserWrite[UserWriteIndex][0]=section;
				strUserWrite[UserWriteIndex][1]=rs.getString(1);
				strUserWrite[UserWriteIndex][2]=rs.getString(2);
				strUserWrite[UserWriteIndex][3]=rs.getString(3);
				UserWriteIndex=UserWriteIndex+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
}
