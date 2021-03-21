package com.example.mad_aflevering01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {


    Context context;
    List<WeatherModel> weatherDataList = new ArrayList<>();
    int[] imagesArr;


    public MyRecyclerViewAdapter(Context con, List<WeatherModel> dataset, int[] images)
    {
        context = con;
        weatherDataList = dataset;
        imagesArr = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from((context));
        View view = inflater.inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.city.setText(weatherDataList.get(position).getCity());
        holder.temp.setText(String.format("%s C", weatherDataList.get(position).getTemp()));
        holder.weather.setText(weatherDataList.get(position).getWeather());
        holder.flag.setImageResource(imagesArr[position]);
        holder.rating.setText(String.format("%s", weatherDataList.get(position).getRating()));



        holder.mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("City", weatherDataList.get(position).getCity());
            intent.putExtra("Temp", weatherDataList.get(position).getTemp());
            intent.putExtra("Weather", weatherDataList.get(position).getWeather());
            intent.putExtra("Humidity", weatherDataList.get(position).getHumidity());
            intent.putExtra("Image", imagesArr[position]);
            intent.putExtra("Rating",weatherDataList.get(position).getRating());
            intent.putExtra("Note",weatherDataList.get(position).getNote());
            intent.putExtra("Position",position);

            ((MainActivity) context).startActivityForResult(intent,420);
        });

    }


    public void setWeathermodels(List<WeatherModel> weathermodels){
        this.weatherDataList = weathermodels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView city, temp, weather, rating;
        ImageView flag;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.city);
            temp = itemView.findViewById(R.id.temp);
            weather = itemView.findViewById(R.id.weather);
            flag = itemView.findViewById(R.id.flag);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            rating = itemView.findViewById(R.id.ratingMain);
        }
    }}