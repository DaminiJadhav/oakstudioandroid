package com.sdaemon.oakstudiotv.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.ContentDTO;
import com.sdaemon.oakstudiotv.model.PostContentReviews;
import com.sdaemon.oakstudiotv.model.PutContentReview;
import com.sdaemon.oakstudiotv.model.ReviewsList;
import com.sdaemon.oakstudiotv.utils.AppSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRatingActivity extends AppCompatActivity implements View.OnClickListener {
    private RatingBar ratingBar1, ratebar;
    private float ratedValue;
    EditText edmessage;
    TextView tvdescription,tvcount,tvview,tvyear,tvrating,tvmovienamae;
    ContentDTO contentDTO;
    ReviewsList reviewsList;
    Button btnsubmit;
    ImageView ivposter;
    int rating2=0;
    private String message;
    AppSession appSession;
    public static String useruniqueid;
    public static int userid;
    public static int contentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_rating);
        ratingBar1 = (RatingBar) findViewById(R.id.ratingBar);
        ratebar = (RatingBar) findViewById(R.id.ratebar);
        tvdescription=findViewById(R.id.tv_futureDescription);
        tvmovienamae=findViewById(R.id.tv_bannerMovieName);
        tvcount=findViewById(R.id.tv_viewcount);
        tvyear=findViewById(R.id.tv_year);
        tvrating=findViewById(R.id.movieRating);
        ivposter=findViewById(R.id.iv_posterImage);
        edmessage=findViewById(R.id.ed_message);
        btnsubmit=findViewById(R.id.btn_submit);
        tvview=findViewById(R.id.tv_views);
        btnsubmit.setOnClickListener(this);
      /*  Drawable drawable = ratingBar1.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);*/

      Bundle bundle=getIntent().getExtras();
      if (bundle!=null){
          String review=bundle.getString("Review");
          contentid=bundle.getInt("KEY_CONTENTIDR");
          edmessage.setText(review);
      }
        Log.i("Content unique id ", "" + contentid);

        Drawable drawable1 = ratebar.getProgressDrawable();
        drawable1.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("    OAKSTUDIO.TV");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);


         appSession=AppSession.getInstance(this);
         if (appSession!=null) {
             userid = appSession.getUserDTO().getResult().getCustomerId();
             useruniqueid = appSession.getUserDTO().getResult().getUniqueIdentifire();
//             contentid = appSession.getContentID();
             Log.i("User id ", "" + userid);
             Log.i("User unique id ", "" + useruniqueid);
         }

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(ReviewRatingActivity.this);
        String json=sharedPreferences.getString("KEY_REVIEWDATA","N/A");
        Gson gson=new Gson();
        contentDTO=gson.fromJson(json,ContentDTO.class);
        if (contentDTO!=null)
        {

            String rating = String.valueOf(contentDTO.getContentDetail().getAvgRating().get(0).getAvgRating());
            final float ratingValue = Float.parseFloat(rating);
            ratebar.setRating(ratingValue);
            tvrating.setText(""+ratingValue);

            for (int i=0;i<contentDTO.getContentDetail().getContentInfos().size();i++) {
                String url="https://oakstudio.azurewebsites.net";
                tvmovienamae.setText(contentDTO.getContentDetail().getContentInfos().get(i).getContentTitle());
                tvdescription.setText(contentDTO.getContentDetail().getContentInfos().get(i).getFeaturesDescription());
                tvview.setText(contentDTO.getContentDetail().getContentInfos().get(i).getViewCount());
                tvyear.setText(contentDTO.getContentDetail().getContentInfos().get(i).getYear().toString());
                Glide.with(this)
                        .load(url+ contentDTO.getContentDetail().getContentInfos().get(i).getPoster_Image())
                        .into(ivposter);

//                tvrating.setText(contentDTO.getContentDetail().getAvgRating().get(0).getAvgRating().toString());
            }

        }


