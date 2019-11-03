package com.example.baitaplonandroid.ui.Food;


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
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Food;
import com.example.baitaplonandroid.ui.SQLHelper.FoodHelper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Food_Home_Admin extends Fragment {

    private EditText edtSearchFood;
    private Button btnSearchFood, btnAddFood;
    private ListView food_list_view;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public Food_Home_Admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food__home__admin, container, false);
        manager = getFragmentManager();

        // EDITTEXT SEARCH FOOD
        edtSearchFood = view.findViewById(R.id.edtSearchFood);

        // BUTTON BTN SEARCH
        btnSearchFood  = view.findViewById(R.id.btnSearchFood);

        // LIST FOODS
        FoodHelper foodHelper = new FoodHelper(getActivity());
        final List<Food> foods = foodHelper.getAllFood();

        //FOOD ADAPTER.
        final Food_Item_For_ListView_Admin food_item_for_listView_admin = new Food_Item_For_ListView_Admin(getContext(), foods);

        // LIST VIEW
        food_list_view = view.findViewById(R.id.food_list_view);
        food_list_view.setAdapter(food_item_for_listView_admin);

        btnAddFood = view.findViewById(R.id.btnAddFood);
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = manager.beginTransaction();
                Food_Item_Update_Admin food_item_update_admin = new Food_Item_Update_Admin();
                transaction.replace(R.id.nav_host_fragment, food_item_update_admin);
                transaction.commit();
            }
        });

        food_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food_Item_Detail_Admin foodDetail = new Food_Item_Detail_Admin();
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
                transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, foodDetail);
                transaction.commit();
            }
        });

        return view;
    }

}
