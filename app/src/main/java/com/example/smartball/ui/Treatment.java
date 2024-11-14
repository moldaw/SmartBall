package com.example.smartball.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Treatment {
    public int id;
    public String areaOption;
    public String timeOption;
    public long timeStamp;

    public Treatment(int id, String areaOption, String timeOption, long timeStamp) {
        this.id = id;
        this.areaOption = areaOption;
        this.timeOption = timeOption;
        this.timeStamp = timeStamp;
    }

    public String getFormattedTimestamp() {
        Date date = new Date(timeStamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }
}

