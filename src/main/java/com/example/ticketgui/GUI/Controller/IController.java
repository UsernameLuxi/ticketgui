package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.GUI.ControllerManager;
import com.example.ticketgui.GUI.util.Screens;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IController {
    void initializeComponents(double width, double height);
    void resizeItems(double width, double height);
    void resizeItems(Map<Region, List<Double>> components, Map<ImageView, List<Double>> imageComponents, double width, double height);

    void setControllerRoot(IController controller);
    void reload();
    AnchorPane getPane(Screens screen) throws IOException;

    Font getFont(ObservableList<String> style, double newWidth, double newHeight);

    void setManager(ControllerManager manager);
}
