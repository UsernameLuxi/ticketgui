package com.example.ticketgui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController implements IController {
    private IController mainController;
    private ControllerManager manager;
    private boolean abool = false;
    private IController root;
    private Map<Region, List<Double>> windowItems = new HashMap<>();
    @FXML
    private AnchorPane loginPane;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField textPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;


    // samme koncept som i main controlleren
    public void initializeComponents(double width, double height) {
        List<Region> windowContent = new ArrayList<>();
        windowContent.add(loginPane);
        // fordi der ikke er nogle sub-panes - så kan dette gøres ;)
        for (Node n : loginPane.getChildren()){
            if (n instanceof Region r){
                windowContent.add(r);
            }
        }

        fillMap(windowContent, width, height);

    }

    // static? - return things?
    private void fillMap(List<Region> items, double width, double height) {
        for (Region item : items) {
            windowItems.put(item, new ArrayList<>(){{
                add(item.getWidth() / width);
                add(item.getHeight() / height);
                add(item.getLayoutX() / width);
                add(item.getLayoutY() / height);}}
            );
        }
    }

    public void resizeItems(double width, double height){
        resizeItems(windowItems, null, width, height);
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
    private void login(ActionEvent actionEvent) {
        // TODO : refactor
        try{
            manager.setStage("MainWindow.fxml");
        }
        catch (IOException e){
            // something
        }

    }

    @Override
    public void reload(){
        try{
            manager.setStage("Login Screen.fxml");
        }
        catch (IOException e){
            // something
        }
    }

    @Override
    public void setManager(ControllerManager manager) {
        this.manager = manager;
    }

    private void setPane(String file) throws IOException {
        loginPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(file));
        AnchorPane pane = loader.load();
        pane.setPrefHeight(loginPane.getWidth());
        pane.setPrefWidth(loginPane.getHeight());
        mainController = loader.getController();

        // husk gør dette kun en gang - ikke flere
        mainController.initializeComponents(1920, 1080);
        mainController.resizeItems(loginPane.getWidth(), loginPane.getHeight());
        mainController.setControllerRoot(this);
        if (!abool){
            loginPane.widthProperty().addListener((observable, oldValue, newValue) -> {mainController.resizeItems(newValue.doubleValue(), loginPane.getHeight());});
            loginPane.heightProperty().addListener(((observable, oldValue, newValue) -> {mainController.resizeItems(loginPane.getWidth(), newValue.doubleValue());}));
            abool = true;
        }
        loginPane.getChildren().addAll(pane.getChildren());

    }
}
