package com.example.weatherappam.util;

import com.example.weatherappam.R;   // ✅ ΔΙΟΡΘΩΜΕΝΟ

public class WeatherUtils {

    public static int iconForCode(int code) {
        if (code == 0) return R.drawable.ic_clear;
        if (code <= 2) return R.drawable.ic_partly_cloudy;
        if (code == 3) return R.drawable.ic_overcast;
        if (code == 45 || code == 48) return R.drawable.ic_fog;
        if (code >= 51 && code <= 57) return R.drawable.ic_drizzle;
        if ((code >= 61 && code <= 67) || (code >= 80 && code <= 82)) return R.drawable.ic_rain;
        if ((code >= 71 && code <= 77) || code == 85 || code == 86) return R.drawable.ic_snow;
        if (code >= 95) return R.drawable.ic_thunderstorm;
        return R.drawable.ic_clear;
    }

    public static String descriptionForCode(int code) {
        if (code == 0) return "Καθαρός ουρανός";
        if (code <= 2) return "Λίγες νεφώσεις";
        if (code == 3) return "Συννεφιά";
        if (code == 45 || code == 48) return "Ομίχλη";
        if (code >= 51 && code <= 57) return "Ψιχάλες";
        if ((code >= 61 && code <= 67) || (code >= 80 && code <= 82)) return "Βροχή";
        if ((code >= 71 && code <= 77) || code == 85 || code == 86) return "Χιόνι";
        if (code >= 95) return "Καταιγίδα";
        return "Άγνωστος";
    }
}