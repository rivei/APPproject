package it.polimi.two.weiava.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import it.polimi.two.weiava.R;

public class QuizeADLActivity extends AppCompatActivity {

    private QuestionLibraryADL mQuestionLibrary = new QuestionLibraryADL();

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

                if (mButtonChoice2.getText() == mAnswer){
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
                Intent intent = new Intent(QuizeADLActivity.this,QnrActivity.class);
                startActivity(intent);
                mScore=0;
            }
        });

    }

    private void updateQuestion(){
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));

        mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
        if (mQuestionNumber==5){
            Toast.makeText(QuizeADLActivity.this, "Questionnaire Successfully filled", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QuizeADLActivity.this,QnrActivity.class);
            startActivity(intent);
        }else {
            mQuestionNumber++;
        }
    }


    private void updateScore(int point) {
        mScoreView.setText("" + mScore);
    }
}