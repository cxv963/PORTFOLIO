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

public class Read extends JFrame implements ActionListener {
	
	private JPanel panBase;
	private JLabel lblSection, lblTitle, lblContent;
	private JLabel section_lbl, title_lbl, id_lbl, day_lbl;
	private JTextField tfTitle;
	private JTextArea taContent;
	
	private JButton btOk;
	public Read(String title, String id, String day, String content, String section) {
		setTitle("글 보기");
		setSize(400, 480);
		setLocation(200,200);
		
		setLayout(new BorderLayout());

		createPanBase(title, id, day, content, section);
		add(panBase);

		setVisible(true);
	}
	
	private void createPanBase(String title, String id, String day, String content, String section) {
		panBase = new JPanel(); // JPanel은 기본적으로 플로우라 바꾸는거임.
		panBase.setLayout(null);
		panBase.setBackground(Color.WHITE);
		
		
		section_lbl = new JLabel(section);
		section_lbl.setBounds(60, 10, 150, 20);
		panBase.add(section_lbl);
		
		lblTitle = new JLabel("제목");
		lblTitle.setFont(new Font("맑은고딕",Font.BOLD,14));
		lblTitle.setBounds(10, -60, 300, 200);
		panBase.add(lblTitle);
		
		title_lbl = new JLabel(title);
		title_lbl.setFont(new Font("맑은고딕",Font.BOLD,14));
		title_lbl.setBounds(60, 25, 300, 25);
		panBase.add(title_lbl);
		
		id_lbl = new JLabel(id);
		id_lbl.setFont(new Font("맑은고딕",Font.BOLD,10));
		id_lbl.setBounds(60, 38, 300, 25);
		panBase.add(id_lbl);
		
		day_lbl = new JLabel(day);
		day_lbl.setFont(new Font("맑은고딕",Font.BOLD,10));
		day_lbl.setBounds(60, 48, 300, 25);
		panBase.add(day_lbl);
		
		lblContent = new JLabel("내용");
		lblContent.setFont(new Font("맑은고딕",Font.BOLD,14));
		lblContent.setBounds(10, 120, 300, 200);
		panBase.add(lblContent);
		
		taContent = new JTextArea(content);
		JScrollPane spContent = new JScrollPane(taContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spContent.setBounds(60, 70, 300, 300);
		taContent.setEditable(false);//수정 못하게 막아버림
		panBase.add(spContent);
		
		btOk = new JButton("확인");
		btOk.setBounds(130,400, 150, 30);
		btOk.addActionListener(this);
		panBase.add(btOk);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btOk)
		{
			dispose();
		}
	}
}
