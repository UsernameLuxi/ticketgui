package com.example.ticketgui.BLL;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.BLL.util.IHashing;
import com.example.ticketgui.BLL.util.PasswordHasher;
import com.example.ticketgui.DAL.IUserAccess;
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
}
