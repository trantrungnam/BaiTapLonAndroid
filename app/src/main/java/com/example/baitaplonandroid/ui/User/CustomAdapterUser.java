package com.example.baitaplonandroid.ui.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.User;
import com.example.baitaplonandroid.ui.listview.CustomAdapter;

import java.util.List;

public class CustomAdapterUser extends BaseAdapter {

    private Context context;
    private List<User> users;
    private LayoutInflater layoutInflater;

    public CustomAdapterUser(Context _context, List<User> _users) {
        this.context = _context;
        this.users = _users;
        this.layoutInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.user_listview, null);
        TextView txtId = view.findViewById(R.id.txtId);
        txtId.setText(users.get(i).getId() + "");
        TextView txtUserName = view.findViewById(R.id.txtUserName);
        txtUserName.setText(users.get(i).getUsername());
        TextView txtIsAdmin = view.findViewById(R.id.txtIsAdmin);
        txtIsAdmin.setText(users.get(i).getRole().equals("NV") ? "Nhân viên" : "Admin");
        return view;
    }
}
