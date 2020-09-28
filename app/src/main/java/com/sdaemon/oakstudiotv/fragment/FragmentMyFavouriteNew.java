package com.sdaemon.oakstudiotv.fragment;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sdaemon.oakstudiotv.R;

import androidx.fragment.app.FragmentManager;

import com.sdaemon.oakstudiotv.utils.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyFavouriteNew extends Fragment {
    private View rootView;
    private Context context;
    Utilities utilities;
    TabLayout tabLayout;
    ViewPager viewPager;
    ProgressBar progressBar;
    LinearLayout lloffline;
    public FragmentMyFavouriteNew() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fragment_my_favourite, container, false);
//        lloffline=rootView.findViewById(R.id.ll_offline);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        utilities = Utilities.getInstance(context);
        initView();

    }

    private void initView() {
        tabLayout = (TabLayout) rootView.findViewById(R.id.myFavouritetabLayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.tab_Favouriteviewpager);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progres_favourite);
//        progressBar.setVisibility(View.VISIBLE);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.my_movie)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.my_tv_show)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.my_wishlist)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.download)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //   MyAdapter adapter = new MyAdapter(context, getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        MyAdapter adapter = new MyAdapter(context, getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

//                if (tab.getPosition()==3){
//                    lloffline.setVisibility(View.GONE);
//                }else {
//                    lloffline.setVisibility(View.VISIBLE);
//                }

//                if (!utilities.isNetworkAvailable()){
//                    if (tab.getPosition()==3){
//                        lloffline.setVisibility(View.GONE);
//                    }else {
//                        lloffline.setVisibility(View.VISIBLE);
//                    }
//                }else {
//                    lloffline.setVisibility(View.GONE);
//                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (!utilities.isNetworkAvailable()) {
//             lloffline.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(2);
//            Log.i("NetworkAvailable :","NO");
        }else {
//            lloffline.setVisibility(View.GONE);
//            Log.i("NetworkAvailable :","YES");
        }

    }

    public class MyAdapter extends FragmentPagerAdapter {
        private Context myContext;
        int totalTabs;

        public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);
            myContext = context;
            this.totalTabs = totalTabs;
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    FragmentMyFavouriteMovies fragmentMyFavouriteMovies = new FragmentMyFavouriteMovies();
                    return fragmentMyFavouriteMovies;

                case 1:
                    FragmentMyFavouriteTVShow fragmentMyFavouriteMovies1 = new FragmentMyFavouriteTVShow();
                    return fragmentMyFavouriteMovies1;

                case 2:
                    FragmentMyFavouriteWishList fragmentMyFavouriteWishList = new FragmentMyFavouriteWishList();
                    return fragmentMyFavouriteWishList;

                case 3:
                    FragmentOffLineDownload fragmentOffLineDownload = new FragmentOffLineDownload();
                    return fragmentOffLineDownload;

                default:
                    return null;
            }
        }// this counts total number of tabs

        @Override
        public int getCount() {
            return totalTabs;
        }
    }


}
