package com.example.Mad_Aflevering02.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Mad_Aflevering02.R;
import com.example.Mad_Aflevering02.Persistance.WeatherModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.example.Mad_Aflevering02.Services.WeatherService;
import com.example.Mad_Aflevering02.ViewModels.ListViewModel;

public class ListActivity extends AppCompatActivity {

    MyRecyclerViewAdapter adapter;
    int[] images = {R.drawable.dk, R.drawable.fi, R.drawable.us, R.drawable.au, R.drawable.na, R.drawable.sg, R.drawable.ru, R.drawable.ae, R.drawable.fo, R.drawable.us, R.drawable.fj, R.drawable.jp};
    RecyclerView recyclerView;
    TextView searchBar;
    List<WeatherModel> weatherDataFromCsv;
    ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Find views for Buttons, Textviews, and Imageviews
        searchBar = findViewById(R.id.SeachList);
        Button addBtn = findViewById(R.id.Add_listActivity);
        Button exitBtn = findViewById(R.id.button);
        recyclerView = findViewById(R.id.view);

        // Initialize recycler view and adaptor for recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this ,images);
        recyclerView.setAdapter(adapter);


        // initialize view model, and observe model
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        listViewModel.getAllWeatherData().observe(this, weatherModels ->
                adapter.setWeathermodels(weatherModels)
        );

        // Get weather data via CSV from assignment 1, and use API to store in room db
        GetWeatherData();
        weatherDataFromCsv.forEach(model -> listViewModel.addCity(model.getCity()));

        // Exit button initialized
        exitBtn.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });
        startForegroundService();

        // Add button initialized
        addBtn.setOnClickListener(v -> {

            if(searchBar.getText() == null)
            {
                Toast.makeText(getApplicationContext(), "Please enter a city",  Toast.LENGTH_LONG).show();
            }

            else
            {
                listViewModel.addCity(searchBar.getText().toString());
            }
        });
    }

    // Start foreground service, service is inspired from Demo from the corresponding lecture about services in the MAD classes
    private void startForegroundService() {
        Intent WeatherServiceIntent = new Intent(this, WeatherService.class);
        startService(WeatherServiceIntent);
    }


    // Functionality for getting info from CSV file, inspiration from : https://www.youtube.com/watch?v=i-TqNzUryn8
    private void GetWeatherData() {
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        weatherDataFromCsv = new ArrayList<>();
        try {
            //line is read twice to remove header
            String line = reader.readLine();
            line = reader.readLine();

            while (line != null) {
                String[] tokens = line.split(",");
                WeatherModel weatherData = WeatherModelMapper(tokens);
                weatherDataFromCsv.add(weatherData);

                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Functionality for getting info from CSV file, inspiration from : https://www.youtube.com/watch?v=i-TqNzUryn8
    private WeatherModel WeatherModelMapper(String[] tokens) {
        WeatherModel data = new WeatherModel();

        data.setCity(tokens[0]);
        data.setCountry(tokens[1]);
        data.setTemp(Double.parseDouble(tokens[2]));
        data.setHumidity(Double.parseDouble(tokens[3]));
        data.setWeather(tokens[4]);

        return  data;
    }
}