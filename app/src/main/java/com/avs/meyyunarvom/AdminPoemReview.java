package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class AdminPoemReview extends AppCompatActivity implements View.OnClickListener {


    private EditText poemTitle, poemContent;
    String poemTitleStr, poemContentStr;
    private TextView addedBy;
    private Button nextButton, prevButton, uploadButton, deleteButton;

    private ProgressBar progressBar1;
    private String loginUserEmail,loginUserName;

    private String userName,userEmail,userPlace, userDetailsText="Added By";
    String isPublish="";

    private final String GET_URL = com.avs.db.URL.url + "/getPoemAdmin.php";
    private final String UPLOAD_URL =com.avs.db.URL.url + "/setPoemByAdmin.php";
    private final String DELETE_PERMANENT_URL =com.avs.db.URL.url +"/deletePoemPermanantlyByAdmin.php";
    private final String DELETE_FROM_ADMIN_URL=com.avs.db.URL.url +"/deletePoemFromAdminPage.php";

    private String KEY_ID = "id";
    private String KEY_TITLE = "title";
    private String KEY_CONTENT = "content";

    private int poemId;

    private int TRACK = 0;
    private JSONObject jsonObject;
    private JSONArray result;

    int poemDataLength =0;

    private RelativeLayout actualLayout;
    private ImageView errorImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_poem_review);


        actualLayout = (RelativeLayout)findViewById(R.id.actualLayout_adminpoem);
        errorImage = (ImageView)findViewById(R.id.error_image_adminpoem);


        addedBy =(TextView)findViewById(R.id.addedBypoem);
        poemTitle = (EditText)findViewById(R.id.admin_title_add_poem);
        poemContent =(EditText)findViewById(R.id.admin_content_add_poem);
        deleteButton =(Button)findViewById(R.id.adminpoemdelete);
        uploadButton= (Button)findViewById(R.id.adminpoemupload);

        nextButton=(Button)findViewById(R.id.admin_buttonNext_forpoem);
        prevButton =(Button)findViewById(R.id.admin_buttonPrev_forpoem);

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar_adminpoem);
        progressBar1.setVisibility(View.VISIBLE);
        actualLayout.setVisibility(View.GONE);
        errorImage.setVisibility(View.GONE);


        if(checkConnection())
        getPoemsFromDB();


        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);


        errorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), AdminPoemReview.class);
                startActivity(intent);
                finish();
            }
        });



    }


    private boolean checkConnection()
    {
        Network network =new Network();
        if (!network.isOnline(AdminPoemReview.this))
        {
            errorImage.setVisibility(View.VISIBLE);
            progressBar1.setVisibility(View.GONE);
            return false;
        }
        else {
            return true;
        }
    }

    public void getPoemsFromDB() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


                        actualLayout.setVisibility(View.VISIBLE);
                        errorImage.setVisibility(View.GONE);

                        showJSON(response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar1.setVisibility(View.GONE);
                actualLayout.setVisibility(View.GONE);
                errorImage.setVisibility(View.VISIBLE);

                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();

                    }
                }
                else
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }


        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);


    }


    private void showJSON(String response)
    {
        try
        {
            jsonObject = new JSONObject(response);
            result=jsonObject.getJSONArray("result");

            poemDataLength = result.length();

            getPoemData();
        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }



    private void getPoemData()
    {

        try {

            JSONObject templeData = result.getJSONObject(TRACK);
            poemId = templeData.getInt("id");

            userName = templeData.getString("name");
            userEmail = templeData.getString("email");
            userPlace= templeData.getString("place");

            poemTitleStr =  templeData.getString("title");
            poemContentStr = templeData.getString("content");
            isPublish=templeData.getString("active_flag_publish");
            setTempleData();
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    private void setTempleData()
    {
        try {

            progressBar1.setVisibility(View.GONE);

            userDetailsText = userDetailsText +"     Name: " + userName+" \n";
            userDetailsText = userDetailsText +"     Place: " +userPlace+"\n";
            userDetailsText = userDetailsText +"     Mobile No: " +userEmail+"\n";

            if(isPublish.equals("Y"))
            {
                userDetailsText = userDetailsText +"     Added: Yes\n";
                addedBy.setText(userDetailsText);
                addedBy.setTextColor(getResources().getColor(R.color.green));

            }
            else if(isPublish.equals("N"))
            {
                userDetailsText = userDetailsText +"     Added: No\n";
                addedBy.setText(userDetailsText);
                addedBy.setTextColor(getResources().getColor(R.color.red));
            }

            poemTitle.setText(poemTitleStr);
            poemContent.setText(poemContentStr);


       }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error Occured While Loading the data", Toast.LENGTH_SHORT).show();
        }

    }


    boolean isCanceled =false;
    private void deletePermanently()
    {
        final ProgressDialog loading =new ProgressDialog(AdminPoemReview.this);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_PERMANENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        isCanceled =false;
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminPoemReview.this);
                        alertDialog.setTitle("நன்றி");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               Intent intent =new Intent(getApplicationContext(), AdminPoemReview.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                        alertDialog.show();

                        //   Toast.makeText(AddTemple.this, response.split(";")[0], Toast.LENGTH_SHORT).show();
                        //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        //startActivity(i);

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
            protected Map<String, String> getParams() throws AuthFailureError {
                //  String tempImage = getStringImage(bitmap);

                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_ID, String.valueOf(poemId));
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);


    }


    private void uploadUpdatePoem() {
        // final ProgressDialog loading = ProgressDialog.show(this, "Uploading", "Please Wait....", false, false);
        final ProgressDialog loading =new ProgressDialog(AdminPoemReview.this);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        isCanceled =false;
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminPoemReview.this);
                        alertDialog.setTitle("நன்றி");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent =new Intent(getApplicationContext(), AdminPoemReview.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        alertDialog.show();


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
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_ID, String.valueOf(poemId));
                params.put(KEY_TITLE, poemTitleStr);
                params.put(KEY_CONTENT, poemContentStr);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }



    private void deletePoemFromAdminPage() {

        final ProgressDialog loading =new ProgressDialog(AdminPoemReview.this);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_FROM_ADMIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        isCanceled =false;
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminPoemReview.this);
                        alertDialog.setTitle("நன்றி");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent =new Intent(getApplicationContext(), AdminPoemReview.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                        alertDialog.show();

                        //   Toast.makeText(AddTemple.this, response.split(";")[0], Toast.LENGTH_SHORT).show();
                        //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        //startActivity(i);

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
            protected Map<String, String> getParams() throws AuthFailureError {
                // String tempImage = getStringImage(bitmap);

                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_ID, String.valueOf(poemId));
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_temple_permanent_admin) {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminPoemReview.this);
            alertDialog.setTitle("நன்றி");
            alertDialog.setMessage("பதிவை நிரந்தரமாக நீக்க வேண்டுமா");
            alertDialog.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //answer.setText("");
                    deletePermanently();

                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });
            alertDialog.show();


            // return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view)
    {

        poemTitleStr=poemTitle.getText().toString();
        poemContentStr=poemContent.getText().toString();

        if(view == nextButton){

            moveNext();
        }
        if(view == prevButton){

            movePrevious();
        }


        if(view ==deleteButton)
        {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminPoemReview.this);
            alertDialog.setTitle("நன்றி");
            alertDialog.setMessage("பதிவை நீக்க வேண்டுமா");
            alertDialog.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //answer.setText("");
                    deletePoemFromAdminPage();
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


        if (view == uploadButton) {


            if (poemTitleStr.isEmpty()) {
                Snackbar.make(view, "Please add the Title for poem", Snackbar.LENGTH_SHORT)
                        .show();
                return;

            }

            if(!poemContentStr.isEmpty())
            {
                uploadUpdatePoem();
            }
            else {
                Snackbar.make(view, "Please Add the Poem", Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void resetFields()
    {
        addedBy.setText("");
        poemTitle.setText("");
        poemContent.setText("");
        userDetailsText="";

    }




    private void moveNext(){
        if(TRACK < poemDataLength -1 ){
            TRACK++;
            resetFields();
            getPoemData();
        }
    }

    private void movePrevious(){
        if(TRACK > 0){
            TRACK--;
            resetFields();
            getPoemData();
        }
    }

}
