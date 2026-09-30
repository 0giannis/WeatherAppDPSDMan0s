package com.example.weatherappam.model;

public class HourlyEntry {
    private String time;
    private double temperature;
    private int weatherCode;

    public HourlyEntry(String time, double temperature, int weatherCode) {
        this.time = time;
        this.temperature = temperature;
        this.weatherCode = weatherCode;
    }

    public String getTime() { return time; }
    public double getTemperature() { return temperature; }
    public int getWeatherCode() { return weatherCode; }
}