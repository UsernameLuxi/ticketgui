package com.example.ticketgui.BE;

public class User{
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
    public User (String username, String password){
        this.username = username;
        this.password_hash = password;
        this.userRole = UserRole.EVENT_KOORDINATOR;
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
    public String getPassword() {
        return password_hash;
    }
    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    @Override
    public String toString(){
        return username;
    }
}
