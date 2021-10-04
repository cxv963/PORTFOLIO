package com.example.minipj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MemberFragment extends Fragment {
    private ListView memberlistview;
    private Adapter_member Adapter;
    private List<member> memberList;

    String userId;
    String code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_member, container, false);

        try {
        userId=getArguments().getString("id");

        //코드 받아오기
        DB_Role R_task = new DB_Role();
        try {
            code = R_task.execute("8",userId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        memberlistview =(ListView) rootView.findViewById(R.id.member_custom);
        DB_Member M_task = new DB_Member();
        memberList=M_task.execute("13",code).get();

        Adapter = new Adapter_member(getContext(),memberList);
        memberlistview.setAdapter(Adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        memberlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+memberList.get(position).getPhone()));
                startActivity(tt);
            }
        });


        return rootView;
    }
}

class member{
    String id ;
    String email;
    String phone;

    public member(String id, String email, String phone) {
        this.id = id;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}