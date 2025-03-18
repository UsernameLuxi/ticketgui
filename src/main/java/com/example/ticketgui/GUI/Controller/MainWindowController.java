package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.BE.Event;
import com.example.ticketgui.GUI.ControllerManager;
import com.example.ticketgui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
// TODO : Ryd op
public class MainWindowController extends Controller {
    private ControllerManager manager;
    private IController rootController;
    private IController viewController;
    private Map<Region, List<Double>> windowItems = new HashMap<>();
    private Map<ImageView, List<Double>> imageViews = new HashMap<>();
    private boolean addedThing = false; // TODO : rename


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
    private TableView<Event> tblEvent;
    @FXML
    private Label lblEvent;
    @FXML
    private Label lvlEventSearch;
    @FXML
    private Label lblEventTypeSearch;
    @FXML
    private TextField txtEventTitle;
    @FXML
    private SplitMenuButton cbEventType;
    @FXML
    private AnchorPane cuponsPane;
    @FXML
    private Label lblCupons;
    @FXML
    private TableView tblCupons;
    @FXML
    private AnchorPane viewPanel;
    private ObservableList<Node> mainContent;

    public ScrollPane createScrollpaneForDatapane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(dataPane.getLayoutX());
        scrollPane.setLayoutY(dataPane.getLayoutY());
        scrollPane.setContent(dataPane);
        scrollPane.setPrefViewportHeight(dataPane.getHeight());
        scrollPane.setPrefViewportWidth(dataPane.getWidth());

        // nok på en anden måde
        scrollPane.setStyle("-fx-background-color: #7766DD;");

