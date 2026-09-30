package com.example.weatherappam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappam.api.RetrofitClient;
import com.example.weatherappam.model.City;
import com.example.weatherappam.model.DayEntry;
import com.example.weatherappam.model.WeatherResponse;
import com.example.weatherappam.util.WeatherUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // Η hardcoded λίστα πόλεων (όπως ζητάει αυστηρά η εκφώνηση)
    public static final List<City> CITIES = new ArrayList<>();
    static {
        CITIES.add(new City("Ερμούπολη", 37.4412, 24.9419));
        CITIES.add(new City("Αθήνα", 37.9838, 23.7275));
        CITIES.add(new City("Θεσσαλονίκη", 40.6401, 22.9444));
        CITIES.add(new City("Πάτρα", 38.2462, 21.7351));
        CITIES.add(new City("Ιωάννινα", 39.6649, 20.8519));
        CITIES.add(new City("Βόλος", 39.3620, 22.9416));
        CITIES.add(new City("Ηράκλειο", 35.3256, 25.1326));
        CITIES.add(new City("Καβάλα", 40.9396, 24.4069));
    }

    private TextView tvCityName, tvCurrentTemp, tvCurrentDesc;
    private ImageView ivCurrentIcon;
    private ListView listViewDays;
    private ProgressBar progressBar;
    private Button btnSettings;
    private DayAdapter adapter;

    // Εδώ κρατάμε όλη την απάντηση του ίντερνετ για να την περάσουμε στην επόμενη οθόνη
    private WeatherResponse currentWeatherResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Σύνδεση των μεταβλητών με τα γραφικά του XML
        tvCityName = findViewById(R.id.tvCityName);
        tvCurrentTemp = findViewById(R.id.tvCurrentTemp);
        tvCurrentDesc = findViewById(R.id.tvCurrentDesc);
        ivCurrentIcon = findViewById(R.id.ivCurrentIcon);
        listViewDays = findViewById(R.id.listViewDays);
        progressBar = findViewById(R.id.progressBar);
        btnSettings = findViewById(R.id.btnSettings);

        // 2. Το κουμπί που μας πάει στις Ρυθμίσεις (πολύ πιο απλό από το Toolbar!)
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // 3. Τι γίνεται όταν πατάμε πάνω σε μια μέρα στη λίστα
        listViewDays.setOnItemClickListener((parent, view, position, id) -> {
            if (currentWeatherResponse != null && currentWeatherResponse.hourly != null) {
                Intent intent = new Intent(MainActivity.this, DayDetailActivity.class);

                // Περνάμε ποια μέρα πατήθηκε (το index)
                intent.putExtra("dayIndex", position);

                // Περνάμε τα ωριαία δεδομένα με GSON (όπως προτείνει η εκφώνηση!)
                Gson gson = new Gson();
                String hourlyJson = gson.toJson(currentWeatherResponse.hourly);
                intent.putExtra("hourlyData", hourlyJson);

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 4. Κάθε φορά που η οθόνη "ξυπνάει" (π.χ. γυρνώντας από ρυθμίσεις), φορτώνει τον καιρό
        loadWeatherForSelectedCity();
    }

    private void loadWeatherForSelectedCity() {
        // Διαβάζουμε την επιλεγμένη πόλη από το "τετράδιο" (SharedPreferences)
        SharedPreferences prefs = getSharedPreferences("WeatherPrefs", MODE_PRIVATE);
        int cityIndex = prefs.getInt("cityIndex", 0); // Το 0 είναι η Ερμούπολη
        City selectedCity = CITIES.get(cityIndex);

        tvCityName.setText(selectedCity.getName());
        progressBar.setVisibility(View.VISIBLE); // Εμφανίζουμε το κυκλάκι φόρτωσης

        // 5. Η κλήση στο API
        RetrofitClient.getInstance().getApiService()
                .getWeather(selectedCity.getLatitude(), selectedCity.getLongitude())
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        progressBar.setVisibility(View.GONE); // Κρύβουμε το κυκλάκι
                        if (response.isSuccessful() && response.body() != null) {
                            currentWeatherResponse = response.body();
                            updateUI(currentWeatherResponse); // Στέλνουμε τα δεδομένα στην οθόνη
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Σφάλμα δικτύου", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI(WeatherResponse response) {
        // Γεμίζουμε το πάνω μπλοκ με τον σημερινό καιρό
        if (response.current != null) {
            tvCurrentTemp.setText(Math.round(response.current.temperature_2m) + "°C");
            tvCurrentDesc.setText(WeatherUtils.descriptionForCode(response.current.weather_code));
            ivCurrentIcon.setImageResource(WeatherUtils.iconForCode(response.current.weather_code));
        }

        // Γεμίζουμε τη λίστα των 5 ημερών
        if (response.daily != null) {
            List<DayEntry> daysList = new ArrayList<>();
            // "Ζιπάρουμε" τους parallel arrays του Open-Meteo, ακριβώς όπως το ζήτησε ο Γαβαλάς!
            for (int i = 0; i < response.daily.time.size(); i++) {
                daysList.add(new DayEntry(
                        response.daily.time.get(i),
                        response.daily.weather_code.get(i),
                        response.daily.temperature_2m_max.get(i),
                        response.daily.temperature_2m_min.get(i)
                ));
            }

            adapter = new DayAdapter(this, daysList);
            listViewDays.setAdapter(adapter);
        }
    }
}