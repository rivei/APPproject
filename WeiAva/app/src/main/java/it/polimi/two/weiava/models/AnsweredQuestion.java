package it.polimi.two.weiava.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei on 14/03/18.
 */

public class AnsweredQuestion {

    private List<Question> questions;

    public AnsweredQuestion() {
    }


    public void setQuesstions(List<Question> questions){
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
