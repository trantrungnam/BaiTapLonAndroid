package com.example.baitaplonandroid.ui.Food;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Food;
import com.example.baitaplonandroid.ui.SQLHelper.FoodHelper;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Food_Item_Detail_Admin extends Fragment {

    private ImageView img_food;
    private TextView food_price, unit, description, food_name;
    private androidx.appcompat.widget.Toolbar toolbar;
    private Button btnBack, btnDelete, btnUpdate;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private FoodHelper foodHelper;


    public Food_Item_Detail_Admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food__item__detail__admin, container, false);

        final int id = getArguments().getInt("id", -1);
        final String nameValue = getArguments().getString("name", "");
        final String descriptionValue = getArguments().getString("description", "");
        final String unitValue = getArguments().getString("unit", "");
        final String imageValue = getArguments().getString("image", "");
        final String priceValue = getArguments().getString("price", "");
        final int[] categoriesId = getArguments().getIntArray("category");

        //PROVIDER DATABASE
        foodHelper = new FoodHelper(getContext());

        //FRAGMENT MANAGER
        manager = getFragmentManager();

        // IMAGE VIEW
        img_food = view.findViewById(R.id.img_food);
        img_food = view.findViewById(R.id.img_food);
        try {

            Bitmap thumbnail = (BitmapFactory.decodeFile(imageValue));
            thumbnail = getResizedBitmap(thumbnail, 400);
            img_food.setImageBitmap(thumbnail);
        } catch ( Exception e ) {
            Log.d("myapp", "onCreateView: " + e);
        }


        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(nameValue);

        //TEXT VIEW

        food_name = view.findViewById(R.id.food_name);
        food_name.setText(nameValue);

        food_price = view.findViewById(R.id.food_price);
        food_price.setText(priceValue);

        unit = view.findViewById(R.id.unit);
        unit.setText(unitValue);

        description = view.findViewById(R.id.description);
        description.setText(descriptionValue);

        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food_Home food_home = new Food_Home();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, food_home);
                transaction.commit();
            }
        });

        // BTN DELETE
        btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food_Home_Admin food_home_admin = new Food_Home_Admin();
                foodHelper.deleteFoodById(id);
                transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, food_home_admin);
                transaction.commit();
            }
        });

        // BTN UPDATE
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Food_Item_Update_Admin_Pure update_admin_pure = new Food_Item_Update_Admin_Pure();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("name", nameValue);
                bundle.putString("description", descriptionValue);
                bundle.putString("unit", unitValue);
                bundle.putString("image", imageValue);
                bundle.putString("price", priceValue);
                bundle.putIntArray("category", categoriesId);
                update_admin_pure.setArguments(bundle);
                transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, update_admin_pure);
                transaction.commit();
            }
        });


        return view;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
