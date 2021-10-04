package com.example.minipj;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_notice extends BaseAdapter {
    private Context context;
    private List<nortice> noticelist;

    public Adapter_notice(Context context, List<nortice> noticelist) {
        this.context = context;
        this.noticelist = noticelist;
    }

    @Override
    public int getCount() {
        return noticelist.size();
    }

    @Override
    public Object getItem(int position) {
        return noticelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.list_notice, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        TextView content = (TextView) v.findViewById(R.id.content);
        TextView date = (TextView) v.findViewById(R.id.date);

        title.setText(noticelist.get(position).getTitle());
        content.setText(noticelist.get(position).getContent());
        date.setText(noticelist.get(position).getDate());

        v.setTag(noticelist.get(position).getTitle());
        return v;
    }
}
