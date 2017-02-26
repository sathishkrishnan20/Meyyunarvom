package com.avs.meyyunarvom;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.avs.db.MeyyunarvomDB;


/**
 * Created by Sathish on 10/8/2016.
 */

public class SplashScreen extends Activity
{
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);


         new MeyyunarvomDB(getApplicationContext());

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
