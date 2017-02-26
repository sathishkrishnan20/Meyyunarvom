package com.avs.meyyunarvom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AdminMenu extends AppCompatActivity {

    private TextView doubt, temple, poem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);



        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("LoginAdmin",0);
        if(!userdetails.getBoolean("isLoginAdmin" ,false))
        {
            Toast.makeText(this,"Please login and Contiue",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(this,AdminLogin.class);
            startActivity(intent);
        }

        doubt= (TextView)findViewById(R.id.admin_menu_doubt);
        temple= (TextView)findViewById(R.id.admin_menu_temple);
        poem= (TextView)findViewById(R.id.admin_menu_poem);

        doubt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), AdminPage.class);
                startActivity(intent);
            }

        });

        temple.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), AdminTempleReview.class);
                startActivity(intent);
            }

        });

        poem.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), AdminPoemReview.class);
                startActivity(intent);
            }

        });


    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

}
