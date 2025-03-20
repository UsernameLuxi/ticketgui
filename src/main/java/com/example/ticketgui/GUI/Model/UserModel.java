package com.example.ticketgui.GUI.Model;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.BLL.UserLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.util.List;

public class UserModel {
    ObservableList<User> users;
    UserLogic userLogic;

    public UserModel() throws Exception {
        users = FXCollections.observableArrayList();
        userLogic = new UserLogic();
        //users.addAll(userLogic.getAllUsers()); <- til dem som ikke skal bruge den skal ikke håndtere at der bliver loaded users fra DB
    }

    public void createUser(User user, String password) throws Exception {
        User u = userLogic.createUser(user, password);
        users.add(u);
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public List<User> getUsersDB() throws Exception {
        users.clear();
        List<User> ul = userLogic.getAllUsers();
        users.addAll(ul);
        return ul;
    }

    public void loadUsersDB() throws Exception {
        users.clear();
        users.addAll(userLogic.getAllUsers());
    }

    public void deleteUser(User user) throws Exception {
        userLogic.deleteUser(user);
        users.remove(user);
    }

    public User login(User user) throws Exception {
        return userLogic.loginUser(user);
    }
    public static void NewUserButtonHandling(String UserRole, Button newUser)  {
        switch (UserRole){
            case "EVENT_KOOORDINATOR":
                newUser.setVisible(false);
                break;
            case "ADMIN":
                newUser.setVisible(true);
                break;
        }
    }

    }
