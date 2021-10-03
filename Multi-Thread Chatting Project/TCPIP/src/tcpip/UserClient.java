package tcpip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tcpip.Lobby.ClientReader;

public class UserClient extends JFrame implements ActionListener {
	
	private JPanel panBase, panSouth;
	private JTextField tfchat;
	private JTextArea tachat;
	private JButton btOk;
	int cnt=0;
	Socket cSoc;
	String sip;


	public UserClient(String title, String ip) {
		setTitle(title);
		setSize(400, 400);
		setLocation(200,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		createPanBase();
		add(panBase);

		setVisible(true);
		sip=ip;
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
		
		btOk = new JButton("작성");
		btOk.addActionListener(this);
		panSouth.add(tfchat,BorderLayout.WEST);
		panSouth.add(btOk,BorderLayout.EAST);
		
		panBase.add(panSouth,BorderLayout.SOUTH);
		
	}

	public static void main(String[] args) {
		new UserClient("클라이언트","125.243.28.82"); //타이틀, 너비, 높이를 매개변수로 받음
		DB.init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj==btOk)
		{
			CLIENT(tfchat.getText(),sip);
			tfchat.setText("");
		}
	}
	
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
	            while (true) {
	                int size = inputStream.read(byteArray);
	                String readMessage = new String(byteArray, 0, size, "UTF-8");
	                System.out.println(" > " + readMessage);
	                tachat.append(readMessage+"\n");
	                tachat.setCaretPosition(tachat.getDocument().getLength());//맨 아래로
	            }
	        } catch (Exception e) {}
	    }
	}//
	 
	public void CLIENT(String sendmsg,String ip) {
        System.out.println("-Client 시작");
        try {
            Socket clientSocket = null;
            String input = sendmsg;
            
            clientSocket = new Socket();
            cSoc=clientSocket;
            clientSocket.connect(new InetSocketAddress(ip, 9190));
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

}
