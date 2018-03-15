package it.polimi.two.weiava.models;

/**
 * Created by Wei on 08/03/18.
 */

public class Question {

        public String questionText;
        public String answerText;

    public Question() {
    }

    public Question(String questionText, String answerText) {
        this.questionText = questionText;
        this.answerText = answerText;
    }

    public void setQuestionText(String questionText){
        this.questionText = questionText;
    }

    public void setAnswerText(String answerText){
        this.answerText = answerText;
    }
}
