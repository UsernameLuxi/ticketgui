package com.example.ticketgui;

import com.example.ticketgui.GUI.ControllerManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login Screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet.css")).toExternalForm());
        stage.setTitle("TICKET GUI");
        stage.setScene(scene);
        stage.show();
        IController mwc = fxmlLoader.getController();

        mwc.initializeComponents(1920, 1080);

        stage.widthProperty().addListener((observable, oldValue, newValue) -> {mwc.resizeItems(newValue.doubleValue(), stage.getHeight());});
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {mwc.resizeItems(stage.getWidth(), newValue.doubleValue());});

        stage.setMaximized(true);

        //mwc.test(stage);

         */
        ControllerManager controllerManager = new ControllerManager(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}