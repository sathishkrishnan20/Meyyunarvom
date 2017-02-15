package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
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
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avs.db.Network;
import com.avs.db.URL;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener
{

    private EditText curName;
    private EditText curEmail;
    private EditText newPass;
    private Button updatebtn;

    private static final String dname="name";
    private static final String demail="email";
    private static final String dpassword="password";
    private static final String loginUrl= URL.url+"/updatepw.php";

    String curentName,currentEmail,newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        curName=(EditText)findViewById(R.id.nameforpass);
        curEmail=(EditText)findViewById(R.id.emailforpass);
        newPass=(EditText)findViewById(R.id.newpassforpw);
        updatebtn=(Button)findViewById(R.id.updatebtnforpw);

        updatebtn.setOnClickListener(this);


    }

    public void onClick(View v)
    {
        if(v==updatebtn)
        {
            curentName = curName.getText().toString().trim();
            currentEmail=curEmail.getText().toString().trim();
            newPassword = newPass.getText().toString();

            Network network =new Network();
            if (!network.isOnline(ForgotPassword.this)) {
                Toast.makeText(ForgotPassword.this, "No Network Connection", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!curentName.isEmpty() && !currentEmail.isEmpty()&& !newPassword.isEmpty()) {
                updatePassword();
            } else {
                Snackbar.make(v, "Please enter the credentials!", Snackbar.LENGTH_SHORT)
                        .show();
            }



        }

    }

    boolean isCanceled =false;
public void updatePassword()
{
//    final ProgressDialog loading = ProgressDialog.show(this,"Loading...","Please wait...",false,false);

    final ProgressDialog loading =new ProgressDialog(ForgotPassword.this);
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


    StringRequest stringRequest=new StringRequest(Request.Method.POST, loginUrl,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    loading.dismiss();
                        Toast.makeText(ForgotPassword.this,response.split(";")[0],Toast.LENGTH_SHORT).show();
                        resetField();

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    loading.dismiss(); if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

                    }
                }
                else
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                }
            })
    {
        @Override
        protected Map<String,String> getParams() throws AuthFailureError
        {
            Map <String,String> params=new HashMap<String,String>();
            params.put(dname,curentName);
            params.put(demail,currentEmail);
            params.put(dpassword,newPassword);
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
        curEmail.setText("");
        curName.setText("");
        newPass.setText("");
    }





}
