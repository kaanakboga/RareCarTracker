package com.example.rarecartracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private TextView welcomeText;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeText = findViewById(R.id.welcomeText);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        String userEmail = getIntent().getStringExtra("userEmail");
        welcomeText.setText("Welcome, " + userEmail + "!");

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_list) {
                startActivity(new Intent(HomeActivity.this, ListActivity.class));
                return true;
            } else if (id == R.id.nav_add_car) {
                startActivity(new Intent(HomeActivity.this, CarCreateActivity.class));
                return true;
            } else if (id == R.id.nav_map) {
                startActivity(new Intent(HomeActivity.this, CarMapActivity.class));
                return true;
            }
            return false;
        });
    }
}
