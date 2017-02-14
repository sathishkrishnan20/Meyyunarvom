package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.avs.db.URL;

import java.util.HashMap;
import java.util.Map;

public class UserUpdateDetails extends AppCompatActivity implements View.OnClickListener{

    private AutoCompleteTextView mEmailView;
    private EditText mUserNameView;
    private EditText mPlaceView;
    private EditText mPasswordView;
    Button update;

    private static String UPDATE_URL = URL.url+"/updateUserDetails.php";

    private static String oldEmail="oldemail";
    private static String dname="name";
    private static String demail="email";
    private static String dplace="place";
    private static String dpassword="password";

    private String name;
    private String email;
    private String place;
    private String password;
    private String oldUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_details);

        mUserNameView = (EditText) findViewById(R.id.name_user_edit);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_user_edit);
        mPasswordView = (EditText) findViewById(R.id.password_user_edit);
        mPlaceView =(EditText) findViewById(R.id.place_user_edit);
        update = (Button) findViewById(R.id.user_update_button);

        Intent intent =getIntent();
        name = intent.getStringExtra("userName");
        oldUserEmail =intent.getStringExtra("userEmail");
        place =intent.getStringExtra("userPlace");
        password =intent.getStringExtra("userPassword");
        update.setOnClickListener(this);

        mUserNameView.setText(name);
        mEmailView.setText(oldUserEmail);
        mPlaceView.setText(place);
        mPasswordView.setText(password);
    }

    @Override
    public void onClick(View v) {

        if(v==update) {

                name= mUserNameView.getText().toString().trim();
                email = mEmailView.getText().toString().trim();
                place = mPlaceView.getText().toString().trim();
                password = mPasswordView.getText().toString();

                Network network=new Network();
                if (!network.isOnline(UserUpdateDetails.this)) {
                    Toast.makeText(UserUpdateDetails.this, "No Network Connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6 || password.length() > 16)
                {
                    mPasswordView.setError("Password Length Should be 6 to 16 characters");
                    return;
                }

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !place.isEmpty()) {
                    registerUser();
                } else {
                    Snackbar.make(v, "Please enter the credentials!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }

        }


    int progressStatus=1;
    boolean isCanceled =false;

    public void registerUser()
    {
        //    final ProgressDialog loading = ProgressDialog.show(this,"Loading...","Please wait...",false,false);

        final ProgressDialog loading =new ProgressDialog(UserUpdateDetails.this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setTitle("Please Wait..");
        loading.setMessage("Loading.........");
        //pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
        loading.setIndeterminate(false);
        loading.setCancelable(false);

        loading.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
            // Set a click listener for progress dialog cancel button
            @Override
            public void onClick(DialogInterface dialog, int which){
                // dismiss the progress dialog
                progressStatus = 0;
                isCanceled = true;
                loading.dismiss();
                // Tell the system about cancellation

            }
        });

        loading.show();

        // Set the progress status zero on each button click
        if(isCanceled) {
            progressStatus = 1;
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        loading.dismiss();
                        Toast.makeText(UserUpdateDetails.this,response.split(";")[0],Toast.LENGTH_SHORT).show();
                        setDataIntoLocal();
                        resetField();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        loading.dismiss();
                        if (error.networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                // Show timeout error message
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserUpdateDetails.this);
                                alertDialog.setTitle("Oops!");
                                alertDialog.setMessage("Please Check Your Network Connection");

                                alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialog.show();

                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String,String> getParams()
            {
                Map <String,String> params=new HashMap<String,String>();
                params.put(dname,name);
                params.put(demail,email);
                params.put(dplace, place);
                params.put(dpassword,password);
                params.put(oldEmail,oldUserEmail);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    private void resetField()
    {
        mEmailView.setText("");
        mPasswordView.setText("");
        mPlaceView.setText("");
        mUserNameView.setText("");
    }

    private void setDataIntoLocal()
    {
        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
        SharedPreferences.Editor editor=userdetails.edit();

        editor.putString("name", name);
        editor.putString("email", email);
        editor.putBoolean("isLogin", true);
        editor.apply();
        editor.commit();

        Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);

    }



}
