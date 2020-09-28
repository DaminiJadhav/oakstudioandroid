package com.sdaemon.oakstudiotv.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;

import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.CategoryFilterDTO;
import com.sdaemon.oakstudiotv.model.Category;
import com.sdaemon.oakstudiotv.model.ContentInfoFilter;
import com.sdaemon.oakstudiotv.retrofit_utils.RetrofitUtils;
import com.sdaemon.oakstudiotv.retrofit_utils.restUtils.RestCallInterface;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.Utilities;
import com.sdaemon.oakstudiotv.viewpageradapter.MyAdapter;
import com.sdaemon.oakstudiotv.viewpageradapter.MyPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAll_Movies extends Fragment  {

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager tabViewPager,viewPager;
    private Retrofit retrofit;
    private Context mContext;
    private Integer images[]={R.drawable.banner,R.drawable.view,R.drawable.view1};
    private ArrayList<Integer> imagesArray = new ArrayList<Integer>();
    private ArrayList<String> imagesViewpagerArray = new ArrayList<String>();
    List<ContentInfoFilter> contentInfoFilter=new ArrayList<>();
    private static int currentPage = 0;
    private ArrayList<Category> movieCategory_lists = new ArrayList<>();
    private Toolbar toolbar;
    int position;
    private LinearLayout llMessageMain, llLoading,llscrollImages;
    private TextView tvmoviename,tvRetry, tvMessage,tvcomedy,tvyear,tvreview,tvstudio,tvfeature,tvparam,tvrating;
    Button btnretry;
    private ImageView ivapplyfilter;
    private CircleIndicator circleIndicator;
    AppSession appSession;
    int useid;
    String uniqueid;
    MyPager myPager;
    MyAdapter myAdapter;
    Category category;
    Utilities utilities;
    ProgressBar progressBar,network_error_progressbar;
    Boolean ismovie=true;
    int MovieSeason;
    SwipeRefreshLayout swipeRefreshLayout;


    public FragmentAll_Movies() {
        // Required empty public constructor
    }


    public FragmentAll_Movies(Boolean ismovie) {
        this.ismovie=ismovie;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_all__movies, container, false);
        mContext=getContext();
        appSession=AppSession.getInstance(mContext);
        try {
            if (appSession!=null) {
                useid = appSession.getUserDTO().getResult().getCustomerId();
                uniqueid = appSession.getUserDTO().getResult().getUniqueIdentifire();
                Log.i("User Id",""+useid);
                Log.i("Unique Id",""+uniqueid);
                Log.i("User first name",""+appSession.getUserDTO().getResult().getFirstName());


            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //  initialize();

//        scrollViewNewImages();
        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        utilities = Utilities.getInstance(mContext);
        initialize();
//        setViewPager();

        getHomeContent();
//        showProgress();
        getValue();
    }


    private void initialize() {
        llMessageMain = rootView.findViewById(R.id.ll_message_main);
        llLoading = rootView.findViewById(R.id.ll_loading);
        progressBar = rootView.findViewById(R.id.progress_allmovie);
//        swipeRefreshLayout=rootView.findViewById(R.id.swiperefres_moviepage);
        network_error_progressbar = rootView.findViewById(R.id.progress_bar_network_error);

        progressBar.setVisibility(View.VISIBLE);

        llscrollImages=rootView.findViewById(R.id.ll_horizontalscrollview);
        viewPager=rootView.findViewById(R.id.ll_viewpager);
        circleIndicator=rootView.findViewById(R.id.circle);
//        ivmovie=rootView.findViewById(R.id.iv_movie);
//        tvmoviename=rootView.findViewById(R.id.tv_bannerMovieName);
        tvMessage = rootView.findViewById(R.id.tv_message);
        tvMessage.setMovementMethod(new ScrollingMovementMethod());
        tvRetry = rootView.findViewById(R.id.tv_retry);
        btnretry = rootView.findViewById(R.id.btn_retry);

        tvcomedy=rootView.findViewById(R.id.txt_comedy);
        tvyear=rootView.findViewById(R.id.txt_year);
        tvreview=rootView.findViewById(R.id.txt_review);
        tvstudio=rootView.findViewById(R.id.txt_studio);
        tvfeature=rootView.findViewById(R.id.txt_feature);
//        tvparam=rootView.findViewById(R.id.txt_param);
        tvrating=rootView.findViewById(R.id.txt_rating);
        ivapplyfilter=rootView.findViewById(R.id.iv_applyfilter);

        appSession.setMovieType(ismovie);


//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                doRefresh();
//                swipeRefreshLayout.setRefreshing(false);
//
//            }
//        });

        tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                network_error_progressbar.setVisibility(View.VISIBLE);
             getValue();
            }
        });

        btnretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Retry", Toast.LENGTH_SHORT).show();
                network_error_progressbar.setVisibility(View.VISIBLE);
                getValue();
            }
        });

      //  retrofit = RetrofitUtils.getRetrofitWithoutHeader();
