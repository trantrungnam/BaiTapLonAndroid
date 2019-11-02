package com.example.baitaplonandroid.ui.User;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.User;
import com.example.baitaplonandroid.ui.SQLHelper.UserHelper;
import com.example.baitaplonandroid.ui.home.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class User_Detail extends Fragment {

    private FragmentManager manager;
    public FragmentTransaction transaction;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView edtUserName;
    private EditText  edtPassword;
    private Button btnSave, btnHuy, btnDangXuat;

    public User_Detail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user__detail, container, false);

        //Config DATABASE PROVIDER
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        //CONFIG WITH SHAREPREFERENCE.
        sharedPreferences = getActivity().getSharedPreferences("restaurant", 0);
        editor = sharedPreferences.edit();

        final int id = sharedPreferences.getInt("id", -1);
        String username = sharedPreferences.getString("username", "");

        //EDITTEXT USERNAME
        edtUserName = view.findViewById(R.id.edtUserName);
        edtUserName.setText(username);

        // EDITTEXT PASSWORD
        edtPassword = view.findViewById(R.id.edtPassword);

        //BTN SAVE
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = edtPassword.getText().toString();
                if(password.equals("")) {
                    User user = new User(id, password);
                    UserHelper userHelper = new UserHelper(getContext());
                    userHelper.UpdatePassword(user);
                    User userAfterChecked = userHelper.getUserById(id);
                    editor.putInt("id", userAfterChecked.getId());
                    editor.putString("username", userAfterChecked.getUsername());
                    editor.putString("role", userAfterChecked.getRole());
                    HomeFragment homeFragment = new HomeFragment();
                    transaction.replace(R.id.nav_host_fragment, homeFragment);
                    transaction.commit();
                }
                else {
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BTN HUY
        btnHuy = view.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.nav_host_fragment, homeFragment);
                transaction.commit();
            }
        });

        //BTN DANG XUAT
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.remove("id");
                editor.remove("username");
                editor.remove("password");
                editor.remove("role");
                editor.commit();

                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.nav_host_fragment, homeFragment);
                transaction.commit();
            }
        });


        return view;
    }

}
