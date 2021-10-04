package com.example.minipj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinTeamActivity extends AppCompatActivity {
    Button JoinTeamBtn;
    EditText leadertext;
    String idIntent;
    String leader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);

        JoinTeamBtn=(Button)findViewById(R.id.JoinTeamButton);
        leadertext=(EditText)findViewById(R.id.leaderidText);

        idIntent =getIntent().getStringExtra("id");

        JoinTeamBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    leader = leadertext.getText().toString();

                    DB_Team task = new DB_Team();
                    String result = task.execute("4",idIntent,leader).get();

                    Log.i("결과", "결과값:" + result);

                    if(result.equals("succes"))
                    {
                        Toast.makeText(JoinTeamActivity.this, "참가되었습니다.", Toast.LENGTH_SHORT).show();
                        //Intent LobbyIntent = new Intent(JoinTeamActivity.this, LobbyActivity.class);
                        //JoinTeamActivity.this.startActivity(LobbyIntent);

                        //아이디를 전달하기 위한 인텐트
                        Intent idIntent = new Intent(JoinTeamActivity.this,LobbyActivity.class);
                        idIntent.putExtra("id",getIntent().getStringExtra("id"));
                        startActivity(idIntent);
                    }else
                    {
                        Toast.makeText(JoinTeamActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e)
                {

                }
            }
        });

    }

    //뒤로가기 버튼 막기
    public void onBackPressed(){}
}
