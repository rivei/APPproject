package it.polimi.two.weiava.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Wei on 08/03/18.
 */
@IgnoreExtraProperties
public class User {
    public String username;
    public String email;

    public User(){

    }

    public User(String username, String email){
        this.username = username;
        this.email = email;
    }
}
