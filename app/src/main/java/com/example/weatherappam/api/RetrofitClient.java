package com.example.weatherappam.api;

import com.example.weatherappam.SettingsActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.open-meteo.com/";
    private static RetrofitClient instance;
    private SettingsActivity.WeatherApiService apiService;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(SettingsActivity.WeatherApiService.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public SettingsActivity.WeatherApiService getApiService() {
        return apiService;
    }
}
/* Αν σε ρωτήσουν "Τι κάνει αυτή η κλάση και γιατί τη γράψαμε έτσι;", εδώ είναι τα 3 βασικά σημεία που πρέπει να αναφέρεις:

1. Το Σχεδιαστικό Πρότυπο Singleton (Μέθοδος getInstance)
Εδώ εφαρμόζεις ένα από τα πιο διάσημα πρότυπα σχεδίασης (Design Patterns) στον προγραμματισμό: το Singleton Pattern.

Η θεωρία: Βλέπεις ότι ο κατασκευαστής (private RetrofitClient()) είναι ιδιωτικός. Αυτό σημαίνει ότι καμία άλλη κλάση δεν μπορεί να φτιάξει ένα νέο αντικείμενο RetrofitClient με τη λέξη new.

Πώς λειτουργεί: Όλοι αναγκάζονται να καλέσουν τη μέθοδο getInstance(). Αυτή ελέγχει: "Έχει ξαναφτιαχτεί το αντικείμενο; Αν όχι, φτιάξ' το (new RetrofitClient()). Αν ναι, δώσε μου το ήδη υπάρχον".

Γιατί το κάνουμε: Η δημιουργία ενός αντικειμένου Retrofit είναι "βαριά" δουλειά για το κινητό. Με το Singleton, εξασφαλίζουμε ότι η εφαρμογή θα δημιουργήσει τη σύνδεση με το ίντερνετ αυστηρά μία φορά σε όλη τη διάρκεια λειτουργίας της, εξοικονομώντας μνήμη και πόρους.

2. Ο Μεταφραστής GSON (addConverterFactory)
Το Open-Meteo API (όπως τα περισσότερα APIs) μας στέλνει τα δεδομένα σε μορφή JSON (ένα τεράστιο, δυσανάγνωστο μπλοκ κειμένου). Η γραμμή .addConverterFactory(GsonConverterFactory.create()) είναι ο "μεταφραστής" σου. Αναλαμβάνει αυτόματα να πάρει αυτό το κείμενο JSON και να το μετατρέψει κατευθείαν σε αντικείμενα Java (δηλαδή στις κλάσεις DayEntry και HourlyEntry που θα δούμε μετά). Έτσι δεν χρειάζεται να γράψεις εσύ χειροκίνητο κώδικα για να ψάχνεις τα δεδομένα μέσα στο κείμενο.

3. Το Βασικό URL (BASE_URL)
Ορίζεις το [https://api.open-meteo.com/](https://api.open-meteo.com/) ως σταθερά. Το Retrofit λειτουργεί "χτίζοντας" το link. Εδώ βάζεις τα θεμέλια (το root domain). Το υπόλοιπο κομμάτι του link (το endpoint /v1/forecast και οι συντεταγμένες) θα προστεθούν στο επόμενο αρχείο.

Συνεχίζουμε περίφημα. Στείλε μου το επόμενο αρχείο, ιδανικά το WeatherApiService.java (που πάει πακέτο με αυτό) ή την MainActivity.java!
*/