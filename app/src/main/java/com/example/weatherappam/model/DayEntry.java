package com.example.weatherappam.model;

public class DayEntry {
    private String date;
    private int weatherCode;
    private double tempMax;
    private double tempMin;

    public DayEntry(String date, int weatherCode, double tempMax, double tempMin) {
        this.date = date;
        this.weatherCode = weatherCode;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public String getDate() { return date; }
    public int getWeatherCode() { return weatherCode; }
    public double getTempMax() { return tempMax; }
    public double getTempMin() { return tempMin; }
}