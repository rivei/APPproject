package it.polimi.two.weiava.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import it.polimi.two.weiava.R;

public class EmailActivity extends AppCompatActivity {
    EditText subject;
    String subjecttext,bodytext;
    EditText body;
    Button buttonsend;
    /*NOT USING activity_measure.xml!!*/
String doc_Email="ava.ghafari72@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",doc_Email, null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Elders care");
        intent.putExtra(Intent.EXTRA_TEXT,"This is an Email from your patient");
        startActivity(Intent.createChooser(intent, "titlee"));
        /*setContentView(R.layout.activity_email);
        subject = findViewById(R.id.subject);
        body = findViewById(R.id.body);
        buttonsend = findViewById(R.id.button_send);
        subject.setEnabled(true);
        body.setEnabled(true);
        subject.setHint("Subject");
        subjecttext = subject.getText().toString();
        bodytext = body.getText().toString();*/

        /*buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {*/
        /*        try{
                    SmsManager smsManager =     SmsManager.getDefault();
                    smsManager.sendTextMessage("3312076376",null,subjecttext+bodytext,null,null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_LONG).show();
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later!",
                        Toast.LENGTH_LONG).show();
                    e.printStackTrace();
            }*/

              /*  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + 3312076376);
                intent.putExtra("sms_body", subjecttext+bodytext);
                startActivity(intent);*/


            }
        /*});


    }*/
}
