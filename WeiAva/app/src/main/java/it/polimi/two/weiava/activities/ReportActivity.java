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
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.models.Schedule;

public class ReportActivity extends AppCompatActivity {

    final ReportActivity self=this;

    GraphView graph;
    ImageButton bgwalking;
    ImageButton bgweight;
    ImageButton bggrip;
    Button bGDS;
    Button bADL;

    LineGraphSeries<DataPoint> series_walking = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series_weight = new LineGraphSeries<>();
    LineGraphSeries<DataPoint> series_grip = new LineGraphSeries<>();

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

        ReadDatabase("WalkingSpeed");
        ReadDatabase("GripForce");
        ReadDatabase("BodyWeight");

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

                graph.removeAllSeries();
                graph.addSeries(series_walking);
                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(self));
                graph.getGridLabelRenderer().setNumHorizontalLabels(4);
                graph.setTitle("WalkingSpeed");
                graph.setTitleTextSize(80);
            }
        });

        bgweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graph.removeAllSeries();
                graph.addSeries(series_weight);
                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(self));
                graph.getGridLabelRenderer().setNumHorizontalLabels(4);
                graph.setTitle("Body Weight");
                graph.setTitleTextSize(80);
            }
        });

        bggrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graph.removeAllSeries();
                graph.addSeries(series_grip);
                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(self));
                graph.getGridLabelRenderer().setNumHorizontalLabels(4);
                graph.setTitle("Grip Force");
                graph.setTitleTextSize(80);
            }
        });

    }

    public void onResume(){
        super.onResume();
    }

    private void ReadDatabase(final String Qtype){
        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;
        DatabaseReference mDBRef;
        String mUserId;
        String testType;
        final List<Date> testdates = new ArrayList<>();
        testType=Qtype;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //Calendar calendar = Calendar.getInstance();
        //Date d1 = ;//calendar.getTime();

        final List<Schedule> schedules = new ArrayList<>();

        if (mFirebaseUser != null) {
            mUserId = mFirebaseUser.getUid();
            mDBRef = FirebaseDatabase.getInstance().getReference();

            Query scheduleRef = mDBRef.child("Schedule").child(mUserId).orderByChild("qType").equalTo(testType).limitToLast(10);

            scheduleRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    schedules.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Schedule schedule = postSnapshot.getValue(Schedule.class);
                        schedules.add(schedule);
                    }
                    for (int i = 0; i < schedules.size(); i++) {
                        Schedule schedule = schedules.get(i);
                        Date d1 = new Date(schedule.getTimestamp());
                        testdates.add(d1);
                        switch (Qtype){
                            case "WalkingSpeed":
                                series_walking.appendData(new DataPoint(d1,schedule.getScore()),true,schedules.size());
                                break;
                            case "GripForce":
                                series_grip.appendData(new DataPoint(d1,schedule.getScore()),true,schedules.size());
                                break;
                            case "BodyWeight":
                                series_weight.appendData(new DataPoint(d1,schedule.getScore()),true,schedules.size());
                                break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

}
