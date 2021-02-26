package com.example.mad_aflevering01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    ImageView iV;
    TextView city, temp, weather, humidity, ratings, notes;
    String cityInput, weatherInput, notesInput;
    double tempInput, humidityInput, ratingInput;
    int image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        iV = findViewById(R.id.imageView);
        city = findViewById(R.id.city_2);
        temp = findViewById(R.id.temp2);
        weather = findViewById(R.id.weather_2);
        humidity = findViewById(R.id.Humidity);
        ratings = findViewById(R.id.Userrating);
        notes = findViewById(R.id.notes);

        if (savedInstanceState != null) {
            cityInput = savedInstanceState.getString("sArr1");
            tempInput = savedInstanceState.getDouble("sArr2");
            weatherInput = savedInstanceState.getString("sArr3");
            humidityInput = savedInstanceState.getDouble("sArr4");
            image = savedInstanceState.getInt("imagesArr");
            ratingInput = savedInstanceState.getDouble("Rating",1);
            notesInput = savedInstanceState.getString("Note");
        } else {
            getData();
        }
        setData();


        Button EditBtn = (Button) findViewById(R.id.edit);

        EditBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, EditActivity.class);
            intent.putExtra("City", cityInput);
            intent.putExtra("Temp", tempInput);
            intent.putExtra("Weather", weatherInput);
            intent.putExtra("Humidity",humidityInput);
            intent.putExtra("imagesArr", image);
            intent.putExtra("Rating",ratingInput);
            intent.putExtra("Note",notesInput);
            DetailsActivity.this.startActivityForResult(intent,69);
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("City", cityInput);
        savedInstanceState.putString("Note", notesInput);
        savedInstanceState.putDouble("Temp", tempInput);
        savedInstanceState.putDouble("Rating", ratingInput);
        savedInstanceState.putString("Weather", weatherInput);
        savedInstanceState.putDouble("Humidity", humidityInput);
        savedInstanceState.putInt("imagesArr", image);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void getData() {
        if(getIntent().hasExtra("City") && getIntent().hasExtra("Temp") && getIntent().hasExtra("Weather") && getIntent().hasExtra("Humidity") && getIntent().hasExtra("imagesArr"))
        {
            cityInput = getIntent().getStringExtra("City");
            tempInput = getIntent().getDoubleExtra("Temp",1);
            weatherInput = getIntent().getStringExtra("Weather");
            humidityInput = getIntent().getDoubleExtra("Humidity", 1);
            image = getIntent().getIntExtra("imagesArr",1);
            ratingInput = getIntent().getDoubleExtra("Rating",1);
            notesInput = getIntent().getStringExtra("Note");
        }
        else
        {
            Toast.makeText(this,"Intent Extra is empty, can't load in data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 69) {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(this,"Inside OnActivityRes", Toast.LENGTH_SHORT).show();
                ratingInput = data.getDoubleExtra("Rating",1);
                notesInput = data.getStringExtra("Note");
                notes.setText(notesInput);
                ratings.setText(String.format("User Rating:  %s", ratingInput));
            }
        }
    }

    private void setData() {
        city.setText(cityInput);
        temp.setText(String.format("Temp:  %sC", tempInput));
        weather.setText(String.format("Weather: %s", weatherInput));
        humidity.setText(String.format("Humidity: %s%%", humidityInput));
        iV.setImageResource(image);
        notes.setText(notesInput);
        ratings.setText(String.format("User Rating:  %s", ratingInput));
    }
}
