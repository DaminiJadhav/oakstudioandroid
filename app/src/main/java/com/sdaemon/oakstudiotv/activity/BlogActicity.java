package com.sdaemon.oakstudiotv.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.BlogAdapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.BlogDto;
import com.sdaemon.oakstudiotv.model.BlogList;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogActicity extends AppCompatActivity {
ImageView iv_back;
public List blogDataList = new ArrayList<>();
BlogList blogList;
BlogAdapter blogAdapter;
private LinearLayoutManager linearLayoutManager;
SearchView searchView;
ProgressBar progressBar;
List<BlogDto>blog =new ArrayList<>();

androidx.recyclerview.widget.RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_acticity);
        progressBar=(ProgressBar) findViewById(R.id.blog_progress);

        progressBar.setVisibility(View.VISIBLE);

           initialize();
    }


    private void initialize(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);

        recyclerView=(androidx.recyclerview.widget.RecyclerView) findViewById(R.id.recycleview);
        searchView=findViewById(R.id.searchview);

        linearLayoutManager = new LinearLayoutManager(BlogActicity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        getBlog();
//        SearchDataPrepare();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent blogIntent = new Intent(BlogActicity.this, HelpandInfoActivity.class);
//                startActivity(blogIntent);

//                getSupportFragmentManager().beginTransaction()
//                                           .add(android.R.id.content, new FragmentHelpandInfo()).commit();

//                                onBackPressed();
//                finish();

//                Intent returnIntent = new Intent();
//                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                blogAdapter.getFilter().filter(query);
//                SearchDataPrepare();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (newText.length() == 0){
////                    hideProgress();
//
////                if (query==data.getName()){
//                    SearchDataPrepare();
//
//                }else {
////                    showDialoge(BlogActicity.this,"hgdghf","sghdgh");
//                }

//                SearchDataPrepare();
                if (blogAdapter!=null) {
                    blogAdapter.getFilter().filter(newText);
                }

                return true;
            }
        });


    }


    public void getBlog() {
        Call<List<BlogDto>> call = RetroClient.sdaemonApi().blog();
        call.enqueue(new Callback<List<BlogDto>>() {
            @Override
            public void onResponse(Call<List<BlogDto>> call, Response<List<BlogDto>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                blog= response.body();
                blogAdapter=new BlogAdapter(getApplicationContext(),blog,onClickCallback);
                recyclerView.setAdapter(blogAdapter);
            }
            @Override
            public void onFailure(Call<List<BlogDto>> call, Throwable t) {
            }
        });
    }



    private void SearchDataPrepare() {
        blogDataList.clear();
        blogList = new BlogList("IT:CHAPTER 1",R.drawable.iv_blog2,"Coulrophobiacs, look away now - because it looks like Pennywise is here to stay. As it rakes in one of the biggest openings for horror films in modern...","31 Oct 2019");
        blogDataList.add(blogList);
        blogList = new BlogList("IT:CHAPTER 2", R.drawable.iv_blog1,"as soon as summer starts to draw to a close,taking the UK music festival season with it a new event in the cultural calendar begins.The biggest,most...","31 Oct 2019");
        blogDataList.add(blogList);
        blogList = new BlogList("IT:CHAPTER 3", R.drawable.iv_blog3,"The Marvel Cenematic Universe's 22nd flim will no doubt be biuld as being bigger and better than any of the 21 flims that preceded it,but Avengers...","31 Oct 2019");
        blogDataList.add(blogList);
        blogList = new BlogList("IT:CHAPTER 4", R.drawable.iv_blog4,"Guardians of the galaxy:Vol.2 may only have just come out,but there's already plenty of information already in circulation about the third flim in the...","31 Oct 2019");
        blogDataList.add(blogList);
        blogList = new BlogList("IT:CHAPTER 5", R.drawable.article_5,"How do you solve a problem like Aquaman? The DC Comics character,whose career began with him battling Nazis in the 1940s,has long been...","31 Oct 2019");
        blogDataList.add(blogList);
        blogList = new BlogList("IT:CHAPTER 6",R.drawable.iv_blog6,"Following the release of Star Wars:the Last fJedi,it's time to think about its follow-up,Episode 9.Ahead of its release in 2019,here's what we know...","31 Oct 2019");
        blogDataList.add(blogList);
        blogList = new BlogList("IT:CHAPTER 7", R.drawable.iv_blog7,"Ever watched a movie and thought...","31 Oct 2019");
        blogDataList.add(blogList);
        blogList = new BlogList("IT:CHAPTER 8", R.drawable.iv_blog8,"Coulrophobiacs, look away now - because it looks like Pennywise is here to stay. As it rakes in one of the biggest openings for horror films in modern...","31 Oct 2019");
        blogDataList.add(blogList);
        blogList = new BlogList("IT:CHAPTER 9", R.drawable.iv_blog9,"Coulrophobiacs, look away now - because it looks like Pennywise is here to stay. As it rakes in one of the biggest openings for horror films in modern...","31 Oct 2019");
        blogDataList.add(blogList);
        blogList = new BlogList("IT:CHAPTER 10", R.drawable.iv_blog11,"Coulrophobiacs, look away now - because it looks like Pennywise is here to stay. As it rakes in one of the biggest openings for horror films in modern...","31 Oct 2019");
        blogDataList.add(blogList);


//        blogAdapter=new BlogAdapter(this,blogDataList);
//        recyclerView.setAdapter(blogAdapter);
//        blogDataList.clear();
//        blogAdapter.notifyDataSetChanged();

    }
    public void showDialoge(Context context, String title, String msg) {
        try {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context,R.style.dialoge);
            //            String centerNRed = "<div align:'center' ><span style='color:red' >"+title+"</span></div>";
//            builder.setTitle(Html.fromHtml(centerNRed))
            builder.setTitle(Html.fromHtml("<font color='#D93723'>"+title+"</font>"))
                    .setMessage(msg)
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setPositiveButton(Html.fromHtml("<font color='#9AEB3D'>Search Again !!!!</font>"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                            query1="";
////                            searchView.clearFocus();
//                            searchView.setQuery("", false);
//                            getSearchData();
//                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(Html.fromHtml("<font color='#9AEB3D'>Cancel</font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                            finish();
//                    System.exit(0);
                        }
                    }).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if(type.equalsIgnoreCase("blog")){
                Intent intent=new Intent(getApplicationContext(), BlogArticleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE",blog.get(position).getBlogId().toString());
                intent.putExtras(bundle);
                startActivity(intent);
//                Toast.makeText(BlogActicity.this, "touch"+ blog.get(position).getBlogId().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public void onBackPressed() {

        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
//        moveTaskToBack(false);


//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();


//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(startMain);

        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
