package com.example.minipj;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_work extends BaseAdapter {
    private Context context;
    private List<work> worklist;

    public Adapter_work(Context context, List<work> worklist) {
        this.context = context;
        this.worklist = worklist;
    }

    @Override
    public int getCount() {
        return worklist.size();
    }

    @Override
    public Object getItem(int position) {
        return worklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.list_work, null);
        TextView name = (TextView) v.findViewById(R.id.nameText);
        TextView job = (TextView) v.findViewById(R.id.jobText);
        TextView content = (TextView) v.findViewById(R.id.contentText);
        TextView date = (TextView) v.findViewById(R.id.dateText);

        name.setText(worklist.get(position).getName());
        job.setText(worklist.get(position).getJob());
        content.setText(worklist.get(position).getContent());
        date.setText(worklist.get(position).getDate());

        v.setTag(worklist.get(position).getName());
        return v;
    }
}
