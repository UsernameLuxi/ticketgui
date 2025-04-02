package com.example.ticketgui.GUI.Model;

import com.example.ticketgui.BE.Event;
import com.example.ticketgui.BE.EventType;
import com.example.ticketgui.BE.User;
import com.example.ticketgui.BE.UserRole;
import com.example.ticketgui.BLL.EventLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDate;
import java.util.List;

public class EventModel {
    private EventLogic logic;
    private ObservableList<Event> events;
    public EventModel() {
        this.logic = new EventLogic();
        events = FXCollections.observableArrayList();
    }


    public void createEvent(Event event) throws Exception {
        Event e = logic.createEvent(event);
        events.add(e);
    }

    public List<EventType> getEventTypes() throws Exception {
        return logic.getEventTypes();
    }

    public void deleteEvent(Event e) throws Exception {
        logic.deleteEvent(e);
        events.remove(e);
    }

    public ObservableList<Event> getEventsForUser(User user) throws Exception {
        events.clear();
        if (user.getUserRole() == UserRole.ADMIN){
            events.addAll(logic.getAllEvents());
            return events;
        }
        events.addAll(logic.getEventAccess(user));
        return events;
    }

    /**
     * Gemmer ændringerne af et event i databasen
     * @param event Kopi af det event som skal have ændringerne -> ikke send originalen
     *             - hvis nu at der kommer en exception/fejl.
     */
    public void updateEvent(Event event) throws Exception {
        logic.updateEvent(event);
        // opdatere eventet i cachen -> hvis altså ikke at logic kaster en exception ;)
        for (Event e : events) {
            if (e.getId() == event.getId()) {
                events.remove(e);
                events.add(event);
                break;
            }
        }
    }

    public int getEventsForThisMonth(){
        LocalDate ld = LocalDate.now();
        int month = ld.getMonthValue();
        int year = ld.getYear();
        int counter = 0;
        for (Event e : events) {
            try {
                String[] date = e.getDateTime().split(" ")[0].split("-");
                int yearE = Integer.parseInt(date[2]);
                int monthE = Integer.parseInt(date[1]);
                if (monthE == month && yearE == year) {
                    counter++;
                }
            } catch (Exception _) {}
        }

        return counter;
    }

    public int getEventsForThisWeek(){
        int counter = 0;
        LocalDate ld = LocalDate.now();
        int year = ld.getYear();

        // når man regner ud hvor mange dage der er tilbage så skal man have dagen på ugen og trække det fra 7
        // derefter skal man lægge det til den nuværende dag for at få til og med søndag-events
        int weekDays = 7 - ld.getDayOfWeek().getValue(); // generere 1 - 7 -> søndag = 7 hvis det er 7 så er der 0 tillægsdage
        int currentdayOfYear = ld.getDayOfYear();
        for (Event e : events) {
            try {
                String[] date = e.getDateTime().split(" ")[0].split("-");
                int yearE = Integer.parseInt(date[2]);
                int monthE = Integer.parseInt(date[1]);
                int dayE = Integer.parseInt(date[0]);
                int dayOfYearE = LocalDate.of(yearE, monthE, dayE).getDayOfYear();
                // 1. -> samme år. 2. -> at det er nuværende day eller større. 3. -> det er mindre end nuværende dag + resten af ugens dage
                if (yearE == year && dayOfYearE >= currentdayOfYear && dayOfYearE <= currentdayOfYear + weekDays) {
                    counter++;
                }
            } catch (Exception _) {}
        }
        return counter;
    }
}
