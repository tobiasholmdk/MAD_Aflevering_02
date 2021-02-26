package com.example.mad_aflevering01;

public class WeatherModel {
    private String City;
    private String Country;
    private double Temp;
    private double Humidity;
    private String Weather;
    private Double Rating;
    private String Note;

    public WeatherModel() {

        setRating(0.0);
        Note = "These are user notes";
    }

    public double getRating() {
        return Rating;
    }
    public void setRating(double rating) {
        this.Rating = rating;
    }

    public String getCity() {
        return City;
    }
    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }
    public void setCountry(String country) {
        Country = country;
    }

    public double getTemp() {
        return Temp;
    }
    public void setTemp(double temp) {
        Temp = temp;
    }

    public double getHumidity() {
        return Humidity;
    }
    public void setHumidity(double humidity) {
        Humidity = humidity;
    }

    public String getWeather() {
        return Weather;
    }
    public void setWeather(String weather) {
        Weather = weather;
    }

    public String getNote() { return Note; }
    public void setNote(String newNote) { this.Note = newNote; }

}