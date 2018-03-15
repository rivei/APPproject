package it.polimi.two.weiava.models;

import java.util.List;

/**
 * Created by Wei on 14/03/18.
 */

public class AnsweredQuestion {

    public String qType;
    //public List<Question> question;
    public int score;
    public String date; //TODO: check the data type
    public String uid;

    public AnsweredQuestion() {
    }

    public AnsweredQuestion(String qType, String date) {
        this.qType = qType;
        this.date = date;
    }

    public void setScore(int inputscore){
        this.score = inputscore;
    }

    public void setUid(String inUid){
        this.uid = inUid;
    }

//    public void AddQuesstion(){
//
//    }

}
