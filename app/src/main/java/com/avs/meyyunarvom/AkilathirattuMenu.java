package com.avs.meyyunarvom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AkilathirattuMenu extends AppCompatActivity {

    private TextView akilathirattu, arulnool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akilathirattu_menu);

        akilathirattu= (TextView)findViewById(R.id.akil_menu_akilathirattu);
        arulnool= (TextView)findViewById(R.id.akil_menu_arulnool);

        akilathirattu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), Akilam.class);
                startActivity(intent);
            }

        });

        arulnool.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), ArulnoolMenu.class);
                startActivity(intent);
            }

        });




    }
}
