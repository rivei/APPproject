package it.polimi.two.weiava.models;

/**
 * Created by Wei on 08/03/18.
 */

public class Question {

        private int qid;
        private String questionText;
        private String OptionA;
        private String OptionB;
        private String OptionC;
        private String OptionD;

        public Question(){
            qid=0;
            questionText ="";
            OptionA="";
            OptionB="";
            OptionC="";
            OptionD="";
        }

        public Question(String quesTion, String opA, String opB, String opC,String opD,
                        String ansWer) {

            questionText = quesTion;
            OptionA = opA;
            OptionB = opB;
            OptionC = opC;
            OptionD = opD;
        }

        public int getId() {
            return qid;
        }

        public void setId(int id) {
            this.qid = id;
        }

        public String getQUESTION() {
            return questionText;
        }

        public void setQUESTION(String QUESTION) {
            this.questionText = QUESTION;
        }

        public String getOptionA() {
            return OptionA;
        }

        public void setOptionA(String optionA) {
            OptionA = optionA;
        }

        public String getOptionB() {
            return OptionB;
        }

        public void setOptionB(String optionB) {
            OptionB = optionB;
        }

        public String getOptionC() {
            return OptionC;
        }

        public void setOptionC(String optionC) {
            OptionC = optionC;
        }

        public String getOptionD() {
            return OptionD;
        }

        public void setOptionD(String optionD) {
            OptionD = optionD;
        }

}
