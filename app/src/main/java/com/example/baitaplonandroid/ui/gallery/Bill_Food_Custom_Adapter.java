package com.example.baitaplonandroid.ui.gallery;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Bill;
import com.example.baitaplonandroid.ui.Models.Bill_Food;
import com.example.baitaplonandroid.ui.Models.Bill_Food_List_Type;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bill_Food_Custom_Adapter extends BaseAdapter {

    private Context _context;
    private List<Bill_Food> bill_foods;
    private LayoutInflater layoutInflater;
    private Activity _activity;
    private FragmentManager fragment;
    private FragmentTransaction transaction;

    public Bill_Food_Custom_Adapter(Activity activity, Context context, List<Bill_Food> bill_foods) {
        // Required empty public constructor
        this._context = _context;
        this.bill_foods = bill_foods;
        this.layoutInflater = LayoutInflater.from(context);
        this._activity = activity;
    }


    @Override
    public int getCount() {
        return bill_foods.size();
    }

    @Override
    public Object getItem(int i) {
        return bill_foods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.fragment_bill__food__custom__adapter, null);
        fragment = _activity.getFragmentManager();
        transaction = fragment.beginTransaction();



        final Bill_Food bill_food = bill_foods.get(i);
        TextView txtName = view.findViewById(R.id.txtName);
        txtName.setText(bill_food.getName());

        TextView txtQuantity = view.findViewById(R.id.txtQuantity);

        txtQuantity.setText(bill_food.getQuantity() + "");

        TextView txtPrice = view.findViewById(R.id.txtPrice);
        txtPrice.setText(bill_food.getPrice() + "");

//        Button btnDelete = view.findViewById(R.id.btnDelete);

        SharedPreferences sharedPreferences = _activity.getSharedPreferences("restaurant", 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Gson gson = new Gson();
        final List<Bill_Food> bill_foods_instance;

        String valueShared = sharedPreferences.getString("bill_order_list", "");
        if (!valueShared.equals("")) {
            Bill_Food_List_Type bill_food_list_type = gson.fromJson(valueShared, Bill_Food_List_Type.class);
            bill_foods_instance = bill_food_list_type.getFoodList();
        } else {
            bill_foods_instance = new ArrayList<>();
        }

//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for (int i = 0 ; i < bill_foods_instance.size() ; i++) {
//                    if (bill_foods_instance.get(i).getName().equals( bill_food.getName())) {
//                        bill_foods_instance.remove(i);
//                    }
//                }
//                Bill_Food_List_Type bill_food_list_type = new Bill_Food_List_Type();
//                bill_food_list_type.setFoodList(bill_foods_instance);
//                String json = gson.toJson(bill_food_list_type);
//                editor.putString("bill_order_list", json);
//                editor.commit();
//
//            }
//        });
        return view;
    }
}
