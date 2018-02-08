package it.polimi.two.weiava.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import it.polimi.two.weiava.R;

public class MeasureActivity extends AppCompatActivity {
    ImageButton buttonwalking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        buttonwalking = findViewById(R.id.button_walking);
        buttonwalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeasureActivity.this,WalkingActivity.class);
                startActivity(intent);
            }
        });
    }


}
