package com.example.myeventmate;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CarMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_map);

        db = FirebaseFirestore.getInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        db.collection("cars").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                Car car = doc.toObject(Car.class);
                if (car != null) {
                    LatLng location = new LatLng(car.getLatitude(), car.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(car.getBrand() + " " + car.getModel()));
                }
            }

            if (!queryDocumentSnapshots.isEmpty()) {
                Car firstCar = queryDocumentSnapshots.getDocuments().get(0).toObject(Car.class);
                if (firstCar != null) {
                    LatLng firstLoc = new LatLng(firstCar.getLatitude(), firstCar.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLoc, 12f));
                }
            }
        });
    }
}
