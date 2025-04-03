package com.example.ticketgui.BE;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    // null hvis ikke købt og først når den er købt skal den generes -> da salgnummeret først kan genereres der
    private String ID = null; // format: "<EventID>-<SalgNummer>" TODO : lav denne til qr kode ;)

    // event ting - som kan forkortes til ét objekt - men dette er for simplificering
    private String eventTitle;
    private String description;
    private String startDate;
    private String endDate;
    private Location location; // <- location.toString() - når den laves til pdf ik' ;)
    private List<Coupon> couponList;

    public Ticket(Event e){
        // get salg nummer og lav ID
        eventTitle = e.getName();
        description = e.getDescription();
        startDate = e.getDateTime();
        endDate = e.getEndDateTime();
        location = e.getLocation();
        couponList = new ArrayList<>();
    }


    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }
}
