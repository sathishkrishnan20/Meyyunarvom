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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.avs.db.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserUpdateDetails extends AppCompatActivity implements View.OnClickListener{

    private AutoCompleteTextView mEmailView;
    private EditText mUserNameView;
    private EditText mPlaceView;
    private EditText mPasswordView;
    Button update;

    private static String UPDATE_URL = URL.url+"/updateUserDetails.php";
    private final String GET_URL = com.avs.db.URL.url + "/getUserDetaills.php";

    private static String oldEmail="oldemail";
    private static String dname="name";
    private static String demail="email";
    private static String dplace="place";
    private static String dpassword="password";

    private String name;
    private String email;
    private String place;
    private String password;
    String loginUserName ="", loginUserEmail ="";

    private JSONObject jsonObject;
    private JSONArray result;
    int userData;
    private RelativeLayout actualLayout;
    private ImageView errorImage;
    private ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_details);

        actualLayout = (RelativeLayout)findViewById(R.id.actualLayout_profile);
        errorImage = (ImageView)findViewById(R.id.error_image_profile);



        mUserNameView = (EditText) findViewById(R.id.name_user_edit);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_user_edit);
        mPasswordView = (EditText) findViewById(R.id.password_user_edit);
        mPlaceView =(EditText) findViewById(R.id.place_user_edit);
        update = (Button) findViewById(R.id.user_update_button);

       progressBar1 = (ProgressBar) findViewById(R.id.progressBar_profile);

        progressBar1.setVisibility(View.VISIBLE);
        actualLayout.setVisibility(View.GONE);
        errorImage.setVisibility(View.GONE);


        SharedPreferences userdetails = getApplicationContext().getSharedPreferences("Login", 0);

        if (!userdetails.getBoolean("isLogin", false)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        loginUserName=userdetails.getString("name", null);
        loginUserEmail=userdetails.getString("email",null);

        update.setOnClickListener(this);
        /*
        Intent intent =getIntent();
        name = intent.getStringExtra("userName");
        oldUserEmail =intent.getStringExtra("userEmail");
        place =intent.getStringExtra("userPlace");
        password =intent.getStringExtra("userPassword");
        update.setOnClickListener(this);

        mUserNameView.setText(name);
        mEmailView.setText(oldUserEmail);
        mPlaceView.setText(place);
        mPasswordView.setText(password);
*/
       if(checkConnection())
           getUserDetailsFromDB();

        errorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), UserUpdateDetails.class);
                startActivity(intent);
                finish();
            }
        });


    }


    private boolean checkConnection()
    {
        Network network =new Network();
        if (!network.isOnline(UserUpdateDetails.this))
        {
            errorImage.setVisibility(View.VISIBLE);
            progressBar1.setVisibility(View.GONE);
            return false;
        }
        else {
            return true;
        }
    }



    public void getUserDetailsFromDB() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_URL,
                new Response.Listener<String>() {
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
            mUserNameView.setText(userNameDb);
            mEmailView.setText(userEmailDb);
            mPasswordView.setText(userPasswordDb);
            mPlaceView.setText(userPlaceDb);
          //  restrictionBtn = false;
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error Occured While Loading", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {

        if(v==update) {

                name= mUserNameView.getText().toString().trim();
                email = mEmailView.getText().toString().trim();
                place = mPlaceView.getText().toString().trim();
                password = mPasswordView.getText().toString();

                Network network=new Network();
                if (!network.isOnline(UserUpdateDetails.this)) {
                    Toast.makeText(UserUpdateDetails.this, "No Network Connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6 || password.length() > 16)
                {
                    mPasswordView.setError("Password Length Should be 6 to 16 characters");
                    return;
                }

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !place.isEmpty()) {
                    registerUser();
                } else {
                    Snackbar.make(v, "Please enter the credentials!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }

        }


    int progressStatus=1;
    boolean isCanceled =false;

    public void registerUser()
    {
        //    final ProgressDialog loading = ProgressDialog.show(this,"Loading...","Please wait...",false,false);

        final ProgressDialog loading =new ProgressDialog(UserUpdateDetails.this);
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
                progressStatus = 0;
                isCanceled = true;
                loading.dismiss();
                // Tell the system about cancellation

            }
        });

        loading.show();

        // Set the progress status zero on each button click
        if(isCanceled) {
            progressStatus = 1;
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response)
                    {
                        loading.dismiss();


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserUpdateDetails.this);
                        alertDialog.setTitle("Thank You");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(response.split(";")[1].equals("success")) {
                                    removeFromLocal();

                                }
                                else
                                    Toast.makeText(UserUpdateDetails.this,"oops! Please try again later",Toast.LENGTH_SHORT).show();
                            }
                        });

                        alertDialog.show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
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
                })
        {
            @Override
            protected Map<String,String> getParams()
            {
                Map <String,String> params=new HashMap<String,String>();
                params.put(dname,name);
                params.put(demail,email);
                params.put(dplace, place);
                params.put(dpassword,password);
                params.put(oldEmail,loginUserEmail);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);
    }


    private void removeFromLocal()
    {
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
            Toast.makeText(getApplicationContext(), "Please Login Again", Toast.LENGTH_SHORT).show();
        }

    }
}
