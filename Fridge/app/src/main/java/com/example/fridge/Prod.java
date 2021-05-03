package com.example.fridge;

import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Prod {
    long id;
    String name;
    int days;
    String date1;
    static int ID = 1;
    String expirationDate;

    long milliseconds ;

    long amountOfDays ;
    long nowDateMills;
    String amountOfDays1 = "";
    String amountMills1 = "";
    String print1 = "";
    String print2 = "";

    public Prod(long id, String name, int days, long date){
        this.id = id;
        this.name = name;
        this.days = days;
        this.milliseconds = date;
        Log.d("seconds",Long.toString(this.milliseconds));
    }
    public Prod(long id, String name, int days) {
        SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy");
        this.id = id;
        this.name = name;

        this.date1 = date1;
        /*long longTime = (days*86400000L);
        expirationDate = formater.format(date2.getTime()+longTime);*/

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getDays() {
        return days;
    }

    public void setYear(int days) {
        this.days = days;
    }


    @Override
    public String toString() {
        return this.name + "\n" +"Добавлено: " +new Date(this.milliseconds)+ "\n" +"Истечёт "+new Date(this.milliseconds + this.days*86400000L);
    }
}
