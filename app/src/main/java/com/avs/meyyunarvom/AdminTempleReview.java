package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class AdminTempleReview extends AppCompatActivity implements View.OnClickListener {

    private EditText templeName,templePlace, templeSpl, templeSplDays, templeVehicle, templeAbout,templePhNo;
    private EditText templeAddressLine, templeDistrict, templeState, templeCountry, latLng;
    private TextView addedBy;
            private Button editLatLng;

    private Button chooseImage,uploadTemple, deleteTemple;
    private Button buttonMoveNext, buttonMovePrevious;

    private ImageView adminTempImage;


    String userName= "";
    String userEmail ="";
    String userPlace = "";

    String tname = "";
    String tplace = "";
    String tAddress= "";
    String tdesc = "";
    String tImageUrl = "";
    String latitude ="";
    String longitude="";

    String isPublish="";
    int tempId;

    String userDetailsText="";
    private final String GET_URL = com.avs.db.URL.url + "/getTempleAdmin.php";
    private final String UPLOAD_URL =com.avs.db.URL.url + "/setTempleByAdmin.php";
    private final String DELETE_PERMANENT_URL =com.avs.db.URL.url +"/deleteTemplePermanantlyByAdmin.php";
    private final String DELETE_FROM_ADMIN_URL=com.avs.db.URL.url +"/deleteTempleFromAdminPage.php";


//    private final String GET_URL ="http://192.168.1.4/Meyyunarvom/getTempleAdmin.php";
  //  private final String UPLOAD_URL ="http://192.168.1.4/Meyyunarvom/setTempleAdmin.php";
  //  private final String DELETE_PERMANENT_URL ="http://192.168.1.4/Meyyunarvom/deleteTemplePermanentByAdmin.php";
  //  private final String DELETE_FROM_ADMIN_URL="http://192.168.1.4/Meyyunarvom/deleteTempleFromAdminPage.php";

    private int TRACK = 0;
    private JSONObject jsonObject;
    private JSONArray result;

    private int PICK_IMAGE_REQUEST = 1;
    private int imageUploadCount =0;
    private Bitmap bitmap;

    private String tempAddressLine, tempDistrict, tempState, tempCountry;
    String tempName, tempPlace, tempSpl,tempSplDays,tempVehicle,tempAbout, tempPhNo;
    String tempDesc , tempAddress;

    private String loginUserName="";
    private String loginUserEmail="";


    private String KEY_ID = "id";
    private String KEY_NAME = "tname";
    private String KEY_PLACE = "tplace";
    private String KEY_DESC = "tdesc";
    private String KEY_IMAGE = "timage";
    private String KEY_DIST = "tdist";
    private String KEY_ADDRESS = "taddress";
    private String KEY_LATTITUDE = "latitude";
    private String KEY_LONGITIDE = "longitude";


    String locationByMap,latLngByMap;
    double  longitudeByMap,lattitudeByMap;

    private ProgressBar progressBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_temple_review);
        checkConnection();
        userCheck();
        addedBy= (TextView)findViewById(R.id.addedBy);

        templeName = (EditText) findViewById(R.id.admintempname);
        templePlace = (EditText) findViewById(R.id.admintempplace);

        latLng = (EditText) findViewById(R.id.admintemplatlng);

        editLatLng =(Button) findViewById(R.id.admintemplatlngedit);



        templeAddressLine =(EditText)findViewById(R.id.admintempaddrsline1);
        templeDistrict =(EditText)findViewById(R.id.admintempdistrict);
        templeState= (EditText)findViewById(R.id.admintempstate);
        templeCountry =(EditText)findViewById(R.id.admintempcountry);


        templeSpl = (EditText) findViewById(R.id.admintempdescspl);
        templeSplDays= (EditText) findViewById(R.id.admintempdescspldays);
        templeVehicle= (EditText) findViewById(R.id.admintempdescvehicles);
        templeAbout = (EditText) findViewById(R.id.admintempdescabout);
        templePhNo= (EditText) findViewById(R.id.admintempdescphno);


        chooseImage = (Button) findViewById(R.id.admintempchoosebtn);
        adminTempImage = (ImageView) findViewById(R.id.admintempimageview);
        uploadTemple = (Button) findViewById(R.id.admintempupload);
        deleteTemple=(Button) findViewById(R.id.admintempdelete);

        buttonMoveNext = (Button) findViewById(R.id.admintempbuttonNext);
        buttonMovePrevious = (Button) findViewById(R.id.admintempbuttonPrev);


        buttonMoveNext.setOnClickListener(this);
        buttonMovePrevious.setOnClickListener(this);
        chooseImage.setOnClickListener(this);
        uploadTemple.setOnClickListener(this);
        deleteTemple.setOnClickListener(this);
        editLatLng.setOnClickListener(this);


        progressBar1 = (ProgressBar) findViewById(R.id.progressBar_admintemple);
        progressBar1.setVisibility(View.VISIBLE);



        getTemplesFromDB();


    }


    private void checkConnection()
    {
        Network network=new Network();
        if (!network.isOnline(AdminTempleReview.this))
        {
            Intent intent = new Intent(AdminTempleReview.this,ConnectionError.class);
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
                progressBar1.setVisibility(View.GONE);
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

    int templeDataLength=0;
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
            tempId = templeData.getInt("id");

            userName = templeData.getString("name");
            userEmail = templeData.getString("email");
            userPlace= templeData.getString("place");
            tname =  templeData.getString("tname");
            tplace = templeData.getString("tplace");
            tAddress=templeData.getString("taddress");
            tdesc =  templeData.getString("tdesc");
            tImageUrl = templeData.getString("timage");
            latitude = templeData.getString("latitude");
            longitude = templeData.getString("longitude");
            isPublish = templeData.getString("active_flag_publish");

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

            String templeDesc[]=new String[5];
            String templeAddess[]=new String[4];
            templeDesc = tdesc.split("%%");
            templeAddess = tAddress.split("%%");


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


            
            templeName.setText(tname);
            templePlace.setText(tplace);

            templeAddressLine.setText(templeAddess[0]);
            templeDistrict.setText(templeAddess[1]);
            templeState.setText(templeAddess[2]);
            templeCountry.setText(templeAddess[3]);

            lattitudeByMap = Double.valueOf(latitude);
            longitudeByMap = Double.valueOf(longitude);

            latLng.setText(latitude+", "+"\n"+longitude+"\n");


            templeSpl.setText(templeDesc[0]);
            templeSplDays.setText(templeDesc[1]);
            templeVehicle.setText(templeDesc[2]);
            templePhNo.setText(templeDesc[3]);
            templeAbout.setText(templeDesc[4]);
            Picasso.with(getApplicationContext()).load(tImageUrl).error(R.drawable.error).placeholder(R.drawable.placeholder).resize(600,360).into(adminTempImage); //this is optional the image to display while the url image is downloading.error(0)         //this is also optional if some error has occurred in downloading the image this image would be displayed
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error Occured While Loading the data", Toast.LENGTH_SHORT).show();
        }

    }



    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                adminTempImage.setImageBitmap(bitmap);
                imageUploadCount = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



