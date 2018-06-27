package it.polimi.two.weiava.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.text.DateFormat;
import java.util.Date;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.activities.AnsweredQnrActivity;
import it.polimi.two.weiava.activities.MainActivity;
import it.polimi.two.weiava.activities.ReportActivity;
import it.polimi.two.weiava.activities.TestsReportActivity;
import it.polimi.two.weiava.models.Schedule;

public class ScheduleRecyclerAdapter extends FirebaseRecyclerAdapter<Schedule, ScheduleRecyclerAdapter.ScheduleViewHolder> {

    private Context context;

    public class ScheduleViewHolder extends RecyclerView.ViewHolder{

        //TextView qType;
        TextView timestamp;
        TextView score;
        //TextView testID;
        Button previewBut;
        String mTestID;
        public ScheduleViewHolder(View v) {
            super(v);
            //qType = (TextView) itemView.findViewById(R.id.test_type_View);
            timestamp = (TextView) itemView.findViewById(R.id.test_date_View);
            score = (TextView)itemView.findViewById(R.id.test_score_View);
            //testID = (TextView)itemView.findViewById(R.id.test_id_View);
            previewBut = (Button)itemView.findViewById(R.id.button2);
            context = v.getContext();
            previewBut.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //Toast.makeText(v.getContext(), "item clicked"+testID.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,AnsweredQnrActivity.class);
                    String message = mTestID;
                    intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
                    context.startActivity(intent);
                }
            });
        }

    }

/*    public interface OnItemClickListener {
        void onItemClick(Schedule item);
    }*/

    public ScheduleRecyclerAdapter(FirebaseRecyclerOptions<Schedule> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(ScheduleViewHolder holder, int position, Schedule model) {
        if(model.getqType() != null) {
            Date resultdate = new Date(model.getTimestamp());
            //holder.qType.setText(model.getqType());
            holder.score.setText("Score: "+ model.getScore().toString());
            holder.timestamp.setText("Test Date: "+ DateFormat.getDateInstance().format(resultdate));
            //holder.testID.setText(model.getId());
            holder.mTestID = model.getId();
        }
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item, parent, false);
        return new ScheduleViewHolder(view);
    }

//    public interface OnItemClickListener {
//        void onItemClick(Schedule test);
//    }
//

}
