package com.avs.meyyunarvom;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.view.View.VISIBLE;


public class ViewPoem extends AppCompatActivity
{

    private GestureDetector mGesture;
    static final int SWIPE_MIN_DISTANCE = 120;
    static final int SWIPE_THRESHOLD_VELOCITY = 200;


    private TextView title, content, postedBy;


    private int TRACK = 0;
    private JSONObject jsonObject;
    private JSONArray result;

    private ProgressBar progressBar1;

    int poemDataLength=0;
    String titleStr, contentStr, userDetailsStr;
    private final String GET_URL = com.avs.db.URL.url + "/getPoem.php";

    private RelativeLayout errorLayout;
    private ImageView errorImage;


    ScrollView scrollView;

    ArrayList<String> userUniqueArry =new ArrayList<String>();

    private MenuItem searchMenuItem;
    private SearchView mSearchView;
    ArrayAdapter adapter;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_poem);

        errorLayout = (RelativeLayout)findViewById(R.id.error_layout_poem);
        errorImage = (ImageView)findViewById(R.id.error_image_poem);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar_poem);
        mGesture = new GestureDetector(this, mOnGesture);
        title =(TextView) findViewById(R.id.title_for_poem);
        content=(TextView) findViewById(R.id.content_for_poem);
        postedBy=(TextView)findViewById(R.id.poem_added_by);
        lv = (ListView)findViewById(R.id.listViewPoem);
        lv.setVisibility(View.GONE);

        scrollView =(ScrollView)findViewById(R.id.scroll_poem);
        progressBar1.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.VISIBLE);
        errorImage.setVisibility(View.GONE);

        if(checkConnection()) {
            getPoemsFromDB();
/*            DbUtil db=new DbUtil();
            // JSONObject jsonObject =
            String s= db.getUrl(ViewPoem.this, GET_URL);
            Toast.makeText(this, "coming" + s, Toast.LENGTH_LONG).show();

                if(db.getUrl(ViewPoem.this, GET_URL))
                {
                    errorLayout.setVisibility(View.GONE);
                    errorImage.setVisibility(View.GONE);
                    Toast.makeText(this, "data is coming" + s, Toast.LENGTH_LONG).show();
                  //  showJSON(jsonObject.getString("data"));
                }
                else if(!db.getUrl(ViewPoem.this, GET_URL))
                {
                     progressBar1.setVisibility(View.GONE);
                     errorLayout.setVisibility(View.VISIBLE);
                     errorImage.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "error is coming" + s, Toast.LENGTH_LONG).show();

                }
*/
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_for_addpoem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), AddPoem.class);
               // intent.putExtra("redirectPage","temple");
                startActivity(intent);
            }
        });

        errorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), ViewPoem.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);

    }


    private boolean checkConnection()
    {
        Network network =new Network();
        if (!network.isOnline(ViewPoem.this))
        {
            errorImage.setVisibility(View.VISIBLE);
            progressBar1.setVisibility(View.GONE);
            return false;
        }
      else {
            return true;
        }
    }


    public void getPoemsFromDB() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        errorLayout.setVisibility(View.GONE);
                        errorImage.setVisibility(View.GONE);

                        showJSON(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar1.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
                errorImage.setVisibility(View.VISIBLE);
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        Toast.makeText(getApplicationContext(), "Please Check Your Network Connection", Toast.LENGTH_LONG).show();
                    }
                }

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
        errorLayout.setVisibility(View.GONE);
        errorImage.setVisibility(View.GONE);
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


            userDetailsStr= "Added By\n    "+    templeData.getString("name");
            userDetailsStr= userDetailsStr+"\n    "+templeData.getString("place");
            userDetailsStr= userDetailsStr+"\n    "+templeData.getString("updated_at");

            setTempleData();
        }
        catch (JSONException e)
        {
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setTempleData()
    {

        try {

            progressBar1.setVisibility(View.GONE);
               title.setText(titleStr);
               content.setText(contentStr);
               postedBy.setText(userDetailsStr);
               getUserUniqueNames();

             }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error Occured While Loading the data" +e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    private void getUserUniqueNames() {

        ArrayList<String> userArry =new ArrayList<String>();

        try {

            for(int userCount =0; userCount < result.length(); userCount++ ) {
                JSONObject userNameData = result.getJSONObject(userCount);
                userArry.add(userNameData.getString("name"));
            }

            for(String s : userArry) {
                if(!userUniqueArry.contains(s)) {
                    userUniqueArry.add(s);
                }
            }


        } catch (JSONException e)
        {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
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
            scrollView.fullScroll(ScrollView.FOCUS_UP);
            getTempleData();
        }
    }

    private void movePrevious(){

        if(TRACK > 0){
            TRACK--;
            resetFields();
            scrollView.fullScroll(ScrollView.FOCUS_UP);
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_poem_menu, menu);

        searchMenuItem = menu.findItem(R.id.action_search_poem);
        mSearchView = (SearchView) searchMenuItem.getActionView();

/*
        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                adapter = new ArrayAdapter<String>(Temples.this,android.R.layout.simple_list_item_1, searchArrayListWithoutCount);
                lv.setAdapter(adapter);
                lv.setVisibility(View.VISIBLE);
            }
        });

*/
        lv.setVisibility(View.VISIBLE);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String searchText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {

                // userUniqueArry.clear();
               // searchArrayListWithoutCount.clear();

               /* for(int i =0 ;i < userUniqueArry.size();i++){
                    if(templePlaceList.get(i).toString().contains(searchText)) {
                        search_result_arraylist.add(templePlaceList.get(i).toString());
                        searchArrayListWithoutCount.add(templePlaceListWithoutCount.get(i).toString());
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

                }
                */
                return false;
            }


        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int distCount =0; distCount <adapter.getCount();distCount++ )
                {
                    if(adapter.getItem(distCount).equals(adapter.getItem(position)))
                    {
                        String districtName= adapter.getItem(position).toString();
                        Toast.makeText(ViewPoem.this,districtName,Toast.LENGTH_SHORT).show();
                        lv.setVisibility(View.GONE);
                    }
                }
            }

        });



        return true;
    }

    private boolean restriction =true;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(restriction)
            return false;


        if(id ==R.id.action_search_poem)
        {
            mSearchView.setVisibility(VISIBLE);
            adapter = new ArrayAdapter<String>(ViewPoem.this,android.R.layout.simple_list_item_1, userUniqueArry);
            lv.setAdapter(adapter);
            //isSearchButonPressed =true;
            lv.setVisibility(View.VISIBLE);
        }



        return super.onOptionsItemSelected(item);
    }



















}
