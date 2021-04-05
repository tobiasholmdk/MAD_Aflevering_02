package com.example.Mad_Aflevering02.Persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = WeatherModel.class, version = 1)
public abstract class WeatherModelDatabase extends RoomDatabase {

    private static WeatherModelDatabase instance;

    public abstract WeathenmodelDao weathenmodelDao();

    public static synchronized WeatherModelDatabase getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), WeatherModelDatabase.class,
                    "weatherModel_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
