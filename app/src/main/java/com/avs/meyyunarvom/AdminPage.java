package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.ColorRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;


public class AdminPage extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener
{

    private JSONObject jsonObject;
    private JSONArray result;
    ArrayList quesarray=new ArrayList();

    private MaterialSpinner spin;
    private EditText answer;
    private TextView fullQuestion;
    private Button buttonSendAns, deleteBtn;
    ArrayAdapter questionAdapter;

    private String answerstr, loginUserNm,loginUsermail="";
    private String dbId;
    int restrictSendButton=0;

    private ProgressBar progressBar1;


    private static final String TAG_ID = "id";
    private static final String TAG_ANSWER = "answer";


    private final String GET_URL = com.avs.db.URL.url + "/getDoubtsAdmin.php";

    private final String SET_URL = com.avs.db.URL.url+"/setDoubtsByAdmin.php";

    private final String DELETE_URL = com.avs.db.URL.url+"/deleteDoubtByAdmin.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        checkConnection();
        checkIsLogin();
        spin = (MaterialSpinner) findViewById(R.id.spinneranswer);

        answer = (EditText) findViewById(R.id.answerfordoubt);
        fullQuestion =(TextView)findViewById(R.id.fulldoubt_text);


        progressBar1 = (ProgressBar) findViewById(R.id.progressBar_admindoubts);
        progressBar1.setVisibility(View.VISIBLE);

        buttonSendAns = (Button) findViewById(R.id.Sendanswer);
        deleteBtn= (Button)findViewById(R.id.delete_question_by_admin);
        spin.setOnItemSelectedListener(this);
        buttonSendAns.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        Intent intent=getIntent();


        getQustions();


    }


    private void checkConnection()
    {
        Network network =new Network();
        if (!network.isOnline(AdminPage.this))
        {
            Intent intent = new Intent(this,ConnectionError.class);
            startActivity(intent);
        }

    }

        private void checkIsLogin() {
        SharedPreferences userdetails = getApplicationContext().getSharedPreferences("Login", 0);
        SharedPreferences.Editor editor = userdetails.edit();

        loginUserNm = userdetails.getString("name", null);
        loginUsermail = userdetails.getString("email", null);

    }

    public void getQustions() {

        //addSpinSelectQuestion();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        showJSON(response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar1.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
            }


        });
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
        }catch (JSONException e){}

        questionAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, quesarray);
        questionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(questionAdapter);

        progressBar1.setVisibility(View.GONE);


    }
    private String getId(int position) {
        String doubtId = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            doubtId = json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return doubtId;
    }

    private String getQuestion(int position ) {
        String questionText = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            questionText = json.getString("question");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return questionText;
    }

    private String getUserDetails(int position ) {
        String userDetailsText = "";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

    //        isAnswered = json.getString("is_answered");
            //Fetching name from that object
            userDetailsText = "Asked By: \n";
            userDetailsText = userDetailsText +"     Name: " + json.getString("name")+" \n";
            userDetailsText = userDetailsText +"     Place: " +json.getString("place")+"\n";
            userDetailsText = userDetailsText +"     Mobile No: " +json.getString("email")+"\n";
            userDetailsText = userDetailsText +"     Asked_at: " +json.getString("created_at")+"\n";
            userDetailsText = userDetailsText +"     Answerd: " +json.getString("is_answered")+"\n";

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return userDetailsText;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
          if(position== -1)
          {
              restrictSendButton=1;
          }else
          {
              restrictSendButton=0;
          }

          dbId=getId(position);

        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

           String isAnswered = json.getString("is_answered");
           //Toast.makeText(this, isAnswered,Toast.LENGTH_LONG).show();
            if(isAnswered.equals("Y")) {
                fullQuestion.setText(getQuestion(position) + "\n" + getUserDetails(position));
                fullQuestion.setTextColor(getResources().getColor(R.color.green));
            }

         else if(isAnswered.equals("N"))
            {
                fullQuestion.setText(getQuestion(position) + "\n" + getUserDetails(position));
                fullQuestion.setTextColor(getResources().getColor(R.color.red));
            }


        }  catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {


    }

    private void uploadAnswer()
    {
       // final ProgressDialog loading=ProgressDialog.show(this,"Uploading","Please Wait....",false,false);

        final ProgressDialog loading =new ProgressDialog(AdminPage.this);
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


        StringRequest stringRequest=new StringRequest(Request.Method.POST, SET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        isCanceled = false;
                        loading.dismiss();

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminPage.this);
                        alertDialog.setTitle("Thank you");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed
                                answer.setText("");
                            }
                        });

                         alertDialog.show();
             //           Toast.makeText(AdminPage.this,response.split(";")[0],Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(AdminPage.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {

            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map <String,String>params=new Hashtable<String, String>();
                params.put("id",dbId);
                params.put("answer",answerstr);
                return params;
            }
        };

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    boolean isCanceled =false;
    private void deleteQuestion() {

        final ProgressDialog loading =new ProgressDialog(AdminPage.this);
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


//        final ProgressDialog loading=ProgressDialog.show(this,"Deleting","Please Wait....",false,false);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, DELETE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();

                        Toast.makeText(AdminPage.this,response.split(";")[0],Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(AdminPage.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {

            protected Map<String,String> getParams() throws AuthFailureError
            {

                Map <String,String>params=new Hashtable<String, String>();
                params.put("id",dbId);
                return params;
            }
        };

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);


    }


    @Override
    public void onClick(View v)
    {
        answerstr=answer.getText().toString().trim();


        if (loginUsermail==null)
        {
            // Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
            Snackbar.make(v, "You are not logged in. Please Login", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(restrictSendButton==1)
        {
            //  Toast.makeText(this,"Please Select one Question to Answer",Toast.LENGTH_SHORT).show();

            Snackbar.make(v, "Please Select a Question", Snackbar.LENGTH_SHORT).show();
            return;

        }

        if(v==buttonSendAns)
        {


            if (!answerstr.isEmpty()) {

                    uploadAnswer();
            }
            else
            {
                Snackbar.make(v, "Please fill the Question", Snackbar.LENGTH_SHORT).show();
            }
        }

        if (v == deleteBtn)
        {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminPage.this);
            alertDialog.setTitle("Thank you");
            alertDialog.setMessage("Are You Sure Want to Delete");
            alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //answer.setText("");
                    deleteQuestion();
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });
            alertDialog.show();



        }

    }




}
