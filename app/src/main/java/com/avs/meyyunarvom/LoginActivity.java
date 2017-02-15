package com.avs.meyyunarvom;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {


        private AutoCompleteTextView mEmailView;
        private EditText mPasswordView;
        private Button signIn, signUp;
        private TextView forgotpw;

        private static final String loginUrl= URL.url+"/signin.php";
        private static final String demail="email";
        private static final String dpassword="password";


        private String email;
        private String password;

        private String sqname;
        private String sqemail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
        if(userdetails.getBoolean("isLogin" ,false))
        {
            Intent intent= new Intent(this,ProfilePage.class);
            startActivity(intent);
        }
        mEmailView=(AutoCompleteTextView)findViewById(R.id.loginemailid);
        mPasswordView=(EditText)findViewById(R.id.loginpasswordid);

        signIn = (Button) findViewById(R.id.email_sign_in_buttonlogin);
        signUp=(Button)findViewById(R.id.signuplogin);
        forgotpw=(TextView) findViewById(R.id.forgotpwsignin);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        forgotpw.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        //
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void onClick(View view)
    {
        if(view==signUp)
        {
            Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
            startActivity(intent);

        }
        if(view==forgotpw)
        {
            Intent intent=new Intent(getApplicationContext(),ForgotPassword.class);
            startActivity(intent);

        }


        if(view==signIn)
        {
            email = mEmailView.getText().toString().trim();
            password = mPasswordView.getText().toString();

            Network network =new Network();
            if (!network.isOnline(LoginActivity.this)) {
                Toast.makeText(LoginActivity.this, "No Network Connection", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.isEmpty() && !password.isEmpty()) {
                loginUser();
            } else {
                Snackbar.make(view, "Please enter the credentials!", Snackbar.LENGTH_SHORT)
                        .show();
            }

        }
    }
    int progressStatus=1;
    boolean isCanceled =false;
    private void loginUser()
    {
       final ProgressDialog loading =new ProgressDialog(LoginActivity.this);
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
                loading.dismiss();
                // Tell the system about cancellation
                isCanceled = true;
            }
        });

        loading.show();

        // Set the progress status zero on each button click
        progressStatus = 0;

        //ProgressDialog.show(this,"Loading...","Please wait...",false,false);

        if(!isCanceled) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            if (response.split(";")[0].equals("invalid")) {
                                Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();

                            } else {
                                showJSON(response);


                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            if (error.networkResponse == null) {
                                if (error.getClass().equals(TimeoutError.class)) {
                                    // Show timeout error message
                                    Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(demail, email);
                    params.put(dpassword, password);
                    return params;

                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue rq = Volley.newRequestQueue(this);
            rq.add(stringRequest);
        } //End of If
    }

    private void showJSON(String response)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result=jsonObject.getJSONArray("result");
            JSONObject userData=result.getJSONObject(0);
            sqname=userData.getString("name");
            sqemail=userData.getString("email");


            setDataIntoLocal();

        }

        catch (Exception e) {

            e.printStackTrace();
        }
        //bitmaImage=downloadImage(timage);
    }

    private void setDataIntoLocal()
    {
        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
        SharedPreferences.Editor editor=userdetails.edit();

            editor.putString("name", sqname);
            editor.putString("email", sqemail);
            editor.putBoolean("isLogin", true);
            editor.apply();
            editor.commit();

            openProfile();
    }

    private void openProfile()
    {

        Intent i=new Intent(this,ProfilePage.class);
        startActivity(i);
        finish();

    }



}
