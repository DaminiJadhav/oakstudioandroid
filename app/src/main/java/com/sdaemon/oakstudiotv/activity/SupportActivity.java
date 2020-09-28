package com.sdaemon.oakstudiotv.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.SupportExpandListViewAdapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.SupportDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_back;
    ExpandableListView expandableListView;
    SupportExpandListViewAdapter supportAdapter;
    private HashMap<String, List<String>> expandableListDetail;
    private List<String> answerLists;
    Button btngmail;
    List<SupportDTO.Questions> questions;
    List<SupportDTO.Answers> answers;
    List<String> answer1 = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        initialize();

    }


    public void initialize(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);
        expandableListView=findViewById(R.id.support_expand_listview);
        btngmail=findViewById(R.id.btn_opengmail);
        progressBar=findViewById(R.id.progress_bar);

        btngmail.setOnClickListener(this::onClick);

        questions=new ArrayList<>();
        answers=new ArrayList<>();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent supportIntent = new Intent(SupportActivity.this, HelpandInfoActivity.class);
//                startActivity(supportIntent);
//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new FragmentHelpandInfo()).commit();
                   finish();
//                onBackPressed();
            }
        });

//        loadData();

        progressBar.setVisibility(View.VISIBLE);
        getQueandAns();



        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
//                Toast.makeText(getApplicationContext(),
//                        answerLists.get(i) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        answerLists.get(i)
//                                + " -> "
//                                + expandableListDetail.get(
//                                answerLists.get(i)), Toast.LENGTH_SHORT
//                ).show();
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();

    }

    public void getQueandAns(){
        Call<SupportDTO> call= RetroClient.sdaemonApi().getSupportQandA();
        call.enqueue(new Callback<SupportDTO>() {
            @Override
            public void onResponse(Call<SupportDTO> call, Response<SupportDTO> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);


                    SupportDTO dto=response.body();
                    questions=dto.getQuestions();
                    answers=dto.getAnswers();

                    Log.i("Answer Size",""+answers.size());
                    answerLists = new ArrayList<>();
                    expandableListDetail = new HashMap<>();

                    for (int i=0;i<questions.size();i++){
                        answerLists.add(questions.get(i).getQuestion());
                    }

                    for (int j=0;j<answers.size();j++){
                        String childItems;
                        childItems =answers.get(j).getAnswer();
                        answer1.add(childItems);
                    }

                    for (int i=0;i<answerLists.size();i++) {
                        expandableListDetail.put(answerLists.get(i), answer1);
                    }


                    supportAdapter=new SupportExpandListViewAdapter(SupportActivity.this,answerLists,expandableListDetail);
                    expandableListView.setAdapter(supportAdapter);


                    Log.i("Question Size",""+answerLists.size());
                    Log.i("Answer Size 1",""+answer1.size());




//                    Toast.makeText(SupportActivity.this, "success"+response.message(), Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(SupportActivity.this, "unsuccess"+response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<SupportDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

//                Toast.makeText(SupportActivity.this, "error "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void loadData(){
           expandableListDetail = new HashMap<>();
        answerLists = new ArrayList<>();



        answerLists.add("1. How do I reset my password?");
        answerLists.add("2. How can I request a TV show or movie?");
        answerLists.add("3. How do I cancel my account?");
        answerLists.add("4. Can I rejoin if I cancel Oakstudio?");
        answerLists.add("5. How do I turn subtitles, captions, or alternate audio on or off?");
        answerLists.add("6.How do  profiles work on my  Oakstudio account?");


        String childItems;
        List<String> answer1 = new ArrayList<>();
        childItems = getString(R.string.support_popular_answer1);
        answer1.add(childItems);


        List<String> answer2 = new ArrayList<>();
        childItems = getString(R.string.support_popular_answer1);
        answer2.add(childItems);


        List<String> answer3 = new ArrayList<>();
        childItems = getString(R.string.support_popular_answer2);
        answer3.add(childItems);


        List<String> answer4 = new ArrayList<>();
        childItems = getString(R.string.support_popular_answer3);
        answer4.add(childItems);


        List<String> answer5 = new ArrayList<>();
        childItems =    getString(R.string.support_popular_answer4);
        answer5.add(childItems);

        List<String> answer6 = new ArrayList<>();
        childItems =    getString(R.string.support_popular_answer5);
        answer6.add(childItems);

//        expandableListDetail.put(answerLists.get(0),answer1);
//        expandableListDetail.put(answerLists.get(1),answer2);
//        expandableListDetail.put(answerLists.get(2),answer3);
//        expandableListDetail.put(answerLists.get(3),answer4);
//        expandableListDetail.put(answerLists.get(4),answer5);
//        expandableListDetail.put(answerLists.get(5),answer6);
//

//        supportAdapter.notifyDataSetChanged();

//        expandableListDetail.put(answerLists.get(0),getString(R.string.support_popular_answer));
//        expandableListDetail.put(answerLists.get(1),getString(R.string.support_popular_answer));
//        expandableListDetail.put(answerLists.get(2),getString(R.string.support_popular_answer));
//        expandableListDetail.put(answerLists.get(3),getString(R.string.support_popular_answer));
//        expandableListDetail.put(answerLists.get(4),getString(R.string.support_popular_answer));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_opengmail:
                shareToGMail("ask your question and receive a response to the email","Questions :  ");

        }
    }
    public void shareToGMail(String subject, String content) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"daminijadhav1213@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager pm = getApplicationContext().getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for(final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            startActivity(emailIntent);
    }

}
