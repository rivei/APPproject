package it.polimi.two.weiava;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Ava Ghafari on 1/21/2018.
 */
@Dao
public interface ReminderDao {

    @Query("SELECT * FROM reminder")
    List<Reminder> getAllReminders();

    @Insert
    void insertAll(Reminder... reminders);
}
