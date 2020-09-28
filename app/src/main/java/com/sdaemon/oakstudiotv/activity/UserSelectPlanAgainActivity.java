package com.sdaemon.oakstudiotv.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.identity.client.ILoggerCallback;
import com.microsoft.identity.client.Logger;
import com.microsoft.identity.client.MsalClientException;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.User;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.Constants;

import java.util.List;

public class UserSelectPlanAgainActivity extends AppCompatActivity implements View.OnClickListener {
TextView tvwelcome;
Button btnselectplan;
AppSession appSession;
    private PublicClientApplication sampleApp;
    AppSubClass state;
    private String[] scopes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select_plan_again);
        btnselectplan=findViewById(R.id.btn_select_plan);
        tvwelcome=findViewById(R.id.tv_username);
        btnselectplan.setOnClickListener(this::onClick);
        appSession = AppSession.getInstance(this)   ;
        String userName=appSession.getUserDTO().getResult().getFirstName();
        tvwelcome.setText("Hi, " +userName+ " !");


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

//        Toast.makeText(UserSelectPlanAgainActivity.this,"Name"+userName,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_select_plan:
                Intent intent=new Intent(this,YourPlantestsActivity.class);
                startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            Log.i("Back Press","balck button");

        return false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        MenuItem m= menu.findItem(R.id.menu_refresh).setVisible(false);
        MenuItem logout= menu.findItem(R.id.action_logout).setVisible(true);

//        logout.setTitle("Logout");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();

//
                /* Attempt to get a account and remove their cookies from cache */
                List<User> accounts = null;

                try {
                    accounts = sampleApp.getUsers();

                    if (accounts == null) {
                        /* We have no accounts */
                        Log.d("", "0000000  We have no accounts");

                    } else if (accounts.size() == 1) {
                        /* We have 1 account */
                        /* Remove from token cache */
                        //  sampleApp.removeAccount(accounts.get(0));
                        Log.d("", "0000000 accounts.get(0): " + accounts.get(0));
                        sampleApp.remove(accounts.get(0));

                        //  updateSignedOutUI();

                        Toast.makeText(this, "updateSignedOutUI", Toast.LENGTH_SHORT)
                                .show();

                        Log.d("", "0000000 updateSignedOutUI");

                    } else {
                        /* We have multiple accounts */
                        for (int i = 0; i < accounts.size(); i++) {
                            //  sampleApp.removeAccount(accounts.get(i));
                            Log.d("", "0000000 accounts.get(i): " + accounts.get(i));
                            sampleApp.remove(accounts.get(i));
                        }
                    }

                    Toast.makeText(this, "Signed Out!", Toast.LENGTH_SHORT)
                            .show();

                    Log.d("", "0000000 Signed Out!");

                    Intent intent = new Intent(this, Home_WithOut_UserActivity.class);
                    //  intent.putExtra(PN_TYPE, LOGOUT_APP);
                    startActivity(intent);
                    this.finish();

                } catch (MsalClientException e) {
                    Log.d("", "User at this position does not exist: " + e.toString());
                }
            }
//                // Signal SwipeRefreshLayout to start the progress indicator
//
//
        return super.onOptionsItemSelected(item);
    }
}
