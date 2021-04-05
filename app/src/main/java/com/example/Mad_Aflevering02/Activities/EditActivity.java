package com.example.Mad_Aflevering02.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Mad_Aflevering02.R;
import com.example.Mad_Aflevering02.Persistance.WeatherModel;

import com.example.Mad_Aflevering02.ViewModels.EditViewModel;

import static java.lang.Integer.valueOf;

public class EditActivity extends AppCompatActivity {


    ImageView iV;
    SeekBar rating;
    TextView city, ratingProgress;
    String notesInput;
    int id;
    EditText note;
    Button Ok, Cancel;
    EditViewModel editViewModel;
    WeatherModel weatherModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ratingProgress = findViewById(R.id.RatingEdit);
        iV = findViewById(R.id.imageView_edit);
        city = findViewById(R.id.CityEDIT);
        rating = findViewById(R.id.seekBar2);
        note = findViewById(R.id.Notes_edit);
        Ok = findViewById(R.id.Ok);
        Cancel = findViewById(R.id.cancel);

        getData();

        editViewModel = new ViewModelProvider(this).get(EditViewModel.class);
        editViewModel.getWeatherData(id).observe(this, weatherModelFromDb -> {
            weatherModel = weatherModelFromDb;
            if (weatherModel != null)
            {
                setData();
            }
        });

        Ok.setOnClickListener(v -> {
            Intent resultIntent = getIntent();
            notesInput = note.getText().toString();
            weatherModel.setNote(note.getText().toString());
            weatherModel.setRating(rating.getProgress());
            editViewModel.update(weatherModel);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        Cancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ratingProgress.setText(String.format("%s     %s", getString(R.string.Rating), Double.parseDouble(String.valueOf(progress))));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

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

    private void setData() {
        city.setText(weatherModel.getCity());
        String url ="https://www.countryflags.io/" + weatherModel.getCountry() + "/flat/64.png";
        Glide.with(iV.getContext()).load(url).into(iV);
        ratingProgress.setText(String.format("%s     %s",getString(R.string.Rating), weatherModel.getRating()));
        note.setText(weatherModel.getNote());
        rating.setProgress(weatherModel.getRating());
    }
}