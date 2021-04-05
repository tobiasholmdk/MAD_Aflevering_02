package com.example.Mad_Aflevering02.API;
import android.app.Application;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Mad_Aflevering02.Persistance.WeatherModel;
import com.example.Mad_Aflevering02.Persistance.WeatherRepository;
import com.google.gson.Gson;


public class WeatherApi {

    private Application app;
    private WeatherRepository repository;
    private WeatherModel weatherModel;

    public WeatherApi(Application application)
    {

        app = application;
    }

    public void getWeatherAPIDate(String city, String APIKey)
    {
        repository = WeatherRepository.getInstance(app);
        RequestQueue queue = Volley.newRequestQueue(app.getApplicationContext());

        // Make request URL using parameters
        String url ="https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + APIKey;

        // Making GET request for weather model request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Deserializing result
                    Gson gson = new Gson();
                    OpenWeatherResponse weather = gson.fromJson(response, OpenWeatherResponse.class);

                    // Initializing, and transfering to desired form of model, and storing in db using repository
                    weatherModel = new WeatherModel();
                    weatherModel.setWeather(weather.weather[0].description);
                    weatherModel.setTemp(weather.main.temp-272.15);
                    weatherModel.setHumidity(weather.main.humidity);
                    weatherModel.setCountry(weather.sys.country);
                    weatherModel.setCity(weather.name);
                    repository.insert(weatherModel);

                    Toast.makeText(app.getApplicationContext(), "City added",  Toast.LENGTH_SHORT).show();
                },
                error -> Toast.makeText(app.getApplicationContext(), "Error city could not be loaded, please try another" + error.getMessage(),  Toast.LENGTH_SHORT).show());
    queue.add(stringRequest);
    }

    public void update(WeatherModel model, String APIKey)
    {
        repository = WeatherRepository.getInstance(app);
        RequestQueue queue = Volley.newRequestQueue(app.getApplicationContext());

        // Make request URL using parameters
        String url ="https://api.openweathermap.org/data/2.5/weather?q=" + model.getCity() + "&appid=" + APIKey;

        // Making GET request for weather model request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Deserializing result
                    Gson gson = new Gson();
                    OpenWeatherResponse weather = gson.fromJson(response, OpenWeatherResponse.class);

                    // Initializing, and transfering to desired form of model, and updating db using repository
                    model.setWeather(weather.weather[0].description);
                    model.setTemp(weather.main.temp-272.15);
                    model.setHumidity(weather.main.humidity);
                    model.setCountry(weather.sys.country);
                    model.setCity(weather.name);
                    repository.update(model);
                },
                error -> Toast.makeText(app.getApplicationContext(), "Error city could not be loaded, please try another" + error.getMessage(),  Toast.LENGTH_LONG).show());
        queue.add(stringRequest);
    }

}