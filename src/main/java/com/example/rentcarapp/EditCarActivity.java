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

public class EditCarActivity extends AppCompatActivity {

    private EditText modelNameEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private Button saveButton;

    private DatabaseReference databaseReference;

    private String carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        modelNameEditText = findViewById(R.id.editModelNameEditText);
        descriptionEditText = findViewById(R.id.editDescriptionEditText);
        priceEditText = findViewById(R.id.editPriceEditText);
        saveButton = findViewById(R.id.saveButton);

        databaseReference = FirebaseDatabase.getInstance().getReference("cars");

        // Récupère l'ID de la voiture depuis l'intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            carId = extras.getString("carId");
            if (carId != null) {
                loadCarDetails();
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCarDetails();
            }
        });
    }

    private void loadCarDetails() {
        databaseReference.child(carId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult() != null && task.getResult().exists()) {
                    CarModel car = task.getResult().getValue(CarModel.class);
                    if (car != null) {
                        modelNameEditText.setText(car.getModelName());
                        descriptionEditText.setText(car.getDescription());
                        priceEditText.setText(car.getPrice());
                    }
                }
            } else {
                // Gérer les erreurs ici
            }
        });
    }

    private void updateCarDetails() {
        String modelName = modelNameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();

        if (!modelName.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
            databaseReference.child(carId).child("modelName").setValue(modelName);
            databaseReference.child(carId).child("description").setValue(description);
            databaseReference.child(carId).child("price").setValue(price);

            Toast.makeText(this, "Informations de la voiture mises à jour avec succès", Toast.LENGTH_SHORT).show();
            finish(); // Retour à l'activité précédente après la mise à jour
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }
}
