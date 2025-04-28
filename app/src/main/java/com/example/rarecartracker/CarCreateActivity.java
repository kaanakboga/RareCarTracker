package com.example.rarecartracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.FirebaseFirestore;

public class CarCreateActivity extends AppCompatActivity {

    private EditText brandEditText, modelEditText;
    private Button saveCarButton;
    private FirebaseFirestore db;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitude, longitude;

    private static final int LOCATION_PERMISSION_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_create);

        brandEditText = findViewById(R.id.brandEditText);
        modelEditText = findViewById(R.id.modelEditText);
        saveCarButton = findViewById(R.id.saveCarButton);

        db = FirebaseFirestore.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        saveCarButton.setOnClickListener(v -> {
            String brand = brandEditText.getText().toString().trim();
            String model = modelEditText.getText().toString().trim();

            if (brand.isEmpty() || model.isEmpty()) {
                Toast.makeText(this, "Marka ve model giriniz", Toast.LENGTH_SHORT).show();
                return;
            }

            getLocationAndSave(brand, model);
        });
    }

    private void getLocationAndSave(String brand, String model) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // İzin yoksa, izin iste
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        } else {
            // İzin varsa konumu al
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            saveCarToFirestore(brand, model, latitude, longitude);
                        } else {
                            Toast.makeText(this, "Konum alınamadı", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveCarToFirestore(String brand, String model, double latitude, double longitude) {
        Car car = new Car(brand, model, latitude, longitude);

        db.collection("cars")
                .add(car)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Araba başarıyla kaydedildi", Toast.LENGTH_SHORT).show();
                    finish(); // Kaydettikten sonra aktiviteyi kapat
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Kullanıcı izin verirse
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // İzin verildi, tekrar konumu al
                saveCarButton.performClick();
            } else {
                Toast.makeText(this, "Konum izni gerekli", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
