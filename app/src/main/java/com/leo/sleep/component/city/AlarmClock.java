package com.leo.sleep.component.city;

/**
 * Created by leo70 on 2016/10/23.
 */

public class AlarmClock {
    private boolean open=false;
    private String time;

    public AlarmClock(boolean open, String time) {
        this.open = open;
        this.time = time;
    }

    public boolean isOpen() {
        return open;
    }

    public String getTime() {
        return time;
    }
}
