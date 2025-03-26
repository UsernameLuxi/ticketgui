package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.BE.Event;
import com.example.ticketgui.BE.EventType;
import com.example.ticketgui.BE.Location;
import com.example.ticketgui.BE.UserRole;
import com.example.ticketgui.GUI.ControllerManager;
import com.example.ticketgui.GUI.util.ShowAlerts;
import com.example.ticketgui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;
public class MainWindowController extends Controller {
    private ControllerManager manager;
    private IController rootController;
    private IController viewController;

    private Map<Region, List<Double>> windowItems = new HashMap<>();
    private Map<ImageView, List<Double>> imageViews = new HashMap<>();
    private ObservableList<Node> mainContent;

    private boolean resizeableListenerAdded = false;

    private Event editEvent = null;


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
    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnLogout;

    /**
     * EVENT TABLE CONTENT
     */
    @FXML private TableView<Event> tblEvent;
    @FXML private TableColumn<Event, String> colTitle;
    @FXML private TableColumn<Event, EventType> colType;
    @FXML private TableColumn<Event, Integer> colPrice;
    @FXML private TableColumn<Event, Location> colLoc;
    @FXML private TableColumn<Event, String> colTime;
    @FXML private TableColumn<Event, String> colEdit;
    @FXML private TableColumn<Event, String> colPrint;
    @FXML private TableColumn<Event, String> colDel;

    /*
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
     */

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
        tblCupons,
        btnLogout));

        // special case:
        //ScrollPane scrollPane = createScrollpaneForDatapane();
        /*
        windowItems.put(scrollPane, new ArrayList<>(){{
            add(scrollPane.getPrefWidth() / width);
            add(scrollPane.getPrefHeight() / height);
            add(scrollPane.getLayoutX() / width);
            add(scrollPane.getLayoutY() / height);}}
        );
         */

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
        imageViews.put(imgLogo, new ArrayList<>(){{
            add(imgLogo.getFitWidth() / width);
            add(imgLogo.getFitHeight() / height);
            add(imgLogo.getLayoutX() / width);
            add(imgLogo.getLayoutY() / height);}});

        fillMap(windowContent, width, height);
        mainContent = FXCollections.observableArrayList();
        mainContent.addAll(viewPanel.getChildren());
        //mainContent.remove(dataPane);

        // indsæt bruger ;)
        lblUserName.setText(ControllerManager.getCurrentUser().getUsername());
        lblUserRole.setText(ControllerManager.getCurrentUser().getUserRole().toString().toLowerCase());

        buttonHandling(ControllerManager.getCurrentUser().getUserRole());

        // indsæt events TODO - spørgsmålet er om det skal gå gennem manageren og så til modellerne
        colTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        colEdit.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Event, String> call(TableColumn<Event, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            Button editButton = new Button("Edit");
                            editButton.setOnAction(event -> {
                                editEvent(getTableView().getItems().get(getIndex()));
                            });
                            setGraphic(editButton);
                        }
                    }
                };
            }
        });
        colDel.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Event, String> call(TableColumn<Event, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            Button delButton = new Button("Del");
                            delButton.setOnAction((event) -> {
                                Event e = getTableView().getItems().get(getIndex());
                                removeEvent(e);
                            });
                            setGraphic(delButton);
                        }
                    }
                };
            }
        });
        colPrint.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Event, String> call(TableColumn<Event, String> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            Button printButton = new Button("Print");
                            setGraphic(printButton);
                        }
                    }
                };
            }
        });
        try {
            tblEvent.setItems(manager.getEventModel().getEventsForUser(ControllerManager.getCurrentUser()));
        } catch (Exception e) {
            ShowAlerts.displayMessage("Event Error", "Could not fetch events for current user\n" + e.getMessage(), Alert.AlertType.ERROR);
            //System.out.println(e.getMessage());
        }

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


    // TODO : Måske panes som enum?
    private void setPane(String file) throws IOException {
        viewPanel.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(file));
        AnchorPane pane = loader.load();

        viewController = loader.getController();
        viewController.setManager(manager);
        if (file.equals("NewEvent.fxml") && editEvent != null){
            EventController ev = (EventController) viewController;
            ev.setEdit(editEvent);
        }
        // husk gør dette kun en gang - ikke flere
        viewController.initializeComponents(1920, 972);
        viewController.resizeItems(viewPanel.getWidth(), viewPanel.getHeight());
        viewController.setControllerRoot(this);
        if (!resizeableListenerAdded){
            viewPanel.widthProperty().addListener((observable, oldValue, newValue) -> {viewController.resizeItems(newValue.doubleValue(), viewPanel.getHeight());});
            viewPanel.heightProperty().addListener(((observable, oldValue, newValue) -> {viewController.resizeItems(viewPanel.getWidth(), newValue.doubleValue());}));
            resizeableListenerAdded = true;
        }

        viewPanel.getChildren().add(pane);

    }

    @FXML
    private void newEventClick(MouseEvent mouseEvent) {
        try{
            setPane("NewEvent.fxml");
        }
        catch (Exception e){
            ShowAlerts.displayMessage("Window Error", "Could not load window\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void newUserClick(MouseEvent mouseEvent) {
        try{
            setPane("NewUser.fxml");
        }
        catch (Exception e){
            ShowAlerts.displayMessage("Window Error", "Could not load window\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void manageCouponClick(MouseEvent mouseEvent) {
        try{
            setPane("ManageCoupons.fxml");
        }
        catch (Exception e){
            ShowAlerts.displayMessage("Window Error", "Could not load window\n" + e.getMessage(), Alert.AlertType.ERROR);
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
    private void logout(ActionEvent mouseEvent) {
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

    private void removeEvent(Event event) {
        if (!ShowAlerts.displayWarning("Sletning af event!", "Vil du gerne slette eventet: " + event.getName()))
            return;

        try {
            manager.getEventModel().deleteEvent(event);
        } catch (Exception ex) {
            ShowAlerts.displayMessage("Event Error", "Could not remove event\n" + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void editEvent(Event event){
        editEvent = event;
        try {
            setPane("NewEvent.fxml");
        } catch (IOException e) {
            editEvent = null;
            ShowAlerts.displayMessage("Window Error", "Could not load window\n" + e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void buttonHandling(UserRole userRole) {
        if (userRole == UserRole.EVENT_KOORDINATOR) {
            newUser.setVisible(false);
        }
        else if (userRole == UserRole.ADMIN)
            newEvent.setVisible(false);
        }

    @FXML
    private void toMainMenu(MouseEvent mouseEvent) {
        reload();
    }
}