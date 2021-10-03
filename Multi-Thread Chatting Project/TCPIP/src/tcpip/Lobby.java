package tcpip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;





public class Lobby extends JFrame implements ActionListener, MouseListener {
	public JPanel panBase, panNorth, panSouth, panCenter;
	private JLabel lbuser;
	
	//대기방 테이블
	private JTable tbRoom,tbLogin;
	DefaultTableModel tbFRoom,tbfLogin;
	private String[][] strTable = new String[100][3],
			           strLoginTable = new String [100][1];
	private String[] strTitle = {"제목","방장","IP"},
					 strLoginTitle= {"사용자"};
	private JScrollPane tsTable, tsLogin;
	public static JTextArea taServerChat;
	private JTextField tfchat;
	private JButton btnchat,btnCreate,btnUpdate;
	DefaultTableCellRenderer RednderCenter;
	private String user;
	int cnt=0;
	Socket cSoc;

	 
	public Lobby(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocation(100,100); //프레임의 생성 위치 조절
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLayout(new BorderLayout());
		
		createPanBase();
		add(panBase);
		
		setVisible(true);
	}
	
	
	
	
	private void createPanBase() {
		panBase = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panBase.setLayout(new BorderLayout());
		panBase.setBackground(Color.WHITE);
		

		createPanCenter();
		panBase.add(panCenter,BorderLayout.CENTER);
		
		createPanSouth();
		panBase.add(panSouth,BorderLayout.SOUTH);
	}




