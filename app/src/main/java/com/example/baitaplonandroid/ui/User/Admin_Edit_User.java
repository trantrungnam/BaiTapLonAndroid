package com.example.baitaplonandroid.ui.User;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.User;
import com.example.baitaplonandroid.ui.SQLHelper.UserHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Admin_Edit_User extends Fragment {

    private EditText edtUserName, edtPassWord;
    private CheckBox cbIsAdmin;
    private Button btnSave, btnHuy;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private UserHelper userHelper;

    public Admin_Edit_User() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin__edit__user, container, false);

        final int id = getArguments().getInt("id");
        final String username = getArguments().getString("username");
        String password = getArguments().getString("password");
        // CONFIG FRAGMENT
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        //CONTENT PROVIDER
        userHelper = new UserHelper(getContext());

        // EDITEXT
        edtUserName = view.findViewById(R.id.edtUserName);
        edtUserName.setText(username);

        // EDITEXT USERNAME
        edtPassWord = view.findViewById(R.id.edtPassword);
        edtPassWord.setText(password);


        //EDITEXT CB ISADMIN
        cbIsAdmin = view.findViewById(R.id.cbIsAdmin);
        cbIsAdmin.setChecked(getArguments().getString("role") == "NV" ? false : true);
//        cbIsAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//
//                } else {
//
//                }
//            }
//        });

        //EDITEXT BTN SAVE
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = 0;
                String password = edtPassWord.getText().toString();
                if (cbIsAdmin.isChecked()) {
                    User user = new User(id, username, password, "Admin");
                    if (password.isEmpty()) {
                        result = userHelper.UpdateRole(user);
                    } else result = userHelper.Update(user);
                } else {
                    User user = new User(id, username, password, "NV");
                    if(!password.isEmpty()) {
                        result = userHelper.Update(user);
                    } else  {
                        result = userHelper.UpdateRole(user);
                    }
                }

                Admin_List_User adminListUser = new Admin_List_User();
                if (result == 1)
                    Toast.makeText(getContext(), "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
                transaction.replace(R.id.nav_host_fragment, adminListUser);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //EDITEXT BTN HUY
        btnHuy = view.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Admin_List_User adminListUser = new Admin_List_User();
                transaction.replace(R.id.nav_host_fragment, adminListUser);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}
