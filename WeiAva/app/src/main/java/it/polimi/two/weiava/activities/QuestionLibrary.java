package it.polimi.two.weiava.activities;

/**
 * Created by Ava Ghafari on 2/13/2018.
 */

public class QuestionLibrary {

        private String mQuestions [] = {
                "Are you basically satisfied with your life?",
                "Have you dropped many of your activities and interests?",
                "Do you feel that your life is empty?",
                "Do you often get bored?",
                "Are you hopeful about the future?",
                "Are you bothered by thoughts you can’t get out of your head?",
                "Are you in good spirits most of the time?",
                "Are you afraid that something bad is going to happen to you?",
                "Do you feel happy most of the time?",
                "Do you often feel helpless?",
                "Do you often get restless and fidgety?",
                "Do you prefer to stay at home, rather than going out and doing new things?",
                "Do you frequently worry about the future?",
                "Do you feel you have more problems with memory than most?",
                "Do you think it is wonderful to be alive now?",
                "Do you often feel downhearted and blue?",
                "Do you feel pretty worthless the way you are now?",
                "Do you worry a lot about the past?",
                "Do you find life very exciting?",
                "Is it hard for you to get started on new projects?",
                "Do you feel full of energy?",
                "Do you feel that your situation is hopeless?",
                "Do you think that most people are better off than you are?",
                "Do you frequently get upset over little things?",
                "Do you frequently feel like crying?",
                "Do you have trouble concentrating?",
                "Do you enjoy getting up in the morning?",
                "Do you prefer to avoid social gatherings?",
                "Is it easy for you to make decisions?",
                "Is your mind as clear as it used to be?"
};


        private String mChoices [][] = {
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
                {"Yes", "No"},
        };



        private String mCorrectAnswers[] = {
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes",
                "Yes",
                "No",
                "No",
                "Yes"

        };




        public String getQuestion(int a) {
            String question = mQuestions[a];
            return question;
        }


        public String getChoice1(int a) {
            String choice0 = mChoices[a][0];
            return choice0;
        }


        public String getChoice2(int a) {
            String choice1 = mChoices[a][1];
            return choice1;
        }

      /*  public String getChoice3(int a) {
            String choice2 = mChoices[a][2];
            return choice2;
        }*/

        public String getCorrectAnswer(int a) {
            String answer = mCorrectAnswers[a];
            return answer;
        }


}
