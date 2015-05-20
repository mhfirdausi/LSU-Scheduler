package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.Main.showOnScreen;

public class Home implements Initializable {
    public ImageView logoView;
    public ToggleButton sizeToggle;
    public TextField eventText;
    public TextField dayText;
    public TextField timeText;

    public void sizeAction(ActionEvent actionEvent) {
        if (Main.isExtended == true) {
            sizeToggle.textProperty().setValue("Normal");
            Main.primaryStage.getScene().getStylesheets().remove(0);
            Main.calendarStage.getScene().getStylesheets().remove(0);
            Main.mapStage.hide();
            Main.infoStage.hide();
            Main.isExtended = false;
        } else {
            sizeToggle.textProperty().setValue("Extended");
            Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
            Main.calendarStage.getScene().getStylesheets().add("/view/extStyle.css");
            showOnScreen(3, Main.mapStage);
            showOnScreen(1, Main.infoStage);
            Main.isExtended = true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoView.fitHeightProperty().bind(Main.primaryStage.heightProperty());
        logoView.fitWidthProperty().bind(Main.primaryStage.widthProperty());

        if (Main.isExtended == true)
            sizeToggle.textProperty().setValue("Extended");
        else
            sizeToggle.textProperty().setValue("Normal");
    }

    public void addAction(ActionEvent actionEvent) {
        String dayData = dayText.getText().toLowerCase();
        String eventData = eventText.getText();
        String timeData = timeText.getText().toLowerCase();

        if (!dayData.equals("") && !timeData.equals("") && !eventData.equals("")) {
            int time;
            if (timeData.contains("all day"))
                time = 0;
            else if (timeData.substring(1, 2).equals(":") || timeData.substring(1, 2).equals(" "))
                time = Integer.parseInt(timeData.substring(0, 1));
            else
                time = Integer.parseInt(timeData.substring(0, 2));

            int base;
            if (time == 12) {
                if (timeData.contains("pm"))
                    base = 1;
                else
                    base = -11;
            } else if (timeData.contains("am"))
                base = 1;
            else if (timeData.contains("pm"))
                base = 13;
            else base = 0;

            int row = base + time;
            switch (dayData) {
                case "sunday":
                    Calendar.data.get(row).setSunProp(eventData);
                    break;
                case "monday":
                    Calendar.data.get(row).setMonProp(eventData);
                    break;
                case "tuesday":
                    Calendar.data.get(row).setTuesProp(eventData);
                    break;
                case "wednesday":
                    Calendar.data.get(row).setWedProp(eventData);
                    break;
                case "thursday":
                    Calendar.data.get(row).setThursProp(eventData);
                    break;
                case "friday":
                    Calendar.data.get(row).setFriProp(eventData);
                    break;
                case "saturday":
                    Calendar.data.get(row).setSatProp(eventData);
                    break;
            }
        }
    }

    public void searchAction(ActionEvent actionEvent) throws IOException {
        String eventData = eventText.getText().toLowerCase();
        if (eventData.contains("lab")) {
            for (int i = 0; i < Calendar.data.size(); i++) {
                if (Calendar.data.get(i).toString().contains("Lab") && !Main.primaryFXML.equals("lab")) {
                    Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Lab.fxml"));
                    Main.primaryStage.setScene(new Scene(Main.pRoot));
                    if (Main.isExtended) {
                        Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                    }
                    return;
                }
            }
        } else if (eventData.contains("4243")) {
            for (int i = 0; i < Calendar.data.size(); i++) {
                if (Calendar.data.get(i).toString().contains("4243") && !Main.primaryFXML.equals("4243")) {
                    Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Lecture.fxml"));
                    Main.primaryStage.setScene(new Scene(Main.pRoot));
                    if (Main.isExtended) {
                        Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                    }
                    return;
                }
            }
        } else if (eventData.contains("459")) {
            for (int i = 0; i < Calendar.data.size(); i++) {
                if (Calendar.data.get(i).toString().contains("4243") && !Main.primaryFXML.equals("459")) {
                    Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Lecture.fxml"));
                    Main.primaryStage.setScene(new Scene(Main.pRoot));
                    if (Main.isExtended) {
                        Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                    }
                    return;
                }
            }
        } else {
            if (!Main.primaryFXML.equals("home")) {
                Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
                Main.primaryStage.setScene(new Scene(Main.pRoot));
                if (Main.isExtended) {
                    Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                }
            }
        }
    }
}
