package com.example.Mad_Aflevering02.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.Mad_Aflevering02.Persistance.WeatherModel;
import com.example.Mad_Aflevering02.Persistance.WeatherRepository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private WeatherRepository repository;
    private LiveData<List<WeatherModel>> weatherData;


    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = WeatherRepository.getInstance(application);
        weatherData = repository.getAllWeatherData();
    }


    // Get all weather models
    public LiveData<List<WeatherModel>> getAllWeatherData()
    {
        return weatherData;
    }

    // Add new city
    public void addCity(String city)
    {
        repository.addCity(city);
    }
}
