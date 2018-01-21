package it.polimi.two.weiava;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Ava Ghafari on 1/21/2018.
 */
@Database(entities = {Reminder.class},version = 1)
public abstract class ReminderDataBase extends RoomDatabase {
    public abstract ReminderDao ReminderDao();
}
