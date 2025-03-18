package com.example.ticketgui.GUI.Model;

import com.example.ticketgui.BE.Event;
import com.example.ticketgui.BE.EventType;
import com.example.ticketgui.BLL.EventLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class EventModel {
    private EventLogic logic;
    private ObservableList<Event> events;
    public EventModel() {
        this.logic = new EventLogic();
        events = FXCollections.observableArrayList();
    }


    public void createEvent(Event event) throws Exception {
        Event e = logic.createEvent(event);
        events.add(e);
    }

    public List<EventType> getEventTypes() throws Exception {
        return logic.getEventTypes();
    }

    public void deleteEvent(Event e) throws Exception {
        logic.deleteEvent(e);
        events.remove(e);
    }
}
