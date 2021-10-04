package com.example.minipj;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_member extends BaseAdapter {
    private Context context;
    private List<member> memberlist;

    public Adapter_member(Context context, List<member> memberlist) {
        this.context = context;
        this.memberlist = memberlist;
    }

    @Override
    public int getCount() {
        return memberlist.size();
    }

    @Override
    public Object getItem(int position) {
        return memberlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.list_member, null);
        TextView id = (TextView) v.findViewById(R.id.idText);
        TextView email = (TextView) v.findViewById(R.id.emailText);
        TextView phone = (TextView) v.findViewById(R.id.phoneText);

        id.setText(memberlist.get(position).getId());
        email.setText(memberlist.get(position).getEmail());
        phone.setText(memberlist.get(position).getPhone());

        v.setTag(memberlist.get(position).getId());
        return v;
    }
}
