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
public class AddCategoryFragment extends Fragment {

    private Button btnAddCategory;
    private EditText edtName;
    private EditText edtDescription;
    private CategoryHelper categoryHelper;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    public AddCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        String message = getArguments().getString("message");
        //Setting global variable
        categoryHelper = new CategoryHelper(getContext());
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        edtName = view.findViewById(R.id.name);
        edtDescription = view.findViewById(R.id.description);

        btnAddCategory = view.findViewById(R.id.btnAddCategory);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();
                Toast.makeText(getContext(), description, Toast.LENGTH_SHORT).show();
                if (name != "" && description != "") {
                    CategoryFragment categoryFragment = new CategoryFragment();
                    categoryHelper.addCategory(new Category(name, description));
                    transaction.replace(R.id.nav_host_fragment, categoryFragment);
                    transaction.commit();
                }
                else {
                    Toast.makeText(getContext(), "Giá trị không được rỗng.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
