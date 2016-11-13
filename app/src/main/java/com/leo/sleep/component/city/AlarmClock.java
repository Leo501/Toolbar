package com.leo.sleep.component.city;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.StringJoiner;

/**
 * Created by leo70 on 2016/10/23.
 */

public class AlarmClock {

    private int id;
    private boolean isOpen=false;
    private boolean isAM;//是否上午
    private int hours;
    private int minutes;
    private int restHours;
    private int restMinutes;

    public AlarmClock(boolean isOpen, boolean isAM, int hours, int minutes) {
        this.isOpen = isOpen;
        this.isAM = isAM;
        this.hours = hours;
        this.minutes = minutes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setAM(boolean AM) {
        isAM = AM;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }


    public boolean isOpen() {
        return isOpen;
    }

    public boolean isAM() {
        return isAM;
    }

    public int getHours() {
        return hours;
    }
    public String getHoursStr(){
        return (hours/10==0)?"0"+hours:""+hours;
    }

    public int getMinutes() {
        return minutes;
    }
    public String getMinutesStr(){
        return (minutes/10==0)?"0"+minutes:""+minutes;
    }

    public int getRestHours() {
        Calendar c = Calendar.getInstance();
        int nowHours=c.get(Calendar.HOUR_OF_DAY);
        int nowMinutes=c.get(Calendar.MINUTE);
        int add=(60-nowMinutes+minutes)/60;
        restHours=(hours-nowHours+23+add)%24;
        return restHours;
    }

    public int getRestMinutes() {
        Calendar c = Calendar.getInstance();
        int nowMinutes=c.get(Calendar.MINUTE);
        restMinutes=(60-nowMinutes+minutes)%60;
        return restMinutes;
    }

    public String getTimer(){
        return String.format("%s:%s",this.getHoursStr(),this.getMinutesStr());
    }

    public String getTestTimer(){
        return String.format("还有%d小时%d分钟",this.getRestHours(),this.getRestMinutes());
    }
}
