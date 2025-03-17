package com.example.ticketgui.BE;

public class User {
    private int id;
    private String username;
    private UserRole userRole;
    private String password_hash;


    public User(int id, String Username, String password_hash, UserRole UserRole) {
        this.id = id;
        this.username = Username;
        this.userRole = UserRole;
        this.password_hash = password_hash;
    }
    public User(String username, UserRole UserRole) {
        this.username = username;
        this.userRole = UserRole;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public UserRole getUserRole() {
        return userRole;
    }
    public String getPassword_hash() {
        return password_hash;
    }
    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

}
