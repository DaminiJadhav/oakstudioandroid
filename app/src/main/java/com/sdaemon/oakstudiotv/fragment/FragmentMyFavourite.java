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
import com.sdaemon.oakstudiotv.model.MyFavouriteCategory_list;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyFavourite extends Fragment {
    private View rootView;
    private TabLayout myFavouritetabLayout;
    private ViewPager tab_Favouriteviewpager;
    private Context context;
    Utilities utilities;
    private ArrayList<MyFavouriteCategory_list> myFavouriteCategory_lists = new ArrayList<>();

    public FragmentMyFavourite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fragment_my_favourite, container, false);
    //    setData();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        utilities = Utilities.getInstance(context);
        setData();
    }

    private void setData() {
        myFavouriteCategory_lists.add(new MyFavouriteCategory_list("MY MOVIE"));
        myFavouriteCategory_lists.add(new MyFavouriteCategory_list("MY TV SHOWS"));
        myFavouriteCategory_lists.add(new MyFavouriteCategory_list("MY WISHLIST"));
        myFavouriteCategory_lists.add(new MyFavouriteCategory_list("DOWNLOADED"));


        tab_Favouriteviewpager = (ViewPager) rootView.findViewById(R.id.tab_Favouriteviewpager);
        myFavouritetabLayout = (TabLayout) rootView.findViewById(R.id.myFavouritetabLayout);

        if (tab_Favouriteviewpager != null) {
            setupViewPager(tab_Favouriteviewpager);
        }
        myFavouritetabLayout.setupWithViewPager(tab_Favouriteviewpager);
        int limit = myFavouriteCategory_lists.size();
        tab_Favouriteviewpager.setOffscreenPageLimit(limit);

    }

    private void setupViewPager(ViewPager tab_Favouriteviewpager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        int count = myFavouriteCategory_lists.size();
        //for (int i = 0; i < count; i++) {

        if (!utilities.isNetworkAvailable()) {
          // adapter.addFrag(new FragmentDownloaded(), myFavouriteCategory_lists.get(3).categoryName);
            adapter.addFrag(new FragmentOffLineDownload(), myFavouriteCategory_lists.get(3).categoryName);

          }
           else {
              adapter.addFrag(new FragmentMyFavouriteMovies(), myFavouriteCategory_lists.get(0).categoryName);
              adapter.addFrag(new FragmentMyFavouriteMovies(), myFavouriteCategory_lists.get(1).categoryName);
              adapter.addFrag(new FragmentMyFavouriteWishList(), myFavouriteCategory_lists.get(2).categoryName);
            //  adapter.addFrag(new FragmentDownloaded(), myFavouriteCategory_lists.get(3).categoryName);
              adapter.addFrag(new FragmentOffLineDownload(), myFavouriteCategory_lists.get(3).categoryName);
            }
       // }
        tab_Favouriteviewpager.setAdapter(adapter);
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
