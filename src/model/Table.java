package model;

import javafx.beans.property.SimpleStringProperty;

public class Table {

    private final SimpleStringProperty dtProp;
    private final SimpleStringProperty sunProp;
    private final SimpleStringProperty monProp;
    private final SimpleStringProperty tuesProp;
    private final SimpleStringProperty wedProp;
    private final SimpleStringProperty thursProp;
    private final SimpleStringProperty friProp;
    private final SimpleStringProperty satProp;

    public Table(String dtProp, String sunProp, String monProp, String tuesProp, String wedProp, String thursProp, String friProp, String satProp) {
        this.dtProp = new SimpleStringProperty(dtProp);
        this.sunProp = new SimpleStringProperty(sunProp);
        this.monProp = new SimpleStringProperty(monProp);
        this.tuesProp = new SimpleStringProperty(tuesProp);
        this.wedProp = new SimpleStringProperty(wedProp);
        this.thursProp = new SimpleStringProperty(thursProp);
        this.friProp = new SimpleStringProperty(friProp);
        this.satProp = new SimpleStringProperty(satProp);
    }

    public String getDtProp() {
        return dtProp.get();
    }

    public SimpleStringProperty dtPropProperty() {
        return dtProp;
    }

    public void setDtProp(String dtProp) {
        this.dtProp.set(dtProp);
    }

    public String getSunProp() {
        return sunProp.get();
    }

    public SimpleStringProperty sunPropProperty() {
        return sunProp;
    }

    public void setSunProp(String sunProp) {
        this.sunProp.set(sunProp);
    }

    public String getMonProp() {
        return monProp.get();
    }

    public SimpleStringProperty monPropProperty() {
        return monProp;
    }

    public void setMonProp(String monProp) {
        this.monProp.set(monProp);
    }

    public String getTuesProp() {
        return tuesProp.get();
    }

    public SimpleStringProperty tuesPropProperty() {
        return tuesProp;
    }

    public void setTuesProp(String tuesProp) {
        this.tuesProp.set(tuesProp);
    }

    public String getWedProp() {
        return wedProp.get();
    }

    public SimpleStringProperty wedPropProperty() {
        return wedProp;
    }

    public void setWedProp(String wedProp) {
        this.wedProp.set(wedProp);
    }

    public String getThursProp() {
        return thursProp.get();
    }

    public SimpleStringProperty thursPropProperty() {
        return thursProp;
    }

    public void setThursProp(String thursProp) {
        this.thursProp.set(thursProp);
    }

    public String getFriProp() {
        return friProp.get();
    }

    public SimpleStringProperty friPropProperty() {
        return friProp;
    }

    public void setFriProp(String friProp) {
        this.friProp.set(friProp);
    }

    public String getSatProp() {
        return satProp.get();
    }

    public SimpleStringProperty satPropProperty() {
        return satProp;
    }

    public void setSatProp(String satProp) {
        this.satProp.set(satProp);
    }

    @Override
    public String toString() {
        return "Table{" +
                "dtProp=" + dtProp +
                ", sunProp=" + sunProp +
                ", monProp=" + monProp +
                ", tuesProp=" + tuesProp +
                ", wedProp=" + wedProp +
                ", thursProp=" + thursProp +
                ", friProp=" + friProp +
                ", satProp=" + satProp +
                '}';
    }
}