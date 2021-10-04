package com.example.minipj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertWorkActivity extends AppCompatActivity {
    Button insertBtn, cancelBtn;
    String id,code;
    EditText jobText, contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_work);

        insertBtn = (Button)findViewById(R.id.insertButton);
        cancelBtn = (Button)findViewById(R.id.cancelButton);
        jobText = (EditText)findViewById(R.id.jobText);
        contentText = (EditText)findViewById(R.id.contentText);

        id =getIntent().getStringExtra("id");
        code =getIntent().getStringExtra("code");

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String job = jobText.getText().toString();
                String content = contentText.getText().toString();

                //날짜구하기
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                String getDate = simpleDate.format(mDate);

                DB_Work W_task = new DB_Work();
                try{
                    W_task.execute("12",id,job,content,getDate,code).get();

                    //아이디를 전달하기 위한 인텐트
                    Intent idIntent = new Intent(InsertWorkActivity.this,LobbyActivity.class);
                    idIntent.putExtra("id",id);
                    startActivity(idIntent);

                }catch (Exception e)
                {

                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이디를 전달하기 위한 인텐트
                Intent idIntent = new Intent(InsertWorkActivity.this,LobbyActivity.class);
                idIntent.putExtra("id",id);
                startActivity(idIntent);
            }
        });


    }
    //뒤로가기 버튼 막기
    public void onBackPressed(){}
}
