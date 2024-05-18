package assignments.assignment4.components.form; //package

import assignments.assignment3.DepeFood; //import section
import assignments.assignment3.payment.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;
import java.util.function.Consumer;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
    }

    private Scene createLoginForm() {
        GridPane grid = new GridPane(); //Using grid pane
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setStyle("-fx-background-color: #F0F4F7;");

        Label welcome = new Label("Welcome to DepeFood"); //First label
        welcome.setFont(Font.font("Lato", FontWeight.BOLD, 24));
        welcome.setTextFill(Color.web("#0076a3"));
        GridPane.setHalignment(welcome, HPos.CENTER);
        grid.add(welcome, 0, 0, 2, 1);

        Label nameLabel = new Label("Name:"); //Name Label
        nameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        nameLabel.setTextFill(Color.web("#333333"));
        grid.add(nameLabel, 0, 1);

        nameInput = new TextField(); //Text field name
        nameInput.setPromptText("Enter your name");
        nameInput.setStyle("-fx-background-radius: 15; -fx-padding: 10;");
        GridPane.setHgrow(nameInput, Priority.ALWAYS);
        grid.add(nameInput, 1, 1);

        Label telpLabel = new Label("Phone Number:"); //phone label
        telpLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        telpLabel.setTextFill(Color.web("#333333"));
        grid.add(telpLabel, 0, 2);

        phoneInput = new TextField(); //text field phone
        phoneInput.setPromptText("Enter your phone number");
        phoneInput.setStyle("-fx-background-radius: 15; -fx-padding: 10;");
        GridPane.setHgrow(phoneInput, Priority.ALWAYS);
        grid.add(phoneInput, 1, 2);

        Button login = new Button("Login"); //login button
        login.setStyle("-fx-background-color: linear-gradient(#4facfe, #00f2fe); -fx-text-fill: white; -fx-background-radius: 15;");
        login.setEffect(new DropShadow(10, Color.GREY));
        GridPane.setHalignment(login, HPos.RIGHT);
        grid.add(login, 1, 3);
        grid.setAlignment(Pos.CENTER);

        login.setOnAction(e -> handleLogin()); //action on button

        return new Scene(grid, 400, 600);
    }


    private void handleLogin() {
        User userFound = DepeFood.getUser(nameInput.getText(),phoneInput.getText()); //Find the user
        if (userFound==null) { //null validation
            Alert alertError = new Alert(AlertType.ERROR);
            alertError.setTitle("Login Failed");
            alertError.setContentText("User not found!");
            alertError.showAndWait();
        } else {
            String role = userFound.getRole(); //Set the scene for admin and customer
            Scene userScene = mainApp.getScene(userFound.getNama());
            if(role.equals("Admin")){
                if(userScene==null){
                    userScene = new AdminMenu(stage, mainApp, userFound).createBaseMenu();
                }
            }
            else if(role.equals("Customer")){
                if(userScene==null){
                    userScene = new CustomerMenu(stage, mainApp, userFound).createBaseMenu();
                }
            }
            mainApp.addScene(userFound.getNama(),userScene);
            mainApp.setScene(userScene);
        }
    }

    public Scene getScene(){
        return this.createLoginForm();
    }

}
