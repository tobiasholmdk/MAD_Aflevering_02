package com.example.Mad_Aflevering02.Persistance;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.Mad_Aflevering02.API.WeatherApi;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.content.ContentValues.TAG;

public class WeatherRepository {
    private WeathenmodelDao weathenmodelDao;
    private WeatherApi weatherApi;
    private ExecutorService executor;
    private LiveData<List<WeatherModel>> WeatherData;
    private WeatherModelDatabase weatherModelDatabase;
    private Application app;
    private final String APIKEY = "7e6fe9a625bfecfc6824cc80140101fb";

    private WeatherRepository(Application application)
    {
        app = application;
        weatherModelDatabase = WeatherModelDatabase.getInstance(application);
        weathenmodelDao = weatherModelDatabase.weathenmodelDao();
        WeatherData = weathenmodelDao.getAllModels();
        weatherApi = new WeatherApi(application);
        executor = Executors.newSingleThreadExecutor();
    }


    private static WeatherRepository instance;

    public static synchronized WeatherRepository getInstance(Application app)
    {
        if (instance == null)
        {
            instance = new WeatherRepository(app);
        }
        return instance;
    }

    public void updateAllWeather()
    {
        getAllWeatherNonLive().forEach(model -> weatherApi.update(model, APIKEY));
        Toast.makeText(app.getApplicationContext(), "Updated cities",  Toast.LENGTH_SHORT).show();
    }

    public void addCity(String city)
    {
        if(containsCity(getAllWeatherNonLive(),city))
        {
            Toast.makeText(app.getBaseContext(), "City already Exists, please try another", Toast.LENGTH_SHORT).show();
        }
        else
        {
            weatherApi.getWeatherAPIDate(city, APIKEY);
        }
    }

    public boolean containsCity(final List<WeatherModel> list, final String City){
        return list.stream().anyMatch(o -> o.getCity().equals(City));
    }

    public List<WeatherModel> getAllWeatherNonLive(){
        Future<List<WeatherModel>> p = executor.submit(() -> weathenmodelDao.getAllWeatherNonLive());

        try {
            return p.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(WeatherModel weatherModel)
    {
        new InsertWeatherModelAsyncTask(weathenmodelDao).execute(weatherModel);
    }

    public void update(WeatherModel weatherModel)
    {
        new UpdateWeatherModelAsyncTask(weathenmodelDao).execute(weatherModel);
    }

    public void delete(int weatherModel)
    {
        new DeleteWeatherModelAsyncTask(weathenmodelDao).execute(weatherModel);
    }

    public LiveData<WeatherModel> getModelById(int Id)
    {
        return weathenmodelDao.getModelById(Id);
    }

    public LiveData<List<WeatherModel>> getAllWeatherData()
    {
        return WeatherData;
    }

    private static class InsertWeatherModelAsyncTask extends AsyncTask<WeatherModel,Void,Void>
    {
        private WeathenmodelDao weathenmodelDao;
        private InsertWeatherModelAsyncTask(WeathenmodelDao weathenmodelDao)
        {
            this.weathenmodelDao = weathenmodelDao;
        }

        @Override
        protected Void doInBackground(WeatherModel... weatherModels) {
            weathenmodelDao.insert(weatherModels[0]);
            return null;
        }
    }

    private static class UpdateWeatherModelAsyncTask extends AsyncTask<WeatherModel,Void,Void>
    {
        private WeathenmodelDao weathenmodelDao;
        private UpdateWeatherModelAsyncTask(WeathenmodelDao weathenmodelDao)
        {
            this.weathenmodelDao = weathenmodelDao;
        }

        @Override
        protected Void doInBackground(WeatherModel... weatherModels) {
            weathenmodelDao.update(weatherModels[0]);
            return null;
        }
    }
    private static class DeleteWeatherModelAsyncTask extends AsyncTask<Integer,Void,Void>
    {
        private WeathenmodelDao weathenmodelDao;
        private DeleteWeatherModelAsyncTask(WeathenmodelDao weathenmodelDao)
        {
            this.weathenmodelDao = weathenmodelDao;
        }

        @Override
        protected Void doInBackground(Integer... weatherModels) {
            weathenmodelDao.delete(weatherModels[0]);
            return null;
        }
    }

}
