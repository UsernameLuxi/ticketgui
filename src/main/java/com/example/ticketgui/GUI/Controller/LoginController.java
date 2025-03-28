package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.BE.User;
import com.example.ticketgui.GUI.ControllerManager;
import com.example.ticketgui.GUI.Model.UserModel;
import com.example.ticketgui.GUI.util.Screens;
import com.example.ticketgui.GUI.util.ShowAlerts;
import com.example.ticketgui.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController extends Controller {
    private IController mainController;
    private ControllerManager manager;
    private boolean abool = false;
    private IController root;
    private Map<Region, List<Double>> windowItems = new HashMap<>();
    private Map<ImageView, List<Double>> imageViews = new HashMap<>();
    private UserModel userModel;
    private boolean isShowing = false;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private TextField txtUsername;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblError;
    @FXML
    private ImageView imgShowHide;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtpasswordShown;

    public LoginController() {
        /* prøv lige med initialize components i stedet
        try{
            userModel = new UserModel();
        }
        catch (Exception e){
            ShowAlerts.displayMessage("Error", "Could not fetch users\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
         */
    }


    // samme koncept som i main controlleren
    public void initializeComponents(double width, double height) {
        List<Region> windowContent = new ArrayList<>();
        windowContent.add(loginPane);
        // fordi der ikke er nogle sub-panes - så kan dette gøres ;)
        for (Node n : loginPane.getChildren()){
            if (n instanceof Region r){
                windowContent.add(r);
            }
        }

        imageViews.put(imgShowHide, new ArrayList<>(){{
            add(imgShowHide.getFitWidth() / width);
            add(imgShowHide.getFitHeight() / height);
            add(imgShowHide.getLayoutX() / width);
            add(imgShowHide.getLayoutY() / height);}});

        fillMap(windowContent, width, height);

        userModel = manager.getUserModel();

    }

    private void fillMap(List<Region> items, double width, double height) {
        for (Region item : items) {
            windowItems.put(item, new ArrayList<>(){{
                add(item.getWidth() / width);
                add(item.getHeight() / height);
                add(item.getLayoutX() / width);
                add(item.getLayoutY() / height);}}
            );
        }
    }

    public void resizeItems(double width, double height){
        resizeItems(windowItems, imageViews, width, height);
    }

    @Override
    public void setControllerRoot(IController controller) {
        root = controller;
    }

    @FXML
    private void loadMain(ActionEvent event) {
        root.reload();
    }

    @FXML
    private void login(ActionEvent actionEvent) {
        //  refactor? -> lav metode i usermodel? -> en lille flytning?
        // f.eks:
        // bool valid = usermodel.login(new User(tingeling))
        // if vaild -> login
        // else -> display not valid
        User loginAttempt = new User(txtUsername.getText(), txtPassword.getText());
        try{
            User loginUser = userModel.login(loginAttempt);
            if (loginUser != null){
                manager.setCurrentUser(loginUser);
            }
            else {
                lblError.setText("Ugyldig username or password!");
                return;
            }
        }
        catch (Exception e){
            lblError.setText(e.getMessage()); // måske lidt mere brugervenligt tekst
            return;
        }
        try{
            manager.setStage(Screens.MAIN_WINDOW);
        }
        catch (Exception e){
            ShowAlerts.displayMessage("Window Error", "Could not load window\n" + e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @Override
    public void reload(){
        try{
            manager.setStage(Screens.LOGIN_WINDOW);
        }
        catch (IOException e){
            ShowAlerts.displayMessage("Window Error", "Could not load window\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void setManager(ControllerManager manager) {
        this.manager = manager;
    }

    private void setPane(String file) throws IOException {
        loginPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(file));
        AnchorPane pane = loader.load();
        pane.setPrefHeight(loginPane.getWidth());
        pane.setPrefWidth(loginPane.getHeight());
        mainController = loader.getController();

        // husk gør dette kun en gang - ikke flere
        mainController.initializeComponents(1920, 1080);
        mainController.resizeItems(loginPane.getWidth(), loginPane.getHeight());
        mainController.setControllerRoot(this);
        if (!abool){
            loginPane.widthProperty().addListener((observable, oldValue, newValue) -> {mainController.resizeItems(newValue.doubleValue(), loginPane.getHeight());});
            loginPane.heightProperty().addListener(((observable, oldValue, newValue) -> {mainController.resizeItems(loginPane.getWidth(), newValue.doubleValue());}));
            abool = true;
        }
        loginPane.getChildren().addAll(pane.getChildren());
    }

    @FXML
    private void togglePassword(MouseEvent mouseEvent) {
        isShowing = !isShowing;
        txtPassword.setVisible(!isShowing);
        txtpasswordShown.setVisible(isShowing);
        if (isShowing){
            // show closed eye
            imgShowHide.setImage(new Image(String.valueOf(Main.class.getResource("symbols/hide.png"))));

            txtpasswordShown.setText(txtPassword.getText());
        }
        else{
            // show open eye
            imgShowHide.setImage(new Image(String.valueOf(Main.class.getResource("symbols/eye.png"))));
            txtPassword.setText(txtpasswordShown.getText());
            txtPassword.selectAll();
        }

    }
}
