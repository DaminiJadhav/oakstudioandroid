package com.sdaemon.oakstudiotv.activity;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.PlanTestAdapter1;
import com.sdaemon.oakstudiotv.model.Periodlist;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;

import static com.sdaemon.oakstudiotv.utils.AppConstants.PN_COREPLAN_ID;
import static com.sdaemon.oakstudiotv.utils.AppConstants.PN_PERIOD_ID;
import static com.sdaemon.oakstudiotv.utils.AppConstants.PN_PLAN_COST;
import static com.sdaemon.oakstudiotv.utils.AppConstants.PN_PLAN_NAME;

public class PlanSelectionActivity extends AppCompatActivity {
    private ImageView iv_tabback;
    TextView tvcheck1,tvcheck2,tvcheck3;
    ArrayList<Periodlist> list1;
    RecyclerView rvList;
    String PlanID="",Name="";
    private LinearLayoutManager mLinearLayoutManager;
    public LinearLayout l1, l2, l3;
    private Context context;
    private PlanTestAdapter1 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_selection);

//        l1 = (LinearLayout) findViewById(R.id.check_30);
//        l2 = (LinearLayout) findViewById(R.id.check_60);
//        l3 = (LinearLayout) findViewById(R.id.check_90);
//        tvcheck1=(TextView) findViewById(R.id.tv_amount1);
//        tvcheck2=(TextView) findViewById(R.id.tv_amount2);
//        tvcheck3=(TextView) findViewById(R.id.tv_amount3);
        initialization();

        Bundle extras = getIntent().getExtras();
       list1  = extras.getParcelableArrayList("arraylist");
      PlanID= extras.getString("TYPE");
      Name=extras.getString("NAME");


//                    tvcheck1.setText(arraylist.get(periodId).getAmount());
        rvList.setLayoutManager(mLinearLayoutManager);
        adapter = new PlanTestAdapter1(context, list1, onClickCallback);

        rvList.setAdapter(adapter);


//                tvcheck2.setText(arraylist.get(i).getPeriodid());
//                tvcheck3.setText(arraylist.get(i).getName());


//            Toast.makeText(this, "Amount :" +arraylist.get(periodId).getAmount() +"Period Id:"+periodId, Toast.LENGTH_SHORT).show();





    }
    private void initialization() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        rvList = (RecyclerView)findViewById(R.id.rv_list1);
        mLinearLayoutManager = new LinearLayoutManager(context);

        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





//        l1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Bundle bundle=new Bundle();
//                bundle.putDouble("Value",30);
//                Intent intent=new Intent(PlanSelectionActivity.this, BrainTreeActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//        l2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle=new Bundle();
//                bundle.putDouble("Value",60);
//                Intent intent=new Intent(PlanSelectionActivity.this, BrainTreeActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//        l3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle=new Bundle();
//                bundle.putDouble("Value",90);
//                Intent intent=new Intent(PlanSelectionActivity.this, BrainTreeActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    final OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if (type.equalsIgnoreCase("test")) {
                try {

                    Log.e("Name", "===" +   Name);
                    Log.e("listPriceAmount", "===" +   list1.get(position).getAmount());
                    Log.e("listPriceID", "===" + list1.get(position).getPeriodid());
                    Log.e("listPlanID", "===" +PlanID);
                    Intent intent=new Intent(PlanSelectionActivity.this,PromoCodeActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(PN_PLAN_NAME, Name);
                    bundle.putDouble(PN_PLAN_COST, Double.parseDouble((list1.get(position).getAmount()))); ;
                    bundle.putInt(PN_PERIOD_ID, Integer.parseInt(list1.get(position).getPeriodid()));
                    bundle.putInt(PN_COREPLAN_ID, Integer.parseInt(PlanID));
                    intent.putExtras(bundle);
                    startActivity(intent);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                ;
            }
        }
    };
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        menu.findItem(R.id.action_like).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
        menu.findItem(R.id.action_tuneUp).setVisible(false);
        menu.findItem(R.id.action_person).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }
}
