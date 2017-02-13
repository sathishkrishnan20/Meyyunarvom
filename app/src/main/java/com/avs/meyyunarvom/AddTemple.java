package com.avs.meyyunarvom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
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
import com.avs.db.URL;
import com.google.android.gms.identity.intents.Address;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class AddTemple extends AppCompatActivity implements View.OnClickListener
{

    String tempName, tempPlace, tempSpl,tempSplDays,tempVehicle,tempAbout, tempPhNo;
    private String tempAddressLine, tempDistrict, tempState, tempCountry;
    String tempDesc , tempAddress;

    String locationByMap, latLng;

    Double lattitude, longitude;
    private EditText templeName,templePlace, templeSpl, templeSplDays, templeVehicle, templeAbout,templePhNo;
    private EditText templeAddressLine, templeDistrict, templeState, templeCountry;


   private MultiAutoCompleteTextView templeDesc;
   private Button chooseImage;
   private ImageView image;
   private Button uploadImage;

   private Bitmap bitmap;

   private String UPLOAD_URL = URL.url + "/setTempleByUser.php";

   // private String UPLOAD_URL = "http://192.168.1.4/Meyyunarvom/setTemple.php";


   private int PICK_IMAGE_REQUEST = 1;

   private String KEY_EMAIL = "email";
   private String KEY_NAME = "tname";
   private String KEY_PLACE = "tplace";
   private String KEY_DESC = "tdesc";
   private String KEY_IMAGE = "timage";
    private String KEY_DIST = "tdist";
    private String KEY_ADDRESS = "taddress";
    private String KEY_LATTITUDE = "latitude";
    private String KEY_LONGITIDE = "longitude";
   private String KEY_IMAGE_ENCODE = "tencode";

   private String loginUserEmail,loginUserName;

   String loginuser;
   SQLiteDatabase db;

   int imageUploadCount = 0;
    String[] locationSplit = new String[4];

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_add_temple);

      checkConnection();

       SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);
       SharedPreferences.Editor editor = userdetails.edit();

       if(!userdetails.getBoolean("isLogin" ,false))
       {
           Toast.makeText(this,"Please login and Contiue",Toast.LENGTH_SHORT).show();
           Intent intent =new Intent(this,LoginActivity.class);
           startActivity(intent);
       }
       userCheck();

       templeName = (EditText) findViewById(R.id.tnameaddtemp);
       templePlace = (EditText) findViewById(R.id.tplaceaddtemp);

       templeAddressLine =(EditText)findViewById(R.id.taddrsline1);
       templeDistrict =(EditText)findViewById(R.id.taddrsdistrict);
       templeState= (EditText)findViewById(R.id.taddrsstate);
       templeCountry =(EditText)findViewById(R.id.taddrscountry);



       templeSpl = (EditText) findViewById(R.id.tdescspladdtemp);
       templeSplDays= (EditText) findViewById(R.id.tdescspldaysaddtemp);
       templeVehicle= (EditText) findViewById(R.id.tdescvehiclessaddtemp);
       templeAbout = (EditText) findViewById(R.id.tdescaboutaddtemp);
       templePhNo= (EditText) findViewById(R.id.tdescphnoaddtemp);

       Intent intent =getIntent();
       locationByMap = intent.getStringExtra("location");
       lattitude = intent.getDoubleExtra("lattitude",0.0);
       longitude = intent.getDoubleExtra("longitude", 0.0);
       latLng = intent.getStringExtra("latLng");




      // templePlace.setText(locationByMap);



       chooseImage = (Button) findViewById(R.id.tchoosebtnaddtemp);
       image = (ImageView) findViewById(R.id.timageviewaddtemp);
       uploadImage = (Button) findViewById(R.id.tuploadbtnaddtemp);

       chooseImage.setOnClickListener(this);
       uploadImage.setOnClickListener(this);

       locationSplit= locationByMap.split("%%");

       if (locationSplit[0].equals("null")) {
           templeAddressLine.setText("");
       }
       else {
           tempAddressLine = locationSplit[0];
           templeAddressLine.setText(locationSplit[0]);
       }


       if (locationSplit[1].equals("null")) {
           templeDistrict.setText("");
       }
       else {
           tempDistrict = locationSplit[1];
           templeDistrict.setText(locationSplit[1]);
       }

       if (locationSplit[2].equals("null")) {
           templeState.setText("");
       }
       else {
           tempState =locationSplit[2];
           templeState.setText(locationSplit[2]);
       }

       if(locationSplit[3].equals("null")) {
           templeCountry.setText("");
       }
       else {
           tempCountry = locationSplit[3];
           templeCountry.setText(locationSplit[3]);
       }
   }

   public void onBackPressed()
   {

       if(getIntent().getStringExtra("redirectPageForAddTemple").equals("mainTemple")) {
           Intent intent = new Intent(getApplicationContext(), Temples.class);
           startActivity(intent);
       }
       if(getIntent().getStringExtra("redirectPageForAddTemple").equals("userTemple"))
       {
           Intent intent = new Intent(getApplicationContext(), UserTemple.class);
           startActivity(intent);
       }

   }

   private void checkConnection()
   {
      Network network=new Network();
      if (!network.isOnline(AddTemple.this))
      {
         Intent intent = new Intent(AddTemple.this,ConnectionError.class);
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
            image.setImageBitmap(bitmap);
            imageUploadCount = 1;
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }


   public String getStringImage(Bitmap bmp) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
      byte[] imageBytes = baos.toByteArray();
      String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
      return encodedImage;
   }


   boolean isCanceled =false;

   private void uploadImage() {
     // final ProgressDialog loading = ProgressDialog.show(this, "Uploading", "Please Wait....", false, false);
      final ProgressDialog loading =new ProgressDialog(AddTemple.this);
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

                     AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddTemple.this);
                     alertDialog.setTitle("Thank you");
                     alertDialog.setMessage(response.split(";")[0]);

                     alertDialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int which) {
                             Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                             startActivity(intent);
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
            Toast.makeText(AddTemple.this, error.toString(), Toast.LENGTH_LONG).show();
         }
      }) {
         protected Map<String, String> getParams() throws AuthFailureError {
            String tempImage = getStringImage(bitmap);

            Map<String, String> params = new Hashtable<String, String>();
            params.put(KEY_EMAIL, loginUserEmail);
            params.put(KEY_NAME, tempName);
            params.put(KEY_PLACE, tempPlace);
            params.put(KEY_DIST, tempDistrict);
            params.put(KEY_ADDRESS,tempAddress);
            params.put(KEY_DESC, tempDesc);
            params.put(KEY_IMAGE, tempImage);
            params.put(KEY_LATTITUDE, lattitude.toString());
            params.put(KEY_LONGITIDE, longitude.toString());
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
   public void onClick(View view) {
      if (view == chooseImage) {
           showFileChooser();
      }
      if (view == uploadImage) {
         StringBuilder templeData = new StringBuilder();
         StringBuilder templeFullAddress = new StringBuilder();

          tempName = templeName.getText().toString().trim();
          tempPlace = templePlace.getText().toString().trim();

          tempSpl = templeSpl.getText().toString().trim();
          tempSplDays = templeSplDays.getText().toString().trim();
          tempVehicle =templeVehicle.getText().toString().trim();
          tempPhNo =templePhNo.getText().toString().trim();
          tempAbout =templeAbout.getText().toString().trim();


          tempAddressLine = templeAddressLine.getText().toString().trim();
          tempDistrict = templeDistrict.getText().toString().trim();
          tempState =templeState.getText().toString().trim();
          tempCountry =templeCountry.getText().toString().trim();





          if(tempName.length()==0){
              Snackbar.make(view, "Enter Temple Name", Snackbar.LENGTH_SHORT).show();
            return;}

            //  templeName.setError("Enter Temple Name"); return;}
          if (tempPlace.length()==0) {
              Snackbar.make(view, "Enter Temple Place", Snackbar.LENGTH_SHORT).show();
              return;
          }

          if(tempSpl.length()==0) {
              // templeSplDays.setError("Enter Spl Days");
              Snackbar.make(view, "Enter Temple Spl", Snackbar.LENGTH_SHORT).show();
              return;
          }

          if(tempSplDays.length()==0) {
              // templeSplDays.setError("Enter Spl Days");
              Snackbar.make(view, "Enter Temple SplDays", Snackbar.LENGTH_SHORT).show();
              return;
          }

          if(tempVehicle.length()==0) {
              // templeSplDays.setError("Enter Vehicle");return;}
              Snackbar.make(view, "Enter Temple Vehicle", Snackbar.LENGTH_SHORT).show();
              return;
          }

          if(tempAbout.length()==0) {
            //  templeAbout.setError("Enter something about temple"); return; }
              Snackbar.make(view, "Enter Temple About", Snackbar.LENGTH_SHORT).show();
              return;
          }

          if(tempPhNo.length()<5) {
              //templePhNo.setError("Enter Correct Mobile No");return;
              Snackbar.make(view, "Enter Correct Mobile No", Snackbar.LENGTH_SHORT).show();
              return;
          }

          if(tempDistrict.length()<5) {
              //templePhNo.setError("Enter Correct Mobile No");return;
              Snackbar.make(view, "Enter District", Snackbar.LENGTH_SHORT).show();
              return;
          }

          templeData.append(tempSpl+"%%");
          templeData.append(tempSplDays+"%%");
          templeData.append(tempVehicle+"%%");
          templeData.append(tempPhNo+"%%");
          templeData.append(tempAbout+"%%");

          templeFullAddress.append(tempAddressLine+"%%");
          templeFullAddress.append(tempDistrict+"%%");
          templeFullAddress.append(tempState+"%%");
          templeFullAddress.append(tempCountry+"%%");

          tempAddress=templeFullAddress.toString();
          tempDesc = templeData.toString();



         //tempDesc = templeDesc.getText().toString().trim();

         if(loginUserEmail==null)
         {
            Toast.makeText(AddTemple.this, "You are Not Logged in..Please Login and Continue", Toast.LENGTH_SHORT).show();
            return;
         }


          if (/*!tempName.isEmpty() && !tempPlace.isEmpty() && !tempDesc.isEmpty() &&*/ imageUploadCount == 0) {
              Snackbar.make(view, "Please Choose the file", Snackbar.LENGTH_SHORT)
                      .show();

          }
          else
                uploadImage();

      }

   }


}