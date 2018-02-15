package it.polimi.two.weiava.activities;

/**
 * Created by Ava Ghafari on 2/15/2018.
 */

public class QuestionLibraryADL {

        private String mQuestions [] = {
                "BATHING",
                "DRESSING",
                "TOILETING",
                "TRANSFERRING",
                "CONTINENCE",
                "FEEDING"
        };


        private String mChoices [][] = {
                {"Bathes self completely or needs help in bathing only a single part of the body such as the back, genital area or disabled extremity.",
                        "Needs help with bathing more than one part of the body, getting in or out of the tub or shower. Requires total bathing."},
                {"Gets clothes from closets and drawers and puts on clothes and outer garments complete with fasteners. May have help tying shoes.",
                        "Needs help with dressing self or needs to be completely dressed."},
                {"Goes to toilet, gets on and off, arranges clothes, cleans genital area without help.",
                        "Needs help transferring to the toilet, cleaning self or uses bedpan or commode."},
                {"Moves in and out of bed or chair unassisted. Mechanical transferring aides are acceptable.",
                        "Needs help in moving from bed to chair or requires a complete transfer."},
                {"Exercises complete self control over urination and defecation.",
                        "Is partially or totally incontinent of bowel or bladder."},
                {"Gets food from plate into mouth without help. Preparation of food may be done by another person.",
                        "Needs partial or total help with feeding or requires parenteral feeding."}
        };



        private String mCorrectAnswers[] = {
                "Bathes self completely or needs help in bathing only a single part of the body such as the back, genital area or disabled extremity.",
                "Gets clothes from closets and drawers and puts on clothes and outer garments complete with fasteners. May have help tying shoes.",
                "Goes to toilet, gets on and off, arranges clothes, cleans genital area without help.",
                "Moves in and out of bed or chair unassisted. Mechanical transferring aides are acceptable.",
                "Exercises complete self control over urination and defecation.",
                "Gets food from plate into mouth without help. Preparation of food may be done by another person."
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


        public String getCorrectAnswer(int a) {
            String answer = mCorrectAnswers[a];
            return answer;
        }


}

