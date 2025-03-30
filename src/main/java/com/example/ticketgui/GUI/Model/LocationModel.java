package com.example.ticketgui.GUI.Model;

import com.example.ticketgui.BE.Location;
import com.example.ticketgui.BLL.LocationLogic;

import java.util.List;
import java.util.Map;

public class LocationModel {
    private Map<Integer, List<Location>> locations;
    private LocationLogic locLogic;

    public LocationModel(){
        locLogic = new LocationLogic();
    }

    public Map<Integer, List<Location>> getLocations() throws Exception {
        if (locations == null) {
            locations = locLogic.getLocations();
        }
        return locations;
    }

    public void loadLocations() throws Exception {
        locations = locLogic.getLocations();
    }
}
