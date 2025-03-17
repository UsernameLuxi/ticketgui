package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.BE.UserRole;
import com.example.ticketgui.GUI.ControllerManager;
import com.example.ticketgui.GUI.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController extends Controller {
    private ControllerManager manager;
    private IController root;
    private Map<Region, List<Double>> windowItems = new HashMap<>();
    private Map<ImageView, List<Double>> imageViews = new HashMap<>();
    UserModel userModel;
    @FXML
    private AnchorPane newUserPane;
    @FXML
    private ImageView imgBtnBack;
    @FXML
    private TextField txtUsername;
    @FXML
    private SplitMenuButton smbUserRole;
    @FXML
    private TextField txtPassword;


    @Override
    public void initializeComponents(double width, double height) {
        List<Region> windowContent = new ArrayList<>();
        // fordi der ikke er nogle sub-panes - så kan dette gøres ;)
        for (Node n : newUserPane.getChildren()){
            if (n instanceof Region r){
                windowContent.add(r);
            }
        }

        imageViews.put(imgBtnBack, new ArrayList<>(){{
            add(imgBtnBack.getFitWidth() / width);
            add(imgBtnBack.getFitHeight() / height);
            add(imgBtnBack.getLayoutX() / width);
            add(imgBtnBack.getLayoutY() / height);}});

        fillMap(windowContent, width, height);

        try{
            userModel = new UserModel();
        }
        catch (Exception e){
            // TODO : indsæt håndtering hvis man ikke kan lave modellen
        }
        // lav rollerne til menutingen
        List<MenuItem> items = new ArrayList<>();
        for (UserRole ur : UserRole.values()) {
            MenuItem mi = new MenuItem(ur.name());
            mi.setOnAction(event -> {
                MenuItem mt = (MenuItem) event.getSource();
                smbUserRole.setText(mt.getText());
            });
            items.add(mi);
        }
        smbUserRole.getItems().clear();
        smbUserRole.getItems().addAll(items);

    }

    public void fillMap(List<Region> items, double width, double height){
        for (Region item : items) {
            windowItems.put(item, new ArrayList<>(){{
                add(item.getPrefWidth() / width);
                add(item.getPrefHeight() / height);
                add(item.getLayoutX() / width);
                add(item.getLayoutY() / height);}}
            );
        }
    }

    @Override
    public void resizeItems(double width, double height) {
        resizeItems(windowItems, imageViews, width, height);

    }

    @Override
    public void setManager(ControllerManager manager) {
        this.manager = manager;
    }

    @Override
    public void setControllerRoot(IController controller) {
        root = controller;
    }

    @FXML
    private void loadMain(ActionEvent event) {
        root.reload();
    }

    @FXML
    private void createUser(ActionEvent actionEvent) {
        User u = new User(txtUsername.getText(), UserRole.getRoleByString(smbUserRole.getText()));
        try{
            userModel.createUser(u, txtPassword.getText().trim());
            txtUsername.clear();
            txtPassword.clear();
        }
        catch (Exception e){
            // TODO : indsæt exception-håndtering
        }
    }
}
