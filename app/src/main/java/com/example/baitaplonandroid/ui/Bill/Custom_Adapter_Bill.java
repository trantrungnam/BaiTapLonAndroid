package com.example.baitaplonandroid.ui.Bill;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Bill;
import com.example.baitaplonandroid.ui.Models.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Custom_Adapter_Bill extends BaseAdapter {


    private Context context;
    private List<Bill> bills;
    private LayoutInflater layoutInflater;

    public Custom_Adapter_Bill(Context _context, List<Bill> bills) {
        this.context = _context;
        this.bills = bills;
        this.layoutInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return bills.size();
    }

    @Override
    public Object getItem(int i) {
        return bills.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.fragment_custom__adapter__bill, null);
        TextView txtId = view.findViewById(R.id.txtId);
        txtId.setText(bills.get(i).getId() + "");
        TextView createDate = view.findViewById(R.id.createDate);
        createDate.setText(bills.get(i).getCreateAt());
        TextView txtTongTien = view.findViewById(R.id.txtTongTien);
        txtTongTien.setText(bills.get(i).getTongTien() + "");
        return view;
    }

}
