package com.example.weatherappam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weatherappam.model.HourlyEntry;
import com.example.weatherappam.util.WeatherUtils;
import java.util.List;

public class HourlyAdapter extends ArrayAdapter<HourlyEntry> {
    private Context context;
    private List<HourlyEntry> hours;

    public HourlyAdapter(Context context, List<HourlyEntry> hours) {
        super(context, 0, hours);
        this.context = context;
        this.hours = hours;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_hourly, parent, false);
        }
        HourlyEntry hour = hours.get(position);

        TextView tvHour = convertView.findViewById(R.id.tvHour);
        ImageView ivIcon = convertView.findViewById(R.id.ivHourlyIcon);
        TextView tvTemp = convertView.findViewById(R.id.tvHourlyTemp);

        // Το time έρχεται ως "2024-01-15T14:00" — κρατάμε μόνο την ώρα
        String timeOnly = hour.getTime().contains("T")
                ? hour.getTime().split("T")[1]
                : hour.getTime();

        tvHour.setText(timeOnly);
        ivIcon.setImageResource(WeatherUtils.iconForCode(hour.getWeatherCode()));
        tvTemp.setText(String.format("%.0f°C", hour.getTemperature()));

        return convertView;
    }
}