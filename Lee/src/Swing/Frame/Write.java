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
	private String[] Category = {"[쿠팡]","[위메프]","[11번가]","[G마켓]","[옥션]","[기타]"};
	
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
		panBase = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panBase.setLayout(null);
		panBase.setBackground(Color.WHITE);
		
		lblSection = new JLabel("게시판");
		lblSection.setFont(new Font("",Font.BOLD,14));
		lblSection.setBounds(10, -60, 300, 200);
		panBase.add(lblSection);
		
		cbSection = new JComboBox<String>(Section);
		cbSection.setBounds(60, 30, 80, 20);
		panBase.add(cbSection);
		
		cbCategory = new JComboBox<String>(Category);
		cbCategory.setBounds(150, 30, 80, 20);
		panBase.add(cbCategory);
		
		lblTitle = new JLabel("제목");
		lblTitle.setFont(new Font("",Font.BOLD,14));
		lblTitle.setBounds(10, -27, 300, 200);
		panBase.add(lblTitle);
		
		tfTitle = new JTextField();
		tfTitle.setBounds(60, 60, 300, 25);
		panBase.add(tfTitle);
		
		lblContent = new JLabel("내용");
		lblContent.setFont(new Font("",Font.BOLD,14));
		lblContent.setBounds(10, 120, 300, 200);
		panBase.add(lblContent);
		
		taContent = new JTextArea();
		JScrollPane spContent = new JScrollPane(taContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spContent.setBounds(60, 90, 300, 300);
		panBase.add(spContent);
		
		btOk = new JButton("작성");
		btOk.setBounds(100,400, 100, 30);
		btOk.addActionListener(this);
		panBase.add(btOk);
		
		btCancel = new JButton("취소");
		btCancel.setBounds(220,400, 100, 30);
		btCancel.addActionListener(this);
		panBase.add(btCancel);
	}

	public static void main(String[] args) {
		new Write("글 작성", 400, 480); //타이틀, 너비, 높이를 매개변수로 받음
		DB.init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//작성버튼이 눌리면
		if(e.getSource()==btOk)
		{
			//데이트 객체를 만들고 포멧형식을 만든 후, 제목작성 여부와, 내용 작성 여부를 확인하고 작성하지 않았다면
			//메세지를 출력하고 다 작성하였다면 sql을 통해 콤보박스에 있는 내용과 작성한 제목, 내용을 섹션에 맞게 DB에 넣은 후
			//디스포즈하고 메인페이지를 띄움
			Date date = new Date();
			now = dateFormat.format(date);
			if(tfTitle.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "제목을 입력해주세요");
			}else
			{
				if(taContent.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "내용을 입력해주세요");
				}else
				{
					 insertSQL = 
								"INSERT INTO "+cbSection.getItemAt(cbSection.getSelectedIndex())+"(TITLE, ID, DAY, CONTENT) "
							  + "VALUES('"+cbCategory.getItemAt(cbCategory.getSelectedIndex())+" "+tfTitle.getText()+"'"
							  		+ ","+ "'"+Login.getA()+"','"+now+"','"+taContent.getText()+"')";
						 System.out.println(insertSQL);
						 DB.executeQuery(insertSQL);
						 dispose();
						 new Main("알고사자", 800,550);
				}
			}
		}
		//취소버튼이 눌리면 디스포즈후 메인페이지를 띄움
		if(e.getSource()==btCancel)
		{
			dispose();
			new Main("알고사자", 800,550);
		}
	}
}
