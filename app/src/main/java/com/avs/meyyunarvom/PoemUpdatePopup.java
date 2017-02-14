package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

public class PoemUpdatePopup extends AppCompatActivity implements View.OnClickListener{

    private EditText poemTitle, poemContent;
    private String poemTitleStr, poemContentStr;
    private Button send;

    private String loginUserEmail,loginUserName;

    private String KEY_ID = "id";
    private String KEY_TITLE = "title";
    private String KEY_CONTENT = "content";

    private String UPLOAD_URL = com.avs.db.URL.url +"/updatePoemByUser.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_update_popup);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        checkConnection();
        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
        SharedPreferences.Editor editor = userdetails.edit();

        if(!userdetails.getBoolean("isLogin" ,false))
        {
            Toast.makeText(this,"Please login and Contiue",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        userCheck();



        Intent intent = getIntent();
       // position = intent.getStringExtra("Position");
        poemTitleStr = intent.getStringExtra("poemTitle");
        poemContentStr= intent.getStringExtra("poemContent");



        poemTitle =(EditText)findViewById(R.id.title_update_poem);
        poemContent= (EditText)findViewById(R.id.content_update_poem);
        send =(Button)findViewById(R.id.buttonUpdateSendpoem);
        send.setOnClickListener(this);

       poemTitle.setText(poemTitleStr);
       poemContent.setText(poemContentStr);

        int width= displayMetrics.widthPixels;
        int height =displayMetrics.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height* 0.6));


    }

    private void checkConnection()
    {
        Network network=new Network();
        if (!network.isOnline(PoemUpdatePopup.this))
        {
            Intent intent = new Intent(PoemUpdatePopup.this,ConnectionError.class);
            startActivity(intent);
        }

    }

    private void userCheck()
    {


        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
        SharedPreferences.Editor editor=userdetails.edit();

        loginUserName=userdetails.getString("name", null);
        loginUserEmail=userdetails.getString("email",null);
    }


    boolean isCanceled =false;

    private void uploadPoem()
    {
        //   final ProgressDialog loading=ProgressDialog.show(this,"Uploading","Please Wait....",false,false);
        final ProgressDialog loading =new ProgressDialog(PoemUpdatePopup.this);
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
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        loading.dismiss();

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PoemUpdatePopup.this);
                        alertDialog.setTitle("Thank you");

                        alertDialog.setMessage("Your Poem is Successfully Changed, We Will review and add Soon");

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed
                                if(response.split(";")[0].equals("success")) {
                                    poemTitle.setText("");
                                    poemContent.setText("");
                                    finish();
                                }
                            }
                        });

                        alertDialog.show();
                        //Toast.makeText(Answers.this, response.split(";")[0], Toast.LENGTH_LONG).show();
                        //Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        // startActivity(i);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PoemUpdatePopup.this);
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

            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String> params=new Hashtable<String, String>();
                params.put(KEY_ID,position);
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


    String position ="";
    @Override
    public void onClick(View v) {


        poemTitleStr = poemTitle.getText().toString().trim();
        poemContentStr = poemContent.getText().toString();

        if (v == send) {

            Intent intent = getIntent();
            position = intent.getStringExtra("Position");

            if (loginUserEmail == null) {
                //Toast.makeText(this,"You are Not logged in. Please Login",Toast.LENGTH_SHORT).show();
                //return;
                Snackbar.make(v, "You are Not logged in. Please Login", Snackbar.LENGTH_SHORT)
                        .show();
                return;
            }

            if (poemTitleStr.isEmpty()) {
                Snackbar.make(v, "Please add the Title for poem", Snackbar.LENGTH_SHORT)
                        .show();
                return;

            }

            if(!poemContentStr.isEmpty())
            {   Toast.makeText(this,String.valueOf(position),Toast.LENGTH_SHORT).show();
                uploadPoem();
            }
            else {
                Snackbar.make(v, "Please Add the Poem", Snackbar.LENGTH_SHORT)
                        .show();

            }
        }

    }

}
