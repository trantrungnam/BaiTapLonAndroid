package com.example.baitaplonandroid.ui.Models;

import java.util.Arrays;
import java.util.List;

public class Food extends  BaseModel{
    private int id;
    private String name;
    private String description;
    private Double price;
    private String unit;
    private String picture;
    private List<Integer> categories;

    public Food() {

    }

    public Food(String name, String description, Double price, String unit, String picture, List<Integer> categories) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.picture = picture;
        this.categories = categories;
    }

    public Food(int id, String name, String description, Double price, String unit, String picture, List<Integer> categories) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.picture = picture;
        this.categories = categories;
    }

    public Food(String createAt, String updateAt, boolean isDelete, int id, String name, String description, Double price, String unit, String picture, List<Integer> categories) {
        super(createAt, updateAt, isDelete);
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.picture = picture;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
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
                ", categories=" + categories.toString() +
                '}';
    }
}
