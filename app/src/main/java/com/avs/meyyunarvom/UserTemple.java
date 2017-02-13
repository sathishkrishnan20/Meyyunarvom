package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.avs.db.Network;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class UserTemple extends AppCompatActivity implements View.OnClickListener{


    private ProgressBar progressBar1;

    private GestureDetector mGesture;
    static final int SWIPE_MIN_DISTANCE = 120;
    static final int SWIPE_THRESHOLD_VELOCITY = 200;

    String loginUserName,loginUserEmail;
    private TextView templeName, templePlace, postedBy;
    private ImageView templeImageUT;
//    private Button prevButton,nextButton;

    private int TRACK = 0;
    private JSONObject jsonObject;
    private JSONArray result;

    int templeDataLength=0;
    String templeNameStr, templePlaceStr, postedByStr, templeAddressStr, templeDescStr, templeImageStr,templeLattitude, templeLongitude;

    private final String GET_URL = com.avs.db.URL.url + "/getTempleByUser.php";
    private final String DELETE_FROM_USER_URL=com.avs.db.URL.url +"/deleteTempleByUser.php";
    private final String DELETE_POEM_PERMANENT_USER_URL = com.avs.db.URL.url +"/deleteTemplePermanantlyByUser.php";


    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButtonAdd, floatingActionButtonEdit, floatingActionButtonDelete;



    public String KEY_ID ="id";
    int templeId =0;

    int restrictButton = 1;

    ArrayList<String> templeDetailsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_temple);


        checkConnection();
        checkIsLogin();
        mGesture = new GestureDetector(this, mOnGesture);

        templeName =(TextView) findViewById(R.id.tname_usertemple);
        templePlace =(TextView) findViewById(R.id.tplace_usertemple);
        postedBy=(TextView)findViewById(R.id.temple_added_by_admin);
        templeImageUT =(ImageView)findViewById(R.id.temple_photo);

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar_temple);
        progressBar1.setVisibility(View.VISIBLE);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu_temple);
        floatingActionButtonAdd = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_add_temple);
        floatingActionButtonEdit = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_edit_temple);
        floatingActionButtonDelete = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_delete_temple);

        floatingActionButtonAdd.setOnClickListener(this);
        floatingActionButtonEdit.setOnClickListener(this);
        floatingActionButtonDelete.setOnClickListener(this);
        getUserTempleFromDB();


    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);

    }


    private void checkConnection()
    {
        Network network =new Network();
        if (!network.isOnline(UserTemple.this))
        {
            Intent intent = new Intent(UserTemple.this,ConnectionError.class);
            startActivity(intent);
        }

    }
    private void checkIsLogin()
    {
        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
        loginUserName=userdetails.getString("name", null);
        loginUserEmail=userdetails.getString("email",null);

    }

    public void getUserTempleFromDB() {
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
            templeDataLength = result.length();

            if(templeDataLength == 0) {
                restrictButton = 1;
                Toast.makeText(this,"You are not added Yet",Toast.LENGTH_LONG).show();
                progressBar1.setVisibility(View.GONE);

            }
            else {
                restrictButton = 0;
                getTempleData();
            }
        }

        catch (Exception e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    private void getTempleData()
    {
        try {

            JSONObject templeData = result.getJSONObject(TRACK);
            templeId = templeData.getInt("id");
            templeNameStr =  templeData.getString("tname");
            templePlaceStr = templeData.getString("tplace");
            templeImageStr =templeData.getString("timage");
            postedByStr =templeData.getString("active_flag_publish");


            templeAddressStr=templeData.getString("taddress");
            templeDescStr = templeData.getString("tdesc");
            templeLattitude =templeData.getString("latitude");
            templeLongitude =templeData.getString("longitude");

            templeDetailsArrayList.clear();
            templeDetailsArrayList.add(String.valueOf(templeId));
            templeDetailsArrayList.add(templeNameStr);
            templeDetailsArrayList.add(templePlaceStr);
            templeDetailsArrayList.add(templeImageStr);
            templeDetailsArrayList.add(templeDescStr);
            templeDetailsArrayList.add(templeAddressStr);

            setTempleData();
        }
        catch (JSONException e)
        {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void setTempleData()
    {
        try {
            progressBar1.setVisibility(View.GONE);
            templeName.setText(templeNameStr);
            templePlace.setText(templePlaceStr);

            Picasso.with(getApplicationContext()).load(templeImageStr).error(R.drawable.error).placeholder(R.drawable.placeholder).resize(600,360).into(templeImageUT); //this is optional the image to display while the url image is downloading.error(0)         //this is also optional if some error has occurred in downloading the image this image would be displayed

            if (postedByStr.equals("N"))
            {
                String str="Not Published.. we will publish soon";
                postedBy.setText(str);
                postedBy.setTextColor(getResources().getColor(R.color.red));
            }
            else if (postedByStr.equals("Y"))
            {
                String str="Your Temple Published Successfully";
                postedBy.setText(str);
                postedBy.setTextColor(getResources().getColor(R.color.green));
            }

        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error Occured While Loading the data", Toast.LENGTH_SHORT).show();
        }

    }

    private void resetFields()
    {
        templeName.setText("");
        templePlace.setText("");
        templeImageUT.setImageResource(R.drawable.placeholder);
    }

    private void moveNext(){


        if(TRACK < templeDataLength-1){
            TRACK++;
            resetFields();
            getTempleData();
        }
    }

    private void movePrevious(){

        if(TRACK>0){
            TRACK--;
            resetFields();
            getTempleData();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handled = super.dispatchTouchEvent(ev);
        handled = mGesture.onTouchEvent(ev);
        return handled;
    }


    private GestureDetector.OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                if(TRACK==templeDataLength-1)
                {
                    Toast.makeText(getApplicationContext(),"You Reached a limit", Toast.LENGTH_SHORT).show();
                }

                moveNext();
                //    Toast.makeText(getApplicationContext(), "Left to Right Swap Performed", Toast.LENGTH_SHORT).show();
                return true;
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                //   Toast.makeText(getApplicationContext(), " Right to Left Swap Performed", Toast.LENGTH_SHORT).show();
                if(TRACK==0)
                {
                    Toast.makeText(getApplicationContext(),"You Reached a limit", Toast.LENGTH_SHORT).show();
                }

                movePrevious();
                return true;
            }

            return false;
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // Toast.makeText(getApplicationContext(), " Down Swap Performed", Toast.LENGTH_SHORT).show();

            return false;
        }
    };



    public void onClick(View v) {


        if(v == floatingActionButtonAdd)
        {
            Intent intent =new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("redirectPage","temple");
            intent.putExtra("redirectPageForAddTemple","userTemple");
            startActivity(intent);
        }
        else if(v == floatingActionButtonEdit && restrictButton == 0)
        {

            Intent intent =new Intent(this,MapsActivity.class);
            intent.putExtra("redirectPage", "userTempleUpdate");
            intent.putExtra("userTemple",templeNameStr);
            intent.putExtra("latUser", Double.parseDouble(templeLattitude));
            intent.putExtra("longUser",Double.parseDouble(templeLongitude));
            intent.putExtra("templeDetailsArrayList", templeDetailsArrayList);
            startActivity(intent);

        }

        else if(v ==floatingActionButtonDelete && restrictButton == 0)
        {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserTemple.this);
            alertDialog.setTitle("Thank you");
            alertDialog.setMessage("Are You Sure Want to remove the Temple");
            alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //answer.setText("");
                    deleteFromHere();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_temple, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_permanent_temple) {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserTemple.this);
            alertDialog.setTitle("Thank you");
            alertDialog.setMessage("Are You Sure Want to unpublish the poem");
            alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //answer.setText("");
                    deleteTemplePermanentlyByUser();
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

        return super.onOptionsItemSelected(item);
    }


    public boolean isCanceled =false;
    private void deleteFromHere()
    {
        final ProgressDialog loading =new ProgressDialog(UserTemple.this);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_FROM_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        isCanceled =false;
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserTemple.this);
                        alertDialog.setTitle("Thank you");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext() , UserTemple.class);
                                startActivity(intent);

                            }
                        });

                        alertDialog.show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(UserTemple.this, error.toString()+templeId, Toast.LENGTH_LONG).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                // String tempImage = getStringImage(bitmap);


                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_ID, String.valueOf(templeId));
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);

    }

    private void deleteTemplePermanentlyByUser()
    {
        final ProgressDialog loading =new ProgressDialog(UserTemple.this);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_POEM_PERMANENT_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        isCanceled =false;
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserTemple.this);
                        alertDialog.setTitle("Thank you");
                        alertDialog.setMessage(response.split(";")[0]);

                        alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent =new Intent(getApplicationContext(), UserTemple.class);
                                startActivity(intent);
                                // Write your code here to execute after dialog closed
                            }
                        });

                        alertDialog.show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(UserTemple.this, error.toString()+templeId, Toast.LENGTH_LONG).show();
                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                // String tempImage = getStringImage(bitmap);

                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_ID, String.valueOf(templeId));
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);


    }








}
