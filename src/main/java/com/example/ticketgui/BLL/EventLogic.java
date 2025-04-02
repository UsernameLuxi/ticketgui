package com.example.ticketgui.BLL;

import com.example.ticketgui.BE.Event;
import com.example.ticketgui.BE.EventType;
import com.example.ticketgui.BE.User;
import com.example.ticketgui.DAL.EventDataAccess;
import com.example.ticketgui.DAL.EventTypeDataAccess;
import com.example.ticketgui.DAL.Interfaces.IDataAccess;
import com.example.ticketgui.DAL.Interfaces.IEventDataAccess;

import java.util.List;

public class EventLogic {
    private IEventDataAccess eventDataAccess;
    private IDataAccess<EventType> eventTypeDataAccess;
    public EventLogic() {
        eventDataAccess = new EventDataAccess();
        eventTypeDataAccess = new EventTypeDataAccess();
    }


    public Event createEvent(Event event) throws Exception{
        return eventDataAccess.create(event);
    }

    public List<EventType> getEventTypes() throws Exception{
        return eventTypeDataAccess.getAll();
    }

    public void deleteEvent(Event event) throws Exception{
        eventDataAccess.delete(event);
    }

    public List<Event> getEventAccess(User user) throws Exception {
        return eventDataAccess.getEventAccess(user);
    }

    public List<Event> getAllEvents() throws Exception {
        return eventDataAccess.getAll();
    }
    public void updateEvent(Event event) throws Exception{
        eventDataAccess.update(event);
    }

    public int incrementSale(Event event) throws Exception{
        return eventDataAccess.incrementSale(event);
    }
}
