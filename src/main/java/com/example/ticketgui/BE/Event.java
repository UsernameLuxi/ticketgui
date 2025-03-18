package com.example.ticketgui.BE;

public class Event {
    private int id;
    private String name;
    private int price;
    private String description;
    private String dateTime;
    private EventType eventType;
    private Location location;

    public Event(int id, String name, int price, String description, String dateTime, EventType eventType, Location location) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.dateTime = dateTime;
        this.eventType = eventType;
        this.location = location;
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
}
