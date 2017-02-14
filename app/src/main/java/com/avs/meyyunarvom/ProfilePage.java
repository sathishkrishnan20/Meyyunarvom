package com.avs.meyyunarvom;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avs.db.Network;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilePage extends AppCompatActivity {

    private TextView userProfile, userPhNo, userDoubts, userTemple, userPoem, logOut;
    private ImageView userEdit;
    String loginUserName, loginUserEmail;

    private JSONObject jsonObject;
    private JSONArray result;
    int userData;

    private ProgressBar progressBar1;
    private final String GET_URL = com.avs.db.URL.url + "/getUserDetaills.php";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        checkConnection();
        checkIsLogin();

        SharedPreferences userdetails = getApplicationContext().getSharedPreferences("Login", 0);
        SharedPreferences.Editor editor = userdetails.edit();

        if (!userdetails.getBoolean("isLogin", false)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        userProfile = (TextView) findViewById(R.id.user_profile_name);
        userPhNo = (TextView) findViewById(R.id.user_profile_ph_no);
        userDoubts = (TextView) findViewById(R.id.user_add_doubt);
        userTemple = (TextView) findViewById(R.id.user_add_temple);
        userPoem = (TextView) findViewById(R.id.user_add_poem);
        userEdit = (ImageView) findViewById(R.id.user_edit);
        logOut = (TextView) findViewById(R.id.user_logout);

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar_profile);
        progressBar1.setVisibility(View.VISIBLE);



        getUserDetailsFromDB();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences userdetails = getApplicationContext().getSharedPreferences("Login", 0);
                SharedPreferences.Editor editor = userdetails.edit();
                if (userdetails.contains("name") && userdetails.contains("email") && userdetails.contains("isLogin")) {
                    editor.remove("name");
                    editor.remove("email");
                    editor.remove("isLogin");
                    editor.apply();
                    boolean commit = editor.commit();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Logout Succesfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Your are already Logged out", Toast.LENGTH_SHORT).show();
                }

            }
        });


        userDoubts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Answers.class);
                startActivity(intent);

            }
        });

        userPoem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), UserViewPoem.class);
                startActivity(intent);

            }
        });




        userTemple.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent =new Intent(getApplicationContext(), UserTemple.class);
                startActivity(intent);
            }
        });



        userEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), UserUpdateDetails.class);
                intent.putExtra("userName", userNameDb);
                intent.putExtra("userEmail", userEmailDb);
                intent.putExtra("userPlace", userPlaceDb);
                intent.putExtra("userPassword", userPasswordDb);
                startActivity(intent);

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ProfilePage Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }



    private void checkConnection()
    {
        Network network =new Network();
        if (!network.isOnline(ProfilePage.this))
        {
            Intent intent = new Intent(ProfilePage.this,ConnectionError.class);
            startActivity(intent);
        }

    }


    private void checkIsLogin()
    {
        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
        SharedPreferences.Editor editor = userdetails.edit();

        loginUserName=userdetails.getString("name", null);
        loginUserEmail=userdetails.getString("email",null);

    }


    public void getUserDetailsFromDB() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        showJSON(response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar1.setVisibility(View.GONE);
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfilePage.this);
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


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", loginUserEmail);

                return params;

            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Creating a request queue
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }

    private void showJSON(String response)
    {
        try
        {
            jsonObject = new JSONObject(response);
            result=jsonObject.getJSONArray("result");
            userData = result.length();

                getUserDetail();

        }

        catch (Exception e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private String userNameDb,userEmailDb,userPlaceDb,userPasswordDb;
    private void getUserDetail()
    {

        try {

            JSONObject userDetail = result.getJSONObject(0);
            userNameDb = userDetail.getString("name");
            userEmailDb =  userDetail.getString("email");
            userPlaceDb=userDetail.getString("place");
            userPasswordDb=userDetail.getString("password");


            setUserData();
        }
        catch (JSONException e)
        {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void setUserData()
    {
        try {

            progressBar1.setVisibility(View.GONE);
            userProfile.setText(userNameDb);
            userPhNo.setText(userEmailDb);

        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error Occured While Loading", Toast.LENGTH_SHORT).show();
        }

    }




    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
