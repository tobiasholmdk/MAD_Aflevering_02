package com.example.mad_aflevering01;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mad_aflevering01.WeatherModel;

import java.util.List;

@Dao
public interface WeathenmodelDao {

    @Insert
    void insert(WeatherModel model);

    @Update
    void update(WeatherModel model);

    @Delete
    void delete(WeatherModel model);

    @Query("SELECT * FROM weather_table")
    LiveData<List<WeatherModel>> getAllModels();

}
