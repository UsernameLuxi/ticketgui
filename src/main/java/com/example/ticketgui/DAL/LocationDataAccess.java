package com.example.ticketgui.DAL;

import com.example.ticketgui.BE.Location;
import com.example.ticketgui.DAL.Interfaces.IDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationDataAccess implements IDataAccess<Location> {

    @Override
    public List<Location> getAll() throws Exception {
        String sql = """
            SELECT Location.ID, PostNummer, Vej, Cities.CityName FROM Location
            INNER JOIN Cities ON Location.CityID = Cities.ID;
            """;
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Location> list = new ArrayList<Location>();
            while(rs.next()) {
                Location loc = new Location(rs.getInt(1), rs.getInt(2), rs.getString(3));
                loc.setCity(rs.getString(4));
                list.add(loc);
            }
            return list;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Location create(Location location) throws Exception {
        // TODO : når det er så kan man godt undlade at gemme Postnummer i Locations tabellen -> fordi den er gemt ved cities tabellen
        String sql = "INSERT INTO Location (PostNummer, Vej, CityID) Values (?, ?, ?)";
        String getCityID = "SELECT ID, CityName FROM Cities WHERE PostalCode = ?";
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement getCity = conn.prepareStatement(getCityID)) {
            ps.setInt(1, location.getPostalCode());
            ps.setString(2, location.getStreet());

            // set by
            getCity.setInt(1, location.getPostalCode());
            ResultSet rscity = getCity.executeQuery();
            if (rscity.next()) {
                ps.setInt(3, rscity.getInt(1));
            }
            else
                ps.setNull(3, java.sql.Types.INTEGER);

            // udfør
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                Location loc = new Location(rs.getInt(1), location.getPostalCode(), location.getStreet());
                loc.setCity(rscity.getString(2));
                return loc;
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
