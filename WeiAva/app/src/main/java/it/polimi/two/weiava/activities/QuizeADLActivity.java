package it.polimi.two.weiava.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.models.AnsweredQuestion;
import it.polimi.two.weiava.models.Question;
import it.polimi.two.weiava.models.Schedule;

public class QuizeADLActivity extends AppCompatActivity {

    private QuestionLibraryADL mQuestionLibrary = new QuestionLibraryADL();

    private static final String TAG = "QuizeADLActivity";
    final QuizeADLActivity self=this;

    private DatabaseReference mDBRef;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Schedule schedule;
    private AnsweredQuestion answeredQuestion;
    private List<Question> questions;
    private Question currentQ;
    private DatabaseReference newRef;

    private String mUserId;

    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonQuite;

    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;
    //private boolean lastQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize);
        //lastQuestion = false;

        if (!isOnline()) {
            Toast.makeText(self, "Please check your connection. \n " +
                    "Database update failed!", Toast.LENGTH_SHORT).show();
        }

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDBRef = FirebaseDatabase.getInstance().getReference();

        schedule = new Schedule(System.currentTimeMillis(),"ADL");
        answeredQuestion = new AnsweredQuestion();
        questions = new ArrayList<Question>();


        if(mFirebaseUser == null){
            //TODO: load login view
        }
        else{
            mUserId = mFirebaseUser.getUid();
        }

        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonQuite = (Button)findViewById(R.id.quit);

        updateQuestion();

        //Start of Button Listener for Button1
        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here
                currentQ = new Question();
                currentQ.setQuestionText(mQuestionLibrary.getQuestion(mQuestionNumber-1));
                currentQ.setAnswerText(mButtonChoice1.getText().toString());
                questions.add(currentQ);

                if (mButtonChoice1.getText() == mAnswer){
                    mScore = mScore + 1;
                    updateScore(mScore);
                    updateQuestion();
                    //This line of code is optiona
                    //Toast.makeText(QuizeActivity.this, "correct", Toast.LENGTH_SHORT).show();

                }else {
                    //Toast.makeText(QuizeActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });

        //End of Button Listener for Button1

        //Start of Button Listener for Button2
        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here
                currentQ = new Question();
                currentQ.setQuestionText(mQuestionLibrary.getQuestion(mQuestionNumber-1));
                currentQ.setAnswerText(mButtonChoice2.getText().toString());
                questions.add(currentQ);

                if (mButtonChoice2.getText() == mAnswer){
                    //answeredQuestion.setQuesstion(mQuestionNumber,mQuestionLibrary.getQuestion(mQuestionNumber),mAnswer);
                    //questions.add(new Question(mQuestionLibrary.getQuestion(mQuestionNumber), mAnswer))
                    mScore = mScore + 1;
                    updateScore(mScore);
                    updateQuestion();
                    //This line of code is optiona
                    //Toast.makeText(QuizeActivity.this, "correct", Toast.LENGTH_SHORT).show();

                }else {
                    //Toast.makeText(QuizeActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
        });
        //End of Button Listener for Button2
        mButtonQuite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //My logic for Button goes in here
                //Intent intent = new Intent(QuizeADLActivity.this,QnrActivity.class);
                //startActivity(intent);
                self.finish();
                mScore=0;
            }
        });

    }

    private void updateQuestion(){
        if (mQuestionNumber == 6){
            writeDatabase();
            Toast.makeText(QuizeADLActivity.this, "Questionnaire Successfully filled", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(QuizeADLActivity.this,QnrActivity.class);
            //startActivity(intent);
            self.finish();
        }else {
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
            mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));

            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
            mQuestionNumber++;
        }
    }


    private void updateScore(int point) {
        mScoreView.setText("" + mScore);
    }

    //TODO: add the question answer to firebase
    private void writeDatabase(){
        String testId;

        schedule.setScore(mScore);
        answeredQuestion.setQuesstions(questions);
        newRef = mDBRef.child("Schedule").child(mUserId).push();
        testId = newRef.getKey();
        mDBRef.child("Schedule").child(mUserId).child(testId).setValue(schedule);
        mDBRef.child("QuestionAnswered").child(mUserId).child(testId).setValue(answeredQuestion);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}