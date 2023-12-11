package com.example.rentcarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button addButton;
    private ListView carListView;

    private List<CarModel> carList;
    private CarAdapter carAdapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        searchEditText = findViewById(R.id.searchEditText);
        addButton = findViewById(R.id.addButton);
        carListView = findViewById(R.id.carListView);

        carList = new ArrayList<>();
        carAdapter = new CarAdapter(this, carList);
        carListView.setAdapter(carAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("cars");

        // Écoute les changements dans la base de données
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CarModel car = dataSnapshot.getValue(CarModel.class);
                    carList.add(car);
                }
                carAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Gérer les erreurs ici
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Rediriger vers l'activité d'ajout de voiture
                Intent intent = new Intent(SecondActivity.this, AddCarActivity.class);
                startActivity(intent);
            }
        });
    }
}
