package it.polimi.two.weiava.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import it.polimi.two.weiava.R;

public class ReportActivity extends AppCompatActivity {

    GraphView graph;
    ImageButton bgwalking;
    ImageButton bgweight;
    ImageButton bggrip;
    Button bGDS;
    Button bADL;
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
            public void onClick(View view) {
                graph.removeAllSeries();
                graph.addSeries(series_walking);
                graph.setTitle("Walking Speed");
                graph.setTitleTextSize(80);
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


}
