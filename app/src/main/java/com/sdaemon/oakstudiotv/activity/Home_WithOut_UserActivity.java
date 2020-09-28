package com.sdaemon.oakstudiotv.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
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
import com.sdaemon.oakstudiotv.activity.signUp.SignUPActivity;
import com.sdaemon.oakstudiotv.adapter.Home_withoutUser_Adapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.CategoryFilterDTO;
import com.sdaemon.oakstudiotv.dto.PlanFinalDTO;
import com.sdaemon.oakstudiotv.dto.SignInPageSliderDTO;
import com.sdaemon.oakstudiotv.dto.UserStatusDTO;
import com.sdaemon.oakstudiotv.dto.UsersDTO;
import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.Constants;
import com.sdaemon.oakstudiotv.utils.Helpers;
import com.sdaemon.oakstudiotv.utils.MyService;
import com.sdaemon.oakstudiotv.utils.Utilities;
import com.sdaemon.oakstudiotv.view.DialogValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

//4e29f5c5-3788-4ca5-b077-aad3f622faf6
public class Home_WithOut_UserActivity extends AppCompatActivity implements View.OnClickListener, AppConstants {
    private static final String TAG = Home_WithOut_UserActivity.class.getSimpleName();
    /* Global App State */
    Context mContext;
    AppSubClass state;
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel, llProgress;
    ProgressBar progressBar;
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
    private String[] PERMISSIONS = {READ_PHONE_STATE};
    String deviceId = "";
    private Retrofit retrofit;
    private ArrayList<String> imagesViewpagerSlideArray = new ArrayList<String>();
    Home_withoutUser_Adapter viewPagerAdapter;
    ImageView ivoffline;
    TextView tvoffline;
    SwipeRefreshLayout swipeRefreshLayout;
    static Boolean slider=true;
    boolean value=true;
    List<SignInPageSliderDTO.Table> table=new ArrayList<>();



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
        // Log.d(TAG, "88888888888888 : "+ utilities.getIMEIorDeviceId());
        progressBar = (ProgressBar) findViewById(R.id.ll_progress);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_signinPage);

        btn_joinforFree = (Button) findViewById(R.id.btn_joinforFree);
        btn_sign = (Button) findViewById(R.id.btn_sign);
        btn_joinforFree.setOnClickListener(this);
        btn_sign.setOnClickListener(this);

        progressBar.setVisibility(View.GONE);
        ivoffline=findViewById(R.id.iv_offline);
        tvoffline=findViewById(R.id.tv_offline);

        if (!utilities.isNetworkAvailable()){
            ivoffline.setVisibility(View.VISIBLE);
            tvoffline.setVisibility(View.VISIBLE);
            tvoffline.setText(getResources().getString(R.string.you_re_offline));
            ivoffline.setImageResource(R.drawable.ic_cloud_off_black_24dp);
            Toast.makeText(mContext, "no internet connection", Toast.LENGTH_SHORT).show();
        }else {
            progressBar.setVisibility(View.VISIBLE);
            btn_sign.setVisibility(View.VISIBLE);
            ivoffline.setVisibility(View.GONE);
            tvoffline.setVisibility(View.GONE);
            getsliderImages();

        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!utilities.isNetworkAvailable()){
//                    viewPagerAdapter.notifyDataSetChanged();
//                    imagesViewpagerSlideArray.clear();
                     value=false;
                    viewPagerAdapter = new Home_withoutUser_Adapter(Home_WithOut_UserActivity.this);
                    viewPager.setAdapter(viewPagerAdapter);
                    btn_sign.setVisibility(GONE);
                    ivoffline.setVisibility(View.VISIBLE);
                    tvoffline.setVisibility(View.VISIBLE);
                    tvoffline.setText(getResources().getString(R.string.you_re_offline));
                    ivoffline.setImageResource(R.drawable.ic_cloud_off_black_24dp);
                    Toast.makeText(mContext, "no internet connection", Toast.LENGTH_SHORT).show();
                }
                else {
                //                    if (table!=null){
//                        slider=false;
//                        getsliderImages();
//
//                    }else {

                    if (ivoffline.getVisibility()== VISIBLE && tvoffline.getVisibility()== VISIBLE)
                    {
                        slider = true;
                        btn_sign.setVisibility(View.VISIBLE);
                        ivoffline.setVisibility(View.GONE);
                        tvoffline.setVisibility(View.GONE);
                        getsliderImages();

                    }else {
                        slider = false;
                        progressBar.setVisibility(View.VISIBLE);
                        btn_sign.setVisibility(View.VISIBLE);
                        ivoffline.setVisibility(View.GONE);
                        tvoffline.setVisibility(View.GONE);
                        getsliderImages();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);

            }
        });


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
            sampleApp.setValidateAuthority(false);

            Logger.getInstance().setExternalLogger(new ILoggerCallback() {
                @Override
                public void log(String tag, Logger.LogLevel logLevel, String message, boolean containsPII) {
                    Log.d(tag, "MSAL_LOG: " + message);
                }
            });
        }

        //  onCallApiClicked(scopes);

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
            Log.i(String.valueOf(Home_WithOut_UserActivity.this), "################ acquireTokencurrentUser :" + currentUser);
            /* We have no user */
            //  startActivity(new Intent(Home_WithOut_UserActivity.this, MainActivity.class)); //change code
            //finish();                                                                                    // change code
            sampleApp.acquireToken(getActivity(), scopes, getAuthInteractiveCallback());

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
                //     startAuthenticated();
                /* Successfully got a token, call api now */
                Log.d(TAG, "Successfully authenticated");
                authResult = authenticationResult;
                Log.i(String.valueOf(Home_WithOut_UserActivity.this), "################getAuthSilentCallback :" + authenticationResult);
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
    public AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
                /* Start authenticated activity */
                //  startAuthenticated();

                /* Successfully got a token, call api now */
                Log.i(String.valueOf(Home_WithOut_UserActivity.this), "################getAuthInteractiveCallback authenticationResult  :" + authenticationResult);
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
                //    startAuthenticated();
                getLogIn(authenticationResult.getUniqueId());
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
                progressBar.setVisibility(View.GONE);
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
                Intent intent = new Intent(Home_WithOut_UserActivity.this, SignUPActivity.class);
                startActivity(intent);
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

