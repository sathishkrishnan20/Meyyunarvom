package com.avs.meyyunarvom;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Temples extends AppCompatActivity
       // implements SearchView.OnQueryTextListener, SearchView.OnCloseListener//, Button.OnClickListener//SearchView.OnQueryTextListener, View.OnClickListener
 {
     private GestureDetector mGesture;
     static final int SWIPE_MIN_DISTANCE = 120;
     static final int SWIPE_THRESHOLD_VELOCITY = 200;


     private ProgressBar progressBar1;

    String tname = "";
    String tplace = "";
    String tdesc = "";
    String tImageUrl = "";
    private int templeDataLength;

  //  private final String GET_URL ="http://192.168.1.4/Meyyunarvom/getTemple.php";
    private int TRACK = 0;

    private JSONObject jsonObject;
    private JSONArray result;



    private TextView setTempleName, setTemplePlace, setTempleDescSpl,setTempleDescFestival,setTempleDescVehicle,setTempleMobileNo,setTempleAbout ;
    private ImageView imageView;

     private float lattitude, longitude;
     private String latStr, lngStr;
     private String taddress, taddressLine1,taddressDistrict, taddressState, taddressCountry;


     private final String GET_URL = com.avs.db.URL.url + "/getTemple.php";


     ArrayList templePlaceList = new ArrayList();

     String districtName ="noDist";

     Boolean isDistClicked = false ;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temples);


        checkConnection();
        mGesture = new GestureDetector(this, mOnGesture);

        imageView=(ImageView)findViewById(R.id.imageViewShow);
        setTempleName=(TextView)findViewById(R.id.tNameSetId);
        setTemplePlace=(TextView)findViewById(R.id.tPlaceSetId);

         //lv_languages = (ListView) findViewById(R.id.list_viewex);

        //setTempleDesc = (TextView)findViewById(R.id.tDescSetId);

         setTempleDescSpl=(TextView)findViewById(R.id.tDescSplSetId);
         setTempleDescFestival=(TextView)findViewById(R.id.tDescFestSetId);
         setTempleDescVehicle =(TextView)findViewById(R.id.tDescVehicleSetId);
         setTempleMobileNo = (TextView)findViewById(R.id.tDescmobileNOSetId);
         setTempleAbout  =(TextView)findViewById(R.id.tDescaboutSetId);


         progressBar1 = (ProgressBar) findViewById(R.id.progressBar_temple);
         progressBar1.setVisibility(View.VISIBLE);

         Intent intent =getIntent();
         int intentTrack=  intent.getIntExtra("track", -1);
         if(intentTrack != -1)
         {
             TRACK = intentTrack;

         }

         isDistClicked = intent.getBooleanExtra("isClicked",false);

         if(isDistClicked) {
             districtName = intent.getStringExtra("districtName");
         }

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        showJSON(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar1.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"hello"+districtName+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String,String> getParams()
            {
                Map <String,String> params=new HashMap<String,String>();
                params.put("district", districtName);

                return params;
            }

        };
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
        setSetTemplePlace();
        getTempleData(TRACK);
    }

    catch (Exception e) {

        Toast.makeText(getApplicationContext(),"Error"+e.toString(), Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
}


public void getTempleData(int TRACK)
{

    try {

        JSONObject templeData = result.getJSONObject(TRACK);

        String [] addressData= new String[4];

        tname =  templeData.getString("tname");
        tplace = templeData.getString("tplace");
        tdesc =  templeData.getString("tdesc");
        taddress= templeData.getString("taddress");
        tImageUrl = templeData.getString("timage");
        latStr=templeData.getString("latitude");
        lngStr=templeData.getString("longitude");
        lattitude=Float.parseFloat(latStr);
        longitude=Float.parseFloat(lngStr);


        setTempleData();

        addressData =taddress.split("%%");
        taddressLine1=addressData[0];
        taddressDistrict=addressData[1];
        taddressState=addressData[2];
        taddressCountry=addressData[3];





    }
    catch (JSONException e)
    {
        Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
}


 private void setTempleData()
 {
try {

    progressBar1.setVisibility(View.GONE);

    String templeDesc[]=new String[5];
    templeDesc= tdesc.split("%%");

    setTempleName.setText(tname);
    setTemplePlace.setText(tplace);


    setTempleDescSpl.setText(templeDesc[0]);


    setTempleDescFestival.setText(templeDesc[1]);
    setTempleDescVehicle.setText(templeDesc[2]);
    setTempleMobileNo.setText(templeDesc[3]);
    setTempleAbout.setText(templeDesc[4]);



    //setTempleDesc.setText("சிறப்புகள்       :"+templeDesc[0]+"\nதிருவிழா        :"+templeDesc[1]+"\nவாகனங்கள்  :"+templeDesc[2]+"\nதொடர்புக்கு   :"+templeDesc[3]+"\nபதியை பற்றி :"+templeDesc[4]);
    Picasso.with(getApplicationContext()).load(tImageUrl).error(R.drawable.error).placeholder(R.drawable.placeholder).resize(600,360).into(imageView); //this is optional the image to display while the url image is downloading.error(0)         //this is also optional if some error has occurred in downloading the image this image would be displayed
}
catch(Exception e)
{
    Toast.makeText(getApplicationContext(),"Error Occured While Loading the data", Toast.LENGTH_SHORT).show();
}

 }



     JSONObject templePlaceData ;

     private void setSetTemplePlace() {
         try {
             templePlaceList.clear();
             for (int templeCount = 0; templeCount < templeDataLength; templeCount++) {
                 templePlaceData = result.getJSONObject(templeCount);
                 templePlaceList.add(templePlaceData.getString("tplace")+" ("+templePlaceData.getString("tname")+")");
             }
         }
         catch (JSONException e)
         {
             Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
             e.printStackTrace();
         }
     }


     private void resetFields()
     {
         setTempleName.setText("");
         setTemplePlace.setText("");
         setTempleDescFestival.setText("");
         setTempleDescVehicle.setText("");
         setTempleMobileNo.setText("");
         setTempleAbout.setText("");

     }


     private void moveNext(){

        if(TRACK < templeDataLength-1){
            TRACK++;
            resetFields();
            getTempleData(TRACK);

        }
    }

    private void movePrevious(){

        if(TRACK>0){
            TRACK--;
            resetFields();
            getTempleData(TRACK);
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

     SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_temple_menu, menu);
/*
        MenuItem search_item = menu.findItem(R.id.action_search_temp);
        searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(false);
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {

                //clear the previous data in search arraylist if exist
                search_result_arraylist.clear();

                keyword = s.toUpperCase();

                //checking language arraylist for items containing search keyword

                for(int i =0 ;i < languagesarraylist.size();i++){
                    if(languagesarraylist.get(i).contains(keyword)){
                        search_result_arraylist.add(languagesarraylist.get(i).toString());
                    }
                }

                language_adapter = new ArrayAdapter<String>(Temples.this,android.R.layout.simple_list_item_1,search_result_arraylist);
                lv_languages.setAdapter(language_adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
*/
        return true;

    }


/*
     private void setupSearchView() {
         searchView.setIconifiedByDefault(true);


         List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

         // Try to use the "applications" global search provider
         SearchableInfo info = searchManager.getSearchableInfo(getComponentName());

         for (SearchableInfo inf : searchables) {
             if (inf.getSuggestAuthority() != null
                     && inf.getSuggestAuthority().startsWith("applications")) {
                 info = inf;
             }
         }
         searchView.setSearchableInfo(info);
}

         searchView.setOnQueryTextListener(this);
         searchView.setOnCloseListener(this);
     }

     public boolean onQueryTextChange(String newText) {
         Toast.makeText(this,"text "+newText,Toast.LENGTH_LONG).show();

         return false;
     }

     public boolean onQueryTextSubmit(String query) {

         Toast.makeText(this,"query "+query,Toast.LENGTH_LONG).show();
         search_result_arraylist.clear();

         keyword = query.toUpperCase();

         //checking language arraylist for items containing search keyword

         for(int i =0 ;i < languagesarraylist.size();i++){
             if(languagesarraylist.get(i).contains(keyword)){
                 search_result_arraylist.add(languagesarraylist.get(i).toString());
             }
         }

         language_adapter = new ArrayAdapter<String>(Temples.this,android.R.layout.simple_list_item_1,search_result_arraylist);
         lv_languages.setAdapter(language_adapter);

         return false;

     }

     public boolean onClose() {
         Toast.makeText(this, "closed", Toast.LENGTH_LONG).show();
         return false;
     }
     */

     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_goto_loc)
        {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Temples.this);
            alertDialog.setTitle("Address");
            alertDialog.setMessage(tplace+"\n"+taddressLine1+"\n"+taddressDistrict+"\n"+taddressState+"\n"+taddressCountry);
            alertDialog.setPositiveButton("Location",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //answer.setText("");
                    goToMap();
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

        else if(id ==R.id.action_search_temp)
        {

            Intent intent = new Intent(this ,SearchPopupTemple.class);
            intent.putExtra("placeList", templePlaceList);
            startActivity(intent);
        }
        else if(id== R.id.action_search_by_dist)
        {
            Intent intent = new Intent(this, TempleSearchDistrict.class);
            startActivity(intent);
        }


        return true;
    }

     private void goToMap() {

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



 }
