package com.example.ticketgui.BLL;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.BE.UserRole;
import com.example.ticketgui.BLL.util.IHashing;
import com.example.ticketgui.BLL.util.PasswordHasher;
import com.example.ticketgui.DAL.Interfaces.IUserAccess;
import com.example.ticketgui.DAL.UserDataAccess;

import java.util.List;

public class UserLogic implements ILogic {
    private IUserAccess userAccess;
    private IHashing hasher;
    public UserLogic() throws Exception {
        userAccess = new UserDataAccess();
        hasher = new PasswordHasher();
    }

    /**
     * Laver en bruger i databasen og hasher kodeordet
     * @param user Den bruger som skal laves med brugernavn og rolle
     * @param password Det password som skal hashes
     * @return returnere brugeren med id genereret af databasen
     */
    public User createUser(User user, String password) throws Exception {
        user.setPassword_hash(hasher.hashString(password));
        return userAccess.create(user);
    }

    public List<User> getAllUsers() throws Exception {
        return userAccess.getAll();
    }

    public void deleteUser(User user) throws Exception {
        userAccess.delete(user);
    }

    /**
     * Tjekker om brugerens password passer med det som blev indsendt.
     * Indsend et brugerobjekt med brugernavn og password(u-hashet) ->
     * giver den fulde bruger retur med rolle og id.
     */
    public User loginUser(User user) throws Exception {
        User dbUser = userAccess.getUserOfUsername(user.getUsername());
        if (dbUser == null)
            return null;

        if (hasher.compare(user.getPassword(), dbUser.getPassword())){
            return dbUser;
        }
        else
            return null;
    }

    public List<User> getAllEventKoordinators() throws Exception {
        return userAccess.getAllCoordinators();
    }

}
