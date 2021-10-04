package com.example.minipj;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NoticeFragment extends Fragment {
    private ListView noticelistview;
    private Adapter_notice Adapter;
    private List<nortice> noticeliList;
    String userId;
    String role;
    String code;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_notice, container, false);

            userId=getArguments().getString("id");

        //역할 받아오기
        DB_Role R_task = new DB_Role();
        try {
            role = R_task.execute("6",userId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //코드 받아오기
        DB_Role R_task2 = new DB_Role();
        try {
            code = R_task2.execute("8",userId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //리스트 생성
        try {
            noticelistview = (ListView) rootView.findViewById(R.id.notice_custom);
            DB_Notice N_task = new DB_Notice();

            noticeliList = N_task.execute("5",userId).get();

            Adapter = new Adapter_notice(getContext(), noticeliList);
            noticelistview.setAdapter(Adapter);
            noticelistview.setChoiceMode(noticelistview.CHOICE_MODE_SINGLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //글작성
        ImageButton writeBtn = (ImageButton) rootView.findViewById(R.id.writeButton);
        writeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(role.equals("팀장"))
                {
                    //아이디와 코드를 전달하기 위한 인텐트
                    Intent idIntent = new Intent(getActivity(),InsertNoticeActivity.class);
                    idIntent.putExtra("id",userId);
                    idIntent.putExtra("code",code);
                    startActivity(idIntent);

                }else
                {
                    Toast.makeText(getContext(), "권한이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //글삭제
        ImageButton deleteBtn = (ImageButton) rootView.findViewById(R.id.deleteButton);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //팀장이면 지울 수 있음
                    if(role.equals("팀장"))
                    {
                        int position = noticelistview.getCheckedItemPosition();
                        String pos =Integer.toString(position+1);
                        if (position == -1) {
                            Toast.makeText(getContext(), "게시글이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                            noticeliList.remove(position);
                            noticelistview.clearChoices();
                            Adapter.notifyDataSetChanged();

                            DB_Notice N_task2 = new DB_Notice();
                                N_task2.execute("7",userId,pos).get(); //데이터베이스에서 삭제
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getContext(), pos+"번째 게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }else
                    {
                        Toast.makeText(getContext(), "권한이 없습니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            //리스트 클릭하면 몇 번째 게시글인지 알려줌
            noticelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getContext(), (position+1) + "번째 게시글이 선택되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return rootView;
    }
}
class nortice{
    String title;
    String content;
    String date;

    public nortice(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}