package com.avs.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sathish on 11/17/2016.
 */

public class SessionManagement
{
    public void setPreferences(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("Androidwarriors", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();

    }



    public  String getPreferences(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("Androidwarriors",	Context.MODE_PRIVATE);
        String position = prefs.getString(key, "");
        return position;
    }
}
