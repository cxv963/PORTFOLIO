package com.example.minipj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button registerBtn, loginBtn;
    EditText idtext, pwtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idtext=(EditText)findViewById(R.id.idText);
        pwtext=(EditText)findViewById(R.id.pwText);
        registerBtn=(Button)findViewById(R.id.RegisterButton);
        loginBtn=(Button)findViewById(R.id.LoginButton);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id = idtext.getText().toString();
                    String pw = pwtext.getText().toString();

                    DB_Login task = new DB_Login();
                    String result = task.execute(id, pw).get();

                    Log.i("결과", "결과값:" + result);

                    if (result.equals("succes")) {
                        /*
                        //로비 액티비티로 이동
                        Intent loginIntent = new Intent(LoginActivity.this, LobbyActivity.class);
                        LoginActivity.this.startActivity(loginIntent);
                         */
                        Toast.makeText(LoginActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();

                        //아이디를 전달하기 위한 인텐트
                        Intent idIntent = new Intent(LoginActivity.this,LobbyActivity.class);
                        idIntent.putExtra("id",id);
                        startActivity(idIntent);

                    }else if(result.equals("createTeam"))
                    {
                        //팀생성 액티비티로 이동
                        Toast.makeText(LoginActivity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent TeamIntent = new Intent(LoginActivity.this, TeamActivity.class);
                        LoginActivity.this.startActivity(TeamIntent);

                        //아이디를 전달하기 위한 인텐트
                        Intent idIntent = new Intent(LoginActivity.this,TeamActivity.class);
                        idIntent.putExtra("id",id);
                        startActivity(idIntent);
                    } else {
                        Toast.makeText(LoginActivity.this, "아이디나 비밀번호를 확인해 주십시오.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //뒤로가기 버튼 막기
    public void onBackPressed(){}
}
