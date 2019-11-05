package com.example.baitaplonandroid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Food.Food_By_Category;
import com.example.baitaplonandroid.ui.Models.Category;
import com.example.baitaplonandroid.ui.SQLHelper.CategoryHelper;
import com.example.baitaplonandroid.ui.listview.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ListView listViewHome;
    private CategoryHelper categoryHelper;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//
//        // Get values from model view that ensure data not be lost when we rotate
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        manager = getFragmentManager();

        categoryHelper = new CategoryHelper(getContext());
        final List<Category> categories = categoryHelper.getAllCategory();
 //
//        // Set List view show data.
        listViewHome = (ListView) root.findViewById(R.id.listViewHome);
        Home_Custom_Adapter home_custom_adapter = new Home_Custom_Adapter(getContext(), categories);
        listViewHome.setAdapter(home_custom_adapter);
        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                transaction = manager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("id", categories.get(i).getId());
                Food_By_Category food_by_category = new Food_By_Category();
                food_by_category.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, food_by_category);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return root;
    }
}