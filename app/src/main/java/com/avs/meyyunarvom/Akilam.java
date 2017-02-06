package com.avs.meyyunarvom;

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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avs.db.AkilathirattuDAO;
import com.avs.db.MeyyunarvomDB;
import fr.ganfra.materialspinner.MaterialSpinner;
import java.util.ArrayList;

public class Akilam extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdapterView.OnItemSelectedListener ,View.OnClickListener{


    private GestureDetector mGesture;
    static final int SWIPE_MIN_DISTANCE = 120;
    static final int SWIPE_THRESHOLD_VELOCITY = 200;


    private int track =1;

    private TextView akilamTitle,akilamContent, pageNo;
    private MaterialSpinner spin;



    ArrayAdapter<String> akilamTitleArraySpinner;

   // String [] titleArray ={"kappu", "vaippu", "kkkkk"};

    ArrayList<String> arrayList =new ArrayList<String>();

    private int dropDownDay= 1;

    private EditText seacrchText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akilam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGesture = new GestureDetector(this, mOnGesture);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        akilamTitle =(TextView)findViewById(R.id.akilam_titleid1);
        akilamContent =(TextView)findViewById(R.id.akilamcontentid);
        pageNo =(TextView)findViewById(R.id.page_no);

        seacrchText = (EditText)findViewById(R.id.seacrchText);



        spin =(MaterialSpinner)findViewById(R.id.spinnerakilam123);

        spin.setOnItemSelectedListener(this);

        MeyyunarvomDB db = new MeyyunarvomDB(getApplicationContext());
        AkilathirattuDAO dao = db.getAkilamContent(track);
        akilamContent.setText(dao.getContent());
        akilamTitle.setText(dao.getTitle());
        String page="PageNo :"+track;
        pageNo.setText(page);


