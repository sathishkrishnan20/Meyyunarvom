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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avs.db.Network;

import java.util.Hashtable;
import java.util.Map;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener
{

    private EditText emailedt, passedt;
    private Button loginBtn;
    String emaiStr, passStr;
    private final String AUTHENTICATEURL = com.avs.db.URL.url + "/adminLoginAuth.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        checkConnection();

        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("LoginAdmin",0);

        if(userdetails.getBoolean("isLoginAdmin" ,false))
        {
            Intent intent =new Intent(this,AdminMenu.class);
            startActivity(intent);
        }



        emailedt=(EditText)findViewById(R.id.adminloginemailid);
        passedt =(EditText)findViewById(R.id.adminloginpasswordid);
        loginBtn =(Button)findViewById(R.id.admin_sign_in_buttonlogin);

        loginBtn.setOnClickListener(this);

    }
    private void checkConnection() {
        Network network = new Network();
        if (!network.isOnline(AdminLogin.this)) {
            Intent intent = new Intent(AdminLogin.this, ConnectionError.class);
            startActivity(intent);
        }

    }
        boolean isCanceled= false;
    private void loginAuthenticate()
    {
        final ProgressDialog loading =new ProgressDialog(AdminLogin.this);
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
                isCanceled = true;
                loading.dismiss();
                // Tell the system about cancellation

            }
        });

        loading.show();

        if(isCanceled) {

            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AUTHENTICATEURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        isCanceled =false;

                        if (response.split(";")[0].equals("invalid")) {
                            Toast.makeText(getApplicationContext(),"Invalid Email or Password", Toast.LENGTH_SHORT).show();

                        } else if(response.split(";")[0].equals("success")){
                            putCredentialsToLocal();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(AdminLogin.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new Hashtable<String, String>();
                params.put("email", emaiStr);
                params.put("password", passStr);
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(stringRequest);
    }


    private void putCredentialsToLocal()
    {
        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("LoginAdmin",0);
        SharedPreferences.Editor editor=userdetails.edit();
        editor.putBoolean("isLoginAdmin", true);
        editor.apply();
        editor.commit();

        Intent intent =new Intent(getApplicationContext(),AdminMenu.class);
        startActivity(intent);

    }



@Override
    public void onClick(View view)
        {

        emaiStr=emailedt.getText().toString().trim();
        passStr=passedt.getText().toString().trim();


        if(emaiStr.isEmpty()){
        Snackbar.make(view,"Please Enter Email",Snackbar.LENGTH_SHORT)
        .show();
        return;

        }

        if(!passStr.isEmpty())
        {
        loginAuthenticate();
        }
        else{
        Snackbar.make(view,"Please Enter Password",Snackbar.LENGTH_SHORT)
        .show();
        }
     }







}
