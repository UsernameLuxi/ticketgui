package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.BE.Coupon;
import com.example.ticketgui.BE.Event;
import com.example.ticketgui.BE.Ticket;
import com.example.ticketgui.BE.User;
import com.example.ticketgui.GUI.ControllerManager;
import com.example.ticketgui.GUI.Model.EventModel;
import com.example.ticketgui.GUI.util.ShowAlerts;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import com.google.zxing.BarcodeFormat;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import javax.imageio.ImageIO;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class PrintEventController extends Controller {
    private ControllerManager manager;
    private IController root;
    private EventModel model;
    private Event editEvent = null;
    private Ticket printTicket = null; // TODO : fjern hvis den ikke bliver brugt
    private int totalPrice = 0;
    private static final String valutaSuffix = ",-";
    private Map<Region, List<Double>> windowItems = new HashMap<>();
    private Map<ImageView, List<Double>> imageViews = new HashMap<>();
    @FXML
    private ImageView imgBtnBack;
    @FXML
    private AnchorPane printEvent;
    @FXML
    private Button btnPrint;
    @FXML
    private Label txtEvent;
    @FXML
    private Label lblPrice;
    @FXML
    private Label lblTotPrice;

    /**
     * Available Coupons Table
     */
    @FXML
    private TableView<Coupon> tblCouponsAvailable;
    @FXML
    private TableColumn<Coupon, String> colAvailPrice;
    @FXML
    private TableColumn<Coupon, String> colAvailTitle;

    /**
     * Selected Coupons Table
     */
    @FXML
    private TableView<Coupon> tblCouponsSelected;
    @FXML
    private TableColumn<Coupon, String> colSelectTitle;
    @FXML
    private TableColumn<Coupon, String> colSelectPrice;


    @Override
    public void initializeComponents(double width, double height) {
        List<Region> windowContent = new ArrayList<>();
        model = manager.getEventModel();
        // fordi der ikke er nogle sub-panes - så kan dette gøres ;)
        for (Node n : printEvent.getChildren()){
            if (n instanceof Region r){
                windowContent.add(r);
            }
        }

        imageViews.put(imgBtnBack, new ArrayList<>(){{
            add(imgBtnBack.getFitWidth() / width);
            add(imgBtnBack.getFitHeight() / height);
            add(imgBtnBack.getLayoutX() / width);
            add(imgBtnBack.getLayoutY() / height);}});

        fillMap(windowContent, width, height);

        printTicket = new Ticket(editEvent);
        loadCouponsForEvent(editEvent);
        btnPrint.setOnAction(event -> {
            try {
                printTicket(printTicket);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        lblPrice.setText("Event price: " + editEvent.getPrice() + valutaSuffix);
        totalPrice = editEvent.getPrice();
        lblTotPrice.setText("Total price: " + totalPrice + valutaSuffix);
        loadCouponsForEvent(editEvent);
    }

    private Image createQrCodeImage(String data) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", byteArrayOutputStream);
        byte[] qrImageBytes = byteArrayOutputStream.toByteArray();
        ImageData imageData = ImageDataFactory.create(qrImageBytes);

        return new Image(imageData);
    }

    private void printTicket(Ticket ticket) throws Exception {
        int sales = model.incrementSale(editEvent);

        if (tblCouponsSelected.getItems().isEmpty() || tblCouponsSelected.getItems().getFirst().getName().isEmpty())
            // do nothing or something
            ticket.setCouponList(new ArrayList<>());
        else
            ticket.setCouponList(tblCouponsSelected.getItems());

        String data = editEvent.getId() + "-" + sales + "-" + UUID.randomUUID();
        //TODO : data SEND NED

        Files.deleteIfExists(Path.of("QRCode.pdf"));

        Image qrCodeImage = createQrCodeImage(data);

        String pdfPath = "QRCode.pdf";
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph textOver = new Paragraph(
                editEvent.getName() +
                        "\n Location: " + ticket.getLocation() +
                        "\n Start Date: " + ticket.getStartDate() +
                        "\n End Date: " + ticket.getEndDate() +
                        "\n Description: " + ticket.getDescription() + "\n "
        ).setBold().setFontSize(14);
        textOver.setTextAlignment(TextAlignment.CENTER);

        qrCodeImage.setHorizontalAlignment(HorizontalAlignment.CENTER);

        Paragraph textUnder = new Paragraph("Billet ID: " + String.valueOf(sales)).setItalic().setFontSize(12);
        textUnder.setTextAlignment(TextAlignment.CENTER);

        document.add(textOver);
        document.add(qrCodeImage);
        document.add(textUnder);

        for (Coupon coupon : ticket.getCouponList()) {
            Paragraph text = new Paragraph(coupon.getName());
            text.setTextAlignment(TextAlignment.CENTER);
            document.add(text);

            Image qrCode = createQrCodeImage(coupon.getId() + "-" + coupon.getExpiryDate());
            qrCode.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(qrCode);

        }

        document.close();

        Desktop.getDesktop().open(new File(pdfPath));
    }

    public void fillMap(List<Region> items, double width, double height){
        for (Region item : items) {
            windowItems.put(item, new ArrayList<>(){{
                add(item.getPrefWidth() / width);
                add(item.getPrefHeight() / height);
                add(item.getLayoutX() / width);
                add(item.getLayoutY() / height);}}
            );
        }
    }

    @Override
    public void resizeItems(double width, double height) {
        resizeItems(windowItems, imageViews, width, height);

    }

    @Override
    public void setManager(ControllerManager manager) {
        this.manager = manager;
    }

    @Override
    public void setControllerRoot(IController controller) {
        root = controller;
    }

    @FXML
    private void loadMain(ActionEvent event) {
        root.reload();
    }

    public void setEvent(Event event) {
        editEvent = event;
        txtEvent.setText("Event: " + event.getName());
    }

    private void loadCouponsForEvent(Event event){
        try {
            List<Coupon> coupons = manager.getCouponModel().getCouponsByEventID(event.getId());
            loadCouponsAvailable(coupons);
            loadCouponsSelected(new ArrayList<>(List.of(/*new Coupon("", 0, "")*/)));

        } catch (Exception e) {
            ShowAlerts.displayMessage("Coupon Error", "Database error:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void addCoupon(ActionEvent actionEvent) {
        Coupon selCoupon = tblCouponsAvailable.getSelectionModel().getSelectedItem();
        if (selCoupon != null && !selCoupon.getName().isEmpty()) {
            /*
            if (tblCouponsSelected.getItems().getFirst().getName().isEmpty()){
                tblCouponsSelected.getItems().clear();
            }
             */
            tblCouponsSelected.getItems().add(selCoupon);
            tblCouponsAvailable.getItems().remove(selCoupon);
            totalPrice += selCoupon.getPrice();
            lblTotPrice.setText("Total price: " + totalPrice + valutaSuffix);
            if (tblCouponsAvailable.getItems().isEmpty()) {
               // tblCouponsAvailable.getItems().add(new Coupon("", 0, ""));
            }
        }
    }

    @FXML
    private void removeEvent(ActionEvent actionEvent){
        Coupon selCoupon = tblCouponsSelected.getSelectionModel().getSelectedItem();
        if (selCoupon != null){
            /*
            if (tblCouponsAvailable.getItems().getFirst().getName().isEmpty()){
                tblCouponsAvailable.getItems().clear();
            }
             */
            tblCouponsAvailable.getItems().add(selCoupon);
            tblCouponsSelected.getItems().remove(selCoupon);
            totalPrice -= selCoupon.getPrice();
            lblTotPrice.setText("Total price: " + totalPrice + valutaSuffix);

            if (tblCouponsSelected.getItems().isEmpty()) {
                //tblCouponsSelected.getItems().add(new Coupon(" ", 0, ""));
            }
        }
    }

    private void loadCouponsAvailable(List<Coupon> coupons) {
        colAvailPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAvailTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCouponsAvailable.setItems(FXCollections.observableArrayList());
        tblCouponsAvailable.getItems().addAll(coupons);

    }
    private void loadCouponsSelected(List<Coupon> coupons) {
        colSelectPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSelectTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCouponsSelected.setItems(FXCollections.observableArrayList());
        tblCouponsSelected.getItems().addAll(coupons);

    }
}
