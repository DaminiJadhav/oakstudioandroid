package com.sdaemon.oakstudiotv.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.Utilities;


public class BaseFragment extends Fragment implements AppConstants {
    AppSession appSession;
    Utilities utilities;
    Context context;
    Bundle bundle;
    Fragment fragment;
    InputMethodManager mInputMethodManager;
    int screenHeight = 0, screenWidth = 0;
    private ConnectivityManager cm;

    /**
     * Method for close keyboard
     */
    public void closeKeyboard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragmentWithoutBack(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                //.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
    }
    public void replaceFragmentWithBack(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(className)
                .commitAllowingStateLoss();
    }

    public void replaceFragmentWithBackAnim(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(className)
                .commitAllowingStateLoss();
    }

    public void addFragmentWithoutBack(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.fragment_container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
    }

    public Fragment getFragment(String fragmentTag) {
        Fragment fragment = null;
        try {
            fragment = getActivity().getSupportFragmentManager().findFragmentByTag(fragmentTag);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return fragment;
    }

    public void log(String tag, String str) {
        Log.i(tag, str);
    }

//    public void dialogOK(Context context, String title, String message) {
//        if (dialogOK != null && dialogOK.isShowing())
//            dialogOK.dismiss();
//        dialogOK = new DialogOK(context, title, message);
//        dialogOK.show();
//    }
//
//    public void dialogOK(Context context, String title, String message, OnDialogConfirmListener onDialogConfirmListener) {
//        if (dialogOK != null && dialogOK.isShowing())
//            dialogOK.dismiss();
//        dialogOK = new DialogOK(context, title, message, onDialogConfirmListener);
//        dialogOK.show();
//    }

    Dialog dialogOK = null, dialogValidation = null;

//    public void dialogValidation(Context context, ArrayList<String> messages) {
//        if (dialogValidation != null && dialogValidation.isShowing())
//            dialogValidation.dismiss();
//        dialogValidation = new DialogValidation(context, messages);
//        dialogValidation.show();
//    }

//    DialogConfirm dialogConfirm = null;
//
//    public void dialogConfirm(Context context, String message, DialogConfirm.OnDialogConfirmListener onDialogConfirmListener) {
//        if (dialogConfirm != null && dialogConfirm.isShowing())
//            dialogConfirm.dismiss();
//        dialogConfirm = new DialogConfirm(context, getString(R.string.confirmation), message, onDialogConfirmListener);
//        dialogConfirm.show();
//    }

    ///////////////////////////////////////////
    /**
     * Choose image from either camera or gallery
     */
    protected Uri cameraUri = null;
    protected String cropPicturePath = "";
    protected String picturePath = "";

    void getScreenSize() {
        if (getActivity() == null) return;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        log(getClass().getName(), screenWidth + "*" + screenHeight);
    }
    public void clearBackStack() {
        FragmentManager fm = getFragmentManager();
        for (int entry = 0; entry < fm.getBackStackEntryCount(); entry++) {
            fm.popBackStack();
        }
    }

    public void showFragmentWithBack(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.fragment_container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .addToBackStack(className)
                .commitAllowingStateLoss();
    }


    public void showFragmentWithBackNew(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
//                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commitAllowingStateLoss();
    }


    public void addFragmentWithBack(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
// .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
// .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.fragment_container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .addToBackStack(className)
                .commitAllowingStateLoss();
    }


    public void addFragmentWithBackAnim(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                //  .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.fragment_container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .addToBackStack(className)
                .commitAllowingStateLoss();
    }


    public void addFragmentWithBackNew(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                // .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.fragment_container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .addToBackStack(className)
                .commitAllowingStateLoss();
    }


    public void showFragmentWithOutBack(Fragment targetFragment, String className) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                // .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.fragment_container, targetFragment, className)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commitAllowingStateLoss();
    }

}
