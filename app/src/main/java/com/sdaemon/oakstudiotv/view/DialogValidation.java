package com.sdaemon.oakstudiotv.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;

import java.util.ArrayList;


public class DialogValidation extends Dialog {
    Context context;

    public DialogValidation(Context context, final ArrayList<String> messages) {
        super(context, R.style.MaterialDialog);
        try {
            this.context = context;
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_box_validation);
            this.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.setCanceledOnTouchOutside(false);
            this.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            this.getWindow().setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
            this.getWindow().setBackgroundDrawable(new ColorDrawable(
                    android.graphics.Color.TRANSPARENT));
            TextView tvTitle = findViewById(R.id.tv_title);
            tvTitle.setText(context.getResources().getString(R.string.whoops));
            TextView tvSubTitle = findViewById(R.id.tv_sub_title);
          //  tvSubTitle.setText("" + subTitle);
            ListView lvValidation = findViewById(R.id.lvValidation);
            ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(context, R.layout.list_item_dialog, R.id.tv_text, messages);
            lvValidation.setAdapter(modeAdapter);
            findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
