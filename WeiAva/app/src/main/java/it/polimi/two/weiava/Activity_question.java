package it.polimi.two.weiava;

import android.arch.persistence.room.Room;
import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class Activity_question extends AppCompatActivity {
    RecyclerView recyclerview;
    RecyclerView.Adapter adapter;
    //List<Reminder> remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        remember = new ArrayList<>();
//        for(int i=0 ; i < 10 ; i++){
//            Reminder reminder = new Reminder("GDS"+i, "1/1/91");
//            remember.add(reminder);
//    }
        ReminderDataBase db= Room.databaseBuilder(getApplicationContext(),ReminderDataBase.class, "production").allowMainThreadQueries().build();
        List<Reminder> reminders= db.ReminderDao().getAllReminders();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        recyclerview =  findViewById(R.id.question_view);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new qnrAdapter(reminders);
        recyclerview.setAdapter(adapter);
    }
 }
