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
	 private String[] strFind = {"力格","累己磊"}, MystrFind= {"力格"};
	 private String[][] strFashion = new String[100][3],
			 			strFood = new String[100][3],
			 			strDomesticAppliances= new String[100][3],
			 			strEtc=new String[100][3],
			 			strMywriting=new String[100][4];
	 private String[] cT = {"力格","累己磊","朝楼"},MyWritingcT = {"冀记","力格","累己磊","朝楼"};
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
		setLocation(100,100); //橇饭烙狼 积己 困摹 炼例
//		setLocationRelativeTo(this); 惑措利牢 困摹俊 积辫.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//JFrame.加己蔼 < 加己蔼俊 付快胶 啊廉啊搁 锅龋焊烙.
		// 橇肺弊伐 辆丰矫 促 辆丰
		
		//饭捞酒眶
		setLayout(new BorderLayout());
		
		//魄海捞胶绰 皋牢其捞瘤甫 焊咯林扁 困茄 巴捞绊 乔付捞其捞瘤绰 付捞其捞瘤甫 焊咯林扁 困茄巴栏肺 
		//郴何傈券阑 荤侩沁嚼聪促.
		createPBase();
		createPMyPage();
		add(pMyPage);
		add(pBase);
		
		setVisible(true);
	}
	
	private void createPMyPage() {
		pMyPage = new JPanel(); // JPanel篮 扁夯利栏肺 敲肺快扼 官操绰芭烙.
		pMyPage.setLayout(null);
		pMyPage.setBackground(Color.WHITE);
		pMyPage.setBounds(0, 0, 800, 550);
		
		createMyInfo(Login.getA());
		
		lblReturn = new JLabel(imgReturn);
		lblReturn.setBounds(430, 10, 40, 40);
		lblReturn.addMouseListener(this);
		pMyPage.add(lblReturn);
		
		Delete=new JButton("昏力");
		Delete.setBounds(690, 470, 60, 30);
		Delete.addActionListener(this);
		pMyPage.add(Delete);
		
		MycbFind=new JComboBox<String>(MystrFind);
		MycbFind.setBounds(50, 235, 100, 30);
		mytfFind = new JTextField(15);
		mytfFind.setBounds(150, 235, 150, 31);
		
		Mysearch = new JButton("八祸");
		Mysearch.setBounds(300, 235, 60, 30);
		Mysearch.addActionListener(this);
		
		pMyPage.add(Mysearch);
		pMyPage.add(MycbFind);
		pMyPage.add(mytfFind);
		
		pMyPage.setVisible(mypflag);
	}

//-------------------------------------付捞其捞瘤---------------------------------------
	//肺弊牢 等 雀盔 沥焊甫 焊咯林扁 困茄 窃荐肺 sql巩阑 捞侩窍咯 糕滚抛捞喉俊 乐绰 肺弊牢等 酒捞叼 漠烦傈眉甫 函荐俊
	//历厘窍绰 窃荐
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
		//------------------------沥焊 菩澄 积己---------------------------
		JPanel pInfo = new JPanel();
		pInfo.setLayout(new GridLayout(3,2));
		pInfo.setBounds(50, 100, 700, 100);
		
		JLabel lblName = new JLabel("捞抚     :  "+name,JLabel.LEFT);
		lblName.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblName);
		
		JLabel lblPhone = new JLabel("楷遏贸 :  "+phone);
		lblPhone.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblPhone);
		
		JLabel lblId = new JLabel("酒捞叼 :  "+ID);
		lblId.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblId);
	
		JLabel lblPw = new JLabel("厚剐锅龋 :  "+pw);
		lblPw.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblPw);
		
		JLabel lblemail = new JLabel("捞皋老 :  "+email);
		lblemail.setFont(new Font("", Font.BOLD, 15));
		pInfo.add(lblemail);
		
		pMyPage.add(pInfo);
		
		//--------------------郴 霸矫臂 焊扁 菩澄 积己---------------------------
		JPanel pMywriting = new JPanel();
		pMywriting.setLayout(new GridLayout());
		pMywriting.setBounds(50, 270, 700, 200);
		
		
		
		//窃荐甫 烹秦 strMywriting俊 阿 抛捞喉俊 磊脚捞 敬 臂阑 惶酒咳.
		createMyWriting("FASHION",ID);
		createMyWriting("FOOD",ID);
		createMyWriting("DOMESTICAPPLIANCES",ID);
		createMyWriting("ETCC",ID);
		
		RednderCenter = new DefaultTableCellRenderer();
		RednderCenter.setHorizontalAlignment(SwingConstants.CENTER);
		
		//抛捞喉葛胆阑 积己窍绊 蔼阑 函版窍瘤 给窍霸 窍绰 内靛
		myWritingModel = new DefaultTableModel(strMywriting,MyWritingcT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//咯率俊 乐绰 内靛绰 抛捞喉狼 农扁炼例阑 窍扁 困茄 内靛
		tbMyWriting=new JTable(myWritingModel);
		tbMyWriting.getColumnModel().getColumn(0).setMaxWidth(600);
		tbMyWriting.getColumnModel().getColumn(1).setMaxWidth(2000);
		tbMyWriting.getColumnModel().getColumn(2).setMaxWidth(200);
		tbMyWriting.getColumnModel().getColumn(2).setCellRenderer(RednderCenter);
		tbMyWriting.getColumnModel().getColumn(3).setMaxWidth(400);
		tbMyWriting.getTableHeader().setReorderingAllowed(false);//拿烦捞悼阂啊
		tbMyWriting.getTableHeader().setResizingAllowed(false);//拿烦农扁炼沥阂啊
		tbMyWriting.addMouseListener(this);
		JScrollPane tsMyWriting=new JScrollPane(tbMyWriting);
		
		pMywriting.add(tsMyWriting);
		pMyPage.add(pMywriting);
	}
	//敲饭弊 函荐绰 胶怕平 函荐肺 付捞其捞瘤俊 乐绰 八祸鄂捞 厚绢 乐促搁 0捞绊 八祸鄂俊 郴侩捞 乐促搁 1肺
	//八祸咯何甫 烹秦辑 sql巩阑 促福霸 累悼矫难 抛捞喉狼 郴侩阑 钎矫窍绰 窃荐
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

