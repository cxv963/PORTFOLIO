package tcpip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UserServer extends JFrame implements ActionListener {
	
	private JPanel panBase, panSouth;
	private JTextField tfchat;
	private JTextArea tachat;
	private JButton btnok;
	String uip;


	public UserServer(String title, String ip) {
		setTitle(title);
		setSize(400, 400);
		setLocation(200,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		createPanBase();
		add(panBase);

		setVisible(true);
		uip=ip;
	}
	
	private void createPanBase() {
		panBase = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panBase.setLayout(new BorderLayout());
		panBase.setBackground(Color.WHITE);
		
		
		tachat = new JTextArea(10,20);
		tachat.setEditable(false);
		JScrollPane spContent = new JScrollPane(tachat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panBase.add(spContent,BorderLayout.CENTER);
		
		
		//---------------------------------------------입력창
		panSouth = new JPanel();
		panSouth.setLayout(new BorderLayout());
		panSouth.setBackground(Color.WHITE);
		
		tfchat = new JTextField(30);
		tfchat.addActionListener(this);
		
		btnok = new JButton("작성");
		btnok.addActionListener(this);
		panSouth.add(tfchat,BorderLayout.WEST);
		panSouth.add(btnok,BorderLayout.EAST);
		
		panBase.add(panSouth,BorderLayout.SOUTH);
		
	}

	public static void main(String[] args) {
		DB.init();
		new UserServer("서버","125.243.28.82").SERVER("125.243.28.82"); //타이틀, 너비, 높이를 매개변수로 받음
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnok)
		{
            writer(tfchat.getText(), uip);
            System.out.println(uip);
            tfchat.setText("");
		}
	}
	
	
	
	
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
	                
	                String sendMessage = new String(byteArray, 0, size, "UTF-8");
	                System.out.println(sendMessage);
	 
	                //
	                for (int i = 0; i < li.size(); i++) {
	                    if (li.get(i).serverSocket != serverSocket) {
	                        OutputStream outputStream = li.get(i).serverSocket.getOutputStream();
	                        outputStream.write(byteArray);
	                    }
	                }
	                tachat.append(sendMessage+"\n");
	                tachat.setCaretPosition(tachat.getDocument().getLength());//맨 아래로
	            }
	        } catch (Exception e) {
	            System.out.println("Client 접속종료");
	            for (int i = 0; i < li.size();   ) {
	                if(serverSocket == li.get(i).serverSocket) {
	                    li.remove(i);
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
	 
	public void SERVER(String ip) {    
	        System.out.println("-SERVER 시작");
	        
	        try {

	            ServerSocket mainServerSocket = null;
	            mainServerSocket = new ServerSocket();
	            mainServerSocket.bind(new InetSocketAddress(ip, 9190));
	            
		        ConnectThread connectThread = new ConnectThread(mainServerSocket);
		        connectThread.start();
	            
	        } catch (Exception e) {e.printStackTrace();}
	    }
	
	public void writer(String msg, String ip) {
		try {
		
        Socket clientSocket= new Socket();
        clientSocket.connect(new InetSocketAddress(ip, 9190));
        
        String sendMessage =Login.getA()+": "+msg;
        System.out.println(sendMessage);
        byte[] byteArray;
			byteArray = sendMessage.getBytes("UTF-8");
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(byteArray);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
