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
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avs.db.Network;

import java.util.Hashtable;
import java.util.Map;

public class AddPoem extends AppCompatActivity implements View.OnClickListener {

    private EditText poemTitle, poemContent;
    private String poemTitleStr, poemContentStr;
    private Button send;

    private String loginUserEmail,loginUserName;

    private String KEY_EMAIL = "email";
    private String KEY_TITLE = "title";
    private String KEY_CONTENT = "content";

    private String UPLOAD_URL = com.avs.db.URL.url +"/setPoemByUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poem);


        checkConnection();
        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);

        if(!userdetails.getBoolean("isLogin" ,false))
        {
            Toast.makeText(this,"Please login and Contiue",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        loginUserName=userdetails.getString("name", null);
        loginUserEmail=userdetails.getString("email",null);



        poemTitle =(EditText)findViewById(R.id.title_add_poem);
        poemContent= (EditText)findViewById(R.id.content_add_poem);
        send =(Button)findViewById(R.id.buttonSendpoem);
        send.setOnClickListener(this);

    }


    private void checkConnection()
    {
        Network network=new Network();
        if (!network.isOnline(AddPoem.this))
        {
            Intent intent = new Intent(AddPoem.this,ConnectionError.class);
            startActivity(intent);
        }

    }


    boolean isCanceled =false;
    private void uploadPoem()
    {
        final ProgressDialog loading =new ProgressDialog(AddPoem.this);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setTitle("Please Wait..");
        loading.setMessage("Loading.........");
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
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        loading.dismiss();

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddPoem.this);
                        alertDialog.setTitle("நன்றி");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed
                                if(response.split(";")[1].equals("success")) {
                                    poemTitle.setText("");
                                    poemContent.setText("");
                                    finish();
                                }
                                else
                                    Toast.makeText(AddPoem.this, "oops! Please try again", Toast.LENGTH_LONG).show();
                            }
                        });

                        alertDialog.show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();

                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

                    }
                }
                else
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();



            }
        })
        {

            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String> params=new Hashtable<String, String>();
                params.put(KEY_EMAIL,loginUserEmail);
                params.put(KEY_TITLE,poemTitleStr);
                params.put(KEY_CONTENT,poemContentStr);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    @Override
    public void onClick(View v) {


        poemTitleStr = poemTitle.getText().toString().trim();
        poemContentStr = poemContent.getText().toString();

        if (v == send) {

            if (loginUserEmail == null) {
                //Toast.makeText(this,"You are Not logged in. Please Login",Toast.LENGTH_SHORT).show();
                //return;
                Snackbar.make(v, "You are Not logged in. Please Login", Snackbar.LENGTH_SHORT)
                        .show();
                return;
            }

            if (poemTitleStr.isEmpty()) {
                Snackbar.make(v, "தலைப்பை உள்ளிடுக", Snackbar.LENGTH_SHORT)
                        .show();
                return;

            }

            if(!poemContentStr.isEmpty())
            {
                uploadPoem();
            }
            else {
                Snackbar.make(v, "பதிவை உள்ளிடுக", Snackbar.LENGTH_SHORT)
                        .show();

            }
        }

    }
}