//------------------------------------皋牢其捞瘤-----------------------------------------
	private void createPBase() {
		pBase = new JPanel(); // JPanel篮 扁夯利栏肺 敲肺快扼 官操绰芭烙.
		pBase.setLayout(null);
		pBase.setBackground(Color.WHITE);
		
		crateUser();
		crateFind();
		createTabbedPane();
		
		write = new JButton("臂累己");
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
		UserName.setFont(new Font("讣篮绊雕",Font.BOLD,14));
		pUser.add(UserName);
		
		logout = new JButton("肺弊酒眶"); //滚瓢阑 穿福搁 肺弊牢 其捞瘤肺 啊具窃.
		logout.addActionListener(this);
		pUser.add(logout);
	}
	
	//徘
	private void createTabbedPane() {
		pTab = new JPanel();
		pTab.setLayout(new BorderLayout());
		pTab.setBackground(Color.WHITE);
		pTab.setBounds(10, 100, 750, 370);

		//菩记 抛捞喉 积己
		createTable("FASHION",strFashion);
		tbFSmodel = new DefaultTableModel(strFashion,cT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		//仟靛 抛捞喉 积己
		createTable("FOOD",strFood);
		tbFDmodel = new DefaultTableModel(strFood,cT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		//啊傈力前 抛捞喉 积己
		createTable("DOMESTICAPPLIANCES",strDomesticAppliances);
		tbDAmodel = new DefaultTableModel(strDomesticAppliances,cT) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		//扁鸥 抛捞喉 积己
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
		tbFashion.getTableHeader().setReorderingAllowed(false);//拿烦捞悼阂啊
		tbFashion.getTableHeader().setResizingAllowed(false);//拿烦农扁炼沥阂啊
		tsFashion=new JScrollPane(tbFashion);
		tbFashion.addMouseListener(this);
		

		tbFood=new JTable(tbFDmodel);
		tbFood.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbFood.getColumnModel().getColumn(1).setMaxWidth(300);
		tbFood.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbFood.getColumnModel().getColumn(2).setMaxWidth(350);
		tbFood.getTableHeader().setReorderingAllowed(false);//拿烦捞悼阂啊
		tbFood.getTableHeader().setResizingAllowed(false);//拿烦农扁炼沥阂啊
		tsFood=new JScrollPane(tbFood);
		tbFood.addMouseListener(this);
		
		tbDomesticAppliances=new JTable(tbDAmodel);
		tbDomesticAppliances.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbDomesticAppliances.getColumnModel().getColumn(1).setMaxWidth(300);
		tbDomesticAppliances.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbDomesticAppliances.getColumnModel().getColumn(2).setMaxWidth(350);
		tbDomesticAppliances.getTableHeader().setReorderingAllowed(false);//拿烦捞悼阂啊
		tbDomesticAppliances.getTableHeader().setResizingAllowed(false);//拿烦农扁炼沥阂啊
		tsDomesticAppliances=new JScrollPane(tbDomesticAppliances);
		tbDomesticAppliances.addMouseListener(this);
		
		tbEtc=new JTable(tbETCmodel);
		tbEtc.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbEtc.getColumnModel().getColumn(1).setMaxWidth(300);
		tbEtc.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbEtc.getColumnModel().getColumn(2).setMaxWidth(350);
		tbEtc.getTableHeader().setReorderingAllowed(false);//拿烦捞悼阂啊
		tbEtc.getTableHeader().setResizingAllowed(false);//拿烦农扁炼沥阂啊
		tsEtc=new JScrollPane(tbEtc);
		tbEtc.addMouseListener(this);
		
		tabPane = new JTabbedPane(JTabbedPane.LEFT);
		tabPane.addTab("",new ImageIcon("image/fashion.png"),tsFashion); // 菩记 酒捞能	
		tabPane.addTab("",new ImageIcon("image/food.png"),tsFood); // 澜侥 酒捞能	
		tabPane.addTab("",new ImageIcon("image/home app.png"),tsDomesticAppliances); // 啊傈力前 酒捞能	
		tabPane.addTab("",new ImageIcon("image/temp.png"),tsEtc); // 扁鸥 酒捞能
		tabPane.addMouseListener(this);
		pTab.add(tabPane);
		
	}
	
	//皋牢其捞瘤俊 抛捞喉阑 积己窍扁困茄 内靛肺 敲贰弊啊 0捞搁 葛电 臂阑 抛捞喉俊 持绊, 
	//敲贰弊啊 1捞搁 力格栏肺 八祸茄 搬苞甫 抛捞喉俊 持绢淋
	//敲贰弊啊 2搁 酒捞叼肺 八祸茄 搬苞甫 抛捞喉 持绢淋.
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

	//八祸扁瓷
	private void crateFind() {		
		pFind=new JPanel();
		pFind.setBounds(100, 60, 400, 40);
		pFind.setLayout(null);
		pFind.setBackground(Color.WHITE);
		
		cbFind=new JComboBox<String>(strFind);
		cbFind.setBounds(0, 0, 100, 30);
		tfFind = new JTextField(15);
		tfFind.setBounds(100, 0, 150, 31);
		
		search = new JButton("八祸");
		search.setBounds(250, 0, 60, 30);
		search.addActionListener(this);
		
		pFind.add(search);
		pFind.add(cbFind);
		pFind.add(tfFind);
	}

	public static void main(String[] args) {
		DB.init();
		new Main("舅绊荤磊", 800,550); //鸥捞撇, 呈厚, 臭捞甫 概俺函荐肺 罐澜
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if(obj==lblImgUser)
		{
			pBase.setVisible(false);
			pMyPage.setVisible(true);
			setTitle("付捞其捞瘤");
		}
		if(obj==tbFashion)
		{
			if(e.getClickCount()==2) 
			{
				//菩记抛捞喉俊辑 后 拿烦阑 急琶沁阑锭绰 霸矫臂捞 绝促. 酒聪搁 臂焊扁芒阑 剁框
				if(strFashion[tbFashion.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "霸矫臂捞 绝嚼聪促.");
				else createRead(strFashion,tbFashion,"FASHION");
			}	
		}
		if(obj==tbFood)
		{
			if(e.getClickCount()==2) 
			{
				if(strFood[tbFood.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "霸矫臂捞 绝嚼聪促.");
				else createRead(strFood,tbFood,"FOOD");
			}	
		}
		if(obj==tbDomesticAppliances)
		{
			if(e.getClickCount()==2) 
			{
				if(strDomesticAppliances[tbDomesticAppliances.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "霸矫臂捞 绝嚼聪促.");
				else createRead(strDomesticAppliances,tbDomesticAppliances,"DOMESTICAPPLIANCES");
			}	
		}
		if(obj==tbEtc)
		{
			if(e.getClickCount()==2) 
			{
				if(strEtc[tbEtc.getSelectedRow()][0]==null)JOptionPane.showMessageDialog(null, "霸矫臂捞 绝嚼聪促.");
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
					System.out.println("粮犁窍瘤 臼绰 霸矫臂涝聪促.");
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
			setTitle("舅绊荤磊");
		}
	}

	//府靛 阂矾坷扁
	//鸥捞撇 酒捞叼 单捞 牧刨飘 冀记 函荐俊 急琶茄 拿烦狼 沥焊甫 持绊 sql巩俊 函荐甫 捞侩窍咯 郴侩阑 惶酒柯 饶
	//秦寸 函荐甸阑 糕滚函荐肺 府靛甫 龋免窃
	//府靛绰 秦寸 函荐甸阑 烹秦 累己等 郴侩阑 撇俊 嘎苗辑 钎矫窃.
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
			System.out.println("粮犁窍瘤 臼绰 霸矫臂涝聪促.");
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
		  new Write("臂 累己", 400, 480); //鸥捞撇, 呈厚, 臭捞甫 概俺函荐肺 罐澜
		  dispose();
		}
		if(e.getSource()==logout)
		{
			dispose();
			new Login("Login", 800, 600);
		}
		//皋牢其捞瘤 八祸
		//八祸滚瓢捞 喘啡阑 锭 八祸鄂捞 厚绢乐促搁 敲饭弊 0阑拎辑 累己等 葛电 臂甸阑 焊咯林绊,
		//厚绢乐瘤臼绊 八祸芒俊 乐绰 霓焊冠胶啊 力格苞 鞍促搁 敲饭弊 1阑 拎辑 力格栏肺 八祸茄 臂甸阑 焊咯林绊
		//厚绢乐瘤臼绊 八祸芒俊 乐绰 霓焊冠胶啊 力格苞 鞍瘤 臼促搁 敲饭弊 2甫 拎辑 id肺 八祸茄 蔼阑 焊咯淋
		if(e.getSource()==search)
		{	
			if(tfFind.getText().isEmpty())
			{
				flag=0;
				dispose();
				temp=tfFind.getText();
				new Main("舅绊荤磊", 800,550);
			}else {
				if(cbFind.getItemAt(cbFind.getSelectedIndex())==cbFind.getItemAt(0))
				{
					flag=1;
					dispose();
					temp=tfFind.getText();
					new Main("舅绊荤磊", 800,550);
				}else{
					flag=2;
					dispose();
					temp=tfFind.getText();
					new Main("舅绊荤磊", 800,550);
				}
			}
		}
		//付捞其捞瘤 八祸
		if(e.getSource()==Mysearch)
		{
			//皋牢敲贰弊客 付捞乔敲饭弊绰 胶怕平函荐肺 付捞其捞瘤俊辑 八祸阑 喘范阑 锭 付捞其捞瘤啊 拌加秦辑 焊咯瘤扁 困茄 函荐
			//袍橇函荐绰 胶怕平函荐肺 付捞其捞瘤俊辑 八祸茄 蔼阑 傈崔秦林扁困茄 函荐 
			//付捞其捞瘤狼 八祸鄂捞 厚绢乐促搁 皋牢敲贰弊客 付捞乔敲饭弊俊 阿阿 妻胶客 飘风甫 焊郴绊, 敲饭弊甫 0栏肺 拎辑
			//郴啊 累己茄 臂 傈何甫 焊绰 巴
			//八祸鄂捞 厚绢乐瘤 臼栏搁 皋牢敲贰弊客 付捞乔敲饭弊俊 妻胶客 飘风甫 焊郴绊, 敲贰弊甫 1肺 拎辑 八祸茄 臂父 焊绰 巴
			
			if(mytfFind.getText().isEmpty())
			{
				mainflag=false;
				mypflag=true;
				flag=0;
				dispose();
				temp=mytfFind.getText();
				new Main("舅绊荤磊", 800,550);
			}else {
					mainflag=false;
					mypflag=true;
					flag=1;
					dispose();
					temp=mytfFind.getText();
					new Main("舅绊荤磊", 800,550);
			}
		}
		//昏力滚瓢阑 穿福搁 付捞其捞瘤 急琶茄 拿烦狼 蔼甸阑 傈何 函荐俊 历厘茄促澜 弊 函荐甫 官帕栏肺 db俊 八祸窍咯 瘤快绊
		//皋牢敲饭弊客 付捞乔敲饭弊甫 妻胶客 飘风肺 拎辑 付捞其捞瘤甫 拌加 杭 荐 乐霸 窍绰 巴
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
			new Main("舅绊荤磊", 800,550);
		}
	}
}
