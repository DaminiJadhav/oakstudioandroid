package com.sdaemon.oakstudiotv.fragment;


import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.ILoggerCallback;
import com.microsoft.identity.client.Logger;
import com.microsoft.identity.client.MsalClientException;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.User;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.BlogActicity;
import com.sdaemon.oakstudiotv.activity.SupportActivity;
import com.sdaemon.oakstudiotv.activity.AboutUsActivity;
import com.sdaemon.oakstudiotv.activity.CareersActivity;
import com.sdaemon.oakstudiotv.activity.ContactActivity;
import com.sdaemon.oakstudiotv.activity.CookiePolicyActivity;
import com.sdaemon.oakstudiotv.activity.Home_WithOut_UserActivity;
import com.sdaemon.oakstudiotv.activity.PrivacyPolicyActivity;
import com.sdaemon.oakstudiotv.activity.TermsOfUseActivity;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.model.AboutUsResponse;
import com.sdaemon.oakstudiotv.utils.AlertBroadcastReceiver;
import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.Constants;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHelpandInfo extends Fragment implements View.OnClickListener, AppConstants {
    private static final String TAG = FragmentHelpandInfo.class.getSimpleName();
    private View rootView;
    private Context context;
    ImageView iv_back;
    Utilities utilities;
    TextView tvLogout, tvCookiePolicy, tvAboutUs, tvCareer, tvPolicy, tvterms,tvContatcUs,tvSupport,tvblog;
    /* Azure AD Variables */
    private PublicClientApplication sampleApp;
    private AuthenticationResult authResult;
    AppSubClass state;
    AppSession appSession;

    private String[] scopes;

    public FragmentHelpandInfo() {
//getActivity().finish();
//        onBackPressed();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_helpand_info, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        appSession = AppSession.getInstance(context);
        utilities = Utilities.getInstance(context);
        getTblAboutUs();
        initView();

    }

    private void initView() {
        tvAboutUs = (TextView) rootView.findViewById(R.id.tv_about_us);
        tvAboutUs.setOnClickListener(this);

        tvLogout = (TextView) rootView.findViewById(R.id.tv_logout);
        tvLogout.setOnClickListener(this);

        tvCookiePolicy = (TextView) rootView.findViewById(R.id.tv_cookie_policy);
        tvCookiePolicy.setOnClickListener(this);

        tvCareer = rootView.findViewById(R.id.tv_career);
        tvCareer.setOnClickListener(this);

        tvContatcUs=rootView.findViewById(R.id.tv_contactus);
        tvContatcUs.setOnClickListener(this);

        tvblog=rootView.findViewById(R.id.tv_blog);
        tvblog.setOnClickListener(this);

        tvPolicy = rootView.findViewById(R.id.tv_privacypolicy);
        tvPolicy.setOnClickListener(this);

        tvterms = rootView.findViewById(R.id.tv_terms);
        tvterms.setOnClickListener(this);

        tvSupport = rootView.findViewById(R.id.tv_support);
        tvSupport.setOnClickListener(this);
        /* Configure your sample app and save state for this activity */


        state = (AppSubClass) context.getApplicationContext();
        scopes = Constants.SCOPES.split("\\s+");

        /* Initializes the app context using MSAL */
        sampleApp = state.getPublicClient();

        /* Initialize the MSAL App context */
        if (sampleApp == null) {
            sampleApp = new PublicClientApplication(
                    context.getApplicationContext(),
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

        /*
         *//* Attempt to get a user and acquireTokenSilent
         * If this fails we do an interactive request
         *//*
        List<User> accounts = null;

        try {
            accounts = sampleApp.getUsers();

            if (accounts != null && accounts.size() == 1) {
                *//* We have 1 account *//*

                sampleApp.acquireTokenSilentAsync(SCOPES, accounts.get(0), getAuthSilentCallback());
            } else {
                *//* We have no account or >1 account *//*
            }
        } catch (MsalClientException e) {
            Log.d(TAG, "Account at this position does not exist: " + e.toString());
        }
*/

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:
                //  onSignOutClicked();
                dialogLogout(getResources().getString(R.string.are_you_sure_you_want_to_logout));
                break;
            case R.id.tv_cookie_policy:
                Intent cookieIntent = new Intent(getActivity(), CookiePolicyActivity.class);
                startActivity(cookieIntent);

                //  setParticularDate();
                break;
            case R.id.tv_about_us:
                Intent aboutIntent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(aboutIntent);

//                startAlert();
                break;

            case R.id.tv_support:
                Intent supportIntent = new Intent(getActivity(), SupportActivity.class);
                startActivity(supportIntent);
                break;

            case R.id.tv_career:
                Intent careerIntent = new Intent(getActivity(), CareersActivity.class);
                startActivity(careerIntent);
                break;

            case R.id.tv_contactus:
                Intent contactusIntent = new Intent(getActivity(), ContactActivity.class);
                startActivity(contactusIntent);
                break;

            case R.id.tv_blog:
                Intent blogIntent = new Intent(getActivity(), BlogActicity.class);
                startActivity(blogIntent);
                break;


            case R.id.tv_privacypolicy:
                Intent privacyIntent = new Intent(getActivity(), PrivacyPolicyActivity.class);
                startActivity(privacyIntent);
                break;

            case R.id.tv_terms:
                Intent termsIntent = new Intent(getActivity(), TermsOfUseActivity.class);
                startActivity(termsIntent);
                break;
        }
    }

    //  https://github.com/AzureAD/microsoft-authentication-library-for-android/releases
    private void onSignOutClicked() {

        /* Attempt to get a account and remove their cookies from cache */
        List<User> accounts = null;

        try {
            accounts = sampleApp.getUsers();

            if (accounts == null) {
                /* We have no accounts */
                Log.d(TAG, "0000000  We have no accounts");

            } else if (accounts.size() == 1) {
                /* We have 1 account */
                /* Remove from token cache */
                //  sampleApp.removeAccount(accounts.get(0));
                Log.d(TAG, "0000000 accounts.get(0): " + accounts.get(0));
                sampleApp.remove(accounts.get(0));

                //  updateSignedOutUI();

                Toast.makeText(context, "updateSignedOutUI", Toast.LENGTH_SHORT)
                        .show();

                Log.d(TAG, "0000000 updateSignedOutUI");

            } else {
                /* We have multiple accounts */
                for (int i = 0; i < accounts.size(); i++) {
                    //  sampleApp.removeAccount(accounts.get(i));
                    Log.d(TAG, "0000000 accounts.get(i): " + accounts.get(i));
                    sampleApp.remove(accounts.get(i));
                }
            }

            Toast.makeText(context, "Signed Out!", Toast.LENGTH_SHORT)
                    .show();

            Log.d(TAG, "0000000 Signed Out!");

            Intent intent = new Intent(context, Home_WithOut_UserActivity.class);
            //  intent.putExtra(PN_TYPE, LOGOUT_APP);
            startActivity(intent);
            getActivity().finish();

        } catch (MsalClientException e) {
            Log.d(TAG, "User at this position does not exist: " + e.toString());
        }
    }

    public void getTblAboutUs() {
        Call<List<AboutUsResponse>> call = RetroClient.sdaemonApi().aboutUs();
        call.enqueue(new Callback<List<AboutUsResponse>>() {
            @Override
            public void onResponse(Call<List<AboutUsResponse>> call, Response<List<AboutUsResponse>> response) {
                List<AboutUsResponse> aboutUsResponse = response.body();
//                Toast.makeText(AboutUsActivity.this,"Success",Toast.LENGTH_LONG).show();
                // tvaboutus.setText(aboutUsResponse.get(0).getFull());
                appSession.setAbout(aboutUsResponse);
//


            }

            @Override
            public void onFailure(Call<List<AboutUsResponse>> call, Throwable t) {
                Toast.makeText(context, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void dialogLogout(String message) {
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
        tv_title.setText(getString(R.string.logout));
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
                onSignOutClicked();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    /* basically , 1000 uses here just for converting second to miliseconds.

     number of seconds in a day. 24 * 60 * 60 = 86400 sec

 1 sec = 1000 milliseconds

     so after calculating expression,result is in milliseconds

     days * 24 * 60 * 60 * 1000 = days * 86400000 ms*/
    public void startAlert() {
//        EditText text = findViewById(R.id.time);
//        int i = Integer.parseInt(text.getText().toString());
//
        int i = 10;

        Intent intent = new Intent(context, AlertBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);
        Toast.makeText(context, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();
    }

    public void setParticularDate1() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();
        // cal.set(2012,2,8,18,16);
        cal.set(2019, 3, 25, 12, 36);

//        cal.set(Calendar.YEAR, 2019);
//        cal.set(Calendar.MONTH, 3);
//        cal.set(Calendar.DAY_OF_MONTH, 25);
//        cal.set(Calendar.HOUR_OF_DAY, 12);
//        cal.set(Calendar.MINUTE, 6);
//        cal.set(Calendar.SECOND, 0);

        Toast.makeText(context, "Alarm date ", Toast.LENGTH_LONG).show();
        Log.i(String.valueOf(context), "============== Alarm date: " + cal.getTimeInMillis());


        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 234324243, intent, 0);
        // cal.add(Calendar.SECOND, 5);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }

    //  https://stackoverflow.com/questions/4700285/android-how-to-set-an-alarm-to-a-specific-date
    // The following code works perfectly for alarm. The date and time i mentioned here is : 2012- June- 28, 11:20:00 AM.
    // And the most important thing is, month is specified from 0 t0 11 only. Means June should be specified by 5.


    public void setParticularDate() {
        AlarmManager objAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar objCalendar = Calendar.getInstance();
        objCalendar.set(Calendar.YEAR, 2019);
        //objCalendar.set(Calendar.YEAR, objCalendar.get(Calendar.YEAR));
        objCalendar.set(Calendar.MONTH, 2);
        objCalendar.set(Calendar.DAY_OF_MONTH, 25);
        objCalendar.set(Calendar.HOUR_OF_DAY, 14);
        objCalendar.set(Calendar.MINUTE, 13);
        objCalendar.set(Calendar.SECOND, 0);
        objCalendar.set(Calendar.MILLISECOND, 0);
        //  objCalendar.set(Calendar.AM_PM, Calendar.AM);

        Toast.makeText(context, "Alarm date ", Toast.LENGTH_LONG).show();
        Intent alamShowIntent = new Intent(context, AlertBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getActivity(context, 0, alamShowIntent, 0);
        objAlarmManager.set(AlarmManager.RTC_WAKEUP, objCalendar.getTimeInMillis(), alarmPendingIntent);

    }

    public static boolean onBackPressed(){

            return true;
    }

}

