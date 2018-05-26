package it.polimi.two.weiava.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.polimi.two.weiava.R;


public class MsrFragment extends Fragment {

    Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_measure, container, false);
        context = rootView.getContext();
        Intent intent = new Intent(context,MeasureActivity.class);
        startActivity(intent);
        return rootView;

    }

}


