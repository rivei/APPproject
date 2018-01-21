package it.polimi.two.weiava;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ava Ghafari on 1/21/2018.
 */

class qnrAdapter extends RecyclerView.Adapter<qnrAdapter.ViewHolder> {
    private String[] mDataset;
    List<Reminder> remember;

    public qnrAdapter(List<Reminder> remember){
        this.remember=remember;
    }


    @Override
    public qnrAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(qnrAdapter.ViewHolder holder, int position) {
        holder.reminderName.setText(remember.get(position).getTest_Name());
        holder.reminderDate.setText(remember.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return remember.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView reminderName;
        public TextView reminderDate;

        public ViewHolder(View itemView) {
            super(itemView);
            reminderName = itemView.findViewById(R.id.reminder_name);
            reminderDate = itemView.findViewById(R.id.reminder_date);
        }
    }
}