//
//                startActivity(new Intent(Home_WithOut_UserActivity.this, MainActivity.class));
//                finish();


//                getLogIn("c20bbc8e-2eb3-418b-94de-2eb56c0e200b");
//                getLogIn("4e29f5c5-3788-4ca5-b077-aad3f622faf6");


                //    dialogFreeTrial(this, getResources().getString(R.string.free_trial_text));


                //    convertUTCtoLocalDateTimeTest("2019-04-03T05:31:28.417");


//                Intent intent2 = new Intent(Home_WithOut_UserActivity.this, ChromeCastActivity.class);
//                startActivity(intent2);


//                Call<RetroPhoto> call = restInterface.test("c20bbc8e-2eb3-418b-94de-2eb56c0e200b");
//            call.enqueue(new Callback<RetroPhoto>() {
//                @Override
//                public void onResponse(Call<RetroPhoto> call, Response<RetroPhoto> response) {
//                    Toast.makeText(mContext, "login successfully", Toast.LENGTH_SHORT).show();
//                    //    progressBar.dismiss();
//                    if (response.code() == 200) {
//                        Toast.makeText(mContext, "login successfully", Toast.LENGTH_SHORT).show();
//
//
//             /*       appSession.setUserDTO(response.body());
//                    startActivity(new Intent(getActivity(),MainActivity.class));
//                    getActivity().finish();
//
//
//*/
//
//
//
//                    } else if (response.code() == 409) {
//                        try {
//                            JSONObject jObjError = new JSONObject(response.errorBody().string());
//                            String msg = jObjError.getString("message");
//                            showDialoge(mContext, "", msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        try {
//                            JSONObject jObjError = new JSONObject(response.errorBody().string());
//                            String msg = jObjError.getString("message");
//                            showDialoge(mContext, "", msg);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<RetroPhoto> call, Throwable t) {
//                    //   progressBar.dismiss();
//                    showDialoge(mContext, "", "" + t.getMessage());
//                }
//            });
//
//


