package com.example.expensetracking;

public class User {
    private int Id;
    private String username;
    public User(int userId, String username) {
        this.Id = userId;
        this.username = username;
    }
    public int getId() {
        return Id;
    }
    public String getUsername() {
        return username;
    }
    public int Id() {
        return 0;
    }
}
