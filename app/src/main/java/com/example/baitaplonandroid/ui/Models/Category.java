package com.example.baitaplonandroid.ui.Models;

import androidx.annotation.NonNull;

public class Category {
    private int id;
    private String name;
    private String description;
    private int[] foods;

    public Category (int id, String name, String description, int[] foods) {
        this.id = id;
        this.name = name;
        this.description = name;
        this.foods = foods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int[] getFoods() {
        return foods;
    }

    public void setFoods(int[] foods) {
        this.foods = foods;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
