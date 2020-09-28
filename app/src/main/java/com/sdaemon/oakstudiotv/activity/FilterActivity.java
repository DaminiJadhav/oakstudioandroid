package com.sdaemon.oakstudiotv.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dto.FilterDTO;
import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.AppSession;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity implements AppConstants {
    private LinearLayout ll_genre,ll_Year,ll_Review,ll_Studio,ll_features,ll_mpaaRating;
    private ImageView iv_tabback;
    private TextView ll_genre_text,ll_year_txt,ll_review_txt,ll_studio_txt,ll_feature_txt,ll_rating_txt,text;
    private Button btnContinue,btnReset;
    String genre="";
    boolean continu;
    Context context;
   public static String strName = "Action",test = "",name="",year="2013",review="review",studio="studio",feature="features",rating="rating";
    ArrayList<FilterDTO> appSessionList;
    AppSession appSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        context = this;
        appSession = AppSession.getInstance(context);

        initialize();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            if(bundle.containsKey("genre"))
            {
                genre=bundle.getString("genre");
                ll_genre_text.setText(genre);
                Toast.makeText(context,""+genre,Toast.LENGTH_LONG).show();
            }

        }
    }

    private void initialize() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        text=(TextView)findViewById(R.id.tvTitle);
        text.setText("Filter");
        ll_genre = (LinearLayout) findViewById(R.id.ll_genre);
        ll_genre_text = (TextView)findViewById(R.id.Genre_text);
        ll_year_txt=findViewById(R.id.year_text);
        ll_review_txt=findViewById(R.id.review_txt);
        ll_studio_txt=findViewById(R.id.studio_txt);
        ll_feature_txt=findViewById(R.id.feature_txt);
        ll_rating_txt=findViewById(R.id.rating_txt);
        ll_Year = (LinearLayout) findViewById(R.id.ll_Year);
        ll_Review =(LinearLayout) findViewById(R.id.ll_Review);
        ll_Studio =(LinearLayout) findViewById(R.id.ll_Studio);
        ll_features =(LinearLayout) findViewById(R.id.ll_features);
        ll_mpaaRating =(LinearLayout) findViewById(R.id.ll_mpaaRating);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        Log.i("Movie Type",""+appSession.getMovieType());

        if (appSession.getMovieType()==true) {
            ll_genre_text.setText(appSession.getGenreID());
            ll_year_txt.setText(appSession.getYearID());
            ll_review_txt.setText(appSession.getReviewID());
            ll_studio_txt.setText(appSession.getStudioID());
            ll_feature_txt.setText(appSession.getFeatureID());
            ll_rating_txt.setText(appSession.getRatingID());
        }else {
            ll_genre_text.setText(appSession.getTVshowGenreID());
            ll_year_txt.setText(appSession.getTVshowYearID());
            ll_review_txt.setText(appSession.getTVshowReviewID());
            ll_studio_txt.setText(appSession.getTVshowStudioID());
            ll_feature_txt.setText(appSession.getTVshowFeatureID());
            ll_rating_txt.setText(appSession.getTVshowRatingID());
        }


        btnReset = (Button) findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_genre_text.setText("");
                ll_year_txt.setText("");
                ll_review_txt.setText("");
                ll_studio_txt.setText("");
                ll_feature_txt.setText("");
                ll_rating_txt.setText("");

                if (appSession.getMovieType() == true) {
                    appSession.setGenreIDposition(0);
                    appSession.setGenreID("");

                    appSession.setYearIDposition(0);
                    appSession.setYearID("");

                    appSession.setReviewIDposition(0);
                    appSession.setReviewID("");

                    appSession.setStudioIDposition(0);
                    appSession.setStudioID("");

                    appSession.setFeatureIDposition(0);
                    appSession.setFeatureID("");

                    appSession.setRatingIDposition(0);
                    appSession.setRatingID("");

                }else {
                    appSession.setTVStudioIDposition(0);
                    appSession.setTVshowGenreID("");

                    appSession.setTVYearIDposition(0);
                    appSession.setTVshowYearID("");

                    appSession.setTVStudioIDposition(0);
                    appSession.setTVshowStudioID("");

                    appSession.setTVFeatureIDposition(0);
                    appSession.setTVshowFeatureID("");

                    appSession.setTVReviewIDposition(0);
                    appSession.setTVshowReviewD("");

                    appSession.setTVRatingIDposition(0);
                    appSession.setTVshowRatingID("");


                }
            }
        });

         btnContinue = (Button) findViewById(R.id.btn_continue);
         btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continu=true;
                finish();
            }
        });






        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(continu==true)
                    {

                    }
                    else{
                        if (appSession.getMovieType()==true) {
                            appSession.setGenreIDposition(0);
                            appSession.setGenreID("");

                            appSession.setYearIDposition(0);
                            appSession.setYearID("");

                            appSession.setReviewIDposition(0);
                            appSession.setReviewID("");

                            appSession.setStudioIDposition(0);
                            appSession.setStudioID("");

                            appSession.setFeatureIDposition(0);
                            appSession.setFeatureID("");

                            appSession.setRatingIDposition(0);
                            appSession.setRatingID("");
                        }else {
                            appSession.setTVGenreIDposition(0);
                            appSession.setTVshowGenreID("");

                            appSession.setTVYearIDposition(0);
                            appSession.setTVshowYearID("");

                            appSession.setTVStudioIDposition(0);
                            appSession.setTVshowStudioID("");

                            appSession.setTVFeatureIDposition(0);
                            appSession.setTVshowFeatureID("");

                            appSession.setTVReviewIDposition(0);
                            appSession.setTVshowReviewD("");

                            appSession.setTVRatingIDposition(0);
                            appSession.setTVshowRatingID("");
                        }

                    }





                onBackPressed();

            }
        });

        ll_genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(FilterActivity.this, GenreActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(FilterActivity.this, GenreActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString(PN_NAME,strName);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                ((FilterActivity.this)).overridePendingTransition(0, 0);


            }
        });

        ll_Year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, YearActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("KEY_YEAR",year);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
                ((FilterActivity.this)).overridePendingTransition(0, 0);
            }
        });

        ll_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, ReviewsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("KEY_REVIEW",review);
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
                ((FilterActivity.this)).overridePendingTransition(0, 0);
            }
        });

        ll_Studio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, StudioActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("KEY_STUDIO",studio);
                intent.putExtras(bundle);
                startActivityForResult(intent, 4);
                ((FilterActivity.this)).overridePendingTransition(0, 0);
            }
        });

        ll_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, FeaturesActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("KEY_FEATURE",feature);
                intent.putExtras(bundle);
                startActivityForResult(intent, 5);
                ((FilterActivity.this)).overridePendingTransition(0, 0);
            }
        });

        ll_mpaaRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, MpaaRatingActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("KEY_RATING",rating);
                intent.putExtras(bundle);
                startActivityForResult(intent, 6);
                ((FilterActivity.this)).overridePendingTransition(0, 0);
            }
        });
    }




    @Override
    protected void onStop() {
        /*if(continu==true)
        {

        }
        else{
            appSession.setGenreIDposition(0);
            appSession.setGenreID("");

            appSession.setYearIDposition(0);
            appSession.setYearID("");

            appSession.setReviewIDposition(0);
            appSession.setReviewID("");

            appSession.setStudioIDposition(0);
            appSession.setStudioID("");

            appSession.setFeatureIDposition(0);
            appSession.setFeatureID("");

            appSession.setRatingIDposition(0);
            appSession.setRatingID("");

        }*/
        super.onStop();
    }

    @Override
    protected void onDestroy() {
      /*  if(continu==true)
        {

        }
        else{
            appSession.setGenreIDposition(0);
            appSession.setGenreID("");

            appSession.setYearIDposition(0);
            appSession.setYearID("");

            appSession.setReviewIDposition(0);
            appSession.setReviewID("");

            appSession.setStudioIDposition(0);
            appSession.setStudioID("");

            appSession.setFeatureIDposition(0);
            appSession.setFeatureID("");

            appSession.setRatingIDposition(0);
            appSession.setRatingID("");

        }*/
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(continu==true)
        {

        }
        else{
            if (appSession.getMovieType()==true) {
                appSession.setGenreIDposition(0);
                appSession.setGenreID("");

                appSession.setYearIDposition(0);
                appSession.setYearID("");

                appSession.setReviewIDposition(0);
                appSession.setReviewID("");

                appSession.setStudioIDposition(0);
                appSession.setStudioID("");

                appSession.setFeatureIDposition(0);
                appSession.setFeatureID("");

                appSession.setRatingIDposition(0);
                appSession.setRatingID("");
            }else {
                appSession.setTVGenreIDposition(0);
                appSession.setTVshowGenreID("");

                appSession.setTVYearIDposition(0);
                appSession.setTVshowYearID("");

                appSession.setTVStudioIDposition(0);
                appSession.setTVshowStudioID("");

                appSession.setTVFeatureIDposition(0);
                appSession.setTVshowFeatureID("");

                appSession.setTVReviewIDposition(0);
                appSession.setTVshowReviewD("");

                appSession.setTVRatingIDposition(0);
                appSession.setTVshowRatingID("");

            }

        }


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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle bundle=data.getExtras();
                if(bundle!=null) {
                    if (bundle.containsKey(PN_NAME)) {
                        strName = bundle.getString(PN_NAME);
                        Toast.makeText(context, ""+strName, Toast.LENGTH_SHORT).show();
                    }
                }
                //  if (strName.equals("Rajendra")) {
                if (appSession.getMovieType()==true) {
                    ll_genre_text.setText(strName);
                    appSession.setGenreID(strName);
                }else {
                    ll_genre_text.setText(strName);
                    appSession.setTVshowGenreID(strName);
                }
               // ll_genre_text.setText(GenreActivity.gentre_name);
             //   }

             }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Bundle bundle=data.getExtras();
                if(bundle!=null) {
                    if (bundle.containsKey("KEY_YEAR")) {
                        year = bundle.getString("KEY_YEAR");
                        Toast.makeText(context, ""+year, Toast.LENGTH_SHORT).show();
                    }
                }
                //  if (strName.equals("Rajendra")) {

                if (appSession.getMovieType()==true) {
                    ll_year_txt.setText(year);
                    appSession.setYearID(year);
                }else {
                    ll_year_txt.setText(year);
                    appSession.setTVshowYearID(year);
                }
                // ll_genre_text.setText(GenreActivity.gentre_name);
                //   }

            }
        }

        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Bundle bundle=data.getExtras();
                if(bundle!=null) {
                    if (bundle.containsKey("KEY_REVIEW")) {
                        review = bundle.getString("KEY_REVIEW");
                        Toast.makeText(context, ""+review, Toast.LENGTH_SHORT).show();
                    }
                }
                //  if (strName.equals("Rajendra")) {
                if (appSession.getMovieType()==true) {
                    ll_review_txt.setText(review);
                }
                else {
                    ll_review_txt.setText(review);

                }
                // ll_genre_text.setText(GenreActivity.gentre_name);
                //   }

            }
        }

        if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                Bundle bundle=data.getExtras();
                if(bundle!=null) {
                    if (bundle.containsKey("KEY_STUDIO")) {
                        studio = bundle.getString("KEY_STUDIO");
                        Toast.makeText(context, ""+studio, Toast.LENGTH_SHORT).show();
                    }
                }
                //  if (strName.equals("Rajendra")) {
                if (appSession.getMovieType()==true){
                    ll_studio_txt.setText(studio);

                }else {
                    ll_studio_txt.setText(studio);

                }
                // ll_genre_text.setText(GenreActivity.gentre_name);
                //   }

            }
        }
        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                Bundle bundle=data.getExtras();
                if(bundle!=null) {
                    if (bundle.containsKey("KEY_FEATURE")) {
                        feature = bundle.getString("KEY_FEATURE");
                        Toast.makeText(context, ""+feature, Toast.LENGTH_SHORT).show();
                    }
                }
                //  if (strName.equals("Rajendra")) {
                if (appSession.getMovieType()==true) {
                    ll_feature_txt.setText(feature);
                }else {
                    ll_feature_txt.setText(feature);
                }
                // ll_genre_text.setText(GenreActivity.gentre_name);
                //   }

            }
        }

        if (requestCode == 6) {
            if (resultCode == RESULT_OK) {
                Bundle bundle=data.getExtras();
                if(bundle!=null) {
                    if (bundle.containsKey("KEY_RATING")) {
                        rating = bundle.getString("KEY_RATING");
                        Toast.makeText(context, ""+rating, Toast.LENGTH_SHORT).show();
                    }
                }
                //  if (strName.equals("Rajendra")) {
                if (appSession.getMovieType()==true) {

                    ll_rating_txt.setText(rating);
                }else {
                    ll_rating_txt.setText(rating);

                }
                // ll_genre_text.setText(GenreActivity.gentre_name);
                //   }

            }
        }
    }


}
