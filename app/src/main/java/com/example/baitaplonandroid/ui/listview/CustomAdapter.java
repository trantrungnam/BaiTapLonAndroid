package com.example.baitaplonandroid.ui.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.baitaplonandroid.R;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater _layoutInflater;
    private String[] _name;
    public CustomAdapter(Context applicationContext, String[] name) {
        this.context = context;
        this._name = name;
        this._layoutInflater = LayoutInflater.from(applicationContext);
    }

    @Override
    public int getCount() {
        return _name.length;
    }

    @Override
    public Object getItem(int i) {
        return _name[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = _layoutInflater.inflate(R.layout.activity_listview, null);
        TextView textView = view.findViewById(R.id.itemListView);
        textView.setText(_name[i]);
        return view;
    }
}
