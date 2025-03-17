package com.example.ticketgui.GUI.Model;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.BLL.UserLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.List;

public class UserModel {
    ObservableList<User> users;
    UserLogic userLogic;

    public UserModel() throws Exception {
        users = FXCollections.observableArrayList();
        userLogic = new UserLogic();
        users.addAll(userLogic.getAllUsers());
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
}
