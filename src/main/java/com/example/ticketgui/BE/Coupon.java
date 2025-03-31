package com.example.ticketgui.BE;

public class Coupon {
    private int id;
    private String name; // eller titel - det kommer an på hvordan man ser på det
    private int price;
    private String expiryDate;
    private int eventID = 0;

    public Coupon(int id, String name, int price, String expiryDate, int eventID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expiryDate = expiryDate;
        this.eventID = eventID;
    }

    public Coupon(String name, int price, String expiryDate, int eventID) {
        this.name = name;
        this.price = price;
        this.expiryDate = expiryDate;
        this.eventID = eventID;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
}
