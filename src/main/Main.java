package main;

import controller.Calendar;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import osc.OSCListener;
import osc.OSCMessage;
import osc.OSCPortIn;
import osc.OSCPortOut;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    public static boolean isExtended = false;
    public static String primaryFXML;
    public static Parent pRoot;
    public static Stage primaryStage;
    public static Stage infoStage;
    public static Stage calendarStage;
    public static Stage mapStage;
    /*public static Stage promptStage;*/
    static OSCPortOut sender, sender2;
    static String sender1IP, sender2IP;
    static ArrayList<Object> oscArgs = new ArrayList<>();
    static InnerShadow borderGlow = new InnerShadow();
    static int depth;
    static int logCount = 0;
    static String eventDat = "";
    static String dayDat = "";
    static String timeDat = "";
    WebView mapView = new WebView();
    WebEngine mapEngine = mapView.getEngine();
    WebView tweetView = new WebView();
    WebEngine tweetEngine = tweetView.getEngine();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter the ip of the first sending device: ");
        sender1IP = in.next();
        System.out.print("Please enter the ip of the second sending device: ");
        sender2IP = in.next();
        in.close();
        launch(args);
    }

    public static void showOnScreen(int screen, Stage stage) {
        ObservableList<Screen> screens = Screen.getScreens();
        Screen display;

        if (screen > -1 && screen < screens.size())
            display = screens.get(screen);

        else if (screens.size() > 0)
            display = screens.get(0);

        else
            throw new RuntimeException("No Screens Found");

        stage.setX(display.getVisualBounds().getMinX());
        stage.setY(display.getVisualBounds().getMinY());
        stage.setWidth(display.getVisualBounds().getWidth());
        stage.setHeight(display.getVisualBounds().getHeight());
        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Robot bot = new Robot();
        OSCPortIn receiver = new OSCPortIn(8000);

        OSCListener normHandler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                Platform.runLater(() -> {
                    if (logCount == 1) {
                        depth = 100;
                        borderGlow.setWidth(depth);
                        borderGlow.setHeight(depth);
                        Main.primaryStage.getScene().getStylesheets().remove("/view/extStyle.css");
                        Main.calendarStage.getScene().getStylesheets().remove("/view/extStyle.css");
                        Main.mapStage.hide();
                        Main.infoStage.hide();
                        Main.isExtended = false;
                    } else {
                        depth = 100;
                        borderGlow.setWidth(depth);
                        borderGlow.setHeight(depth);
                        Main.primaryStage.getScene().getStylesheets().remove("/view/extStyle.css");
                        Main.isExtended = false;
                        try {
                            oscArgs.set(0, 1.0);
                            sender.send(new OSCMessage("/nav/normLED", oscArgs));
                            sender2.send(new OSCMessage("/nav/normLED", oscArgs));
                            oscArgs.set(0, 0.0);
                            sender.send(new OSCMessage("/nav/extLED", oscArgs));
                            sender2.send(new OSCMessage("/nav/extLED", oscArgs));
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }
                    }
                });
            }
        };

        OSCListener extHandler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                Platform.runLater(() -> {
                    if (logCount == 1) {
                        depth = 1000;
                        borderGlow.setWidth(depth);
                        borderGlow.setHeight(depth);
                        Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                        Main.calendarStage.getScene().getStylesheets().add("/view/extStyle.css");
                        showOnScreen(4, Main.mapStage);
                        showOnScreen(2, Main.infoStage);
                        Main.isExtended = true;
                    } else {
                        depth = 1000;
                        borderGlow.setWidth(depth);
                        borderGlow.setHeight(depth);
                        Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                        Main.isExtended = true;
                        try {
                            oscArgs.set(0, 1.0);
                            sender.send(new OSCMessage("/nav/extLED", oscArgs));
                            sender2.send(new OSCMessage("/nav/extLED", oscArgs));
                            oscArgs.set(0, 0.0);
                            sender.send(new OSCMessage("/nav/normLED", oscArgs));
                            sender2.send(new OSCMessage("/nav/normLED", oscArgs));

                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }
                    }
                });
            }
        };

        OSCListener m0Handler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                Platform.runLater(primaryStage::toFront);
            }
        };

        OSCListener m1Handler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                Platform.runLater(infoStage::toFront);
            }
        };

        OSCListener m2Handler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                Platform.runLater(calendarStage::toFront);
            }
        };

        OSCListener m3Handler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                Platform.runLater(mapStage::toFront);
            }
        };

        OSCListener upHandler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0"))
                bot.keyPress(KeyEvent.VK_UP);
        };

        OSCListener downHandler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0"))
                bot.keyPress(KeyEvent.VK_DOWN);
        };

        OSCListener leftHandler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0"))
                bot.keyPress(KeyEvent.VK_LEFT);
        };

        OSCListener rightHandler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0"))
                bot.keyPress(KeyEvent.VK_RIGHT);
        };

        OSCListener selectHandler = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                Platform.runLater(() -> {
                    if (logCount == 0) {
                        logCount++;
                        Main.primaryFXML = "home";
                        try {
                            Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Main.primaryStage.setScene(new Scene(Main.pRoot));

                        if (Main.isExtended) {
                            Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                            Main.calendarStage.getScene().getStylesheets().add("/view/extStyle.css");
                            showOnScreen(4, Main.mapStage);
                            showOnScreen(2, Main.infoStage);
                        }

                        Calendar.data.get(11).setTuesProp("CSC 4243");
                        Calendar.data.get(11).setThursProp("CSC 4243");
                        Calendar.data.get(16).setTuesProp("CSC 4243 (Lab)");
                        Calendar.data.get(16).setThursProp("CSC 4243 (Lab)");
                        showOnScreen(3, Main.calendarStage);
                    }
                });
            } else
                bot.keyPress(KeyEvent.VK_ENTER);
        };

        OSCListener normLEDHandler = (time, message) -> {
        };
        OSCListener extLEDHandler = (time, message) -> {
        };
        OSCListener m0LEDHandler = (time, message) -> {
        };
        OSCListener m1LEDHandler = (time, message) -> {
        };
        OSCListener m2LEDHandler = (time, message) -> {
        };
        OSCListener m3LEDHandler = (time, message) -> {
        };

        OSCListener addHandler = (time, message) -> {
            String dayData = dayDat.toLowerCase();
            String eventData = eventDat;
            String timeData = timeDat.toLowerCase();

            if (!dayData.equals("") && !timeData.equals("") && !eventData.equals("")) {
                int oscTime;
                if (timeData.contains("all day"))
                    oscTime = 0;
                else if (timeData.substring(1, 2).equals(":") || timeData.substring(1, 2).equals(" "))
                    oscTime = Integer.parseInt(timeData.substring(0, 1));
                else
                    oscTime = Integer.parseInt(timeData.substring(0, 2));

                int base;
                if (oscTime == 12) {
                    if (timeData.contains("pm"))
                        base = 1;
                    else
                        base = -11;
                } else if (timeData.contains("am"))
                    base = 1;
                else if (timeData.contains("pm"))
                    base = 13;
                else base = 0;

                int row = base + oscTime;
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
        };

        OSCListener searchHandler = (time, message) -> {
            Platform.runLater(() -> {
                try {
                    String eventData = eventDat.toLowerCase();
                    if (eventData.contains("lab")) {
                        for (int i = 0; i < Calendar.data.size(); i++) {
                            if (Calendar.data.get(i).toString().contains("Lab") && !Main.primaryFXML.equals("lab")) {
                                Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Lab.fxml"));
                                Main.primaryStage.setScene(new Scene(Main.pRoot));
                                if (Main.isExtended) {
                                    Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                                    tweetView = new WebView();
                                    tweetEngine = tweetView.getEngine();
                                    tweetEngine.load("https://twitter.com/lsu");
                                    Main.infoStage.setScene(new Scene(tweetView));
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
                                    tweetView = new WebView();
                                    tweetEngine = tweetView.getEngine();
                                    tweetEngine.load("https://twitter.com/lsu");
                                    Main.infoStage.setScene(new Scene(tweetView));
                                }
                                return;
                            }
                        }
                    } else if (eventData.contains("459")) {
                        for (int i = 0; i < Calendar.data.size(); i++) {
                            if (Calendar.data.get(i).toString().contains("4243") && !Main.primaryFXML.equals("459")) {
                                Main.pRoot = FXMLLoader.load(getClass().getResource("/view/Meal.fxml"));
                                Main.primaryStage.setScene(new Scene(Main.pRoot));
                                if (Main.isExtended) {
                                    Main.primaryStage.getScene().getStylesheets().add("/view/extStyle.css");
                                    tweetView = new WebView();
                                    tweetEngine = tweetView.getEngine();
                                    tweetEngine.load("https://twitter.com/lsudining");
                                    Main.infoStage.setScene(new Scene(tweetView));
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
                                tweetView = new WebView();
                                tweetEngine = tweetView.getEngine();
                                tweetEngine.load("https://twitter.com/lsu");
                                Main.infoStage.setScene(new Scene(tweetView));
                            }
                        }
                    }
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            });
        };

        OSCListener labHandle = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                oscArgs.set(0, 0.0);
                sender.send(new OSCMessage("/tools/lectureToggle", oscArgs));
                sender.send(new OSCMessage("/tools/mealToggle", oscArgs));
                sender.send(new OSCMessage("/tools/newToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/lectureToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/mealToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/newToggle", oscArgs));
                eventDat = "CSC 4243 (Lab)";
            }

        };

        OSCListener lectureHandle = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                oscArgs.set(0, 0.0);
                sender.send(new OSCMessage("/tools/labToggle", oscArgs));
                sender.send(new OSCMessage("/tools/mealToggle", oscArgs));
                sender.send(new OSCMessage("/tools/newToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/labToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/mealToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/newToggle", oscArgs));
                eventDat = "CSC 4243";
            }
        };

        OSCListener mealHandle = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                oscArgs.set(0, 0.0);
                sender.send(new OSCMessage("/tools/labToggle", oscArgs));
                sender.send(new OSCMessage("/tools/lectureToggle", oscArgs));
                sender.send(new OSCMessage("/tools/newToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/labToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/lectureToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/newToggle", oscArgs));
                eventDat = "459";
            }
        };

        OSCListener newHandle = (time, message) -> {
            if (message.getArguments().get(0).toString().equals("1.0")) {
                oscArgs.set(0, 0.0);
                sender.send(new OSCMessage("/tools/labToggle", oscArgs));
                sender.send(new OSCMessage("/tools/lectureToggle", oscArgs));
                sender.send(new OSCMessage("/tools/mealToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/labToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/lectureToggle", oscArgs));
                sender2.send(new OSCMessage("/tools/mealToggle", oscArgs));
                eventDat = "";
            }
        };

        OSCListener dayHandler = (time, message) -> {
            int dayValue = (int) Double.parseDouble(message.getArguments().get(0).toString());

            switch (dayValue) {
                case 1:
                    dayDat = "Sunday";
                    break;
                case 2:
                    dayDat = "Monday";
                    break;
                case 3:
                    dayDat = "Tuesday";
                    break;
                case 4:
                    dayDat = "Wednesday";
                    break;
                case 5:
                    dayDat = "Thursday";
                    break;
                case 6:
                    dayDat = "Friday";
                    break;
                case 7:
                    dayDat = "Saturday";
                    break;
            }

            oscArgs.set(0, dayDat);
            sender.send(new OSCMessage("/tools/dayLabel", oscArgs));
            sender2.send(new OSCMessage("/tools/dayLabel", oscArgs));
        };

        OSCListener timeHandler = (time, message) -> {
            int timeValue = (int) Double.parseDouble(message.getArguments().get(0).toString());

            if (timeValue == 0)
                timeDat = "All Day";
            else if (timeValue == 1)
                timeDat = "12:00 am";
            else if (timeValue < 13)
                timeDat = timeValue - 1 + ":00 am";
            else if (timeValue == 13)
                timeDat = "12:00 pm";
            else
                timeDat = timeValue - 13 + ":00 pm";

            oscArgs.set(0, timeDat);
            sender.send(new OSCMessage("/tools/timeLabel", oscArgs));
            sender2.send(new OSCMessage("/tools/timeLabel", oscArgs));
        };

        receiver.addListener("/nav/normPush", normHandler);
        receiver.addListener("/nav/normLED", normLEDHandler);

        receiver.addListener("/nav/extPush", extHandler);
        receiver.addListener("/nav/extLED", extLEDHandler);

        receiver.addListener("/nav/m0Push", m0Handler);
        receiver.addListener("/nav/m0LED", m0LEDHandler);

        receiver.addListener("/nav/m1Push", m1Handler);
        receiver.addListener("/nav/m1LED", m1LEDHandler);

        receiver.addListener("/nav/m2Push", m2Handler);
        receiver.addListener("/nav/m2LED", m2LEDHandler);

        receiver.addListener("/nav/m3Push", m3Handler);
        receiver.addListener("/nav/m3LED", m3LEDHandler);

        receiver.addListener("/nav/upPush", upHandler);
        receiver.addListener("/nav/downPush", downHandler);
        receiver.addListener("/nav/leftPush", leftHandler);
        receiver.addListener("/nav/rightPush", rightHandler);
        receiver.addListener("/nav/selectPush", selectHandler);

        receiver.addListener("/tools/addPush", addHandler);
        receiver.addListener("/tools/searchPush", searchHandler);
        receiver.addListener("/tools/labToggle", labHandle);
        receiver.addListener("/tools/lectureToggle", lectureHandle);
        receiver.addListener("/tools/mealToggle", mealHandle);
        receiver.addListener("/tools/newToggle", newHandle);
        receiver.addListener("/tools/dayRotary", dayHandler);
        receiver.addListener("/tools/timeRotary", timeHandler);

        receiver.startListening();

        sender = new OSCPortOut(InetAddress.getByName(sender1IP), 9000);
        sender2 = new OSCPortOut(InetAddress.getByName(sender2IP), 9000);
        oscArgs.add(1.0);
        sender.send(new OSCMessage("/nav/m0LED", oscArgs));
        sender.send(new OSCMessage("/nav/normLED", oscArgs));
        sender2.send(new OSCMessage("/nav/m0LED", oscArgs));
        sender2.send(new OSCMessage("/nav/normLED", oscArgs));
        oscArgs.set(0, 0.0);
        sender.send(new OSCMessage("/nav/m1LED", oscArgs));
        sender.send(new OSCMessage("/nav/m2LED", oscArgs));
        sender.send(new OSCMessage("/nav/m3LED", oscArgs));
        sender.send(new OSCMessage("/nav/extLED", oscArgs));
        sender.send(new OSCMessage("/tools/dayRotary", oscArgs));
        sender.send(new OSCMessage("/tools/timeRotary", oscArgs));
        sender.send(new OSCMessage("/tools/labToggle", oscArgs));
        sender.send(new OSCMessage("/tools/lectureToggle", oscArgs));
        sender.send(new OSCMessage("/tools/mealToggle", oscArgs));
        sender.send(new OSCMessage("/tools/newToggle", oscArgs));
        sender2.send(new OSCMessage("/nav/m1LED", oscArgs));
        sender2.send(new OSCMessage("/nav/m2LED", oscArgs));
        sender2.send(new OSCMessage("/nav/m3LED", oscArgs));
        sender2.send(new OSCMessage("/nav/extLED", oscArgs));
        sender2.send(new OSCMessage("/tools/dayRotary", oscArgs));
        sender2.send(new OSCMessage("/tools/timeRotary", oscArgs));
        sender2.send(new OSCMessage("/tools/labToggle", oscArgs));
        sender2.send(new OSCMessage("/tools/lectureToggle", oscArgs));
        sender2.send(new OSCMessage("/tools/mealToggle", oscArgs));
        sender2.send(new OSCMessage("/tools/newToggle", oscArgs));
        oscArgs.set(0, "Day");
        sender.send(new OSCMessage("/tools/dayLabel", oscArgs));
        sender2.send(new OSCMessage("/tools/dayLabel", oscArgs));
        oscArgs.set(0, "Time");
        sender.send(new OSCMessage("/tools/timeLabel", oscArgs));
        sender2.send(new OSCMessage("/tools/timeLabel", oscArgs));


        borderGlow.setColor(Color.rgb(248, 203, 0));

        Main.primaryStage = primaryStage;
        primaryFXML = "login";
        pRoot = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setScene(new Scene(pRoot));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue) {
                if (isExtended) {
                    depth = 1000;
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);
                } else {
                    depth = 100;
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);
                }
                pRoot.setEffect(borderGlow);
                oscArgs.set(0, 1.0);
            } else {
                pRoot.setEffect(null);
                oscArgs.set(0, 0.0);
            }
            try {
                sender.send(new OSCMessage("/nav/m0LED", oscArgs));
                sender2.send(new OSCMessage("/nav/m0LED", oscArgs));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        calendarStage = new Stage();
        Parent cRoot = FXMLLoader.load(getClass().getResource("/view/Calendar.fxml"));
        calendarStage.setScene(new Scene(cRoot));
        calendarStage.initStyle(StageStyle.UNDECORATED);

        calendarStage.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue) {
                if (isExtended) {
                    depth = 1000;
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);
                } else {
                    depth = 100;
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);
                }
                cRoot.setEffect(borderGlow);
                oscArgs.set(0, 1.0);
            } else {
                cRoot.setEffect(null);
                oscArgs.set(0, 0.0);
            }
            try {
                sender.send(new OSCMessage("/nav/m2LED", oscArgs));
                sender2.send(new OSCMessage("/nav/m2LED", oscArgs));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        infoStage = new Stage();
        tweetEngine.load("https://twitter.com/lsu");
        infoStage.setScene(new Scene(tweetView));
        infoStage.initStyle(StageStyle.UNDECORATED);

        infoStage.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue) {
                if (isExtended) {
                    depth = 1000;
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);
                } else {
                    depth = 100;
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);
                }
                tweetView.setEffect(borderGlow);
                oscArgs.set(0, 1.0);
            } else {
                tweetView.setEffect(null);
                oscArgs.set(0, 0.0);
            }
            try {
                sender.send(new OSCMessage("/nav/m1LED", oscArgs));
                sender2.send(new OSCMessage("/nav/m1LED", oscArgs));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mapStage = new Stage();
        mapEngine.load("https://www.google.com/maps/@30.4125444,-91.1785831,17z");
        mapStage.setScene(new Scene(mapView));
        mapStage.initStyle(StageStyle.UNDECORATED);

        mapStage.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue) {
                if (isExtended) {
                    depth = 1000;
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);
                } else {
                    depth = 100;
                    borderGlow.setWidth(depth);
                    borderGlow.setHeight(depth);
                }
                mapView.setEffect(borderGlow);
                oscArgs.set(0, 1.0);
            } else {
                mapView.setEffect(null);
                oscArgs.set(0, 0.0);
            }
            try {
                sender.send(new OSCMessage("/nav/m3LED", oscArgs));
                sender2.send(new OSCMessage("/nav/m3LED", oscArgs));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mapStage.showingProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            try {
                if (newPropertyValue) {
                    oscArgs.set(0, 1.0);
                    sender.send(new OSCMessage("/nav/extLED", oscArgs));
                    sender2.send(new OSCMessage("/nav/extLED", oscArgs));
                    oscArgs.set(0, 0.0);
                    sender.send(new OSCMessage("/nav/normLED", oscArgs));
                    sender2.send(new OSCMessage("/nav/normLED", oscArgs));
                } else {
                    oscArgs.set(0, 1.0);
                    sender.send(new OSCMessage("/nav/normLED", oscArgs));
                    sender2.send(new OSCMessage("/nav/normLED", oscArgs));
                    oscArgs.set(0, 0.0);
                    sender.send(new OSCMessage("/nav/extLED", oscArgs));
                    sender2.send(new OSCMessage("/nav/extLED", oscArgs));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        /*promptStage = new Stage();
        Parent promptRoot = FXMLLoader.load(getClass().getResource("/view/OSCPrompt.fxml"));
        promptStage.setScene(new Scene(cRoot));
        promptStage.initStyle(StageStyle.UNDECORATED);*/

        showOnScreen(1, primaryStage);
    }
}