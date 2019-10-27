package com.example.baitaplonandroid.ui.Models;

import java.util.Arrays;

public class Food {
    private int id;
    private String name;
    private String description;
    private Double price;
    private int unit;
    private String picture;
    private int[] categories;
    private int[] orders;

    public Food() {

    }

    public Food(int id, String name, String description, Double price, int unit, String picture, int[] categories, int[] orders) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.picture = picture;
        this.categories = categories;
        this.orders = orders;
        this.categories = categories;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int[] getCategories() {
        return categories;
    }

    public void setCategories(int[] categories) {
        this.categories = categories;
    }

    public int[] getOrders() {
        return orders;
    }

    public void setOrders(int[] orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", unit=" + unit +
                ", picture='" + picture + '\'' +
                ", categories=" + Arrays.toString(categories) +
                ", orders=" + Arrays.toString(orders) +
                '}';
    }
}
