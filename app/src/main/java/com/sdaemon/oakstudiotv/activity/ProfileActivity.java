package com.sdaemon.oakstudiotv.activity;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.fragment.FragmentAccountSetting;
import com.sdaemon.oakstudiotv.fragment.FragmentBalance_details;
import com.sdaemon.oakstudiotv.fragment.FragmentNotification;
import com.sdaemon.oakstudiotv.fragment.FragmentPayment_details;
import com.sdaemon.oakstudiotv.fragment.FragmentYourPlanAPI;
import com.sdaemon.oakstudiotv.model.Profile_categories;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager tabViewPager;
    private Context mContext;
    private ArrayList<Profile_categories> profile_categories = new ArrayList<>();
    private ImageView iv_tabback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialization();
    }



    private void initialization() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        profile_categories.add(new Profile_categories(""));
        profile_categories.add(new Profile_categories("ACCOUNT SETTING"));
        profile_categories.add(new Profile_categories("PAYMENT DETAILS"));
        profile_categories.add(new Profile_categories("BALANCE DETAILS"));
        profile_categories.add(new Profile_categories("YOUR PLAN"));
        tabViewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        if (tabViewPager != null) {
            setupViewPager(tabViewPager);
        }
        tabLayout.setupWithViewPager(tabViewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.setIcon(R.mipmap.bell);
        int limit = profile_categories.size();
        tabViewPager.setOffscreenPageLimit(limit);
    }

    private void setupViewPager(ViewPager tabViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        int count = profile_categories.size();
        // for (int i = 0; i < count; i++) {
        adapter.addFrag(new FragmentNotification(), profile_categories.get(0).profile_catName);
        adapter.addFrag(new FragmentAccountSetting(), profile_categories.get(1).profile_catName);
        adapter.addFrag(new FragmentPayment_details(""), profile_categories.get(2).profile_catName);
        adapter.addFrag(new FragmentBalance_details(), profile_categories.get(3).profile_catName);
      //  adapter.addFrag(new FragmentYourPlan(), profile_categories.get(4).profile_catName);
        adapter.addFrag(new FragmentYourPlanAPI(), profile_categories.get(4).profile_catName);
        //}
        tabViewPager.setAdapter(adapter);
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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        menu.findItem(R.id.action_like).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
        menu.findItem(R.id.action_tuneUp).setVisible(false);
        menu.findItem(R.id.action_person).setVisible(false);
        menu.findItem(R.id.action_done).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }
}
