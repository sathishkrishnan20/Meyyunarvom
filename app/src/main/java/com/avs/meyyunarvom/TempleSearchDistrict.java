package com.avs.meyyunarvom;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TempleSearchDistrict extends AppCompatActivity {

    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;


    ArrayList templeDistrictList =new ArrayList();


    private final String GET_URL = com.avs.db.URL.url + "/getTempleDistrict.php";
    private int TRACK = 0;
    private JSONObject jsonObject;
    private JSONArray result;

    int distLength =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_search_district);

        getDistrictsFromDB();

        lv = (ListView) findViewById(R.id.list_viewexdist);
        inputSearch = (EditText) findViewById(R.id.inputSearchdist);


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                TempleSearchDistrict.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(),String.valueOf(position)+arrayList.get(position),Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getApplicationContext(), Temples.class);

                String districtName = String.valueOf(templeDistrictList.get(position));
                intent1.putExtra("districtName",districtName);
                intent1.putExtra("isClicked",true);
                startActivity(intent1);
            }
        });

    }

    public void getDistrictsFromDB() {
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
              //  progressBar1.setVisibility(View.GONE);
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


    private void showJSON(String response)
    {
        try
        {
            jsonObject = new JSONObject(response);
            result=jsonObject.getJSONArray("result");

            distLength = result.length();
            getSetdistData();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }


    private void getSetdistData()
    {
        try {

            templeDistrictList.clear();
            for (int templeCount = 0; templeCount < distLength; templeCount++) {
                JSONObject DistrictData = result.getJSONObject(templeCount);
                templeDistrictList.add(DistrictData.getString("tdist"));
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter(this, R.layout.list_item_dist, R.id.dist_name, templeDistrictList);
        lv.setAdapter(adapter);
    }

}
