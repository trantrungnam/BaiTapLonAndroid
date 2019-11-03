package com.example.baitaplonandroid.ui.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.User;
import com.example.baitaplonandroid.ui.SQLHelper.UserHelper;
import com.example.baitaplonandroid.ui.home.HomeFragment;

public class User_Login extends Fragment {

    private EditText edtTaiKhoan, edtMatKhau;
    private Button btnDangNhap, btnDangKy;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private UserHelper userHelper;
    private SharedPreferences sharedPreferences;

    public User_Login() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user__login, container, false);

        //HANDLE FRAGMENT TRANSACTION
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        //HANDLE DATABASE PROVIDER
        userHelper = new UserHelper(getContext());

        //EDITEXT TAI KHOAN
        edtTaiKhoan = view.findViewById(R.id.edtTaiKhoan);

        //EDITEXT MAT KHAU
        edtMatKhau = view.findViewById(R.id.edtMatKhau);


        //BTN DANG NHAP
        btnDangNhap = view.findViewById(R.id.btnLogin);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();
                User user = userHelper.loginUser(taikhoan, matkhau);
                if (user != null) {
                    sharedPreferences = getActivity().getSharedPreferences("restaurant", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor  = sharedPreferences.edit();
                    editor.putInt("id", user.getId());
                    editor.putString("username", user.getUsername());
                    editor.putString("role", user.getRole());
                    editor.commit();

                    HomeFragment homeFragment = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("username" , taikhoan);
                    bundle.putString("role", user.getRole());
                    transaction.replace(R.id.nav_host_fragment, homeFragment);
                    transaction.commit();
                    Toast.makeText(getContext(), "Đăng nhập thành công" + taikhoan, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BTN DANG KY
        btnDangKy = view.findViewById(R.id.btnRegister);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();
                if (taikhoan.isEmpty() || matkhau.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = userHelper.getUserInfoByUsername(taikhoan);
                if (user != null) {
                    Toast.makeText(getContext(), "Tài khoản đã tồn tại trong hệ thống", Toast.LENGTH_SHORT).show();
                } else {
                    userHelper.addUser(new User(taikhoan, matkhau));
                    User userInfoAfterRegister = userHelper.getUserInfoByUsername(taikhoan);
                    if (userInfoAfterRegister != null) {
                        // LOGIN SUCCESS
                        User userAfterSearch = userHelper.getUserInfoByUsername(taikhoan);
                        sharedPreferences = getActivity().getSharedPreferences("restaurant", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("id", userAfterSearch.getId());
                        editor.putString("username", userAfterSearch.getUsername());
                        editor.putString("role", userAfterSearch.getRole());
                        editor.commit();

                        HomeFragment homeFragment = new HomeFragment();
                        transaction.replace(R.id.nav_host_fragment, homeFragment);
                        transaction.commit();
                        Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        return view;
    }

}
