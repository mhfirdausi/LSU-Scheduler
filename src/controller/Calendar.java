package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import main.Main;
import model.Table;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Calendar implements Initializable {

    public static ObservableList<Table> data = FXCollections.observableArrayList(
            new Table("All Day", null, null, null, null, null, null, null),
            new Table("12:00 am", null, null, null, null, null, null, null),
            new Table("1:00 am", null, null, null, null, null, null, null),
            new Table("2:00 am", null, null, null, null, null, null, null),
            new Table("3:00 am", null, null, null, null, null, null, null),
            new Table("4:00 am", null, null, null, null, null, null, null),
            new Table("5:00 am", null, null, null, null, null, null, null),
            new Table("6:00 am", null, null, null, null, null, null, null),
            new Table("7:00 am", null, null, null, null, null, null, null),
            new Table("8:00 am", null, null, null, null, null, null, null),
            new Table("9:00 am", null, null, null, null, null, null, null),
            new Table("10:00 am", null, null, null, null, null, null, null),
            new Table("11:00 am", null, null, null, null, null, null, null),
            new Table("12:00 pm", null, null, null, null, null, null, null),
            new Table("1:00 pm", null, null, null, null, null, null, null),
            new Table("2:00 pm", null, null, null, null, null, null, null),
            new Table("3:00 pm", null, null, null, null, null, null, null),
            new Table("4:00 pm", null, null, null, null, null, null, null),
            new Table("5:00 pm", null, null, null, null, null, null, null),
            new Table("6:00 pm", null, null, null, null, null, null, null),
            new Table("7:00 pm", null, null, null, null, null, null, null),
            new Table("8:00 pm", null, null, null, null, null, null, null),
            new Table("9:00 pm", null, null, null, null, null, null, null),
            new Table("10:00 pm", null, null, null, null, null, null, null),
            new Table("11:00 pm", null, null, null, null, null, null, null)
    );
    public TableView<Table> calTable;
    public TableColumn<Table, String> dt;
    public TableColumn<Table, String> sun;
    public TableColumn<Table, String> mon;
    public TableColumn<Table, String> tues;
    public TableColumn<Table, String> wed;
    public TableColumn<Table, String> thurs;
    public TableColumn<Table, String> fri;
    public TableColumn<Table, String> sat;
    public WebView tweetView = new WebView();
    public WebEngine tweetEngine = tweetView.getEngine();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dt.prefWidthProperty().bind(calTable.widthProperty().divide(8));
        dt.setCellValueFactory(new PropertyValueFactory<>("dtProp"));

        sun.prefWidthProperty().bind(calTable.widthProperty().divide(8));
        sun.setCellValueFactory(new PropertyValueFactory<>("sunProp"));

        mon.prefWidthProperty().bind(calTable.widthProperty().divide(8));
        mon.setCellValueFactory(new PropertyValueFactory<>("monProp"));

        tues.prefWidthProperty().bind(calTable.widthProperty().divide(8));
        tues.setCellValueFactory(new PropertyValueFactory<>("tuesProp"));

        wed.prefWidthProperty().bind(calTable.widthProperty().divide(8));
        wed.setCellValueFactory(new PropertyValueFactory<>("wedProp"));

        thurs.prefWidthProperty().bind(calTable.widthProperty().divide(8));
        thurs.setCellValueFactory(new PropertyValueFactory<>("thursProp"));

        fri.prefWidthProperty().bind(calTable.widthProperty().divide(8));
        fri.setCellValueFactory(new PropertyValueFactory<>("friProp"));

        sat.prefWidthProperty().bind(calTable.widthProperty().divide(8));
        sat.setCellValueFactory(new PropertyValueFactory<>("satProp"));

        calTable.setItems(data);
    }

    public void mouseClick(Event event) throws IOException {
        String dat = event.getTarget().toString().toLowerCase();
        if (dat.contains("lab")) {
            if (!Main.primaryFXML.equals("lab")) {
                Main.primaryFXML = "lab";
                Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Lab.fxml"));
                Main.primaryStage.setScene(new Scene(Main.pRoot));
                if (Main.isExtended) {
                    Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                    tweetView = new WebView();
                    tweetEngine = tweetView.getEngine();
                    tweetEngine.load("https://twitter.com/lsu");
                    Main.infoStage.setScene(new Scene(tweetView));
                }
                Main.calendarStage.toFront();

            }
        } else if (dat.contains("4243")) {
            if (!Main.primaryFXML.equals("4243")) {
                Main.primaryFXML = "4243";
                Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Lecture.fxml"));
                Main.primaryStage.setScene(new Scene(Main.pRoot));
                if (Main.isExtended) {
                    Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                    tweetView = new WebView();
                    tweetEngine = tweetView.getEngine();
                    tweetEngine.load("https://twitter.com/lsu");
                    Main.infoStage.setScene(new Scene(tweetView));
                }
                Main.calendarStage.toFront();
            }
        } else if (dat.contains("459")) {
            if (!Main.primaryFXML.equals("459")) {
                Main.primaryFXML = "459";
                Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Meal.fxml"));
                Main.primaryStage.setScene(new Scene(Main.pRoot));
                if (Main.isExtended) {
                    Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                    tweetView = new WebView();
                    tweetEngine = tweetView.getEngine();
                    tweetEngine.load("https://twitter.com/lsudining");
                    Main.infoStage.setScene(new Scene(tweetView));
                }
                Main.calendarStage.toFront();
            }
        } else {
            if (!Main.primaryFXML.equals("home")) {
                Main.primaryFXML = "home";
                Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
                Main.primaryStage.setScene(new Scene(Main.pRoot));
                if (Main.isExtended) {
                    Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                    tweetView = new WebView();
                    tweetEngine = tweetView.getEngine();
                    tweetEngine.load("https://twitter.com/lsu");
                    Main.infoStage.setScene(new Scene(tweetView));
                }
                Main.calendarStage.toFront();
            }
        }
    }
}