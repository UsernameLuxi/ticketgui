package com.example.ticketgui.BE;

public class User {
    private int id;
    private String Username;
    private UserRole UserRole;
    // TODO : password ?


    public User(int id, String Username, UserRole UserRole) {
        this.id = id;
        this.Username = Username;
        this.UserRole = UserRole;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return Username;
    }
    public UserRole getUserRole() {
        return UserRole;
    }

}
