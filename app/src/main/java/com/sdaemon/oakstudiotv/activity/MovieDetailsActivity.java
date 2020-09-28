package com.sdaemon.oakstudiotv.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.ContentDTO;
import com.sdaemon.oakstudiotv.dto.WishListDTO;
import com.sdaemon.oakstudiotv.fragment.FragmentCreators_Cast;
import com.sdaemon.oakstudiotv.fragment.FragmentMovieDetail_Info;
import com.sdaemon.oakstudiotv.fragment.FragmentUserReviews;
import com.sdaemon.oakstudiotv.model.CastAndCrewList;
import com.sdaemon.oakstudiotv.model.CategoiesList;
import com.sdaemon.oakstudiotv.model.ContentInfo;
import com.sdaemon.oakstudiotv.model.MovieDetails;
import com.sdaemon.oakstudiotv.model.MovieDetailsCategory;
import com.sdaemon.oakstudiotv.model.ReviewsList;
import com.sdaemon.oakstudiotv.model.Table;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.Methods;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailsActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager tabViewPager;
    private Context mContext;
    MenuItem item;
    boolean like1=true;
    TextView tvfeatureDescription,tvviewCount,tvview,tvYear,tvavgRating,tvMoviename,tvlike;
    private RatingBar ratingBar;
    ImageView ivposter;
    private LinearLayout ll_ratingView;
    public List<ContentInfo> list;
    public List<CategoiesList> infoList;
    public List<CastAndCrewList> castAndCrewLists;
    public List<ReviewsList> reviewsLists;
    public List<Table> table;
    AppSession appSession;
    ProgressBar progressBar;
    GifImageView gifImageView;



    private ArrayList<MovieDetailsCategory> movieDetailsCategories=new ArrayList<>();
    private ImageView ivtabback,like;
    public Retrofit retrofit;
    LinearLayout errorLayout;
    TextView txtError;
    Button btnRetry;

    int userId;
    int contentid,id;
    String likeicon,selectseason,selectepisode;
    String rating;
    int wishliststatus;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        tvfeatureDescription=findViewById(R.id.tv_featureDescription);
        tvviewCount=findViewById(R.id.tv_view);
        tvYear=findViewById(R.id.tv_year);
        ratingBar = (RatingBar) findViewById(R.id.rateBar);
        ivposter = (ImageView) findViewById(R.id.iv_posterImage);
        tvMoviename=findViewById(R.id.tv_bannerMovieName);
        tvlike=findViewById(R.id.tv_likes);

        errorLayout = findViewById(R.id.error_layout);
        txtError = findViewById(R.id.error_txt_cause);
        btnRetry = findViewById(R.id.error_btn_retry);

        progressBar=findViewById(R.id.progress_bar);
        gifImageView=findViewById(R.id.loading_gifimageview);

//        progressBar.setVisibility(View.VISIBLE);
        gifImageView.setVisibility(View.VISIBLE);



        tvview=findViewById(R.id.tv_views);
        tvavgRating=findViewById(R.id.movieRating);
        mContext=this;
        list=new ArrayList<>();
        infoList=new ArrayList<>();
        castAndCrewLists=new ArrayList<>();
        reviewsLists=new ArrayList<>();
        table=new ArrayList<>();



        try {
            appSession=AppSession.getInstance(mContext);
            if (appSession!=null){
                userId=appSession.getUserDTO().getResult().getCustomerId();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null) {
            contentid = bundle.getInt("KEY_CONTENTIDS");
            likeicon=bundle.getString("KEY_LIKEWISHLIST");
            selectseason=bundle.getString("KEY_SELECT_SEASON");
            selectepisode=bundle.getString("KEY_SELECT_EPISODE");

            tvviewCount.setMovementMethod(LinkMovementMethod.getInstance());




            Log.i("Like Icon String ",""+likeicon);
//            id=bundle.getInt("KEY_WISHLCONTID");

        }

        Log.i("Content Id 1",""+contentid);
        Log.i("Wish List Content Id 1",""+contentid);



        Drawable drawable = ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);



//        getAllMovieDetails();
//        getWishList();

//        pd = new ProgressDialog(MovieDetailsActivity.this, R.style.MyAlertDialogStyle);
//        pd.setIndeterminate(true);
//        pd.setMessage("Buffering video please wait...");
//        pd.setProgress(70);
//        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pd.setCancelable(true);

        new AsynctaskClass().execute("");


//        initialize();
    }

    private void initialize() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);




        movieDetailsCategories.add(new MovieDetailsCategory(getResources().getString(R.string.information)));
        movieDetailsCategories.add(new MovieDetailsCategory(getResources().getString(R.string.creator_cast)));
        movieDetailsCategories.add(new MovieDetailsCategory(getResources().getString(R.string.user_review)));


        tabViewPager = (ViewPager) findViewById(R.id.tab_viewpager);
       // ll_ratingView = (LinearLayout) findViewById(R.id.ll_ratingView);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

