package it.polimi.two.weiava.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.polimi.two.weiava.R;


public class QnrFragment extends Fragment {
    Button buttonGDS;
    Button buttonADL;
    Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_qnr, container, false);
        context = rootView.getContext();
//        Intent intent = new Intent(context,QnrActivity.class);
//        startActivity(intent);

        //setContentView(R.layout.activity_qnr);
        buttonGDS = rootView.findViewById(R.id.button_gds);
        buttonADL = rootView.findViewById(R.id.button_adl);
        buttonGDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,QuizeActivity.class);
                startActivity(intent);
            }
        });
        buttonADL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,QuizeADLActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}

