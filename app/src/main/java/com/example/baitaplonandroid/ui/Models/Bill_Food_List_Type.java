package com.example.baitaplonandroid.ui.Models;

import java.util.List;

public class Bill_Food_List_Type {
    private List<Bill_Food> foodList;

    public Bill_Food_List_Type(List<Bill_Food> foodList) {
        this.foodList = foodList;
    }

    public Bill_Food_List_Type() {
    }

    public List<Bill_Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Bill_Food> foodList) {
        this.foodList = foodList;
    }
}