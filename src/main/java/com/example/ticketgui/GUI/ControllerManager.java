package com.example.ticketgui.GUI;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.GUI.Controller.IController;
import com.example.ticketgui.GUI.Model.EventModel;
import com.example.ticketgui.GUI.Model.UserModel;
import com.example.ticketgui.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ControllerManager {
    // TODO : overvej om alle modellerne skal gemmes her?
    private EventModel eventModel;
    // TODO : overvej om currentUser skal være static
    private static User currentUser = null; // null igen logget ind ;)
    private Stage rootstage;
    private IController mainController;
    private boolean resizeTingen = false;

    public ControllerManager(Stage rootstage) {
        this.rootstage = rootstage;
        rootstage.setWidth(1920);
        rootstage.setHeight(1080);
        try{
            setStage("Login Screen.fxml");
        } catch (IOException e) {
            // something
        }

        // models?
        eventModel = new EventModel();
    }

    public void setStage(String file) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(file));
        Scene scene = new Scene(fxmlLoader.load(), rootstage.getWidth(), rootstage.getHeight());
        //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet.css")).toExternalForm()); // <-- denne er nok ikke nødvendig fordi der er allerede tilføjet et stylesheet på vinduet ;) - men den producere også en exception - så den er bare dejligt
        rootstage.setTitle("TICKET GUI");
        rootstage.setScene(scene);
        rootstage.show();
        mainController = fxmlLoader.getController();

        mainController.setManager(this);
        mainController.initializeComponents(1920, 1080);
        mainController.resizeItems(rootstage.getWidth(), rootstage.getHeight());
        if (!resizeTingen){
            rootstage.widthProperty().addListener((observable, oldValue, newValue) -> {mainController.resizeItems(newValue.doubleValue(), rootstage.getHeight());});
            rootstage.heightProperty().addListener((observable, oldValue, newValue) -> {mainController.resizeItems(rootstage.getWidth(), newValue.doubleValue());});
            rootstage.setMaximized(true);
            resizeTingen = true;
        }
    }

    public void setPaneRoot(AnchorPane pane, String file) {

    }

    public static User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User user){
        currentUser = user;
    }

    public EventModel getEventModel() {
        return eventModel;
    }

}
