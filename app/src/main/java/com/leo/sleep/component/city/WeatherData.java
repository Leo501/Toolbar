package com.leo.sleep.component.city;

/**
 * Created by leo70 on 2016/10/19.
 */

public class WeatherData {
    private String icon;
    private String city;
    private int temp_now;
    private int temp_today_max;
    private int temp_today_min;
    private int temp_tomorrow_max;
    private int temp_tomarrow_min;

    public WeatherData(String icon, String city, int temp_now, int temp_today_max, int temp_today_min, int temp_tomorrow_max, int temp_tomarrow_min) {
        this.icon = icon;
        this.city = city;
        this.temp_now = temp_now;
        this.temp_today_max = temp_today_max;
        this.temp_today_min = temp_today_min;
        this.temp_tomorrow_max = temp_tomorrow_max;
        this.temp_tomarrow_min = temp_tomarrow_min;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTemp_now() {
        return temp_now;
    }

    public void setTemp_now(int temp_now) {
        this.temp_now = temp_now;
    }

    public int getTemp_today_max() {
        return temp_today_max;
    }

    public void setTemp_today_max(int temp_today_max) {
        this.temp_today_max = temp_today_max;
    }

    public int getTemp_today_min() {
        return temp_today_min;
    }

    public void setTemp_today_min(int temp_today_min) {
        this.temp_today_min = temp_today_min;
    }

    public int getTemp_tomorrow_max() {
        return temp_tomorrow_max;
    }

    public void setTemp_tomorrow_max(int temp_tomorrow_max) {
        this.temp_tomorrow_max = temp_tomorrow_max;
    }

    public int getTemp_tomarrow_min() {
        return temp_tomarrow_min;
    }

    public void setTemp_tomarrow_min(int temp_tomarrow_min) {
        this.temp_tomarrow_min = temp_tomarrow_min;
    }
}
