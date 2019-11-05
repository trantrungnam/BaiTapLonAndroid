package com.example.baitaplonandroid.ui.Food;


import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Bill_Food;
import com.example.baitaplonandroid.ui.Models.Bill_Food_List_Type;
import com.example.baitaplonandroid.ui.gallery.Bill_Home_Fragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Custom_Dialog extends DialogFragment {

    private EditText edtQuantity;
    public View c;

    public Custom_Dialog(View activity) {
        this.c = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_custom__dialog, container, false);

        // Do all the stuff to initialize your custom view
        edtQuantity = v.findViewById(R.id.edtQuantity);

        final int id = getArguments().getInt("id");
        final String name = getArguments().getString("name");
        final String price = getArguments().getString("price");

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("restaurant", 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        final EditText edtQuantity = v.findViewById(R.id.edtQuantity);
        Button btnXacNhan = v.findViewById(R.id.btnXacNhan);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity  =  edtQuantity.getText().toString();
                Toast.makeText(getContext(), quantity + "", Toast.LENGTH_SHORT).show();

                FragmentManager manager = getFragmentManager();
                try {
                    int quant = Integer.parseInt(quantity);
                    FragmentTransaction transaction = manager.beginTransaction();
                    Bill_Home_Fragment bill_home_fragment = new Bill_Home_Fragment();
//                    Bundle bundle = new Bundle();
//
//                    bundle.putInt("id", id);
//                    bundle.putString("name", name);
//                    bundle.putString("price", price);
//                    bundle.putInt("quantity", quant);

                    Bill_Food bill_food = new Bill_Food();
                    bill_food.setName(name);
                    bill_food.setPrice(Double.parseDouble(price));
                    bill_food.setQuantity(quant);


                    String getBillFoodList = sharedPreferences.getString("bill_order_list", "");

                    Gson gson = new Gson();
                    List<Bill_Food> bill_foodList;
                    if(!getBillFoodList.equals("")) {

                        Bill_Food_List_Type bill_food_list_type = gson.fromJson(getBillFoodList, Bill_Food_List_Type.class);
                        bill_foodList = bill_food_list_type.getFoodList();
                        boolean checkExistItem = false;
                        for (Bill_Food item: bill_foodList
                             ) {
                            if (item.getName().equals(bill_food.getName()))
                            {
                                item.setQuantity(quant);
                                checkExistItem = true;
                            }
                        }
                        if (checkExistItem == false) {
                            bill_foodList.add(bill_food);
                        }
                    }
                    else {
                        bill_foodList = new ArrayList<>();
                        bill_foodList.add(bill_food);
                    }

                    Bill_Food_List_Type bill_food_list_type = new Bill_Food_List_Type();
                    bill_food_list_type.setFoodList(bill_foodList);

                    String json = gson.toJson(bill_food_list_type);

                    editor.putString("bill_order_list", json);
                    editor.commit();
//
//                    bill_home_fragment.setArguments(bundle);

                    transaction.replace(R.id.nav_host_fragment, bill_home_fragment);

                    transaction.addToBackStack(null);
                    transaction.commit();
                    dismiss();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Vui lòng nhập số nguyên lơn hơn 0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}