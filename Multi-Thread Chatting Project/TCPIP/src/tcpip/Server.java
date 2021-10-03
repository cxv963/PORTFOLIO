package tcpip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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



public class Server extends JFrame implements ActionListener {
	private JPanel panBase, panNorth, panSouth, panCenter;
	private JLabel lbuser;
	
	//대기방 테이블
	private JTable tbMember,tbLogin;
	DefaultTableModel tbfMember,tbfLogin;
	private String[][] strTable = new String[100][4],
			           strLoginTable = new String [100][1];
	private String[] strTitle = {"아아디","이름","전화번호","이메일"},
					 strLoginTitle= {"사용자"};
	private JScrollPane tsTable, tsLogin;
	public static JTextArea taServerChat;
	private JTextField tfchat;
	private JButton btnchat;
	DefaultTableCellRenderer RednderCenter;
	private String user=Login.getA();
	
	public Server(String title, int width, int height) {
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
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panChat.add(sp);
		
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
		

		createMemberTable();
		System.out.println(strTable[0][1]);
		tbfMember = new DefaultTableModel(strTable,strTitle) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tbMember=new JTable(tbfMember);
		tbMember.getColumnModel().getColumn(0).setMaxWidth(400);
		tbMember.getColumnModel().getColumn(0).setCellRenderer(RednderCenter);
		tbMember.getColumnModel().getColumn(1).setMaxWidth(400);
		tbMember.getColumnModel().getColumn(1).setCellRenderer(RednderCenter);
		tbMember.getColumnModel().getColumn(2).setMaxWidth(400);
		tbMember.getColumnModel().getColumn(2).setCellRenderer(RednderCenter);
		tbMember.getColumnModel().getColumn(3).setMaxWidth(400);
		tbMember.getColumnModel().getColumn(3).setCellRenderer(RednderCenter);
		tbMember.getTableHeader().setReorderingAllowed(false);//컬럼이동불가
		tbMember.getTableHeader().setResizingAllowed(false);//컬럼크기조정불가
		tbMember.setShowVerticalLines(false);
		tbMember.setShowHorizontalLines(false);
		
		tsTable=new JScrollPane(tbMember);
		
		panCenter.add(tsTable);
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


	private void createMemberTable() {
		String sql = "SELECT * FROM MEMBER";
		ResultSet rs = DB.getResultSet(sql);
		int i=0;
		try {
			while(rs.next())
			{
				strTable[i][0]=rs.getString(1);
				strTable[i][1]=rs.getString(3);
				strTable[i][2]=rs.getString(4);
				strTable[i][3]=rs.getString(5);
				i=i+1;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		DB.init();
		new Server("Server", 800,400).SERVER();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
	}
	
//---------------------------------------------서버쪽----------------------------------------
	class UserInfo {
	    Socket serverSocket;
	 
	    UserInfo(Socket serverSocket) {
	        this.serverSocket = serverSocket;
	    }
	}
	 
	class UserThread extends Thread {    
	    Socket serverSocket;
	    List<UserInfo> li = new ArrayList<UserInfo>();
	 
	    UserThread(Socket serverSocket, List li) {
	        this.serverSocket = serverSocket;
	        this.li = li;
	    }
	 
	    @Override
	    public void run() {
	        try {
	            while (true) {
	                //
	                InputStream inputStream = serverSocket.getInputStream();
	                byte[] byteArray = new byte[256];
	                int size = inputStream.read(byteArray);
	                
	                if (size == -1) {
	                    break;
	                }
	                
	                
	                tbfLogin = new DefaultTableModel(strLoginTable,strLoginTitle) {
	        			@Override
	        			public boolean isCellEditable(int row, int column) {
	        				return false;
	        			}
	        		};

	                String sendMessage = new String(byteArray, 0, size, "UTF-8");
	                System.out.println(sendMessage);
	 
	                //
	                for (int i = 0; i < li.size(); i++) {
	                    if (li.get(i).serverSocket != serverSocket) {
	                        OutputStream outputStream = li.get(i).serverSocket.getOutputStream();
	                        outputStream.write(byteArray);
	                    }
	                }
	                taServerChat.append(sendMessage+"\n");
	                taServerChat.setCaretPosition(taServerChat.getDocument().getLength());//맨 아래로

		              //----------------------------로그인 테이블 갱신--------------------------------
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
		    			//-------------------------------------------------------------------------
	            }
	        } catch (Exception e) {
	            System.out.println("클라이언트 접속종료");
	            for (int i = 0; i < li.size();   ) {
	                if(serverSocket == li.get(i).serverSocket) {
	                    li.remove(i);
	                    String sql = "DELETE FROM LOGIN WHERE ID='"+tbfLogin.getValueAt(0, i)+"'";
	    				DB.executeQuery(sql);
	    				System.out.println(tbfLogin.getValueAt(0, i));

	                } else {
	                    i++;
	                }
	                
	            }
	        }
	    }
	}//
	 
	 
	class ConnectThread extends Thread {
	    ServerSocket mainServerSocket = null;
	    List<UserInfo> li = new ArrayList<UserInfo>();
	    
	    ConnectThread(ServerSocket mainServerSocket) {
	        this.mainServerSocket = mainServerSocket;
	    }
	 
	    @Override
	    public void run() {
	        try {
	            while (true) {
	                Socket serverSocket = mainServerSocket.accept();
	                System.out.println("-Client 접속");
	 
	                li.add(new UserInfo(serverSocket));
	                UserThread userThread = new UserThread(serverSocket, li);
	                userThread.start();
	                
	            }
	        } catch (Exception e) {}
	    }
	}//
	 
	public void SERVER() { 
	        System.out.println("-SERVER 시작");
	        
	        try {
	            ServerSocket mainServerSocket = null;
	            mainServerSocket = new ServerSocket();
	            mainServerSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 4040));
	            
	            ConnectThread connectThread = new ConnectThread(mainServerSocket);
	            connectThread.start();
	            
	            Scanner input = new Scanner(System.in);
	            int temp = input.nextInt();
	        } catch (Exception e) {}
	        
	        System.out.println("-SERVER 종료");
	}
}
