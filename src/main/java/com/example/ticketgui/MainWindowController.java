package com.example.ticketgui;

import javafx.css.StyleClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class MainWindowController implements Initializable {

    @FXML
    private AnchorPane dataPane;
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane sideMenu;
    @FXML
    private AnchorPane menuBar;
    @FXML
    private AnchorPane userInformation;
    @FXML
    private Label lblUserName;
    @FXML
    private Label lblUserRole;
    @FXML
    private ImageView imgUserImage;
    @FXML
    private ImageView imgLogout;
    @FXML
    private Label lblMenuTitle;
    @FXML
    private AnchorPane newEvent;
    @FXML
    private ImageView imgNewEvent;
    @FXML
    private Label lblNewEventlbl;
    @FXML
    private AnchorPane newUser;
    @FXML
    private ImageView imgNewUser;
    @FXML
    private Label lblNewUser;
    @FXML
    private AnchorPane manageCupons;
    @FXML
    private ImageView imgManageCupons;
    @FXML
    private Label lblManageCupons;
    @FXML
    private AnchorPane data1;
    @FXML
    private Label lblData1Title;
    @FXML
    private Label lblData1data;
    @FXML
    private AnchorPane data2;
    @FXML
    private Label lvlData2Title;
    @FXML
    private Label lblData2data;
    @FXML
    private AnchorPane data3;
    @FXML
    private Label lblData3Title;
    @FXML
    private Label lblData3data;
    @FXML
    private AnchorPane data4;
    @FXML
    private Label lblData4Title;
    @FXML
    private Label lblData4data;
    @FXML
    private AnchorPane eventOverview;
    @FXML
    private TableView tblEvent;
    @FXML
    private Label lblEvent;
    @FXML
    private Label lvlEventSearch;
    @FXML
    private Label lblEventTypeSearch;
    @FXML
    private TextField txtEventTitle;
    @FXML
    private ComboBox cbEventType;
    @FXML
    private AnchorPane cuponsPane;
    @FXML
    private Label lblCupons;
    @FXML
    private TableView tblCupons;
    private Map<Region, List<Double>> windowItems = new HashMap<>();

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

    // erhmmm ja... man kunne også bare give hvert pane et fxId og .getChildren() - hilsen Casper -> but also made by Casper... træls (root.getChildren() for each)
    // update note - det kan man ikke - fordi root childen har ikke nok children (7 currently ift. 40)
    public void initializeComponents(double width, double height) {
        List<Region> windowContent = new ArrayList<>();
        windowContent.add(dataPane);
        windowContent.add(root);
        windowContent.add(sideMenu);
        windowContent.add(menuBar);
        windowContent.add(userInformation);
        windowContent.add(lblUserName);
        windowContent.add(lblUserRole);
        //windowContent.add(imgLogout);
        windowContent.add(lblMenuTitle);
        windowContent.add(newEvent);
        //windowContent.add(imgNewEvent);
        windowContent.add(lblNewEventlbl);
        windowContent.add(newUser);
        windowContent.add(lblNewUser);
        windowContent.add(manageCupons);
        windowContent.add(lblManageCupons);
        windowContent.add(data1);
        windowContent.add(lblData1Title);
        windowContent.add(lblData1data);
        windowContent.add(data2);
        windowContent.add(lvlData2Title);
        windowContent.add(lblData2data);
        windowContent.add(data3);
        windowContent.add(lblData3Title);
        windowContent.add(lblData3data);
        windowContent.add(data4);
        windowContent.add(lblData4Title);
        windowContent.add(lblData4data);
        windowContent.add(eventOverview);
        windowContent.add(tblEvent);
        windowContent.add(lblEvent);
        windowContent.add(lvlEventSearch);
        windowContent.add(lblEventTypeSearch);
        windowContent.add(txtEventTitle);
        windowContent.add(cbEventType);
        windowContent.add(cuponsPane);
        windowContent.add(lblCupons);
        windowContent.add(tblCupons);
        //windowContent.add(imgManageCupons);
        //windowContent.add(imgNewUser);
        //windowContent.add(imgUserImage);

        fillMap(windowContent, width, height);

    }

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

            // TODO : vurdér om dette er okay + om vi skal have dette
            try{
                Label label = (Label) n;
                double orgSize = label.getStyleClass().contains("bigText") ? 32 : label.getStyleClass().contains("normalText") ? 24 : 12;
                double newValueAVG = (orgSize * (width / 1920) + orgSize * (height / 1080)) / 2;
                Font newFont = new Font(newValueAVG);
                label.setFont(newFont);
            } catch (Exception e) {
                // do nothing
            }
        }

        // set font size - relative to the size difference
        String stylesheet = root.getStylesheets().getFirst();
        System.out.println(stylesheet.contains(".bigText"));
    }

}