int mapRequest =0;

    public void onClick(View view) {

        if(view == buttonMoveNext){
            resetFields();
            moveNext();
        }
        if(view == buttonMovePrevious){
            resetFields();
            movePrevious();
        }

        if (view == chooseImage) {
            showFileChooser();
        }


        if(view == editLatLng)
        {
           /* mapRequest =1;
            Intent intent =new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("redirectPage","adminTemple");
            startActivity(intent);
            */
        }

        if(view ==deleteTemple)
        {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminTempleReview.this);
            alertDialog.setTitle("நன்றி");
            alertDialog.setMessage("பதியை நீக்க வேண்டுமா");
            alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //answer.setText("");
                    deleteTempleFromAdminPage();
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


        if (view == uploadTemple) {
            StringBuilder templeData = new StringBuilder();
            StringBuilder templeFullAddress = new StringBuilder();

            if (mapRequest == 1) {
                Intent intent = getIntent();
                locationByMap = intent.getStringExtra("location");
                lattitudeByMap = intent.getDoubleExtra("lattitude", 0.0);
                longitudeByMap = intent.getDoubleExtra("longitude", 0.0);
                latLngByMap = intent.getStringExtra("latLng");
                latLng.setText(lattitudeByMap + ",\n" + longitudeByMap);
            }


            tempName = templeName.getText().toString().trim();
            tempPlace = templePlace.getText().toString().trim();

            tempSpl = templeSpl.getText().toString().trim();
            tempSplDays = templeSplDays.getText().toString().trim();
            tempVehicle = templeVehicle.getText().toString().trim();
            tempPhNo = templePhNo.getText().toString().trim();
            tempAbout = templeAbout.getText().toString().trim();

            tempAddressLine = templeAddressLine.getText().toString().trim();
            tempDistrict = templeDistrict.getText().toString().trim();
            tempState = templeState.getText().toString().trim();
            tempCountry = templeCountry.getText().toString().trim();


            //  templeName.setError("Enter Temple Name"); return;}
            if (tempPlace.length() == 0) {
                Snackbar.make(view, "Enter Temple Place", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (tempSpl.length() == 0) {
                // templeSplDays.setError("Enter Spl Days");
                Snackbar.make(view, "Enter Temple Spl", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (tempSplDays.length() == 0) {
                // templeSplDays.setError("Enter Spl Days");
                Snackbar.make(view, "Enter Temple SplDays", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (tempVehicle.length() == 0) {
                // templeSplDays.setError("Enter Vehicle");return;}
                Snackbar.make(view, "Enter Temple Vehicle", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (tempAbout.length() == 0) {
                //  templeAbout.setError("Enter something about temple"); return; }
                Snackbar.make(view, "Enter Temple About", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (tempPhNo.length() < 5) {
                //templePhNo.setError("Enter Correct Mobile No");return;
                Snackbar.make(view, "Enter Correct Mobile No", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (tempDistrict.length() < 5) {
                //templePhNo.setError("Enter Correct Mobile No");return;
                Snackbar.make(view, "Enter District", Snackbar.LENGTH_SHORT).show();
                return;
            }

            templeData.append(tempSpl + "%%");
            templeData.append(tempSplDays + "%%");
            templeData.append(tempVehicle + "%%");
            templeData.append(tempPhNo + "%%");
            templeData.append(tempAbout + "%%");

            templeFullAddress.append(tempAddressLine + "%%");
            templeFullAddress.append(tempDistrict + "%%");
            templeFullAddress.append(tempState + "%%");
            templeFullAddress.append(tempCountry + "%%");

            tempAddress = templeFullAddress.toString();
            tempDesc = templeData.toString();


            if (tempName.length() == 0) {
                Snackbar.make(view, "Enter Temple Name", Snackbar.LENGTH_SHORT).show();

            } else {
                uploadUpdate();
            }
        }

    }


    boolean isCanceled =false;
    private void deleteTempleFromAdminPage() {

        final ProgressDialog loading =new ProgressDialog(AdminTempleReview.this);
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
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminTempleReview.this);
                        alertDialog.setTitle("நன்றி");
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
                params.put(KEY_ID, String.valueOf(tempId));
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);



    }



    private void deletePermanently()
    {
        final ProgressDialog loading =new ProgressDialog(AdminTempleReview.this);
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
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminTempleReview.this);
                        alertDialog.setTitle("நன்றி");
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
                params.put(KEY_ID, String.valueOf(tempId));
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(stringRequest);


    }



    private void uploadUpdate() {
        // final ProgressDialog loading = ProgressDialog.show(this, "Uploading", "Please Wait....", false, false);
        final ProgressDialog loading =new ProgressDialog(AdminTempleReview.this);
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
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminTempleReview.this);
                        alertDialog.setTitle("நன்றி");
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
                String tempImage = getStringImage(bitmap);

                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_ID, String.valueOf(tempId));
                params.put(KEY_NAME, tempName);
                params.put(KEY_PLACE, tempPlace);
                params.put(KEY_DIST, tempDistrict);
                params.put(KEY_ADDRESS,tempAddress);
                params.put(KEY_DESC, tempDesc);
                params.put(KEY_IMAGE, tempImage);
                params.put(KEY_LATTITUDE, String.valueOf(lattitudeByMap));
                params.put(KEY_LONGITIDE, String.valueOf(longitudeByMap));
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

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminTempleReview.this);
            alertDialog.setTitle("நன்றி");
            alertDialog.setMessage("பதியை நிரந்தரமாக நீக்க வேண்டுமா");
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


            // return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void resetFields()
    {
        templeName.setText("");
        templePlace.setText("");
        templeSpl.setText("");
        templeSplDays.setText("");

        templeVehicle.setText("");
        templeAbout.setText("");
        templePhNo.setText("");
        templeAddressLine.setText("");
        templeDistrict.setText("");
        templeState.setText("");
        templeCountry.setText("");
        latLng.setText("");
        adminTempImage.setImageResource(R.drawable.error);

        userDetailsText="";
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

}