        //double length = Stage.getWindows().getFirst().getWidth() - sideMenu.getWidth();
        double length = 1920 - sideMenu.getWidth();
        scrollPane.setPrefSize(length, dataPane.getHeight());
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        viewPanel.getChildren().add(scrollPane);
        return scrollPane;
    }

    // Skifte mellem ting/menuer
    // dette virker find lige en metode at initte resize på - måske flyt til en manager
    public void test(Stage stage) throws IOException {
        /*
        viewPanel.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Print Event.fxml"));
        AnchorPane jens = loader.load();

        viewController = loader.getController();
        // husk gør dette kun en gang - ikke flere
        viewController.initializeComponents(1920, 972);
        viewController.resizeItems(viewPanel.getWidth(), viewPanel.getHeight());
        viewPanel.widthProperty().addListener((observable, oldValue, newValue) -> {viewController.resizeItems(newValue.doubleValue(), viewPanel.getHeight());});
        viewPanel.heightProperty().addListener(((observable, oldValue, newValue) -> {viewController.resizeItems(viewPanel.getWidth(), newValue.doubleValue());}));

        viewPanel.getChildren().add(jens);

        //viewPanel.getChildren().add(//something)

         */
    }

    // erhmmm ja... man kunne også bare give hvert pane et fxId og .getChildren() - hilsen Casper -> but also made by Casper... træls (root.getChildren() for each)
    // update note - det kan man ikke - fordi root childen har ikke nok children (7 currently ift. 40)
    @Override
    public void initializeComponents(double width, double height) {
        List<Region> windowContent = new ArrayList<>(Arrays.asList(
        dataPane, 
        viewPanel,
        root,
        sideMenu,
        menuBar,
        userInformation,
        lblUserName,
        lblUserRole,
        lblMenuTitle,
        newEvent,
        lblNewEventlbl,
        newUser,
        lblNewUser,
        manageCupons,
        lblManageCupons,
        data1,
        lblData1Title,
        lblData1data,
        data2,
        lvlData2Title,
        lblData2data,
        data3,
        lblData3Title,
        lblData3data,
        data4,
        lblData4Title,
        lblData4data,
        eventOverview,
        tblEvent,
        lblEvent,
        lvlEventSearch,
        lblEventTypeSearch,
        txtEventTitle,
        cbEventType,
        cuponsPane,
        lblCupons,
        tblCupons));

        // special case:
        ScrollPane scrollPane = createScrollpaneForDatapane();
        windowItems.put(scrollPane, new ArrayList<>(){{
            add(scrollPane.getPrefWidth() / width);
            add(scrollPane.getPrefHeight() / height);
            add(scrollPane.getLayoutX() / width);
            add(scrollPane.getLayoutY() / height);}}
        );

        // også et specielt tilfælde
        imageViews.put(imgManageCupons, new ArrayList<>(){{
            add(imgManageCupons.getFitWidth() / width);
            add(imgManageCupons.getFitHeight() / height);
            add(imgManageCupons.getLayoutX() / width);
            add(imgManageCupons.getLayoutY() / height);}});
        imageViews.put(imgNewUser, new ArrayList<>(){{
            add(imgNewUser.getFitWidth() / width);
            add(imgNewUser.getFitHeight() / height);
            add(imgNewUser.getLayoutX() / width);
            add(imgNewUser.getLayoutY() / height);}});
        imageViews.put(imgUserImage, new ArrayList<>(){{
            add(imgUserImage.getFitWidth() / width);
            add(imgUserImage.getFitHeight() / height);
            add(imgUserImage.getLayoutX() / width);
            add(imgUserImage.getLayoutY() / height);}});
        imageViews.put(imgNewEvent, new ArrayList<>(){{
            add(imgNewEvent.getFitWidth() / width);
            add(imgNewEvent.getFitHeight() / height);
            add(imgNewEvent.getLayoutX() / width);
            add(imgNewEvent.getLayoutY() / height);}});
        imageViews.put(imgLogout, new ArrayList<>(){{
            add(imgLogout.getFitWidth() / width);
            add(imgLogout.getFitHeight() / height);
            add(imgLogout.getLayoutX() / width);
            add(imgLogout.getLayoutY() / height);}});

        fillMap(windowContent, width, height);
        mainContent = FXCollections.observableArrayList();
        mainContent.addAll(viewPanel.getChildren());
        mainContent.remove(dataPane);

        // indsæt bruger ;)
        lblUserName.setText(ControllerManager.getCurrentUser().getUsername());
        lblUserRole.setText(ControllerManager.getCurrentUser().getUserRole().toString());

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

    @Override
    public void resizeItems(double width, double height){
        resizeItems(windowItems, imageViews, width, height);
    }


    private void setPane(String file) throws IOException {
        viewPanel.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(file));
        AnchorPane pane = loader.load();

        viewController = loader.getController();
        // husk gør dette kun en gang - ikke flere
        viewController.initializeComponents(1920, 972);
        viewController.resizeItems(viewPanel.getWidth(), viewPanel.getHeight());
        viewController.setControllerRoot(this);
        if (!addedThing){
            viewPanel.widthProperty().addListener((observable, oldValue, newValue) -> {viewController.resizeItems(newValue.doubleValue(), viewPanel.getHeight());});
            viewPanel.heightProperty().addListener(((observable, oldValue, newValue) -> {viewController.resizeItems(viewPanel.getWidth(), newValue.doubleValue());}));
            addedThing = true;
        }

        viewPanel.getChildren().add(pane);

    }

    @FXML
    private void newEventClick(MouseEvent mouseEvent) {
        try{
            setPane("NewEvent.fxml");
        }
        catch (Exception e){
            // TODO : Find et eller andet og putte her
        }
    }

    @FXML
    private void newUserClick(MouseEvent mouseEvent) {
        try{
            setPane("NewUser.fxml");
        }
        catch (Exception e){
            // TODO : Find et eller andet og putte her
        }
    }

    @FXML
    private void manageCouponClick(MouseEvent mouseEvent) {
        try{
            setPane("ManageCoupons.fxml");
        }
        catch (Exception e){
            // TODO : Find et eller andet og putte her
        }
    }

    @Override
    public void setControllerRoot(IController controller) {
        rootController = controller;
    }

    @Override
    public void reload() {
        viewPanel.getChildren().setAll(mainContent);
    }

    @FXML
    private void logout(MouseEvent mouseEvent) {
        try{
            manager.setStage("Login Screen.fxml");
        }
        catch (Exception e){
            // noget het
        }
    }

    @Override
    public void setManager(ControllerManager manager) {
        this.manager = manager;
    }

    private void removeEvent(ActionEvent event) {
        // TODO : confirmation message
        Event e = tblEvent.getSelectionModel().getSelectedItem();
        if (e == null){
            return;
        }
        try {
            manager.getEventModel().deleteEvent(e);
        } catch (Exception ex) {
            // TODO : indsæt noget
        }
    }
}