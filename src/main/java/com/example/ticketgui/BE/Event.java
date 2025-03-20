package com.example.ticketgui.BE;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private int price;
    private String description;
    private String dateTime;
    private EventType eventType;
    private Location location;
    private List<User> eventKoordinators;

    public Event(int id, String name, int price, String description, String dateTime, EventType eventType, Location location) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.dateTime = dateTime;
        this.eventType = eventType;
        this.location = location;
        this.eventKoordinators = new ArrayList<>();
    }
    public Event(int id, String name){
        this.id = id;
        this.name = name;
        this.price = 0;
        this.description = "";
        this.dateTime = "";
        this.eventType = null;
        this.location = null;
        this.eventKoordinators = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Location getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }
    public int getPrice() {
        return price;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<User> getEventKoordinators() {
        return eventKoordinators;
    }
    public void setEventKoordinators(List<User> eventKoordinators) {
        this.eventKoordinators = eventKoordinators;
    }
}