        arrayList.add("காப்பு");
        arrayList.add("நூல் சுருக்கம்");
        arrayList.add("அவையடக்கம்");
        arrayList.add("தெய்வம் பராவல்");
        arrayList.add("அடியெடுத்தருளல்");
        arrayList.add("நூற்பயன்");
        arrayList.add("தெட்சணா புதுமை");
        arrayList.add("சீமையின் இயல்பு");
        arrayList.add("தர்மநீதம்");
        arrayList.add("தெய்வநீதம்");
        arrayList.add("பெண்கள் நிலைமை");
        addTitleInSpinner();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.akilam, menu);
        return true;
    }

     public void searchByNumber()
     {
            track =  Integer.parseInt(seacrchText.getText().toString().trim());
            getContentByTrack();
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        int id = item.getItemId();





        if (id == R.id.first) {

            dropDownDay=1;
            arrayList.clear();
            akilamTitleArraySpinner.clear();
            arrayList.add("காப்பு");
            arrayList.add("நூல் சுருக்கம்");
            arrayList.add("அவையடக்கம்");
            arrayList.add("தெய்வம் பராவல்");
            arrayList.add("அடியெடுத்தருளல்");
            arrayList.add("நூற்பயன்");
            arrayList.add("தெட்சணா புதுமை");
            arrayList.add("சீமையின் இயல்பு");
            arrayList.add("தர்மநீதம்");
            arrayList.add("தெய்வநீதம்");
            arrayList.add("பெண்கள் நிலைமை");

            addTitleInSpinner();

            // Handle the camera action
        } else if (id == R.id.second)
        {
            dropDownDay=2;
            arrayList.clear();
            akilamTitleArraySpinner.clear();
            arrayList.add("சாதி வளமை");
            arrayList.add("கயிலை வளமை");
            arrayList.add("அகில வளமை");
            arrayList.add("நீடியயுகம்");
            arrayList.add("சதுரயுகம் - குண்டோமசாலி பாடு");
            arrayList.add("தேவர்கள் வேண்டுகோள்");
            arrayList.add("நெடியயுகம்");
            arrayList.add("கிரேதாயுகம்");
            arrayList.add("கந்தன் அவதாரம்");
            arrayList.add("வீரவாகு தேவர் தூது");
            arrayList.add("சூரன் பாடு");
            arrayList.add("இரணியன் பாடு");


            addTitleInSpinner();



        } else if (id == R.id.third)
        {

            dropDownDay =3;
            arrayList.clear();
            akilamTitleArraySpinner.clear();


            arrayList.add("திரேதாயுகம் - இராவணன் பாடு");
            arrayList.add("இராவணன் வரம் வேண்டல்");
            arrayList.add("இராவணன் கொடுமை");
            arrayList.add("தேவர்கள் முறையம்");
            arrayList.add("இராமாவதாரம்");
            arrayList.add("குறுமுனி சாபம்");
            arrayList.add("சீதா கல்யாணம்");
            arrayList.add("ஸ்ரீ ராமர் வனவாசம்");
            arrayList.add("அனுமன் தூது");
            arrayList.add("இராவணன் பாடு");
            addTitleInSpinner();

        } else if (id == R.id.fourth) {

            dropDownDay=4;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("துவாபரயுகம்");
            arrayList.add("தேவர் முறையம்    ");
            arrayList.add("தேவர் வேண்டுதலுக்கு இரங்கல்");
            arrayList.add("கண்ணன் அவதாரம்");
            arrayList.add("கஞ்சன் பாடு");
            arrayList.add("ருக்மணி கல்யாணம்");
            arrayList.add("பாண்டவர் வரலாறு");
            arrayList.add("பாண்டவர் வனவாசம்");
            arrayList.add("துரியோதனன் பாடு");
            arrayList.add("கர்ணனுக்கு முக்தியருளல்");

            addTitleInSpinner();

        } else if (id == R.id.fifth) {
            dropDownDay=5;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("சப்தகன்னியரும் சாண்றோர் பிறப்பும்");
            arrayList.add("அம்மைமார் தவம்");
            arrayList.add("திருமால் அமுதளித்தல்");
            arrayList.add("காளி வரவு");
            arrayList.add("சாண்றோர்க்கு நாமம் அருளல்");
            arrayList.add("சரஸ்வதி தாலாட்டு");
            arrayList.add("கற்பகத்தரு");
            arrayList.add("பத்திரத்தாள் பெற்ற மக்கள்");
            arrayList.add("சான்றோர் பெருமை");
            arrayList.add("சாண்றோர் திருமணம்");


            addTitleInSpinner();

        } else if (id == R.id.sixth) {
            dropDownDay=6;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("கலியன் பிறப்பு");
            arrayList.add("கலிச்சி தோற்றம்");
            arrayList.add("கலியன் கேட்ட வரங்கள்");
            arrayList.add("அகத்தீசர் பிறப்பு");
            arrayList.add("கலியரசன் சத்தியம்");
            arrayList.add("சக்கராயுதம் பணமாதல்");
            arrayList.add("கலியுகம்");
            arrayList.add("நல்லவை மறைதல்");
            arrayList.add("கலைமுனி ஞானமுனி தவசு");
            arrayList.add("போகமுனி தவசு");
            arrayList.add("மலைப்புதுமை");
            arrayList.add("சலந்தவசு");



            addTitleInSpinner();

        }
        else if (id == R.id.seventh) {
            dropDownDay=7;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("நந்திவரவு");
            arrayList.add("பேய்ப்பிறப்பு");
            arrayList.add("சோழன் வினை");
            arrayList.add("ஸ்ரீரங்கம் விட்டு சுவாமி அனந்தபுரம் ஏகல்");
            arrayList.add("பிரம்ம ரிஷிகள்");
            arrayList.add("எக்காள துர்க்கை");
            arrayList.add("வானோர்கு அருளல்");
            arrayList.add("பசுவும் பெண்ணும்");
            arrayList.add("சேத்திர பாலனுக்கு சொன்னது");
            arrayList.add("புலச்சிக்கு அருளியது");

            addTitleInSpinner();

        }
        else if (id == R.id.eighth) {
            dropDownDay=8;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("கலியரசன் தவம்");
            arrayList.add("மருமக்கள் வழி");
            arrayList.add("வெண்ணீசன்");
            arrayList.add("கலிநீசன் சாபம்");
            arrayList.add("கலிநீசன் கொடுமை");
            arrayList.add("ஸ்ரீபத்மநாபர் கலியரசனுக்கு உபதேசித்தல்");
            arrayList.add("அனந்தபுரம் விட்டு சுவாமி திருசெந்தூர் ஏகல்");
            arrayList.add("தேவர் முறையம்");
            arrayList.add("அம்மை உமை இரங்கள்");
            arrayList.add("திருமாள் திருகயிலை ஏகல்");
            arrayList.add("சிவன் திருமாள் ஐக்கியம்");
            arrayList.add("தேவலோகத்தார் மனுப்பிறப்பு");

            addTitleInSpinner();

        }
        else if (id == R.id.ninth) {

            dropDownDay=9;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("சம்பூர்ணதேவன்-பரதேவதை தவசு");
            arrayList.add("எமலேகத்தார் மனுபிறப்பு");
            arrayList.add("செர்கலோகத்தார் மனுபிறப்பு");
            arrayList.add("பிரம்மலோகத்தார் மனுபிறப்பு");
            arrayList.add("வைகுண்டலோகத்தார் மனுபிறப்பு");
            arrayList.add("சிவலோகத்தார் மனுபிறப்பு");
            arrayList.add("பரலோகத்தார் மனுபிறப்பு");
            arrayList.add("திருமால் சிவனிடம் மேல்நடப்புரைத்தல்");
            arrayList.add("தர்மயுக சிறப்பு");
            arrayList.add("அம்மைமார் தவநிறைவு");
            arrayList.add("காளி சிறை");
            arrayList.add("மகாலெட்சுமி மகரமாதல்");

            addTitleInSpinner();

        }

        else if (id == R.id.tenth) {
            dropDownDay=10;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("சம்பூரணதேவன் பரதேவதை மனுபிறப்பு");
            arrayList.add("ஆத்ம ஜெயம்");
            arrayList.add("திருமாள் கலைமுனிக்கு அருளல்");
            arrayList.add("திருநடன உலா");
            arrayList.add("வேறு");
            arrayList.add("திருவாசகம் 1");
            arrayList.add("வேதவியாசர் முன்னாகமம் கூறல்");
            arrayList.add("கலைமுனி ஞானமுனி வரவு");
            arrayList.add("சம்பூரணதேவனுக்கு நற்பேறு அருளல்");
            arrayList.add("வைகுண்டர் உதயம்");
            arrayList.add("விஞ்சையருளல்");
            arrayList.add("தாலாட்டு");

            addTitleInSpinner();

        }

        else if (id == R.id.eleventh) {
            dropDownDay=11;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("வேறு");
            arrayList.add("பள்ளிஉணர்த்தல்");
            arrayList.add("தேவாரம்");
            arrayList.add("திருவாசகம் - 2");
            arrayList.add("வைகுண்டர் முருகனுக்கு அருளல்");
            arrayList.add("வானோர்கள் வைகுண்டரை போற்றுதல்");
            arrayList.add("செந்தூர் விட்டு தெட்சணம் எழுந்தருளல்");
            arrayList.add("பிசாசுகளில் பணிவிடையை மறுத்தல்");
            arrayList.add("வெண்மை உயிரினங்கள் பணிதல்");
            arrayList.add("கடல் உயிரிணங்கள் பணிதல்");
            arrayList.add("நல்ல குலதெய்வங்கள் மறைதல்");




            addTitleInSpinner();

        }

        else if (id == R.id.twelth) {
            dropDownDay=12;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("வைகுண்டர் பகவதிக்கு அருளல்");
            arrayList.add("வைகுண்டர் மணவைப்பதி ஏகல்");
            arrayList.add("கலைமுனி தவஇடம் சிறப்பு கூறல்");
            arrayList.add("கலைமுனி வேதவியாசர் பிறப்புரைத்தல்");
            arrayList.add("வேதவியாசர் முக்காலம் உரைத்தல்");
            arrayList.add("தெட்சணாபூமி வளம்");
            arrayList.add("திருவாசகம் - 3");
            arrayList.add("அய்யா வைகுண்டர் திருதவம்");
            arrayList.add("அய்யா மக்களுக்கு அருளல்");
            arrayList.add("திருவாசகம் - 4");

            addTitleInSpinner();

        }

        else if (id == R.id.thirteenth) {
            dropDownDay=13;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("பேய்களை எரித்தல்");
            arrayList.add("மந்திரவாதிகள் விசையடக்குதல்");
            arrayList.add("குறத்தி வருகை");
            arrayList.add("வைகுண்டர் தர்மம் கூறல்");
            arrayList.add("கலியனுக்கு பூவண்டர் உபதேசம்");
            arrayList.add("சுவாமி பாற்கடல் நோக்கி ஒடுதல்");
            arrayList.add("முட்டப்பதி விஞ்சை 1");
            arrayList.add("கலிசோதனை");
            arrayList.add("சுவாமி பொருமையுரைத்தல்");
            arrayList.add("நீசன் கொடுமை");
            arrayList.add("அய்யா சாண்றேர்க்கு அருளல்");

            addTitleInSpinner();

        }

        else if (id == R.id.fourteenth) {
            dropDownDay=14;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("சுவாமி கைது");
            arrayList.add("சுசீந்திரத்தில் அய்யா");
            arrayList.add("அய்யாவை அனந்தபுரம் கொண்டு செல்லல்");
            arrayList.add("சிங்காரதோப்பில் சுவாமி சிறை");
            arrayList.add("கடுவாய் சோதனை");
            arrayList.add("வைகுண்டர் விடுதலை");
            arrayList.add("துவையல் பந்தி-வாகைபதி தவசு");
            arrayList.add("முட்டப்பதி தவசு");
            arrayList.add("அய்யாவை முட்டப்பதி அழைத்தல்");
            arrayList.add("அய்யா முட்டப்பதி ஏகல்");
            arrayList.add("முட்டப்பதி விஞ்சை - 2");

            addTitleInSpinner();


        }

        else if (id == R.id.fifteenth) {
            dropDownDay=15;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("அய்யா தோப்புபதி திரும்புதல்");
            arrayList.add("சாண்றோர் பக்தி");
            arrayList.add("சத்தகன்னிமார் வருகை");
            arrayList.add("திருக்கல்யாணம்");
            arrayList.add("திருமுகூர்த்தம்");
            arrayList.add("சிந்து");
            arrayList.add("சோபனம்");
            arrayList.add("அய்யா திருநாள் இகனை நடத்துதல்");
            arrayList.add("அம்மைமார்களுக்கு மதலை ஈதல்");
            arrayList.add("அய்யா மூலகுண்டப்பதி எழுந்தருளல்");
            arrayList.add("அய்யா கன்னி பகவதி பதியேகல்");

            addTitleInSpinner();

        }

        else if (id == R.id.sixteenth) {
            dropDownDay=16;
            arrayList.clear();
            akilamTitleArraySpinner.clear();

            arrayList.add("பகவதிக்கு சச்சிதானந்த காட்சி காண அருள்");
            arrayList.add("பகவதியம்மை காண்டம் படித்தல்");
            arrayList.add("சிவன் ரோமரிஷிக்கு அருளியது");
            arrayList.add("பகவதி திருக்கல்யாணம்");
            arrayList.add("பார்வதி திருக்கல்யாணம்");
            arrayList.add("மோகினி வேடம்");
            arrayList.add("மண்டைகாட்டம்மை காண்டம் படித்தல்");
            arrayList.add("மண்டைகாட்டம்மை திருக்கல்யாணம்");
            arrayList.add("வள்ளி திருக்கல்யாணம்");
            arrayList.add("அய்யா தாங்கள்களுக்கு எமுந்தருளல்");
            arrayList.add("பூமடந்தையம்மை திருக்கல்யாணம்");


            addTitleInSpinner();

        }

        else if (id == R.id.seventeenth) {
            dropDownDay=17;
            arrayList.clear();
            akilamTitleArraySpinner.clear();
            arrayList.add("அய்யா வைகுண்டம் எழுந்தருளல்");
            arrayList.add("சாண்றோர் கோவில் அமைத்தல்");
            arrayList.add("கலைமுனி ஞானமுனி சாட்சியம்");
            arrayList.add("அய்யா சாண்றோர்க்கு இரங்கல்");
            arrayList.add("பொய் வேசம்");
            arrayList.add("நல்லவை முழித்தல்");
            arrayList.add("நடுத்தீர்ப்பு");
            arrayList.add("கலி முடிவு");
            arrayList.add("தர்மயுகம்");
            arrayList.add("பட்டாபிஷேகம்");
            arrayList.add("அய்யா அருள்வாக்கு");


            addTitleInSpinner();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addTitleInSpinner()
    {
        akilamTitleArraySpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        akilamTitleArraySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(akilamTitleArraySpinner);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

        if(dropDownDay ==1)
        {

            if(position ==0)
            {   track = 1;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 2;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 4;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 5;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 6;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 7;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 8;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 9;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 10;
                getContentByTrack();
            }
            else if(position ==9)
            {
                track = 11;
                getContentByTrack();
            }
            else if(position ==10)
            {
                track = 13;
                getContentByTrack();
            }

        }

        else if(dropDownDay ==2)
        {

            if(position ==0)
            {   track = 14;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 15;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 16;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 17;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 19;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 20;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 21;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 25;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 27;
                getContentByTrack();
            }
            else if(position ==9)
            {
                track = 28;
                getContentByTrack();
            }
            else if(position ==10)
            {
                track = 29;
                getContentByTrack();
            }
            else if(position ==11)
            {
                track = 31;
                getContentByTrack();
            }

        }


        else if(dropDownDay ==3)
        {

            if(position ==0)
            {   track = 32;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 33;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 35;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 36;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 37;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 39;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 42;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 43;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 46;
                getContentByTrack();
            }
            else if(position ==9)
            {
                track = 47;
                getContentByTrack();
            }
        }


        else if(dropDownDay ==4)
        {

            if(position ==0)
            {   track = 49;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 57;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 61;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 62;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 66;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 69;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 70;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 71;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 73;
                getContentByTrack();
            }
            else if(position ==9)
            {
                track = 77;
                getContentByTrack();
            }
        }



        else if(dropDownDay ==5)
        {

            if(position ==0)
            {   track = 79;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 83;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 84;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 85;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 87;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 89;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 90;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 92;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 94;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 95;
                getContentByTrack();
            }
        }

        else if(dropDownDay ==6)
        {

            if(position ==0)
            {   track = 101;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 108;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 111;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 114;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 115;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 117;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 118;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 119;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 122;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 124;
                getContentByTrack();
            }
            else if(position ==10)
            {
                track = 125;
                getContentByTrack();
            }
            else if(position ==11)
            {
                track = 126;
                getContentByTrack();
            }
        }

        else if(dropDownDay ==7)
        {

            if(position ==0)
            {   track = 129;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 131;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 134;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 137;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 139;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 141;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 144;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 145;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 146;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 148;
                getContentByTrack();
            }
        }

        else if(dropDownDay ==8)
        {

            if(position ==0)
            {   track = 149;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 151;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 152;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 155;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 157;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 161;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 165;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 168;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 169;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 173;
                getContentByTrack();
            }
            else if(position ==10)
            {
                track = 183;
                getContentByTrack();
            }
            else if(position ==11)
            {
                track = 186;
                getContentByTrack();
            }
        }


        else if(dropDownDay ==9)
        {

            if(position ==0)
            {   track = 187;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 189;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 190;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 191;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 198;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 201;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 204;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 205;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 206;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 209;
                getContentByTrack();
            }
            else if(position ==10)
            {
                track = 220;
                getContentByTrack();
            }
            else if(position ==11)
            {
                track = 223;
                getContentByTrack();
            }
        }


        else if(dropDownDay ==10)
        {

            if(position ==0)
            {   track = 226;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 236;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 239;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 245;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 255;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 256;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 261;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 265;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 266;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 274;
                getContentByTrack();
            }
            else if(position ==10)
            {
                track = 278;
                getContentByTrack();
            }
            else if(position ==11)
            {
                track = 297;
                getContentByTrack();
            }
        }

        else if(dropDownDay ==11)
        {

            if(position ==0)
            {   track = 300;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 301;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 305;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 311;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 314;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 317;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 319;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 322;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 323;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 324;
                getContentByTrack();
            }
            else if(position ==10)
            {
                track = 325;
                getContentByTrack();
            }
        }

        else if(dropDownDay ==12)
        {

            if(position ==0)
            {   track = 326;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 327;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 328;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 329;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 330;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 332;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 335;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 338;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 341;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 344;
                getContentByTrack();
            }
        }

        else if(dropDownDay ==13)
        {

            if(position ==0)
            {   track = 345;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 355;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 356;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 361;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 368;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 372;
                getContentByTrack();
            } else if(position ==6)
            {
                track = 374;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 376;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 376;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 379;
                getContentByTrack();
            }
            else if(position ==10)
            {
                track = 381;
                getContentByTrack();
            }
        }


        else if(dropDownDay ==14)
        {

            if(position ==0)
            {   track = 382;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 383;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 385;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 386;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 388;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 394;
                getContentByTrack();
            }
            else if(position ==6)
            {
                track = 397;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 403;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 409;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 414;
                getContentByTrack();
            }
            else if(position ==10)
            {
                track = 420;
                getContentByTrack();
            }
        }

        else if(dropDownDay ==15)
        {

            if(position ==0)
            {   track = 424;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 425;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 429;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 439;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 443;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 444;
                getContentByTrack();
            }
            else if(position ==6)
            {
                track = 447;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 448;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 452;
                getContentByTrack();
            }

            else if(position ==9)
            {
                track = 461;
                getContentByTrack();
            }
            else if(position ==10)
            {
                track = 462;
                getContentByTrack();
            }

        }


        else if(dropDownDay ==16)
        {

            if(position ==0)
            {   track = 470;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 474;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 478;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 482;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 483;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 486;
                getContentByTrack();
            }
            else if(position ==6)
            {
                track = 488;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 490;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 491;
                getContentByTrack();
            }
            else if(position ==9)
            {
                track = 494;
                getContentByTrack();
            }

            else if(position ==10)
            {
                track = 496;
                getContentByTrack();
            }

        }

        else if(dropDownDay ==17)
        {

            if(position ==0)
            {   track = 497;
                //Toast.makeText(this,position,Toast.LENGTH_SHORT).show();
                getContentByTrack();
            }

            else if(position ==1)
            {
                track = 501;
                getContentByTrack();
            }

            else if(position ==2)
            {
                track = 503;
                getContentByTrack();
            }
            else if(position ==3)
            {
                track = 505;
                getContentByTrack();
            }
            else if(position ==4)
            {
                track = 507;
                getContentByTrack();
            }
            else if(position ==5)
            {
                track = 508;
                getContentByTrack();
            }
            else if(position ==6)
            {
                track = 510;
                getContentByTrack();
            }
            else if(position ==7)
            {
                track = 521;
                getContentByTrack();
            }
            else if(position ==8)
            {
                track = 522;
                getContentByTrack();
            }
            else if(position ==9)
            {
                track = 528;
                getContentByTrack();
            }

            else if(position ==10)
            {
                track = 529;
                getContentByTrack();
            }

        }

    }



    private void getContentByTrack()
    {
        try {
            MeyyunarvomDB db = new MeyyunarvomDB(getApplicationContext());
            AkilathirattuDAO dao = db.getAkilamContent(track);
            akilamContent.setText(dao.getContent());
            akilamTitle.setText(dao.getTitle());
            String page = "Page :" + track;
            pageNo.setText(page);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error in "+String.valueOf(track),Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    @Override
    public void onClick(View v) {

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


                if (track <533 ) {
                    track = track + 1;
                }
                MeyyunarvomDB db = new MeyyunarvomDB(getApplicationContext());
                AkilathirattuDAO dao = db.getAkilamContent(track);
                akilamContent.setText(dao.getContent());
                akilamTitle.setText(dao.getTitle());
                String page="Page :"+track;
                pageNo.setText(page);

                 ///   Toast.makeText(getApplicationContext(), "Left to Right Swap Performed", Toast.LENGTH_SHORT).show();

                return true;

            }
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

               if (track > 1) {
                    track = track - 1;
                }
                MeyyunarvomDB db = new MeyyunarvomDB(getApplicationContext());
                AkilathirattuDAO dao = db.getAkilamContent(track);
                akilamContent.setText(dao.getContent());
                akilamTitle.setText(dao.getTitle());
                String page="Page :"+track;
                pageNo.setText(page);
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

