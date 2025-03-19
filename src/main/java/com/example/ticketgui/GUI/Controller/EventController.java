package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.BE.Event;
import com.example.ticketgui.BE.EventType;
import com.example.ticketgui.BE.Location;
import com.example.ticketgui.BE.UserRole;
import com.example.ticketgui.GUI.ControllerManager;
import com.example.ticketgui.GUI.Model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.text.SimpleDateFormat;
import java.util.*;

public class EventController extends Controller {
    private ControllerManager manager;
    private IController root;
    private EventModel model;
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
    private TextField txtLocation;
    @FXML
    private TextField txtTime;
    @FXML
    private TextArea txtEventDesc;
    @FXML
    private DatePicker datePicker;
    @FXML
    private SplitMenuButton smbType;


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

        // create event model
        model = new EventModel();

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
            // TODO : noget her
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
        // TODO : lav tjek på tidspunkt at den er valid
        // TODO : lav tjek på lokationen - måske eller på et tidspunkt brug google maps api?
        String name = txtEventName.getText();
        String desc = txtEventDesc.getText();
        EventType type = currentEventType;
        // location
        String post = txtLocation.getText().split(",")[0];
        int postInt = 0;
        int price;
        try {
            postInt = Integer.parseInt(post);
            price = Integer.parseInt(txtEventPrice.getText());
        } catch (NumberFormatException e) {
            // TODO : noget her
            return;
        }
        String street = txtLocation.getText().split(",")[1];
        Location location = new Location(-1, postInt, street); // TODO : tjek lige om den allerede er i db!

        //String dateTime = datePicker.getValue().toString() + " (" + txtTime.getText() + ")";
        String dateTime = datePicker.getValue().getDayOfMonth() + "-"+ datePicker.getValue().getMonthValue() + "-" + datePicker.getValue().getYear() + " (" + txtTime.getText() + ")";
        Event e = new Event(-1, name, price, desc, dateTime, type, location);
        try {
            model.createEvent(e);
            // TODO : tilføj noget feedback

            // tøm felter
            txtEventName.clear();
            txtEventDesc.clear();
            txtEventPrice.clear();
            datePicker.setValue(null);
            txtLocation.clear();
            txtTime.clear();
        } catch (Exception ex) {
            // TODO : tilføj noget her!
        }
    }
}
