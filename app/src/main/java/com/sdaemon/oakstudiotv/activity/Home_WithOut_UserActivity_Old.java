package com.sdaemon.oakstudiotv.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.ILoggerCallback;
import com.microsoft.identity.client.Logger;
import com.microsoft.identity.client.MsalClientException;
import com.microsoft.identity.client.MsalException;
import com.microsoft.identity.client.MsalServiceException;
import com.microsoft.identity.client.MsalUiRequiredException;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.User;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.Home_withoutUser_Adapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.SignInPageSliderDTO;
import com.sdaemon.oakstudiotv.dto.UsersDTO;
import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.Constants;
import com.sdaemon.oakstudiotv.utils.Helpers;
import com.sdaemon.oakstudiotv.utils.MyService;
import com.sdaemon.oakstudiotv.utils.Utilities;
import com.sdaemon.oakstudiotv.view.DialogValidation;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_WithOut_UserActivity_Old extends AppCompatActivity implements View.OnClickListener,AppConstants {

    private static final String TAG = Home_WithOut_UserActivity_Old.class.getSimpleName();
    /* Global App State */
    Context mContext;
    AppSubClass state;
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private Button btn_joinforFree, btn_sign;
    private int dotscount;
    private ImageView[] dots;
    private BottomNavigationView bottomNavigationView;
    /* Azure AD variables */
    private PublicClientApplication sampleApp;
    private AuthenticationResult authResult;
    private String[] scopes;
    private String strEmail = "";
    private String strPass = "";
    Utilities utilities;
    AppSession appSession;
    private ArrayList<String> imagesViewpagerSlideArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__with_out__user);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_only_logo);
        mContext = this;
        appSession = AppSession.getInstance(mContext);
        utilities = Utilities.getInstance(mContext);
      //  mContext.registerReceiver(brRefresh, new IntentFilter(REFRESH_LIST));
        initialize();

    }

    private void initialize() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        btn_joinforFree = (Button) findViewById(R.id.btn_joinforFree);
        btn_sign = (Button) findViewById(R.id.btn_sign);
        btn_joinforFree.setOnClickListener(this);
        btn_sign.setOnClickListener(this);


        SignInPageSliderDTO sliderDTO=new SignInPageSliderDTO();

        Home_withoutUser_Adapter viewPagerAdapter = new Home_withoutUser_Adapter(this,imagesViewpagerSlideArray,sliderDTO);
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];
        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //AD Inisialization code

        state = (AppSubClass) getApplicationContext();
        scopes = Constants.SCOPES.split("\\s+");

        /* Initializes the app context using MSAL */
        sampleApp = state.getPublicClient();

        /* Initialize the MSAL App context */
        if (sampleApp == null) {
            sampleApp = new PublicClientApplication(
                    this.getApplicationContext(),
                    Constants.CLIENT_ID,
                    String.format(Constants.AUTHORITY, Constants.TENANT, Constants.SISU_POLICY));
            state.setPublicClient(sampleApp);

            Logger.getInstance().setExternalLogger(new ILoggerCallback() {
                @Override
                public void log(String tag, Logger.LogLevel logLevel, String message, boolean containsPII) {
                    Log.d(tag, "MSAL_LOG: " + message);
                }
            });
        }

      onCallApiClicked(scopes);

    }

    //
    // Core Identity methods used by MSAL
    // ==================================
    // onActivityResult() - handles redirect from System browser
    // onCallApiClicked() - attempts to get tokens for our api, if it succeeds calls api & updates UI
    //

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        sampleApp.handleInteractiveRequestRedirect(requestCode, resultCode, data);
    }

    /* Use MSAL to acquireToken for the end-user
     *  Call API
     *  Pass UserInfo response data to AuthenticatedActivity
     */
    private void onCallApiClicked(String[] scopes) {

        /* Attempt to get a user and acquireTokenSilently
         * If this fails we will do an interactive request
         */
        Log.d(TAG, "Call API Clicked");

        try {
            User currentUser = Helpers.getUserByPolicy(sampleApp.getUsers(), Constants.SISU_POLICY);
            Log.i(String.valueOf(Home_WithOut_UserActivity_Old.this),"################currentUser :"+currentUser);

            if (currentUser != null) {
                /* We have 1 user */

                Log.i(String.valueOf(Home_WithOut_UserActivity_Old.this),"################acquireTokenSilentAsync currentUser IF :"+currentUser);
                sampleApp.acquireTokenSilentAsync(
                        scopes,
                        currentUser,
                        String.format(Constants.AUTHORITY, Constants.TENANT, Constants.SISU_POLICY),
                        false,
                        getAuthSilentCallback());
            } else {
                Log.i(String.valueOf(Home_WithOut_UserActivity_Old.this),"################ acquireToken currentUser ELSE :"+currentUser);
                /* We have no user */
              //  startActivity(new Intent(Home_WithOut_UserActivity.this, MainActivity.class)); //change code
                //finish();                                                                                    // change code
                sampleApp.acquireToken(getActivity(), scopes, getAuthInteractiveCallback());
            }
        } catch (MsalClientException e) {
            /* No token in cache, proceed with normal unauthenticated app experience */
            Log.d(TAG, "MSAL Exception Generated while getting users: " + e.toString());

        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "User at this position does not exist: " + e.toString());
        }
    }

    //
    // App callbacks for MSAL
    // ======================
    // getActivity() - returns activity so we can acquireToken within a callback
    // getAuthSilentCallback() - callback defined to handle acquireTokenSilent() case
    // getAuthInteractiveCallback() - callback defined to handle acquireToken() case
    //

    public Activity getActivity() {
        return this;
    }

    /* Callback used in for silent acquireToken calls.
     * Looks if tokens are in the cache (refreshes if necessary and if we don't forceRefresh)
     * else errors that we need to do an interactive request.
     */
    private AuthenticationCallback getAuthSilentCallback() {
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
                startAuthenticated();
                /* Successfully got a token, call api now */
                Log.d(TAG, "Successfully authenticated");
                authResult = authenticationResult;

                Log.i(String.valueOf(Home_WithOut_UserActivity_Old.this),"################getAuthSilentCallback :"+authenticationResult);

                Log.d(TAG, "getAccessToken22: " + authenticationResult.getAccessToken());
                Log.d(TAG, "getTenantId22: " + authenticationResult.getTenantId());
                Log.d(TAG, "getUniqueId22: " + authenticationResult.getUniqueId());
                Log.d(TAG, "getExpiresOn22: " + authenticationResult.getExpiresOn());
                Log.d(TAG, "getScope22: " + authenticationResult.getScope());
                Log.d(TAG, "getUser22: " + authenticationResult.getUser());

                Log.d(TAG, "getUser getDisplayableId 22: " + authenticationResult.getUser().getDisplayableId());
                Log.d(TAG, "getUser getIdentityProvider 22: " + authenticationResult.getUser().getIdentityProvider());
                Log.d(TAG, "getUser getName 22: " + authenticationResult.getUser().getName());
                Log.d(TAG, "getUser getUserIdentifier 22: " + authenticationResult.getUser().getUserIdentifier());

                state.setAuthResult(authResult);

                /* Start authenticated activity */

            }

            @Override
            public void onError(MsalException exception) {
                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: " + exception.toString());

                if (exception instanceof MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                    assert true;

                } else if (exception instanceof MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                    assert true;

                } else if (exception instanceof MsalUiRequiredException) {
                    /* Tokens expired or no session, retry with interactive */
                    sampleApp.acquireToken(getActivity(), scopes, getAuthInteractiveCallback());
                }
            }

            @Override
            public void onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
    }

    /* Callback used for interactive request.  If succeeds we use the access
     * token to call the api. Does not check cache.
     */
    private AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
                /* Start authenticated activity */
                startAuthenticated();
                /* Successfully got a token, call api now */
                Log.i(String.valueOf(Home_WithOut_UserActivity_Old.this),"################getAuthInteractiveCallback authenticationResult  :"+authenticationResult);
                Log.d(TAG, "Successfully authenticated");
                Log.d(TAG, "ID Token: " + authenticationResult.getIdToken());


                Log.d(TAG, "getAccessToken11: " + authenticationResult.getAccessToken());
                Log.d(TAG, "getTenantId11: " + authenticationResult.getTenantId());
                Log.d(TAG, "getUniqueId11: " + authenticationResult.getUniqueId());
                Log.d(TAG, "getExpiresOn11: " + authenticationResult.getExpiresOn());
                Log.d(TAG, "getScope11: " + authenticationResult.getScope());
                Log.d(TAG, "getUser11: " + authenticationResult.getUser());

                Log.d(TAG, "getUser getDisplayableId 11: " + authenticationResult.getUser().getDisplayableId());
                Log.d(TAG, "getUser getIdentityProvider 11: " + authenticationResult.getUser().getIdentityProvider());
                Log.d(TAG, "getUser getName 11: " + authenticationResult.getUser().getName());
                Log.d(TAG, "getUser getUserIdentifier 11: " + authenticationResult.getUser().getUserIdentifier());

                authResult = authenticationResult;
                state.setAuthResult(authResult);
            }

            @Override
            public void onError(MsalException exception) {
                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: " + exception.toString());

                if (exception instanceof MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                    assert true;
                } else if (exception instanceof MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                    assert true;
                }
            }
            @Override
            public void onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
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
        menu.findItem(R.id.action_english).setVisible(true);
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
        if (id == R.id.action_english) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_joinforFree:
