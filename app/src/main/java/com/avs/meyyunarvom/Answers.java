package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avs.db.Network;
import com.avs.db.URL;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Answers extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener
{


    private JSONObject jsonObject;
    private JSONArray result;
    private fr.ganfra.materialspinner.MaterialSpinner spin;

   // MaterialSpinner spin;
    ArrayList quesarray=new ArrayList();
    private static String URL = com.avs.db.URL.url + "/getDoubtsByUser.php";

    private String loginUserEmail ,loginUserName;
    boolean isLogin;


////Doubts
    private EditText question;
    SQLiteDatabase db;
    private String loginuser;
    private String userquestion;

    private Button buttonSend;


    private String UPLOAD_URL = com.avs.db.URL.url +"/setDoubtsByUser.php";

    ArrayAdapter questionArray;
    //Tags used in the JSON String
    public static final String TAG_EMAIL = "email";
    public static final String TAG_QUESTION = "question";
    public static final String TAG_ANSWER = "answer";

    NiftyDialogBuilder materialDesignAnimatedDialog;


    String responseData="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        checkConnection();
        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
        SharedPreferences.Editor editor = userdetails.edit();

        if(!userdetails.getBoolean("isLogin" ,false))
        {
            Toast.makeText(this,"Please login and Contiue",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        checkIsLogin();


        //spin = (Spinner) findViewById(R.id.spinnerdoubt);
        spin =(fr.ganfra.materialspinner.MaterialSpinner)findViewById(R.id.spinnerdoubt);
        question = (EditText) findViewById(R.id.qustiondoubt);
        buttonSend = (Button) findViewById(R.id.buttonSendquestion);

        spin.setOnItemSelectedListener(this);
        materialDesignAnimatedDialog = NiftyDialogBuilder.getInstance(this);
        getQustions();
        buttonSend.setOnClickListener(this);


  }


    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }



    private void checkConnection()
    {
        Network network =new Network();
        if (!network.isOnline(Answers.this))
        {
            Intent intent = new Intent(Answers.this,ConnectionError.class);
            startActivity(intent);
        }

    }

    public void getQustions()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        responseData =response;
                        showJSON(response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", loginUserEmail);

                return params;


            }


        };

        //Creating a request queue
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);




    }

    private void showJSON(String response)
    {
        try {

            jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray("result");



            for (int i = 0; i < result.length(); i++) {
                try {
                    //Getting json object
                    JSONObject json = result.getJSONObject(i);
                    //questions.add(json.getString("question"));
                    quesarray.add(json.getString("question"));
                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }
        catch (JSONException e){}



        questionArray = new ArrayAdapter(this, android.R.layout.simple_spinner_item, quesarray);
        questionArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(questionArray);
      //  spin.setItems(quesarray);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, final int position, long l)
    {
        if (position == -1)
        {


        }
        else {

              Intent intent = new Intent(this ,AnswerPopup.class);
                intent.putExtra("Response", responseData);
                intent.putExtra("Position",position);
                startActivity(intent);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    boolean isCanceled =false;

    private void uploadQuestion()
    {
     //   final ProgressDialog loading=ProgressDialog.show(this,"Uploading","Please Wait....",false,false);
        final ProgressDialog loading =new ProgressDialog(Answers.this);
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
                    public void onResponse(String response) {
                        loading.dismiss();

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Answers.this);
                        alertDialog.setTitle("Thank you");
                        alertDialog.setMessage(response.split(";")[0]);

                       alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               // Write your code here to execute after dialog closed
                               question.setText("");
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
                Toast.makeText(Answers.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {

            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map<String,String> params=new Hashtable<String, String>();
                params.put(TAG_EMAIL,loginUserEmail);
                params.put(TAG_QUESTION,userquestion);
                params.put(TAG_ANSWER,"Your Question was not Answered yet, We Will Answer Soon");
                return params;
            }
        };

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }



    private void checkIsLogin()
    {
        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
        SharedPreferences.Editor editor = userdetails.edit();

        loginUserName=userdetails.getString("name", null);
        loginUserEmail=userdetails.getString("email",null);

    }

    @Override
    public void onClick(View v) {


        userquestion = question.getText().toString().trim();

        if (v == buttonSend) {

            if (loginUserEmail == null) {
                //Toast.makeText(this,"You are Not logged in. Please Login",Toast.LENGTH_SHORT).show();
                //return;
                Snackbar.make(v, "You are Not logged in. Please Login", Snackbar.LENGTH_SHORT)
                        .show();
                return;
            }

            if (!userquestion.isEmpty()) {

                uploadQuestion();
            } else {
                Snackbar.make(v, "Please fill the Question", Snackbar.LENGTH_SHORT)
                        .show();
            }
        }

    }

}

