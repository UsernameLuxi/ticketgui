package com.example.ticketgui.DAL;

import com.example.ticketgui.BE.Event;
import com.example.ticketgui.BE.EventType;
import com.example.ticketgui.BE.Location;
import com.example.ticketgui.BE.User;
import com.example.ticketgui.DAL.Interfaces.IEventDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventDataAccess implements IEventDataAccess {
    LocationDataAccess locDataAccess = new LocationDataAccess();
    // TODO : implement
    @Override
    public List<Event> getAll() throws Exception {
        return List.of();
    }

    @Override
    public Event create(Event event) throws Exception {
        String sql = "INSERT INTO Events (Name, Price, Type, DateTime, Location, Description) Values (?, ?, ?, ?, ?, ?)";
        DBConnector db = new DBConnector();
        if (event.getLocation().getId() == -1){
            // laver lokationen i databasen hvis den ikke eksistere
            event.setLocation(locDataAccess.create(event.getLocation()));
        }

        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, event.getName());
            ps.setInt(2, event.getPrice());
            ps.setInt(3, event.getEventType().getId());
            ps.setString(4, event.getDateTime());
            ps.setInt(5, event.getLocation().getId());
            ps.setString(6, event.getDescription());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                int id = rs.getInt(1);
                return new Event(id, event.getName(), event.getPrice(), event.getDescription(), event.getDateTime(), event.getEventType(), event.getLocation());
            }

        }
        catch (Exception e) {
            throw new Exception("Couldn't create event");
        }
        return null;
    }

    // TODO : implement
    @Override
    public Event getById(int id) throws Exception {
        return null;
    }

    // TODO : implement
    @Override
    public void update(Event event) throws Exception {

    }

    // TODO : implement
    @Override
    public void delete(Event event) throws Exception {
        String sql = "DELETE FROM Events WHERE ID = ?";
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, event.getId());
            ps.executeUpdate();
        }
        catch (Exception e) {
            throw new Exception("Couldn't delete event: " + e.getMessage());
        }

    }

    @Override
    public List<Event> getEventAccess(User user) throws Exception {
        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT Events.ID, Events.Name, Events.Price, Events.Type, Category.Name, Events.DateTime, Events.Location, Location.PostNummer, Location.Vej, Events.Description" +
                " FROM EventAssignment" +
                " INNER JOIN Events ON EventAssignment.EventID = Events.ID" +
                " INNER JOIN Category ON Events.Type = Category.ID" +
                " INNER JOIN Locaiton ON Events.Location = Location.ID" +
                " WHERE UserID = ?;";
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            // TODO : test lige om det virker
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Event e = new Event(rs.getInt("Events.ID"), rs.getString("Events.Name"));
                e.setPrice(rs.getInt("Events.Price"));
                e.setEventType(new EventType(rs.getInt("Events.Type"), rs.getString("Category.Name")));
                e.setDateTime(rs.getString("Events.DateTime"));
                e.setLocation(new Location(rs.getInt("Events.Location"), rs.getInt("Location.PostNummer"), rs.getString("Location.Vej")));
                e.setDescription(rs.getString("Events.Description"));
                eventList.add(e);
            }
        }
        catch (Exception e) {
            throw new Exception("Couldn't get event list");
        }
        return eventList;
    }
}
