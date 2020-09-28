package com.sdaemon.oakstudiotv.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.identity.client.ILoggerCallback;
import com.microsoft.identity.client.Logger;
import com.microsoft.identity.client.MsalClientException;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.User;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.model.AboutUsResponse;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.Constants;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpandInfoActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_back;
    Utilities utilities;
    /* Azure AD Variables */
    private PublicClientApplication sampleApp;
    AppSubClass state;
    AppSession appSession;
    private String[] scopes;
    TextView tvLogout, tvCookiePolicy, tvAboutUs, tvCareer, tvPolicy, tvterms,tvContatcUs,tvSupport,tvblog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpand_info);
        initialize();
        appSession = AppSession.getInstance(this);
        utilities = Utilities.getInstance(this);
        getTblAboutUs();
        initView();

    }


    private void initialize(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        tvAboutUs = (TextView) findViewById(R.id.tv_about_us);
        tvAboutUs.setOnClickListener(this);

        tvLogout = (TextView) findViewById(R.id.tv_logout);
        tvLogout.setOnClickListener(this);

        tvCookiePolicy = (TextView) findViewById(R.id.tv_cookie_policy);
        tvCookiePolicy.setOnClickListener(this);

        tvCareer =(TextView) findViewById(R.id.tv_career);
        tvCareer.setOnClickListener(this);

        tvContatcUs=(TextView) findViewById(R.id.tv_contactus);
        tvContatcUs.setOnClickListener(this);

        tvblog=(TextView) findViewById(R.id.tv_blog);
        tvblog.setOnClickListener(this);

        tvPolicy = (TextView) findViewById(R.id.tv_privacypolicy);
        tvPolicy.setOnClickListener(this);

        tvterms = (TextView) findViewById(R.id.tv_terms);
        tvterms.setOnClickListener(this);

        tvSupport = (TextView) findViewById(R.id.tv_support);
        tvSupport.setOnClickListener(this);
        /* Configure your sample app and save state for this activity */


        state = (AppSubClass) this.getApplicationContext();
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
//                Log.i("About us",""+aboutUsResponse.get(0).getId());
//


            }

            @Override
            public void onFailure(Call<List<AboutUsResponse>> call, Throwable t) {
                Log.i("About Error ",""+t.getMessage());

                Toast.makeText(HelpandInfoActivity.this, "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:
                //  onSignOutClicked();
                dialogLogout(getResources().getString(R.string.are_you_sure_you_want_to_logout));
                break;
            case R.id.tv_cookie_policy:
                Intent cookieIntent = new Intent(this, CookiePolicyActivity.class);
                startActivity(cookieIntent);

                //  setParticularDate();
                break;
            case R.id.tv_about_us:
                Intent aboutIntent = new Intent(this, AboutUsActivity.class);
                startActivity(aboutIntent);

//                startAlert();
                break;

            case R.id.tv_support:
                Intent supportIntent = new Intent(this, SupportActivity.class);
                startActivity(supportIntent);
                break;

            case R.id.tv_career:
                Intent careerIntent = new Intent(this, CareersActivity.class);
                startActivity(careerIntent);
                break;

            case R.id.tv_contactus:
                Intent contactusIntent = new Intent(this, ContactActivity.class);
                startActivity(contactusIntent);
                break;

            case R.id.tv_blog:
                Intent blogIntent = new Intent(this, BlogActicity.class);
                startActivity(blogIntent);
                break;


            case R.id.tv_privacypolicy:
                Intent privacyIntent = new Intent(this, PrivacyPolicyActivity.class);
                startActivity(privacyIntent);
                break;

            case R.id.tv_terms:
                Intent termsIntent = new Intent(this, TermsOfUseActivity.class);
                startActivity(termsIntent);
                break;
        }
    }
    public void dialogLogout(String message) {
        final Dialog dialog = new Dialog(this);
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
                appSession.logout();
                Log.i("AppSession Genre",""+appSession.getGenreIDposition());
                Log.i("AppSession Review",""+appSession.getReviewIDposition());
                Log.i("AppSession Feature",""+appSession.getFeatureIDposition());
                Log.i("AppSession Studio",""+appSession.getStudioIDposition());
                Log.i("AppSession Rating",""+appSession.getRatingIDposition());
                Log.i("AppSession Year",""+appSession.getYearIDposition());




//                appSession.setGenreID("");
                onSignOutClicked();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void onSignOutClicked() {

        /* Attempt to get a account and remove their cookies from cache */
        List<User> accounts = null;

        try {
            accounts = sampleApp.getUsers();

            if (accounts == null) {
                /* We have no accounts */
                Log.d("HelpandInfo", "0000000  We have no accounts");

            } else if (accounts.size() == 1) {
                /* We have 1 account */
                /* Remove from token cache */
                //  sampleApp.removeAccount(accounts.get(0));
                Log.d("HelpandInfo", "0000000 accounts.get(0): " + accounts.get(0));
                sampleApp.remove(accounts.get(0));

                //  updateSignedOutUI();

                Toast.makeText(HelpandInfoActivity.this, "updateSignedOutUI", Toast.LENGTH_SHORT)
                        .show();

                Log.d("HelpandInfo", "0000000 updateSignedOutUI");

            } else {
                /* We have multiple accounts */
                for (int i = 0; i < accounts.size(); i++) {
                    //  sampleApp.removeAccount(accounts.get(i));
                    Log.d("", "0000000 accounts.get(i): " + accounts.get(i));
                    sampleApp.remove(accounts.get(i));
                }
            }

            Toast.makeText(HelpandInfoActivity.this, "Signed Out!", Toast.LENGTH_SHORT)
                    .show();

            Log.d("HelpandInfo", "0000000 Signed Out!");

            Intent intent = new Intent(HelpandInfoActivity.this, Home_WithOut_UserActivity.class);
            //  intent.putExtra(PN_TYPE, LOGOUT_APP);
            startActivity(intent);
            finish();

        } catch (MsalClientException e) {
            Log.d("HelpandInfo", "User at this position does not exist: " + e.toString());
        }
    }
}
