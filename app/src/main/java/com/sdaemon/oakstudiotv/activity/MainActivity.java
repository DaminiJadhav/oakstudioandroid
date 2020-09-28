package com.sdaemon.oakstudiotv.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.PublicClientApplication;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.brainTreePayment.BrainTreeActivity;
import com.sdaemon.oakstudiotv.fragment.FragmentAll_Movies;

import com.sdaemon.oakstudiotv.fragment.FragmentMyFavouriteNew;
import com.sdaemon.oakstudiotv.fragment.FragmentRecentMovies;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.Constants;
import com.sdaemon.oakstudiotv.utils.LanguageUtil;
import com.sdaemon.oakstudiotv.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
//    https://coderwall.com/p/zrdsmq/signing-configs-with-gradle-android
    public static final String NEWS_FRAGMENT = "news_fragment";
    public static final String NEWSITEM_FRAGMENT = "newsitem_fragment";
    private ImageView ivtabSearch;
    /* UI & Debugging Variables */
    private static final String TAG = MainActivity.class.getSimpleName();
    /* Azure AD variables */
    AppSubClass appState;
    private AuthenticationResult authResult;
    private PublicClientApplication sampleApp;
    String[] scopes;
    DownloadManager downloadManager;
    Utilities utilities;
    Context context;
    AppSession appSession;
    String Token;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageUtil.loadLocal(this);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        utilities = Utilities.getInstance(context);
        initialize();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initialize() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_search);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_search);

        ivtabSearch = findViewById(R.id.iv_tabSearch);




        ivtabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//

                startActivity(new Intent(MainActivity.this, SearchItemActivity.class));
                //  finish();
            }
        });

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        FragmentManager manager = getSupportFragmentManager();
        //    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAll_Movies()).addToBackStack(null).commit();
        //  showFragment(new FragmentAll_Movies(),"FragmentAll_Movies");







        if (!utilities.isNetworkAvailable()) {
            //  showFragment(new FragmentMyFavourite(),"FragmentMyFavourite");
            showFragment(new FragmentMyFavouriteNew(), "FragmentMyFavouriteNew");
        } else {
            showFragment(new FragmentAll_Movies(), "FragmentAll_Movies");
        }

//            Bundle bundle=getIntent().getExtras();
//            if (bundle!=null) {
//             String likeicon=bundle.getString("LIKE");
//
//
//             if(likeicon.equals("LIKE"))
//             {
//                 showFragment(new FragmentMyFavouriteNew(), "FragmentMyFavouriteNew");
//             }
//             else
//             {
//                 showFragment(new FragmentAll_Movies(), "FragmentAll_Movies");
//             }
//            }
//            else
//            {
//                showFragment(new FragmentAll_Movies(), "FragmentAll_Movies");
//            }








        Token= FirebaseInstanceId.getInstance().getToken();
        Log.i("Firebase token",""+Token);


        appState = AppSubClass.getInstance();
        sampleApp = appState.getPublicClient();
        authResult = appState.getAuthResult();
        scopes = Constants.SCOPES.split("\\s+");

        /* Calls API, dump out response from UserInfo endpoint into UI */
        this.callAPI();

    }

    //
    // Core Identity methods used by MSAL
    // ==================================
    // onActivityResult() - Catches the redirect from the system browser
    // callAPI() - Calls our api with new access token
    // editProfile() - Calls b2c edit policy with this temporary authority
    // clearCache() - Clears token cache of this app
    //

    //commented @Ankur
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        sampleApp.handleInteractiveRequestRedirect(requestCode, resultCode, data);
    }

    /* Use Volley to request the /me endpoint from API
     *  Sets the UI to what we get back
     */
    private void callAPI() {

        Log.d(TAG, "Starting volley request to API");

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject parameters = new JSONObject();

        try {
            parameters.put("key", "value");
        } catch (Exception e) {
            Log.d(TAG, "Failed to put parameters: " + e.toString());
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.API_URL,
                parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /* Successfully called API */
                Log.d(TAG, "Response: " + response);
                try {
                    Toast.makeText(getBaseContext(), "Response: " + response.get("name"), Toast.LENGTH_SHORT)
                            .show();
                } catch (JSONException e) {
                    Log.d(TAG, "JSONEXception Error: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                if (authResult!=null){
                    Log.d(TAG, "Token: " + authResult.getUser());
                    headers.put("Authorization", "Bearer " + authResult.getAccessToken());
                }

                return headers;
            }
        };

        queue.add(request);

    }

    /* Use Volley to request the /me endpoint from API
     *  Sets the UI to what we get back
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        menu.findItem(R.id.action_tuneUp).setVisible(true);
        menu.findItem(R.id.action_person).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // ActivityCompat.finishAffinity(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            Log.i(getClass().getName(),
                    "stack count: " + fragmentManager.getBackStackEntryCount());
        } else {
            dialogExitApp(this, getResources().getString(R.string.do_you_want_to_close_the_app));

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(MainActivity.this, BrainTreeActivity.class));
                finish();
                return true;

            case R.id.action_tuneUp:
              Intent intent = new Intent(MainActivity.this, FilterActivity.class);
               startActivity(intent);


//                Intent intent=new Intent(MainActivity.this, BrainTreeActivity.class);
//                startActivity(intent);
//
//                Intent intent = new Intent(MainActivity.this, ButtonActivity.class);
//                startActivity(intent);

                return true;
            case R.id.action_person:
//                Intent intent1 = new Intent(MainActivity.this, MovieDetailsActivity.class);
                Intent intent1 = new Intent(MainActivity.this, ProfileActivityNew.class);
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_movie:
                //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAll_Movies()).addToBackStack(null).commit();
                showFragment(new FragmentAll_Movies(), "FragmentAll_Movies");

                break;
            case R.id.navigation_tv:
                //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAll_Movies()).addToBackStack(null).commit();
                showFragment(new FragmentAll_Movies(false), "FragmentAll_Movies");
                break;

            case R.id.navigation_recent:

                showFragment(new FragmentRecentMovies(), "FragmentRecentMovies");
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRecentMovies()).addToBackStack(null).commit();
                break;

            case R.id.navigation_myMovies:

                //  showFragment(new FragmentMyFavourite(),"FragmentMyFavourite");
                showFragment(new FragmentMyFavouriteNew(), "FragmentMyFavouriteNew");
                //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMyFavourite()).addToBackStack(null).commit();
                break;
            case R.id.navigation_info:


                Intent intent=new Intent(MainActivity.this,HelpandInfoActivity.class);
                startActivity(intent);
//                showFragment(new FragmentHelpandInfo(), "FragmentHelpandInfo");
                //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHelpandInfo()).addToBackStack(null).commit();
                break;
        }

        return true;

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }


    private void showFragment(Fragment targetFragment, String className) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, targetFragment, className);
        ft.commitAllowingStateLoss();
    }



    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(MainActivity.this, MainActivity.class);  //your class
        startActivity(i);
        Log.i("h", "onRestart:");
        finish();
    }


    public void dialogExitApp(final Context context, String message) {
        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_box_yes_no);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.confirm));
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText(Html.fromHtml("" + message));
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
                System.exit(0);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void  loadLocal(){
        if (appSession.getLanguage()!=null) {
            String ghdfdh = appSession.getLanguage();
            com.google.android.exoplayer2.util.Log.i("Language 1", "" + ghdfdh);

        }
//        SharedPreferences editor=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
//        String language=editor.getString("My_lang","");
//        setLocale(language);
    }


}