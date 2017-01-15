package com.avs.meyyunarvom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class AdminTempleReview extends AppCompatActivity implements View.OnClickListener {

    private EditText templeName,templePlace, templeSpl, templeSplDays, templeVehicle, templeAbout,templePhNo;
    private EditText templeAddressLine, templeDistrict, templeState, templeCountry, latLng;
    private TextView addedBy;

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
    //private final String GET_URL = com.avs.db.URL.url + "/getTempleAdmin.php";

    private final String GET_URL ="http://192.168.1.4/Meyyunarvom/getTempleAdmin.php";
    private int TRACK = 0;
    private JSONObject jsonObject;
    private JSONArray result;



    String tempName, tempPlace, tempSpl,tempSplDays,tempVehicle,tempAbout, tempPhNo;
    String tempDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_temple_review);
        checkConnection();

        addedBy= (TextView)findViewById(R.id.addedBy);

        templeName = (EditText) findViewById(R.id.admintempname);
        templePlace = (EditText) findViewById(R.id.admintempplace);

        latLng = (EditText) findViewById(R.id.admintemplatlng);



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

            String templeDesc[]=new String[5];
            String templeAddess[]=new String[4];
            templeDesc = tdesc.split(";");
            templeAddess = tAddress.split(";");


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

            latLng.setText(latitude+",\n"+longitude);


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








    public void onClick(View v) {

        if(v == buttonMoveNext){
            resetFields();
            moveNext();
        }
        if(v== buttonMovePrevious){
            resetFields();
            movePrevious();
        }
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
