package com.example.baitaplonandroid.ui.Food;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.baitaplonandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class Food_Detail extends Fragment {

    private ImageView img_food;
    private TextView food_price, unit, description, food_name;
    private androidx.appcompat.widget.Toolbar toolbar;
    private Button btnBack;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private FloatingActionButton btncart;


    public Food_Detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food__detail, container, false);

        final int id = getArguments().getInt("id", -1);
        String nameValue = getArguments().getString("name", "");
        String descriptionValue = getArguments().getString("description", "");
        String unitValue = getArguments().getString("unit", "");
        String imageValue = getArguments().getString("image", "");
        String priceValue = getArguments().getString("price", "");

        //FRAGMENT MANAGER
        manager = getFragmentManager();

        // IMAGE VIEW
        img_food = view.findViewById(R.id.img_food);
        img_food = view.findViewById(R.id.img_food);
        Bitmap thumbnail = (BitmapFactory.decodeFile(imageValue));
        thumbnail = getResizedBitmap(thumbnail, 400);
        img_food.setImageBitmap(thumbnail);


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
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        btncart = view.findViewById(R.id.btncart);
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                Bundle bundle = new Bundle();

                bundle.putInt("id", id);
                bundle.putString("name", food_name.getText().toString());
                bundle.putString("price", food_price.getText().toString());
                DialogFragment dialogFragment = new Custom_Dialog(view);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(ft, "dialog");
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

    public void GetValueFromDialog(String value) {
        Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
    }
}

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Make sure fragment codes match up
//        if (requestCode == DialogFragment.REQUEST_CODE) {
//            String editTextString = data.getStringExtra(
//                    DialogFragment.EDIT_TEXT_BUNDLE_KEY);
//
//}
