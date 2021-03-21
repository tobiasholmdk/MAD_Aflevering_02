package com.example.mad_aflevering01;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WeatherRepository {
    private WeathenmodelDao weathenmodelDao;
    private LiveData<List<WeatherModel>> WeatherData;
    private WeatherModelDatabase weatherModelDatabase;

    public WeatherRepository(Application application)
    {
        weatherModelDatabase = WeatherModelDatabase.getInstance(application);
        weathenmodelDao = weatherModelDatabase.weathenmodelDao();
        WeatherData = weathenmodelDao.getAllModels();
    }

    public void insert(WeatherModel weatherModel)
    {
        new InsertWeatherModelAsyncTask(weathenmodelDao).execute(weatherModel);
    }

    public void update(WeatherModel weatherModel)
    {
        new UpdateWeatherModelAsyncTask(weathenmodelDao).execute(weatherModel);
    }

    public void delete(WeatherModel weatherModel)
    {
        new DeleteWeatherModelAsyncTask(weathenmodelDao).execute(weatherModel);
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
    private static class DeleteWeatherModelAsyncTask extends AsyncTask<WeatherModel,Void,Void>
    {
        private WeathenmodelDao weathenmodelDao;
        private DeleteWeatherModelAsyncTask(WeathenmodelDao weathenmodelDao)
        {
            this.weathenmodelDao = weathenmodelDao;
        }

        @Override
        protected Void doInBackground(WeatherModel... weatherModels) {
            weathenmodelDao.delete(weatherModels[0]);
            return null;
        }
    }

}
