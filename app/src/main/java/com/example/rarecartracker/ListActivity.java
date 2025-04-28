package com.example.rarecartracker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.carRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carAdapter = new CarAdapter(carList);
        recyclerView.setAdapter(carAdapter);

        db = FirebaseFirestore.getInstance();

        db.collection("cars")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        carList.clear();
                        for (DocumentSnapshot doc : task.getResult()) {
                            Car car = doc.toObject(Car.class);
                            carList.add(car);
                        }
                        carAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Arabalar yüklenemedi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
