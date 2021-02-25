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

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    Context context;
    String sArr1[], sArr2[],sArr3[], sArr4[];
    int imagesArr[];


    public MyRecyclerViewAdapter(Context con, String s1[],String s2[],String s3[],String s4[],int images [] )
    {
        context = con;
        sArr1 = s1;
        sArr2 = s2;
        sArr3 = s3;
        sArr4 = s4;
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
        holder.city.setText(sArr1[position]);
        holder.temp.setText(String.format("%s C", sArr2[position]));
        holder.weather.setText(sArr3[position]);
        holder.flag.setImageResource(imagesArr[position]);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity2.class);
                intent.putExtra("sArr1", sArr1[position]);
                intent.putExtra("sArr2", sArr2[position]);
                intent.putExtra("sArr3", sArr3[position]);
                intent.putExtra("sArr4", sArr4[position]);
                intent.putExtra("imagesArr", imagesArr[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sArr1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView city, temp, weather;
        ImageView flag;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.city);
            temp = itemView.findViewById(R.id.temp);
            weather = itemView.findViewById(R.id.weather);
            flag = itemView.findViewById(R.id.flag);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }}