package com.example.baitaplonandroid.ui.home;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Category;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Custom_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Category> categories;

    public Home_Custom_Adapter(Context _context, List<Category> _categories) {
        this.context = _context;
        this.layoutInflater = LayoutInflater.from(_context);
        this.categories = _categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.fragment_home__custom__adapter, null);
        TextView menu_name = view.findViewById(R.id.menu_name);
        ImageView menu_item = view.findViewById(R.id.menu_item);

        try {
            Bitmap thumbnail = (BitmapFactory.decodeFile(categories.get(i).getImageurl()));
            thumbnail = getResizedBitmap(thumbnail, 400);
            menu_item.setImageBitmap(thumbnail);
//        imageButton.setImageResource();
        } catch (Exception e) {
            Log.d("myapp", "getView: " + e);
        }

        menu_name.setText(categories.get(i).getName());
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