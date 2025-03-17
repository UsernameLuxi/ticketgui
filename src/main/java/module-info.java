module com.example.ticketgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;


    opens com.example.ticketgui to javafx.fxml;
    exports com.example.ticketgui;
    exports com.example.ticketgui.GUI.Controller;
    opens com.example.ticketgui.GUI.Controller to javafx.fxml;
    exports com.example.ticketgui.GUI;
    opens com.example.ticketgui.GUI to javafx.fxml;
}