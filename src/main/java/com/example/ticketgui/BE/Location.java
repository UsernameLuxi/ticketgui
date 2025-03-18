package com.example.ticketgui.BE;

public class Location {
    private int id;
    private int postalCode;
    private String street;

    public Location(int id, int postalCode, String street) {
        this.id = id;
        this.postalCode = postalCode;
        this.street = street;
    }

    public int getId() {
        return id;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }
}
