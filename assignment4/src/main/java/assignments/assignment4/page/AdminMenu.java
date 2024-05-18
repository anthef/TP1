package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assignments.assignment3.DepeFood;
import assignments.assignment3.payment.Restaurant;
import assignments.assignment3.payment.User;
import assignments.assignment3.payment.Menu;
import assignments.assignment4.MainApp;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu {
    private Stage stage; //Attribute
    private Scene scene;
    private User user;
    private Restaurant resto;
    private ArrayList<String> restaurantNames = new ArrayList<>();
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private TextField restaurantNameInput;
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();

    public AdminMenu(Stage stage, MainApp mainApp, User user) { //Constructor
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

    @Override
    public Scene createBaseMenu() { //Base menu
        VBox menuLayout = new VBox(10);
        menuLayout.setPadding(new Insets(10));
        menuLayout.setAlignment(Pos.CENTER);
        String buttonStyle = "-fx-background-color: linear-gradient(#4facfe, #00f2fe); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-font-weight: bold; " + 
                            "-fx-font-size: 14px;"; 

        DropShadow shadow = new DropShadow(10, Color.GREY);
        //Button for admin
        Button tambahRestoranButton = new Button("Tambah Restoran");
        tambahRestoranButton.setStyle(buttonStyle);
        tambahRestoranButton.setEffect(shadow);
        menuLayout.getChildren().add(tambahRestoranButton);

        Button tambahMenuButton = new Button("Tambah Menu Restoran");
        tambahMenuButton.setStyle(buttonStyle);
        tambahMenuButton.setEffect(shadow);
        menuLayout.getChildren().add(tambahMenuButton);

        Button lihatDaftarRestoranButton = new Button("Lihat Daftar Restoran");
        lihatDaftarRestoranButton.setStyle(buttonStyle);
        lihatDaftarRestoranButton.setEffect(shadow);
        menuLayout.getChildren().add(lihatDaftarRestoranButton);

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle(buttonStyle);
        logoutButton.setEffect(shadow);
        menuLayout.getChildren().add(logoutButton);

        //Action on button
        tambahRestoranButton.setOnAction(e -> mainApp.setScene(createAddRestaurantForm()));
        tambahMenuButton.setOnAction(e -> mainApp.setScene(createAddMenuForm()));
        lihatDaftarRestoranButton.setOnAction(e -> mainApp.setScene(createViewRestaurantsForm()));
        logoutButton.setOnAction(e -> mainApp.logout());
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createAddRestaurantForm() { //Scene add restaurant
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #F0F4F7;");

        String buttonStyle = "-fx-background-color: linear-gradient(#4facfe, #00f2fe); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-font-weight: bold; " + 
                            "-fx-font-size: 14px;"; 

        DropShadow shadow = new DropShadow(10, Color.GREY);

        //Label and text field for the feature
        Label restaurantNameLabel = new Label("Restaurant Name:");
        restaurantNameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        restaurantNameLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(restaurantNameLabel);

        TextField restoNameInput = new TextField();
        restoNameInput.setPromptText("Enter your restaurant name");
        restoNameInput.setStyle("-fx-background-radius: 15; -fx-padding: 10;");
        layout.getChildren().add(restoNameInput);

        Button submitButton = new Button("Submit");
        submitButton.setStyle(buttonStyle);
        submitButton.setEffect(shadow);
        layout.getChildren().add(submitButton);

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle(buttonStyle);
        kembaliButton.setEffect(shadow);
        layout.getChildren().add(kembaliButton);

        //hover
        submitButton.setOnMouseEntered(e -> submitButton.setScaleX(1.1));
        submitButton.setOnMouseEntered(e -> submitButton.setScaleY(1.1));
        submitButton.setOnMouseExited(e -> submitButton.setScaleX(1.0));
        submitButton.setOnMouseExited(e -> submitButton.setScaleY(1.0));

        kembaliButton.setOnMouseEntered(e -> kembaliButton.setScaleX(1.1));
        kembaliButton.setOnMouseEntered(e -> kembaliButton.setScaleY(1.1));
        kembaliButton.setOnMouseExited(e -> kembaliButton.setScaleX(1.0));
        kembaliButton.setOnMouseExited(e -> kembaliButton.setScaleY(1.0));

        //Action on button
        submitButton.setOnAction(e -> handleTambahRestoran(restoNameInput.getText()));
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu()));
        return new Scene(layout, 400, 600);
    }

    private Scene createAddMenuForm() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #F0F4F7;");

        String buttonStyle = "-fx-background-color: linear-gradient(#4facfe, #00f2fe); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-font-weight: bold; " + 
                            "-fx-font-size: 14px;";

        DropShadow shadow = new DropShadow(10, Color.GREY);

        //Label and text field for add menu
        Label restoNameLabel = new Label("Restaurant Name:");
        restoNameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        restoNameLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(restoNameLabel);

        restaurantNameInput = new TextField();
        restaurantNameInput.setPromptText("Enter your restaurant name");
        restaurantNameInput.setStyle("-fx-background-radius: 15; -fx-padding: 10;");
        layout.getChildren().add(restaurantNameInput);

        Label menuItemLabel = new Label("Menu Item Name:");
        menuItemLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        menuItemLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(menuItemLabel);

        TextField menuItemInput = new TextField();
        menuItemInput.setPromptText("Enter your menu item name");
        menuItemInput.setStyle("-fx-background-radius: 15; -fx-padding: 10;");
        layout.getChildren().add(menuItemInput);

        Label hargaItemLabel = new Label("Price:");
        hargaItemLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        hargaItemLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(hargaItemLabel);

        TextField hargaItemInput = new TextField();
        hargaItemInput.setPromptText("Enter your price");
        hargaItemInput.setStyle("-fx-background-radius: 15; -fx-padding: 10;");
        layout.getChildren().add(hargaItemInput);

        Button addMenuButton = new Button("Add Menu Item");
        addMenuButton.setStyle(buttonStyle);
        addMenuButton.setEffect(shadow);
        layout.getChildren().add(addMenuButton);

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle(buttonStyle);
        kembaliButton.setEffect(shadow);
        layout.getChildren().add(kembaliButton);

        addMenuButton.setOnMouseEntered(e -> addMenuButton.setScaleX(1.1));
        addMenuButton.setOnMouseEntered(e -> addMenuButton.setScaleY(1.1));
        addMenuButton.setOnMouseExited(e -> addMenuButton.setScaleX(1.0));
        addMenuButton.setOnMouseExited(e -> addMenuButton.setScaleY(1.0));

        kembaliButton.setOnMouseEntered(e -> kembaliButton.setScaleX(1.1));
        kembaliButton.setOnMouseEntered(e -> kembaliButton.setScaleY(1.1));
        kembaliButton.setOnMouseExited(e -> kembaliButton.setScaleX(1.0));
        kembaliButton.setOnMouseExited(e -> kembaliButton.setScaleY(1.0));

        //Action on button
        addMenuButton.setOnAction(e -> handleTambahMenuRestoran(menuItemInput.getText(), hargaItemInput.getText()));
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu()));
        return new Scene(layout, 400, 600);
    }

    private void handleTambahRestoran(String nama) {
        //validation for tambah restoran
        String validName = DepeFood.getValidRestaurantName(nama);
        boolean valid = restoList.stream().noneMatch(r -> r.getNama().equals(validName));
        boolean anotherValid = true;
        if(validName==null){
            anotherValid = false;
            valid = false;
            showAlert(Alert.AlertType.ERROR, "Error", "Wrong format!");
        }
        if (valid) {
            Restaurant resto = new Restaurant(validName);
            restoList.add(resto);
            for (Restaurant r : restoList) {
                if(!restaurantNames.contains(r.getNama())){
                    restaurantNames.add(r.getNama());
                }
            }
            restaurantComboBox.setItems(FXCollections.observableArrayList(restaurantNames)); // Update the ComboBox options
            if(anotherValid){
                showAlert(Alert.AlertType.INFORMATION, "Message", "Restaurant successfully registered!");
            }
        } else {
            if(anotherValid){
                showAlert(Alert.AlertType.ERROR, "Error", "Restaurant already exists!");
            }
        }
    }

    private Scene createViewRestaurantsForm() { //Scene for view restoran
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #F0F4F7;");

        String buttonStyle = "-fx-background-color: linear-gradient(#4facfe, #00f2fe); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 15; " +
                            "-fx-font-weight: bold; " + 
                            "-fx-font-size: 14px;";

        DropShadow shadow = new DropShadow(10, Color.GREY);

        Label restaurantNameLabel = new Label("Restaurant Name:");
        restaurantNameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        restaurantNameLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(restaurantNameLabel);

        restaurantComboBox.setStyle("-fx-background-radius: 15;");
        restaurantComboBox.setOnAction(e -> updateMenuItemsListView());
        layout.getChildren().add(restaurantComboBox);

        Label menuLabel = new Label("Menu:");
        menuLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        menuLabel.setTextFill(Color.web("#333333"));
        layout.getChildren().add(menuLabel);

        menuItemsListView.setStyle("-fx-background-radius: 15;");
        layout.getChildren().add(menuItemsListView);

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle(buttonStyle);
        kembaliButton.setEffect(shadow);
        layout.getChildren().add(kembaliButton);

        //hover
        kembaliButton.setOnMouseEntered(e -> kembaliButton.setScaleX(1.1));
        kembaliButton.setOnMouseEntered(e -> kembaliButton.setScaleY(1.1));
        kembaliButton.setOnMouseExited(e -> kembaliButton.setScaleX(1.0));
        kembaliButton.setOnMouseExited(e -> kembaliButton.setScaleY(1.0));

        //Action on button
        kembaliButton.setOnAction(e -> mainApp.setScene(createBaseMenu()));

        return new Scene(layout, 400, 600);
    }

    private void sortMenuItems(ArrayList<Menu> daftarMenu) {
        // Melakukan sorting menu dengan menggunakan bubble sort algorithm
        for (int i = 0; i < daftarMenu.size() - 1; i++) {
            for (int j = 0; j < daftarMenu.size() - i - 1; j++) {
                if (daftarMenu.get(j).getHarga() > daftarMenu.get(j + 1).getHarga()) {
                    Menu temp = daftarMenu.get(j);
                    // Menu akan di swap supaya memiliki urutan yang benar
                    daftarMenu.set(j, daftarMenu.get(j + 1));
                    daftarMenu.set(j + 1, temp);
                } 
                // Kondisi apabila harganya sama
                else if (daftarMenu.get(j).getHarga() == daftarMenu.get(j + 1).getHarga()) {
                    if (daftarMenu.get(j).getNamaMakanan().compareTo(daftarMenu.get(j + 1).getNamaMakanan()) > 0) {
                        Menu temp = daftarMenu.get(j);
                        // Menu akan di swap supaya memiliki urutan yang benar
                        daftarMenu.set(j, daftarMenu.get(j + 1));
                        daftarMenu.set(j + 1, temp);
                    }
                }
            }
        }
    }

    private void updateMenuItemsListView() { //Update the list of menu for list of view
        String selectedRestaurantName = restaurantComboBox.getSelectionModel().getSelectedItem();
        if (selectedRestaurantName == null) {
            menuItemsListView.getItems().clear();
            return;
        }

        Optional<Restaurant> optionalRestaurant = restoList.stream()
            .filter(r -> r.getNama().equalsIgnoreCase(selectedRestaurantName))
            .findFirst();

        if (!optionalRestaurant.isPresent()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Restaurant not found!");
            return;
        }

        Restaurant restaurant = optionalRestaurant.get();
        ArrayList<Menu> menuItems = new ArrayList<>(restaurant.getMenu());

        // Sort the menu items
        sortMenuItems(menuItems);

        ArrayList<String> menuDisplayItems = new ArrayList<>();
        for (Menu menu : menuItems) {
            menuDisplayItems.add(menu.getNamaMakanan() + " - Rp " + menu.getHarga());
        }
        menuItemsListView.setItems(FXCollections.observableArrayList(menuDisplayItems));
    }



    private void handleTambahMenuRestoran(String itemName, String price) {
        //Validation for tambah menu
        String restaurantName = restaurantNameInput.getText().trim();
        Optional<Restaurant> optionalRestaurant = restoList.stream()
            .filter(r -> r.getNama().equalsIgnoreCase(restaurantName))
            .findFirst();

        if (!optionalRestaurant.isPresent()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Restaurant not found!");
            return;
        }

        Restaurant restaurant = optionalRestaurant.get();
        double itemPrice;
        try {
            itemPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid price format!");
            return;
        }

        Menu menu = new Menu(itemName, itemPrice);
        restaurant.addMenu(menu);
        showAlert(Alert.AlertType.INFORMATION, "Message", "Menu item successfully added!");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
