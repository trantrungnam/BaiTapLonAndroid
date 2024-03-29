package com.example.baitaplonandroid.ui.category;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Category;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class CategoryCustomAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Category> categories;

    public CategoryCustomAdapter(Context _context, List<Category> _categories) {
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
        view = layoutInflater.inflate(R.layout.activity_listview_category, null);
        TextView id = view.findViewById(R.id.categoryId);
        TextView name = view.findViewById(R.id.categoryName);
        ImageView btnImageurl = view.findViewById(R.id.btnImageurl);

        try {
            Bitmap thumbnail = (BitmapFactory.decodeFile(categories.get(i).getImageurl()));
            thumbnail = getResizedBitmap(thumbnail, 400);
            btnImageurl.setImageBitmap(thumbnail);
//        imageButton.setImageResource();
        } catch (Exception e) {
            Log.d("myapp", "getView: " + e);
        }

        id.setText(categories.get(i).getId() + "");
        name.setText(categories.get(i).getName());
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
