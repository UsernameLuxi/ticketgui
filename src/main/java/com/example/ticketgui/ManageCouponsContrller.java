package com.example.ticketgui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageCouponsContrller implements IController{
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

    private Font getFont(ObservableList<String> style, double newWidth, double newHeight){
        double orgSize = style.contains("bigText") ? 32 : style.contains("normalText") ? 24 : 12;
        double newValueAVG = (orgSize * (newWidth / 1920) + orgSize * (newHeight / 1080)) / 2;
        return new Font(newValueAVG);
    }

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

    }

    // static? - return things?
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
        width -= 15; // hold dig fra siden mand!
        height -= 30;
        for (Region n : windowItems.keySet()) {
            n.setPrefWidth(width * windowItems.get(n).get(0));
            n.setLayoutX(width * windowItems.get(n).get(2));

            n.setPrefHeight(height * windowItems.get(n).get(1));
            n.setLayoutY(height * windowItems.get(n).get(3));

            // TODO : skal have dette?
            // set font size - relative to the size difference
            switch (n) {
                case Label label -> label.setFont(getFont(label.getStyleClass(), width, height));
                case Button btn -> btn.setFont(getFont(btn.getStyleClass(), width, height));
                case TextField txt -> txt.setFont(getFont(txt.getStyleClass(), width, height));
                default -> {
                }
            }
        }

        // image views
        for (ImageView v : imageViews.keySet()){
            v.setFitWidth(width * imageViews.get(v).get(0));
            v.setFitHeight(height * imageViews.get(v).get(1));
            v.setLayoutX(width * imageViews.get(v).get(2));
            v.setLayoutY(height * imageViews.get(v).get(3));
        }
    }
}
