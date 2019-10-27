package com.example.baitaplonandroid.ui.category;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.baitaplonandroid.ui.Models.Category;
import com.example.baitaplonandroid.ui.SQLHelper.CategoryHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    private MutableLiveData<List<Category>> categories;


    public CategoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is category fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Category>> getCategories(Context context) {
        ///Add item to list, This line will be replaced by sql query get all category
        if (categories == null) {
            categories = new MutableLiveData<List<Category>>();
        }
        loadUsers(context);
        return categories;
    }

    private void loadUsers(Context context) {
        // Do an asynchronous operation to fetch users.
        CategoryHelper categoryHelper = new CategoryHelper(context);
        List<Category> categoryList = categoryHelper.getAllCategory();
        categories.setValue(categoryList);
    }
}
