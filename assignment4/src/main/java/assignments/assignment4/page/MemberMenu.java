package assignments.assignment4.page;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import assignments.assignment3.payment.*;
//Abstract classs for customer menu and admin menu
public abstract class MemberMenu {
    private Scene scene;

    abstract protected Scene createBaseMenu(); //abstract method
    protected static ArrayList<Restaurant> restoList = new ArrayList<>(); //array list for restaurant
    protected void showAlert(String title, String header, String content, Alert.AlertType c){
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Scene getScene(){
        return this.scene;
    }

}
