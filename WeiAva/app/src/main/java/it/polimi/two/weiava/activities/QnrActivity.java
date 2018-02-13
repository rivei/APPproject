package it.polimi.two.weiava.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.polimi.two.weiava.R;

public class QnrActivity extends AppCompatActivity {
    Button buttonGDS;
    Button buttonADL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnr);
        buttonGDS = findViewById(R.id.button_gds);
        buttonADL = findViewById(R.id.button_adl);
        buttonGDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QnrActivity.this,QuizeActivity.class);
                startActivity(intent);
            }
        });
        buttonADL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QnrActivity.this,Activity_question.class);
                startActivity(intent);
            }
        });
    }
    


}
