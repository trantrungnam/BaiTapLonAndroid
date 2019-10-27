package com.example.baitaplonandroid.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.baitaplonandroid.ui.Models.Category;

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

    public LiveData<List<Category>> getCategories() {

        categories = new MutableLiveData<List<Category>>();

        int[] foods = {1, 2, 3, 4, 5};
        //Initial items in here
        Category category = new Category(1, "Món chay", "Rất ngon", foods);
        Category category2 = new Category(2, "Món chay", "Rất ngon", foods);
        Category category3 = new Category(3, "Món chay", "Rất ngon", foods);

        ///Add item to list, This line will be replaced by sql query get all category
        List<Category> categoryList = new ArrayList<Category>();
        categoryList.add(category);
        categoryList.add(category2);
        categoryList.add(category3);

//        if (users == null) {
//            users = new MutableLiveData<List<User>>();
//            loadUsers();
//        }

        categories.setValue(categoryList);
        return categories;
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}
