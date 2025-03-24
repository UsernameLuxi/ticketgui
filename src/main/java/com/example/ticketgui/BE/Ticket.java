package com.example.ticketgui.BE;

public class Ticket {
    // null hvis ikke købt og først når den er købt skal den generes -> da salgnummeret først kan genereres der
    private String ID = null; // format: "<EventID>-<SalgNummer>" TODO : lav denne til qr kode ;)

    // event ting - som kan forkortes til ét objekt - men dette er for simplificering
    private String eventTitle;
    private String description;
    private String startDate;
    private String endDate;
    private Location location; // <- location.toString() - når den laves til pdf ik' ;)

    public Ticket(Event e){
        // get salg nummer og lav ID
        eventTitle = e.getName();
        description = e.getDescription();
        startDate = e.getDateTime();
        // endDate = e.getEndDate; <- ikke lavet endnu men den kommer
        location = e.getLocation();

    }

}
