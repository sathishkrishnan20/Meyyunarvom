package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class Temples extends AppCompatActivity implements View.OnClickListener//SearchView.OnQueryTextListener
 {

    String tname = "";
    String tplace = "";
    String tdesc = "";
    String tImageUrl = "";
    private int templeDataLength;

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

        imageView=(ImageView)findViewById(R.id.imageViewShow);
        buttonMoveNext = (Button) findViewById(R.id.buttonNext);
        buttonMovePrevious = (Button) findViewById(R.id.buttonPrev);
        buttonMoveNext.setOnClickListener(this);
        buttonMovePrevious.setOnClickListener(this);

        setTempleName=(TextView)findViewById(R.id.tNameSetId);
        setTemplePlace=(TextView)findViewById(R.id.tPlaceSetId);
        setTempleDesc = (TextView)findViewById(R.id.tDescSetId);

        getTemplesFromDB();
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


private void getTempleData()
{

    try {

        JSONObject templeData = result.getJSONObject(TRACK);

        tname =  templeData.getString("tname");
        tplace = templeData.getString("tplace");
        tdesc =  templeData.getString("tdesc");
        tImageUrl = templeData.getString("timage");
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

   String templeDesc[]=tdesc.split(";");

    setTempleName.setText(tname);
    setTemplePlace.setText(tplace);
   // setTempleDesc.setText("Special :"+templeDesc[0]+"\nSpecial Days  :"+templeDesc[1]+"\nVehicles"+templeDesc[2]+"\nPh.No"+templeDesc[3]+"\nAbout"+templeDesc[4]);
    Picasso.with(getApplicationContext()).load(tImageUrl).error(R.drawable.error).placeholder(R.drawable.placeholder).resize(600,360).into(imageView); //this is optional the image to display while the url image is downloading.error(0)         //this is also optional if some error has occurred in downloading the image this image would be displayed
}
catch(Exception e)
{
    Toast.makeText(getApplicationContext(),"Error Occured While Loading the data", Toast.LENGTH_SHORT).show();
}

 }


    public void onClick(View v) {

        if(v == buttonMoveNext){
            moveNext();
        }
        if(v== buttonMovePrevious){
            movePrevious();
        }
    }



    private void moveNext(){
        if(TRACK < templeDataLength){
            TRACK++;
            getTempleData();
        }
    }

    private void movePrevious(){
        if(TRACK>0){
            TRACK--;
            getTempleData();
        }
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewtemple_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    // The following callbacks are called for the SearchView.OnQueryChangeListener
    public boolean onQueryTextChange(String newText) {
        newText = newText.isEmpty() ? "" : "Query so far: " + newText;
      //  mSearchText.setText(newText);
      //  mSearchText.setTextColor(Color.GREEN);
        return true;
    }

    public boolean      onQueryTextSubmit      (String query) {
        //Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
      //  mSearchText.setText("Searching for: " + query + "...");
      // mSearchText.setTextColor(Color.RED);
        return true;
    }



*/
}
