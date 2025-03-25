package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.BE.UserRole;
import com.example.ticketgui.GUI.ControllerManager;
import com.example.ticketgui.GUI.Model.UserModel;
import com.example.ticketgui.GUI.util.ShowAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableView<User> tblBrugere;
    @FXML
    private TableColumn<User, String> colBrugernavn;
    @FXML
    private TableColumn<User, UserRole> colRolle;


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


        // udfyld kolonnerne
        colBrugernavn.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRolle.setCellValueFactory(new PropertyValueFactory<>("userRole"));

        try{
            userModel = new UserModel();
            userModel.loadUsersDB();
            tblBrugere.setItems(userModel.getUsers());
        }
        catch (Exception e){
            ShowAlerts.displayMessage("Error", "Could not load users\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
        // laver rollerne til menutingen
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
            // TODO : indsæt exception-håndtering -> label
        }
    }

    @FXML
    private void deleteUser(ActionEvent actionEvent) {
        User selctedUser = tblBrugere.getSelectionModel().getSelectedItem();
        if (selctedUser != null){
            // TODO : lav et tjek at brugeren ikke sletter sig selv ;)
            // TODO : måske lidt verificering af brugerens valg
            try{
                userModel.deleteUser(selctedUser);
            }
            catch (Exception e){
                // TODO : der skete noget med sletningen - vis på label
            }
        }
        else{
            // TODO : null giv måske en advarsel eller noget information i form af label ?
        }
    }
}
