package com.example.ticketgui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController {
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
        width -= 15; // hold dig fra siden mand!
        height -= 30;
        for (Region n : windowItems.keySet()) {
            //n.resize(width * windowItems.get(n).get(0), height * windowItems.get(n).get(1));
            n.setPrefWidth(width * windowItems.get(n).get(0));
            n.setLayoutX(width * windowItems.get(n).get(2));

            n.setPrefHeight(height * windowItems.get(n).get(1));
            n.setLayoutY(height * windowItems.get(n).get(3));

            // TODO : skal have dette?
            if (n instanceof Label label)
            {
                double orgSize = label.getStyleClass().contains("bigText") ? 32 : label.getStyleClass().contains("normalText") ? 24 : 12;
                double newValueAVG = (orgSize * (width / 1920) + orgSize * (height / 1080)) / 2;
                Font newFont = new Font(newValueAVG);
                label.setFont(newFont);
            }
        }
    }
}
