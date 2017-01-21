package com.avs.meyyunarvom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    private EditText emailedt, passedt;
    private Button loginBtn;
    String emaiStr, passStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("LoginAdmin",0);
        SharedPreferences.Editor editor = userdetails.edit();

        if(userdetails.getBoolean("isLoginAdmin" ,false))
        {
            Intent intent =new Intent(this,AdminMenu.class);
            startActivity(intent);
        }


        emailedt=(EditText)findViewById(R.id.adminloginemailid);
        passedt =(EditText)findViewById(R.id.adminloginpasswordid);
        loginBtn =(Button)findViewById(R.id.admin_sign_in_buttonlogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                emaiStr= emailedt.getText().toString().trim();
                passStr= passedt.getText().toString().trim();

                if(emaiStr.equals("admin@avs.com") && passStr.equals("admin123"))
                {
                        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("LoginAdmin",0);
                        SharedPreferences.Editor editor=userdetails.edit();
                        editor.putBoolean("isLoginAdmin", true);
                        editor.apply();
                        editor.commit();

                        Intent intent =new Intent(getApplicationContext(),AdminMenu.class);
                        startActivity(intent);
                 }
                else {
                    Toast.makeText(getApplicationContext(),"provide Correct Details",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }





}
