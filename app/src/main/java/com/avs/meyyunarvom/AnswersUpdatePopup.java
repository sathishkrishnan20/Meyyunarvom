package com.avs.meyyunarvom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.Hashtable;
import java.util.Map;

public class AnswersUpdatePopup extends Activity implements View.OnClickListener{

    private EditText questionChange;
    private Button changeQuestionBtn;

    private static final String TAG_ID ="id";
    private static final String TAG_QUESTION ="question";

    String updateQuestion= "";
    String  position = "0";


    private final String UPLOAD_URL =com.avs.db.URL.url +"/updateDoubtByUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers_update_popup);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        questionChange =(EditText)findViewById(R.id.qustiondoubt_change);
        changeQuestionBtn=(Button)findViewById(R.id.buttonchangequestion);

        changeQuestionBtn.setOnClickListener(this);

        int width= displayMetrics.widthPixels;
        int height =displayMetrics.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height* 0.6));


    }


    boolean isCanceled =false;

    private void uploadUpdatedQuestion()
    {
        //   final ProgressDialog loading=ProgressDialog.show(this,"Uploading","Please Wait....",false,false);
        final ProgressDialog loading =new ProgressDialog(AnswersUpdatePopup.this);
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

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnswersUpdatePopup.this);
                        alertDialog.setTitle("நன்றி");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed
                                if(response.split(";")[1].equals("success")) {
                                    questionChange.setText("");
                                    Intent intent = new Intent(getApplicationContext(), Answers.class);
                                    startActivity(intent);
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

                Map<String,String> params=new Hashtable<String, String>();
                params.put(TAG_ID, position);
                params.put(TAG_QUESTION,updateQuestion);

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
        if(v==changeQuestionBtn)
        {
            Intent intent = getIntent();
            position = intent.getStringExtra("Position");

            updateQuestion = questionChange.getText().toString();

            if (!updateQuestion.isEmpty()) {
               // Toast.makeText(this,"sssss"+position, Toast.LENGTH_LONG).show();

                uploadUpdatedQuestion();
            } else {
                Snackbar.make(v, "Please fill the Question", Snackbar.LENGTH_SHORT)
                        .show();
            }

        }
    }
}
