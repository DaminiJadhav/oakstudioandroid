package com.sdaemon.oakstudiotv.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.sdaemon.oakstudiotv.activity.Home_WithOut_UserActivity;
import com.sdaemon.oakstudiotv.activity.MainActivity;
import com.sdaemon.oakstudiotv.activity.UserSelectPlanAgainActivity;
import com.sdaemon.oakstudiotv.dto.UsersDTO;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.Constants;
import com.sdaemon.oakstudiotv.utils.Helpers;
import com.sdaemon.oakstudiotv.utils.Utilities;


public class Splash extends Fragment {
    private static final String TAG = Splash.class.getSimpleName();
    AppSubClass state;
    private PublicClientApplication sampleApp;
    private AuthenticationResult authResult;
    private String[] scopes;
    private AppSession appSession = null;
    private static final int SPLASH_DELAY = 2;
    private Utilities utilities;
    private Context context;
    private Context contextAppsession;
    private Context contextActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(R.layout.splash, container, false);
        } catch (InflateException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        context =this.getActivity();
        contextActivity = getActivity().getApplication();
//        appSession = AppSession.getInstance(context);
        utilities = Utilities.getInstance(context);

        if (!utilities.isNetworkAvailable()){
            startAuthenticated22();
        }
        // getValue();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                //    Intent intent = new Intent(getActivity(), Home_WithOut_UserActivity.class);
////        if (bundle != null)
//            intent.putExtras(bundle);
                 //   startActivity(intent);
                  //  getActivity().finish();


                    state = (AppSubClass) contextActivity;
                    scopes = Constants.SCOPES.split("\\s+");

                    /* Initializes the app context using MSAL */
                    sampleApp = state.getPublicClient();

                    /* Initialize the MSAL App context */
                    if (sampleApp == null) {
                        sampleApp = new PublicClientApplication(
                                contextActivity,
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

                    onCallApiClicked(scopes);



                } catch (Exception e) {
                    Log.i("Exception",e.getMessage());
                    e.printStackTrace();
                }
            }
        }, SPLASH_DELAY*1000);

    }


    public  void getValue(){
        //AD Inisialization code

        state = (AppSubClass) contextActivity;
        scopes = Constants.SCOPES.split("\\s+");

        /* Initializes the app context using MSAL */
        sampleApp = state.getPublicClient();

        /* Initialize the MSAL App context */
        if (sampleApp == null) {
            sampleApp = new PublicClientApplication(
                    this.contextActivity,
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
            Log.i(String.valueOf(context),"################currentUser :"+currentUser);

            if (currentUser != null) {
                /* We have 1 user */

                Log.i(String.valueOf(context),"################acquireTokenSilentAsync currentUser IF :"+currentUser);
                sampleApp.acquireTokenSilentAsync(
                        scopes,
                        currentUser,
                        String.format(Constants.AUTHORITY, Constants.TENANT, Constants.SISU_POLICY),
                        false,
                        getAuthSilentCallback());
            } else {
                Log.i(String.valueOf(context),"################ acquireToken currentUser ELSE :"+currentUser);

                /* We have no user */
                //  startActivity(new Intent(Home_WithOut_UserActivity.this, MainActivity.class)); //change code
                //finish();
                // change code

//             startAuthenticated11();
             startAuthenticated22();


              //  sampleApp.acquireToken(getActivity(), scopes, getAuthInteractiveCallback());
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
//
//    public Activity getActivity() {
//        return this;
//    }
//

    /* Callback used in for silent acquireToken calls.
     * Looks if tokens are in the cache (refreshes if necessary and if we don't forceRefresh)
     * else errors that we need to do an interactive request.
     */
    private AuthenticationCallback getAuthSilentCallback() {
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
                startAuthenticated22();
                /* Successfully got a token, call api now */
                Log.d(TAG, "Successfully authenticated");
                authResult = authenticationResult;
                Log.i(String.valueOf(context),"################getAuthSilentCallback :"+authenticationResult);
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
              //  startAuthenticated22();
                /* Successfully got a token, call api now */
                Log.i(String.valueOf(context),"################getAuthInteractiveCallback authenticationResult  :"+authenticationResult);
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
    private void startAuthenticated11() {
        //  startActivity(new Intent(SplashActivity.this, MainActivity.class));
        startActivity(new Intent(context, Home_WithOut_UserActivity.class));
      //  getActivity().finish();
        getActivity().finish();

    }

 private void startAuthenticated22() {
        //  startActivity(new Intent(SplashActivity.this, MainActivity.class));

//     startActivity(new Intent(context, MainActivity.class));
//     getActivity().finish();
                   Log.i("Splash  Status ", "check" );

           if (checkLoggedin()){
//               startActivity(new Intent(context, MainActivity.class));
//               getActivity().finish();

               try {
                   int planstatus = Integer.parseInt(appSession.getPlanStatus());

                   Log.i("Plan Status ", "" + planstatus);
                   if (planstatus == 0) {
                       Intent intent = new Intent(context, UserSelectPlanAgainActivity.class);
//                  Intent intent = new Intent(context, MainActivity.class);
                       startActivity(intent);
                   } else if (planstatus == 1) {
                       Log.i("Login success", "Success");
                       startActivity(new Intent(context, MainActivity.class));
                       getActivity().finish();

                   }
               }catch (Exception e){
                   e.printStackTrace();
               }

            }else {
               Log.i("Login","Sign in");
               appSession.logout();
//               startActivity(new Intent(context, MainActivity.class));
               startActivity(new Intent(context, Home_WithOut_UserActivity.class));
               getActivity().finish();
             }

    }


    public boolean checkLoggedin() {
//        contextActivity=getActivity().getApplicationContext();
        appSession = AppSession.getInstance(context);
        Log.i("Login response msg" ,""+appSession);
        UsersDTO usersDTO=appSession.getUserDTO();
//        String userDTO=appSession.getUserDTO().toString();
        Log.i("Login User data",""+usersDTO);
//        if (!TextUtils.isEmpty(userDTO)){
            if (usersDTO != null) {
                return true;
            }
//        }
        return false;
    }

}
//    TextView tv = (TextView)findViewById(R.id.textview1);
//    Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font-name.ttf");
//tx.setTypeface(custom_font);