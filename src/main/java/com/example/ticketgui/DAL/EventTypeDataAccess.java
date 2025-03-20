package com.example.ticketgui.DAL;

import com.example.ticketgui.BE.EventType;
import com.example.ticketgui.DAL.Interfaces.IDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventTypeDataAccess implements IDataAccess<EventType> {
    @Override
    public List<EventType> getAll() throws Exception {
        String sql = "SELECT ID, Name FROM Category;";
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<EventType> eventTypes = new ArrayList<>();
            while(rs.next()) {
                EventType eventType = new EventType(rs.getInt(1), rs.getString(2));
                eventTypes.add(eventType);
            }
            return eventTypes;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public EventType create(EventType eventType) throws Exception {
        return null;
    }

    @Override
    public EventType getById(int id) throws Exception {
        return null;
    }

    @Override
    public void update(EventType eventType) throws Exception {

    }

    @Override
    public void delete(EventType eventType) throws Exception {

    }
}
