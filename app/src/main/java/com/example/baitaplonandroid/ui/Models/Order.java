package com.example.baitaplonandroid.ui.Models;

import java.util.Arrays;

public class Order {
    private int id;
    private String order_date;
    private int[] foods;
    private int customerId;
    private double total_price;
    private int[] tablesId;
    private String status;
    private boolean isAtTable;

    public Order(int id, String order_date, int[] foods, int customerId, double total_price, int[] tablesId, String status, boolean isAtTable) {
        this.id = id;
        this.order_date = order_date;
        this.foods = foods;
        this.customerId = customerId;
        this.total_price = total_price;
        this.tablesId = tablesId;
        this.status = status;
        this.isAtTable = isAtTable;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int[] getFoods() {
        return foods;
    }

    public void setFoods(int[] foods) {
        this.foods = foods;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int[] getTablesId() {
        return tablesId;
    }

    public void setTablesId(int[] tablesId) {
        this.tablesId = tablesId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAtTable() {
        return isAtTable;
    }

    public void setAtTable(boolean atTable) {
        isAtTable = atTable;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", order_date='" + order_date + '\'' +
                ", foods=" + Arrays.toString(foods) +
                ", customerId=" + customerId +
                ", total_price=" + total_price +
                ", tablesId=" + Arrays.toString(tablesId) +
                ", status='" + status + '\'' +
                ", isAtTable=" + isAtTable +
                '}';
    }
}
