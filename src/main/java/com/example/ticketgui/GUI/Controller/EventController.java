package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.BE.*;
import com.example.ticketgui.GUI.ControllerManager;
import com.example.ticketgui.GUI.Model.EventModel;
import com.example.ticketgui.GUI.util.ShowAlerts;
import com.example.ticketgui.GUI.util.VerifyTime;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Region;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class EventController extends Controller {
    private ControllerManager manager;
    private IController root;
    private EventModel model;
    private Event editEvent = null;
    private Map<Region, List<Double>> windowItems = new HashMap<>();
    private Map<ImageView, List<Double>> imageViews = new HashMap<>();
    private EventType currentEventType;
    @FXML
    private AnchorPane eventPane;
    @FXML
    private ImageView imgBtnBack;
    @FXML
    private TextField txtEventName;
    @FXML
    private TextField txtEventPrice;
    @FXML
    private TextField txtTime;
    @FXML
    private TextArea txtEventDesc;
    @FXML
    private DatePicker datePicker;
    @FXML
    private SplitMenuButton smbType;
    @FXML
    private ListView<User> lstEventUser;
    @FXML
    private ListView<User> lstUnassigned;
    @FXML
    private Label lblFeedback;
    @FXML
    private TextField txtGade;
    @FXML
    private TextField txtPostnummer;
    @FXML
    private TextField txtTimeEnd;
    @FXML
    private DatePicker datePickerEnd;


    @Override
    public void initializeComponents(double width, double height) {
        List<Region> windowContent = new ArrayList<>();
        // fordi der ikke er nogle sub-panes - så kan dette gøres ;)
        for (Node n : eventPane.getChildren()){
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

        // get event model
        model = manager.getEventModel();

        // fill the split menu
        try {
            List<MenuItem> items = new ArrayList<>();
            List<EventType> types = model.getEventTypes();
            for (EventType et : types) {
                MenuItem mi = new MenuItem(et.getName());
                mi.setOnAction(event -> {
                    MenuItem mt = (MenuItem) event.getSource();
                    smbType.setText(mt.getText());
                    currentEventType = et;
                });
                items.add(mi);
            }
            currentEventType = types.getFirst();
            smbType.setText(currentEventType.getName());
            smbType.getItems().clear();
            smbType.getItems().addAll(items);
        } catch (Exception e) {
            ShowAlerts.displayMessage("Event Error", "Could not load types\n" + e.getMessage(), Alert.AlertType.ERROR);
        }

        // fill the koordinator list
        lstEventUser.setItems(FXCollections.observableArrayList());
        if (ControllerManager.getCurrentUser().getUserRole() != UserRole.ADMIN) { // ingen admins
            lstEventUser.getItems().add(ControllerManager.getCurrentUser());
        }

        lstUnassigned.setItems(FXCollections.observableArrayList());
        int id = ControllerManager.getCurrentUser().getId();
        lstUnassigned.getItems().addAll(manager.getUserModel().getEventKoordinators());
        for (User u : lstUnassigned.getItems()) {
            if (u.getId() == id){
                lstUnassigned.getItems().remove(u);
                break;
            }
        }

        if (editEvent != null){
            setEditEvent();
        }
    }

    private void setEditEvent(){
        txtEventName.setText(editEvent.getName());
        txtEventPrice.setText(editEvent.getPrice() + "");
        txtEventDesc.setText(editEvent.getDescription());
        txtGade.setText(editEvent.getLocation().getStreet());
        txtPostnummer.setText(editEvent.getLocation().getPostalCode() + "");
        smbType.setText(editEvent.getEventType().getName());
        currentEventType = editEvent.getEventType();
        // users
        lstEventUser.getItems().clear();
        lstEventUser.getItems().addAll(editEvent.getEventKoordinators());
        List<User> removUser = new ArrayList<>();
        for (User u : lstUnassigned.getItems()){
            for (User ue : editEvent.getEventKoordinators()){
                if (ue.getId() == u.getId()){
                    removUser.add(u);
                }
            }
        }
        lstUnassigned.getItems().removeAll(removUser);

        // date and time
        String[] timedate = editEvent.getDateTime().split(" ");
        txtTime.setText(timedate[1].substring(1, timedate[1].length() - 1)); // se hvordan de ser ud og ændre det hvis det ikke er godt
        String[] date = timedate[0].split("-");

        String[] timedateEnd = editEvent.getEndDateTime().split(" ");
        System.out.println(editEvent.getEndDateTime());
        txtTimeEnd.setText(timedateEnd[1].substring(1, timedateEnd[1].length() - 1)); // se hvordan de ser ud og ændre det hvis det ikke er godt
        String[] dateEnd = timedate[0].split("-");
        try {
            int year = Integer.parseInt(date[2]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[0]);

            int yearEnd = Integer.parseInt(dateEnd[2]);
            int monthEnd = Integer.parseInt(dateEnd[1]);
            int dayEnd = Integer.parseInt(dateEnd[0]);

            datePicker.setValue(LocalDate.of(year, month, day));
            datePickerEnd.setValue(LocalDate.of(yearEnd, monthEnd, dayEnd));
        } catch (NumberFormatException e) {
            ShowAlerts.displayMessage("Event Error", "Could not read the date!\n" + e.getMessage(), Alert.AlertType.ERROR);
        }

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
    private void saveEvent(ActionEvent actionEvent) {
        // TODO : lav tjek på lokationen - måske eller på et tidspunkt brug google maps api?
        String name = txtEventName.getText();
        String desc = txtEventDesc.getText();
        EventType type = currentEventType;

        // location
        String post = txtPostnummer.getText().trim();
        int postInt = 0;
        int price;
        // postnummer try catch
        try {
            postInt = Integer.parseInt(post);
        } catch (NumberFormatException e) {
            lblFeedback.setText("Could not read number");
            lblFeedback.setStyle("-fx-text-fill: red");
            txtPostnummer.setStyle("-fx-border-color: red");
        }

        String street = txtGade.getText().trim();
        Location location = null;
        try {
            location = getLocation(postInt, street.trim());
        } catch (Exception e) {
            lblFeedback.setText("Could not fetch location from database. Try again later!");
            lblFeedback.setStyle("-fx-text-fill: red");
            return;
        }

        // pris
        try {
            price = Integer.parseInt(txtEventPrice.getText());
            // TODO : tjek at den ikke er negativ
        } catch (NumberFormatException e) {
            lblFeedback.setText("Could not read number");
            lblFeedback.setStyle("-fx-text-fill: red");
            txtEventPrice.setStyle("-fx-border-color: red");
            return;
        }

        //String dateTime = datePicker.getValue().toString() + " (" + txtTime.getText() + ")";
        if (!VerifyTime.verifyTime(txtTime.getText()) || !VerifyTime.verifyTime(txtTimeEnd.getText())){
            lblFeedback.setText("Invalid time!");
            return;
        }
        String dateTime = datePicker.getValue().getDayOfMonth() + "-"+ datePicker.getValue().getMonthValue() + "-" + datePicker.getValue().getYear() + " (" + txtTime.getText() + ")";
        String dateTimeEnd = datePickerEnd.getValue().getDayOfMonth() + "-"+ datePickerEnd.getValue().getMonthValue() + "-" + datePickerEnd.getValue().getYear() + " (" + txtTimeEnd.getText() + ")";

        int id = editEvent == null ? -1 : editEvent.getId();// in case of edit
        Event e = new Event(id, name, price, desc, dateTime, type, location);
        e.setEventKoordinators(lstEventUser.getItems());
        e.setEndDateTime(dateTimeEnd);
        try {
            if ((id > 0)) {
                model.updateEvent(e);
                loadMain(actionEvent); // lukker den ned efter den har gemt
            } else {
                model.createEvent(e);
            }

            // TODO : lav den timed så man ikke ser på den hele tiden - måske lave schduledExecutor idk -> runnable
            String feedback = e.getName() + " -> saved!";
            lblFeedback.setStyle("-fx-text-fill: black");
            lblFeedback.setText(feedback);

            // tøm felter
            txtEventName.clear();
            txtEventDesc.clear();
            txtEventPrice.clear();
            txtEventPrice.setStyle("-fx-border-color: transparent");
            datePicker.setValue(null);
            datePickerEnd.setValue(null);
            txtGade.clear();
            txtPostnummer.clear();
            txtTime.clear();
            txtTimeEnd.clear();
            
            // reset formatering på textfields
            txtEventPrice.setStyle("-fx-border-color: transparent");
            txtPostnummer.setStyle("-fx-border-color: transparent");
        } catch (Exception ex) {
            lblFeedback.setText("Could not save event");
            lblFeedback.setStyle("-fx-text-fill: red");
        }
    }

    private Location getLocation(int postInt, String street) throws Exception {
        List<Location> locationList = manager.getLocationModel().getLocations().getOrDefault(postInt, new ArrayList<>());
        if (!locationList.isEmpty()){
            for (Location loc : locationList){
                if (loc.getStreet().equalsIgnoreCase(street)){
                    return loc;
                }
            }

        }
        Location newLocation = new Location(-1, postInt, street);
        try{
            newLocation = manager.getLocationModel().createLocation(newLocation);
        }
        catch (Exception e){
            lblFeedback.setText("Could not create new location");
        }

        return newLocation;
    }

    @FXML
    private void addKoor(ActionEvent actionEvent) {
        User selUser = lstUnassigned.getSelectionModel().getSelectedItem();
        if (selUser != null && !selUser.getUsername().isEmpty()) {
            lstEventUser.getItems().add(selUser);
            lstUnassigned.getItems().remove(selUser);
            if (lstUnassigned.getItems().isEmpty()) {
                lstUnassigned.getItems().add(new User("", ""));
            }
        }

    }

    @FXML
    private void removeKoor(ActionEvent actionEvent) {
        User selUser = lstEventUser.getSelectionModel().getSelectedItem();
        if (selUser != null){
            if (selUser.getId() != ControllerManager.getCurrentUser().getId() && lstEventUser.getItems().size() > 1){
                if (lstUnassigned.getItems().getFirst().getUsername().isEmpty()){
                    lstUnassigned.getItems().clear();
                }
                lstUnassigned.getItems().add(selUser);
                lstEventUser.getItems().remove(selUser);
            }
        }
    }

    public void setEdit(Event event){
        editEvent = event;
    }
}