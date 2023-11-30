package com.labactivity.logmate;

public class MainModel {
    private String Date;
    private String Name;
    private String ID_Num;
    private String Time_In;
    private String Time_Out;

    public MainModel() {
        // Default constructor required for Firebase
    }

    // Constructor with parameters
    public MainModel(String date, String name, String idNum, String timeIn, String timeOut) {
        this.Date = date;
        this.Name = name;
        this.ID_Num = idNum;
        this.Time_In = timeIn;
        this.Time_Out = timeOut;
    }

    // Getter methods
    public String getDate() {
        return Date;
    }

    public String getName() {
        return Name;
    }

    public String getId_Num() {
        return ID_Num;
    }

    public String getTime_In() {
        return Time_In;
    }

    public String getTime_Out() {
        return Time_Out;
    }
}

