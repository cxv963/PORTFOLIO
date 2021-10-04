package com.example.minipj;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DB_Member extends AsyncTask<String, Void, List<member>> {
    String sendMsg, receiveMsg;
    private List<member> memberList;

    private String[] getJsonData = { "", "" ,"" };
    @Override
    protected List<member> doInBackground(String... strings) {
        try {
            String str;
            String code="";

            // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
            URL url = new URL("http://125.243.28.82:8080/Android/androidDB.jsp");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            // 전송할 데이터. GET 방식으로 작성
            if(strings[0].equals("13"))
            {
                //조회
                sendMsg = "flag=" + strings[0] + "&code=" +strings[1];
            }




            osw.write(sendMsg);
            osw.flush();

            //jsp와 통신 성공 시 수행
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                String page="";
                // jsp에서 보낸 값을 받는 부분 원래 str
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                    page=buffer.toString();
                }
                receiveMsg = page;
                memberList=new ArrayList<member>();

                JSONObject json = new JSONObject(receiveMsg);
                JSONArray jarr =  json.getJSONArray("dataSend");
                for(int i=0;i<jarr.length();i++){
                    json = jarr.getJSONObject(i);
                    getJsonData[0] = json.getString("id");
                    getJsonData[1] = json.getString("email");
                    getJsonData[2] = json.getString("phone");


                    memberList.add(new member(getJsonData[0],getJsonData[1],getJsonData[2]));
                    Log.i("결과", "결과값:" + jarr);
                }



            } else {
            }
        } catch (MalformedURLException e) {
            Log.i("DBtest", ".....URL URL ERROR.....!");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("DBtest", ".....아이오 아이오 ERROR.....!");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.i("DBtest", ".....제이손 제이손 ERROR.....!");
            e.printStackTrace();
        }

        //jsp로부터 받은 리턴 값
        return memberList;
    }

}
