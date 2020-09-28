package com.sdaemon.oakstudiotv.fragment;


import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.RecentCategory_list;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRecentMovies extends Fragment {
    private View rootView;
    private TabLayout recenttabLayout;
    private ViewPager tabViewPager;
    private Context mContext;
    private ArrayList<RecentCategory_list> recentCategory_lists = new ArrayList<>();

    public FragmentRecentMovies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_recent_movies, container, false);
        initialize();
        return rootView;
    }

    private void initialize() {
        /*RatingBar ratingBar = (RatingBar)rootView.findViewById(R.id.ratingBar);
        Drawable drawable = ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);

*/

        recentCategory_lists.add(new RecentCategory_list(getResources().getString(R.string.recently_view)));
        recentCategory_lists.add(new RecentCategory_list(getResources().getString(R.string.recently_added)));


        tabViewPager = (ViewPager) rootView.findViewById(R.id.tab_viewpager);
        recenttabLayout = (TabLayout) rootView.findViewById(R.id.recenttabLayout);

        if (tabViewPager != null) {
            setupViewPager(tabViewPager);
        }
        recenttabLayout.setupWithViewPager(tabViewPager);
        int limit = recentCategory_lists.size();
        tabViewPager.setOffscreenPageLimit(limit);
    }

    private void setupViewPager(ViewPager tabViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        int count = recentCategory_lists.size();
        for (int i = 0; i < count; i++) {
            if (i==0){
                adapter.addFrag(new FragmentRecentlyViewed(), recentCategory_lists.get(i).categoryName);
            }
            if (i==1){
                adapter.addFrag(new FragmentRecentlyAdded(), recentCategory_lists.get(i).categoryName);

            }
        }
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
}
