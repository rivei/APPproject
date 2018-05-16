package it.polimi.two.weiava.activities;

//import android.arch.persistence.room.Room;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.polimi.two.weiava.R;

import it.polimi.two.weiava.models.Schedule;


public class WalkingActivity extends AppCompatActivity {

    TextView textView ;
    Button start,stop,reset,save;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Seconds, Minutes, MilliSeconds, walking_duration;
    Handler handler;
    private DatabaseReference newRef;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDBRef;
    private Schedule schedule;
    private String mUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDBRef = FirebaseDatabase.getInstance().getReference();
        schedule = new Schedule(System.currentTimeMillis(),"WalkingSpeed");
        setContentView(R.layout.activity_walking);

        textView = (TextView)findViewById(R.id.textView);
        start = (Button)findViewById(R.id.button_start);
        stop = (Button)findViewById(R.id.button_stop);
        reset = (Button)findViewById(R.id.button_reset);
        save = (Button) findViewById(R.id.button_save);
        handler = new Handler() ;
        stop.setVisibility(View.GONE);
        start.setVisibility(View.VISIBLE);
        save.setEnabled(false);

        if(mFirebaseUser == null){
            //TODO: load login view
        }
        else{
            mUserId = mFirebaseUser.getUid();
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
                save.setEnabled(false);
                start.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                save.setEnabled(true);
                stop.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;
                textView.setText("00:00:00");
                reset.setEnabled(false);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                walking_duration = Minutes*60+Seconds;
                schedule.setScore(walking_duration);
                writeDatabase();
                Toast.makeText(WalkingActivity.this, "Walking Speed Successfully saved", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds)+":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };

    private void writeDatabase(){
        String testId;

        newRef = mDBRef.child("Schedule").child(mUserId).push();
        testId = newRef.getKey();
        mDBRef.child("Schedule").child(mUserId).child(testId).setValue(schedule);
    }
}