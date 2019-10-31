package com.example.baitaplonandroid.ui.User;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.User;
import com.example.baitaplonandroid.ui.SQLHelper.UserHelper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Admin_List_User extends Fragment {

    private List<User> users;
    private UserHelper userHelper;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ListView listViewUser;


    public Admin_List_User() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin__list__user, container, false);

        //FRAMENT CONFIG
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        //CONFIG DATABASE PROVIDER
        userHelper = new UserHelper(getContext());

        //GET LIST USER
        users = userHelper.getAllUser();

        //CUSTOM ADAPTER
        CustomAdapterUser adapterUser = new CustomAdapterUser(getContext(), users);
        listViewUser = view.findViewById(R.id.listUser);
        listViewUser.setAdapter(adapterUser);

        return view;
    }

}
