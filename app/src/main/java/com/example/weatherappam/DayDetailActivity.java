package com.example.weatherappam;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weatherappam.model.HourlyEntry;
import com.example.weatherappam.model.WeatherResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class DayDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);

        ListView listViewHourly = findViewById(R.id.listViewHourly);

        // Παίρνουμε τα ωριαία δεδομένα σε μορφή JSON που στείλαμε από τη MainActivity
        String hourlyJson = getIntent().getStringExtra("hourlyData");

        if (hourlyJson != null) {
            Gson gson = new Gson();
            // Μετατρέπουμε το JSON πίσω στο αντικείμενο Hourly της Java
            WeatherResponse.Hourly hourlyResponse = gson.fromJson(hourlyJson, WeatherResponse.Hourly.class);

            List<HourlyEntry> hourlyList = new ArrayList<>();
            if (hourlyResponse != null && hourlyResponse.time != null) {
                // Ζιπάρουμε τα δεδομένα της ώρας, θερμοκρασίας και κωδικού καιρού
                for (int i = 0; i < hourlyResponse.time.size(); i++) {
                    hourlyList.add(new HourlyEntry(
                            hourlyResponse.time.get(i),
                            hourlyResponse.temperature_2m.get(i),
                            hourlyResponse.weather_code.get(i)
                    ));
                }
            }

            // Συνδέουμε τον HourlyAdapter με τη λίστα
            HourlyAdapter adapter = new HourlyAdapter(this, hourlyList);
            listViewHourly.setAdapter(adapter);
        }
    }
}