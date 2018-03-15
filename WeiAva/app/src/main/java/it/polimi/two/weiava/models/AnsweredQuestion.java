package it.polimi.two.weiava.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei on 14/03/18.
 */

public class AnsweredQuestion {

    public String qType;
    public List<Question> questions;
    //public Question q1;
    //public Question q2;
    public int score;
    public String date; //TODO: check the data type
    public String uid;
    //private int questionnumber;

    public AnsweredQuestion() {
    }

    public AnsweredQuestion(String qType, String date) {
        this.qType = qType;
        this.date = date;
        if (qType == "ADL") {
//            questionnumber = 6;
            //q1 = new Question("A1","A1");
            //q2 = new Question("Q2","A2");
/*            questions = new ArrayList<Question>();
            Question qtemp;
            qtemp = new Question("Question","answer");
            questions.add(qtemp);*/
//            for (int i=0;i<6;i++){
//                qtemp.setQuestionText("question"+i);
//                qtemp.setAnswerText("answer"+i);
//                questions.add(qtemp);
//            }
        }
    }

    public void setScore(int inputscore){
        this.score = inputscore;
    }

    public void setUid(String inUid){
        this.uid = inUid;
    }

    public void setQuesstions(List<Question> questions){
        this.questions = questions;
    }
//    public void addQuestion(Question question){
//
//    }
}
