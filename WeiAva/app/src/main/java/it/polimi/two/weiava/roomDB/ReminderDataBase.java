package it.polimi.two.weiava.roomDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import it.polimi.two.weiava.roomDB.Reminder;
import it.polimi.two.weiava.roomDB.ReminderDao;

/**
 * Created by Ava Ghafari on 1/21/2018.
 */
@Database(entities = {Reminder.class},version = 1)
public abstract class ReminderDataBase extends RoomDatabase {
    public abstract ReminderDao ReminderDao();
}
