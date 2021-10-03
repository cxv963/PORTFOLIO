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
	 private String[] strFind = {"����","�ۼ���"}, MystrFind= {"����"};
	 private String[][] strFashion = new String[100][3],
			 			strFood = new String[100][3],
			 			strDomesticAppliances= new String[100][3],
			 			strEtc=new String[100][3],
			 			strMywriting=new String[100][4];
	 private String[] cT = {"����","�ۼ���","��¥"},MyWritingcT = {"����","����","�ۼ���","��¥"};
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
		setLocation(100,100); //�������� ���� ��ġ ����
//		setLocationRelativeTo(this); ������� ��ġ�� ����.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//JFrame.�Ӽ��� < �Ӽ����� ���콺 �������� ��ȣ����.
		// ���α׷� ����� �� ����
		
		//���̾ƿ�
		setLayout(new BorderLayout());
		
		//�Ǻ��̽��� ������������ �����ֱ� ���� ���̰� �Ǹ����������� ������������ �����ֱ� ���Ѱ����� 
		//������ȯ�� ����߽��ϴ�.
		createPBase();
		createPMyPage();
		add(pMyPage);
		add(pBase);
		
		setVisible(true);
	}
	
	private void createPMyPage() {
		pMyPage = new JPanel(); // JPanel�� �⺻������ �÷ο�� �ٲٴ°���.
		pMyPage.setLayout(null);
		pMyPage.setBackground(Color.WHITE);
		pMyPage.setBounds(0, 0, 800, 550);
		
		createMyInfo(Login.getA());
		
		lblReturn = new JLabel(imgReturn);
		lblReturn.setBounds(430, 10, 40, 40);
		lblReturn.addMouseListener(this);
		pMyPage.add(lblReturn);
		
		Delete=new JButton("����");
		Delete.setBounds(690, 470, 60, 30);
		Delete.addActionListener(this);
		pMyPage.add(Delete);
		
		MycbFind=new JComboBox<String>(MystrFind);
		MycbFind.setBounds(50, 235, 100, 30);
		mytfFind = new JTextField(15);
		mytfFind.setBounds(150, 235, 150, 31);
		
		Mysearch = new JButton("�˻�");
		Mysearch.setBounds(300, 235, 60, 30);
		Mysearch.addActionListener(this);
		
		pMyPage.add(Mysearch);
		pMyPage.add(MycbFind);
		pMyPage.add(mytfFind);
		
		pMyPage.setVisible(mypflag);
	}

