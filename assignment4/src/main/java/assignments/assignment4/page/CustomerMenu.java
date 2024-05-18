package assignments.assignment4.page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import assignments.assignment3.DepeFood;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.payment.Menu;
import assignments.assignment3.payment.Restaurant;
import assignments.assignment3.payment.User;
import assignments.assignment3.payment.Order;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private static Label label = new Label();
    private MainApp mainApp;
    private User user;


    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp, this.user); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }

    @Override
    public Scene createBaseMenu() {
        VBox menuLayout = new VBox(10);
        menuLayout.setPadding(new Insets(10));
        menuLayout.setAlignment(Pos.CENTER);
        String buttonStyle = "-fx-background-color: linear-gradient(#4facfe, #00f2fe); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-font-weight: bold; " + 
                            "-fx-font-size: 14px;"; 

        DropShadow shadow = new DropShadow(10, Color.GREY);

        Button buatPesananButton = new Button("Buat Pesanan"); //button buat pesanan
        buatPesananButton.setStyle(buttonStyle);
        buatPesananButton.setEffect(shadow);
        menuLayout.getChildren().add(buatPesananButton);

        Button cetakBillButton = new Button("Cetak Bill"); //button cetak bill
        cetakBillButton.setStyle(buttonStyle);
        cetakBillButton.setEffect(shadow);
        menuLayout.getChildren().add(cetakBillButton);

        Button bayarBillButton = new Button("Bayar Bill"); //button bayar bill
        bayarBillButton.setStyle(buttonStyle);
        bayarBillButton.setEffect(shadow);
        menuLayout.getChildren().add(bayarBillButton);

        Button cekSaldoButton = new Button("Cek Saldo"); //button cek saldo
        cekSaldoButton.setStyle(buttonStyle);
        cekSaldoButton.setEffect(shadow);
        menuLayout.getChildren().add(cekSaldoButton);

        Button logoutButton = new Button("Logout"); //button logout
        logoutButton.setStyle(buttonStyle);
        logoutButton.setEffect(shadow);
        menuLayout.getChildren().add(logoutButton);

        //action on button
        buatPesananButton.setOnAction(e->mainApp.setScene(createTambahPesananForm()));
        cetakBillButton.setOnAction(e-> mainApp.setScene(createBillPrinter()));
        cekSaldoButton.setOnAction(e->mainApp.setScene(createCekSaldoScene()));
        bayarBillButton.setOnAction(e->mainApp.setScene(createBayarBillForm()));
        logoutButton.setOnAction(e -> mainApp.logout());
        return new Scene(menuLayout, 400, 600);
    }


    private Scene createTambahPesananForm(){ //Scene for tambah pesanan
        VBox menuLayout = new VBox(10);
        menuLayout.setPadding(new Insets(10));
        menuLayout.setAlignment(Pos.CENTER);
        String buttonStyle = "-fx-background-color: linear-gradient(#4facfe, #00f2fe); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-font-weight: bold; " + 
                            "-fx-font-size: 14px;"; 
    
        DropShadow shadow = new DropShadow(10, Color.GREY);
    
        Label restoNameLabel = new Label("Restaurant:");//Restoran label
        restoNameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        restoNameLabel.setTextFill(Color.web("#333333"));
        menuLayout.getChildren().add(restoNameLabel);
    
        // Populate ComboBox with restaurant names from restoList
        ArrayList<String> restoName = new ArrayList<>(); //Making a restaurant name list for combo box
        for (Restaurant resto : restoList) {
            restoName.add(resto.getNama());
        }
    
        restaurantComboBox.setPromptText("Select A Restaurant");
        restaurantComboBox.setItems(FXCollections.observableArrayList(restoName));
        restaurantComboBox.setStyle("-fx-background-radius: 15;");
        menuLayout.getChildren().add(restaurantComboBox); //Make the combo box
    
        Label dateLabel = new Label("Date (DD/MM/YYYY):"); //Date label
        dateLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        dateLabel.setTextFill(Color.web("#333333"));
        menuLayout.getChildren().add(dateLabel);
    
        TextField dateInput = new TextField(); //Date input
        dateInput.setPromptText("Enter your date");
        dateInput.setStyle("-fx-background-radius: 15; -fx-padding: 10;");
        menuLayout.getChildren().add(dateInput);
    
        Label menuItemsLabel = new Label("Menu Items:"); //menu input
        menuItemsLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        menuItemsLabel.setTextFill(Color.web("#333333"));
        menuLayout.getChildren().add(menuItemsLabel);
        
        Button menuButton = new Button("Menu"); //Menu button
        menuButton.setStyle(buttonStyle);
        menuButton.setEffect(shadow);
        menuLayout.getChildren().add(menuButton);

        // ListView for menu items
        ListView<String> menuItemsListView = new ListView<>();
        menuItemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        menuLayout.getChildren().add(menuItemsListView);
    
        // Handle Menu button click to update ListView with menu items of the selected restaurant
        menuButton.setOnAction(e -> {
            String selectedRestaurantName = restaurantComboBox.getValue();
            if (selectedRestaurantName != null) {
                Restaurant selectedRestaurant = restoList.stream()
                    .filter(resto -> resto.getNama().equals(selectedRestaurantName))
                    .findFirst()
                    .orElse(null);
                if (selectedRestaurant != null) {
                    List<String> menuItems = selectedRestaurant.getMenu()
                        .stream()
                        .map(Menu::getNamaMakanan)
                        .collect(Collectors.toList());
                    menuItemsListView.setItems(FXCollections.observableArrayList(menuItems));
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please select a restaurant first!");
                alert.showAndWait();
            }
        });
    
        Button buatPesananButton = new Button("Buat Pesanan"); //buat pesanan button
        buatPesananButton.setStyle(buttonStyle);
        buatPesananButton.setEffect(shadow);
        menuLayout.getChildren().add(buatPesananButton);
    
        Button kembaliButton = new Button("Kembali"); //kembali button
        kembaliButton.setStyle(buttonStyle);
        kembaliButton.setEffect(shadow);
        menuLayout.getChildren().add(kembaliButton);
        
        //action on button
        buatPesananButton.setOnAction(e-> handleBuatPesanan(restaurantComboBox.getValue(),dateInput.getText(),menuItemsListView.getSelectionModel().getSelectedItems()));
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu()));
        return new Scene(menuLayout, 400, 600);
    }  

    private Scene createBillPrinter(){ //scene for create bill
        VBox menuLayout = new VBox(10);
        menuLayout.setPadding(new Insets(10));
        menuLayout.setAlignment(Pos.CENTER);
        String buttonStyle = "-fx-background-color: linear-gradient(#4facfe, #00f2fe); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-font-weight: bold; " + 
                            "-fx-font-size: 14px;"; 

        DropShadow shadow = new DropShadow(10, Color.GREY);

        TextField orderIDInput = new TextField(); //Text field for order id
        orderIDInput.setPromptText("Enter your OrderID");
        orderIDInput.setStyle("-fx-background-radius: 15; -fx-padding: 10;");
        menuLayout.getChildren().add(orderIDInput);

        Button cetakBillButton = new Button("Print Bill"); //button for print bill
        cetakBillButton.setStyle(buttonStyle);
        cetakBillButton.setEffect(shadow);
        menuLayout.getChildren().add(cetakBillButton);

        Button kembaliButton = new Button("Kembali"); //kembali button
        kembaliButton.setStyle(buttonStyle);
        kembaliButton.setEffect(shadow);
        menuLayout.getChildren().add(kembaliButton);

        //Action on button and run the program on bill printer.java
        cetakBillButton.setOnAction(e->{
            billPrinter = new BillPrinter(stage, mainApp, user);
            Scene sceneTemp = billPrinter.getScene(orderIDInput.getText(), printBillScene);
            if(sceneTemp!=null){
                mainApp.setScene(sceneTemp);
            }
        });

        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu()));
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createBayarBillForm() { //Scene for bayar bill
        VBox menuLayout = new VBox(10);
        menuLayout.setPadding(new Insets(10));
        menuLayout.setAlignment(Pos.CENTER);
        String buttonStyle = "-fx-background-color: linear-gradient(#4facfe, #00f2fe); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-font-weight: bold; " + 
                            "-fx-font-size: 14px;"; 

        DropShadow shadow = new DropShadow(10, Color.GREY);

        TextField orderIDInput = new TextField(); //Order id text field
        orderIDInput.setPromptText("Masukkan Order ID");
        orderIDInput.setStyle("-fx-background-radius: 15; -fx-padding: 10;");
        menuLayout.getChildren().add(orderIDInput);

        ComboBox<String> paymentOptionsComboBox = new ComboBox<>(); //Combo box for payment options
        paymentOptionsComboBox.setPromptText("Pilih Opsi Pembayaran");
        paymentOptionsComboBox.setItems(FXCollections.observableArrayList("Credit Card", "Debit"));
        paymentOptionsComboBox.setStyle("-fx-background-radius: 15;");
        menuLayout.getChildren().add(paymentOptionsComboBox);

        Button bayarButton = new Button("Bayar"); //bayar button
        bayarButton.setStyle(buttonStyle);
        bayarButton.setEffect(shadow);
        menuLayout.getChildren().add(bayarButton);

        Button kembaliButton = new Button("Kembali"); //kembali button
        kembaliButton.setStyle(buttonStyle);
        kembaliButton.setEffect(shadow);
        menuLayout.getChildren().add(kembaliButton);
        
        //Action on button
        bayarButton.setOnAction(e->handleBayarBill(orderIDInput.getText(), paymentOptionsComboBox.getValue()));
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu()));
        return new Scene(menuLayout, 400,600);
    }


    private Scene createCekSaldoScene() { //Scene for cek saldo
        VBox menuLayout = new VBox(10);
        menuLayout.setPadding(new Insets(10));
        menuLayout.setAlignment(Pos.CENTER);
        String buttonStyle = "-fx-background-color: linear-gradient(#4facfe, #00f2fe); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-font-weight: bold; " + 
                            "-fx-font-size: 14px;"; 

        DropShadow shadow = new DropShadow(10, Color.GREY);

        Label nameLabel = new Label(user.getNama()); //Name label
        nameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        nameLabel.setTextFill(Color.web("#333333"));
        menuLayout.getChildren().add(nameLabel);

        long saldo = user.getSaldo();
        String saldoString = String.valueOf(saldo);
        Label saldoLabel = new Label("Saldo: Rp " + saldoString); //Saldo Label
        saldoLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        saldoLabel.setTextFill(Color.web("#333333"));
        menuLayout.getChildren().add(saldoLabel);

        Button kembaliButton = new Button("Kembali"); //button kembali
        kembaliButton.setStyle(buttonStyle);
        kembaliButton.setEffect(shadow);
        menuLayout.getChildren().add(kembaliButton);

        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu()));
        return new Scene(menuLayout, 400, 600);

    }
    public static int convertCTV(char c) {  //Function untuk mengubah character menjadi suatu value
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 10;
        } else {
            return -1;
        }
    }

    public static char convertVTC(int value) { //Function untuk mengembalikan character berdasarkan value yang dipunya
        if (value >= 0 && value <= 9) {
            return (char) (value + '0');
        } else if (value >= 10 && value <= 35) {
            return (char) (value - 10 + 'A');
        } else {
            return '?';
        }
    }
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) { //Function untuk generate order ID
        String output = ""; 
        char compare = ' '; //Mengambil 4 karakter pertama
        for(int i = 0;  i<namaRestoran.length(); i++){
            char current  = namaRestoran.charAt(i);
            if(output.length()<4 && (current != compare)){
                output += Character.toUpperCase(current);
            }
            else if(output.length()<4 && (current == compare)){
                continue;
            }
            if(output.length()==4){
                break;
            }
        }

        String [] tanggalSplit = tanggalOrder.split("/");
        for(String tggl : tanggalSplit){
            output += tggl; //Mengekstaksi input tanggal
        }
        
        int totalDigitNoTelp = 0;
        for(int k = 0; k<noTelepon.length();k++){
            totalDigitNoTelp += Character.getNumericValue(noTelepon.charAt(k)); //Menjumlahkan seluruh digit nomor telepon
        }
        int digitNoTelp = totalDigitNoTelp%100;
        if(digitNoTelp<10){
            output += "0"+String.valueOf(digitNoTelp);
        }
        else{
            output += String.valueOf(digitNoTelp);
        }
        

        int totalCheckSum1 = 0;
        int totalCheckSum2 = 0;
        for(int l = 0; l<output.length();l++){
            if(l%2==0){
                totalCheckSum1 += convertCTV(output.charAt(l)); //Membuat checksum index genap
            }
            else{
                totalCheckSum2 += convertCTV(output.charAt(l)); //Membuat checksum index ganjil
            }
        }

        int checkSum1 = totalCheckSum1%36;
        int checkSum2 = totalCheckSum2%36;

        char check1 = convertVTC(checkSum1);
        char check2 = convertVTC(checkSum2);

        output += check1;
        output+= check2;

        return output;
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Validasi tanggal
        try {
            LocalDate tanggal = LocalDate.parse(tanggalPemesanan, formatter);
        } catch (Exception e) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error");
            alertError.setContentText("Wrong format for date!");
            alertError.showAndWait();
            return; 
        }
    
        Restaurant restaurant = null;
        for (Restaurant resto : restoList) {
            if (namaRestoran.equals(resto.getNama())) {
                restaurant = resto;
                break;
            }
        }
    
        if (restaurant == null) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error");
            alertError.setContentText("Restaurant not found!");
            alertError.showAndWait();
            return; // Exit method if restaurant is not found
        }
    
        Menu[] items = new Menu[menuItems.size()]; //Making the list of the menu that already ordered
        int index = 0;
        for (String itemName : menuItems) {
            boolean found = false;
            for (Menu menu : restaurant.getMenu()) {
                if (menu.getNamaMakanan().equals(itemName)) {
                    items[index] = menu;
                    found = true;
                    break;
                }
            }
            if (!found) {
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Error");
                alertError.setHeaderText("Error");
                alertError.setContentText("Menu item " + itemName + " not found in restaurant!");
                alertError.showAndWait();
                return; // Exit method if any menu item is not found
            }
            index++;
        }
    
        String orderID = generateOrderID(namaRestoran, tanggalPemesanan, user.getNomorTelepon()); // Membuat order ID
        Order newOrder = new Order(orderID, tanggalPemesanan, user.getOngkir(user.getLokasi()), restaurant, items); // Membuat object order dan menambahkannya ke order history
        user.addOrderHistory(newOrder);
    
        Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION); //alert success information
        alertSuccess.setTitle("Success");
        alertSuccess.setContentText("Order dengan ID " + orderID + " berhasil ditambahkan");
        alertSuccess.showAndWait();
    }

    private void handleBayarBill(String orderID, String pilihanPembayaran) {
        Order odr = null;
        for(Order order : user.getOrderHistory()){
            if(order.getOrderId().equals(orderID)){
                odr = order;
                break;
            }
        }
        if(odr == null){ //validation for null
            Alert alertError = new Alert(AlertType.ERROR);
            alertError.setTitle("Error");
            alertError.setHeaderText("Error");
            alertError.setContentText("Order ID not found!");
            alertError.showAndWait();
        }
        else{
            long totalPembayaran = (long) odr.getTotalHarga();
            long saldo = (long) user.getSaldo();
            if(user.getPaymentSystem() instanceof CreditCardPayment && (pilihanPembayaran.equals("Credit Card"))){ //Credit card option
                    if(odr.getOrderFinished()){ //Validation for finished order
                        Alert alertError = new Alert(AlertType.ERROR);
                        alertError.setTitle("Error");
                        alertError.setHeaderText("Error");
                        alertError.setContentText("Order ID sudah lunas!");
                        alertError.showAndWait();   
                    }
                    else{
                        CreditCardPayment payment = (CreditCardPayment) user.getPaymentSystem();
                        long charge = payment.countTransactionFee(totalPembayaran);
                        long biayaResult = payment.processPayment(saldo,totalPembayaran);
                        user.setSaldo(user.getSaldo()-biayaResult);
                        odr.getRestaurant().setSaldo(odr.getRestaurant().getSaldo()+totalPembayaran);
                        odr.setOrderFinished(true);

                        Alert alertInformation = new Alert(AlertType.INFORMATION);
                        alertInformation.setTitle("Success");
                        alertInformation.setContentText("Berhasil Membayar Bill sebesar Rp " + totalPembayaran + " dengan biaya transaksi sebesar Rp " + charge);

                        // Access the DialogPane and set the preferred size
                        DialogPane dialogPane = alertInformation.getDialogPane();
                        dialogPane.setMinHeight(Region.USE_PREF_SIZE);
                        dialogPane.setMinWidth(Region.USE_PREF_SIZE);
                        dialogPane.setPrefSize(500, 100); // Adjust the width and height as needed
                        alertInformation.showAndWait(); 
                    }
                }
                else if (((user.getPaymentSystem() instanceof CreditCardPayment) && pilihanPembayaran.equals("Debit")) || 
                    ((user.getPaymentSystem() instanceof DebitPayment) && pilihanPembayaran.equals("Credit Card"))) {
                        Alert alertError = new Alert(AlertType.ERROR); //Validation for payment method
                        alertError.setTitle("Error");
                        alertError.setHeaderText("Error");
                        alertError.setContentText("Invalid Payment Method");
                        alertError.showAndWait();   
                }

                else if(user.getPaymentSystem() instanceof DebitPayment && pilihanPembayaran.equals("Debit")){ //Debit option
                    DebitPayment payment = (DebitPayment) user.getPaymentSystem();
                    long biayaResult = payment.processPayment(user.getSaldo(),totalPembayaran);
                    if(odr.getOrderFinished()){
                        Alert alertError = new Alert(AlertType.ERROR); //Validation for finished order
                        alertError.setTitle("Error");
                        alertError.setHeaderText("Error");
                        alertError.setContentText("Order ID sudah lunas!");
                        alertError.showAndWait();  
                    }
                    else{
                        if(user.getSaldo()>=biayaResult){ //Payment process
                            payment.processPayment(user.getSaldo(),totalPembayaran);
                            user.setSaldo(user.getSaldo()-biayaResult);
                            odr.getRestaurant().setSaldo(odr.getRestaurant().getSaldo()+totalPembayaran);
                            odr.setOrderFinished(true);

                            Alert alertInformation = new Alert(AlertType.INFORMATION); 
                            alertInformation.setTitle("Success");
                            alertInformation.setContentText("Berhasil Membayar Bill sebesar Rp " + totalPembayaran);
                            alertInformation.showAndWait(); 
                        }
                        else{
                            Alert alertError = new Alert(AlertType.ERROR);
                            alertError.setTitle("Error");
                            alertError.setHeaderText("Not enough balance!");
                            alertError.showAndWait();  
                        }
                    }
                }
            }
    }
}