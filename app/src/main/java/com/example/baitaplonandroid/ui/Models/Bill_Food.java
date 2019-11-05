package com.example.baitaplonandroid.ui.Models;

public class Bill_Food extends BaseModel {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private int billId;

    public Bill_Food(int id, String name, int quantity, double price, int billId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.billId = billId;
    }

    public Bill_Food(int id, String name, int quantity, double price , int billId, String createAt, String updateAt, boolean isDelete) {
        super(createAt, updateAt, isDelete);
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.billId = billId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public Bill_Food() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
