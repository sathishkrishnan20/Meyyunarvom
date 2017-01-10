package com.avs.meyyunarvom;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

    }

    @Override
    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }
}
