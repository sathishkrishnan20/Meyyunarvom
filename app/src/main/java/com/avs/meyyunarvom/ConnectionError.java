package com.avs.meyyunarvom;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.avs.db.Network;

public class ConnectionError extends AppCompatActivity implements View.OnClickListener {


    private ImageView errorConnection;
    Network network = new Network();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_error);


        errorConnection = (ImageView) findViewById(R.id.connectErrorImageView);
        errorConnection.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == errorConnection) {
            if (network.isOnline(ConnectionError.this)) {
                finish();
            }

        }
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        if (network.isOnline(ConnectionError.this)) {
            finish();
        }
        else {
            Intent intent =new Intent(ConnectionError.this,MainActivity.class);
            startActivity(intent);
        }
    }



}