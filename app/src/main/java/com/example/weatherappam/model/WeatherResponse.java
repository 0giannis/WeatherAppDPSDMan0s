package com.example.weatherappam.model;

import java.util.List;

public class WeatherResponse {
    public Current current;
    public Daily daily;
    public Hourly hourly;

    public static class Current {
        public double temperature_2m;
        public int weather_code;
        public double wind_speed_10m;
    }

    public static class Daily {
        public List<String> time;
        public List<Double> temperature_2m_max;
        public List<Double> temperature_2m_min;
        public List<Integer> weather_code;
    }

    public static class Hourly {
        public List<String> time;
        public List<Double> temperature_2m;
        public List<Integer> weather_code;
    }
}
/* 1. Στο City.java: Γιατί υπάρχει η μέθοδος toString();
Βλέπεις στο τέλος του City.java [source: 3] αυτή τη γραμμή:
@Override public String toString() { return name; }

Η εξήγηση: Όταν θα φτιάξουμε τις Ρυθμίσεις, θα βάλουμε μια λίστα (Spinner) που θα περιέχει αντικείμενα City. Το Android από μόνο του δεν ξέρει πώς να δείξει ένα ολόκληρο "αντικείμενο" στην οθόνη. Αν δεν υπήρχε η toString(), η λίστα θα έδειχνε ακαταλαβίστικα (π.χ. com.example.weatherappam.model.City@3f8a). Η μέθοδος toString() λέει στο Android: "Όταν σου ζητάνε να τυπώσεις αυτή την πόλη στην οθόνη, απλά δώσε τους το όνομά της (name)" [source: 3].

2. Στα DayEntry.java & HourlyEntry.java

Η εξήγηση: Το API του Open-Meteo μας δίνει τα δεδομένα της ημέρας και της ώρας χύμα σε ξεχωριστές λίστες (parallel arrays). Αυτά τα δύο αρχεία τα φτιάξαμε για να ομαδοποιήσουμε τα δεδομένα [source: 3]. Παίρνουμε δηλαδή τη θερμοκρασία, την ώρα και το εικονίδιο, τα βάζουμε μέσα σε ένα HourlyEntry [source: 3], και μετά δίνουμε αυτό το "πακετάκι" στον Custom Adapter για να το δείξει όμορφα σε μια γραμμή στη λίστα.

3. Στο WeatherResponse.java: Γιατί τα ονόματα των μεταβλητών είναι τόσο περίεργα;
Βλέπεις ονόματα όπως temperature_2m και weather_code [source: 3] (με underscores), που συνήθως στην Java τα γράφουμε ως temperature2m (camelCase).

Η εξήγηση: Αυτό είναι το "μαγικό" της βιβλιοθήκης Gson. Τα ονόματα των μεταβλητών σε αυτό το αρχείο πρέπει να είναι ακριβώς τα ίδια (letter by letter) με τα ονόματα (keys) που υπάρχουν μέσα στο JSON που μας στέλνει το ίντερνετ. Αν στο JSON λέει temperature_2m, πρέπει και εμείς να το πούμε temperature_2m [source: 3]. Έτσι, το Gson διαβάζει το JSON, βλέπει ότι ταιριάζουν τα ονόματα, και "κουμπώνει" αυτόματα τις τιμές μέσα στις μεταβλητές μας χωρίς να γράψουμε ούτε μια γραμμή κώδικα!  */