package com.example.weatherappam;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weatherappam.model.City;
import com.example.weatherappam.model.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "WeatherPrefs";
    private static final String KEY_CITY_INDEX = "cityIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Αφαιρέσαμε την Toolbar για απλοποίηση.
        // Αντικαταστήσαμε την επιστροφή στο μενού με ένα απλό κουμπί "Αποθήκευση".

        Spinner spinner = findViewById(R.id.spinnerCity);
        List<City> cities = MainActivity.CITIES;

        ArrayAdapter<City> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                cities
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Φόρτωση της αποθηκευμένης πόλης από τα SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedIndex = prefs.getInt(KEY_CITY_INDEX, 0);
        spinner.setSelection(savedIndex);

        // Αποθήκευση και επιστροφή
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            int selectedIndex = spinner.getSelectedItemPosition();
            // Αποθήκευση της νέας επιλογής
            prefs.edit().putInt(KEY_CITY_INDEX, selectedIndex).apply();

            Toast.makeText(this, "Η πόλη αποθηκεύτηκε!", Toast.LENGTH_SHORT).show();
            finish(); // Κλείνει αυτή την οθόνη και μας γυρνάει στην MainActivity
        });
    }

    public static interface WeatherApiService {
/*Αν σε ρωτήσουν "Τι είναι αυτό το for-loop μέσα στην updateUI()", θα τους απαντήσεις:

"Το Open-Meteo API δεν μας στέλνει τα δεδομένα σε έτοιμα αντικείμενα (όπως κάναμε με το REST Countries στο Lab 12), αλλά σε παράλληλους πίνακες (parallel arrays). Αυτό το for-loop παίρνει το στοιχείο μηδέν από τον πίνακα της ώρας, το στοιχείο μηδέν από τη θερμοκρασία και τα 'ζιπάρει' μέσα σε ένα δικό μου αντικείμενο DayEntry, ώστε να μπορέσω να το δώσω στον Adapter."*/
        // Βάζουμε όλες τις σταθερές παραμέτρους απευθείας στο URL
        @GET("v1/forecast?current=temperature_2m,weather_code,wind_speed_10m&daily=temperature_2m_max,temperature_2m_min,weather_code&hourly=temperature_2m,weather_code&timezone=auto&forecast_days=5")
        Call<WeatherResponse> getWeather(
                @Query("latitude")  double latitude,
                @Query("longitude") double longitude
        );
    }
}