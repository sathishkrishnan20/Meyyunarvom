package com.avs.meyyunarvom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

public class ViewPoem extends AppCompatActivity implements View.OnClickListener
{

    private GestureDetector mGesture;
    static final int SWIPE_MIN_DISTANCE = 120;
    static final int SWIPE_THRESHOLD_VELOCITY = 200;


    private TextView title, content;
    private Button prevButton,nextButton;

    private int TRACK = 0;
    private JSONObject jsonObject;
    private JSONArray result;

    int poemDataLength=0;
    String titleStr, contentStr;
    private final String GET_URL = com.avs.db.URL.url + "/getPoem.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_poem);
        checkConnection();
        mGesture = new GestureDetector(this, mOnGesture);

        title =(TextView) findViewById(R.id.title_for_poem);
        content=(TextView) findViewById(R.id.content_for_poem);

        prevButton =(Button)findViewById(R.id.buttonPrev_forpoem);
        nextButton=(Button)findViewById(R.id.buttonNext_forpoem);

        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        getPoemsFromDB();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_for_addpoem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), AddPoem.class);
               // intent.putExtra("redirectPage","temple");
                startActivity(intent);
            }
        });


    }

    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }


    private void checkConnection()
    {
        Network network =new Network();
        if (!network.isOnline(ViewPoem.this))
        {
            Intent intent = new Intent(ViewPoem.this,ConnectionError.class);
            startActivity(intent);
        }

    }


    public void getPoemsFromDB() {
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
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
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
            poemDataLength = result.length();
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

            titleStr =  templeData.getString("title");
            contentStr = templeData.getString("content");
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
               title.setText(titleStr);
               content.setText(contentStr);

             }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error Occured While Loading the data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {

        if(v == nextButton){
            if(TRACK==poemDataLength-1)
            {
                Toast.makeText(this,"You Reached a limit", Toast.LENGTH_SHORT).show();
            }
            moveNext();
        }
        if(v== prevButton){
            if(TRACK==0)
            {
                Toast.makeText(this,"You Reached a limit", Toast.LENGTH_SHORT).show();
            }
            movePrevious();
        }
    }

    private void resetFields()
    {
        title.setText("");
        content.setText("");

    }

    private void moveNext(){


        if(TRACK < poemDataLength-1){
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

                if(TRACK==poemDataLength-1)
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







}