//-------------------------------------����������---------------------------------------
	//�α��� �� ȸ�� ������ �����ֱ� ���� �Լ��� sql���� �̿��Ͽ� ������̺� �ִ� �α��ε� ���̵� Į����ü�� ������
	//�����ϴ� �Լ�
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
		//------------------------���� �г� ����---------------------------
		JPanel pInfo = new JPanel();
		pInfo.setLayout(new GridLayout(3,2));
		pInfo.setBounds(50, 100, 700, 100);
		
		JLabel lblName = new JLabel("�̸�     :  "+name,JLabel.LEFT);
		lblName.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblName);
		
		JLabel lblPhone = new JLabel("����ó :  "+phone);
		lblPhone.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblPhone);
		
		JLabel lblId = new JLabel("���̵� :  "+ID);
		lblId.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblId);
	
		JLabel lblPw = new JLabel("��й�ȣ :  "+pw);
		lblPw.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblPw);
		
		JLabel lblemail = new JLabel("�̸��� :  "+email);
		lblemail.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblemail);
		
		pMyPage.add(pInfo);
		
		//--------------------�� �Խñ� ���� �г� ����---------------------------
		JPanel pMywriting = new JPanel();
		pMywriting.setLayout(new GridLayout());
		pMywriting.setBounds(50, 270, 700, 200);
		
		
		
		//�Լ��� ���� strMywriting�� �� ���̺� �ڽ��� �� ���� �̾ƿ�.
		createMyWriting("FASHION",ID);
		createMyWriting("FOOD",ID);
		createMyWriting("DOMESTICAPPLIANCES",ID);
		createMyWriting("ETCC",ID);
		
		RednderCenter = new DefaultTableCellRenderer();
		RednderCenter.setHorizontalAlignment(SwingConstants.CENTER);
		
		//���̺���� �����ϰ� ���� �������� ���ϰ� �ϴ� �ڵ�
		myWritingModel = new DefaultTableModel(strMywriting,MyWritingcT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//���ʿ� �ִ� �ڵ�� ���̺��� ũ�������� �ϱ� ���� �ڵ�
		tbMyWriting=new JTable(myWritingModel);
		tbMyWriting.getColumnModel().getColumn(0).setMaxWidth(600);
		tbMyWriting.getColumnModel().getColumn(1).setMaxWidth(2000);
		tbMyWriting.getColumnModel().getColumn(2).setMaxWidth(200);
		tbMyWriting.getColumnModel().getColumn(2).setCellRenderer(RednderCenter);
		tbMyWriting.getColumnModel().getColumn(3).setMaxWidth(400);
		tbMyWriting.getTableHeader().setReorderingAllowed(false);//�÷��̵��Ұ�
		tbMyWriting.getTableHeader().setResizingAllowed(false);//�÷�ũ�������Ұ�
		tbMyWriting.addMouseListener(this);
		JScrollPane tsMyWriting=new JScrollPane(tbMyWriting);
		
		pMywriting.add(tsMyWriting);
		pMyPage.add(pMywriting);
	}
	//�÷��� ������ ����ƽ ������ ������������ �ִ� �˻����� ��� �ִٸ� 0�̰� �˻����� ������ �ִٸ� 1��
	//�˻����θ� ���ؼ� sql���� �ٸ��� �۵����� ���̺��� ������ ǥ���ϴ� �Լ�
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

