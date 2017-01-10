package com.avs.meyyunarvom;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.avs.db.AkilathirattuDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sathish on 11/10/2016.
 */
public class MeyyDB extends Activity{
    Cursor c1;

    SQLiteDatabase db;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void createDb() {

        db = openOrCreateDatabase("MeyyDB", Context.MODE_PRIVATE, null);
        String akilathirattuDB = "create table akilathirattu(rollno int primary key not null unique,title text not null,content text not null);";
        db.execSQL(akilathirattuDB);


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

        db.execSQL("INSERT INTO akilathirattu2 VALUES(9,'Akila','180 varudankalukku');");


    }


}



