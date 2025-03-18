package com.example.ticketgui.DAL;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.BE.UserRole;
import com.example.ticketgui.DAL.Interfaces.IUserAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDataAccess implements IUserAccess {
    private DBConnector db;
    public UserDataAccess() throws Exception {
        try{
            db = new DBConnector();
        }
        catch(Exception e){
            throw new Exception("Couldn't get a connection");
        }
    }
    @Override
    public List<User> getAll() throws Exception {
        String sql = "SELECT ID, Username, Password, Role FROM Users";
        List<User> users = new ArrayList<User>();
        try(Connection conn = db.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), UserRole.getUserRole(rs.getInt(4)));
                users.add(u);
            }

        } catch (Exception e) {
            throw new Exception("Couldn't get users");
        }

        return users;
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
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getUserRole().getId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                return new User(rs.getInt(1), user.getUsername(), user.getPassword(), user.getUserRole());
            }
            else{
                throw new Exception("Couldn't get the generated key");
            }
        }
    }

    // TODO : implement
    @Override
    public User getById(int id) throws Exception {
        String sql = "SELECT ID, Username, Password, Role FROM Users WHERE ID = ?";
        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), UserRole.getUserRole(rs.getInt(4)));
            else
                return null;
        }
        catch(Exception e){
            throw new Exception("Couldn't get the user");
        }
    }

    // TODO : implement
    @Override
    public void update(User user) {

    }

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

    @Override
    public User getUserOfUsername(String username) throws Exception{
        String sql = "SELECT ID FROM [Users] WHERE Username = ?";

        try(Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return getById(rs.getInt(1)); // TODO : måske indsæt egen ting så 2 sql kald ikke forekommer
            }
            else
                return null;
        } catch (Exception e) {
            throw new Exception("Couldn't fetch password");
        }
    }
}
