package com.avs.meyyunarvom;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.avs.UTRecycCard.ItemObject;
import com.avs.UTRecycCard.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserTemple extends AppCompatActivity {

    private LinearLayoutManager lLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertemple_main);

        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(UserTemple.this);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_viewut);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(UserTemple.this, rowListItem);
        rView.setAdapter(rcAdapter);
    }


    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("United States", R.drawable.bg1));
        allItems.add(new ItemObject("Canada", R.drawable.bg2));
        allItems.add(new ItemObject("United Kingdom", R.drawable.bg3));
        allItems.add(new ItemObject("Germany", R.drawable.bg4));
        allItems.add(new ItemObject("Sweden", R.drawable.dialog_bg));

        return allItems;
    }
}
