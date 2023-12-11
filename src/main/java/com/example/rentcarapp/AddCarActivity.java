package com.example.rentcarapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCarActivity extends AppCompatActivity {

    private EditText modelNameEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private EditText imagesEditText;
    private Button addButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        modelNameEditText = findViewById(R.id.modelNameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        imagesEditText = findViewById(R.id.imagesEditText);
        addButton = findViewById(R.id.addButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("cars");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCarToDatabase();
            }
        });
    }

    private void addCarToDatabase() {
        String modelName = modelNameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        String images = imagesEditText.getText().toString().trim();

        if (!modelName.isEmpty() && !description.isEmpty() && !price.isEmpty() && !images.isEmpty()) {
            String carId = databaseReference.push().getKey();
            CarModel car = new CarModel(carId, modelName, description, price, images);

            if (carId != null) {
                databaseReference.child(carId).setValue(car);
                Toast.makeText(this, "Voiture ajoutée avec succès", Toast.LENGTH_SHORT).show();
                finish(); // Retour à l'activité précédente après l'ajout
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout de la voiture", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }
}