//        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
//        Drawable drawable = ratingBar.getProgressDrawable();
//        drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);



        setMovieFilter();


        }

        public void getHomeContent(){
//            Call<CategoryFilterDTO> call = RetroClient.sdaemonApi().getHomePageContent(13335,"25250892-17ab-47d8-b809-a6494f69a342");
            Call<CategoryFilterDTO> call = RetroClient.sdaemonApi().getHomePageContent(useid,uniqueid);

            call.enqueue(new Callback<CategoryFilterDTO>() {
                @Override
                public void onResponse(Call<CategoryFilterDTO> call, Response<CategoryFilterDTO> response) {
                    if (response.isSuccessful()){
                        progressBar.setVisibility(GONE);

//                        Toast.makeText(mContext,"Home Page",Toast.LENGTH_LONG).show();
                        CategoryFilterDTO categoryFilterDTO=response.body();
                        contentInfoFilter=categoryFilterDTO.getContentDetail().getContentInfoFilters();


                        for (int i=0;i<contentInfoFilter.size();i++){

                            imagesViewpagerArray.add(contentInfoFilter.get(i).getBannerImage());
//                            tvmoviename.setText(contentInfoFilter.get(i).getContentTitle());
                        }
                        setViewPager();

                        Log.i("Home Page slider",""+imagesViewpagerArray);


                    }
                }

                @Override
                public void onFailure(Call<CategoryFilterDTO> call, Throwable t) {
                    Log.i("Home Page slider",""+t.getMessage());

//                    Toast.makeText(mContext,"Home Page  :" +t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });
        }


    public void getValue(){
        if (!utilities.isNetworkAvailable()) {
            tvMessage.setText( getResources().getString(R.string.network_error));
            showNoInternet();
//            network_error_progressbar.setVisibility(GONE);
        }
        else {
            retrofit = RetrofitUtils.getRetrofitWithoutHeader();
            RestCallInterface restInterface = retrofit.create(RestCallInterface.class);
            //Calling method to get whether report
            Call<ArrayList<Category>> call = restInterface.getAllCategories(ismovie);
            call.enqueue(new Callback<ArrayList<Category>>() {
                @Override
                public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                    llLoading.setVisibility(GONE);
                    if (response.isSuccessful()) {
                        network_error_progressbar.setVisibility(GONE);
                        try {
                            if (response.code() == 200) {

                                Log.i("TVshows or Movie",""+ismovie);
                                llMessageMain.setVisibility(GONE);

                                tabViewPager = (ViewPager) rootView.findViewById(R.id.tab_viewpager);
                                tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);

                                if (tabViewPager != null) {
                                   // setupViewPager(tabViewPager);

//                                    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
                                    ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

                                    int count = response.body().size();

                                    for (int i = 0; i < count; i++) {

                                        movieCategory_lists.addAll(response.body());
                                        if (ismovie==true) {
                                            adapter.addFrag(new FragmentCategorySdaemonApi(response.body().get(i).getCategoryID().toString(),1), response.body().get(i).getCategoryDescription());

                                        }else {
//                                            appSession.logout();
//                                            Log.i("Filter clear position",""+appSession.getYearIDposition());
//                                            Log.i("Filter clear id ",""+appSession.getYearID());
//                                            textviewInvisible();
                                            adapter.addFrag(new FragmentCategorySdaemonApi(response.body().get(i).getCategoryID().toString(),2), response.body().get(i).getCategoryDescription());

                                        }
//                                         adapter.addFrag1(response.body().get(i).getCategoryDescription());
                                    }
                                    tabViewPager.setAdapter(adapter);

                                   // movieCategory_lists.add   (new MovieCategory_list("ALL MOVIES"));
                                }
                                tabLayout.setupWithViewPager(tabViewPager);
                                int limit = response.body().size();
                                tabViewPager.setOffscreenPageLimit(limit);

                                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {

                                        int id = movieCategory_lists.get(tab.getPosition()).getCategoryID();
                                        Toast.makeText(getActivity(),"Category :"+id,Toast.LENGTH_LONG).show();



//                                        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
//                                        movieCategory_lists.addAll(response.body());
//                                        adapter.addFrag(new FragmentCategorySdaemonApi(response.body().get(tab.getPosition()).getCategoryID().toString()), response.body().get(tab.getPosition()).getCategoryDescription());
//                                        tabViewPager.setAdapter(adapter);




//                                        if (id == 1) {
//                                             FragmentCategorySdaemonApi.newInstance(tab.getPosition(), String.valueOf(movieCategory_lists.get(tab.getPosition()).getCategoryID()));
//                                              FragmentCategorySdaemonApi fragmentCategorySdaemonApi=new FragmentCategorySdaemonApi(movieCategory_lists.get(tab.getPosition()).getCategoryID().toString());
//                                        }
//                                        if (id == 2) {
//                                            FragmentCategorySdaemonApi.newInstance(tab.getPosition(), String.valueOf(movieCategory_lists.get(tab.getPosition()).getCategoryID()));
//
//                                        }


                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });

                            } else {
                                tvMessage.setText("" + response.message());
                                showMessage();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                   /* genreList_adapter = new FeaturesList_Adapter(getActivity(), response.body());
                    featuresListView.setAdapter(genreList_adapter);*/


                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(mContext, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }

    }


    public void refreshPatient(Category category) {
        this.category=category;
//        FragmentCategorySdaemonApi(category.getCategoryID(),category.getCategoryDescription()));
    }

/*
    private void setupViewPager(ViewPager tabViewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        int count = movieCategory_lists.size();
        for (int i = 0; i < count; i++) {
            adapter.addFrag(new FragmentCategory(), movieCategory_lists.get(i).categoryName);
        }
        tabViewPager.setAdapter(adapter);
        movieCategory_lists.add(new MovieCategory_list("ALL MOVIES"));
        movieCategory_lists.add(new MovieCategory_list("POPULAR"));
        movieCategory_lists.add(new MovieCategory_list("TRENDING"));
        movieCategory_lists.add(new MovieCategory_list("DRAMA"));
        movieCategory_lists.add(new MovieCategory_list("RECENT"));

    }*/
       public static Fragment newInstance() {

        Fragment f = new Fragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
     //  return FragmentCategorySdaemonApi.newInstance(position + 1, String.valueOf(movieCategory_lists.get(position).getCategoryID()));
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

        public void addFrag1(String title) {
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setViewPager(){

//           for (int i=0;i<images.length;i++){
//               imagesArray.add(images[i]);
//           }
           if (viewPager!=null){
//               viewPager.setAdapter(new MyAdapter(getActivity(),imagesArray));
               viewPager.setAdapter(new MyAdapter(getActivity(),imagesViewpagerArray,contentInfoFilter));
               circleIndicator.setViewPager(viewPager);
           }

           final Handler handler=new Handler();
           final Runnable runnable=new Runnable() {
               @Override
               public void run() {
                   if (currentPage==imagesViewpagerArray.size()){
                       currentPage=0;
                   }
                   viewPager.setCurrentItem(currentPage++,true);
               }
           };
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },3000,3000);


        circleIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                        currentPage=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

//           myPager=new MyPager(getActivity());
//            if (viewPager!=null){
//                viewPager.setAdapter(myPager);
//                circleIndicator.setViewPager(viewPager);
//            }


    }



//    private void scrollViewNewImages(){
//
//     for (Integer image:images){
//         if (llscrollImages!=null){
//             llscrollImages.addView(getImageView(image));
//         }
//     }
//    }

//    private View getImageView(Integer image){
//          ivmovie=new ImageView(getActivity());
//           LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//           ivmovie.setLayoutParams(lp);
//           ivmovie.setImageResource(image);
//           return ivmovie;
//
//    }
    void showProgress() {
        llMessageMain.setVisibility(View.VISIBLE);
        llLoading.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(GONE);
        btnretry.setVisibility(GONE);

//        tvRetry.setVisibility(GONE);
    }
    void showError() {
        llMessageMain.setVisibility(View.VISIBLE);
        llLoading.setVisibility(GONE);
        tvMessage.setVisibility(View.VISIBLE);
        btnretry.setVisibility(View.VISIBLE);

//        tvRetry.setVisibility(View.VISIBLE);


    }
    void showMessage() {
        llMessageMain.setVisibility(View.VISIBLE);
        llLoading.setVisibility(GONE);
        tvMessage.setVisibility(View.VISIBLE);
        btnretry.setVisibility(GONE);

//        tvRetry.setVisibility(GONE);


    }
    void showNoInternet() {
        llMessageMain.setVisibility(View.VISIBLE);
        tvMessage.setVisibility(View.VISIBLE);
        btnretry.setVisibility(View.VISIBLE);
        llLoading.setVisibility(View.GONE);
        network_error_progressbar.setVisibility(GONE);
//        tvRetry.setVisibility(View.VISIBLE);

    }

    public void setMovieFilter(){

        if(textviewInvisible()) {

            if (ismovie == true) {

                if (appSession.getGenreID() == "" && appSession.getYearID() == "" && appSession.getReviewID() == "" && appSession.getStudioID() == "" && appSession.getFeatureID() == "" && appSession.getRatingID() == "") {
                    textviewInvisible();
                } else if (appSession.getGenreID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvcomedy.setVisibility(View.VISIBLE);
                    tvcomedy.setText(appSession.getGenreID());

                }
                if (appSession.getYearID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvyear.setVisibility(View.VISIBLE);
                    tvyear.setText(appSession.getYearID());
                }
                if (appSession.getReviewID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvreview.setVisibility(View.VISIBLE);
                    tvreview.setText(appSession.getReviewID());
                }
                if (appSession.getStudioID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvstudio.setVisibility(View.VISIBLE);
                    tvstudio.setText(appSession.getStudioID());
                }
                if (appSession.getFeatureID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvfeature.setVisibility(View.VISIBLE);
                    tvfeature.setText(appSession.getFeatureID());
                }
                if (appSession.getRatingID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvrating.setVisibility(View.VISIBLE);
                    tvrating.setText(appSession.getRatingID());
                }
            } else {

                if (appSession.getTVshowGenreID() == "" && appSession.getTVshowYearID() == "" && appSession.getTVshowReviewID() == "" && appSession.getTVshowStudioID() == "" && appSession.getTVshowFeatureID() == "" && appSession.getTVshowRatingID() == "") {
                    textviewInvisible();
                } else if (appSession.getTVshowGenreID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvcomedy.setVisibility(View.VISIBLE);
                    tvcomedy.setText(appSession.getTVshowGenreID());

                }
                if (appSession.getTVshowYearID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvyear.setVisibility(View.VISIBLE);
                    tvyear.setText(appSession.getTVshowYearID());
                }
                if (appSession.getTVshowReviewID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvreview.setVisibility(View.VISIBLE);
                    tvreview.setText(appSession.getTVshowReviewID());
                }
                if (appSession.getTVshowStudioID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvstudio.setVisibility(View.VISIBLE);
                    tvstudio.setText(appSession.getTVshowStudioID());
                }
                if (appSession.getTVshowFeatureID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvfeature.setVisibility(View.VISIBLE);
                    tvfeature.setText(appSession.getTVshowFeatureID());
                }
                if (appSession.getTVshowRatingID() != "") {
                    ivapplyfilter.setVisibility(View.VISIBLE);
                    tvrating.setVisibility(View.VISIBLE);
                    tvrating.setText(appSession.getTVshowRatingID());
                }

            }
        }
    }

    boolean textviewInvisible(){
        ivapplyfilter.setVisibility(View.GONE);
        tvcomedy.setVisibility(View.GONE);
        tvyear.setVisibility(View.GONE);
        tvreview.setVisibility(View.GONE);
        tvstudio.setVisibility(View.GONE);
        tvfeature.setVisibility(View.GONE);
        tvrating.setVisibility(View.GONE);

        return true;
    }

    public void doRefresh(){
           getHomeContent();
           getValue();

    }

}