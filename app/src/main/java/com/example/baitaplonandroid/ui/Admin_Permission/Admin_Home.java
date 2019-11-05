package com.example.baitaplonandroid.ui.Admin_Permission;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Bill.Bill_Home_Admin;
import com.example.baitaplonandroid.ui.Food.Food_Home_Admin;
import com.example.baitaplonandroid.ui.User.Admin_List_User;
import com.example.baitaplonandroid.ui.User.User_Login;
import com.example.baitaplonandroid.ui.category.CategoryFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Admin_Home extends Fragment {


    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Button btnUser, btnMonAn, btnCategory, btnOrder;
    private SharedPreferences sharedPreferences;

    public Admin_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin__home, container, false);

        // CONFIG MANAGER
        manager = getFragmentManager();

        sharedPreferences = this.getActivity().getSharedPreferences("restaurant", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String role = sharedPreferences.getString("role", "");
        int id = sharedPreferences.getInt("id", -1);

        if(id >= 0 && role.equals("Admin")) {
//        if (id >= 0) {
            //HANDLE BTN USER
            btnUser = view.findViewById(R.id.btnUser);
            btnUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transaction = manager.beginTransaction();
                    Admin_List_User admin_list_user = new Admin_List_User();
                    transaction.replace(R.id.nav_host_fragment, admin_list_user);

                    transaction.addToBackStack(null);
                    transaction.commit();

                }
            });

            //HANDLE BTN MON AN
            btnMonAn = view.findViewById(R.id.btnMonAn);
            btnMonAn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transaction = manager.beginTransaction();
                    Food_Home_Admin food_home_admin = new Food_Home_Admin();
                    transaction.replace(R.id.nav_host_fragment, food_home_admin);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            //HANDLE BTN CATEGORY
            btnCategory = view.findViewById(R.id.btnCategory);
            btnCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transaction = manager.beginTransaction();
                    CategoryFragment categoryFragment  = new CategoryFragment();
                    transaction.replace(R.id.nav_host_fragment, categoryFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

            //HANDLE BTN ORDER
            btnOrder = view.findViewById(R.id.btnOrder);
            btnOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transaction = manager.beginTransaction();
                    Bill_Home_Admin bill_home_admin  = new Bill_Home_Admin();
                    transaction.replace(R.id.nav_host_fragment, bill_home_admin);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        } else {
            User_Login user_login = new User_Login();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.nav_host_fragment, user_login);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        return view;
    }

}
