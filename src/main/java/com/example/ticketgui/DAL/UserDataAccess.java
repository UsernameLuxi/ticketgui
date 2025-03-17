package com.example.ticketgui.DAL;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.BE.UserRole;
import com.example.ticketgui.DAL.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDataAccess implements IUserAccess{
    private DBConnector db;
    public UserDataAccess() throws Exception {
        try{
            db = new DBConnector();
        }
        catch(Exception e){
            throw new Exception("Couldn't get a connection");
        }
    }
    // TODO : implement
    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    /**
     * Denne metode antager at brugerens password allerede er 'hashed'
     */
    // TODO : se om man kan lave en transaktion eller noget -> specifikt nede ved hvor man får id'et hvis nu det er at ja you know så man kan roll back i hvert fald kig på det
    public User create(User user) throws Exception{
        // 1-> username
        // 2 ->Password
        // 3 -> Role id-baseret på enum -> tjek om rolle kan forsage problemer
        String sql = "INSERT INTO [Users] (Username, Password, Role) VALUES (?,?,?)";
        try(Connection conn = new DBConnector().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword_hash());
            stmt.setInt(3, user.getUserRole().getId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                return new User(rs.getInt(1), user.getUsername(), user.getPassword_hash(), user.getUserRole());
            }
            else{
                throw new Exception("Couldn't get the generated key");
            }
        }
    }

    // TODO : implement
    @Override
    public User getById(int id) {
        return null;
    }

    // TODO : implement
    @Override
    public void update(User user) {

    }

    // TODO : implement
    @Override
    public void delete(User user) {

    }
}
