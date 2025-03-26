package com.example.ticketgui.GUI.util;

public enum Screens {
    MAIN_WINDOW("MainWindow.fxml"),
    LOGIN_WINDOW("Login Screen.fxml"),
    MANAGE_COUPONS("ManageCoupons.fxml"),
    EVENT_WINDOW("NewEvent.fxml"),
    USER_WINDOW("NewUser.fxml"),
    PRINT_EVENT("Print Event.fxml");


    private String file = "";
    Screens(String file){
        this.file = file;
    }
    public String getFile(){
        return file;
    }
}
