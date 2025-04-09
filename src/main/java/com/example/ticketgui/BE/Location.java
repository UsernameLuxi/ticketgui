package com.example.ticketgui.BE;

public class Location {
    private int id;
    private int postalCode;
    private String street;
    private String city;

    public Location(int id, int postalCode, String street) {
        this.id = id;
        this.postalCode = postalCode;
        this.street = street;

        //setCity(getCity);
        this.city = "";
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

    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }

    @Override
    public String toString() {
        return street + ", " + postalCode + " " + city;
    }
}
