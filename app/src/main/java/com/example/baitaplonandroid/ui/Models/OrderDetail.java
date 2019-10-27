package com.example.baitaplonandroid.ui.Models;

import java.util.Arrays;

public class OrderDetail {
    private int id;
    private int[] foodIds;
    private int[] orderIds;
    private int quantity;
    private String comment;
    private String total_price;
    private String note;

    public OrderDetail(int id, int[] foodIds, int[] orderIds, int quantity, String comment, String total_price, String note) {
        this.id = id;
        this.foodIds = foodIds;
        this.orderIds = orderIds;
        this.quantity = quantity;
        this.comment = comment;
        this.total_price = total_price;
        this.note = note;
    }

    public OrderDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(int[] foodIds) {
        this.foodIds = foodIds;
    }

    public int[] getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(int[] orderIds) {
        this.orderIds = orderIds;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", foodIds=" + Arrays.toString(foodIds) +
                ", orderIds=" + Arrays.toString(orderIds) +
                ", quantity=" + quantity +
                ", comment='" + comment + '\'' +
                ", total_price='" + total_price + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
