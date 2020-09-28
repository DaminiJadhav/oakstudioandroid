package com.sdaemon.oakstudiotv.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sdaemon.oakstudiotv.R;


public class InternetUtils {
//    private Context mContext;
//
//    public InternetUtils(Context mContext) {
//        this.mContext = mContext;
//    }

    /**
     * Checking for all possible internet providers
     **/

    public static boolean isInternetAvailable(Context mContext) {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void showNoInternetDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Setting Dialog Title
        builder.setTitle(context.getString(R.string.app_name) + " Says...");

        // Setting Dialog Message
        builder.setMessage(context.getString(R.string.err_no_internet));

//        // Setting alert dialog icon
//        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        builder.setPositiveButton(context.getString(R.string.action_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(context.getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public static AlertDialog showNoInternetDialogSplash(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Setting Dialog Title
        builder.setTitle(context.getString(R.string.app_name) + " Says...");

        // Setting Dialog Message
        builder.setMessage(context.getString(R.string.err_no_internet));

//        // Setting alert dialog icon
//        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        builder.setPositiveButton(context.getString(R.string.action_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(context.getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Showing Alert Message
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;
    }
}
