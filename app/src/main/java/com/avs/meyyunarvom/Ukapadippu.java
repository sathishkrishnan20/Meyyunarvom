package com.avs.meyyunarvom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Ukapadippu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ukapadippu);
    }


    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }
}
