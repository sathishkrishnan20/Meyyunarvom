package com.avs.meyyunarvom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avs.db.Network;
import com.avs.db.URL;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener
{


    private AutoCompleteTextView mEmailView;
    private EditText mUserNameView;
    private EditText mPlaceView;
    private EditText mPasswordView;
    Button register;

    private static String regUrl = URL.url+"/signup.php";

    private static String dname="name";
    private static String demail="email";
    private static String dplace="place";
    private static String dpassword="password";



    private String name;
    private String email;
    private String place;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mUserNameView = (EditText) findViewById(R.id.name);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPlaceView =(EditText) findViewById(R.id.place);
        register = (Button) findViewById(R.id.email_sign_up_button);


        register.setOnClickListener(this);
    }
                public void onClick(View v)
                {

                    if(v.getId()==R.id.email_sign_up_button) {

                     name= mUserNameView.getText().toString().trim();
                     email = mEmailView.getText().toString().trim();
                     place = mPlaceView.getText().toString().trim();
                     password = mPasswordView.getText().toString();

                    AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
                    mAwesomeValidation.addValidation(SignUpActivity.this, R.id.name, "[a-zA-Z ]+", R.string.err_name);


                     Network network=new Network();
                     if (!network.isOnline(SignUpActivity.this)) {
                         Toast.makeText(SignUpActivity.this, "No Network Connection", Toast.LENGTH_SHORT).show();
                         return;
                     }


                     if(password.length() < 6 || password.length() > 16)
                     {
                         mPasswordView.setError("Password Length Should be 6 to 16 characters");
                         return;
                     }

                     if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !place.isEmpty() && mAwesomeValidation.validate()) {

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

        final ProgressDialog loading =new ProgressDialog(SignUpActivity.this);
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
        StringRequest stringRequest=new StringRequest(Request.Method.POST, regUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        loading.dismiss();
                        Toast.makeText(SignUpActivity.this,response.split(";")[0],Toast.LENGTH_SHORT).show();
                        resetField();
                        Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        loading.dismiss();
                        Toast.makeText(SignUpActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                        resetField();

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


        }


