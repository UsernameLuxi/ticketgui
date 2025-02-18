package com.example.ticketgui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public interface IController {
    // TODO : overvej om dette skal laves til en abstrakt klasse
    // TODO : overalt i controllerne er der duplikater - tænk på hvordan dette enten kan implementeres i interfacet el lign
    void initializeComponents(double width, double height);
    void resizeItems(double width, double height);
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
}
