package it.polimi.two.weiava.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Wei on 08/03/18.
 */
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String name;
    public long dobTimestamp;
    public boolean gender;
    public float bodyweight;

    public User(){

    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    public User(String username, String email, String name, long DOBmilliseconds, boolean gender, float bodyweight){
        this.username = username;
        this.email = email;
        this.name = name;
        this.dobTimestamp = DOBmilliseconds;
        this.gender = gender;
        this.bodyweight = bodyweight;
    }

    public long getDOBmilliseconds() {
        return dobTimestamp;
    }

    public void setDOBmilliseconds(long DOBmilliseconds) {
        this.dobTimestamp = DOBmilliseconds;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public double getBodyweight() {
        return bodyweight;
    }

    public void setBodyweight(float bodyweight) {
        this.bodyweight = bodyweight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
