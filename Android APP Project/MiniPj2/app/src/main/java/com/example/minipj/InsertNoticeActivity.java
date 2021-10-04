package com.example.minipj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class InsertNoticeActivity extends AppCompatActivity {
    Button insertBtn, cancelBtn;
    String id,code;
    EditText titleText, contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_notice);

        insertBtn = (Button)findViewById(R.id.insertButton);
        cancelBtn = (Button)findViewById(R.id.cancelButton);
        titleText = (EditText)findViewById(R.id.titleText);
        contentText = (EditText)findViewById(R.id.contentText);


        id =getIntent().getStringExtra("id");
        code =getIntent().getStringExtra("code");

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();
                String content = contentText.getText().toString();

                //날짜구하기
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                String getDate = simpleDate.format(mDate);

                DB_Notice N_task = new DB_Notice();
                try {
                    N_task.execute("9",title,content,getDate,code).get();

                    //아이디를 전달하기 위한 인텐트
                    Intent idIntent = new Intent(InsertNoticeActivity.this,LobbyActivity.class);
                    idIntent.putExtra("id",id);
                    startActivity(idIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이디를 전달하기 위한 인텐트
                Intent idIntent = new Intent(InsertNoticeActivity.this,LobbyActivity.class);
                idIntent.putExtra("id",id);
                startActivity(idIntent);
            }
        });

    }
    //뒤로가기 버튼 막기
    public void onBackPressed(){}
}
