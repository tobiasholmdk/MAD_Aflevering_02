package com.example.mad_aflevering01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {

    ImageView iV;
    TextView city, temp, weather, humidity;
    String s1, s2,s3,s4;
    int image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        iV = findViewById(R.id.imageView);
        city = findViewById(R.id.city_2);
        temp = findViewById(R.id.temp2);
        weather = findViewById(R.id.weather_2);
        humidity = findViewById(R.id.Humidity);

        getData();
        setData();
    }

    private void getData() {
        if(getIntent().hasExtra("sArr1") && getIntent().hasExtra("sArr2") && getIntent().hasExtra("sArr3") && getIntent().hasExtra("sArr4") && getIntent().hasExtra("imagesArr"))
        {
            s1 = getIntent().getStringExtra("sArr1");
            s2 = getIntent().getStringExtra("sArr2");
            s3 = getIntent().getStringExtra("sArr3");
            s4 = getIntent().getStringExtra("sArr4");
            image = getIntent().getIntExtra("imagesArr",1);
        }
        else
        {
            Toast.makeText(this,"Extra is empty, can't load in data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        city.setText(s1);
        temp.setText(String.format("Temp:  %sC", s2));
        weather.setText(String.format("Weather: %s", s3));
        humidity.setText(String.format("Humidity: %s%%", s4));
        iV.setImageResource(image);

    }
}
