package com.sdaemon.oakstudiotv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;

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
import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.Constants;
import com.sdaemon.oakstudiotv.utils.Helpers;

public class SplashActivityOld extends AppCompatActivity implements AppConstants {
    private RelativeLayout ll_splash;
    private static final String TAG = SplashActivityOld.class.getSimpleName();
    AppSubClass state;
    private PublicClientApplication sampleApp;
    private AuthenticationResult authResult;
    private String[] scopes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ll_splash = (RelativeLayout) findViewById(R.id.ll_splash);

   //     checkForNotification();

        Thread timer = new Thread() {
            public void run() {
                try {
                    /*Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                    ll_splash.startAnimation(animation2);*/
                    sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                        Intent i = new Intent(SplashActivityOld.this,Home_WithOut_UserActivity.class);
                        startActivity(i);
                        finish();

                 //   getValue();

                }
            }
        };
        timer.start();
    }


    public  void getValue(){
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
            Log.i(String.valueOf(SplashActivityOld.this),"################currentUser :"+currentUser);

            if (currentUser != null) {
                /* We have 1 user */

                Log.i(String.valueOf(SplashActivityOld.this),"################acquireTokenSilentAsync currentUser IF :"+currentUser);
                sampleApp.acquireTokenSilentAsync(
                        scopes,
                        currentUser,
                        String.format(Constants.AUTHORITY, Constants.TENANT, Constants.SISU_POLICY),
                        false,
                        getAuthSilentCallback());
            } else {
                Log.i(String.valueOf(SplashActivityOld.this),"################ acquireToken currentUser ELSE :"+currentUser);
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

                Log.i(String.valueOf(SplashActivityOld.this),"################getAuthSilentCallback :"+authenticationResult);

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
                Log.i(String.valueOf(SplashActivityOld.this),"################getAuthInteractiveCallback authenticationResult  :"+authenticationResult);
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


 // Starts authenticated intent
    private void startAuthenticated() {
      //  startActivity(new Intent(SplashActivity.this, MainActivity.class));
        startActivity(new Intent(SplashActivityOld.this, Home_WithOut_UserActivity.class));
      //  finish();
    }







    private void checkForNotification() {
        Bundle bundle;
      //  Fragment fragment = new Splash();
        Intent i = new Intent(SplashActivityOld.this,Home_WithOut_UserActivity.class);
        if (getIntent() != null) {
            bundle = getIntent().getExtras();
            if (bundle != null) {
                String test = bundle.getString(TYPE);
                if (!TextUtils.isEmpty(test)) {
                    Log.i("--------------", ": " + getIntent().getStringExtra(TYPE));
                    Log.i("--------------", ": " + getIntent().getStringExtra(PN_TITLE));
                    Log.i("--------------", ": " + getIntent().getStringExtra(PN_LINK));
                    Log.i("--------------", ": " + getIntent().getStringExtra(MESSAGE_EN));
                    Log.i("--------------", ": " + getIntent().getStringExtra(NOTIFICATION));
                    bundle.putString(PN_TITLE, getIntent().getStringExtra(PN_TITLE));
                    bundle.putString(PN_LINK, getIntent().getStringExtra(PN_LINK));
                    bundle.putString(MESSAGE_EN, getIntent().getStringExtra(MESSAGE_EN));
                    bundle.putString(TYPE, getIntent().getStringExtra(TYPE));
                    bundle.putString(NOTIFICATION, NOTIFICATION);
                   // fragment.setArguments(bundle);
                    i.putExtras(bundle);
                }
            }
        }
        startActivity(i);
        finish();
    }


}
