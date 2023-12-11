package com.example.rentcarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CarDetailsActivity extends AppCompatActivity {

    private TextView modelNameTextView;
    private TextView descriptionTextView;
    private TextView priceTextView;
    private Button editButton;
    private Button deleteButton;

    private DatabaseReference databaseReference;

    private String carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        modelNameTextView = findViewById(R.id.modelNameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        priceTextView = findViewById(R.id.priceTextView);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("cars");

        // Récupère l'ID de la voiture depuis l'intent
        Intent intent = getIntent();
        if (intent != null) {
            carId = intent.getStringExtra("carId");
            if (carId != null) {
                loadCarDetails();
            }
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Rediriger vers l'activité de modification de voiture
                Intent editIntent = new Intent(CarDetailsActivity.this, EditCarActivity.class);
                editIntent.putExtra("carId", carId);
                startActivity(editIntent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCar();
            }
        });
    }

    private void loadCarDetails() {
        databaseReference.child(carId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    CarModel car = snapshot.getValue(CarModel.class);
                    if (car != null) {
                        modelNameTextView.setText(car.getModelName());
                        descriptionTextView.setText(car.getDescription());
                        priceTextView.setText(car.getPrice());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gérer les erreurs ici
            }
        });
    }

    private void deleteCar() {
        databaseReference.child(carId).removeValue();
        Toast.makeText(this, "Voiture supprimée avec succès", Toast.LENGTH_SHORT).show();
        finish(); // Retour à l'activité précédente après la suppression
    }
}

