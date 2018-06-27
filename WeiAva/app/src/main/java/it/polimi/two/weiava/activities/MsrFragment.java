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
                Intent intent = new Intent(context,DeviceListActivity.class);
                String message = "BodyWeight";
                intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
                startActivity(intent);

            }
        });
        buttongrip.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              Intent intent = new Intent(context,DeviceListActivity.class);
                                              String message = "GripForce";
                                              intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
                                              startActivity(intent);
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

    @Override
    public void onResume(){
        super.onResume();
    }
}


