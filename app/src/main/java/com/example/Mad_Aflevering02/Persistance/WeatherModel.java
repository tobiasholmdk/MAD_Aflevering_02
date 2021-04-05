package com.example.Mad_Aflevering02.Persistance;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_table")
public class WeatherModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String City;
    private String Country;
    private double Temp;
    private double Humidity;
    private String Weather;
    private int Rating;
    private String Note;


    public WeatherModel() {

    }

    public void setCity(String city) {
        City = city;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setTemp(double temp) {
        Temp = temp;
    }

    public void setHumidity(double humidity) {
        Humidity = humidity;
    }

    public void setWeather(String weather) {
        Weather = weather;
    }

    public void setRating(int rating) {
        Rating = rating;
    }


    public void setNote(String note) {
        Note = note;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public double getTemp() {
        return Temp;
    }

    public double getHumidity() {
        return Humidity;
    }

    public String getWeather() {
        return Weather;
    }

    public int getRating() {
        return Rating;
    }

    public String getNote() {
        return Note;
    }
}