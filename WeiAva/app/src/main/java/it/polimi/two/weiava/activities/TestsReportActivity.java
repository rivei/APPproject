package it.polimi.two.weiava.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.adapter.ScheduleRecyclerAdapter;
import it.polimi.two.weiava.models.Schedule;

public class TestsReportActivity extends AppCompatActivity {

    private static final String TAG = "TestsReportActivity";

    private RecyclerView scheduleRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ScheduleRecyclerAdapter scheduleAdapter;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDBRef;
    private String mUserId;
    private String testType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_report);

        Intent intent = getIntent();
        testType = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser != null){
            mUserId = mFirebaseUser.getUid();
        }

        scheduleRecyclerView = (RecyclerView) findViewById(R.id.test_report_recycleView);
        linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setStackFromEnd(true); //Show records from the button of the screen

        mDBRef = FirebaseDatabase.getInstance().getReference();

        SnapshotParser<Schedule> parser = new SnapshotParser<Schedule>() {
            @Override
            public Schedule parseSnapshot(DataSnapshot dataSnapshot) {
                Schedule schedule = dataSnapshot.getValue(Schedule.class);
                if (schedule != null) {
                    schedule.setId(dataSnapshot.getKey());
                }
                return schedule;
            }
        };

        Query scheduleRef = mDBRef.child("Schedule").child(mUserId).orderByChild("qType").equalTo(testType);

        FirebaseRecyclerOptions<Schedule> options =
                new FirebaseRecyclerOptions.Builder<Schedule>()
                        .setQuery(scheduleRef, parser)
                        .build();

        scheduleAdapter = new ScheduleRecyclerAdapter(options);
        scheduleRecyclerView.setLayoutManager(linearLayoutManager);
        scheduleRecyclerView.setAdapter(scheduleAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Initialize Firebase listeners in adapter
        scheduleAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Tear down Firebase listeners in adapter
        scheduleAdapter.stopListening();
    }

    @Override
    public void onPause() {
        scheduleAdapter.stopListening();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        scheduleAdapter.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
