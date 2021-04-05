package com.example.Mad_Aflevering02.Persistance;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.Mad_Aflevering02.API.WeatherApi;
import com.example.Mad_Aflevering02.R;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.content.ContentValues.TAG;

public class WeatherRepository {
    private final WeathenmodelDao weathenmodelDao;
    private final WeatherApi weatherApi;
    private final ExecutorService executor;
    private final LiveData<List<WeatherModel>> WeatherData;
    private final Application app;
    private static WeatherRepository instance;


    // Making sure Repository is a singleton using private constructor
    private WeatherRepository(Application application)
    {
        app = application;
        WeatherModelDatabase weatherModelDatabase = WeatherModelDatabase.getInstance(application);
        weathenmodelDao = weatherModelDatabase.weathenmodelDao();
        WeatherData = weathenmodelDao.getAllModels();
        weatherApi = new WeatherApi(application);
        executor = Executors.newSingleThreadExecutor();
    }

    // Making sure database is a singleton using getInstance
    public static synchronized WeatherRepository getInstance(Application app)
    {
        if (instance == null)
        {
            instance = new WeatherRepository(app);
        }
        return instance;
    }

    // Updates weather, called from serivce, uses externalized APIKey
    public void updateAllWeather()
    {
        getAllWeatherNonLive().forEach(model -> weatherApi.update(model, app.getResources().getString(R.string.APIKey)));
        Toast.makeText(app.getApplicationContext(), "Updated cities",  Toast.LENGTH_SHORT).show();
    }

    // Adds city weather, uses externalized APIKey
    public void addCity(String city)
    {
        // Only adds if city is unique
        if(containsCity(getAllWeatherNonLive(),city))
        {
            Toast.makeText(app.getBaseContext(), "City already Exists, please try another", Toast.LENGTH_SHORT).show();
        }
        else
        {
            weatherApi.getWeatherAPIDate(city, app.getResources().getString(R.string.APIKey));
        }
    }

    // Checks if a city already exists
    public boolean containsCity(final List<WeatherModel> list, final String City){
        return list.stream().anyMatch(o -> o.getCity().equals(City));
    }

    // Gets List of weather data when not deserired as LiveData
    public List<WeatherModel> getAllWeatherNonLive(){
        Future<List<WeatherModel>> p = executor.submit(() -> weathenmodelDao.getAllWeatherNonLive());

        try {
            return p.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<WeatherModel> getModelById(int Id)
    {
        return weathenmodelDao.getModelById(Id);
    }

    public LiveData<List<WeatherModel>> getAllWeatherData()
    {
        return WeatherData;
    }

    public void insert(WeatherModel weatherModel){
        executor.submit(() -> weathenmodelDao.insert(weatherModel));
    }

    public void update(WeatherModel weatherModel){
        executor.submit(() -> weathenmodelDao.update(weatherModel));
    }

    public void delete(int id){
        executor.submit(() -> weathenmodelDao.delete(id));
    }
}
