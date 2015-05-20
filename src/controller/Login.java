package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

import static main.Main.showOnScreen;

public class Login implements Initializable {
    public static Button geauxButton;
    public ImageView logoView;
    public ToggleButton sizeToggle;

    public void geauxAction(ActionEvent actionEvent) throws Exception {
        Main.primaryFXML = "home";
        Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Main.primaryStage.setScene(new Scene(Main.pRoot));

        if (Main.isExtended == true) {
            Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
            Main.calendarStage.getScene().getStylesheets().add("/view/extStyle.css");
            showOnScreen(4, Main.mapStage);
            showOnScreen(2, Main.infoStage);
        }

        Calendar.data.get(11).setTuesProp("CSC 4243");
        Calendar.data.get(11).setThursProp("CSC 4243");
        Calendar.data.get(16).setTuesProp("CSC 4243 (Lab)");
        Calendar.data.get(16).setThursProp("CSC 4243 (Lab)");
        showOnScreen(2, Main.calendarStage);
    }

    public void sizeAction(ActionEvent actionEvent) {
        if (sizeToggle.isSelected()) {
            sizeToggle.textProperty().setValue("Extended");
            Main.isExtended = true;
        } else {
            sizeToggle.textProperty().setValue("Normal");
            Main.isExtended = false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoView.fitHeightProperty().bind(Main.primaryStage.heightProperty());
        logoView.fitWidthProperty().bind(Main.primaryStage.widthProperty());
    }
}
