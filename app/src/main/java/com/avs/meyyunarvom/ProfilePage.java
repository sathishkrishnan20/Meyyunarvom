package com.avs.meyyunarvom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
        SharedPreferences.Editor editor = userdetails.edit();

        if(!userdetails.getBoolean("isLogin" ,false))
        {
            Intent intent =new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
