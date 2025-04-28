package com.example.rarecartracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;

    public CarAdapter(List<Car> carList) {
        this.carList = carList;
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView brandText, modelText, locationText;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            brandText = itemView.findViewById(R.id.carBrandText);
            modelText = itemView.findViewById(R.id.carModelText);
            locationText = itemView.findViewById(R.id.carLocationText);
        }
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.brandText.setText(car.getBrand());
        holder.modelText.setText(car.getModel());
        holder.locationText.setText(car.getLatitude() + ", " + car.getLongitude());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }
}
