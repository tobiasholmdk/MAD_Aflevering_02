package com.example.Mad_Aflevering02.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.Mad_Aflevering02.Persistance.WeatherModel;
import com.example.Mad_Aflevering02.Persistance.WeatherRepository;


public class DetailsViewModel extends AndroidViewModel {

    private WeatherRepository repository;
    public DetailsViewModel(@NonNull Application application) {
        super(application);
        repository = WeatherRepository.getInstance(application);

    }

    // Delete weather model from activity
    public void delete(int weatherModel)
    {
        repository.delete(weatherModel);
    }

    // Get weather data using an Id
    public LiveData<WeatherModel> getWeatherData(int Id)
    {
        return repository.getModelById(Id);
    }
}
