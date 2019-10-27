package com.example.baitaplonandroid.ui.category;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.baitaplonandroid.R;
import com.example.baitaplonandroid.ui.Models.Category;

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
        id.setText(categories.get(i).getId() + "");
        name.setText(categories.get(i).getName());
        return view;
    }
}
