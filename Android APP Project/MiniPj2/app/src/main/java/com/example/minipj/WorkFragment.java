package com.example.minipj;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkFragment extends Fragment {
    private ListView worklistview;
    private Adapter_work Adapter;
    private List<work> workList;

    String userId;
    String code;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_work, container, false);


        //리스트 생성
        try {
        userId=getArguments().getString("id");

            //코드 받아오기
            DB_Role R_task2 = new DB_Role();
            try {
                code = R_task2.execute("8",userId).get();
            } catch (Exception e) {
                e.printStackTrace();
            }


        worklistview = (ListView) rootView.findViewById(R.id.work_custom);
        DB_Work W_task = new DB_Work();

        //데이터베이스 조회한 결과를 넣어줌
        workList= W_task.execute("10",userId).get();

        Adapter = new Adapter_work(getContext(), workList);
        worklistview.setAdapter(Adapter);
        worklistview.setChoiceMode(worklistview.CHOICE_MODE_SINGLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //글작성
        ImageButton writeBtn =(ImageButton) rootView.findViewById(R.id.writeButton);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //아이디와 코드를 전달하기 위한 인텐트
                Intent idIntent = new Intent(getActivity(),InsertWorkActivity.class);
                idIntent.putExtra("id",userId);
                idIntent.putExtra("code",code);
                startActivity(idIntent);

            }
        });

        //글삭제
        ImageButton deleteBtn =(ImageButton) rootView.findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            int position = worklistview.getCheckedItemPosition();
            String pos =Integer.toString(position+1);
            String posid= workList.get(position).getName();

                if (position == -1) {
                    Toast.makeText(getContext(), "게시글이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    if(userId.equals(posid))
                    {
                        try {
                            DB_Work W_task2 = new DB_Work();
                            W_task2.execute("11",userId,pos).get();

                            workList.remove(position);
                            worklistview.clearChoices();
                            Adapter.notifyDataSetChanged();

                            Toast.makeText(getContext(), pos+"번째 게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "권한이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //리스트 클릭하면 몇 번째 게시글인지 알려줌
        worklistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), (position+1) + "번째 게시글이 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}

class work{
    String name;
    String job;
    String content;
    String date;

    public work(String name, String job, String content, String date) {
        this.name = name;
        this.job = job;
        this.content = content;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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