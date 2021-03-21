package com.example.mad_aflevering01;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private WeatherRepository repository;
    private LiveData<List<WeatherModel>> weatherData;
    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository(application);
        weatherData = repository.getAllWeatherData();
    }

    public void insert(WeatherModel weatherModel)
    {
        repository.insert(weatherModel);
    }

    public void update(WeatherModel weatherModel)
    {
        repository.update(weatherModel);
    }

    public void delete(WeatherModel weatherModel)
    {
        repository.delete(weatherModel);
    }

    public LiveData<List<WeatherModel>> getAllWeatherData()
    {
        return weatherData;
    }
}
