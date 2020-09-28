package com.sdaemon.oakstudiotv.activity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.CommunicationPrefList_Adapter;
import com.sdaemon.oakstudiotv.model.CommunicatonPref_list;

import java.util.ArrayList;

public class CommunicationPrefActivity extends AppCompatActivity {
    private ListView simpleList;
    private ArrayList<CommunicatonPref_list> communicatonPref_lists=new ArrayList<>();
    private CommunicationPrefList_Adapter communicationPrefList_adapter;
    private ImageView iv_tabback;
    public static final String TAG = "CommunicationPrefActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_pref);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        simpleList = (ListView)findViewById(R.id.simpleListView);




        communicatonPref_lists.add(new CommunicatonPref_list(getResources().getString(R.string.communicat_get_notification)));
        communicatonPref_lists.add(new CommunicatonPref_list(getResources().getString(R.string.communict_new_release)));
        communicatonPref_lists.add(new CommunicatonPref_list(getResources().getString(R.string.communict_feedback)));
        communicatonPref_lists.add(new CommunicatonPref_list(getResources().getString(R.string.communict_share_my_inf)));



        communicationPrefList_adapter = new CommunicationPrefList_Adapter(CommunicationPrefActivity.this, communicatonPref_lists);
        simpleList.setAdapter(communicationPrefList_adapter);

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
        menu.findItem(R.id.action_person).setVisible(false);
        menu.findItem(R.id.action_tuneUp).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

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

            case R.id.action_done:
                for (int i=0;i<=communicatonPref_lists.size();i++){
                    communicationPrefList_adapter.Check(i);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
