package com.example.ticketgui.GUI.Model;

import com.example.ticketgui.BE.Location;
import com.example.ticketgui.BLL.LocationLogic;

import java.util.ArrayList;
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

    public Location createLocation(Location location) throws Exception {
        Location loc = locLogic.createLocation(location);
        if (locations.containsKey(loc.getPostalCode())){
            locations.get(loc.getPostalCode()).add(loc);
        }
        else{
            List<Location> l = new ArrayList<>();
            l.add(loc);
            locations.put(loc.getPostalCode(), l);
        }
        return loc;
    }
}
