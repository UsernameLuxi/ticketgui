module com.example.ticketgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.ticketgui to javafx.fxml;
    exports com.example.ticketgui;
}