package tcpip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DB {
	public static Connection conn; 
	public static Statement stmt;
	public static int count=0;
	
	public static void init() {
		//1. ����Ŭ ����̹� ��ġ
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");//����Ŭ�� �̸��� �θ�����.
			
			//2. ����̹� �Ŵ��� ����
			conn = DriverManager.getConnection( //����ȣ��Ʈ, ��Ʈ��ȣ, SID
					"jdbc:oracle:thin:@125.243.28.82:1521:XE",
					"inya",
					"1234"); //url, user, password
			
			stmt = conn.createStatement(); //����� ���� ����
			System.out.println("DB ���� ����");
			
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC ����̹� �ε� ����");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("DB ���� ���� �Ǵ� ���� ���� �Դϴ�.");
			e.printStackTrace();
		} 
	}
	
	//��ȸ
	public static ResultSet getResultSet(String sql) {
		try {
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
		//���� ����, ����
	public static void executeQuery(String sql) {
		try {
			stmt.executeQuery(sql);
			count=1;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "�� �׸��� �ֽ��ϴ�.");
			count=0;
			e.printStackTrace();
		}
	}
}
