package it.polimi.two.weiava.models;

/**
 * Created by Wei on 08/03/18.
 */

public class Question {

        public int qid;
        public String questionText;
        public String answerText;

    public Question() {
    }

    public Question(int qid, String questionText, String answerText) {
        this.qid = qid;
        this.questionText = questionText;
        this.answerText = answerText;
    }
}
