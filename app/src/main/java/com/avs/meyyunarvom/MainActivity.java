package com.avs.meyyunarvom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity
      implements NavigationView.OnNavigationItemSelectedListener
{

    ImageButton history;
    ImageButton akilaThirattu;
    ImageButton doubts;
    ImageButton addTemple;
    ImageButton temples;
    ImageButton ayyavazhi;
    ImageButton ukapadippu;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    protected ViewFlipper mViewFlipperText;
    private Context mContext;
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
    Animation fadeIn, fadeOut,slideUp,slideDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        history=(ImageButton)findViewById(R.id.historyid);
        akilaThirattu=(ImageButton)findViewById(R.id.akilathirattuid);
        doubts=(ImageButton)findViewById(R.id.doubtid);
        addTemple=(ImageButton)findViewById(R.id.addtempleid);
        temples=(ImageButton)findViewById(R.id.templeid);

        ayyavazhi=(ImageButton)findViewById(R.id.ayyavazhiid);
       // ukapadippu=(ImageButton)findViewById(R.id.ukapadippuid);

        mContext = this;
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
        mViewFlipperText = (ViewFlipper) this.findViewById(R.id.view_flipperText);

        mViewFlipper.setAutoStart(true);
        mViewFlipperText.setAutoStart(true);
        fadeIn = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(mContext,R.anim.fade_out);
        slideUp = AnimationUtils.loadAnimation(mContext,R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(mContext,R.anim.slide_down);

        mViewFlipper.setInAnimation(fadeIn);
        mViewFlipper.setOutAnimation(fadeOut);
        mViewFlipperText.setInAnimation(slideUp);
        mViewFlipperText.setOutAnimation(slideDown);

        mViewFlipper.setFlipInterval(10000);
        mViewFlipperText.setFlipInterval(10000);
        mViewFlipper.startFlipping();
        mViewFlipperText.startFlipping();


        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                mViewFlipper.stopFlipping();
                mViewFlipperText.stopFlipping();
                detector.onTouchEvent(event);
                return true;
            }

        });


        mViewFlipperText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                mViewFlipper.stopFlipping();
                mViewFlipperText.stopFlipping();
                detector.onTouchEvent(event);
                return true;
            }

        });


        history.setOnClickListener(new View.OnClickListener() {
                                       @Override
         public void onClick(View view)
         {
             Intent intent=new Intent(getApplicationContext(), HistoryMenu.class);
             startActivity(intent);

         }
        }
        );

        akilaThirattu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), AkilathirattuMenu.class);
                startActivity(intent);
            }

        });
   /*     doubts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Answers.class);
                startActivity(intent);
            }
        });
*/

        addTemple.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), ViewPoem.class);
                startActivity(intent);

            }
        });

        temples.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), Temples.class);
                startActivity(intent);

            }
        });


        ayyavazhi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), AyyaVazhiMenu.class);
                startActivity(intent);

            }
        });
/*
        ukapadippu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(), Ukapadippu.class);
                startActivity(intent);

            }
        });

*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            SharedPreferences userdetails = getApplicationContext().getSharedPreferences("LoginAdmin", 0);
            SharedPreferences.Editor editor = userdetails.edit();
            if (userdetails.contains("isLoginAdmin")) {

                editor.remove("isLoginAdmin");
                editor.apply();
                boolean commit = editor.commit();

            }



            //super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {



        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        SQLiteDatabase db;

        int id = item.getItemId();

        if (id == R.id.admin)
        {
            Intent intent=new Intent(getApplicationContext(),AdminLogin.class);
            startActivity(intent);
        }

        else if (id == R.id.profile)
        {
            Intent intent=new Intent(getApplicationContext(),ProfilePage.class);
            startActivity(intent);
        }

        else if(id==R.id.profilelogin)
        {
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.about_us)
        {

        }
        else if (id == R.id.help_faq) {


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {

            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_out));

                mViewFlipperText.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_up));
                mViewFlipperText.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_down));
                mViewFlipper.showNext();
                mViewFlipperText.showNext();
                return true;
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_out));
                mViewFlipperText.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_up));
                mViewFlipperText.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_down));

                mViewFlipper.showPrevious();
                mViewFlipperText.showPrevious();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
}