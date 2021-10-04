package com.example.minipj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class TeamActivity extends AppCompatActivity {
    ImageButton createteamBtn;
    ImageButton jointeamBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        createteamBtn=(ImageButton)findViewById(R.id.CreateTeamButton);
        jointeamBtn=(ImageButton)findViewById(R.id.JoinTeamButton);

        final String idIntent =getIntent().getStringExtra("id");

        createteamBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    DB_Team task = new DB_Team();
                    String result = task.execute("3",idIntent).get();

                    Log.i("결과", "결과값:" + result);
                    if(result.equals("lobby"))
                    {
                        Toast.makeText(TeamActivity.this, "이미 가입된 팀이 있습니다.", Toast.LENGTH_SHORT).show();
                        Intent idIntent = new Intent(TeamActivity.this,LobbyActivity.class);
                        idIntent.putExtra("id",getIntent().getStringExtra("id"));
                        startActivity(idIntent);
                    }else if(result.equals("create"))
                    {
                        Toast.makeText(TeamActivity.this, "팀이 생성되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent idIntent = new Intent(TeamActivity.this,LobbyActivity.class);
                        idIntent.putExtra("id",getIntent().getStringExtra("id"));
                        startActivity(idIntent);
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        jointeamBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    Intent JoinTeamIntent = new Intent(TeamActivity.this, JoinTeamActivity.class);
                    TeamActivity.this.startActivity(JoinTeamIntent);

                    //아이디를 전달하기 위한 인텐트
                    Intent idIntent = new Intent(TeamActivity.this,JoinTeamActivity.class);
                    idIntent.putExtra("id",getIntent().getStringExtra("id"));
                    startActivity(idIntent);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }
}
