package com.example.baitaplonandroid.ui.Food;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Bill;
import com.example.baitaplonandroid.ui.Models.Bill_Food;
import com.example.baitaplonandroid.ui.Models.Bill_Food_List_Type;
import com.example.baitaplonandroid.ui.Models.Category;
import com.example.baitaplonandroid.ui.Models.Food;
import com.example.baitaplonandroid.ui.SQLHelper.FoodHelper;
import com.example.baitaplonandroid.ui.SQLHelper.UserHelper;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Food_Home extends Fragment {

    private EditText edtSearchFood;
    private Button btnSearchFood;
    private ListView food_list_view;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public Food_Home() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food__home, container, false);

        // HANDLE FRAGMENT
        manager = getFragmentManager();
        transaction = manager.beginTransaction();

        // EDITTEXT SEARCH FOOD
        edtSearchFood = view.findViewById(R.id.edtSearchFood);


        //BUTTON SEACH FOOD
        btnSearchFood = view.findViewById(R.id.btnSearchFood);

        // USER HELPER
        FoodHelper foodHelper = new FoodHelper(getContext());
        final List<Food> foods = foodHelper.getAllFood();

        //INTITIAL HOME ADAPTER
        Food_Item_For_ListView item = new Food_Item_For_ListView(getContext(), foods);

        //LISTVIEW WITH FOODS

        food_list_view = view.findViewById(R.id.food_list_view);
        food_list_view.setAdapter(item);
        food_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food_Detail foodDetail = new Food_Detail();
                Bundle bundle = new Bundle();
                Food food = foods.get(i);
                bundle.putInt("id", food.getId());
                bundle.putString("name", food.getName());
                bundle.putString("description", food.getName());
                bundle.putString("unit", food.getUnit());
                bundle.putString("image", food.getPicture());
                bundle.putString("price", food.getPrice().toString());
                List<Integer> categories = food.getCategories();
                int[] categoryIds = new int[categories.size()];
                for (int z = 0 ; z < categories.size(); z++) {
                    categoryIds[z] = categories.get(z);
                }
                bundle.putIntArray("category", categoryIds);
                foodDetail.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, foodDetail);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}
