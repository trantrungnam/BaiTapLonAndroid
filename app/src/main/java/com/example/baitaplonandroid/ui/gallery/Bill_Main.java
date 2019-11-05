package com.example.baitaplonandroid.ui.gallery;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Bill;
import com.example.baitaplonandroid.ui.SQLHelper.BillFoodHelper;
import com.example.baitaplonandroid.ui.SQLHelper.BillHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bill_Main extends Fragment {

    private Button btnAdd;
    private ListView listViewBill;
    private BillHelper billHelper;

    public Bill_Main() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bill__main, container, false);

        billHelper = new BillHelper(getContext());
        List<Bill> bills = billHelper.getAllBill();

        List<String> bill_date_create  = new ArrayList<String>();
        for (Bill item: bills
        ) {
            bill_date_create.add("BILL TẠO LÚC: " + item.getCreateAt());
        }

        //LIST VIEW TONG THANH TIEN
        listViewBill = view.findViewById(R.id.listViewCategories);
        ArrayAdapter arrayAdapter = new ArrayAdapter(null, android.R.layout.simple_list_item_1, bill_date_create);
        listViewBill.setAdapter(arrayAdapter);

        return view;
    }

}