//------------------------------------����������-----------------------------------------
	private void createPBase() {
		pBase = new JPanel(); // JPanel�� �⺻������ �÷ο�� �ٲٴ°���.
		pBase.setLayout(null);
		pBase.setBackground(Color.WHITE);
		
		crateUser();
		crateFind();
		createTabbedPane();
		
		write = new JButton("���ۼ�");
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
		UserName.setFont(new Font("�������",Font.BOLD,14));
		pUser.add(UserName);
		
		logout = new JButton("�α׾ƿ�"); //��ư�� ������ �α��� �������� ������.
		logout.addActionListener(this);
		pUser.add(logout);
	}
	
	//��
	private void createTabbedPane() {
		pTab = new JPanel();
		pTab.setLayout(new BorderLayout());
		pTab.setBackground(Color.WHITE);
		pTab.setBounds(10, 100, 750, 370);

		//�м� ���̺� ����
		createTable("FASHION",strFashion);
		tbFSmodel = new DefaultTableModel(strFashion,cT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		//Ǫ�� ���̺� ����
		createTable("FOOD",strFood);
		tbFDmodel = new DefaultTableModel(strFood,cT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		//������ǰ ���̺� ����
		createTable("DOMESTICAPPLIANCES",strDomesticAppliances);
		tbDAmodel = new DefaultTableModel(strDomesticAppliances,cT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		//��Ÿ ���̺� ����
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
		tbFashion.getTableHeader().setReorderingAllowed(false);//�÷��̵��Ұ�
		tbFashion.getTableHeader().setResizingAllowed(false);//�÷�ũ�������Ұ�
		tsFashion=new JScrollPane(tbFashion);
		tbFashion.addMouseListener(this);
		

		tbFood=new JTable(tbFDmodel);
		tbFood.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbFood.getColumnModel().getColumn(1).setMaxWidth(300);
		tbFood.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbFood.getColumnModel().getColumn(2).setMaxWidth(350);
		tbFood.getTableHeader().setReorderingAllowed(false);//�÷��̵��Ұ�
		tbFood.getTableHeader().setResizingAllowed(false);//�÷�ũ�������Ұ�
		tsFood=new JScrollPane(tbFood);
		tbFood.addMouseListener(this);
		
		tbDomesticAppliances=new JTable(tbDAmodel);
		tbDomesticAppliances.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbDomesticAppliances.getColumnModel().getColumn(1).setMaxWidth(300);
		tbDomesticAppliances.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbDomesticAppliances.getColumnModel().getColumn(2).setMaxWidth(350);
		tbDomesticAppliances.getTableHeader().setReorderingAllowed(false);//�÷��̵��Ұ�
		tbDomesticAppliances.getTableHeader().setResizingAllowed(false);//�÷�ũ�������Ұ�
		tsDomesticAppliances=new JScrollPane(tbDomesticAppliances);
		tbDomesticAppliances.addMouseListener(this);
		
		tbEtc=new JTable(tbETCmodel);
		tbEtc.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbEtc.getColumnModel().getColumn(1).setMaxWidth(300);
		tbEtc.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbEtc.getColumnModel().getColumn(2).setMaxWidth(350);
		tbEtc.getTableHeader().setReorderingAllowed(false);//�÷��̵��Ұ�
		tbEtc.getTableHeader().setResizingAllowed(false);//�÷�ũ�������Ұ�
		tsEtc=new JScrollPane(tbEtc);
		tbEtc.addMouseListener(this);
		
		tabPane = new JTabbedPane(JTabbedPane.LEFT);
		tabPane.addTab("",new ImageIcon("image/fashion.png"),tsFashion); // �м� ������	
		tabPane.addTab("",new ImageIcon("image/food.png"),tsFood); // ���� ������	
		tabPane.addTab("",new ImageIcon("image/home app.png"),tsDomesticAppliances); // ������ǰ ������	
		tabPane.addTab("",new ImageIcon("image/temp.png"),tsEtc); // ��Ÿ ������
		tabPane.addMouseListener(this);
		pTab.add(tabPane);
		
	}
	
	//������������ ���̺��� �����ϱ����� �ڵ�� �÷��װ� 0�̸� ��� ���� ���̺� �ְ�, 
	//�÷��װ� 1�̸� �������� �˻��� ����� ���̺� �־���
	//�÷��װ� 2�� ���̵�� �˻��� ����� ���̺� �־���.
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

	//�˻����
	private void crateFind() {		
		pFind=new JPanel();
		pFind.setBounds(100, 60, 400, 40);
		pFind.setLayout(null);
		pFind.setBackground(Color.WHITE);
		
		cbFind=new JComboBox<String>(strFind);
		cbFind.setBounds(0, 0, 100, 30);
		tfFind = new JTextField(15);
		tfFind.setBounds(100, 0, 150, 31);
		
		search = new JButton("�˻�");
		search.setBounds(250, 0, 60, 30);
		search.addActionListener(this);
		
		pFind.add(search);
		pFind.add(cbFind);
		pFind.add(tfFind);
	}

	public static void main(String[] args) {
		DB.init();
		new Main("�˰����", 800,550); //Ÿ��Ʋ, �ʺ�, ���̸� �Ű������� ����
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if(obj==lblImgUser)
		{
			pBase.setVisible(false);
			pMyPage.setVisible(true);
			setTitle("����������");
		}
		if(obj==tbFashion)
		{
			if(e.getClickCount()==2) 
			{
				//�м����̺��� �� �÷��� ������������ �Խñ��� ����. �ƴϸ� �ۺ���â�� ���
				if(strFashion[tbFashion.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "�Խñ��� �����ϴ�.");
				else createRead(strFashion,tbFashion,"FASHION");
			}	
		}
		if(obj==tbFood)
		{
			if(e.getClickCount()==2) 
			{
				if(strFood[tbFood.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "�Խñ��� �����ϴ�.");
				else createRead(strFood,tbFood,"FOOD");
			}	
		}
		if(obj==tbDomesticAppliances)
		{
			if(e.getClickCount()==2) 
			{
				if(strDomesticAppliances[tbDomesticAppliances.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "�Խñ��� �����ϴ�.");
				else createRead(strDomesticAppliances,tbDomesticAppliances,"DOMESTICAPPLIANCES");
			}	
		}
		if(obj==tbEtc)
		{
			if(e.getClickCount()==2) 
			{
				if(strEtc[tbEtc.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "�Խñ��� �����ϴ�.");
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
					System.out.println("�������� �ʴ� �Խñ��Դϴ�.");
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
			setTitle("�˰����");
		}
	}

	//���� �ҷ�����
	//Ÿ��Ʋ ���̵� ���� ����Ʈ ���� ������ ������ �÷��� ������ �ְ� sql���� ������ �̿��Ͽ� ������ �̾ƿ� ��
	//�ش� �������� ��������� ���带 ȣ����
	//����� �ش� �������� ���� �ۼ��� ������ Ʋ�� ���缭 ǥ����.
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
			System.out.println("�������� �ʴ� �Խñ��Դϴ�.");
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
		  new Write("�� �ۼ�", 400, 480); //Ÿ��Ʋ, �ʺ�, ���̸� �Ű������� ����
		  dispose();
		}
		if(e.getSource()==logout)
		{
			dispose();
			new Login("Login", 800, 600);
		}
		//���������� �˻�
		//�˻���ư�� ������ �� �˻����� ����ִٸ� �÷��� 0���༭ �ۼ��� ��� �۵��� �����ְ�,
		//��������ʰ� �˻�â�� �ִ� �޺��ڽ��� ����� ���ٸ� �÷��� 1�� �༭ �������� �˻��� �۵��� �����ְ�
		//��������ʰ� �˻�â�� �ִ� �޺��ڽ��� ����� ���� �ʴٸ� �÷��� 2�� �༭ id�� �˻��� ���� ������
		if(e.getSource()==search)
		{	
			if(tfFind.getText().isEmpty())
			{
				flag=0;
				dispose();
				temp=tfFind.getText();
				new Main("�˰����", 800,550);
			}else {
				if(cbFind.getItemAt(cbFind.getSelectedIndex())==cbFind.getItemAt(0))
				{
					flag=1;
					dispose();
					temp=tfFind.getText();
					new Main("�˰����", 800,550);
				}else{
					flag=2;
					dispose();
					temp=tfFind.getText();
					new Main("�˰����", 800,550);
				}
			}
		}
		//���������� �˻�
		if(e.getSource()==Mysearch)
		{
			//�����÷��׿� �������÷��״� ����ƽ������ �������������� �˻��� ������ �� ������������ ����ؼ� �������� ���� ����
			//���������� ����ƽ������ �������������� �˻��� ���� �������ֱ����� ���� 
			//������������ �˻����� ����ִٸ� �����÷��׿� �������÷��׿� ���� �޽��� Ʈ�縦 ������, �÷��׸� 0���� �༭
			//���� �ۼ��� �� ���θ� ���� ��
			//�˻����� ������� ������ �����÷��׿� �������÷��׿� �޽��� Ʈ�縦 ������, �÷��׸� 1�� �༭ �˻��� �۸� ���� ��
			
			if(mytfFind.getText().isEmpty())
			{
				mainflag=false;
				mypflag=true;
				flag=0;
				dispose();
				temp=mytfFind.getText();
				new Main("�˰����", 800,550);
			}else {
					mainflag=false;
					mypflag=true;
					flag=1;
					dispose();
					temp=mytfFind.getText();
					new Main("�˰����", 800,550);
			}
		}
		//������ư�� ������ ���������� ������ �÷��� ������ ���� ������ �����Ѵ��� �� ������ �������� db�� �˻��Ͽ� �����
		//�����÷��׿� �������÷��׸� �޽��� Ʈ��� �༭ ������������ ��� �� �� �ְ� �ϴ� ��
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
			new Main("�˰����", 800,550);
		}
	}
}
