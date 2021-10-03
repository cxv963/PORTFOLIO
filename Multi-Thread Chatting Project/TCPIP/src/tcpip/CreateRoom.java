package tcpip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CreateRoom extends JFrame implements ActionListener {

	private JPanel panBase;
	private JLabel lblTitle;
	private JTextField tfTitle;
	private JButton btOk, btCancel;
	String uip, uid;

	public CreateRoom(String ip, String id) {
		setTitle("�����");
		setSize(400, 200);
		setLocation(200,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		createPanBase();
		add(panBase);

		setVisible(true);
		
		uip=ip;
		uid=id;
	}
	
	private void createPanBase() {
		panBase = new JPanel(); // JPanel�� �⺻������ �÷ο�� �ٲٴ°���.
		panBase.setLayout(null);
		panBase.setBackground(Color.WHITE);
		
		lblTitle = new JLabel("����");
		lblTitle.setFont(new Font("",Font.BOLD,14));
		lblTitle.setBounds(10, -50, 300, 200);
		panBase.add(lblTitle);
		
		tfTitle = new JTextField();
		tfTitle.setBounds(60, 38, 300, 25);
		panBase.add(tfTitle);
		
		btOk = new JButton("����");
		btOk.setBounds(80,100, 100, 30);
		btOk.addActionListener(this);
		panBase.add(btOk);
		
		btCancel = new JButton("���");
		btCancel.setBounds(220,100, 100, 30);
		btCancel.addActionListener(this);
		panBase.add(btCancel);
	}

	public static void main(String[] args) {
		new CreateRoom("127.0.0.1","cxv963"); //Ÿ��Ʋ, �ʺ�, ���̸� �Ű������� ����
		DB.init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btOk)
		{
			if(tfTitle.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "������ ����ֽ��ϴ�.");
			}else
			{
				String sql="INSERT INTO ROOM(TITLE,ID,PERSONS,ADDRESS)"
						+ " VALUES('"+tfTitle.getText()+"','"+uid+"','1','"+uip+"')";
				DB.executeQuery(sql);
				dispose();
				new UserServer(tfTitle.getText(),uip).SERVER(uip);
			}
		}
		if(e.getSource()==btCancel)
		{
			this.dispose();
		}
	}

}
