package com.example.ticketgui.BLL;

import com.example.ticketgui.BE.Location;
import com.example.ticketgui.DAL.LocationDataAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationLogic {
    private LocationDataAccess locationDataAccess = new LocationDataAccess();
    public LocationLogic(){
        locationDataAccess = new LocationDataAccess();
    }

    public Map<Integer, List<Location>> getLocations() throws Exception {
        Map<Integer, List<Location>> locations = new HashMap<>();
        List<Location> locList = locationDataAccess.getAll();

        for(Location loc : locList){
            if (locations.containsKey(loc.getPostalCode())){
                locations.get(loc.getPostalCode()).add(loc);
            }
            else{
                List<Location> l = new ArrayList<>();
                l.add(loc);
                locations.put(loc.getPostalCode(), l);
            }
        }
        return locations;
    }
}
