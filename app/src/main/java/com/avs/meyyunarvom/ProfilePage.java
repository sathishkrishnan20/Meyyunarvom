package com.avs.meyyunarvom;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avs.db.Network;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage extends AppCompatActivity {


    String loginUserName, loginUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        TextView userProfile = (TextView) findViewById(R.id.user_profile_name);
        TextView userPhNo = (TextView) findViewById(R.id.user_profile_ph_no);
        TextView userDoubts = (TextView) findViewById(R.id.user_add_doubt);
        TextView userTemple = (TextView) findViewById(R.id.user_add_temple);
        TextView userPoem = (TextView) findViewById(R.id.user_add_poem);
        ImageView userEdit = (ImageView) findViewById(R.id.user_edit);
        TextView logOut = (TextView) findViewById(R.id.user_logout);

       final ProgressBar pg = (ProgressBar)findViewById(R.id.progPro);
       pg.setVisibility(View.GONE);

        SharedPreferences userdetails = getApplicationContext().getSharedPreferences("Login", 0);

        if (!userdetails.getBoolean("isLogin", false)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        loginUserName=userdetails.getString("name", null);
        loginUserEmail=userdetails.getString("email",null);

        userProfile.setText(loginUserName);
        userPhNo.setText(loginUserEmail);



        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pg.setVisibility(View.VISIBLE);
                CountDownTimer countDownTimerStatic = new CountDownTimer(1000, 16) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {

                        SharedPreferences userdetails = getApplicationContext().getSharedPreferences("Login", 0);
                        SharedPreferences.Editor editor = userdetails.edit();
                        if (userdetails.contains("name") && userdetails.contains("email") && userdetails.contains("isLogin")) {
                            editor.remove("name");
                            editor.remove("email");
                            editor.remove("isLogin");
                            editor.apply();
                            boolean commit = editor.commit();
                            pg.setVisibility(View.GONE);
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Logout Succesfully", Toast.LENGTH_SHORT).show();

                        } else {
                            pg.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Your are already Logged out", Toast.LENGTH_SHORT).show();
                        }



                    }
                };
                countDownTimerStatic.start();







            }
        });


        userDoubts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Answers.class);
                startActivity(intent);

            }
        });

        userPoem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), UserViewPoem.class);
                startActivity(intent);

            }
        });




        userTemple.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(getApplicationContext(), UserTemple.class);
                startActivity(intent);
            }
        });



        userEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(), UserUpdateDetails.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
