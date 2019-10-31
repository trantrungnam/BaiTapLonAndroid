package com.example.baitaplonandroid.ui.category;


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
import com.example.baitaplonandroid.ui.Models.Category;
import com.example.baitaplonandroid.ui.SQLHelper.CategoryHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryUpdateFragment extends Fragment {

    private Button btnUpdate, btnBack;
    private EditText edtName, edtDescription;
    private CategoryHelper categoryHelper;
    private FragmentManager manager;
    private FragmentTransaction transaction;


    public CategoryUpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_update, container, false);

        //HANDLE FRAGMENT MANAGER AND TRANSACTION
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        //CONFIG DATABASE PROVIDER
        categoryHelper = new CategoryHelper(getContext());

        //GET ARGRUMENT PASS BY DETAIL FRAGMENT
        final int idCategory = getArguments().getInt("id");
        final String name = getArguments().getString("name");
        final String description = getArguments().getString("description");


        // HANDLE EDIT TEXT NAME
        edtName = view.findViewById(R.id.edtName);
        edtName.setText(name);


        //HANDLE EDITTEXT DESCRIPTION
        edtDescription = view.findViewById(R.id.edtDescription);
        edtDescription.setText(description != null ? description : "");

        // HANDLE BTN UPDATE.
        btnUpdate = view.findViewById(R.id.btnUpdateCategory);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = idCategory;
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();
                categoryHelper.Update(new Category(idCategory, name, description));
                CategoryFragment categoryFragment = new CategoryFragment();
                transaction.replace(R.id.nav_host_fragment, categoryFragment);
                transaction.commit();
                Toast.makeText(getContext(), "Update Success", Toast.LENGTH_SHORT).show();
            }
        });


        //HANDLE BTN BACK
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryDetailFragment categoryDetailFragment = new CategoryDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", idCategory);
                bundle.putString("name", name);
                bundle.putString("description", description);
                categoryDetailFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, categoryDetailFragment);
                transaction.commit();
            }
        });

        return view;
    }

}
