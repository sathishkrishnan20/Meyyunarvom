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
import java.util.Hashtable;
import java.util.Map;


public class AddTemple extends AppCompatActivity implements View.OnClickListener
{

    String tempName, tempPlace, tempSpl,tempSplDays,tempVehicle,tempAbout;
    String tempDesc;

    String locationByMap;
    Double lattitude, longitude;

   private EditText templeName,templePlace, templeSpl, templeSplDays, templeVehicle, templeAbout,templePhNo;


   private MultiAutoCompleteTextView templeDesc;
   private Button chooseImage;
   private ImageView image;
   private Button uploadImage;

   private Bitmap bitmap;

   private String UPLOAD_URL = URL.url + "/setTemple.php";

  //  private String UPLOAD_URL = "http://192.168.1.4/Meyyunarvom/setTemple.php";


   private int PICK_IMAGE_REQUEST = 1;

   private String KEY_EMAIL = "email";
   private String KEY_NAME = "tname";
   private String KEY_PLACE = "tplace";
   private String KEY_DESC = "tdesc";
   private String KEY_IMAGE = "timage";
   private String KEY_IMAGE_ENCODE = "tencode";

   private String loginUserEmail,loginUserName;

   String loginuser;
   SQLiteDatabase db;

   int imageUploadCount = 0;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_add_temple);

      checkConnection();
      userCheck();


       templeName = (EditText) findViewById(R.id.tnameaddtemp);
       templePlace = (EditText) findViewById(R.id.tplaceaddtemp);
       templeSpl = (EditText) findViewById(R.id.tdescspladdtemp);
       templeSplDays= (EditText) findViewById(R.id.tdescspldaysaddtemp);
       templeVehicle= (EditText) findViewById(R.id.tdescvehiclessaddtemp);
       templeAbout = (EditText) findViewById(R.id.tdescaboutaddtemp);
       templePhNo= (EditText) findViewById(R.id.tdescphnoaddtemp);

       Intent intent =getIntent();
       locationByMap = intent.getStringExtra("location");
       lattitude = intent.getDoubleExtra("lattitude",0.0);
       longitude = intent.getDoubleExtra("longitude", 0.0);

       templePlace.setText(locationByMap);



       chooseImage = (Button) findViewById(R.id.tchoosebtnaddtemp);
       image = (ImageView) findViewById(R.id.timageviewaddtemp);
       uploadImage = (Button) findViewById(R.id.tuploadbtnaddtemp);

       chooseImage.setOnClickListener(this);
       uploadImage.setOnClickListener(this);





   }

   public void onBackPressed()
   {
      Intent i=new Intent(getApplicationContext(),MainActivity.class);
      startActivity(i);

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
      bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
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
            params.put(KEY_DESC, tempDesc);
            params.put(KEY_IMAGE, tempImage);

            return params;
         }
      };

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

         tempName = templeName.getText().toString().trim();
         tempPlace = templePlace.getText().toString().trim();
          templeData.append(templeSplDays.getText().toString().trim()+";");
          templeData.append(templeVehicle.getText().toString().trim()+";");
          templeData.append(templeAbout.getText().toString().trim()+";");
          templeData.append(templePhNo.getText().toString().trim()+";");

          tempDesc = templeData.toString();



         //tempDesc = templeDesc.getText().toString().trim();

         if(loginUserEmail==null)
         {
            Toast.makeText(AddTemple.this, "You are Not Logged in..Please Login and Continue", Toast.LENGTH_SHORT).show();
            return;
         }

          if (!tempName.isEmpty() && !tempPlace.isEmpty() && !tempDesc.isEmpty() && imageUploadCount == 1) {

            uploadImage();
         } else {
            Snackbar.make(view, "Please fill all the Fields", Snackbar.LENGTH_SHORT)
                    .show();
         }


      }

   }


}