package com.example.Mad_Aflevering02.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.Mad_Aflevering02.R;
import com.example.Mad_Aflevering02.Persistance.WeatherModel;

import com.example.Mad_Aflevering02.ViewModels.DetailsViewModel;

import java.text.DecimalFormat;


public class DetailsActivity extends AppCompatActivity {

    ImageView iV;
    TextView city, temp, weather, humidity, ratings, notes;
    int id;
    Button EditBtn, BackBtn, DeleteBtn;
    DetailsViewModel detailsViewModel;
    WeatherModel weatherModel = new WeatherModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get id from Intent, and observe model
        getData();
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        detailsViewModel.getWeatherData(id).observe(this, weatherModelFromDb -> {
            weatherModel = weatherModelFromDb;
            if (weatherModel != null)
            {
                setData();
            }
        });

        // Find views for Buttons, Textviews, and Imageviews
        iV = findViewById(R.id.imageView);
        city = findViewById(R.id.city_2);
        temp = findViewById(R.id.temp2);
        weather = findViewById(R.id.weather_2);
        humidity = findViewById(R.id.Humidity);
        ratings = findViewById(R.id.Userrating);
        notes = findViewById(R.id.notes);
        EditBtn = findViewById(R.id.edit);
        BackBtn = findViewById(R.id.backbutton);
        DeleteBtn = findViewById(R.id.deleteButton);

        // Back button initialized
        BackBtn.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        // Delete button initialized
        DeleteBtn.setOnClickListener(v -> {
            detailsViewModel.delete(id);
            setResult(RESULT_CANCELED);
            Toast.makeText(this,"City Deleted", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Edit button initialized
        EditBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, EditActivity.class);
            intent.putExtra("Id", id);
            DetailsActivity.this.startActivityForResult(intent,69);
        });
    }

    // Get data from intent, in case of assigment2, only Id is sent as intent.
    private void getData() {
        if(getIntent().hasExtra("Id"))
        {
            id = getIntent().getIntExtra("Id",1);
        }
        else
        {
            Toast.makeText(this,"Intent Extra is empty, can't load in data", Toast.LENGTH_SHORT).show();
        }
    }


    // Set data for UI widgets using the weather model
    private void setData() {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String tempFormat = (numberFormat.format(weatherModel.getTemp()));
        String url ="https://www.countryflags.io/" + weatherModel.getCountry() + "/flat/64.png";
        city.setText(weatherModel.getCity());
        temp.setText(String.format("%s  %sC",getString(R.string.Temp), tempFormat));
        weather.setText(String.format("%s: %s", getString(R.string.Weather), weatherModel.getWeather()));
        humidity.setText(String.format("%s: %s%%", getString(R.string.hum),weatherModel.getHumidity()));
        Glide.with(iV.getContext()).load(url).into(iV);
        notes.setText(weatherModel.getNote());
        ratings.setText(String.format("%s  %s",getString(R.string.User), weatherModel.getRating()));
    }
}
