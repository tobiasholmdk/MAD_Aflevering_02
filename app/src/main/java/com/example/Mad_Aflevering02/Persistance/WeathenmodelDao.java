package com.example.Mad_Aflevering02.Persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeathenmodelDao {

    @Insert
    void insert(WeatherModel model);

    @Update
    void update(WeatherModel model);

    //@Delete
    //void delete(WeatherModel model);

    @Query("DELETE FROM weather_table WHERE id LIKE :Id")
    void delete(int Id);

    @Query("SELECT * FROM weather_table WHERE id LIKE :Id")
    LiveData<WeatherModel> getModelById(int Id);

    @Query("SELECT * FROM weather_table")
    List<WeatherModel> getAllWeatherNonLive();

    @Query("SELECT * FROM weather_table")
    LiveData<List<WeatherModel>> getAllModels();

}
