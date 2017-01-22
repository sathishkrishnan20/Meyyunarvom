package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

public class Temples extends AppCompatActivity //implements View.OnClickListener//SearchView.OnQueryTextListener
 {
     private GestureDetector mGesture;
     static final int SWIPE_MIN_DISTANCE = 120;
     static final int SWIPE_THRESHOLD_VELOCITY = 200;



     String tname = "";
    String tplace = "";
    String tdesc = "";
    String tImageUrl = "";
    private int templeDataLength;

  //  private final String GET_URL ="http://192.168.1.4/Meyyunarvom/getTemple.php";
    private int TRACK = 0;

    private JSONObject jsonObject;
    private JSONArray result;



    private TextView setTempleName, setTemplePlace, setTempleDesc;
    private Button buttonMoveNext;
    private Button buttonMovePrevious;
    private ImageView imageView;

     private final String GET_URL = com.avs.db.URL.url + "/getTemple.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temples);
        checkConnection();
        mGesture = new GestureDetector(this, mOnGesture);

        imageView=(ImageView)findViewById(R.id.imageViewShow);
      //  buttonMoveNext = (Button) findViewById(R.id.buttonNext);
      //  buttonMovePrevious = (Button) findViewById(R.id.buttonPrev);
  //      buttonMoveNext.setOnClickListener(this);
    //    buttonMovePrevious.setOnClickListener(this);

        setTempleName=(TextView)findViewById(R.id.tNameSetId);
        setTemplePlace=(TextView)findViewById(R.id.tPlaceSetId);
        setTempleDesc = (TextView)findViewById(R.id.tDescSetId);
        getTemplesFromDB();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("redirectPage","temple");
                startActivity(intent);
            }
        });

    }

     @Override
     public void onBackPressed() {
         Intent intent =new Intent(this, MainActivity.class);
         startActivity(intent);

     }

     private void checkConnection()
    {
        Network network=new Network();
        if (!network.isOnline(Temples.this))
        {
            Intent intent = new Intent(Temples.this,ConnectionError.class);
            startActivity(intent);
        }

    }

    public void getTemplesFromDB() {
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
        templeDataLength = result.length();
        getTempleData();
    }

    catch (Exception e) {

        e.printStackTrace();
    }
}

private float lattitude, longitude;
     private String latStr, lngStr;
private void getTempleData()
{

    try {

        JSONObject templeData = result.getJSONObject(TRACK);

        tname =  templeData.getString("tname");
        tplace = templeData.getString("tplace");
        tdesc =  templeData.getString("tdesc");
        tImageUrl = templeData.getString("timage");
        latStr=templeData.getString("latitude");
        lngStr=templeData.getString("longitude");
        lattitude=Float.parseFloat(latStr);
        longitude=Float.parseFloat(lngStr);
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

   String templeDesc[]=new String[5];
    templeDesc= tdesc.split(";");

    setTempleName.setText(tname);
    setTemplePlace.setText(tplace);
    setTempleDesc.setText("சிறப்புகள்       :"+templeDesc[0]+"\nதிருவிழா        :"+templeDesc[1]+"\nவாகனங்கள்  :"+templeDesc[2]+"\nதொடர்புக்கு   :"+templeDesc[3]+"\nபதியை பற்றி :"+templeDesc[4]);
    Picasso.with(getApplicationContext()).load(tImageUrl).error(R.drawable.error).placeholder(R.drawable.placeholder).resize(600,360).into(imageView); //this is optional the image to display while the url image is downloading.error(0)         //this is also optional if some error has occurred in downloading the image this image would be displayed
}
catch(Exception e)
{
    Toast.makeText(getApplicationContext(),"Error Occured While Loading the data", Toast.LENGTH_SHORT).show();
}

 }



     private void resetFields()
     {
         setTempleName.setText("");
         setTemplePlace.setText("");
         setTempleDesc.setText("");
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_temple_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_goto_loc)
        {
           /* Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }

*/
//            Toast.makeText(this,latStr+","+lngStr,Toast.LENGTH_LONG).show();
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)",lattitude , longitude, tname+"("+tplace+")");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            try
            {
                startActivity(intent);
            }
            catch(ActivityNotFoundException ex)
            {
                try
                {
                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(unrestrictedIntent);
                }
                catch(ActivityNotFoundException innerEx)
                {
                    Toast.makeText(this, "Please install Google maps application", Toast.LENGTH_LONG).show();
                }
            }

        }

        return true;
    }





}
