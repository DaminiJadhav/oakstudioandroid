package com.sdaemon.oakstudiotv.activity.signUp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.CompleteActivity;
import com.sdaemon.oakstudiotv.adapter.ChoosePlanAdapter;
import com.sdaemon.oakstudiotv.fragment.FragmentPayment_details;
import com.sdaemon.oakstudiotv.model.ChoosePlanList;

import java.util.ArrayList;

public class SignUp_Plan_Activity extends AppCompatActivity implements View.OnClickListener {
    private ChoosePlanAdapter choosePlanAdapter;
    private LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager mLayoutManager;
    private RecyclerView rv_choosePlan;
    public static RelativeLayout rl_plan;
    private Button btn_choosePlan,btn_finish;
    private TextView tv_number;
    private ArrayList<ChoosePlanList> choosePlanListArrayList = new ArrayList<>();

    private Dialog dialog;
    private Context context;
    private ImageView iv_tabback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__plan_);

        initialization();
    }

    private void initialization() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

        context = SignUp_Plan_Activity.this;

        rv_choosePlan = (RecyclerView) findViewById(R.id.rv_choosePlan);
        rl_plan = (RelativeLayout) findViewById(R.id.rl_plan);
        btn_choosePlan = (Button) findViewById(R.id.btn_choosePlan);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        tv_number = (TextView) findViewById(R.id.tv_number);
        String text = "<font color=#9AEB3D>2</font> <font color=#505053>/3</font>";
        tv_number.setText(Html.fromHtml(text));

        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rv_choosePlan.setHasFixedSize(true);
        rv_choosePlan.setLayoutManager(mLayoutManager);
        rv_choosePlan.setItemAnimator(new DefaultItemAnimator());

        choosePlanListArrayList.add(new ChoosePlanList("BASIC",7.99,0,0,1,1,1,1,1,20.99,90.99));
        choosePlanListArrayList.add(new ChoosePlanList("STANDARD",10.99,1,0,2,1,1,1,1,20.99,90.99));
        choosePlanListArrayList.add(new ChoosePlanList("PREMIUM",13.99,1,1,4,1,1,1,1,20.99,90.99));

        choosePlanAdapter = new ChoosePlanAdapter(SignUp_Plan_Activity.this,choosePlanListArrayList);
        rv_choosePlan.setAdapter(choosePlanAdapter);

        btn_choosePlan.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        iv_tabback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_choosePlan:
                /*Intent intent= new Intent(SignUp_Plan_Activity.this, CreditC_Payment_Method_Activity.class);
                startActivity(intent);*/
                btn_choosePlan.setVisibility(View.GONE);
                rl_plan.setVisibility(View.GONE);
                btn_finish.setVisibility(View.VISIBLE);
                Fragment fragment = new FragmentPayment_details("1");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame1, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
                break;

            case R.id.btn_finish:
                dialogConfirmation();
                break;
            case R.id.iv_tabback:
                finish();
                break;
        }
    }
    private void dialogConfirmation() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirmation_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        lp.y = 200; // top margin
        dialog.getWindow().setAttributes(lp);

        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnAccept = (Button) dialog.findViewById(R.id.btnAccept);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progresDialog();
                Intent intent=new Intent(context,CompleteActivity.class);
                startActivity(intent);
               // finish();
            }
        });
        dialog.show();

    }




    private void progresDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        lp.y = 200; // top margin
        dialog.getWindow().setAttributes(lp);

//        LinearLayout linearLayout=(LinearLayout) dialog.findViewById(R.id.ll_accept);
//        linearLayout.setVisibility(View.INVISIBLE);
//        TextView textView=findViewById(R.id.tv_acceptmsg)
//        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
//        Button btnAccept = (Button) dialog.findViewById(R.id.btnAccept);

//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//
//            }
//        });
//
//        btnAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(context,CompleteActivity.class);
//                startActivity(intent);
//                // finish();
//            }
//        });
        dialog.show();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
