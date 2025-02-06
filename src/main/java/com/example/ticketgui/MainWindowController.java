package com.example.ticketgui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private List<Node> windowContent;

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
    public void initializeComponents(){
        windowContent = new ArrayList<>();
        windowContent.add(dataPane);
        windowContent.add(root);
        windowContent.add(sideMenu);
        windowContent.add(menuBar);
        windowContent.add(userInformation);
        windowContent.add(lblUserName);
        windowContent.add(lblUserRole);
        windowContent.add(imgLogout);
        windowContent.add(lblMenuTitle);
        windowContent.add(newEvent);
        windowContent.add(imgNewEvent);
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
        windowContent.add(imgManageCupons);
        windowContent.add(imgNewUser);
        windowContent.add(imgUserImage);
    }
}