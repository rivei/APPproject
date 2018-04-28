package it.polimi.two.weiava.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.adapter.AnsweredQnrRecyclerAdapter;
import it.polimi.two.weiava.adapter.ScheduleRecyclerAdapter;
import it.polimi.two.weiava.models.Question;
import it.polimi.two.weiava.models.Schedule;

public class AnsweredQnrActivity extends AppCompatActivity {

    private static final String TAG = "AnsweredQnrActivity";

    private RecyclerView scheduleRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AnsweredQnrRecyclerAdapter answeredQnrRecyclerAdapter;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDBRef;
    private String mUserId;
    private String testID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answered_qnr);

        Intent intent = getIntent();
        testID = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser != null){
            mUserId = mFirebaseUser.getUid();
        }

        scheduleRecyclerView = (RecyclerView) findViewById(R.id.answered_qnr_recycleView);
        linearLayoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setStackFromEnd(true); //Show records from the button of the screen

        mDBRef = FirebaseDatabase.getInstance().getReference();

        SnapshotParser<Question> parser = new SnapshotParser<Question>() {
            @Override
            public Question parseSnapshot(DataSnapshot dataSnapshot) {
                Question question = dataSnapshot.getValue(Question.class);
                if (question != null) {
                    question.setId(dataSnapshot.getKey());
                }
                return question;
            }
        };

        DatabaseReference qnrRef = mDBRef.child("QuestionAnswered").child(mUserId).child(testID).child("questions");

        FirebaseRecyclerOptions<Question> options =
                new FirebaseRecyclerOptions.Builder<Question>()
                        .setQuery(qnrRef, parser)
                        .build();

        answeredQnrRecyclerAdapter = new AnsweredQnrRecyclerAdapter(options);

        scheduleRecyclerView.setLayoutManager(linearLayoutManager);
        scheduleRecyclerView.setAdapter(answeredQnrRecyclerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Initialize Firebase listeners in adapter
        answeredQnrRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Tear down Firebase listeners in adapter
        answeredQnrRecyclerAdapter.stopListening();
    }


}
