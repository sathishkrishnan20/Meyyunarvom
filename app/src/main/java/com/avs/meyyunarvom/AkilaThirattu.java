package com.avs.meyyunarvom;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;




public class AkilaThirattu extends AppCompatActivity // implements View.OnTouchListener
 {


    private Activity activity;
    static final int MIN_DISTANCE = 100;
    private float downX, downY, upX, upY;


    TextView title;
    TextView content;


    Button next;
    Button previous;


    Cursor c1;
    SQLiteDatabase db;

    float x1, x2;
    float y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akila_thirattu);


        title = (TextView) findViewById(R.id.titleid);
        content = (TextView) findViewById(R.id.contentid);
        next = (Button) findViewById(R.id.akilnextbtn);
        previous = (Button) findViewById(R.id.akilpreviousbtn);


        db = openOrCreateDatabase("MeyyDBAkil", Context.MODE_PRIVATE, null);


        db.execSQL("drop table if exists akilathirattu2");
        db.execSQL("CREATE TABLE IF NOT EXISTS akilathirattu2(rollno int(4),title text,content text);");


        db.execSQL("INSERT INTO akilathirattu2 VALUES(1,'வைகுண்டரின் வாழ்க்கை வரலாறு','இந்த உலகத்தையே படைத்து நிறைந்த ஆதி நாராயணர் , திருச்செந்தூர் பால்க்கடலில் மாசி மாதம் இருபதாம் தேதி அன்னை மகாலெட்ச்சுமியை மகரமாக வளரச் செய்து மாயாதி மாயன் பத்தாவது அவதாரமான வைகுண்ட அவதாரம் எடுத்து வந்தார்\n" +
                "\n" +
                "அய்யா வைகுண்டர் அவதாரம் எடுத்து வருவதற்க்கு முன்னுள்ள ஒன்பது அவதாரத்திலும் ஆதி நாராயணரை சுமந்த பொன்மேனிக் கூடான சம்பூரணதேவனை அய்யா வைகுண்டர் அமர்ந்திருக்கும் சுவாமித்தோப்பு என்னும் ஊரில் உள்ள வெயிளாள் அன்னைக்கு மகனாகப் பிறவிச் செய்தார்\n" +
                "\n" +
                "சம்பூரணதேவன் பிறந்து 29 வயது நடந்துக் கொண்டிருக்கும் போது அவருக்கு உடல்நிலை சரியில்லாமல் ஆனது , இந்த நேரத்தில் தான் ஆதி நாராயணர் அன்னை வெயிளாள் கனவில் வந்து , உன் மகனை திருச்செந்தூரில் இருக்கும் முருகன் கோவிலுக்கு மாசி மாத திருவிழாவின் போது அழைத்துவா என்றுச் சொல்லி சென்றார்\n" +
                "\n" +
                "ஆதி நாராயணர் சொன்னதுப் போலவே வெயிளாள் அன்னையும் சம்பூரண தேவனை திருச்செந்தூர்க்கு அலைத்துச் சென்றார் , அவருடன் அந்த ஊர் மக்களும் சென்றார்கள்\n" +
                "\n" +
                "சென்றவர்கள் அனைவரும் ஓர் இடத்தில் \n" +
                "சடவாரினார்கள் , அப்போது ஆதி நாராயணர் , கலைமுனி , ஞானமுனி யான இரண்டுப் பேரையும் அழைத்துச் சொல்லுவார் , என் மகன் அங்கே சடவாரிக் கொண்டிருக்கிறான் அவனை நீங்கள்ச் சென்று அழைத்து வாருங்கள் என்றார்\n" +
                "\n" +
                "சென்றவர்கள் இரண்டு பேரும் சம்பூரண தேவனை இருபுறம் நின்று அழைத்து வந்தார்கள்\n" +
                "\n" +
                "மகன் செல்வதைக் கண்ட அன்னை வெயிளாள் மற்றும் உடன் வந்தவர்கள்\n" +
                "சொல்லுகிறார்கள் , என்ன ஆச்சர்யம் இதுவரை இவரை நாம் அழைத்து வந்தோம் ஆனால் இப்போது அவரே எழுந்துச் செல்கிறார் என்றார்கள்\n" +
                "\n" +
                "ஏன் என்றால் அவர்களுக்குத் தெரியாது இரண்டு முனிமார்கள் தூக்கிச் செல்வது\n" +
                "\n');");
        db.execSQL("INSERT INTO akilathirattu2 VALUES(2,'Akilathirattu','akilathirattu ayyavazhi kudumbam enbathu 180 varudankalukku munnal eluthappattathu');");
        db.execSQL("INSERT INTO akilathirattu2 VALUES(3,'Akila','ayyavazhi kudumbam enbathu 180 varudankalukku munnal eluthappattathu');");
        db.execSQL("INSERT INTO akilathirattu2 VALUES(4,'Akila',' kudumbam enbathu 180 varudankalukku munnal eluthappattathu');");
        db.execSQL("INSERT INTO akilathirattu2 VALUES(5,'Akila varalaru','enbathu 180 varudankalukku munnal eluthappattathu');");
        db.execSQL("INSERT INTO akilathirattu2 VALUES(6,'alaru','akilathirattu enbathu 180 varudankalukku munnal eluthappattathu');");
        db.execSQL("INSERT INTO akilathirattu2 VALUES(7,'Aki varalaru','akilathirattu munnal eluthappattathu');");
        db.execSQL("INSERT INTO akilathirattu2 VALUES(8,'Akilat alaru','akilathirattu enbathu 180 varudankalukku');");


        c1 = db.rawQuery("Select * from akilathirattu2", null);

        if (c1.moveToNext()) {
            title.setText(c1.getString(0) + ". " + c1.getString(1).toString());
            content.setText(c1.getString(2).toString());
        }


        next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //sqLitedb();

                if (c1.moveToNext()) {
                    title.setText(c1.getString(0) + ". " + c1.getString(1).toString());
                    content.setText(c1.getString(2).toString());

                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c1.moveToPrevious()) {
                    title.setText(c1.getString(0) + ". " + c1.getString(1).toString());
                    content.setText(c1.getString(2).toString());
                }
            }
        });


    }

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }
        @Override
        public boolean onTouchEvent(MotionEvent touchevent) {
            switch (touchevent.getAction())
            {
                // when user first touches the screen we get x and y coordinate
                case MotionEvent.ACTION_DOWN:
                {
                    x1 = touchevent.getX();
                    y1 = touchevent.getY();
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    x2 = touchevent.getX();
                    y2 = touchevent.getY();

                    //if left to right sweep event on screen
                    if (x1 < x2)
                    {
                        Toast.makeText(this, "Left to Right Swap Performed", Toast.LENGTH_SHORT).show();
                    }

                    // if right to left sweep event on screen
                    if (x1 > x2)
                    {
                        Toast.makeText(this, "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                    }

                    // if UP to Down sweep event on screen
                    if (y1 < y2)
                    {
                        Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                    }

                    //if Down to UP sweep event on screen
                    if (y1 > y2)
                    {
                        Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
            }
            return false;



        }











public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
           return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            Intent intent=new Intent(this, GotoAkilathirattu.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}//end of class
