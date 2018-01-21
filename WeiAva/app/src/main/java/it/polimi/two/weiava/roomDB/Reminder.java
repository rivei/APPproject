package it.polimi.two.weiava.roomDB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ava Ghafari on 1/21/2018.
 */

@Entity
public class Reminder {

    public Reminder(){}

    public Reminder(String test_Name, String date) {
        this.Test_Name = test_Name;
        this.Date = date;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Test")
    private String Test_Name;

    @ColumnInfo(name = "Date")
    private String Date;

    public int getId() {
        return id;
    }

    public String getTest_Name() {
        return Test_Name;
    }

    public String getDate() {
        return Date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTest_Name(String test_Name) {
        this.Test_Name = test_Name;
    }

    public void setDate(String date) {
        this.Date = date;
    }
}
