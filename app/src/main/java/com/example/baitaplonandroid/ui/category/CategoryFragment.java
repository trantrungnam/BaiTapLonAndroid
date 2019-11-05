package com.example.baitaplonandroid.ui.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Category;
import com.example.baitaplonandroid.ui.SQLHelper.CategoryHelper;
import com.example.baitaplonandroid.ui.home.HomeViewModel;
import com.example.baitaplonandroid.ui.listview.CustomAdapter;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;
    private ListView listView;
    private List<Category> categoriesInstance;
    private Button btnAddCategory;
    private CategoryHelper categoryHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        categoryHelper = new CategoryHelper(getContext());
        //
        listView = root.findViewById(R.id.list_category);
        categoriesInstance = categoryHelper.getAllCategory();
        final CategoryCustomAdapter categoryCustomAdapter = new CategoryCustomAdapter(getContext(), categoriesInstance);
        listView.setAdapter(categoryCustomAdapter);

        //TEXT VIEW
        final TextView textView = root.findViewById(R.id.text_category);
                textView.setText("Danh mục");

        // SETTING FOR FRAGMENT TRANSACTION
        final FragmentManager manager = getFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();

        //LIST VIEW
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category item = (Category) adapterView.getItemAtPosition(i);
                Toast.makeText(getContext(), item.getDescription(), Toast.LENGTH_SHORT).show();
                //PASS DATA TO FRAGMENT BY BUNDLE
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                bundle.putString("name", item.getName());
                bundle.putString("description", item.getDescription());
                bundle.putString("imageurl", item.getImageurl());
                //CREATE FRAGMENT INSTANCE AND SHOW IT.
//                CategoryDetailFragment categoryDetailFragment = new CategoryDetailFragment();
//                categoryDetailFragment.setArguments(bundle);
                CategoryUpdateFragment categoryUpdateFragment = new CategoryUpdateFragment();
                categoryUpdateFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, categoryUpdateFragment).addToBackStack(null);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        // BUTTON
        btnAddCategory = root.findViewById(R.id.btnAddCategory);
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
//                bundle.putString("message", "hello qua r ne");
                AddCategoryFragment addCategoryFragment = new AddCategoryFragment();
                addCategoryFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, addCategoryFragment);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return root;
    }
}
