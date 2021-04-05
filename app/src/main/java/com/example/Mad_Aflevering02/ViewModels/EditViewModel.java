package com.example.Mad_Aflevering02.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.Mad_Aflevering02.Persistance.WeatherModel;
import com.example.Mad_Aflevering02.Persistance.WeatherRepository;

import java.util.List;

public class EditViewModel extends AndroidViewModel {

    private WeatherRepository repository;
    private LiveData<List<WeatherModel>> weatherData;


    public EditViewModel(@NonNull Application application) {
        super(application);
        repository = WeatherRepository.getInstance(application);
        weatherData = repository.getAllWeatherData();
    }

    // Update weather model from activity
    public void update(WeatherModel weatherModel)
    {
        repository.update(weatherModel);
    }

    // get weather model from activity using id
    public LiveData<WeatherModel> getWeatherData(int Id)
    {
        return repository.getModelById(Id);
    }
}
