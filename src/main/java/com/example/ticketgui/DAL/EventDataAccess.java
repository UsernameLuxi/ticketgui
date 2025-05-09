package com.example.ticketgui.DAL;

import com.example.ticketgui.BE.*;
import com.example.ticketgui.DAL.Interfaces.IEventDataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDataAccess implements IEventDataAccess {
    LocationDataAccess locDataAccess = new LocationDataAccess();
    @Override
    public List<Event> getAll() throws Exception {
        String sql = "SELECT Events.ID, Events.Name, Events.Price, Events.Type, Category.Name, " +
                "Events.DateTime, Events.Location, Location.PostNummer, Location.Vej, Cities.CityName, Events.Description, Events.EndDateTime " +
                "FROM Events " +
                "INNER JOIN Category ON Events.Type = Category.ID " +
                "INNER JOIN Location ON Events.Location = Location.ID " +
                "INNER JOIN Cities ON Location.CityID = Cities.ID;";

        DBConnector dbConnector = new DBConnector();
        try(Connection conn = dbConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            List<Event> events = new ArrayList<>();
            Map<Integer, List<User>> eventUsers = eventUsers();
            while(rs.next()) {
                events.add(makeEvent(rs, eventUsers.get(rs.getInt(1))));
            }
            return events;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private Event makeEvent(ResultSet rs, List<User> eventUsers) throws SQLException {
        // create event
        Event e = new Event(rs.getInt("ID"), rs.getString("Name"));
        e.setPrice(rs.getInt("Price"));
        e.setEventType(new EventType(rs.getInt("Type"), rs.getString(5)));
        e.setDateTime(rs.getString("DateTime"));
        e.setLocation(new Location(rs.getInt("Location"), rs.getInt("PostNummer"), rs.getString("Vej")));
        e.getLocation().setCity(rs.getString("CityName"));
        e.setDescription(rs.getString("Description"));
        e.setEndDateTime(rs.getString("EndDateTime"));
        e.setEventKoordinators(eventUsers);
        return e;
    }

    private Map<Integer, List<User>> eventUsers() throws Exception {
        Map<Integer, List<User>> eventUsers = new HashMap<>();
        String sql = "SELECT EventID, UserID, Users.Username, Users.Role FROM EventAssignment " +
                "INNER JOIN Users ON EventAssignment.UserID = Users.ID " +
                "ORDER BY UserID;";
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            User tempU = null;
            while(rs.next()) {
                if (tempU == null || tempU.getId() != rs.getInt(2)){
                    tempU = new User(rs.getInt(2), rs.getString(3), "####", UserRole.getUserRole(rs.getInt(4)));

                }
                boolean newE = !eventUsers.containsKey(rs.getInt(1));
                if (newE){
                    eventUsers.put(rs.getInt(1), new ArrayList<>());
                    eventUsers.get(rs.getInt(1)).add(tempU);
                }
                else
                    eventUsers.get(rs.getInt(1)).add(tempU);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return eventUsers;
    }

    @Override
    public Event create(Event event) throws Exception {
        String sql = "INSERT INTO Events (Name, Price, Type, DateTime, Location, Description, EndDateTime) Values (?, ?, ?, ?, ?, ?, ?)";
        DBConnector db = new DBConnector();
        if (event.getLocation().getId() == -1){
            // laver lokationen i databasen hvis den ikke eksistere
            event.setLocation(locDataAccess.create(event.getLocation()));
        }

        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, event.getName());
            ps.setInt(2, event.getPrice());
            ps.setInt(3, event.getEventType().getId());
            ps.setString(4, event.getDateTime());
            ps.setInt(5, event.getLocation().getId());
            ps.setString(6, event.getDescription());
            ps.setString(7, event.getEndDateTime());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                int id = rs.getInt(1);
                Event e = new Event(id, event.getName(), event.getPrice(), event.getDescription(), event.getDateTime(), event.getEventType(), event.getLocation());
                e.setEventKoordinators(event.getEventKoordinators());
                addUsersToEvent(e, event.getEventKoordinators());
                return e;
            }

        }
        catch (Exception e) {
            throw new Exception("Couldn't create event");
        }
        return null;
    }

    public void addUsersToEvent(Event event, List<User> users) throws Exception {
        String sql = "INSERT INTO EventAssignment (EventID, UserID) Values (?, ?)";
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, event.getId()); // konstant
            for (User u : users){
                ps.setInt(2, u.getId());
                ps.addBatch();
            }
            ps.executeBatch(); // commit all numbers ;)

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    // TODO : implement
    @Override
    public Event getById(int id) throws Exception {
        return null;
    }

    @Override
    public void update(Event event) throws Exception {
        String sqlUp = "UPDATE Events SET Name = ?, Price = ?, Type = ?, DateTime = ?, Location = ?, Description = ?, EndDateTime = ?  WHERE ID = ?;";
        String sqlRemUsers = "DELETE FROM EventAssignment WHERE EventID = ?;";
        String sqlReAddUsers = "INSERT INTO EventAssignment (EventID, UserID) Values (?, ?)"; // måske kunne man finde -
        // en måde at genbruge add users på fra den anden hvor transaktionerne stadig er intakte
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection()) {
            try(PreparedStatement psUp = conn.prepareStatement(sqlUp);
                PreparedStatement psRemUsers = conn.prepareStatement(sqlRemUsers);
                PreparedStatement psReAddUsers = conn.prepareStatement(sqlReAddUsers))
            {
                conn.setAutoCommit(false); // start transaktion
                // fjern brugere
                psRemUsers.setInt(1, event.getId());
                psRemUsers.executeUpdate();

                // tilføje brugere igen
                psReAddUsers.setInt(1, event.getId()); // konstant
                for (User u : event.getEventKoordinators()){
                    psReAddUsers.setInt(2, u.getId());
                    psReAddUsers.addBatch();
                }
                psReAddUsers.executeBatch();

                // opdatere lokationen TODO : nok ikke nødvendig længere
                if (event.getLocation().getId() == -1){
                    // laver lokationen i databasen hvis den ikke eksistere
                    event.setLocation(locDataAccess.create(event.getLocation()));
                }


                // opdatere resten af informationerne
                psUp.setString(1, event.getName());
                psUp.setInt(2, event.getPrice());
                psUp.setInt(3, event.getEventType().getId());
                psUp.setString(4, event.getDateTime());
                psUp.setInt(5, event.getLocation().getId());
                psUp.setString(6, event.getDescription());
                psUp.setString(7, event.getEndDateTime());
                psUp.setInt(8, event.getId());
                psUp.executeUpdate();

                conn.commit(); // committer ændringer
            } catch (Exception e) {
                conn.rollback();
                throw new Exception(e.getMessage());
            }
            finally{
                // for at være sikker på at den bliver sat tilbage -> hvis nu den fejler osv.
                conn.setAutoCommit(true);
            }
        }
        catch (Exception e) {
            throw new Exception("Couldn't update event " + e.getMessage());
        }

    }

    @Override
    public void delete(Event event) throws Exception {
        String sql = "DELETE FROM Events WHERE ID = ?";
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, event.getId());
            ps.executeUpdate();
        }
        catch (Exception e) {
            throw new Exception("Couldn't delete event: " + e.getMessage());
        }

    }

    @Override
    public List<Event> getEventAccess(User user) throws Exception {
        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT Events.ID, Events.Name, Events.Price, Events.Type, Category.Name, Events.DateTime, Events.Location, Location.PostNummer, Location.Vej, Cities.CityName, Events.Description, Events.EndDateTime" +
                " FROM EventAssignment" +
                " INNER JOIN Events ON EventAssignment.EventID = Events.ID" +
                " INNER JOIN Category ON Events.Type = Category.ID" +
                " INNER JOIN Location ON Events.Location = Location.ID" +
                " INNER JOIN Cities ON Location.CityID = Cities.ID" +
                " WHERE UserID = ?;";
        DBConnector db = new DBConnector();
        try(Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            Map<Integer, List<User>> eventUsers = eventUsers();
            while (rs.next()){
                eventList.add(makeEvent(rs, eventUsers.get(rs.getInt(1))));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Couldn't get event list: " + e.getMessage());
        }
        return eventList;
    }

    @Override
    public int incrementSale(Event event) throws Exception {
        String updateSql = "UPDATE Events SET Sales = Sales + 1 WHERE ID = ?";
        String selectSql = "SELECT Sales FROM Events WHERE ID = ?";
        DBConnector db = new DBConnector();

        try (Connection conn = db.getConnection();
             PreparedStatement updatePs = conn.prepareStatement(updateSql);
             PreparedStatement selectPs = conn.prepareStatement(selectSql)) {

            updatePs.setInt(1, event.getId());
            int rowsAffected = updatePs.executeUpdate();

            if (rowsAffected > 0) {
                selectPs.setInt(1, event.getId());
                try (ResultSet rs = selectPs.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("Sales");
                    }
                }
            } else {
                throw new Exception("No rows updated, event not found");
            }
        } catch (Exception e) {
            throw new Exception("Couldn't increment sales: " + e.getMessage());
        }

        throw new Exception("Sales value not found");
    }
}
