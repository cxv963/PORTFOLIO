package Swing.Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import oracle.sql.DATE;

public class Write extends JFrame implements ActionListener {
	
	private JPanel panBase;
	private JLabel lblSection, lblTitle, lblContent;
	
	private JComboBox<String> cbSection;
	private String[] Section = {"FASHION","FOOD","DOMESTICAPPLIANCES","ETCC"};
	
	private JComboBox<String> cbCategory;
	private String[] Category = {"[����]","[������]","[11����]","[G����]","[����]","[��Ÿ]"};
	
	private JTextField tfTitle;
	private JTextArea taContent;
	
	private JButton btOk, btCancel;
	
	private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String insertSQL, now;

	public Write(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
		setLocation(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		createPanBase();
		add(panBase);

		setVisible(true);
	}
	
	private void createPanBase() {
		panBase = new JPanel(); // JPanel�� �⺻������ �÷ο�� �ٲٴ°���.
		panBase.setLayout(null);
		panBase.setBackground(Color.WHITE);
		
		lblSection = new JLabel("�Խ���");
		lblSection.setFont(new Font("",Font.BOLD,14));
		lblSection.setBounds(10, -60, 300, 200);
		panBase.add(lblSection);
		
		cbSection = new JComboBox<String>(Section);
		cbSection.setBounds(60, 30, 80, 20);
		panBase.add(cbSection);
		
		cbCategory = new JComboBox<String>(Category);
		cbCategory.setBounds(150, 30, 80, 20);
		panBase.add(cbCategory);
		
		lblTitle = new JLabel("����");
		lblTitle.setFont(new Font("",Font.BOLD,14));
		lblTitle.setBounds(10, -27, 300, 200);
		panBase.add(lblTitle);
		
		tfTitle = new JTextField();
		tfTitle.setBounds(60, 60, 300, 25);
		panBase.add(tfTitle);
		
		lblContent = new JLabel("����");
		lblContent.setFont(new Font("",Font.BOLD,14));
		lblContent.setBounds(10, 120, 300, 200);
		panBase.add(lblContent);
		
		taContent = new JTextArea();
		JScrollPane spContent = new JScrollPane(taContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spContent.setBounds(60, 90, 300, 300);
		panBase.add(spContent);
		
		btOk = new JButton("�ۼ�");
		btOk.setBounds(100,400, 100, 30);
		btOk.addActionListener(this);
		panBase.add(btOk);
		
		btCancel = new JButton("���");
		btCancel.setBounds(220,400, 100, 30);
		btCancel.addActionListener(this);
		panBase.add(btCancel);
	}

	public static void main(String[] args) {
		new Write("�� �ۼ�", 400, 480); //Ÿ��Ʋ, �ʺ�, ���̸� �Ű������� ����
		DB.init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//�ۼ���ư�� ������
		if(e.getSource()==btOk)
		{
			//����Ʈ ��ü�� ����� ���������� ���� ��, �����ۼ� ���ο�, ���� �ۼ� ���θ� Ȯ���ϰ� �ۼ����� �ʾҴٸ�
			//�޼����� ����ϰ� �� �ۼ��Ͽ��ٸ� sql�� ���� �޺��ڽ��� �ִ� ����� �ۼ��� ����, ������ ���ǿ� �°� DB�� ���� ��
			//�������ϰ� ������������ ���
			Date date = new Date();
			now = dateFormat.format(date);
			if(tfTitle.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "������ �Է����ּ���");
			}else
			{
				if(taContent.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "������ �Է����ּ���");
				}else
				{
					 insertSQL = 
								"INSERT INTO "+cbSection.getItemAt(cbSection.getSelectedIndex())+"(TITLE, ID, DAY, CONTENT) "
							  + "VALUES('"+cbCategory.getItemAt(cbCategory.getSelectedIndex())+" "+tfTitle.getText()+"'"
							  		+ ","+ "'"+Login.getA()+"','"+now+"','"+taContent.getText()+"')";
						 System.out.println(insertSQL);
						 DB.executeQuery(insertSQL);
						 dispose();
						 new Main("�˰����", 800,550);
				}
			}
		}
		//��ҹ�ư�� ������ �������� ������������ ���
		if(e.getSource()==btCancel)
		{
			dispose();
			new Main("�˰����", 800,550);
		}
	}
}
