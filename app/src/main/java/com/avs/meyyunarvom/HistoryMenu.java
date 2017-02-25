package com.avs.meyyunarvom;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HistoryMenu extends AppCompatActivity implements
        WorldHistory.OnFragmentInteractionListener,
        HistoryKannimar.OnFragmentInteractionListener,
        HistorySivayimar.OnFragmentInteractionListener,
        HistorySudalai.OnFragmentInteractionListener,
        HistoryKali.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_menu);



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_history);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_history);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        HistoryMenu.ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WorldHistory(), "உலகம்");
        adapter.addFragment(new HistoryKannimar(), "கன்னிமார்");
        adapter.addFragment(new HistorySivayimar(), "சிவாயிமார்");
        adapter.addFragment(new HistorySudalai(), "சுடலை");
        adapter.addFragment(new HistoryKali(), "காளி");
        viewPager.setAdapter(adapter);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}
