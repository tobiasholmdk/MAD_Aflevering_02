package com.example.Mad_Aflevering02.Services;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.Mad_Aflevering02.Persistance.WeatherModel;
import com.example.Mad_Aflevering02.Persistance.WeatherRepository;
import com.example.Mad_Aflevering02.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// This Foreground service is built using the services demo from Lecture 6 in the MAD-course, with changes made to fit the assignment requirements

public class WeatherService extends Service {

    private static final String TAG = "ForegroundService";
    public static final String SERVICE_CHANNEL = "serviceChannel";
    public static final int NOTIFICATION_ID = 420;
    WeatherRepository weatherRepository;
    ExecutorService execService;
    boolean started = false;

    public WeatherService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        NotificationChannel channel = new NotificationChannel(SERVICE_CHANNEL, "Foreground Service", NotificationManager.IMPORTANCE_LOW);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

        doBackground();

        return START_STICKY;
    }

    private void doBackground() {
        if(!started) {
            started = true;

            UpdateWeather();
        }
    }

    private void UpdateWeather(){
        if(execService == null) {
            execService = Executors.newSingleThreadExecutor();
        }
        weatherRepository = WeatherRepository.getInstance(this.getApplication());
        weatherRepository.updateAllWeather();

        execService.submit(() -> {
            Log.d(TAG, "Updated Weather with new data from API: ");

            List<WeatherModel> weatherModels =  weatherRepository.getAllWeatherNonLive();
            Random rand = new Random();
            WeatherModel randomElement = weatherModels.get(rand.nextInt(weatherModels.size()));

            DecimalFormat numberFormat = new DecimalFormat("#.00");
            String tempFormat = (numberFormat.format(randomElement.getTemp()));

            Notification notification = new NotificationCompat.Builder(this, SERVICE_CHANNEL)
                    .setContentTitle("Weather update from " + randomElement.getCity() + ": ")
                    .setContentText("Current temp: " + tempFormat + "C" + ", and Current weather: " + randomElement.getWeather())
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .build();

            startForeground(NOTIFICATION_ID, notification);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error during weather update", e);
            }
            if(started) {
                UpdateWeather();
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        started = false;
        super.onDestroy();
    }
}
