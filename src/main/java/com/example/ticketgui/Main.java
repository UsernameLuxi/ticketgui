package com.example.ticketgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet.css")).toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        MainWindowController mwc = fxmlLoader.getController();

        mwc.initializeComponents(1920, 1080);

        stage.widthProperty().addListener((observable, oldValue, newValue) -> {mwc.resizeItems(newValue.doubleValue(), stage.getHeight());});
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {mwc.resizeItems(stage.getWidth(), newValue.doubleValue());});

        stage.setMaximized(true);

        mwc.test(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}