//        like=findViewById(R.id.action_like);


        if (tabViewPager != null) {
            setupViewPager(tabViewPager);
        }
        tabLayout.setupWithViewPager(tabViewPager);
        int limit = movieDetailsCategories.size();
        tabViewPager.setOffscreenPageLimit(limit);



        ivtabback=findViewById(R.id.iv_tabback);
        ivtabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    finish();



//                    loadFragment(new FragmentMyFavouriteNew());
//                MovieDetailsActivity.this.finish()

//                showFragment(new FragmentMyFavouriteWishList(),getClass().getName());

//                onDestroy();
//                FragmentManager.OnBackStackChangedListener changedListener=new FragmentManager.OnBackStackChangedListener() {
//                    @Override
//                    public void onBackStackChanged() {
//                        finish();
//                    }
//                };

//                onBackPressed();


//                Bundle bundle=new Bundle();
//
//                bundle.putString("LIKE","LIKE");
//
//                Intent intent=new Intent(mContext, MainActivity.class);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
//                finish();




//                finish();
//                if (likeicon!=null){
//                if (likeicon.equals("Like")) {
//                    FragmentMyFavouriteNew fragmentMyFavouriteNew=new FragmentMyFavouriteNew();
//                    loadFragment(fragmentMyFavouriteNew);
//                    finish();
//                LinearLayout linearLayout=findViewById(R.id.ll_moviedetail);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(android.R.id.content, new FragmentMyFavouriteNew()).addToBackStack(null).commit();
////                    MovieDetailsActivity.this.finish();
//                ((MovieDetailsActivity) mContext).finish();


