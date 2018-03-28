package it.polimi.two.weiava.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.models.Question;


public class AnsweredQnrRecyclerAdapter extends FirebaseRecyclerAdapter <Question, AnsweredQnrRecyclerAdapter.QuestionViewHolder> {

    public static class QuestionViewHolder extends RecyclerView.ViewHolder{

        TextView questionText;
        TextView answerText;

        public QuestionViewHolder(View v) {
            super(v);
            questionText = (TextView) itemView.findViewById(R.id.questionTextView);
            answerText = (TextView) itemView.findViewById(R.id.answerTextView);
        }

    }



    public AnsweredQnrRecyclerAdapter(FirebaseRecyclerOptions<Question> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(AnsweredQnrRecyclerAdapter.QuestionViewHolder holder, int position, Question model) {
        if(model.getQuestionText() != null) {
            holder.questionText.setText(model.getQuestionText());
            holder.answerText.setText(model.getAnswerText());
        }
    }

    @Override
    public AnsweredQnrRecyclerAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answered_question, parent, false);
        return new AnsweredQnrRecyclerAdapter.QuestionViewHolder(view);
    }

}
