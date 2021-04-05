package com.example.Mad_Aflevering02.Activities;

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

import com.bumptech.glide.Glide;
import com.example.Mad_Aflevering02.R;
import com.example.Mad_Aflevering02.Persistance.WeatherModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {


    Context context;
    List<WeatherModel> weatherDataList = new ArrayList<>();
    int[] imagesArr;


    public MyRecyclerViewAdapter(Context con, int[] images)
    {
        context = con;
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

        // Format temperature to have 2 decimals
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String tempFormat = (numberFormat.format(weatherDataList.get(position).getTemp()));

        // Set all data using model
        holder.city.setText(weatherDataList.get(position).getCity());
        holder.temp.setText(String.format("%s C", tempFormat));
        holder.weather.setText(weatherDataList.get(position).getWeather());
        holder.rating.setText(String.format("%s", weatherDataList.get(position).getRating()));

        // Initialize URL, and get flag using URL
        String url ="https://www.countryflags.io/" + weatherDataList.get(position).getCountry() + "/flat/64.png";
        Glide.with(holder.flag.getContext()).load(url).into(holder.flag);

        // Initialize click on a row
        holder.mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("Id", weatherDataList.get(position).getId());
            ((ListActivity) context).startActivityForResult(intent,420);
        });

    }


    // Set weather model when data changes, is called from observer in activity
    public void setWeathermodels(List<WeatherModel> weathermodels){
        this.weatherDataList = weathermodels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // Initialize all Textviews and Imageviews
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