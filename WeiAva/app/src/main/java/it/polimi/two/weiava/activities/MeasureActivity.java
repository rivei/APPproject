package it.polimi.two.weiava.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.models.Schedule;

public class MeasureActivity extends AppCompatActivity {
    ImageButton buttonwalking;
    ImageButton buttonweight;
    ImageButton buttongrip;
    final Context context = this;
    private DatabaseReference newRef;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDBRef;
    private Schedule schedule;
    private String mUserId;
    int body_weight,grip_force;
    private String value="";
    private static final String TAG = "Measurement";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDBRef = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.content_msr);
        //setContentView(R.layout.fragment_msr);
        buttonwalking = findViewById(R.id.button_walking);
        buttonweight = findViewById(R.id.button_weight);
        buttongrip = findViewById(R.id.button_grip);

        buttonwalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeasureActivity.this,WalkingActivity.class);
                startActivity(intent);
            }
        });

        if(mFirebaseUser == null){
            //TODO: load login view
        }
        else{
            mUserId = mFirebaseUser.getUid();
        }


        buttonweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                schedule = new Schedule(System.currentTimeMillis(),"BodyWeight");
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.weight_enter, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        value=userInput.getText().toString();
                                        Log.i(TAG, "value"+value);
                                        body_weight = Integer.parseInt(value);
                                        Log.i(TAG, "integer"+body_weight);
                                        schedule.setScore(body_weight);
                                        writeDatabase();
                                        Toast.makeText(MeasureActivity.this, "Body weight Successfully saved", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        buttongrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schedule = new Schedule(System.currentTimeMillis(),"GripForce");
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.weight_enter, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        value=userInput.getText().toString();
                                        Log.i(TAG, "value"+value);
                                        grip_force = Integer.parseInt(value);
                                        Log.i(TAG, "integer"+grip_force);
                                        schedule.setScore(grip_force);
                                        writeDatabase();
                                        Toast.makeText(MeasureActivity.this, "Grip Force Successfully saved", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
            }
        );
    }

    private void writeDatabase(){
        String testId;

        newRef = mDBRef.child("Schedule").child(mUserId).push();
        testId = newRef.getKey();
        mDBRef.child("Schedule").child(mUserId).child(testId).setValue(schedule);
    }
}
