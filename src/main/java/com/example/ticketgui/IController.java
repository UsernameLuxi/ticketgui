package com.example.ticketgui;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IController {
    // TODO : overalt i controllerne er der duplikater - tænk på hvordan dette enten kan implementeres i interfacet el lign
    void initializeComponents(double width, double height);
    void resizeItems(double width, double height);
    default void resizeItems(Map<Region, List<Double>> components, Map<ImageView, List<Double>> imageComponents, double width, double height){
        width -= 15; // hold dig fra siden mand!
        height -= 30;
        for (Region n : components.keySet()) {
            //n.resize(width * windowItems.get(n).get(0), height * windowItems.get(n).get(1));
            n.setPrefWidth(width * components.get(n).get(0));
            n.setLayoutX(width * components.get(n).get(2));

            n.setPrefHeight(height * components.get(n).get(1));
            n.setLayoutY(height * components.get(n).get(3));

            // set font size - relative to the size difference
            switch (n) {
                case Label label -> label.setFont(getFont(label.getStyleClass(), width, height));
                case Button btn -> btn.setFont(getFont(btn.getStyleClass(), width, height));
                case TextField txt -> txt.setFont(getFont(txt.getStyleClass(), width, height));
                case TextArea txt -> txt.setFont(getFont(txt.getStyleClass(), width, height));
                case SplitMenuButton smb -> smb.setFont(getFont(smb.getStyleClass(), width, height));
                default -> {
                }
            }
        }

        if (imageComponents == null)
            return;

        // image views
        for (ImageView v : imageComponents.keySet()){
            v.setFitWidth(width * imageComponents.get(v).get(0));
            v.setFitHeight(height * imageComponents.get(v).get(1));
            v.setLayoutX(width * imageComponents.get(v).get(2));
            v.setLayoutY(height * imageComponents.get(v).get(3));
        }
    }
    void setControllerRoot(IController controller);
    default void reload(){
        try{
            AnchorPane pane = getPane("Print Event.fxml");
            // TODO : Do something
        }
        catch (Exception e){
            // TODO : indsæt noget
        }
    }
    default AnchorPane getPane(String file) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(file));
        return loader.load();
    }

    default Font getFont(ObservableList<String> style, double newWidth, double newHeight){
        double orgSize = style.contains("bigText") ? 32 : style.contains("normalText") ? 24 : 12;
        double newValueAVG = (orgSize * (newWidth / 1920) + orgSize * (newHeight / 1080)) / 2;
        return new Font(newValueAVG);
    }

    void setManager(ControllerManager manager);
}
