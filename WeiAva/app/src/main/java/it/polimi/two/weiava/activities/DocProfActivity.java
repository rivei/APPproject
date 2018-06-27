package it.polimi.two.weiava.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.SignUpActivity;

public class DocProfActivity extends Fragment {
//public class DocProfActivity extends AppCompatActivity {


    FloatingActionButton fab;
    Context context;

    @Override
    // protected void onCreate(Bundle savedInstanceState) {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_doc_prof);
        View rootView = inflater.inflate(R.layout.activity_doc_prof, container, false);
        context = rootView.getContext();
        //  fab = (FloatingActionButton) findViewById(R.id.fab);
        fab = rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(context, EmailActivity.class);
                startActivity(intent);

            }
        });

        final TextView tvNumber = rootView.findViewById(R.id.tvNumber2);
        tvNumber.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvNumber.getText()));
                startActivity(intent);
            }
        });


        return rootView;

    }
}

