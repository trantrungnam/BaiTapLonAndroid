package com.example.baitaplonandroid.ui.Food;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Food;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Food_Item_For_ListView extends BaseAdapter {

    private List<Food> foods;
    private Context context;
    private LayoutInflater layoutInflater;
    private String Document_img1 = "";

    public Food_Item_For_ListView(Context _context, List<Food> foods) {
        this.context = context;
        this.foods = foods;
        this.layoutInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int i) {
        return foods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view =  layoutInflater.inflate(R.layout.fragment_food__item__for__list_view, null);
        ImageView imageView = view.findViewById(R.id.img_food);
        Bitmap thumbnail = (BitmapFactory.decodeFile(foods.get(i).getPicture()));
        thumbnail = getResizedBitmap(thumbnail, 400);
        imageView.setImageBitmap(thumbnail);
//        imageButton.setImageResource();

        TextView txt_food_name = view.findViewById(R.id.food_name);
        txt_food_name.setText(foods.get(i).getName());

        TextView txt_food_price = view.findViewById(R.id.food_price);
        txt_food_price.setText(foods.get(i).getPrice().toString());

        return view;
    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
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
