package com.sdaemon.oakstudiotv.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.MovieAdapter;
import com.sdaemon.oakstudiotv.model.RetroPhoto;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private MovieAdapter categoryAdapter;
    Button reset,search;
    SearchView searchView;
    private LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager mLayoutManager;
    private RecyclerView rv;
    private ImageView iv_tabback;
    private ArrayList<RetroPhoto> trailersByCategory = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initialization();
    }

    private void initialization() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        rv = (RecyclerView) findViewById(R.id.recycler_categoryTrailers);
        reset=findViewById(R.id.btn_reset);
        search=findViewById(R.id.btn_search);
        searchView=findViewById(R.id.sv_data);


        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                searchView.setQuery("",false);
                searchView.clearFocus();
                searchView.onActionViewCollapsed();

            }
        });

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());



      /*  trailersByCategory.add(new MovieDetails(R.drawable.view,"EXPANDABLES 2","(2016)",3,123));
        trailersByCategory.add(new MovieDetails(R.drawable.view1,"ANT-MAN","(2016)",4,1021));
        trailersByCategory.add(new MovieDetails(R.drawable.view2,"JUSTICE LEAGUE","(2016)",5,500));
        trailersByCategory.add(new MovieDetails(R.drawable.view3,"BLACK PANTHER","(2016)",3,123));
        trailersByCategory.add(new MovieDetails(R.drawable.view4,"MISSION IMPOSSIBLE","(2016)",5,854));*/
        categoryAdapter = new MovieAdapter(SearchActivity.this,trailersByCategory,onClickCallback);
        rv.setAdapter(categoryAdapter);
    }





    OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if(type.equalsIgnoreCase("image")){
                Toast.makeText(getApplicationContext(),"Image Click",Toast.LENGTH_LONG).show();
            }
            else {

            }
        }
    };

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
}
