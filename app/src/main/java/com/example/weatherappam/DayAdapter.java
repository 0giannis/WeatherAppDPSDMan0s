package com.example.weatherappam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weatherappam.model.DayEntry;
import com.example.weatherappam.util.WeatherUtils;
import java.util.List;

public class DayAdapter extends ArrayAdapter<DayEntry> {
    private Context context;
    private List<DayEntry> days;

    public DayAdapter(Context context, List<DayEntry> days) {
        super(context, 0, days);
        this.context = context;
        this.days = days;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_day, parent, false);
        }

        DayEntry day = days.get(position);

        // 1. ΠΡΩΤΑ συνδέουμε τα Views από το XML
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        ImageView ivIcon = convertView.findViewById(R.id.ivDayIcon);
        TextView tvTempMax = convertView.findViewById(R.id.tvTempMax);
        TextView tvTempMin = convertView.findViewById(R.id.tvTempMin);

        // 2. ΜΕΤΑ κάνουμε τη μορφοποίηση της ημερομηνίας
        String rawDate = day.getDate(); // Παίρνει την ημερομηνία π.χ. "2026-09-23"
        if (rawDate != null && rawDate.contains("-")) {
            String[] parts = rawDate.split("-"); // Χωρίζει το κείμενο στις παύλες
            if (parts.length == 3) {
                String formattedDate = parts[2] + "/" + parts[1]; // Παίρνει μέρα/μήνα (π.χ. "23/09")
                tvDate.setText(formattedDate);
            } else {
                tvDate.setText(rawDate);
            }
        } else {
            tvDate.setText(rawDate);
        }

        // 3. ΤΕΛΟΣ περνάμε τα υπόλοιπα δεδομένα (εικονίδιο και θερμοκρασίες)
        ivIcon.setImageResource(WeatherUtils.iconForCode(day.getWeatherCode()));
        tvTempMax.setText(String.format("%.0f°C", day.getTempMax()));
        tvTempMin.setText(String.format("%.0f°C", day.getTempMin()));

        return convertView;
    }
}