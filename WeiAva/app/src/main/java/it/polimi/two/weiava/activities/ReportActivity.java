package it.polimi.two.weiava.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.models.Schedule;

public class ReportActivity extends AppCompatActivity {

    GraphView graph;
    ImageButton bgwalking;
    ImageButton bgweight;
    ImageButton bggrip;
    Button bGDS;
    Button bADL;
    String Qtype;
    final LineGraphSeries<DataPoint> series_walking = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(0, 1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),
            new DataPoint(4, 6)
    });

    final LineGraphSeries<DataPoint> series_weight = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(1, 0),
            new DataPoint(5, 1),
            new DataPoint(3, 2),
            new DataPoint(2, 3),
            new DataPoint(6, 4)
    });
    final LineGraphSeries<DataPoint> series_grip = new LineGraphSeries<>(new DataPoint[] {
            new DataPoint(1, 1),
            new DataPoint(1, 5),
            new DataPoint(1, 3),
            new DataPoint(1, 2),
            new DataPoint(1, 6)
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        bGDS = (Button) findViewById(R.id.button_GDSreport);
        bADL = (Button) findViewById(R.id.button_ADLreport);
        bggrip = (ImageButton) findViewById(R.id.Button_ggrip);
        bgweight = (ImageButton) findViewById(R.id.Button_gweight);
        bgwalking = (ImageButton) findViewById(R.id.Button_gwalking);
        graph = (GraphView) findViewById(R.id.graph);

        bGDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity.this,TestsReportActivity.class);
                String message = "GDS";
                intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });
        bADL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportActivity.this,TestsReportActivity.class);
                String message = "ADL";
                intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

        bgwalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ReadDatabase("WalkingSpeed");

//                graph.removeAllSeries();
//                graph.addSeries(series_walking);
//                graph.setTitle("WalkingSpeed");
//                graph.setTitleTextSize(80);
            }
        });

        bgweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                graph.removeAllSeries();
                graph.addSeries(series_weight);
                graph.setTitle("Body Weight");
                graph.setTitleTextSize(80);
            }
        });

        bggrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                graph.removeAllSeries();
                graph.addSeries(series_grip);
                graph.setTitle("Grip Force");
                graph.setTitleTextSize(80);
            }
        });

    }

    public void onResume(){
        super.onResume();
    }

    private void ReadDatabase(String Qtype){

        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;
        DatabaseReference mDBRef;
        String mUserId;
        String testType;
        final List<Long> timestamps = new ArrayList<>();
        final List<Integer> scores = new ArrayList<>();
        testType=Qtype;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        final List<Schedule> schedules = new ArrayList<>();

        if (mFirebaseUser != null) {
            mUserId = mFirebaseUser.getUid();


            mDBRef = FirebaseDatabase.getInstance().getReference();

            Query scheduleRef = mDBRef.child("Schedule").child(mUserId).orderByChild("qType").equalTo(testType);

            scheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    schedules.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Schedule schedule = postSnapshot.getValue(Schedule.class);
                        schedules.add(schedule);
                        // here you can access to name property like university.name
                    }
                    for (int i = 0; i < schedules.size(); i++) {
                        Schedule schedule = schedules.get(i);
                        timestamps.add(schedule.getTimestamp());
                        scores.add(schedule.getScore());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

}
