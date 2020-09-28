package com.sdaemon.oakstudiotv.activity;

import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.utils.AppSession;

import org.json.JSONObject;


public class AboutUsActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT = "fragment";
    TextView tvaboutus;
    AppSession appSession;
    Context context;
    ImageView iv_back;
    LinearLayout llprogress;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        tvaboutus = (TextView) findViewById(R.id.tv_about_us);
//        llprogress=(LinearLayout) findViewById(R.id.ll_progress);
        progressBar=(ProgressBar) findViewById(R.id.ll_progress);

        context = this;

        progressBar.setVisibility(View.VISIBLE);
        initialize();
        tvaboutus.setMovementMethod(new ScrollingMovementMethod());

//        initialize();

        try {
            appSession = AppSession.getInstance(context);
            if (appSession != null) {
                String content = appSession.getAbout().get(0).getFull();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setVisibility(View.GONE);
                    tvaboutus.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));

                } else
                    progressBar.setVisibility(View.GONE);
                     tvaboutus.setText(Html.fromHtml(content));
            }
        }catch (Exception e){
//            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }


    private void initialize(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.GONE);
//                Intent aboutIntent = new Intent(AboutUsActivity.this, HelpandInfoActivity.class);
//                startActivity(aboutIntent);
//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new FragmentHelpandInfo()).commit();
                finish();
//                onBackPressed();

            }
        });
    }

    @Override
    public void onBackPressed() {
//        final FragmentHelpandInfo fragment = (FragmentHelpandInfo) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
//        fragment.onBackPressed();
        super.onBackPressed();

    }


    public void demo(){
//        String url = "http://www.mocky.io/v2/597c41390f0000d002f4dbd1";
        String url = "https://oakstudio.azurewebsites.net/Services/api/NativeContent/GetAddToWishList?userId=13335&ContentId=4";

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JSONObject parameters = new JSONObject();
        JsonObjectRequest jsonArrayRequest=new JsonObjectRequest(Request.Method.GET, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                try {
//                    Log.i("Response","success");
////                    Toast.makeText(getBaseContext(), "Response: " + response.getString("Message"), Toast.LENGTH_SHORT)
////                                .show();
////                    for (int i=0;i<response.getJSONArray("users").length();i++) {
////                        Toast.makeText(getBaseContext(), "Response: " + response.getJSONArray("users").getJSONObject(i).getString("name"), Toast.LENGTH_SHORT)
////                                .show();
////                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getBaseContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT)
//                        .show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }





}