//                if (TextUtils.isEmpty(deviceId) || deviceId == "") {
//                    if (!hasPermissions(getActivity(), PERMISSIONS)) {
//                        //     ActivityCompat.requestPermissions((Activity) getActivity(), PERMISSIONS, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            requestPermissions(PERMISSIONS, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//                        }
//                    } else {
//                        Log.i(String.valueOf(mContext), "7777777777777777 22 ");
//                        deviceId = utilities.getIMEIorDeviceId();
//                        Log.i(String.valueOf(mContext), "7777777777777 33 : " + deviceId);
//                    }
//
//                }


//                String  s1=   convertUTCtoLocalDateTime(2019-03-28T07:14:40Z)  UTC to local
//                String  s = getUTCFormattedTimeString("28/03/2019 03:50:42 PM"); // Local to UTC
//                Log.i(String.valueOf(mContext), "###################### UTC: " + s);
//



/*


              //  String dateStr = "Jul 16, 2013 12:08:59 AM";
                String dateStr = "Mar 28, 2019 07:00:00 AM";
                SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a", Locale.ENGLISH);
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = null;
                try {
                    date = df.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                df.setTimeZone(TimeZone.getDefault());
                String formattedDate = df.format(date);

                Log.i(String.valueOf(mContext), "###################### UTC: " + formattedDate);



*/


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

                //https://github.com/Azure-Samples/active-directory-b2c-android-native-msal/tree/master
//                azure ad b2c  samples
//                https://docs.microsoft.com/en-us/azure/active-directory-b2c/code-samples
//                https://github.com/Azure-Samples/active-directory-b2c-android-native-appauth/tree/master/app
//                https://github.com/Azure-Samples/active-directory-b2c-android-native-msal
//                https://docs.microsoft.com/en-us/azure/active-directory/develop/quickstart-v1-android
//                https://github.com/Azure-Samples/active-directory-b2c-android-appauth-ropc/tree/master/app
//                https://github.com/uddish/DynamicLayouts/blob/master/app/src/main/java/com/example/uddishverma/dynamiclayoutsexample/MainActivity.java


                break;

            case R.id.btn_sign:
