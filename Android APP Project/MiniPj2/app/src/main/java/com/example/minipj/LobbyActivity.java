package com.example.minipj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LobbyActivity extends AppCompatActivity {
    String idIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Button noticeBtn =  (Button) findViewById(R.id.NoticeButton);
        Button workBtn =  (Button) findViewById(R.id.WorkButton);
        Button memberBtn =  (Button) findViewById(R.id.MemberButton);

        idIntent =getIntent().getStringExtra("id");

        Bundle bundle = new Bundle();
        bundle.putString("id", idIntent);

        NoticeFragment mainFragment = new NoticeFragment();
        mainFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_place, mainFragment);
        transaction.commit();



        noticeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //액티비티 -> 프레그먼트로 값 전달
                Bundle bundle = new Bundle();
                bundle.putString("id", idIntent);

                NoticeFragment mainFragment = new NoticeFragment();
                mainFragment.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_place, mainFragment);
                transaction.commit();
            }
        });

        workBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //액티비티 -> 프레그먼트로 값 전달
                Bundle bundle = new Bundle();
                bundle.putString("id", idIntent);

                WorkFragment mainFragment2 = new WorkFragment();
                mainFragment2.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_place, mainFragment2);
                transaction.commit();
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place,new WorkFragment()).commit();
            }
        });

        memberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //액티비티 -> 프레그먼트로 값 전달
                Bundle bundle = new Bundle();
                bundle.putString("id", idIntent);

                MemberFragment mainFragment3 = new MemberFragment();
                mainFragment3.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_place, mainFragment3);
                transaction.commit();
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place,new MemberFragment()).commit();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.leave:
                Toast.makeText(this, "팀을 나가셨습니다.", Toast.LENGTH_SHORT).show();
                DB_Team task = new DB_Team();
                try {
                    task.execute("14",getIntent().getStringExtra("id")).get();
                    Intent LobbyIntent = new Intent(LobbyActivity.this, LoginActivity.class);
                    LobbyActivity.this.startActivity(LobbyIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.logout:
                Intent LobbyIntent = new Intent(LobbyActivity.this, LoginActivity.class);
                LobbyActivity.this.startActivity(LobbyIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //뒤로가기 버튼 막기
    public void onBackPressed(){}
}


