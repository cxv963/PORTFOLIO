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
		//1. 오라클 드라이버 설치
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");//오라클의 이름을 부를꺼임.
			
			//2. 드라이버 매니저 연결
			conn = DriverManager.getConnection( //로컬호스트, 포트번호, SID
					"jdbc:oracle:thin:@125.243.28.82:1521:XE",
					"inya",
					"1234"); //url, user, password
			
			stmt = conn.createStatement(); //연결된 정보 저장
			System.out.println("DB 연결 성공");
			
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("DB 연결 오류 또는 뭐리 오류 입니다.");
			e.printStackTrace();
		} 
	}
	
	//조회
	public static ResultSet getResultSet(String sql) {
		try {
			Statement stmt = conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
		//삽입 수정, 삭제
	public static void executeQuery(String sql) {
		try {
			stmt.executeQuery(sql);
			count=1;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "빈 항목이 있습니다.");
			count=0;
			e.printStackTrace();
		}
	}
}
