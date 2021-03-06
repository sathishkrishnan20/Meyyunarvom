package com.avs.meyyunarvom;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ArulnoolMenu extends AppCompatActivity implements
                                    ArulFragUchipadippu.OnFragmentInteractionListener,
                                    ArulFragUkapadippu.OnFragmentInteractionListener,
                                    ArulFragVazhapadippu.OnFragmentInteractionListener,
                                     ArulNoolFragThanaNiraivuVasakam.OnFragmentInteractionListener,
                                    ArulFragPathiram.OnFragmentInteractionListener,
                                    ArulFragSivathandam.OnFragmentInteractionListener,
                                    ArulFragThingalPatham.OnFragmentInteractionListener,
                                    ArulFragChattuNeetolai.OnFragmentInteractionListener,
                                    ArulFragKalyanaVazhthu.OnFragmentInteractionListener,
                                    ArulFragPanchaThevar.OnFragmentInteractionListener,
                                    ArulFragSapthaKanniPadal.OnFragmentInteractionListener,
                                    ArulFragNadutheerpuUla.OnFragmentInteractionListener


{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arulnool_menu);

        viewPager = (ViewPager) findViewById(R.id.viewpager_arul);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_arul);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ArulFragUchipadippu(), "உச்சிப் படிப்பு");
        adapter.addFragment(new ArulFragUkapadippu(), "உகப் படிப்பு");
        adapter.addFragment(new ArulFragVazhapadippu(), "வாழப் படிப்பு");
        adapter.addFragment(new ArulNoolFragThanaNiraivuVasakam(), "தானநிறைவு வாசகம்");
        adapter.addFragment(new ArulFragChattuNeetolai(), "சாட்டு நீட்டோலை");
        adapter.addFragment(new ArulFragPathiram(), "பத்திரம்");
        adapter.addFragment(new ArulFragSivathandam(), "சிவகாண்ட அதிகார பத்திரம்");
        adapter.addFragment(new ArulFragSapthaKanniPadal(), "சப்த கன்னிமார் பாடல்");
        adapter.addFragment(new ArulFragThingalPatham(), "திங்கள் பதம்");
        adapter.addFragment(new ArulFragPanchaThevar(), "பஞ்சதேவர் உற்பத்தி");
        adapter.addFragment(new ArulFragNadutheerpuUla(), "நடுத்தீர்வை உலா");
        adapter.addFragment(new ArulFragKalyanaVazhthu(), "கல்யாண வாழ்த்து");

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