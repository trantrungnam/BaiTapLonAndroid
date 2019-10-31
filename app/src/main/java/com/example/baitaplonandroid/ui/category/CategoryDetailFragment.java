package com.example.baitaplonandroid.ui.category;


import android.location.Address;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Category;
import com.example.baitaplonandroid.ui.SQLHelper.CategoryHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDetailFragment extends Fragment {

    private TextView txtName;
    private TextView txtDescription;
    private Button btnBack, btnXoa, btnUpdate;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private CategoryHelper categoryHelper;
    private int idCategory;

    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_detail, container, false);

        // GET VALUE FROM BUNDLE
        idCategory = getArguments().getInt("id");
        Toast.makeText(getContext(), idCategory + "", Toast.LENGTH_SHORT).show();
        final String name = getArguments().getString("name");
        final String description = getArguments().getString("description");

        //INITIAL CATEGORY HELPER DATABASE.
        categoryHelper = new CategoryHelper(getContext());

        //SETTING FOR FRAGMENT TRANSACTION
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        //TEXT VIEW CATEGORY_NAME
        txtName = view.findViewById(R.id.name);
        txtName.setText(name);

        //TEXT VIEW CATEGORY_DESCRIPTION
        txtDescription = view.findViewById(R.id.description);
        txtDescription.setText(description);

        // Handle Button Back
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryFragment categoryFragment = new CategoryFragment();

                //ADD CATEGORY FRAGMENT UI.
                transaction.replace(R.id.nav_host_fragment, categoryFragment);
                transaction.commit();
            }
        });

        // HANDLE BTN XOA
        btnXoa = view.findViewById(R.id.btnXoa);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), idCategory + "", Toast.LENGTH_SHORT).show();
                categoryHelper.deleteCategoryById(idCategory);
                CategoryFragment categoryFragment = new CategoryFragment();

                //ADD CATEGORY FRAGMENT UI.
                transaction.replace(R.id.nav_host_fragment, categoryFragment);
                transaction.commit();
            }
        });

        //HANDLE BTN UPDATE
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Category category = categoryHelper.getCategoryById(idCategory);
                bundle.putInt("id", idCategory);
                bundle.putString("name", category.getName());
                bundle.putString("description", category.getDescription());
                CategoryUpdateFragment categoryUpdateFragment = new CategoryUpdateFragment();
                categoryUpdateFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, categoryUpdateFragment);
                transaction.commit();
            }
        });

        return view;
    }

}
