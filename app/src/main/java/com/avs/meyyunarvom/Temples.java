package com.avs.meyyunarvom;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class Temples extends AppCompatActivity //implements View.OnClickListener
       // implements SearchView.OnQueryTextListener, SearchView.OnCloseListener//, Button.OnClickListener//SearchView.OnQueryTextListener, View.OnClickListener
 {
     private GestureDetector mGesture;
     static final int SWIPE_MIN_DISTANCE = 120;
     static final int SWIPE_THRESHOLD_VELOCITY = 200;
     private static final int PERMISSION_REQUEST_CODE = 1;


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



    private TextView setTempleName, setTemplePlace,setTempleDist, setTempleDescSpl,setTempleDescFestival,setTempleDescVehicle,setTempleMobileNo,setTempleAbout ;
    private ImageView imageView;

     private float lattitude, longitude;
     private String latStr, lngStr;
     private String taddress, taddressLine1,taddressDistrict, taddressState, taddressCountry;


     private final String GET_URL = com.avs.db.URL.url + "/getTemple.php";


     ArrayList templePlaceList = new ArrayList();

     ListView lv;

     String districtName ="noDist";

     Boolean isDistClicked = false ;

     RelativeLayout layout;

     private LinearLayout expandCollapseLayout;
     private ImageButton expandCollapseButton;
     private ImageView expandCollapseArrow;
     private ImageButton downloadImage;

     private boolean isSearchButonPressed =false;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temples);


        checkConnection();


         if (Build.VERSION.SDK_INT >= 23)
         {
             if (!checkPermission())
                requestPermission();
         }

         mGesture = new GestureDetector(this, mOnGesture);
         imageView=(ImageView)findViewById(R.id.imageViewShow);
        setTempleName=(TextView)findViewById(R.id.tNameSetId);
        setTemplePlace=(TextView)findViewById(R.id.tPlaceSetId);
        setTempleDist=(TextView)findViewById(R.id.tdistSetId);
        layout =(RelativeLayout)findViewById(R.id.relativeTemple);
        expandCollapseLayout =(LinearLayout)findViewById(R.id.expand_collapse_layout);
        expandCollapseButton= (ImageButton) findViewById(R.id.expand_collapse);
        expandCollapseArrow =(ImageView)findViewById(R.id.expand_collapse_arrow);
        downloadImage =(ImageButton)findViewById(R.id.download_image);
        expandCollapseLayout.setVisibility(View.GONE);


         expandCollapseButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(expandCollapseLayout.getVisibility()==GONE) {

                     expandCollapseLayout.setVisibility(View.VISIBLE);
                     expandCollapseArrow.setImageResource(R.drawable.ic_action_collapse);

                 }
                 else if(expandCollapseLayout.getVisibility()==VISIBLE)
                 {
                     expandCollapseLayout.setVisibility(View.GONE);
                     expandCollapseArrow.setImageResource(R.drawable.ic_action_expand);
                 }

             }
         });


         lv = (ListView) findViewById(R.id.list_viewextemp);
         lv.setVisibility(View.GONE);


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
                intent.putExtra("redirectPageForAddTemple","mainTemple");
                startActivity(intent);
            }
        });



         layout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(lv.getVisibility()==VISIBLE)
                 {
                     lv.setVisibility(View.GONE);
                 }
             }
         });

         downloadImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (Build.VERSION.SDK_INT >= 23)
                 {
                     if (!checkPermission())
                         requestPermission();
                 }
                NotificationManager mNotifyManager =
                         (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Temples.this);
                 mBuilder.setContentTitle(tname+ " Download")
                         .setContentText("Download in progress")
                         .setSmallIcon(R.drawable.ic_action_download);

                Target target = new Target() {
                     @Override
                     public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {


                                 File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                                // Toast.makeText(getApplicationContext(), sd.toString(), Toast.LENGTH_LONG).show();
                                 File folder = new File(sd, "/AVS/");
                                 if (!folder.exists()) {
                                     if (!folder.mkdir()) {
                                         //Toast.makeText(getApplicationContext(),"cant create folder", Toast.LENGTH_LONG).show();
                                     } else {
                                         folder.mkdir();
                                     }
                                 }

                                 File fileName = new File(folder, "one.jpg");

                                 if (!fileName.exists()) {
                                     try {
                                         fileName.createNewFile();
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }
                                 } else {

                                     try {
                                         FileOutputStream outputStream = new FileOutputStream(String.valueOf(fileName));
                                         bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                         outputStream.close();

                                     } catch (FileNotFoundException e) {
                                         e.printStackTrace();
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }
                                 }

                             }

                     @Override
                     public void onBitmapFailed(Drawable errorDrawable) {

                     }

                     @Override
                     public void onPrepareLoad(Drawable placeHolderDrawable) {

                     }
                 };

                 Picasso.with(getApplicationContext()).load(tImageUrl).into(target);
                 mBuilder.setContentText("Download complete").setProgress(0,0,false);
                 mNotifyManager.notify(1, mBuilder.build());
                 Toast.makeText(getApplicationContext(),tname+" is Saved to "+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Toast.LENGTH_LONG).show();

             }
         });



     }




     ArrayList search_result_arraylist =new ArrayList();
     ArrayList searchArrayListWithoutCount = new ArrayList();
     ArrayAdapter adapter;
     private MenuItem searchMenuItem;
     private SearchView mSearchView;
     private boolean isSearchResultEmpty =false;

     //ArrayList <String> hs=new ArrayList<String>();

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.view_temple_menu, menu);
         searchMenuItem = menu.findItem(R.id.action_search_temp123);



         mSearchView = (SearchView) searchMenuItem.getActionView();





         lv.setVisibility(View.VISIBLE);

         mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

             @Override
             public boolean onQueryTextSubmit(String searchText) {
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String searchText) {

                 search_result_arraylist.clear();
                 searchArrayListWithoutCount.clear();

                 for(int i =0 ;i < templePlaceList.size();i++){
                     if(templePlaceList.get(i).toString().contains(searchText)){
                         search_result_arraylist.add(templePlaceList.get(i).toString());
                         searchArrayListWithoutCount.add(templePlaceListWithoutCount.get(i).toString());
                        // Toast.makeText(getApplicationContext(),templePlaceList.indexOf("VairaviKinaru (vairai)"),Toast.LENGTH_LONG).show();
                     }
                 }
                 if(searchArrayListWithoutCount.isEmpty())
                 {
                     isSearchResultEmpty = true;
                     searchArrayListWithoutCount.add("Sorry We Cannot Find Any temples");
                     lv.setAdapter(adapter);
                  //   lv.setVisibility(VISIBLE);

                 }
                 else {
                 isSearchResultEmpty = false;
                 adapter = new ArrayAdapter<String>(Temples.this,android.R.layout.simple_list_item_1, searchArrayListWithoutCount);
                 lv.setAdapter(adapter);
                 //lv.setVisibility(VISIBLE);
                 }
                 return false;
             }


         });



         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 if(isSearchResultEmpty)
                 {
                     lv.setVisibility(View.GONE);
                     mSearchView.setVisibility(View.GONE);
                     mSearchView.setVisibility(VISIBLE);
                     mSearchView.setEnabled(false);
                     mSearchView.setEnabled(true);

                 }
                 else {

                     String item = (String) search_result_arraylist.get(position);
                     String templePlacePathi = item.split(";")[0];
                     TRACK = Integer.parseInt(templePlacePathi);
                     mSearchView.setVisibility(View.GONE);
                     getTempleData(TRACK);
                     lv.setVisibility(View.GONE);
                     mSearchView.setVisibility(VISIBLE);
                 }
             }
         });
         return true;
     }

     JSONObject templePlaceData;
     ArrayList templePlaceListWithoutCount =new ArrayList();

     private void setSetTemplePlace() {
         try {
             templePlaceList.clear();
             templePlaceListWithoutCount.clear();
             for (int templeCount = 0; templeCount < templeDataLength; templeCount++) {
                 templePlaceData = result.getJSONObject(templeCount);
                 templePlaceList.add(templeCount+";"+templePlaceData.getString("tplace")+" ("+templePlaceData.getString("tname")+")");
                 templePlaceListWithoutCount.add(templePlaceData.getString("tplace")+" ("+templePlaceData.getString("tname")+")");

             }
         }
         catch (Exception e)
         {
             Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
             e.printStackTrace();
         }
     }





     @Override
     public void onBackPressed() {

         lv.setVisibility(View.GONE);
         if(isSearchButonPressed && lv.getVisibility() == View.VISIBLE) {

                 lv.setVisibility(View.GONE);
         }

         else {
                      Intent intent = new Intent(this, MainActivity.class);
             startActivity(intent);
         }

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

                progressBar1.setVisibility(GONE);
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Temples.this);
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
            protected Map<String,String> getParams()
            {
                Map <String,String> params=new HashMap<String,String>();
                params.put("district", districtName);

                return params;
            }

        };

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

        expandCollapseLayout.setVisibility(View.GONE);
        expandCollapseArrow.setImageResource(R.drawable.ic_action_expand);

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

        addressData =taddress.split("%%");
        taddressLine1=addressData[0];
        taddressDistrict=addressData[1];
        taddressState=addressData[2];
        taddressCountry=addressData[3];

        setTempleData();


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

    progressBar1.setVisibility(GONE);

    String templeDesc[]=new String[5];
    templeDesc= tdesc.split("%%");

    setTempleName.setText(tname);
    setTemplePlace.setText(tplace);
    setTempleDist.setText(taddressDistrict);


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





     private void resetFields()
     {
         setTempleName.setText("");
         setTemplePlace.setText("");
         setTempleDist.setText("");
         setTempleDescFestival.setText("");
         setTempleDescVehicle.setText("");
         setTempleMobileNo.setText("");
         setTempleAbout.setText("");
         imageView.setImageResource(R.drawable.placeholder);

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


        else if(id ==R.id.action_search_temp123)
        {
            adapter = new ArrayAdapter<String>(Temples.this,android.R.layout.simple_list_item_1, searchArrayListWithoutCount);
            lv.setAdapter(adapter);
            isSearchButonPressed =true;
            lv.setVisibility(View.VISIBLE);
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




     private boolean checkPermission() {
         int result = ContextCompat.checkSelfPermission(Temples.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
         if (result == PackageManager.PERMISSION_GRANTED) {
             return true;
         } else {
             return false;
         }
     }

     private void requestPermission() {

         if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
             Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
         } else {
             ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
         }
     }

     @Override
     public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
         switch (requestCode) {
             case PERMISSION_REQUEST_CODE:
                 if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                     Log.e("value", "Permission Granted, Now you can use local drive .");
                 } else {
                     Log.e("value", "Permission Denied, You cannot use local drive .");
                 }
                 break;
         }
     }




 }