	private void createPanSouth() {
		panSouth = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panSouth.setLayout(new BorderLayout());
		panSouth.setBackground(Color.WHITE);
		panSouth.setBorder(new TitledBorder(new LineBorder(Color.BLACK,1)));
		
		
		//채팅창
		JPanel panChat = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panChat.setLayout(new BorderLayout());
		panChat.setBackground(Color.WHITE);
		
		taServerChat= new JTextArea(10,20); //줄, 칸
		taServerChat.setEditable(false);
		JScrollPane sp = new JScrollPane(taServerChat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		tfchat = new JTextField(18);
		tfchat.addActionListener(this);
		
		btnchat = new JButton("입력");
		btnchat.addActionListener(this);
		
		panChat.add(tfchat,BorderLayout.CENTER);
		panChat.add(btnchat,BorderLayout.EAST);
		panChat.add(sp,BorderLayout.NORTH);
		
		panSouth.add(panChat,BorderLayout.CENTER);
		
		//이용자 창
		RednderCenter = new DefaultTableCellRenderer();
		RednderCenter.setHorizontalAlignment(SwingConstants.CENTER);
		

		createLoginTable();

		tbfLogin = new DefaultTableModel(strLoginTable,strLoginTitle) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tbLogin=new JTable(tbfLogin);
		tbLogin.getColumnModel().getColumn(0).setMaxWidth(200);
		tbLogin.getColumnModel().getColumn(0).setCellRenderer(RednderCenter);
		tbLogin.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
		tbLogin.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
		tbLogin.setShowVerticalLines(false);
		tbLogin.setShowHorizontalLines(false);
		
		tsLogin=new JScrollPane(tbLogin);
		tsLogin.setPreferredSize(new Dimension(200,200));
		
		panSouth.add(tsLogin,BorderLayout.EAST);
	}




	private void createPanCenter() {
		panCenter = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panCenter.setLayout(new BorderLayout());
		panCenter.setBackground(Color.WHITE);
		panCenter.setBorder(new TitledBorder(new LineBorder(Color.BLACK,1)));
		
		RednderCenter = new DefaultTableCellRenderer();
		RednderCenter.setHorizontalAlignment(SwingConstants.CENTER);
		

		createRoomTable();
		System.out.println(strTable[0][1]);
		tbFRoom = new DefaultTableModel(strTable,strTitle) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tbRoom=new JTable(tbFRoom);
		tbRoom.getColumnModel().getColumn(0).setMaxWidth(2000);
		tbRoom.getColumnModel().getColumn(1).setMaxWidth(300);
		tbRoom.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbRoom.getColumnModel().getColumn(2).setMaxWidth(350);
		tbRoom.getColumnModel().getColumn(2).setCellRenderer(RednderCenter);
		tbRoom.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
		tbRoom.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
		tbRoom.setShowVerticalLines(false);
		tbRoom.setShowHorizontalLines(false);
		tbRoom.addMouseListener(this);
		
		tsTable=new JScrollPane(tbRoom);
		tsTable.setPreferredSize(new Dimension(200,300));
		
		JPanel panBtn = new JPanel();
		panBtn.setLayout(new GridLayout(1,7));
		panBtn.setBackground(Color.WHITE);
		
		JLabel a = new JLabel();
		panBtn.add(a,BorderLayout.EAST);
		
		JLabel b = new JLabel();
		panBtn.add(b,BorderLayout.EAST);
		
		JLabel c = new JLabel();
		panBtn.add(c,BorderLayout.EAST);
		
		JLabel d = new JLabel();
		panBtn.add(d,BorderLayout.EAST);
		
		JLabel e = new JLabel();
		panBtn.add(e,BorderLayout.EAST);
		
		btnUpdate=new JButton("새로고침");
		btnUpdate.addActionListener(this);
		panBtn.add(btnUpdate,BorderLayout.EAST);
		
		btnCreate = new JButton("방 생성");
		btnCreate.addActionListener(this);
		panBtn.add(btnCreate,BorderLayout.EAST);
		
		panCenter.add(panBtn,BorderLayout.NORTH);
		panCenter.add(tsTable,BorderLayout.CENTER);
	}


	private void createLoginTable() {
		String sql = "SELECT * FROM LOGIN";
		ResultSet rs = DB.getResultSet(sql);
		int i=0;
		try {
			while(rs.next())
			{
				strLoginTable[i][0]=rs.getString(1);
				i=i+1;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void createRoomTable() {
		String sql = "SELECT * FROM ROOM";
		ResultSet rs = DB.getResultSet(sql);
		int i=0;
		try {
			while(rs.next())
			{
				strTable[i][0]=rs.getString(1);
				strTable[i][1]=rs.getString(2);
				strTable[i][2]=rs.getString(4);
				i=i+1;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		DB.init();
		new Lobby("로비", 800,600);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj==btnchat)
		{
			CLIENT(tfchat.getText());
			tfchat.setText("");
		}
		
		if(obj==btnCreate)
		{
			String str =cSoc.getLocalAddress().toString();
			for(int i=str.length()-1;i>=0;i--)
			{
				if(str.charAt(i)=='/')
				{
					str=str.substring(i+1);
					break;
				}
			}
			System.out.println("클라이언트의 ip: "+str);
			new CreateRoom(str,Login.getA());
		}
		
		if(obj==btnUpdate)
		{
			//--------------------------------------인원
			DefaultTableModel model = (DefaultTableModel)tbLogin.getModel();
			model.setNumRows(0);
			
			createLoginTable();

			model = new DefaultTableModel(strLoginTable,strLoginTitle) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tbLogin.setModel(model);
			tbLogin.getColumnModel().getColumn(0).setMaxWidth(200);
			tbLogin.getColumnModel().getColumn(0).setCellRenderer(RednderCenter);
			tbLogin.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
			tbLogin.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
			tbLogin.setShowVerticalLines(false);
			tbLogin.setShowHorizontalLines(false);
			
			//------------------------------------방
			DefaultTableModel model2 = (DefaultTableModel)tbRoom.getModel();
			model2.setNumRows(0);
			
			createRoomTable();

			model2 = new DefaultTableModel(strTable,strTitle) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tbRoom.setModel(model2);
			tbRoom.getColumnModel().getColumn(0).setMaxWidth(2000);
			tbRoom.getColumnModel().getColumn(1).setMaxWidth(300);
			tbRoom.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
			tbRoom.getColumnModel().getColumn(2).setMaxWidth(350);
			tbRoom.getColumnModel().getColumn(2).setCellRenderer(RednderCenter);
			tbRoom.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
			tbRoom.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
			tbRoom.setShowVerticalLines(false);
			tbRoom.setShowHorizontalLines(false);
		}
	}
	
//---------------------------------------------클라이언트쪽----------------------------------------
	class ClientReader extends Thread {
	    Socket clientSocket;
	 
	    ClientReader(Socket clientSocket) {
	        this.clientSocket = clientSocket;
	    }
	 
	    @Override
	    public void run() {
	        try {
	            InputStream inputStream = clientSocket.getInputStream();
	            byte[] byteArray = new byte[256];
	            while(true)
	            {
	                int size = inputStream.read(byteArray);
	                String readMessage = new String(byteArray, 0, size, "UTF-8");
	                System.out.println(" > " + readMessage);
	                taServerChat.append(readMessage+"\n");
	                taServerChat.setCaretPosition(taServerChat.getDocument().getLength());//맨 아래로
	                
	                
	                
	    			DefaultTableModel model = (DefaultTableModel)tbLogin.getModel();
	    			model.setNumRows(0);
	    			
	    			createLoginTable();

	    			model = new DefaultTableModel(strLoginTable,strLoginTitle) {
	    				@Override
	    				public boolean isCellEditable(int row, int column) {
	    					return false;
	    				}
	    			};
	    			tbLogin.setModel(model);
	    			tbLogin.getColumnModel().getColumn(0).setMaxWidth(200);
	    			tbLogin.getColumnModel().getColumn(0).setCellRenderer(RednderCenter);
	    			tbLogin.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
	    			tbLogin.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
	    			tbLogin.setShowVerticalLines(false);
	    			tbLogin.setShowHorizontalLines(false);
	    			
	    			
	    			
	    			
	    			DefaultTableModel model2 = (DefaultTableModel)tbRoom.getModel();
	    			model2.setNumRows(0);
	    			
	    			createRoomTable();

	    			model2 = new DefaultTableModel(strTable,strTitle) {
	    				@Override
	    				public boolean isCellEditable(int row, int column) {
	    					return false;
	    				}
	    			};
	    			tbRoom.setModel(model2);
	    			tbRoom.getColumnModel().getColumn(0).setMaxWidth(2000);
	    			tbRoom.getColumnModel().getColumn(1).setMaxWidth(300);
	    			tbRoom.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
	    			tbRoom.getColumnModel().getColumn(2).setMaxWidth(350);
	    			tbRoom.getColumnModel().getColumn(2).setCellRenderer(RednderCenter);
	    			tbRoom.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
	    			tbRoom.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
	    			tbRoom.setShowVerticalLines(false);
	    			tbRoom.setShowHorizontalLines(false);
	            }
	                
	        } catch (Exception e) {}
	    }
	}//
	 
	public void CLIENT(String sendmsg) {
	        System.out.println("-Client 시작");
	        try {
	            Socket clientSocket = null;
	            String input = sendmsg;
	            
	            clientSocket = new Socket();
	            cSoc=clientSocket;
	            clientSocket.connect(new InetSocketAddress("125.243.28.82", 4040));
	            System.out.print("-접속 성공");
	            
	            
	            
	            if(cnt==0)
	            {
	                ClientReader clientReader = new ClientReader(clientSocket);
	                clientReader.start();
	            }
	            
                String sendMessage =Login.getA()+": "+input;
                byte[] byteArray = sendMessage.getBytes("UTF-8");
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write(byteArray);
	        } catch (Exception e) {
		        JOptionPane.showMessageDialog(null, "서버가 닫혀있습니다.");
		        this.dispose();
	        }
	        cnt++;
	        System.out.println("-Client 종료");
	    }




	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==tbRoom)
		{
			String title=strTable[tbRoom.getSelectedRow()][0];
			String ip=strTable[tbRoom.getSelectedRow()][2];
			System.out.println(ip);
			new UserClient(title,ip);
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
