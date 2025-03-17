package com.example.ticketgui.GUI.Model;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.BLL.UserLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class UserModel {
    ObservableList<User> users;
    UserLogic userLogic;

    public UserModel() throws Exception {
        users = FXCollections.observableArrayList();
        userLogic = new UserLogic();
    }

    public void createUser(User user, String password) throws Exception {
        User u = userLogic.createUser(user, password);
        users.add(u);
    }
}