//
//
//                    Log.i("Like Icon String", "Success");
//                  }
//                }else {
//                    Log.i("Like Icon String","Fail");
//                    finish();
//                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void getAllMovieDetails(){
//        progressBar.setVisibility(View.VISIBLE);

        Call<ContentDTO> call= RetroClient.sdaemonApi().getAllMovieDetails(contentid,userId);
        call.enqueue(new Callback<ContentDTO>() {
            @Override
            public void onResponse(Call<ContentDTO> call, Response<ContentDTO> response) {
                if (response.isSuccessful()){
                    String url="https://oakstudio.azurewebsites.net";

                    ContentDTO contentDTO=response.body();
//                    progressBar.setVisibility(View.GONE);
                    gifImageView.setVisibility(View.GONE);

//                    appSession.setMovieDetail(contentDTO);

//                    for (int i=0;i<contentDTO.getContentDetail().getContentInfos().size();i++){
//                        int moviedetailcontentid =contentDTO.getContentDetail().getContentInfos().get(i).getContentID();
//                        if (contentid==moviedetailcontentid){
//                            item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
//                            like1=false;
//                        }
//
//
//                    }

//                    Toast.makeText(MovieDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Log.i("MovieDetailsActivity","" +contentDTO);
//                    appSession.setMovieDetail(contentDTO);
                    list=contentDTO.getContentDetail().getContentInfos();

                    infoList=contentDTO.getContentDetail().getCategoiesList();
                    castAndCrewLists=contentDTO.getContentDetail().getCastAndCrew();
                    reviewsLists=contentDTO.getContentDetail().getReviewList();

                    Gson gson=new Gson();
                    String JsonStr=gson.toJson(contentDTO,ContentDTO.class);
                    SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(MovieDetailsActivity.this).edit();
                    editor.putString("KEY_REVIEWDATA",JsonStr);
                    editor.apply();






                    rating = String.valueOf(response.body().getContentDetail().getAvgRating().get(0).getAvgRating());
                        final float ratingValue = Float.parseFloat(rating);
                        ratingBar.setRating(ratingValue);
                       tvavgRating.setText(""+ratingValue);

                    for (int i=0;i<list.size();i++){
                        tvMoviename.setVisibility(View.VISIBLE);
                        ratingBar.setVisibility(View.VISIBLE);
                        tvviewCount.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        tvlike.setVisibility(View.VISIBLE);
                        tvviewCount.setText("234");
                        tvlike.setText("234876");

                        tvMoviename.setText(list.get(i).getContentTitle());
                    tvfeatureDescription.setText(list.get(i).getFeaturesDescription());
                    tvview.setText("" +list.get(i).getViewCount());
                    tvYear.setText(list.get(i).getYear().toString());


//                        Picasso.with(mContext).load(url+ list.get(i).getPoster_Image()).into(ivposter);
                        Glide.with(getApplicationContext())
                                .load(url+ list.get(i).getTrailer_Image())
                                .into(ivposter);

//                                .placeholder(R.mipmap.view)


                    }

//                    tvfeatureDescription.setText(contentInfo.getFeaturesDescription());
//                    tvviewCount.setText(""+contentInfo.getViewCount());
//                    tvYear.setText(contentInfo.getYear().toString());
                    initialize();
//                    tabViewPager = (ViewPager) findViewById(R.id.tab_viewpager);
//                    // ll_ratingView = (LinearLayout) findViewById(R.id.ll_ratingView);
//                    tabLayout = (TabLayout) findViewById(R.id.tabLayout);

                }
            }


            @Override
            public void onFailure(Call<ContentDTO> call, Throwable t) {
                Log.i("MovieDetailsActivity","Fail" );
//                Toast.makeText(MovieDetailsActivity.this, "" +t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }




    public void setupViewPager(ViewPager tabViewPager) {
         ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        int count = movieDetailsCategories.size();
        /*for (int i = 0; i < count; i++) {

        }*/

        adapter.addFrag(new FragmentMovieDetail_Info(list,selectseason,selectepisode,rating), movieDetailsCategories.get(0).getCategoryName());
        adapter.addFrag(new FragmentCreators_Cast(castAndCrewLists), movieDetailsCategories.get(1).getCategoryName());
        adapter.addFrag(new FragmentUserReviews(reviewsLists,contentid), movieDetailsCategories.get(2).getCategoryName());
        tabViewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
//        private final List<ContentInfo> t1 = new ArrayList<>();


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

        public void addFrag(Fragment fragment,String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
        item=menu.findItem(R.id.action_like).setVisible(true);
        menu.findItem(R.id.action_share).setVisible(true);
        menu.findItem(R.id.action_tuneUp).setVisible(false);
        menu.findItem(R.id.action_person).setVisible(false);
//        menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_likes));

//        if (likeicon!=null) {
//            if (likeicon.equals("Like")) {
//                item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
//            }
//        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         int id = item.getItemId();


         if (id == R.id.action_like) {
                 if (like1) {
                           item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                           AddandRemoveWishList();

                 } else {
                           item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border));
                           AddandRemoveWishList();
                        }
                like1 = !like1;
                return true;

            }


        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareSubText = "WhatsApp - The Great Chat App";
            String shareBodyText = "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(shareIntent, "Share With"));

            return true;
        }

//        if (id==android.R.id.home)
//        {
//            onBackPressed();
//            return true;
//        }

        return false;

//        return super.onOptionsItemSelected(item);
    }

    public void AddandRemoveWishList(){
        Call<JsonObject> call1=RetroClient.sdaemonApi().getaddtofavouritewishlist(userId,contentid);
        Log.e(getClass().getName(), "===" + call1.request().url());

        call1.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("FavouriteWishList",""+response.body());


                if (response.isSuccessful()){
                    JsonObject jsonObject=response.body();
                    String msg=jsonObject.get("Message").getAsString();
                    wishliststatus=jsonObject.get("Status").getAsInt();
                    Log.i("Like Status" ,""+wishliststatus);
//                    Toast.makeText(MovieDetailsActivity.this, ""+msg, Toast.LENGTH_SHORT).show();

                }else {
//                    Toast.makeText(MovieDetailsActivity.this, "Unsuccess "+response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("WishList",""+t.getMessage());
//                Toast.makeText(MovieDetailsActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getWishList() {
//        progressBar.setVisibility(View.VISIBLE);
        gifImageView.setVisibility(View.VISIBLE);


        Call<WishListDTO> call = RetroClient.sdaemonApi().getFavouriteListData(userId, 0);
        Log.i(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<WishListDTO>() {
            @Override
            public void onResponse(Call<WishListDTO> call, Response<WishListDTO> response) {
                if (response.isSuccessful()) {
//                    progressBar.setVisibility(View.GONE);

//                    Toast.makeText(MovieDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    WishListDTO wishListDTO = response.body();
//                    if (wishListDTO.getStatus())
                    table = wishListDTO.getContentDetail().getTable();
//                    Log.i("Wish List ",""+table.get(1).getContentID());
                    for (int i=0;i<table.size();i++){
                        if (contentid==table.get(i).getContentID()){
                            Log.i("Content Id for wishlist",""+table.get(i).getContentID());
                            item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                            like1=false;
                        }
//                        else {
//                            item.setIcon(getResources().getDrawable(R.drawable.ic_favorite_border));
//                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<WishListDTO> call, Throwable t) {
//                Toast.makeText(MovieDetailsActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Favourite MyTVShow", "" + t.getMessage());

            }
        });

    }
    private void showErrorView(Throwable throwable) {

        if ( errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
//            progressBar.setVisibility(View.GONE);
            gifImageView.setVisibility(View.GONE);

            txtError.setText(fetchErrorMessage(throwable));
        }
    }
    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
//            progressBar.setVisibility(View.VISIBLE);
            gifImageView.setVisibility(View.VISIBLE);

        }
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!Methods.isOnline(MovieDetailsActivity.this)) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }


    @Override
    public void onBackPressed() {


//       getSupportFragmentManager().beginTransaction().add(android.R.id.content,new FragmentMyFavouriteNew()).commit();
//        finish();
        super.onBackPressed();


    }


    private class AsynctaskClass extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {

//            pd.show();
//            gifImageView.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            getAllMovieDetails();
            getWishList();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
//            pd.dismiss();
            super.onPostExecute(s);
        }
    }
}
