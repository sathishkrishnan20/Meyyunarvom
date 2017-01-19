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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_poem_review);


        checkConnection();
        userCheck();

        addedBy =(TextView)findViewById(R.id.addedBypoem);
        poemTitle = (EditText)findViewById(R.id.admin_title_add_poem);
        poemContent =(EditText)findViewById(R.id.admin_content_add_poem);
        deleteButton =(Button)findViewById(R.id.adminpoemdelete);
        uploadButton= (Button)findViewById(R.id.adminpoemupload);

        nextButton=(Button)findViewById(R.id.admin_buttonNext_forpoem);
        prevButton =(Button)findViewById(R.id.admin_buttonPrev_forpoem);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);

        getPoemsFromDB();


    }


    private void checkConnection()
    {
        Network network=new Network();
        if (!network.isOnline(AdminPoemReview.this))
        {
            Intent intent = new Intent(AdminPoemReview.this,ConnectionError.class);
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


    public void getPoemsFromDB() {
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
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
            }


        });
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
                        alertDialog.setTitle("Thank you");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed

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
                        Toast.makeText(AdminPoemReview.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                //  String tempImage = getStringImage(bitmap);

                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_ID, String.valueOf(poemId));
                return params;
            }
        };

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
                        alertDialog.setTitle("Thank you");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed

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
                        Toast.makeText(AdminPoemReview.this, error.toString()+"ssss "+poemId, Toast.LENGTH_LONG).show();
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
                        alertDialog.setTitle("Thank you");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog closed

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
                        Toast.makeText(AdminPoemReview.this, error.toString()+poemId, Toast.LENGTH_LONG).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                // String tempImage = getStringImage(bitmap);

                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_ID, String.valueOf(poemId));
                return params;
            }
        };

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
        if (id == R.id.action_delete_temple_from_admin) {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminPoemReview.this);
            alertDialog.setTitle("Thank you");
            alertDialog.setMessage("Are You Sure Want to Delete from here");
            alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //answer.setText("");
                    deletePoemFromAdminPage();
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
            resetFields();
            moveNext();
        }
        if(view == prevButton){
            resetFields();
            movePrevious();
        }


        if(view ==deleteButton)
        {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminPoemReview.this);
            alertDialog.setTitle("Thank you");
            alertDialog.setMessage("Are You Sure Want to Delete Poem. You cannot Recover");
            alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //answer.setText("");
                    deletePermanently();
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


        if (view == uploadButton) {

            if (loginUserEmail == null) {
                //Toast.makeText(this,"You are Not logged in. Please Login",Toast.LENGTH_SHORT).show();
                //return;
                Snackbar.make(view, "You are Not logged in. Please Login", Snackbar.LENGTH_SHORT)
                        .show();
                return;
            }

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
        poemTitle.setText("");
        poemContent.setText("");
        userDetailsText="";

    }




    private void moveNext(){
        if(TRACK < poemDataLength){
            TRACK++;
            getPoemData();
        }
    }

    private void movePrevious(){
        if(TRACK>0){
            TRACK--;
            getPoemData();
        }
    }

}
