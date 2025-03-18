package com.example.ticketgui.DAL;

import com.example.ticketgui.BE.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class LocationDataAccess implements IDataAccess<Location> {
    // TODO : implement
    @Override
    public List<Location> getAll() throws Exception {
        return List.of();
    }

    @Override
    public Location create(Location location) throws Exception {
        String sql = "INSERT INTO Location (PostNummer, Vej) Values (?, ?)";
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, location.getPostalCode());
            ps.setString(2, location.getStreet());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return new Location(rs.getInt(1), location.getPostalCode(), location.getStreet());
            }
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return null;
    }

    // TODO : implement
    @Override
    public Location getById(int id) throws Exception {
        return null;
    }

    // TODO : implement
    @Override
    public void update(Location location) throws Exception {

    }

    // TODO : implement
    @Override
    public void delete(Location location) throws Exception {

    }
}
