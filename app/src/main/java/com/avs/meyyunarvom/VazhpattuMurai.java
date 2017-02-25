package com.avs.meyyunarvom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.avs.db.ChattuDAO;
import com.avs.db.MeyyunarvomDB;
import com.avs.db.VazhipattuMuraiDAO;

public class VazhpattuMurai extends AppCompatActivity {

    private TextView title, content;
    private Button next, previous;

    private int track =1;


    ScrollView scrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vazhpattu_murai);

        scrollView =(ScrollView)findViewById(R.id.scrollView_vazhi);


        title = (TextView) findViewById(R.id.vazhi_titleid1);
        content = (TextView) findViewById(R.id.vazhi_contentid1);
        next = (Button) findViewById(R.id.vazhinextbtn);
        previous = (Button)findViewById(R.id.vazhipreviousbtn);

        try {
            MeyyunarvomDB db = new MeyyunarvomDB(getApplicationContext());
            VazhipattuMuraiDAO dao = db.getVazhipattuMuraiContents(track);
            title.setText(dao.getTitle());
            content.setText(dao.getContent());


            next.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    if (track < 22) {
                        track = track + 1;
                    }
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                    MeyyunarvomDB db = new MeyyunarvomDB(getApplicationContext());
                    VazhipattuMuraiDAO dao = db.getVazhipattuMuraiContents(track);
                    title.setText(dao.getTitle());
                    content.setText(dao.getContent());

                }

            });

            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (track > 1) {
                        track = track - 1;
                    }
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                    MeyyunarvomDB db = new MeyyunarvomDB(getApplicationContext());
                    VazhipattuMuraiDAO dao = db.getVazhipattuMuraiContents(track);
                    title.setText(dao.getTitle());
                    content.setText(dao.getContent());

                }
            });


        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }





    }
}
