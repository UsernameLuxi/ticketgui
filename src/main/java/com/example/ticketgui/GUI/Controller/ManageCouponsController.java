package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.BE.Coupon;
import com.example.ticketgui.BE.Event;
import com.example.ticketgui.GUI.ControllerManager;
import com.example.ticketgui.GUI.Model.CouponModel;
import com.example.ticketgui.GUI.util.ShowAlerts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageCouponsController extends Controller {
    private ControllerManager manager;
    private IController root;
    private CouponModel couponModel;
    private Coupon selectedCoupon = null;
    private Event currentEvent = null;
    private Map<Region, List<Double>> windowItems = new HashMap<>();
    private Map<ImageView, List<Double>> imageViews = new HashMap<>();
    @FXML
    private AnchorPane manageCouponsPane;
    @FXML
    private TextField txtcoupon;
    @FXML
    private TextField txtDate;
    @FXML
    private Label lblcoupon;
    @FXML
    private Label lblDate;
    @FXML
    private TableView tblCoupons;
    @FXML
    private Label lblCouponsTable;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView imgBtnBack;
    @FXML
    private TextField txtPrice;
    @FXML
    private SplitMenuButton smbEvents;
    @FXML
    private DatePicker txtExpirDate;
    @FXML
    private Label txtFeedback;

    @Override
    // samme koncept som i main controlleren
    public void initializeComponents(double width, double height) {
        List<Region> windowContent = new ArrayList<>();
        //windowContent.add(manageCouponsPane);
        // fordi der ikke er nogle sub-panes - så kan dette gøres ;)
        for (Node n : manageCouponsPane.getChildren()){
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

        couponModel = manager.getCouponModel(); // tjek lige om den er null eller hvad

        try {
            List<Event> events = manager.getEventModel().getEventsForUser(ControllerManager.getCurrentUser());
            smbEvents.getItems().clear();

            MenuItem itemAll = new MenuItem("All events");
            itemAll.setOnAction((e) -> {
                smbEvents.setText("All events");
                currentEvent = null;
            });
            smbEvents.getItems().add(itemAll);
            for (Event event : events){
                MenuItem item = new MenuItem(event.getName());
                item.setOnAction((e) -> {
                    smbEvents.setText(event.getName());
                    currentEvent = event;
                });
                smbEvents.getItems().add(item);
            }
        } catch (Exception e) {
            ShowAlerts.displayMessage("Event loading", "Could not fetch database information\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // static? - return things? - flyt til abstrakt klasse?
    private void fillMap(List<Region> items, double width, double height) {
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
    private void saveCoupon(ActionEvent actionEvent) {
        int price = 0;
        String name = txtcoupon.getText().trim();
        String expire;

        try{
            price = Integer.parseInt(txtPrice.getText());
        } catch (Exception e) {
            txtFeedback.setText("Price not compatible");
        }

        LocalDate localDate = txtExpirDate.getValue();
        expire = localDate.getDayOfMonth() + "-" + localDate.getMonthValue() + "-" + localDate.getYear();
        Coupon c = new Coupon(name, price, expire);
        c.setEvent(currentEvent);

        try{
            couponModel.createCoupon(c);

            txtcoupon.setText("");
            txtDate.setText("");
            txtPrice.setText("");
            txtFeedback.setText("Coupon created!");
        }
        catch (Exception e) {
            txtFeedback.setText("Coupon creation failed");
        }
    }

    @FXML
    private void deleteCoupon(ActionEvent actionEvent) {
    }
}
