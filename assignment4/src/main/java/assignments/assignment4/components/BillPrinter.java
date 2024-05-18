package assignments.assignment4.components;

import java.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import assignments.assignment3.DepeFood;
import assignments.assignment3.payment.Menu;
import assignments.assignment3.payment.Order;
import assignments.assignment3.payment.User;
import assignments.assignment4.MainApp;

public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    private Scene createBillPrinterForm(Order order, Scene scene){
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        String buttonStyle = "-fx-background-color: linear-gradient(#4facfe, #00f2fe); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-font-weight: bold; " + 
                            "-fx-font-size: 14px;"; 

        DropShadow shadow = new DropShadow(10, Color.GREY);

        Label billLabel = new Label("Bill");
        billLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
        billLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(billLabel);

        Label orderIDLabel = new Label("Order ID: " + order.getOrderId());
        orderIDLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
        orderIDLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(orderIDLabel);

        Label tanggalLabel = new Label("Tanggal Pemesanan: " + order.getTanggal());
        tanggalLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
        tanggalLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(tanggalLabel);

        Label restaurantLabel = new Label("Restaurant: " + order.getRestaurant().getNama());
        restaurantLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
        restaurantLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(restaurantLabel);

        Label lokasiLabel = new Label("Lokasi pengiriman: " + user.getLokasi());
        lokasiLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
        lokasiLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(lokasiLabel);

        if(order.getOrderFinished()==false){
            Label statusLabel = new Label("Status Pengiriman: Not Finished");
            statusLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
            statusLabel.setTextFill(Color.web("#333333"));
            layout.getChildren().add(statusLabel);
        }
        else if(order.getOrderFinished()==true){
            Label statusLabel = new Label("Status Pengiriman: Finished");
            statusLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
            statusLabel.setTextFill(Color.web("#333333"));
            layout.getChildren().add(statusLabel);
        }

        Label pesananLabel = new Label("Pesanan:");
        pesananLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
        pesananLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(pesananLabel);

        for(Menu menu : order.getItems()){
            Label menuMakananLabel = new Label("- " + menu.getNamaMakanan() + " " + menu.getHarga());
            menuMakananLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
            menuMakananLabel.setTextFill(Color.web("#333333"));
            layout.getChildren().add(menuMakananLabel);
        }

        Label ongkirLabel = new Label("Biaya ongkos kirim: Rp " + order.getOngkir());
        ongkirLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
        ongkirLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(ongkirLabel);

        Label totalBiayaLabel = new Label("Total biaya: Rp " + order.getTotalHarga());
        totalBiayaLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 11));
        totalBiayaLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(totalBiayaLabel);

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle(buttonStyle);
        kembaliButton.setEffect(shadow);
        layout.getChildren().add(kembaliButton);

        kembaliButton.setOnAction(e -> mainApp.setScene(scene));

        return new Scene(layout, 400, 400);
    }

    private Order printBill(String orderId) {
        Order odr = null;
        for(Order order : user.getOrderHistory()){
            if(order.getOrderId().equals(orderId)){
                odr = order;
                break;
            }
        }
        return odr;
    }

    public Scene getScene(String order, Scene scene) {
        Order orderObj = printBill(order);
        if(orderObj==null){
            Alert alertError = new Alert(AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error");
            alertError.setContentText("Order ID not found!");
            alertError.showAndWait();
            return null;
        }
        return this.createBillPrinterForm(orderObj, scene);
    }

}
