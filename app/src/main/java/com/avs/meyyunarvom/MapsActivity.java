package com.avs.meyyunarvom;

import android.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.avs.db.Network;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,

        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        PlaceSelectionListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener
        {

       StringBuilder strReturnedAddress = new StringBuilder("");


    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    private static final int REQUEST_SELECT_PLACE = 1000;
    Place place;

    private GoogleMap mMap;

    private int REQUEST_CODE_ACCESS_FINE_PERMISSIONS = 124;
    private int REQUEST_CODE_ACCESS_COARSE_PERMISSIONS = 120;

    private double latitude = 9.92520007;
    private double longitude = 78.1197751;

    private GoogleApiClient googleApiClient;
    String redirectPage="";

    private ProgressBar progressBar1;

    ArrayList<String> templeDetailsForUpdatePage = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        checkConnection();
        SharedPreferences userdetails=getApplicationContext().getSharedPreferences("Login",0);

        if(!userdetails.getBoolean("isLogin" ,false))
        {
            Toast.makeText(this,"Please login and Contiue",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar_maps);
        progressBar1.setVisibility(View.GONE);

        checkIsLogin();
        checkForGooglePlayService();

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if (!manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

        if (!isReadStorageAllowed()) {
            requestStoragePermission();
        }

        Intent intent=getIntent();
        redirectPage = intent.getStringExtra("redirectPage");

        if(redirectPage.equals("userTempleUpdate"))
        {
          Intent intent1 = getIntent();
          templeDetailsForUpdatePage = intent1.getStringArrayListExtra("templeDetailsArrayList");
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

       @Override
       public void onBackPressed() {

           if(redirectPage.equals("temple")) {
               Intent intent = new Intent(this, Temples.class);
               startActivity(intent);
           }

           if(redirectPage.equals("adminTemple")) {

               Intent intent = new Intent(this, AdminTempleReview.class);
               startActivity(intent);

           }
           if(redirectPage.equals("userTempleUpdate"))
           {
               Intent intent = new Intent(this, UserTemple.class);
               startActivity(intent);
           }
       }




       @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng madurai = new LatLng(9.92520007, 78.1197751);
        mMap.addMarker(new MarkerOptions().position(madurai).draggable(true).title("madurai"));
           mMap.setOnMapLongClickListener(this);
           mMap.setOnMapClickListener(this);
           mMap.moveCamera(CameraUpdateFactory.newLatLng(madurai));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7));

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            mMap.clear();
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            moveMap();

        }
    }

    private void moveMap()
    {
        String msg = latitude + ",MoveMap "+longitude;
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.clear();

        try {
            mMap.addMarker(new MarkerOptions()
                    .position(latLng) //setting position
                    .draggable(false) //Making the marker draggable
                    .title("Current Location")); //Adding a title

            //Moving the camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //Animating the camera
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
         //   Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    String templeName ="";
    private void LocationByLatLngUserUpdate() {

        LatLng latLng = new LatLng(latitude, longitude);
        mMap.clear();

        try {
            mMap.addMarker(new MarkerOptions()
                    .position(latLng) //setting position
                    .draggable(false) //Making the marker draggable
                    .title(templeName)); //Adding a title

            //Moving the camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //Animating the camera
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            //   Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }

    }

    private void geoLocationData()
    {
        Geocoder gc = new Geocoder(this);
        try {
            List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
            String latlong= ""+latitude+ ","+longitude;
            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                //strReturnedAddress = new StringBuilder("Address:\n");
                strReturnedAddress.append(returnedAddress.getLocality()+"%%");
                strReturnedAddress.append(returnedAddress.getSubAdminArea()+"%%");
                strReturnedAddress.append(returnedAddress.getAdminArea()+"%%");
                strReturnedAddress.append(returnedAddress.getCountryName()+"%%");


                //  Toast.makeText(this,latlong,Toast.LENGTH_LONG).show();

                progressBar1.setVisibility(View.GONE);
                  if(redirectPage.equals("temple")) {
                    Intent intent = new Intent(this, AddTemple.class);
                    intent.putExtra("location", strReturnedAddress.toString());
                    intent.putExtra("lattitude", latitude);
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("latLng", latlong);
                    startActivity(intent);
                }

                if(redirectPage.equals("adminTemple")) {
                    Intent intent = new Intent(this, AdminTempleReview.class);
                    intent.putExtra("location", strReturnedAddress.toString());
                    intent.putExtra("lattitude", latitude);
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("latLng", latlong);
                    startActivity(intent);
                }

                if(redirectPage.equals("userTempleUpdate"))
                {
                    Intent intent = new Intent(this, UserTempleUpdate.class);
                    intent.putExtra("location", strReturnedAddress.toString());
                    intent.putExtra("lattitude", latitude);
                    intent.putExtra("longitude", longitude);
                    intent.putExtra("latLng", latlong);
                    intent.putExtra("templeDetails", templeDetailsForUpdatePage);
                    startActivity(intent);
                }

              //  myAddress.setText(strReturnedAddress.toString());
            }

            else {
                progressBar1.setVisibility(View.GONE);
                Toast.makeText(this,"No Address",Toast.LENGTH_LONG).show();
              //  myAddress.setText("No Address returned!");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            progressBar1.setVisibility(View.GONE);
            Toast.makeText(this,"Cannot get Address  "+ e.toString() ,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.googlemap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        if (!manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {

            Toast.makeText(this,"Turn on Your GPS",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (id == R.id.action_search) {
            try {
                Intent intent = new PlaceAutocomplete.IntentBuilder
                        (PlaceAutocomplete.MODE_OVERLAY)
                        .setBoundsBias(BOUNDS_MOUNTAIN_VIEW)
                        .build(MapsActivity.this);
                startActivityForResult(intent, REQUEST_SELECT_PLACE);
            } catch (GooglePlayServicesRepairableException |
                    GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
            return true;
        }

        else if(id == R.id.action_ok)
        {
            progressBar1.setVisibility(View.VISIBLE);
            geoLocationData();
        }

        else if(id == R.id.action_current)
        {


                getCurrentLocation();

        }

        else if (id == R.id.map_normal)
        {
             mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        else if (id == R.id.map_satelite)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else if (id == R.id.map_hybird)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

    return super.onOptionsItemSelected(item);
}

    @Override
    public void onPlaceSelected(Place place) {
       // Log.i(LOG_TAG, "Place Selected: " + place.getName());
       /* locationTextView.setText(getString(R.string.formatted_place_data, place
                .getName(), place.getAddress(), place.getPhoneNumber(), place
                .getWebsiteUri(), place.getRating(), place.getId())); */

        //   Toast.makeText(this,"place Selected",Toast.LENGTH_LONG).show();

        if (!TextUtils.isEmpty(place.getAttributions())) {
            // attributionsTextView.setText(Html.fromHtml(place.getAttributions().toString()));
        }
    }

    @Override
    public void onError(Status status) {
       // Log.e(LOG_TAG, "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                place = PlaceAutocomplete.getPlace(this, data);
                this.onPlaceSelected(place);

                latitude= place.getLatLng().latitude;
                longitude =place.getLatLng().longitude;
                moveMap();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                this.onError(status);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

           if(redirectPage.equals("userTempleUpdate"))
           {
               Intent intent = getIntent();
               templeName = intent.getStringExtra("userTemple");
               latitude = intent.getDoubleExtra("latUser", 9.92520007);
               longitude = intent.getDoubleExtra("longUser", 78.1197751);
               Toast.makeText(this, latitude+ " coming "+longitude,Toast.LENGTH_LONG).show();
               LocationByLatLngUserUpdate();

           }
           else {
               getCurrentLocation();
           }
    }



            @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {


        latitude =latLng.latitude;
        longitude= latLng.longitude;

        moveMap();

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        latitude =latLng.latitude;
        longitude= latLng.longitude;

        moveMap();

    }

   @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


            private void checkConnection()
            {
                Network network =new Network();
                if (!network.isOnline(MapsActivity.this))
                {
                    Intent intent = new Intent(this,ConnectionError.class);
                    startActivity(intent);
                }

            }
            String loginUserNm,loginUsermail;
            private void checkIsLogin() {
                SharedPreferences userdetails = getApplicationContext().getSharedPreferences("Login", 0);

                loginUserNm = userdetails.getString("name", null);
                loginUsermail = userdetails.getString("email", null);
            }




            private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

            Toast.makeText(getApplicationContext(), "This App Needs a Permission", Toast.LENGTH_LONG).show();
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ACCESS_FINE_PERMISSIONS);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ACCESS_COARSE_PERMISSIONS);

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_ACCESS_FINE_PERMISSIONS) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }


        if (requestCode == REQUEST_CODE_ACCESS_COARSE_PERMISSIONS) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }



            private void checkForGooglePlayService() {
                GoogleApiAvailability api = GoogleApiAvailability.getInstance();
                int code = api.isGooglePlayServicesAvailable(this);
                if (code == ConnectionResult.SUCCESS) {
                    // Toast.makeText(this,"Google play service available",Toast.LENGTH_LONG).show();
                } else {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this);
                    alertDialog.setTitle("Google Play Services Unavailable");
                    alertDialog.setMessage("You need to download Google Play Services in order to use this part of the application");

                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    });

                    alertDialog.show();
                }
            }

            private void buildAlertMessageNoGps() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                dialog.cancel();
                                finish();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }




   }
