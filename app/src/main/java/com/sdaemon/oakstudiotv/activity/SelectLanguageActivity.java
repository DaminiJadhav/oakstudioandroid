package com.sdaemon.oakstudiotv.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.exoplayer2.util.Log;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.LanguageList_Adapter;
import com.sdaemon.oakstudiotv.model.Language_list;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.LanguageUtil;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;
import java.util.Locale;

public class SelectLanguageActivity extends AppCompatActivity {
    private ListView simpleList;
    private ArrayList<Language_list> language_lists=new ArrayList<>();
    private LanguageList_Adapter languageList_adapter;
    private ImageView iv_tabback;
    AppSession appSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appSession=AppSession.getInstance(this);
        LanguageUtil.loadLocal(this);
        setContentView(R.layout.activity_select_language);

        initialize();
    }

    private void initialize() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

//        appSession=AppSession.getInstance(this);

        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        simpleList = (ListView)findViewById(R.id.simpleListView);



        language_lists.add(new Language_list("Dutch"));
        language_lists.add(new Language_list("English"));
        language_lists.add(new Language_list("French"));
        language_lists.add(new Language_list("German"));
        language_lists.add(new Language_list("Italian"));
        language_lists.add(new Language_list("Russian"));
        language_lists.add(new Language_list("Swedish"));
        language_lists.add(new Language_list("Spanish"));





        languageList_adapter = new LanguageList_Adapter(SelectLanguageActivity.this,onClickCallback, language_lists);
        simpleList.setAdapter(languageList_adapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SelectLanguageActivity.this, ""+language_lists.get(i), Toast.LENGTH_SHORT).show();
            }
        });


    }


    OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if(type.equalsIgnoreCase("")){
//                languageList_adapter.showSelectLanguage();
                Toast.makeText(SelectLanguageActivity.this, ""+language_lists.get(position).getLanguage(), Toast.LENGTH_SHORT).show();
                showChangeLanguage(position);

                appSession.setSelectedLanguage(language_lists.get(position).getLanguage());
//                Intent intent = new Intent();
//                intent.putExtra("Key_Language", language_lists.get(position).getLanguage());
//                setResult(RESULT_OK, intent);

                Intent intent = new Intent(SelectLanguageActivity.this,MainActivity.class);
                startActivity(intent);

                finish();

            }

        }
    };


    public void showChangeLanguage(int position){

                if (position==0){
                    LanguageUtil.setLocale(this,"du");
                    recreate();
                }else if (position==1){
                    LanguageUtil.setLocale(this,"en");
                    recreate();
                }else if (position==2){
                    LanguageUtil.setLocale(this,"fr");
                    recreate();
                }else if (position==3){
                    LanguageUtil.setLocale(this,"ge");
                    recreate();
                }else if (position==4){
                    LanguageUtil.setLocale(this,"it");
                    recreate();
                }else if (position==5){
                    LanguageUtil.setLocale(this,"ru");
                    recreate();
                }else if (position==6){
                    LanguageUtil.setLocale(this,"swe");
                    recreate();
                }else if (position==7){
                    LanguageUtil.setLocale(this,"es");
                    recreate();
                }

    }

    public void setLocale(String lang){
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
//        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
//        editor.putString("My_lang",lang);
//        Log.i("Language",""+lang);
//        editor.apply();

        appSession.setLanguage(lang);
    }

    public void  loadLocal(){
        if (appSession.getLanguage()!=null) {
            String language = appSession.getLanguage();
            Log.i("Language 1", "" + language);
            setLocale(language);

        }
//        SharedPreferences editor=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
//        String language=editor.getString("My_lang","");
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            return true;
        }

        if (id == android.R.id.home){
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
}
