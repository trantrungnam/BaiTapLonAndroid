package com.example.baitaplonandroid.ui.Models;

public class User extends BaseModel{
    private int Id;
    private String username;
    private String password;
    private String role;

    public User(int id, String username, String password, String role, String createAt, String updateAt, boolean isDelete) {
        super(createAt, updateAt, isDelete);
        Id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(int id, String username, String password, String role) {
        Id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(int id, String username, String password, String role, boolean isDelete) {
        super(isDelete);
        Id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
