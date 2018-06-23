package it.polimi.two.weiava.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.polimi.two.weiava.R;


public class QnrFragment extends Fragment {

    Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_qnr, container, false);
        context = rootView.getContext();
        /*Intent intent = new Intent(context,QnrActivity.class);
        startActivity(intent);*/
        Intent myIntent = new Intent(getActivity(), QnrActivity.class);
        getActivity().startActivity(myIntent);
        return rootView;

    }
    public Void onViewCreated()


}

