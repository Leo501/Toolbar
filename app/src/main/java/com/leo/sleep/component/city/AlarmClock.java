package com.leo.sleep.component.city;

import java.sql.Time;

/**
 * Created by leo70 on 2016/10/23.
 */

public class AlarmClock {

    private boolean isOpen=false;
    private boolean isAM;//是否上午
    private String time;
    private String restTime;

    public AlarmClock(boolean isOpen, boolean isAM, String time, String restTime) {
        this.isOpen = isOpen;
        this.isAM = isAM;
        this.time = time;
        this.restTime = restTime;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setAM(boolean AM) {
        isAM = AM;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isAM() {
        return isAM;
    }

    public String getTime() {
        return time;
    }

    public String getRestTime() {
        return restTime;
    }
}
