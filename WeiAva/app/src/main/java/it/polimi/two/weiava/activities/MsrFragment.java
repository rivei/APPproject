package it.polimi.two.weiava.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.models.Schedule;


public class MsrFragment extends Fragment {
    ImageButton buttonwalking;
    ImageButton buttonweight;
    ImageButton buttongrip;
    private DatabaseReference newRef;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDBRef;
    private Schedule schedule;
    private String mUserId;
    int body_weight,grip_force;
    private String value="";
    private static final String TAG = "Measurement";

    Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_measure, container, false);
        context = rootView.getContext();
//        Intent intent = new Intent(context,MeasureActivity.class);
////        startActivity(intent);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDBRef = FirebaseDatabase.getInstance().getReference();

        buttonwalking = rootView.findViewById(R.id.button_walking);
        buttonweight = rootView.findViewById(R.id.button_weight);
        buttongrip = rootView.findViewById(R.id.button_grip);

        buttonwalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,WalkingActivity.class);
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
                                        Toast.makeText(context, "Body weight Successfully saved", Toast.LENGTH_SHORT).show();
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
                                      Toast.makeText(context, "Grip Force Successfully saved", Toast.LENGTH_SHORT).show();
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
        return rootView;

    }

    private void writeDatabase(){
        String testId;

        newRef = mDBRef.child("Schedule").child(mUserId).push();
        testId = newRef.getKey();
        mDBRef.child("Schedule").child(mUserId).child(testId).setValue(schedule);
    }
}


