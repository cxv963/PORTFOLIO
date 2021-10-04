<%@page import="com.db.ConnectDB"%>
<%@ page language="java" contentType="application/json;charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.db.*"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject" %>

<%
ConnectDB connectDB = ConnectDB.getInstance();

request.setCharacterEncoding("UTF-8");
String id = request.getParameter("id");
String leader = request.getParameter("leader");
String pw = request.getParameter("pw");
String email = request.getParameter("email");
String phone = request.getParameter("phone");
String flag = request.getParameter("flag");
String pos = request.getParameter("pos");

String code = request.getParameter("code");
String title = request.getParameter("title");
String content = request.getParameter("content");
String date = request.getParameter("date");
String job = request.getParameter("job");

JSONObject jsonMain = new JSONObject();//객체
JSONArray jArray = new JSONArray();//배열
JSONObject jObject = new JSONObject();//JSON 내용을 담을 객체


if(flag.equals("1"))
{
	//로그인
	String result = connectDB.LoginDB(id, pw);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
	
}else if(flag.equals("2"))
{
	//회원가입
	String result = connectDB.RegisterDB(id, pw, email, phone);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
}else if(flag.equals("3"))
{
	//팀생성
	String result = connectDB.CreateTeamDB(id);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
}else if(flag.equals("4"))
{
	//팀참가
	String result = connectDB.JoinTeamDB(id, leader);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
}else if(flag.equals("5"))
{
	//공지사항
	List noticelist = connectDB.NoticeDB(id);
	if(noticelist!=null)
	{
		for(int i=0;i<noticelist.size();i++)
		{
			NoticeVO vo= (NoticeVO)noticelist.get(i);
			title = vo.getTitle();
			content = vo.getContent();
			date = vo.getDate();
			
			jObject = new JSONObject();
			jObject.put("date",date);
			jObject.put("content",content);
			jObject.put("title",title);	
			
			jArray.add(i, jObject);
		}
		jsonMain.put("dataSend",jArray);
		out.println(jsonMain);
	}
}else if(flag.equals("6"))
{
	//역할
	String result = connectDB.RoleDB(id);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
}else if(flag.equals("7"))
{
	//공지사항 삭제
	String result = connectDB.DeleteNoticeDB(id, pos);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
}else if(flag.equals("8"))
{
	//코드
	String result = connectDB.CodeDB(id);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
}else if(flag.equals("9"))
{
	//공지사항 삽입
	String result = connectDB.InsertNoticeDB(code, title, content, date);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
}else if(flag.equals("10"))
{
	//작업내역 조회
	List worklist = connectDB.WorkDB(id);
	if(worklist!=null)
	{
		for(int i=0;i<worklist.size();i++)
		{
			WorkVO vo= (WorkVO)worklist.get(i);
			id = vo.getId();
			job = vo.getJob();
			content = vo.getContent();
			date = vo.getDate();
			
			jObject = new JSONObject();
			jObject.put("date",date);
			jObject.put("content",content);
			jObject.put("job",job);
			jObject.put("id",id);	
			
			jArray.add(i, jObject);
		}
		jsonMain.put("dataSend",jArray);
		out.println(jsonMain);
	}

}else if(flag.equals("11"))
{
	//작업내역 삭제
	String result = connectDB.DeleteWorkDB(id, pos);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
}else if(flag.equals("12"))
{
	//작업내역 삽입
	String result = connectDB.InsertWorkDB(code, id, job, content, date);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
}else if(flag.equals("13"))
{
	//멤버 조회
	List memberlist = connectDB.MemberDB(code);
	if(memberlist!=null)
	{
		for(int i=0;i<memberlist.size();i++)
		{
			MemberVO vo= (MemberVO)memberlist.get(i);
			id = vo.getId();
			email=vo.getEmail();
			phone=vo.getPhone();
			
			jObject = new JSONObject();
			
			jObject.put("phone",phone);
			jObject.put("email",email);
			jObject.put("id",id);	
			
			jArray.add(i, jObject);
		}
		jsonMain.put("dataSend",jArray);
		out.println(jsonMain);
	}
}else if(flag.equals("14"))
{
	//팀나가기
	String result = connectDB.ExitProjectDB(id);
	jObject.put("result",result);
	jArray.add(0, jObject);
	jsonMain.put("dataSend",jArray);
	out.println(jsonMain);
}
%>

