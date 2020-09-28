package com.sdaemon.oakstudiotv.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.BlogDetailDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogArticleActivity extends AppCompatActivity {
    private ImageView mImageView, iv_back;
    String type;
    TextView tvsmalltitle,tvlargeTitle,tvdirectorname,tvDate,tvDescription,tvLongDescription,tvveryDest,tvshortDesccription;
    List<BlogDetailDTO> blog = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_article);
        initialize();

    }


    private void initialize(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);



        tvsmalltitle = (TextView) findViewById(R.id.tv_aquamanmovie);
        tvshortDesccription=(TextView) findViewById(R.id.tv_short);
        tvsmalltitle = (TextView) findViewById(R.id.tv_aquamanmovie);
        tvlargeTitle = (TextView) findViewById(R.id.tv_aquaman);
        tvdirectorname = (TextView) findViewById(R.id.tv_director);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvDescription = (TextView) findViewById(R.id.tv_superheros);
        tvLongDescription = (TextView) findViewById(R.id.tv_long);
        tvveryDest= (TextView) findViewById(R.id.tv_last);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            {
                if (bundle.containsKey("TYPE"))
                    type = bundle.getString("TYPE");
            }
        }
        getBlog();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getBlog() {
        Call<List<BlogDetailDTO>> call = RetroClient.sdaemonApi().blogdetails(type);
        call.enqueue(new Callback<List<BlogDetailDTO>>() {
            @Override
            public void onResponse(Call<List<BlogDetailDTO>> call, Response<List<BlogDetailDTO>> response) {
                blog = response.body();
                tvsmalltitle.setText(blog.get(0).getSmallTitle());
                tvlargeTitle.setText(blog.get(0).getLargeTitle());
                tvdirectorname.setText(blog.get(0).getDirectorName());
                tvshortDesccription.setText(blog.get(0).getSmallDescription());
                tvDate.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(getUTCToLocalDate(blog.get(0).getDate())));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvDescription.setText(Html.fromHtml(blog.get(0).getLargeDescription(), Html.FROM_HTML_MODE_LEGACY));
                    tvLongDescription.setText(Html.fromHtml(blog.get(0).getDescription1Bullets(), Html.FROM_HTML_MODE_LEGACY));
                    tvveryDest.setText(Html.fromHtml(blog.get(0).getDescription2(), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    tvDescription.setText(Html.fromHtml(blog.get(0).getDescription1Bullets()));
                    tvLongDescription.setText(Html.fromHtml(blog.get(0).getDescription1Bullets()));
                    tvveryDest.setText(Html.fromHtml(blog.get(0).getDescription2()));
                }
            }

            public Date getUTCToLocalDate(String date) {
                Date inputDate = new Date();
                if (date != null && !date.isEmpty()) {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    try {
                        inputDate = simpleDateFormat.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return inputDate;
            }
            @Override
            public void onFailure(Call<List<BlogDetailDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
