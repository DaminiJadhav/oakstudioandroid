package com.sdaemon.oakstudiotv.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.sdaemon.oakstudiotv.R;


/**
 * Created by Devendra P. Chaudhari on 08-Feb-17.
 */

public class DialogUtils {
    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog_layout);
        ProgressBar pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
        pb.getIndeterminateDrawable().setColorFilter(mContext.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        dialog.dismiss();
        return dialog;
    }
}
