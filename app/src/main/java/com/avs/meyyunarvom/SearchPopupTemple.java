package com.avs.meyyunarvom;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchPopupTemple extends AppCompatActivity {

    private ListView lv;
    ArrayAdapter<String> adapter;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_popup_temple);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        Intent intent =getIntent();
        final ArrayList arrayList =intent.getStringArrayListExtra("placeList");

        lv = (ListView) findViewById(R.id.list_viewex);
        inputSearch = (EditText) findViewById(R.id.inputSearch);


        // Adding items to listview
        adapter = new ArrayAdapter(this, R.layout.list_item, R.id.product_name, arrayList);
        lv.setAdapter(adapter);


        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) ((width * 0.9)), (int) (height * 0.9));
       // Toast.makeText(this, String.valueOf(width) + " " + String.valueOf(height), Toast.LENGTH_LONG).show();


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SearchPopupTemple.this.adapter.getFilter().filter(cs);
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
                    intent1.putExtra("track",position);
                    startActivity(intent1);


                }
            });



    }

}
