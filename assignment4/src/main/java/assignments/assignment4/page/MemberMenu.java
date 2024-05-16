package assignments.assignment4.page;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import assignments.assignment3.payment.*;

public abstract class MemberMenu {
    private Scene scene;

    abstract protected Scene createBaseMenu();
    protected static ArrayList<Restaurant> restoList = new ArrayList<>();
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
