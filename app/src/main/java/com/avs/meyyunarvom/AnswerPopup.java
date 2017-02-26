package com.avs.meyyunarvom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;


public class AnswerPopup extends Activity implements View.OnClickListener
{

    private TextView answerTitle , answerExplain;
    private ImageView edit, delete;

    private JSONObject jsonObject;
    private JSONArray result;
    String dbId ="0";

    private final String DELETE_URL =com.avs.db.URL.url +"/deleteDoubtByUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_answer);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Intent intent = getIntent();
        String responseData = intent.getStringExtra("Response");
        int position = intent.getIntExtra("Position",1);

        answerTitle =(TextView)findViewById(R.id.question_textview);
        answerExplain =(TextView)findViewById(R.id.answer_textview);
        edit=(ImageView)findViewById(R.id.doubt_update);
        delete=(ImageView)findViewById(R.id.doubt_delete);

        edit.setOnClickListener(this);
        delete.setOnClickListener(this);

        answerTitle.setText(getQuestion(position,responseData));
        answerExplain.setText(getAnswers(position,responseData));
        dbId =getId(position,responseData);

        int width= displayMetrics.widthPixels;
        int height =displayMetrics.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height* 0.6));

    }


    private String getId(int position ,String response) {
        String id = "";
        try {
            //Getting object of given index
            jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray("result");

            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            id = json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return id;
    }





    private String getQuestion(int position ,String response) {
        String quetionDbText = "";
        try {


            jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray("result");


            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            quetionDbText = json.getString("question");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return quetionDbText;
    }



    private String getAnswers(int position, String response) {
        String answerDbText = "";
        try {
            //Getting object of given index

            jsonObject = new JSONObject(response);
            result = jsonObject.getJSONArray("result");



            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            answerDbText = json.getString("answer");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return answerDbText;
    }


    boolean isCanceled =false;
    private void deleteQuestionByUser() {

        final ProgressDialog loading =new ProgressDialog(AnswerPopup.this);
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

        StringRequest stringRequest=new StringRequest(Request.Method.POST, DELETE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnswerPopup.this);
                        alertDialog.setTitle("நன்றி");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent =new Intent(getApplicationContext(), Answers.class);
                                startActivity(intent);
                                finish();
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
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
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


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);

    }

        @Override
    public void onClick(View v) {

        if (v == delete)
        {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnswerPopup.this);
            alertDialog.setTitle("நன்றி");
            alertDialog.setMessage("இந்த சந்தேகத்தை நீக்க வேண்டுமா");
            alertDialog.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //answer.setText("");
                    deleteQuestionByUser();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });
            alertDialog.show();

        }
         if(v == edit)
         {
            // Toast.makeText(this,dbId,Toast.LENGTH_LONG).show();
             Intent intent =new Intent(this,AnswersUpdatePopup.class);
             intent.putExtra("Position", String.valueOf(dbId));

             startActivity(intent);
         }




    }
}
