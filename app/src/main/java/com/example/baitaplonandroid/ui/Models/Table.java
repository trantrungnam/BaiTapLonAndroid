package com.example.baitaplonandroid.ui.Models;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class Table {
    private int id;
    private int capacity;
    private int[] orderIds;

    public Table(int id, int capacity, int[] orders) {
        this.id = id;
        this.capacity = capacity;
        this.orderIds = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int[] getOrderIds() {
        return orderIds;
    }

    public void setOrders(int[] orderIds) {
        this.orderIds = orderIds;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", orders=" + Arrays.toString(orderIds) +
                '}';
    }
}
