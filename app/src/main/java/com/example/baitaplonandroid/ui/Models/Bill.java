package com.example.baitaplonandroid.ui.Models;

import java.util.List;

public class Bill extends BaseModel {
    private int id;
    private Double tongTien;
    int isPayed;

    public Bill(int id) {
        this.id = id;
    }

    public Bill(String createAt, String updateAt, boolean isDelete, int id, Double tongTien, int isPayed) {
        super(createAt, updateAt, isDelete);
        this.id = id;
        this.tongTien = tongTien;
        this.isPayed = isPayed;

    }

    public Bill() {
    }

    public int getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(int isPayed) {
        this.isPayed = isPayed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                '}';
    }
}
