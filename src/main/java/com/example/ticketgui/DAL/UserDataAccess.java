package com.example.ticketgui.DAL;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.BE.UserRole;
import com.example.ticketgui.DAL.DBConnector;

import java.sql.*;
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
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
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
    public void delete(User user) throws Exception {
        String sql = "DELETE FROM [Users] WHERE ID = ?";
        try(Connection conn = new DBConnector().getConnection()){
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                // Jeg er sikker på at transaktioner ikke er nødvendig her meeeen hvis der sker en fejl
                // så er brugeren stadig i live ;)
                conn.setAutoCommit(false);
                stmt.setInt(1, user.getId());
                stmt.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            }
            catch(Exception e){
                conn.rollback();
                throw new Exception("Couldn't delete user");
            }

        } catch (Exception e) {
            throw new Exception("Couldn't delete user");
        }
    }
}
