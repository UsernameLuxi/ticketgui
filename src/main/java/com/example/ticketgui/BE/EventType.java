package com.example.ticketgui.BE;

public class EventType {
    private int id;
    private String name;

    public EventType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name;
    }
}
