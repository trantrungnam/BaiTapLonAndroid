package com.example.baitaplonandroid.ui.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Category;
import com.example.baitaplonandroid.ui.home.HomeViewModel;
import com.example.baitaplonandroid.ui.listview.CustomAdapter;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;
    private ListView listView;
    private List<Category> categoriesInstance;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryViewModel =
                ViewModelProviders.of(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        final TextView textView = root.findViewById(R.id.text_category);
        categoryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        listView = (ListView) root.findViewById(R.id.list_category);
        CategoryViewModel viewModel = ViewModelProviders.of(getActivity()).get(CategoryViewModel.class);
        viewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoriesInstance = categories;
                CategoryCustomAdapter categoryCustomAdapter = new CategoryCustomAdapter(getContext(), categoriesInstance);

                listView.setAdapter(categoryCustomAdapter);
            }
        });
        return root;
    }
}
