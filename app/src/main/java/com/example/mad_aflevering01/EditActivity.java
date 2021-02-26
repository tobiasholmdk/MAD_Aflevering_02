package com.example.mad_aflevering01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {


    ImageView iV;
    SeekBar rating;
    TextView city, ratingProgress;
    String cityInput, notesInput;
    double tempInput, ratingInput;
    int image;
    EditText note;
    Button Ok, Cancel;


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
        setData();

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = getIntent();
                notesInput = note.getText().toString();

                resultIntent.putExtra("Note",notesInput);
                resultIntent.putExtra("Rating",Double.valueOf(rating.getProgress()));

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
        {
            setResult(RESULT_CANCELED);
            finish();
        }});

        rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ratingProgress.setText(String.format("User Rating: %s", progress));
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
        if(getIntent().hasExtra("City") && getIntent().hasExtra("Temp") && getIntent().hasExtra("Weather") && getIntent().hasExtra("Humidity") && getIntent().hasExtra("imagesArr"))
        {
            cityInput = getIntent().getStringExtra("City");
            tempInput = getIntent().getDoubleExtra("Temp",1);
            image = getIntent().getIntExtra("imagesArr",1);
            ratingInput = getIntent().getDoubleExtra("Rating",1);
            notesInput = getIntent().getStringExtra("Note");
        }
        else
        {
            Toast.makeText(this,"Intent Extra is empty, can't load in data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        city.setText(cityInput);
        iV.setImageResource(image);
    }
}