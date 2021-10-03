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
		setLocation(200,200); //�������� ���� ��ġ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		createpanBase();
		add(panBase);
	
		setVisible(true);
	}
	
	private void createpanBase() {
		panBase = new JPanel(); // JPanel�� �⺻������ �÷ο�� �ٲٴ°���.
		panBase.setLayout(null);
		panBase.setBackground(Color.WHITE);
		
		
		//�������̺� �˻�
		cbUser = new JComboBox<String>(strUserCT);
		cbUser.setBounds(45, 10, 80, 25);
		panBase.add(cbUser);
		
		tfUser = new JTextField();
		tfUser.setBounds(127, 10, 650, 26);
		panBase.add(tfUser);
		
		User_search = new JButton("�˻�");
		User_search.setBounds(780, 10, 65, 25);
		User_search.addActionListener(this);
		panBase.add(User_search);
		
		//�������̺�
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
		
		
		//�������̺� ���� �������� �� ���� ����
		lblID = new JLabel("���̵�");
		lblID.setFont(new Font("", Font.BOLD, 12));
		lblID.setBounds(45, 250, 40, 25);
		panBase.add(lblID);
		
		tfID = new JTextField();
		tfID.setBounds(90, 250, 100, 25);
		panBase.add(tfID);
		
		lblPW = new JLabel("��й�ȣ");
		lblPW.setFont(new Font("", Font.BOLD, 12));
		lblPW.setBounds(200, 250, 50, 25);
		panBase.add(lblPW);
		
		tfPW = new JTextField();
		tfPW.setBounds(255, 250, 100, 25);
		panBase.add(tfPW);
		
		lblNAME = new JLabel("ȸ���̸�");
		lblNAME.setFont(new Font("", Font.BOLD, 12));
		lblNAME.setBounds(360, 250, 50, 25);
		panBase.add(lblNAME);
		
		tfNAME = new JTextField();
		tfNAME.setBounds(415, 250, 100, 25);
		panBase.add(tfNAME);
		
		lblPHONE = new JLabel("��ȭ��ȣ");
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
		
		//����, ���� ��ư
		User_update = new JButton("����");
		User_update.setBounds(585, 280, 100, 30);
		User_update.addActionListener(this);
		panBase.add(User_update);
		
		User_delete = new JButton("����");
		User_delete.setBounds(750, 280, 100, 30);
		User_delete.addActionListener(this);
		panBase.add(User_delete);
		
		
		createpanUserWriteSearch();
		panUserWriteSearch.setBounds(585, 330, 265, 60);
		panBase.add(panUserWriteSearch);
	}

	//�����˻�
	private void createpanUserWriteSearch() {
		panUserWriteSearch = new JPanel(); // JPanel�� �⺻������ �÷ο�� �ٲٴ°���.
		panUserWriteSearch.setLayout(null);
		panUserWriteSearch.setBackground(Color.WHITE);
		
		//ȸ�� �Խñ� �˻�
		cbUserWrite = new JComboBox<String>(strUserWriteSearchCT);
		cbUserWrite.setBounds(0, 0, 80, 25);
		panUserWriteSearch.add(cbUserWrite);
		
		tfUserWrite = new JTextField();
		tfUserWrite.setBounds(80, 0, 130, 26);
		panUserWriteSearch.add(tfUserWrite);
		
		UserWrite_search = new JButton("�˻�");
		UserWrite_search.setBounds(205, 0, 60, 25);
		UserWrite_search.addActionListener(this);
		panUserWriteSearch.add(UserWrite_search);
		
		UserWrite_delete = new JButton("����");
		UserWrite_delete.setBounds(0, 30, 265, 30);
		UserWrite_delete.addActionListener(this);
		panUserWriteSearch.add(UserWrite_delete);
		
		panUserWriteSearch.setVisible(false);
	}
	
	//����str����
	//�÷��� ����ƽ������ �˻����� �Է� ������ �Ǵ���
	//�÷��װ� 0�̸� ��� ������ ǥ���ϰ�, �÷��װ� 1�̸� �˻��� ������ ǥ����.
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
		new Admin("���� ������", 900, 550); //Ÿ��Ʋ, �ʺ�, ���̸� �Ű������� ����
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//���� �˻�
		//�˻���ư�� ������ �� �����̸� �÷��� 0���ְ� ������ �� ���� â�� ����� ��� ȸ���� ǥ��
		//������ �ƴϸ� �÷��� 1�� �ְ� ����ƽ���� ���̺꿡�������� �˻��� ������ �����ϰ�, ���̺��޺������� �̿���
		//�˻��޺��ڽ� ������ ������
		if(e.getSource()==User_search)
		{
			if(tfUser.getText().isEmpty()) {
				flag=0;
				dispose();
				new Admin("���� ������", 900, 550);
			}else {
				flag=1;
				dispose();
				savetfUser=tfUser.getText();
				savecbUser=cbUser.getItemAt(cbUser.getSelectedIndex());
				new Admin("���� ������", 900, 550);
			}
		}
		//������ư�� ������ ��
		//�߾ӿ� ��ġ�� �ؽ�Ʈ �ʵ���� ���� ������ �����ؼ� sql���� �־� ������ �����ϰ� �������� ���ο� â�� ���
		if(e.getSource()==User_update)
		{
			String ID, PW, NAME, PHONE, EMAIL;
			ID=tfID.getText();
			PW=tfPW.getText();
			NAME=tfNAME.getText();
			PHONE=tfPHONE.getText();
			EMAIL=tfEMAIL.getText();
			
			//ID�� �ٲ�� �ȵ�.
			sql="UPDATE MEMBER "+
					"SET PW='"+PW+"', NAME='"+NAME+"', PHONE='"+PHONE+"', EMAIL='"+EMAIL+"'"+
					" WHERE ID='"+ID+"'";
			DB.executeQuery(sql);
			dispose();
			new Admin("���� ������", 900, 550);
		}
		
		//�߾ӿ� ��ġ�� ���̵��ؽ�Ʈ�ʵ带 �̿��� ������ ���̵� ���� �޾� ������ ��,
		//UserSectionDataDelate�� ���� ��� ���ǿ� �ۼ��� �۵��� ����
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
			new Admin("���� ������", 900, 550);
		}
		//ȸ���� �ۼ��� �� �˻�
		//����ƽ������ �������÷��׸� �̿��ؼ� �˻����� ��������� 0�� �༭ ��� ������ ǥ���ϰ� �������� ��â�� ����
		//�˻����� ���� �ִٸ� ������ �÷��׿� 2�� �ְ� �������� �޺��ڽ������ �˻��ѳ����� ����ƽ������ ������ �� ��â�� ���
		if(e.getSource()==UserWrite_search)
		{
			if(tfUserWrite.getText().isEmpty()) {
				wflag=0;
				dispose();
				new Admin("���� ������", 900, 550);
				System.out.println(wflag);
			}else {
				wflag=2;
				dispose();
				savetfUser=tfUserWrite.getText();
				savecbUser=cbUserWrite.getItemAt(cbUserWrite.getSelectedIndex());
				new Admin("���� ������", 900, 550);
				System.out.println(wflag);
			}
		}
		//ȸ���� �ۼ��� �� ����
		//������ �÷��� ����,����,��¥�� �̿��Ͽ� sql���� ���� db���� ã�Ƴ��� �����ϰ� �������� ��â�� ���� �ڵ�
		if(e.getSource()==UserWrite_delete)
		{
			sql="DELETE FROM "+strUserWrite[tbUserWrite.getSelectedRow()][0]+
					" WHERE TITLE='"+strUserWrite[tbUserWrite.getSelectedRow()][1]+"'"+
							"AND DAY='"+strUserWrite[tbUserWrite.getSelectedRow()][3]+"'";
			DB.executeQuery(sql);
			dispose();
			new Admin("���� ������", 900, 550);
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
			
			//����ƽ���� ���̺�ο�� �÷��� ��ġ�� �����ϱ� ���� ������
			//������ ������ �÷��� ���� ������ �÷��� �ٸ��� �������� ��â�� ���� ���õ��÷��� �ο찪�� �����Ѵ�
			//���� ���缱���� �÷��� ������ ������ �÷��� ���� ���ٸ� �߾ӿ� ��ġ�� ���̵� �ؽ�Ʈ�ʵ� ���� 
			//����ƽ���� ���̺���̵� �����ϰ� ���̺� �� �迭�� ����
			//�� �� ���̺� �迭�� �־ ȸ���� �ۼ��� ���� ��Ÿ��
			System.out.println("����� �ο�:"+saveRow+" ���� �ο�:"+tbUser.getSelectedRow());
			if(tbUser.getSelectedRow()!=saveRow)
			{
				dispose();
				new Admin("���� ������", 900, 550);
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
				if(strUserWrite[tbUserWrite.getSelectedRow()][0].isEmpty())JOptionPane.showMessageDialog(null, "�Խñ��� �����ϴ�.");
				else createRead(strUserWrite,tbUserWrite);
			}
		}
	}
	
	//���� �ҷ�����
	//Ÿ��Ʋ ���̵� ���� ����Ʈ ���� ������ ������ �÷��� ������ �ְ� sql���� ������ �̿��Ͽ� ������ �̾ƿ� ��
	//�ش� �������� ��������� ���带 ȣ����
	//����� �ش� �������� ���� �ۼ��� ������ Ʋ�� ���缭 ǥ����.
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
			System.out.println("�������� �ʴ� �Խñ��Դϴ�.");
			e1.printStackTrace();
		}
		new Read(title, id, day, content,section);
	}
	
	private void createUserWrite() {
		//ȸ�� �Խñ� ���̺�
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
				tbUserWrite.getTableHeader().setReorderingAllowed(false);//�÷��̵��Ұ�
				tbUserWrite.getTableHeader().setResizingAllowed(false);//�÷�ũ�������Ұ�
				JScrollPane tsUserWrite=new JScrollPane(tbUserWrite);
				tsUserWrite.setBounds(45, 290, 500, 200);
				tbUserWrite.addMouseListener(this);
				panBase.add(tsUserWrite);
				
				panUserWriteSearch.setVisible(true);
	}

	//�˻����� ��������� ȸ���� �ۼ��� ��� ���� ǥ���ϰ�,
	//�˻����� ���� ������ ȸ���� �ۼ��� �� �� �˻��� ���븸 ǥ���ϰ� �ϴ� �Լ��̴�.
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
