package com.example.ticketgui.BLL;

import com.example.ticketgui.BE.Event;
import com.example.ticketgui.DAL.EventDataAccess;
import com.example.ticketgui.DAL.Interfaces.IEventDataAccess;

public class EventLogic {
    private IEventDataAccess eventDataAccess;
    public EventLogic() {
        eventDataAccess = new EventDataAccess();
    }


    public Event createEvent(Event event) throws Exception{
        return eventDataAccess.create(event);
    }
}
