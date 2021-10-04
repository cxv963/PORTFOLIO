package com.db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ConnectDB {
	private static ConnectDB instance = new ConnectDB();
	
	public static ConnectDB getInstance() {
		return instance;
	}
	public ConnectDB() {}
	
	String jdbcUrl = "jdbc:mysql://125.243.28.82/testdb_b";
	String userId = "root";
	String userPw = "1234";

	String returns = "a";
	
	public String LoginDB(String id, String pw)
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs= stmt.executeQuery("SELECT id FROM member WHERE id = '"+id+"'AND PW='"+pw+"'");

			if (rs.next())
			{
				rs= stmt.executeQuery("SELECT id FROM project WHERE id = '"+id+"'");
				if(rs.next())
				{
					returns = "succes"; 
				}else
				{
					returns = "createTeam";
				}
			}else
			{
				returns = "fail"; 
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}

	
	public String RegisterDB(String id, String pw, String email, String phone) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs= stmt.executeQuery("SELECT id FROM member WHERE id = '"+id+"'");

			if (rs.next())
			{
				returns = "fail"; 
			}else{
				if(id==""|pw==""|email==""|phone=="")
				{
					returns = "blank";
				}else
				{
					stmt.executeUpdate("insert into member(id, pw, email, phone) values ('"+id+"','"+pw+"','"+email+"','"+phone+"')");
					returns = "succes";
				}
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}
	
	public String CreateTeamDB(String id) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			String code = numberGen(4, 1);
			Statement stmt = conn.createStatement();
			ResultSet rs= stmt.executeQuery("SELECT id FROM project WHERE code = '"+code+"'");
			if(rs.next())
			{
				while(rs.getString(1)!=code)
				{
					code = numberGen(4, 1);
				}
			}
			
			rs = stmt.executeQuery("SELECT id FROM project WHERE id = '"+id+"'");
			if(rs.next())
			{
				returns="lobby";
			}else {
				stmt.executeUpdate("insert into project(code, id, role) values ('"+code+"','"+id+"','����')");
				returns="create";
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}
	
	public String JoinTeamDB(String id, String leader) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs= stmt.executeQuery("SELECT * FROM project WHERE id = '"+leader+"'");
			System.out.println("�������̵� : "+ leader);
			if(rs.next())
			{
				stmt.executeUpdate("insert into project(code, id, role) values ('"+rs.getString(1)+"','"+id+"','����')");
				returns = "succes";
			}else
			{
				returns = "fail";
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}
	
	public String RoleDB(String id) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs= stmt.executeQuery("SELECT * FROM project WHERE id = '"+id+"'");
			if(rs.next())
			{
				returns = rs.getString(3);
			}else
			{
				returns = "fail";
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}
	
	public String CodeDB(String id) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs= stmt.executeQuery("SELECT * FROM project WHERE id = '"+id+"'");
			if(rs.next())
			{
				returns = rs.getString(1);
			}else
			{
				returns = "fail";
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}
	
	public String ExitProjectDB(String id) {
		int cnt=0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs= stmt.executeQuery("SELECT * FROM project WHERE id = '"+id+"'");
			if(rs.next())
			{
				if(rs.getString(3).equals("����"))
				{
					stmt.executeUpdate("delete from project where code='"+rs.getString(1)+"'");
				}else
				{
					stmt.executeUpdate("delete from project where id='"+id+"'");
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}
	
	public String DeleteNoticeDB(String id, String pos) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM project WHERE id = '"+id+"'");
			if(rs.next())
			{
				String code = rs.getString(1);
				rs=stmt.executeQuery("SELECT * FROM notice WHERE code = '"+code+"' order by row desc");
				while(rs.next())
				{
					stmt.executeUpdate("delete from notice where row='"+pos+"'");
					System.out.println(pos);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}
	
	public String InsertNoticeDB(String code, String title, String content, String date) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			int row=1;
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM notice WHERE code = '"+code+"' order by row desc");
			if(rs.next())
			{
				row=Integer.parseInt(rs.getString(5));
				row=row+1;
				stmt.executeUpdate("insert into notice(title, content, date, code, row) values ('"+title+"','"+content+"','"+date+"','"+code+"','"+row+"')");
				System.out.println(row+"�Խñ�");
			}else
			{
				stmt.executeUpdate("insert into notice(title, content, date, code, row) values ('"+title+"','"+content+"','"+date+"','"+code+"','"+row+"')");
				System.out.println("ù �Խñ�");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}
	
	public String InsertWorkDB(String code, String id, String job, String content, String date) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			int row=1;
			System.out.println("�ڵ�� :" + code);
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM work WHERE code = '"+code+"' order by row desc");
			if(rs.next())
			{
				row=Integer.parseInt(rs.getString(6))+1;
				stmt.executeUpdate("insert into work(id, job, content, date, code, row) values ('"+id+"','"+job+"','"+content+"','"+date+"','"+code+"','"+row+"')");
				System.out.println(row+"�Խñ�");
			}else
			{
				stmt.executeUpdate("insert into work(id, job, content, date, code, row) values ('"+id+"','"+job+"','"+content+"','"+date+"','"+code+"','"+row+"')");
				System.out.println("ù �Խñ�");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}
	
	public List NoticeDB(String id) {
		List NoticeList = new ArrayList();	
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM project WHERE id = '"+id+"'");
			if(rs.next())
			{
				String code = rs.getString(1);
				rs=stmt.executeQuery("SELECT * FROM notice WHERE code = '"+code+"' order by row desc");
				while(rs.next())
				{
					String title = rs.getString(1);
					String content = rs.getString(2);
					String date = rs.getString(3);
					
					System.out.println(title);
					
					NoticeVO vo = new NoticeVO();
					vo.setTitle(title);
					vo.setContent(content);
					vo.setDate(date);
					
					NoticeList.add(vo);
				}
			}
			return NoticeList;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List WorkDB(String id) {
		List WorkList = new ArrayList();	
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM project WHERE id = '"+id+"'");
			if(rs.next())
			{
				String code = rs.getString(1);
				rs=stmt.executeQuery("SELECT * FROM work WHERE code = '"+code+"' order by row desc");
				while(rs.next())
				{
					String wid = rs.getString(1);
					String job = rs.getString(2);
					String content = rs.getString(3);
					String date = rs.getString(4);
					
					System.out.println(job);
					
					WorkVO vo = new WorkVO();
					vo.setId(wid);
					vo.setJob(job);
					vo.setContent(content);
					vo.setDate(date);

					
					WorkList.add(vo);
				}
			}
			return WorkList;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public List MemberDB(String code) {
		List Memberlist = new ArrayList();	
		String[] id = new String[100];
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM project WHERE code = '"+code+"'");
			for(int i=0; rs.next(); i++)
			{
				id[i]=rs.getString(2);
				System.out.println("���̵� :" + id[i]);
			}
			
			for(int i=0;i<id.length;i++)
			{
				rs=stmt.executeQuery("SELECT * FROM member WHERE id = '"+id[i]+"'");
				if(rs.next())
				{
					String wid = rs.getString(1);
					String email = rs.getString(3);
					String phone = rs.getString(4);
					
					System.out.println("���̵� :" + wid);
					
					MemberVO vo = new MemberVO();
					vo.setId(wid);
					vo.setEmail(email);
					vo.setPhone(phone);

					Memberlist.add(vo);
				}
			}
			
			return Memberlist;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public String DeleteWorkDB(String id, String pos) {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/android",
					"root",
					"1234");
			
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM project WHERE id = '"+id+"'");
			if(rs.next())
			{
				String code = rs.getString(1);
				rs=stmt.executeQuery("SELECT * FROM work WHERE code = '"+code+"' order by row desc");
				if(rs.next())
				{
					rs=stmt.executeQuery("select * from work where row='"+pos+"'");
					if(rs.next())
					{
						if(id.equals(rs.getString(1)))
						{
							stmt.executeUpdate("delete from work where row='"+pos+"'");
						}else
						{
							returns="fail";
						}

					}
					System.out.println(pos);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return returns;
	}
	

	
	
	 public static String numberGen(int len, int dupCd ) {
	        Random rand = new Random();
	        String numStr = ""; //������ ����� ����
	        
	        for(int i=0;i<len;i++) {
	            
	            //0~9 ���� ���� ����
	            String ran = Integer.toString(rand.nextInt(10));
	            
	            if(dupCd==1) {
	                //�ߺ� ���� numStr�� append
	                numStr += ran;
	            }else if(dupCd==2) {
	                //�ߺ��� ������� ������ �ߺ��� ���� �ִ��� �˻��Ѵ�
	                if(!numStr.contains(ran)) {
	                    //�ߺ��� ���� ������ numStr�� append
	                    numStr += ran;
	                }else {
	                    //������ ������ �ߺ��Ǹ� ��ƾ�� �ٽ� �����Ѵ�
	                    i-=1;
	                }
	            }
	        }
	        return numStr;
	    }

}
