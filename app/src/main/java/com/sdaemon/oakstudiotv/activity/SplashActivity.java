package com.sdaemon.oakstudiotv.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.fragment.Splash;
import com.sdaemon.oakstudiotv.utils.AppConstants;

public class SplashActivity extends AppCompatActivity implements AppConstants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_new);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        showFragment(new Splash(),"Splash");

    }
    private void showFragment(Fragment targetFragment, String className) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_main, targetFragment, className);
        ft.commitAllowingStateLoss();
    }


}