//               Intent intent1=new Intent(Home_WithOut_UserActivity.this, MainActivity.class);
//               startActivity(intent1);

                progressBar.setVisibility(View.VISIBLE);
                onCallApiClicked(scopes);
                //   getData();


                break;
        }
    }


    private void getData() {
        //  progressBar.show();
        //    String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<PlanFinalDTO> call = RetroClient.sdaemonApi().testrr("$2a$10$0aU.MrwoZHUOAadZPnedG.nKrUWy6HJ6nqpMezKR2MHMqAdisL.6.");
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<PlanFinalDTO>() {
            @Override
            public void onResponse(Call<PlanFinalDTO> call, Response<PlanFinalDTO> response) {
                Log.i(String.valueOf(mContext), "=========RESPONSE: " + response.body());
                //  setData(response.body());

                JSONObject PersonObject = new JSONObject();

                JSONArray personarray = new JSONArray();

                try {
                    for (int j = 0; j < 2; j++) {
                        for (int i = 0; i < 3; i++) {
                            JSONObject person1 = new JSONObject();
                            //adding items to first json object
                            person1.put("status", "" + i);
                            //adding json objects to json array
                            personarray.put(person1);
                            //adding json array to json object
                            PersonObject.put("Persons", personarray);
                            PersonObject.put("SubDescription", "Rajendra");
                        }
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                //final json string
                String JsonData = PersonObject.toString();
                Log.i(String.valueOf(mContext), "=========jsonString: " + JsonData);

            }

            @Override
            public void onFailure(Call<PlanFinalDTO> call, Throwable t) {

                showDialoge(mContext, "", "" + t.getMessage());
            }
        });
    }


    public String convertUTCtoLocalDateTimeTest(String utcDate) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        //  SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date date = new Date();
        try {
            //  d = input.parse("2018-02-02T06:54:57.744Z");
            //  d = input.parse("2019-03-28T07:14:40Z");
            date = input.parse(utcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(date);
        Log.i(String.valueOf(mContext), "######################  UTC to LOCAl: " + formatted);
        return formatted;

    }


    public String convertUTCtoLocalDateTime(String utcDate) {

        //  SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date date = new Date();
        try {
            //  d = input.parse("2018-02-02T06:54:57.744Z");
            //  d = input.parse("2019-03-28T07:14:40Z");
            if (utcDate!=null) {
                date = input.parse(utcDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatted = output.format(date);
        Log.i(String.valueOf(mContext), "###################### LOCAl: " + formatted);
//        Toast.makeText(mContext, "Local Date  :" +formatted, Toast.LENGTH_SHORT).show();
        return formatted;

    }

    //  28/03/2019 03:50:42 PM   // localDate
    public String getUTCFormattedTimeString(String localDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SS'Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(localDate);

            //this format changeable
            //   sdf.setTimeZone(TimeZone.getDefault());
            localDate = sdf.format(value);
        } catch (Exception e) {
//            localDate = "00-00-0000 00:00";
        }
//        Toast.makeText(mContext, "Local Date: " +localDate, Toast.LENGTH_SHORT).show();
        return localDate;
    }


//
//  //  https://github.com/AzureAD/microsoft-authentication-library-for-android/releases
//    private void onSignOutClicked() {
//
//        /* Attempt to get a account and remove their cookies from cache */
//        List<User> accounts = null;
//
//        try {
//            accounts = sampleApp.getUsers();
//
//            if (accounts == null) {
//                /* We have no accounts */
//
//            } else if (accounts.size() == 1) {
//                /* We have 1 account */
//                /* Remove from token cache */
//              //  sampleApp.removeAccount(accounts.get(0));
//                sampleApp.remove(accounts.get(0));
//
//              //  updateSignedOutUI();
//
//                Toast.makeText(getBaseContext(), "updateSignedOutUI", Toast.LENGTH_SHORT)
//                        .show();
//
//            }
//            else {
//                /* We have multiple accounts */
//                for (int i = 0; i < accounts.size(); i++) {
//                  //  sampleApp.removeAccount(accounts.get(i));
//                    sampleApp.remove(accounts.get(i));
//                }
//            }
//
//            Toast.makeText(getBaseContext(), "Signed Out!", Toast.LENGTH_SHORT)
//                    .show();
//
//        } catch (MsalClientException e) {
//            Log.d(TAG, "User at this position does not exist: " + e.toString());
//        }
//    }
//
//
//


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
        Log.i("LoginStatus","Start Authenticated");

        //        startActivity(new Intent(Home_WithOut_UserActivity.this, MainActivity.class));
        //        finish();
        progressBar.setVisibility(View.GONE);
        Intent i = new Intent(mContext, MainActivity.class);
        // finish();  //Kill the activity from which you will go to next activity
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }


    private void getLogIn(String UniqueId) {
        Log.i("Unique Id :" ,""+UniqueId);

        //  progressBar.show();
        //    String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<UsersDTO> call = RetroClient.sdaemonApi().login(UniqueId);
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<UsersDTO>() {
            @Override
            public void onResponse(Call<UsersDTO> usersDTOCall, Response<UsersDTO> response) {
                // Toast.makeText(mContext, "login successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                UsersDTO usersDTO=response.body();
                Log.i("Login Response :" ,""+response.body());

                //    if (response.code() == 200) {

                //  showDialoge(mContext, "", "" + response.body().getMessage());


                //  if (response.body().getResult().toString().equalsIgnoreCase("No Data Available")) {
//                if (response.body().getMessage()==null && response.body().getMessage().equals("")) {
//                    Toast.makeText(mContext, response.body().getResult().toString(), Toast.LENGTH_SHORT).show();
//                } else {
//                if (response.body().getMessage()!=null){
                if (usersDTO!=null){
                if (usersDTO.getResult().isActiveStatus()==true){
                    Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    appSession.setUserDTO(response.body());

                    if (appSession!=null){
                        Log.i(String.valueOf(mContext), "====== USER F NAME :" + appSession.getUserDTO().getResult().getFirstName());
                        Log.i(String.valueOf(mContext), "====== USER L NAME :" + appSession.getUserDTO().getResult().getLastName());
//                Log.i(String.valueOf(mContext), "User first name :" + appSession.getUsersDTO().getResult().getFirstName());
                    }


                    getUserLoginStatus(UniqueId);

                   }
                }

//                }




                //                   startActivity(new Intent(getActivity(),MainActivity.class));
//                    getActivity().finish();



                //    startAuthenticated();


//                } else if (response.code() == 409) {
                //    }


//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        String msg = jObjError.getString("message");
//                        showDialoge(mContext, "", msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        String msg = jObjError.getString("message");
//                        showDialoge(mContext, "", msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }

            }

            @Override
            public void onFailure(Call<UsersDTO> call, Throwable t) {
                Log.i("Login Error :" ,""+t.getMessage());
                progressBar.setVisibility(View.GONE);
                showDialoge(mContext, "", "" + t.getMessage());
            }
        });
    }


    private void getUserLoginStatus(String UniqueId) {
        //  progressBar.show();
        //    String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<UserStatusDTO> call = RetroClient.sdaemonApi().loginStatus(UniqueId);
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<UserStatusDTO>() {
            @Override
            public void onResponse(Call<UserStatusDTO> call, Response<UserStatusDTO> response) {
                // Toast.makeText(mContext, "login successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                //    if (response.code() == 200) {

//                Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                appSession.setUserStatusList(response.body().getResultList());
                String profileID=response.body().getResultList().get(0).getUserProfileID().toString();
                Log.i("Profile ID",""+profileID);
                getUserplanDetails(profileID);

                convertUTCtoLocalDateTime(response.body().getResultList().get(0).getCreateDate());

                //                   startActivity(new Intent(getActivity(),MainActivity.class));
//                    getActivity().finish();

                Log.i(String.valueOf(mContext), "====== APP SESSION LIST CreateDate :" + appSession.getUserStatusList().get(0).getCreateDate());
                Log.i("LoginStatus",""+response.body().getStatus());
//                startAuthenticated();


//                } else if (response.code() == 409) {
                //    }


//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        String msg = jObjError.getString("message");
//                        showDialoge(mContext, "", msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        String msg = jObjError.getString("message");
//                        showDialoge(mContext, "", msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }

            }

            @Override
            public void onFailure(Call<UserStatusDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showDialoge(mContext, "", "" + t.getMessage());
            }
        });
    }




    private void getUserplanDetails(String profileID) {
        //  progressBar.show();
        //    String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<JsonArray> call = RetroClient.sdaemonApi().planStatus(profileID);
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                // Toast.makeText(mContext, "login successfully", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    Toast.makeText(Home_WithOut_UserActivity.this, "succes", Toast.LENGTH_SHORT).show();

//                    llProgress.setVisibility(View.GONE);
                        if (response.code() == 200) {

                            JsonArray jsonArra1 = response.body();
//                    String status=jsonArra1.getAsJsonArray().get(0).getAsJsonObject().get("FreePlanStatus").toString();
                            int status1 = Integer.parseInt(jsonArra1.getAsJsonArray().get(0).getAsJsonObject().get("FreePlanStatus").toString());
                            appSession.setPlanStatus(jsonArra1.getAsJsonArray().get(0).getAsJsonObject().get("FreePlanStatus").toString());
                            Log.i("Plan Status",""+status1);

                            if (status1 == 1) {
                                startAuthenticated();
                            } else {
                                Intent intent = new Intent(Home_WithOut_UserActivity.this, UserSelectPlanAgainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                }
                else if (response.code() == 404) {
                            Toast.makeText(Home_WithOut_UserActivity.this,""+response.message(),Toast.LENGTH_LONG).show();
                    }


//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        String msg = jObjError.getString("message");
//                        showDialoge(mContext, "", msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        String msg = jObjError.getString("message");
//                        showDialoge(mContext, "", msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error", "onResponse: "+t.getMessage());
                Toast.makeText(Home_WithOut_UserActivity.this,""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                showDialoge(mContext, "", "" + t.getMessage());
            }
        });
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

   /* private void getLogIn(String email, String password) {
        // progressBar.show();

    // Call<UserDTO> call = RetroClient.sdaemonApi().login(email, password);
     Call<UserDTO> call = RetroClient.sdaemonApi().login(email);
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
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
                        JSONObject jObjError = new JSONObject(response.errorBody().string());*//**//*
                        String msg = jObjError.getString("message");
                        showDialoge(mContext, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                //    progressBar.dismiss();
                showDialoge(mContext, "", "" + t.getMessage());
            }
        });
    }*/

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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public void HomePageslider(){
//        Call<CategoryFilterDTO> call = RetroClient.sdaemonApi().getHomePageContent(useid,uniqueid);
//
//        call.enqueue(new Callback<CategoryFilterDTO>() {
//            @Override
//            public void onResponse(Call<CategoryFilterDTO> call, Response<CategoryFilterDTO> response) {
//                if (response.isSuccessful()){
//                    progressBar.setVisibility(GONE);
//
////                        Toast.makeText(mContext,"Home Page",Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategoryFilterDTO> call, Throwable t) {
//                Log.i("Home Page slider",""+t.getMessage());
//
////                    Toast.makeText(mContext,"Home Page  :" +t.getMessage(),Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }

//--------------------------------------------------------

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
//
//--------------------------------------------------------
    private boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        boolean allPermissionsGranted = true;
        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
        }
        if (!allPermissionsGranted) {
            boolean somePermissionsForeverDenied = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                    //denied
                    Log.e("denied", permission);
                } else {
                    if (ActivityCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED) {
                        //allowed
                        Log.e("allowed", permission);
                    } else {
                        //set to never ask again
                        Log.e("set to never ask again", permission);
                        somePermissionsForeverDenied = true;
                    }
                }
            }
            if (somePermissionsForeverDenied) {
                final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Permissions Required")
                        .setMessage("You have forcefully denied some of the required permissions " +
                                "for this action. Please open settings, go to permissions and allow them.")
                        .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getActivity().getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        } else {
            switch (requestCode) {
                //act according to the request code used while requesting the permission(s).

//                case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
//                    String[] PERMISSIONS = {Manifest.permission.CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE,READ_PHONE_STATE};
//                    if (hasPermissions(getActivity(), PERMISSIONS)) {
//                        deviceId = utilities.getIMEIorDeviceId();
//                        Log.i(String.valueOf(mContext), "777777777777777 44 " + deviceId);
//                    }
//                    return;
//                }


            }
        }
    }




    public void getsliderImages(){
        Log.i("SignInPage refresh",""+slider);

        Call<SignInPageSliderDTO> call = RetroClient.sdaemonApi().getSliderImages();

      call.enqueue(new Callback<SignInPageSliderDTO>() {
          @Override
          public void onResponse(Call<SignInPageSliderDTO> call, Response<SignInPageSliderDTO> response) {
              if (response.isSuccessful()){
                  progressBar.setVisibility(View.GONE);

                  SignInPageSliderDTO sliderDTO=response.body();

                  table=sliderDTO.getTable();

//                  Toast.makeText(Home_WithOut_UserActivity.this, ""+sliderDTO.getTable().get(0).getContentImageURL(), Toast.LENGTH_SHORT).show();

                  if (slider==true) {
                      for (int i = 0; i < sliderDTO.getTable().size(); i++) {
                          imagesViewpagerSlideArray.add(sliderDTO.getTable().get(i).getContentImageURL());
                      }
                  }

                  Log.i("SignInPage slider",""+sliderDTO.getTable().size()+"  "+imagesViewpagerSlideArray.size());


                  viewPagerAdapter = new Home_withoutUser_Adapter(Home_WithOut_UserActivity.this,imagesViewpagerSlideArray,sliderDTO);
                  viewPager.setAdapter(viewPagerAdapter);

                  viewPagerAdapter.notifyDataSetChanged();

                  dotscount = viewPagerAdapter.getCount();

                  if (slider==true) {

                      Log.i("SignInPage slider 1", "" + dotscount);

                      dots = new ImageView[dotscount];
                      for (int i = 0; i < dotscount; i++) {
                          dots[i] = new ImageView(Home_WithOut_UserActivity.this);
                          dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                          LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                          params.setMargins(8, 0, 8, 0);
                          sliderDotspanel.addView(dots[i], params);
                      }

                      dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                      Toast.makeText(Home_WithOut_UserActivity.this, "Success" + response.message(), Toast.LENGTH_SHORT).show();
                     }
                  }

          }

          @Override
          public void onFailure(Call<SignInPageSliderDTO> call, Throwable t) {
              Log.i("SignInPage slider","error " +t.getMessage());

              Toast.makeText(Home_WithOut_UserActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();

          }
      });
    }

    public void dialogFreeTrial(final Context context, String message) {
        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_free_trial);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        // window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
//        tv_title.setText(getString(R.string.confirm));
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText(Html.fromHtml("" + message));
        tv_message.setMovementMethod(new ScrollingMovementMethod());

        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
                System.exit(0);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK)
            Log.i("Back Press","balck button");
        return false;
    }
}