package com.example.ticketgui.DAL.Interfaces;

import com.example.ticketgui.BE.Event;
import com.example.ticketgui.BE.User;

import java.util.List;

public interface IEventDataAccess extends IDataAccess<Event> {
    List<Event> getEventAccess(User user) throws Exception;
    int incrementSale(Event event) throws Exception;
    int getSales(Event event) throws Exception;
}
