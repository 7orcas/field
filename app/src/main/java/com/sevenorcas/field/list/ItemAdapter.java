package com.sevenorcas.field.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sevenorcas.field.R;
import com.sevenorcas.field.db.Graph;

import java.text.SimpleDateFormat;
import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Graph> list;
    private SimpleDateFormat dateFormatDefault = new SimpleDateFormat("dd/MMM/yyyy");

    public ItemAdapter(Context c, List<Graph> list) {
        this.list = list;
        mInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.activity_listdetail, null);
        TextView descrTV = (TextView)v.findViewById(R.id.descrTV);
        TextView createdTV = (TextView)v.findViewById(R.id.createdTV);

        Graph graph = list.get(i);

        descrTV.setText(graph.getDescr());
        createdTV.setText(dateFormatDefault.format(graph.getCreated()));

        return v;
    }
}
