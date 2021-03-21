package com.example.mad_aflevering01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyRecyclerViewAdapter adapter;
    int[] images = {R.drawable.dk, R.drawable.fi, R.drawable.us, R.drawable.au, R.drawable.na, R.drawable.sg, R.drawable.ru, R.drawable.ae, R.drawable.fo, R.drawable.us, R.drawable.fj, R.drawable.jp};
    RecyclerView recyclerView;
    List<WeatherModel> weatherDataList;
    private ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyRecyclerViewAdapter(this, weatherDataList, images);
        recyclerView.setAdapter(adapter);



        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        listViewModel.getAllWeatherData().observe(this, new Observer<List<WeatherModel>>() {
            @Override
            public void onChanged(List<WeatherModel> weatherModels) {

            }
        });


        Button exitBtn = (Button) findViewById(R.id.button);
        exitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 420) {
            if (resultCode == RESULT_OK)
            {

                int position = data.getIntExtra("Position",0);

                String newNote = data.getStringExtra("Note");

                weatherDataList.get(position).setRating(data.getDoubleExtra("Rating",1));
                weatherDataList.get(position).setNote(newNote);

                adapter = new MyRecyclerViewAdapter(this, weatherDataList, images);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        }
    }


    // Functionality for getting info from CSV file, inspiration from : https://www.youtube.com/watch?v=i-TqNzUryn8
    private void GetWeatherData() {
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        weatherDataList = new ArrayList<>();
        try {
            //line is read twice to remove header
            String line = reader.readLine();
            line = reader.readLine();

            while (line != null) {
                String[] tokens = line.split(",");
                WeatherModel weatherData = WeatherModelMapper(tokens);
                weatherDataList.add(weatherData);

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