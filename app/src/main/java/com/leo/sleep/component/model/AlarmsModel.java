package com.leo.sleep.component.model;

import com.litesuits.orm.db.annotation.Check;
import com.litesuits.orm.db.annotation.Default;
import com.litesuits.orm.db.annotation.NotNull;

import com.litesuits.orm.db.annotation.Table;

import java.util.Calendar;

/**
 * Created by leo70 on 2016/11/6.
 */

@Table("alarm_table")
public class AlarmsModel extends ModelBase{

    @NotNull
    @Default("true")
    private boolean isOpen;

    @NotNull
    @Default("true")
    private boolean isAM;

    @NotNull
    @Check("hours >=0 AND hours < 24")
    private int hours;

    @NotNull
    @Check("minutes >-1 AND minutes<60")
    private int minutes;

    public AlarmsModel(boolean isOpen, int hours, int minutes) {
        this.isOpen = isOpen;
        this.hours = hours;
        this.minutes = minutes;
        this.isAM=getAM();
    }

    public AlarmsModel(boolean isOpen, boolean isAM, int hours, int minutes) {
        this.isOpen = isOpen;
        this.isAM = isAM;
        this.hours = hours;
        this.minutes = minutes;
    }

    public AlarmsModel(int id, boolean isOpen, boolean isAM, int hours, int minutes) {
        this.id=id;
        this.isOpen = isOpen;
        this.isAM = isAM;
        this.hours = hours;
        this.minutes = minutes;
    }

    public void setId(int id){ this.id=id;};

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

    public int getId(){ return this.id;}

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isAM() {
        return isAM;
    }

    public boolean getAM(){
        return hours/13>0?false:true;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getHoursStr(){
        return (hours/10==0)?"0"+hours:""+hours;
    }

    public String getMinutesStr(){
        return (minutes/10==0)?"0"+minutes:""+minutes;
    }

    public int getRestHours() {
        Calendar c = Calendar.getInstance();
        int nowHours=c.get(Calendar.HOUR_OF_DAY);
        int nowMinutes=c.get(Calendar.MINUTE);
        int add=(60-nowMinutes+minutes)/60;
        return (hours-nowHours+23+add)%24;
    }

    public int getRestMinutes() {
        Calendar c = Calendar.getInstance();
        int nowMinutes=c.get(Calendar.MINUTE);
        return (60-nowMinutes+minutes)%60;
    }

    public String getTime(){
        return String.format("%s:%s",this.getHoursStr(),this.getMinutesStr());
    }

    public String getRestTime(){
        return String.format("还有%d小时%d分钟",this.getRestHours(),this.getRestMinutes());
    }
}
