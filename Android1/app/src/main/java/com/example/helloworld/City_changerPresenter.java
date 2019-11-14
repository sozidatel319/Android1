package com.example.helloworld;

public final class City_changerPresenter {

    private String cityName = "";
    private boolean infoisChecked;
    private String wind;
    private String pressure;

    private static City_changerPresenter instance;

    public static City_changerPresenter getInstance() {
        instance = instance == null ? new City_changerPresenter() : instance;
        return instance;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public boolean getInfoisChecked() {
        return infoisChecked;
    }

    public void setInfoisChecked(boolean infoisChecked) {
        this.infoisChecked = infoisChecked;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
}
