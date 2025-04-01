package com.example.ticketgui.BLL.util;
import com.example.ticketgui.BE.Event;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;

public class SearchFilter{
    public static List<Event> searchEvents(List<Event> events, String searchWord) {
        List<Event> searchEvents = new ArrayList<>();
        for(Event event : events) {
            // sammenlinger filmens titel med søgetermet
            if (event.getName().toLowerCase().contains(searchWord.toLowerCase())) {
                searchEvents.add(event);
            }
        }
        return searchEvents;
    }

}