//                Intent intent = new Intent(Home_WithOut_UserActivity.this, SignUPActivity.class);
//                startActivity(intent);
              //  onCallApiClicked(scopes);



//                if (brRefresh != null)
//                    mContext.unregisterReceiver(brRefresh);
           //     appSession.setOfflineDate(null);
              //  stopService(new Intent(this, MyServiceSimple.class));

/*

//                appSession.setOfflineDate("3/10/2019");
//                startService(new Intent(this, MyServiceSimple.class));

                if(appSession!=null) {
                    Calendar c = Calendar.getInstance();
                   // SimpleDateFormat dateformat = new SimpleDateFormat("d/M/yyyy h:m aa");
                  //  SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy hh:mm:ss");
                    SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
                    String datetime = dateformat.format(c.getTime());
                    Log.i(String.valueOf(mContext), "###################### Date: " + datetime);
                    //  appSession.setOfflineDate(datetime);
                    appSession.setOfflineDate("3/10/2019");
                    startService(new Intent(this, MyServiceSimple.class));
                }


*/




               startActivity(new Intent(Home_WithOut_UserActivity_Old.this, MainActivity.class));
               finish();



//            strEmail = etEmail.getText().toString().trim();
//            strPass = etPassword.getText().toString();
//
//                strEmail ="rajendra.sdaemon@gmail.com";
//                strPass = "Rajendra@1a";
//                if(isValid()){
//                    Toast.makeText(mContext, "Call Api", Toast.LENGTH_SHORT).show();
//                    getLogIn(strEmail,strPass);
//                }



