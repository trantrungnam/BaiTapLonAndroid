package com.example.baitaplonandroid.ui.Models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Category extends BaseModel {
    private int id;
    private String name;
    private String description;
    private String imageurl;

    public Category(int id, String name, String description, String imageurl, String createAt, String updateAt, boolean isDeleted) {
        super(createAt, updateAt, isDeleted);
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
    }

    public Category(int id, String name, String description, String imageurl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
    }

    public Category(int id) {
        this.id = id;
    }

    public Category(String name, String description, String imageurl) {
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
    }

    public Category() {
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

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
