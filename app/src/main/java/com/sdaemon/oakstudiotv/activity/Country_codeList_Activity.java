package com.sdaemon.oakstudiotv.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.CoutryList_Adapter;
import com.sdaemon.oakstudiotv.model.Country_list;

import java.util.ArrayList;

public class Country_codeList_Activity extends AppCompatActivity {
   private ListView simpleList;
   private ArrayList<Country_list> country_listArrayList=new ArrayList<>();
   private CoutryList_Adapter countryList_adapter;
    private ImageView iv_tabback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_code_list_);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_logo_with_back);

        simpleList = (ListView)findViewById(R.id.simpleListView);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        country_listArrayList.add(new Country_list("India","+91"));
        country_listArrayList.add(new Country_list("Australia","+234"));
        country_listArrayList.add(new Country_list("China","+885"));
        country_listArrayList.add(new Country_list("Japan","+965"));
        country_listArrayList.add(new Country_list("America","+455"));
        country_listArrayList.add(new Country_list("Newzeland","+789"));

        countryList_adapter = new CoutryList_Adapter(Country_codeList_Activity.this, country_listArrayList);
        simpleList.setAdapter(countryList_adapter);

        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        menu.findItem(R.id.action_done).setVisible(true);
        menu.findItem(R.id.action_tuneUp).setVisible(false);
        menu.findItem(R.id.action_person).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_done:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
