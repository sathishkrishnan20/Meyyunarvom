package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avs.app.Config;
import com.avs.db.Network;
import com.avs.db.URL;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;



import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import com.google.firebase.messaging.FirebaseMessaging;




public class SignUpActivity extends AppCompatActivity implements View.OnClickListener
{

    String fireBaseId= "";

    private int REQUEST_CODE_ACCESS_FINE_PERMISSIONS = 124;
    private int REQUEST_CODE_ACCESS_COARSE_PERMISSIONS = 120;


    private AutoCompleteTextView mEmailView, mCountryCodeView;
    private EditText mUserNameView;
    private EditText mPlaceView;
    private EditText mPasswordView;
    Button register;

    private static String regUrl = URL.url+"/signup.php";

    private static String dname="name";
    private static String demail="email";
    private static String dplace="place";
    private static String dpassword="password";





    private String name;
    private String email, countryCode;
    private String place;
    private String password;


    private static final String TAG = SignUpActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserNameView = (EditText) findViewById(R.id.name);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPlaceView =(EditText) findViewById(R.id.place);
        register = (Button) findViewById(R.id.email_sign_up_button);
        mCountryCodeView = (AutoCompleteTextView) findViewById(R.id.countryCode);


        mCountryCodeView.setText(getCountryCode());


/*
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

//                    txtMessage.setText(message);
                }
            }
        };
*/

        displayFirebaseRegId();





        register.setOnClickListener(this);

  }

    String CountryZipCode="";


    private String getCountryCode()
    {
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        String CountryID= tm.getSimCountryIso().toUpperCase();
        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for (int i=0; i<rl.length; i++){
            String[] g=rl[i].split(",");
            if(g[1].trim().equals(CountryID.trim())){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
      //  return tm.getSimCountryIso();
    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
            System.err.println(e);
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            //Toast.makeText(this, "Phone Number is Valid " + internationalFormat, Toast.LENGTH_LONG).show();
            return true;
        } else {
            //Toast.makeText(this, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public void onClick(View v)
                {

                    if(v.getId()==R.id.email_sign_up_button) {

                     name= mUserNameView.getText().toString().trim();
                     email = mEmailView.getText().toString().trim();
                     place = mPlaceView.getText().toString().trim();
                     password = mPasswordView.getText().toString().trim();
                     countryCode =mCountryCodeView.getText().toString().trim();


                     if(email.substring(0,1).equals("0")) {
                         email = email.substring(1);
                     }


                        Network network=new Network();
                        if (!network.isOnline(SignUpActivity.this)) {
                            Toast.makeText(SignUpActivity.this, "No Network Connection", Toast.LENGTH_SHORT).show();
                            return;
                        }

                          AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
                          mAwesomeValidation.addValidation(SignUpActivity.this, R.id.name, "[a-zA-Z .]+", R.string.err_name);
                          mAwesomeValidation.addValidation(SignUpActivity.this, R.id.place, "[a-zA-Z .]+", R.string.err_place);


                        try {
                            if (countryCode.length() > 0 && email.length() > 0) {

                                if (isValidPhoneNumber(email)) {
                                    boolean status = validateUsing_libphonenumber(countryCode, email);
                                    if (!status) {

                                        mEmailView.setError("Please Enter valid Mobile Number");
                                        return;

                                    }
                                } else {
                                    mEmailView.setError("Invalid Mobile Number");
                                    return;
                                }
                            } else {

                                Snackbar.make(v, "Please enter Mobile Number", Snackbar.LENGTH_LONG)
                                        .show();
                                return;

                            }
                        } catch (Exception e)
                        {
                            mCountryCodeView.setError("Please Enter valid Country Code");
                            return;
                        }

                     if(password.length() < 6 || password.length() > 16 )
                     {
                         mPasswordView.setError("Password Length Should be 6 to 16 characters");
                         return;
                     }

                     if (name.isEmpty() && place.isEmpty()) {

                         Snackbar.make(v, "Please enter the credentials!", Snackbar.LENGTH_LONG)
                                 .show();
                         return;
                     }
                     if(mAwesomeValidation.validate()) {
                         registerUser();
                     }

                 }
            }

    int progressStatus=1;
    boolean isCanceled =false;

    public void registerUser()
    {
    //    final ProgressDialog loading = ProgressDialog.show(this,"Loading...","Please wait...",false,false);

        final ProgressDialog loading =new ProgressDialog(SignUpActivity.this);
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
        StringRequest stringRequest=new StringRequest(Request.Method.POST, regUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        loading.dismiss();

                        Toast.makeText(SignUpActivity.this,response.split(";")[0],Toast.LENGTH_SHORT).show();
                        resetField();
                        Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                       startActivity(intent);

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

                        resetField();

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
                params.put("firebaseid",fireBaseId);

                return params;
            }


        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(stringRequest);


    }


    private void resetField()
    {
        mEmailView.setText("");
        mPasswordView.setText("");
        mPlaceView.setText("");
        mUserNameView.setText("");
    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
  //          Toast.makeText(this, "FireBase Id" + regId, Toast.LENGTH_LONG).show();
        fireBaseId = regId;
        }
        else
            Toast.makeText(this, "You have Not Recieved Yet",Toast.LENGTH_LONG).show();

    }

/*
    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        com.avs.util.NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
*/




}


