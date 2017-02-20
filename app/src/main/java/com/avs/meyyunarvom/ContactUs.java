package com.avs.meyyunarvom;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    private EditText subject, body;
    private Button send;


    String subjectStr = "";
    String bodyStr ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        subject = (EditText)findViewById(R.id.addSubject);
        body =(EditText)findViewById(R.id.addBody);
        send =(Button)findViewById(R.id.sendEmail);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 subjectStr = subject.getText().toString();
                 bodyStr = body.getText().toString();

                if(subjectStr.isEmpty())
                {
                    Snackbar.make(v, "Please Add Subject", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(bodyStr.isEmpty())
                {
                    Snackbar.make(v, "Please Write some text in Body", Snackbar.LENGTH_SHORT).show();
                }
                else
                   sendEmail();
            }
        });

    }


    protected void sendEmail() {
       // Log.i("Send email", "");

        String[] TO = {"avsinternationalgroup1008@gmail.com"};
        String[] CC = {"sathishkrish20@gmail.com, Alexkic037@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectStr);
        emailIntent.putExtra(Intent.EXTRA_TEXT, bodyStr);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            //Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactUs.this, "Please Install any Email application", Toast.LENGTH_SHORT).show();
        }
    }

}
