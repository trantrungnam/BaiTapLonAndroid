package com.example.baitaplonandroid.ui.gallery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Food.Food_Home;
import com.example.baitaplonandroid.ui.Models.Bill;
import com.example.baitaplonandroid.ui.Models.Bill_Food;
import com.example.baitaplonandroid.ui.Models.Bill_Food_List_Type;
import com.example.baitaplonandroid.ui.SQLHelper.BillFoodHelper;
import com.example.baitaplonandroid.ui.SQLHelper.BillHelper;
import com.google.gson.Gson;

import java.util.List;

public class Bill_Home_Fragment extends Fragment {

    private ListView listViewCategories;
    private TextView txtTongTieng;
    private BillFoodHelper billFoodHelper;
    private BillHelper billHelper;
    private Button btnThemMon, btnTao;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private List<Bill_Food> bill_foods_item;

    public Bill_Home_Fragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_home, container, false);


        //BILL FOOD HELPER
        billFoodHelper = new BillFoodHelper(getContext());
        billHelper = new BillHelper(getContext());

        listViewCategories = view.findViewById(R.id.listViewCategories);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("restaurant", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        double tongTien = 0;
        String myString = sharedPreferences.getString("bill_order_list", "");

        if (!myString.equals("")) {
            Bill_Food_List_Type bill_food_list_type = gson.fromJson(myString, Bill_Food_List_Type.class);
            bill_foods_item = bill_food_list_type.getFoodList();
            Bill_Food_Custom_Adapter bill_food_custom_adapter = new Bill_Food_Custom_Adapter(getActivity(), getContext(), bill_foods_item);
            listViewCategories.setAdapter(bill_food_custom_adapter);

            for (Bill_Food item : bill_foods_item
            ) {
                tongTien += item.getQuantity() * item.getPrice();
            }
        }

        txtTongTieng = view.findViewById(R.id.txtTongTieng);
        txtTongTieng.setText(tongTien + "");

        btnThemMon = view.findViewById(R.id.btnThemMon);
        btnThemMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Food_Home food_home = new Food_Home();
                transaction.replace(R.id.nav_host_fragment, food_home);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                Bundle bundle = new Bundle();

                bundle.putInt("id", bill_foods_item.get(i).getId());
                bundle.putString("name", bill_foods_item.get(i).getName());
                bundle.putString("price", bill_foods_item.get(i).getPrice() + "");
                DialogFragment dialogFragment = new Dialog_With_Config_Quantity(view);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(ft, "dialog");
            }
        });

        btnTao  = view.findViewById(R.id.btnThanhToan);

        btnTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("restaurant", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String bill_order = sharedPreferences.getString("bill_order_list", "");
                Gson gson = new Gson();
                if(!bill_order.equals("")) {
                    Bill item = new Bill();
                    item.setTongTien(Double.parseDouble(txtTongTieng.getText().toString()));
                    Bill result = billHelper.addBill(item);
                    if(result != null) {
                        Bill_Food_List_Type bill_food_list_type = gson.fromJson(bill_order, Bill_Food_List_Type.class);
                        bill_foods_item = bill_food_list_type.getFoodList();
                        for (Bill_Food x :bill_foods_item
                         ) {
                            billFoodHelper.addBillFood(x, result.getId());
                        }
                    }
                    editor.putString("bill_order_list", "");
                    editor.commit();
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                    Bill_Home_Fragment bill_home_fragment = new Bill_Home_Fragment();

                    manager = getFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, bill_home_fragment);
                    transaction.commit();
                }
            }
        });

        return view;
    }
}