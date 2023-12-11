package com.example.rentcarapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseHelper {

    private DatabaseReference databaseReference;

    public FirebaseDatabaseHelper() {
        // Initialise la référence à la base de données Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("cars");
    }

    public void addCar(CarModel car) {
        // Ajoute une voiture à la base de données
        String carId = databaseReference.push().getKey();
        if (carId != null) {
            car.setCarId(carId);
            databaseReference.child(carId).setValue(car);
        }
    }

    public void updateCar(CarModel car) {
        // Met à jour les informations d'une voiture dans la base de données
        if (car.getCarId() != null) {
            databaseReference.child(car.getCarId()).setValue(car);
        }
    }

    public void deleteCar(String carId) {
        // Supprime une voiture de la base de données
        if (carId != null) {
            databaseReference.child(carId).removeValue();
        }
    }
}
