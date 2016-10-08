package com.leo.sleep.component.city;

/**
 * Created by leo70 on 2016/10/5.
 */

public class ChangeCityEvent {
    String city;
    boolean isSetting;

    public ChangeCityEvent() {
    }

    public ChangeCityEvent(boolean isSetting) {
        this.isSetting = isSetting;
    }

    public ChangeCityEvent(String city) {
        this.city = city;
    }
}