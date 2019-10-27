package com.example.baitaplonandroid.ui.Models;

public class BaseModel {
    private String CreateAt;
    private String UpdateAt;
    private boolean isDelete;

    public BaseModel(String createAt, String updateAt, boolean isDelete) {
        CreateAt = createAt;
        UpdateAt = updateAt;
        this.isDelete = isDelete;
    }

    public BaseModel() {
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(String createAt) {
        CreateAt = createAt;
    }

    public String getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(String updateAt) {
        UpdateAt = updateAt;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "CreateAt='" + CreateAt + '\'' +
                ", UpdateAt='" + UpdateAt + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }
}