//               ArrayList<NationalParkDTO>arrayList = new ArrayList<>();
//               NationalParkDTO nationalParkDTO = new NationalParkDTO();
//               nationalParkDTO.setTitleEn("Title Rajendra");
//               nationalParkDTO.setMessageEN("Message Rajendra");
//               nationalParkDTO.setType("Type Rajendra");
//              //  arrayList.addAll(result);
//                arrayList.add(nationalParkDTO);
//             //   if(appSession!=null);
//              //  appSession.getNotificationList().clear();
//                appSession.setNotificationList(arrayList);
//                Log.i("============== ", "===========" + arrayList.size());
//
//                startService();
                break;

            case R.id.btn_sign:
//                Intent intent1=new Intent(Home_WithOut_UserActivity.this, Sign_inActivity.class);
//                startActivity(intent1);
                onCallApiClicked(scopes);
                break;/**/
        }
    }

    public void startService() {
        try {
            Log.i(getClass().getName(), "=====startService========");
            Calendar cal = Calendar.getInstance();
            Intent intent = new Intent(getActivity(), MyService.class);
            intent.setData((Uri.parse("LAT_LON_S1001")));
            PendingIntent pintent = PendingIntent.getService(getActivity(), 0,
                    intent, 0);
            AlarmManager alarm = (AlarmManager) getActivity()
                    .getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(),
                    30 * 1000, pintent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Starts authenticated intent */
    private void startAuthenticated() {
        startActivity(new Intent(Home_WithOut_UserActivity_Old.this, MainActivity.class));
        finish();
    }


    private Boolean isValid() {
        Boolean isValid = true;
        ArrayList arrayList = new ArrayList<String>();
        if (TextUtils.isEmpty(strEmail)) {
            isValid = false;
            arrayList.add(getString(R.string.email));
        } else {
            if (!utilities.checkEmail(strEmail)) {
                arrayList.add(getResources().getString(R.string.email_invalid));
                isValid = false;
            }
        }
        if (TextUtils.isEmpty(strPass)) {
            isValid = false;
            arrayList.add(getString(R.string.password));
        } else if (strPass.length() < 6) {
            isValid = false;
            arrayList.add(getString(R.string.password_valid));
        }
        if (!isValid)
            dialogValidation(getActivity(), arrayList);
        return isValid;
    }

    Dialog dialogValidation = null;
    public void dialogValidation(Context context, ArrayList<String> messages) {
        if (dialogValidation != null && dialogValidation.isShowing())
            dialogValidation.dismiss();
        dialogValidation = new DialogValidation(context, messages);
        dialogValidation.show();
    }

    private void getLogIn( String email, String password) {
       // progressBar.show();

        Call<UsersDTO> call = RetroClient.sdaemonApi().login(email);
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<UsersDTO>() {
            @Override
            public void onResponse(Call<UsersDTO> call, Response<UsersDTO> response) {
           //     progressBar.dismiss();
                if (response.code() == 200) {
                    Toast.makeText(mContext, "login successfully", Toast.LENGTH_SHORT).show();
//                    appSession.setUserDTO(response.body());
//                    startActivity(new Intent(getActivity(),MainActivity.class));
//                    getActivity().finish();
                } else if (response.code() == 409) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String msg = jObjError.getString("message");
                        showDialoge(mContext, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());/**/
                        String msg = jObjError.getString("message");
                        showDialoge(mContext, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UsersDTO> call, Throwable t) {
            //    progressBar.dismiss();
                showDialoge(mContext, "", "" + t.getMessage());
            }
        });
    }

    public void showDialoge(Context context, String title, String msg) {
        try {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context, R.style.dialoge);
            builder.setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })

                    .show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

//    Bundle bundle = new Bundle();
//    String type = "";
//    BroadcastReceiver brRefresh = new BroadcastReceiver() {
//        @Override
//        public void onReceive(final Context context, Intent intent) {
//            try {
//                // Bundle bundle = intent.getExtras();
//                bundle = intent.getExtras();
//                if (bundle != null) {
//                    if (bundle.containsKey(PN_TYPE))
//                        type = bundle.getString(PN_TYPE);
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.i(getClass().getName(), "============ run  type :"+type);
//
//                        }
//                    }, 100);
//
//                    return;
//
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };


}