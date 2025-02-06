package com.example.ticketgui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private AnchorPane dataPane;
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane sideMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void test(Stage primaryStage) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(dataPane.getLayoutX());
        scrollPane.setLayoutY(dataPane.getLayoutY());
        scrollPane.setContent(dataPane);
        scrollPane.setPrefViewportHeight(dataPane.getHeight());
        scrollPane.setPrefViewportWidth(dataPane.getWidth());
        double length = Stage.getWindows().getFirst().getWidth() - sideMenu.getWidth();
        scrollPane.setPrefSize(length, dataPane.getHeight());
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        root.getChildren().add(scrollPane);
    }

    // erhmmm ja... man kunne også bare give hvert pane et fxId og .getChildren() - hilsen Casper -> but also made by Casper... træls
    public void initializeComponents(){

    }
}