//        ratingBar.setNumStars(5);
        ratingBar1.setRating(4);


        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override

            public void onRatingChanged(RatingBar ratingBar, float rating,

                                        boolean fromUser) {


                if (ratingBar1.toString().trim()==null){

                }
                ratedValue = ratingBar.getRating();

               ratingBar1.setRating(Math.round(ratedValue));
                rating2=Math.round(ratedValue);

                Log.i("Your Rating : ",""+ratedValue);

                Log.i("Your Rating round : ",""+Math.round(ratedValue));

            }

        });


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
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_like) {
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
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void postContentReview(String msg){
        Log.i("post User id ",""+userid);
        Log.i("post User unique id ",""+useruniqueid);
        Log.i("post content unique id ",""+contentid);

//        Call<PostContentReviews> call= RetroClient.sdaemonApi().postAllContentReview(0,"eaa775cb-3acf-4c2b-95ae-11ae1f0f30cb",msg,4,"4",0);



        Call<PostContentReviews> call= RetroClient.sdaemonApi().postAllContentReview(userid,useruniqueid,msg,rating2,contentid,0);

        Log.e(getClass().getName(), "===" + call.request().url());

        call.enqueue(new Callback<PostContentReviews>() {
            @Override
            public void onResponse(Call<PostContentReviews> call, Response<PostContentReviews> response) {
//                PostContentReviews postContentReviews=new PostContentReviews();
                if (response.isSuccessful()){

//                    Toast.makeText(ReviewRatingActivity.this, "Post Review Comment " +response.message(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ReviewRatingActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
//                   getSupportFragmentManager().beginTransaction()
//                                                   .add(android.R.id.content, new FragmentUserReviews()).commit();
                }
                else {
//                    Toast.makeText(ReviewRatingActivity.this, "no post review " +response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<PostContentReviews> call, Throwable t) {
                Toast.makeText(ReviewRatingActivity.this, "Failure :"  +t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                message=edmessage.getText().toString().trim();
                if (TextUtils.isEmpty(message)){
                    Toast.makeText(this, ""+getResources().getString(R.string.please_write_comment), Toast.LENGTH_SHORT).show();
                }  else {
//                if (message!="") {
                    Bundle bundle = getIntent().getExtras();
                    String extra = bundle.getString("KEY_EDITREVIEW");
                    if (extra != null) {
                        int reviewid=bundle.getInt("KEY_REVIEWID");

//                    Toast.makeText(this, "" +extra, Toast.LENGTH_SHORT).show();
                        Log.i("putContentReview", "Put Review Data");
                        putComment(message,reviewid);
                    } else {
                        Log.i("postContentReview", "Post Review Data");
                        postContentReview(message);
                    }
//                }
                }

        }
    }
    public void putComment(String message,int reviewid){
        Log.i("put User id ",""+userid);
        Log.i("put User unique id ",""+useruniqueid);
//        for (int i=0;i<contentDTO.getContentDetail().getReviewList().size();i++){
//            i=contentDTO.getContentDetail().getReviewList().get(i).getContentReviewID();
//            Log.i("put comment Review id",""+i);
//
//        }
//        Call<PutContentReview> call=RetroClient.sdaemonApi().putContentReview(6175,"eaa775cb-3acf-4c2b-95ae-11ae1f0f30cb",message,4,"4",0,i);
        Call<PutContentReview> call=RetroClient.sdaemonApi().putContentReview(userid,useruniqueid,message,4,contentid,0,reviewid);




        Log.e(getClass().getName(), "===" + call.request().url());

        call.enqueue(new Callback<PutContentReview>() {
            @Override
            public void onResponse(Call<PutContentReview> call, Response<PutContentReview> response) {

                Log.i("put comment  : ",""+response.body());

                if (response.isSuccessful()){
                    Log.i("put comment  : ","success");
//                    Toast.makeText(ReviewRatingActivity.this, "Put Review Comment", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ReviewRatingActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
//                    Toast.makeText(ReviewRatingActivity.this, "Not Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PutContentReview> call, Throwable t) {
                Log.i("put comment  : ","fail");

                Toast.makeText(ReviewRatingActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
//                if (t instanceof SocketTimeoutException)
//                {
//                    // "Connection Timeout";
//                }
//                else if (t instanceof IOException)
//                {
//                    // "Timeout";
//                }
//                else
//                {
//                    //Call was cancelled by user
//                    if(call.isCanceled())
//                    {
//                        System.out.println("Call was cancelled forcefully");
//                    }
//                    else
//                    {
//                        //Generic error handling
//                        System.out.println("Network Error :: " + t.getLocalizedMessage());
//                    }
//                }
            }
        });
    }
}
