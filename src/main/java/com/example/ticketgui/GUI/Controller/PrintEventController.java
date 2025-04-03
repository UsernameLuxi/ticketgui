package com.example.ticketgui.GUI.Controller;

import com.example.ticketgui.BE.Coupon;
import com.example.ticketgui.BE.Event;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import com.google.zxing.BarcodeFormat;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintEventController extends Controller {
    private ControllerManager manager;
    private IController root;
    private EventModel model;
    private Event editEvent = null;
    private int totalPrice = 0;
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


        btnPrint.setOnAction(event -> {
            try {
                printTicket();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        lblPrice.setText("Event price: " + editEvent.getPrice());
        totalPrice = editEvent.getPrice();
        lblTotPrice.setText("Total price: " + totalPrice);
        loadCouponsForEvent(editEvent);
    }


    private void printTicket() throws Exception {
        int sales = model.incrementSale(editEvent);
        String data = editEvent.getId() + "-" + sales;

        Files.deleteIfExists(Path.of("QRCode.pdf"));

        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", byteArrayOutputStream);
        byte[] qrImageBytes = byteArrayOutputStream.toByteArray();

        String pdfPath = "QRCode.pdf";
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph(editEvent.getName()).setBold().setFontSize(14));
        ImageData imageData = ImageDataFactory.create(qrImageBytes);
        Image qrCodeImage = new Image(imageData);
        document.add(qrCodeImage);
        document.add(new Paragraph("Billet ID: " + String.valueOf(sales)).setItalic().setFontSize(12));

        document.close();
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
        } catch (Exception e) {
            ShowAlerts.displayMessage("Coupon Error", "Database error:\n" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
