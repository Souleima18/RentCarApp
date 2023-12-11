package com.example.rentcarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CarAdapter extends ArrayAdapter<CarModel> {

    private Context context;
    private List<CarModel> carList;

    public CarAdapter(Context context, List<CarModel> carList) {
        super(context, R.layout.car_list_item, carList);
        this.context = context;
        this.carList = carList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.car_list_item, null);
        }

        // Récupère la voiture à la position donnée
        CarModel car = carList.get(position);

        if (car != null) {
            // Remplit les éléments de la vue avec les données de la voiture
            TextView modelNameTextView = view.findViewById(R.id.modelNameTextView);
            TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
            TextView priceTextView = view.findViewById(R.id.priceTextView);

            modelNameTextView.setText(car.getModelName());
            descriptionTextView.setText(car.getDescription());
            priceTextView.setText(car.getPrice());
        }

        return view;
    }
}
