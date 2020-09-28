package com.sdaemon.oakstudiotv.activity.brainTreePayment;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.MainActivity;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.model.Payment;
import com.sdaemon.oakstudiotv.utils.AppSession;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrainTreeTransactionSuccess extends AppCompatActivity {
    TextView tvtransaction, tvtransaction1, tvtransactionId,tvdate;
    ImageView ivtransaction;
    Button button;
    AppSession appSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain_tree_transaction_success);
        tvtransaction = findViewById(R.id.tv_transaction);
        tvtransaction1 = findViewById(R.id.tv_transactionsuccess);
        tvtransactionId = findViewById(R.id.tv_transactionId);
        ivtransaction = findViewById(R.id.iv_transaction);
        tvdate = findViewById(R.id.tv_currentDate);
        button = findViewById(R.id.finish);
        appSession=AppSession.getInstance(this);
        gettransaction();

//        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy G 'at' HH:mm:ss z");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        tvdate.setText(currentDateandTime);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String data = bundle.getString("KEY_TRANSCATION");
            if (data != null) {
                ivtransaction.setImageDrawable(getResources().getDrawable(R.drawable.checkmark));
                tvtransactionId.setText(getResources().getString(R.string.transaction_id)+ " "+ data);
                tvtransaction1.setText(getResources().getString(R.string.transaction_successful));
            } else {
//                tvtransaction.setVisibility(View.GONE);
                ivtransaction.setImageDrawable(getResources().getDrawable(R.drawable.failed));
                tvtransaction.setText(getResources().getString(R.string.transaction_failed));

//                tvtransaction.setText(data);
            }
            //   Toast.makeText(BrainTreeTransactionSuccess.this, "success" +data , Toast.LENGTH_SHORT).show();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BrainTreeTransactionSuccess.this, MainActivity.class);
//                Intent intent = new Intent(BrainTreeTransactionSuccess.this, Home_WithOut_UserActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void gettransaction(){

        Call<Payment> call=  RetroClient.sdaemonApi().transactionObj(paymentData());
        Log.e(getClass().getName(), "===" + call.request().url());

        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.isSuccessful()){
                    Toast.makeText(BrainTreeTransactionSuccess.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Toast.makeText(BrainTreeTransactionSuccess.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    Payment paymentData() {
        Payment dto = new Payment();
        try {
            dto.setSubscriptionID(0);
            dto.setCustID(0);
            dto.setPlanID(0);
            dto.setStatusID(1);
            dto.setCreateDate("25-09-1974");
            dto.setCreatedBy(0);
            dto.setLastModifiedDate("25-09-1974");
            dto.setLastModifiedBy(0);
            Log.i(getClass().getName(), "=========== DTO dd :" + dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

}
