package com.example.baitaplonandroid.ui.Models;

import java.util.List;

public class Bill extends  BaseModel{
    private int id;

    public Bill(int id) {
        this.id = id;
    }

    public Bill(String createAt, String updateAt, boolean isDelete, int id) {
        super(createAt, updateAt, isDelete);
        this.id = id;
    }

    public Bill() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                '}';
    }
}
