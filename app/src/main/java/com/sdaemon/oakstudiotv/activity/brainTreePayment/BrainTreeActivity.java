package com.sdaemon.oakstudiotv.activity.brainTreePayment;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.Card;
import com.braintreepayments.api.GooglePayment;
import com.braintreepayments.api.ThreeDSecure;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import com.braintreepayments.api.models.CardBuilder;
import com.braintreepayments.api.models.GooglePaymentRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.TokenizationKey;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.WalletConstants;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dao.RetrofitBrainTreeClient;
import com.sdaemon.oakstudiotv.model.BrainTreePostResponse;
import com.sdaemon.oakstudiotv.model.BrainTreeResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrainTreeActivity extends AppCompatActivity implements View.OnClickListener {

final int REQUEST_CODE = 1;
Button btnpay;
boolean googleplay;
LinearLayout  llprogress;
ImageView iv_back;
EditText edamount;
String token;
    private DropInRequest dropInRequest;
    private BraintreeFragment mBraintreeFragment;
      Double  totalamount;
      TextView view,Amount1,planname;
    BrainTreePostResponse brainTreePostResponse;;
    HashMap<String, String> paramHash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain_tree);
        btnpay=findViewById(R.id.btnPay);
        edamount=findViewById(R.id.etPrice);
        llprogress=findViewById(R.id.ll_progress);
        view=findViewById(R.id.tvMessage2);
        Amount1=findViewById(R.id.tvAmount);
        planname=findViewById(R.id.tv_planName);
        btnpay.setOnClickListener(this);
        btnpay.setVisibility(View.GONE);

        llprogress.setVisibility(View.VISIBLE);
        initialize();


        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle!=null){
          Double data=bundle.getDouble("Value");
           String name=bundle.getString("PlanName");
            Amount1.setText(""+data);
            planname.setText(name);
//            Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
            totalamount=data;

            //   Toast.makeText(BrainTreeTransactionSuccess.this, "success" +data , Toast.LENGTH_SHORT).show();
        }
        getBrainsTreeData();
   //     token="eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJleUowZVhBaU9pSktWMVFpTENKaGJHY2lPaUpGVXpJMU5pSXNJbXRwWkNJNklqSXdNVGd3TkRJMk1UWXRjMkZ1WkdKdmVDSjkuZXlKbGVIQWlPakUxTmprME9UUTRNVE1zSW1wMGFTSTZJbU5qT0dRNU5EazJMV05oTW1VdE5EVmlOeTA0WlRjeExUWXpZekk0TnpCaVlqVTRNQ0lzSW5OMVlpSTZJamxxTkhsdWVXWTJPVGRyT1RZNE5YUWlMQ0pwYzNNaU9pSkJkWFJvZVNJc0ltMWxjbU5vWVc1MElqcDdJbkIxWW14cFkxOXBaQ0k2SWpscU5IbHVlV1kyT1Rkck9UWTROWFFpTENKMlpYSnBabmxmWTJGeVpGOWllVjlrWldaaGRXeDBJanBtWVd4elpYMHNJbkpwWjJoMGN5STZXeUp0WVc1aFoyVmZkbUYxYkhRaVhTd2liM0IwYVc5dWN5STZlMzE5LjZPdDktX003bFJObVpNYmhQOHZjSXdDSEJqS3NJSlNuMHFfVFdpRmdHMjlCdlp2d1RvZkVITzIxVmZ5ZmZ0aV9HZEhLNTBHTEY2cXFfaTlmS0NCTUVRIiwiY29uZmlnVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzlqNHlueWY2OTdrOTY4NXQvY2xpZW50X2FwaS92MS9jb25maWd1cmF0aW9uIiwiZ3JhcGhRTCI6eyJ1cmwiOiJodHRwczovL3BheW1lbnRzLnNhbmRib3guYnJhaW50cmVlLWFwaS5jb20vZ3JhcGhxbCIsImRhdGUiOiIyMDE4LTA1LTA4In0sImNoYWxsZW5nZXMiOlsiY3Z2Il0sImVudmlyb25tZW50Ijoic2FuZGJveCIsImNsaWVudEFwaVVybCI6Imh0dHBzOi8vYXBpLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb206NDQzL21lcmNoYW50cy85ajR5bnlmNjk3azk2ODV0L2NsaWVudF9hcGkiLCJhc3NldHNVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImF1dGhVcmwiOiJodHRwczovL2F1dGgudmVubW8uc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFuYWx5dGljcyI6eyJ1cmwiOiJodHRwczovL29yaWdpbi1hbmFseXRpY3Mtc2FuZC5zYW5kYm94LmJyYWludHJlZS1hcGkuY29tLzlqNHlueWY2OTdrOTY4NXQifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiSWZsZXhTb2Z0IiwiY2xpZW50SWQiOm51bGwsInByaXZhY3lVcmwiOiJodHRwOi8vZXhhbXBsZS5jb20vcHAiLCJ1c2VyQWdyZWVtZW50VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3RvcyIsImJhc2VVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFzc2V0c1VybCI6Imh0dHBzOi8vY2hlY2tvdXQucGF5cGFsLmNvbSIsImRpcmVjdEJhc2VVcmwiOm51bGwsImFsbG93SHR0cCI6dHJ1ZSwiZW52aXJvbm1lbnROb05ldHdvcmsiOnRydWUsImVudmlyb25tZW50Ijoib2ZmbGluZSIsInVudmV0dGVkTWVyY2hhbnQiOmZhbHNlLCJicmFpbnRyZWVDbGllbnRJZCI6Im1hc3RlcmNsaWVudDMiLCJiaWxsaW5nQWdyZWVtZW50c0VuYWJsZWQiOnRydWUsIm1lcmNoYW50QWNjb3VudElkIjoiaWZsZXhzb2Z0IiwiY3VycmVuY3lJc29Db2RlIjoiVVNEIn0sIm1lcmNoYW50SWQiOiI5ajR5bnlmNjk3azk2ODV0IiwidmVubW8iOiJvZmYifQ==";




    }


    private void initialize(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }



    public void googleplay()
    {
        if(googleplay==false)
        {

        }
        else
        {
//            GooglePayment.requestPayment(mBraintreeFragment, getGooglePaymentRequest());
        }



    }

    public void onBraintreeSubmit() {
        if(googleplay==false)
        {
            //DropInRequest dropInRequest = new DropInRequest().disableGooglePayment()
             //       .clientToken(token).googlePaymentRequest(googlePaymentRequest);
            startActivityForResult(getDropInRequest().getIntent(this), REQUEST_CODE);
        }
        else
        {
          //  DropInRequest dropInRequest = new DropInRequest()
               //    .clientToken(token);
          //  startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
            startActivityForResult(getDropInRequest().getIntent(this), REQUEST_CODE);
        }
        Log.i("token" ,""+token);

//         dropInRequest = new DropInRequest()
//                .clientToken(token)
//                .googlePaymentRequest(enableGooglePay(dropInRequest));
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            startActivityForResult(dropInRequest.getIntent(this), 399);
////        }

    }

//    private GooglePaymentRequest getGooglePaymentRequest() {
//        return new GooglePaymentRequest()
//                .transactionInfo(TransactionInfo.newBuilder()
//                        .setTotalPrice("1.00")
//                        .setCurrencyCode("USD")
//                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
//                        .build())
//                .emailRequired(true);
//    }
//    private  GooglePaymentRequest enableGooglePay(DropInRequest dropInRequest) {
//        GooglePaymentRequest googlePaymentRequest = new GooglePaymentRequest()
//                .transactionInfo(TransactionInfo.newBuilder()
//                        .setTotalPrice("1.00")
//                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
//                        .setCurrencyCode("USD")
//                        .build())
//                .billingAddressRequired(true); // We recommend collecting and passing billing address information with all Google Pay transactions as a best practice.
//
////        dropInRequest.googlePaymentRequest(googlePaymentRequest);
//        return googlePaymentRequest;
//    }

    private DropInRequest getDropInRequest() {
        GooglePaymentRequest googlePaymentRequest = new GooglePaymentRequest()
                .transactionInfo(TransactionInfo.newBuilder()
                        .setTotalPrice("1.00")
                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                        .setCurrencyCode("INR")
                        .build())
                .allowPrepaidCards(true)
//                .googleMerchantId("cf2sx7y7wq6vwkxp")
                .billingAddressRequired(true);

        return new DropInRequest()
                .amount("1.00")
                .clientToken(token)
                .collectDeviceData(true)
                .googlePaymentRequest(googlePaymentRequest);
    }






    public void getBrainTree(Double amount, String name){
        Call<BrainTreePostResponse> call= RetrofitBrainTreeClient.sdaemonApi().getBrainTree(amount,name);
        view.setVisibility(View.VISIBLE);
        Log.e(getClass().getName(), "===" + call.request().url());
        Log.d("mylog", "amount: " + amount);
        Log.d("mylog", "name: " + name);
        call.enqueue(new Callback<BrainTreePostResponse>(){
            @Override
            public void onResponse(Call<BrainTreePostResponse> call, Response<BrainTreePostResponse> response) {
                if (response.isSuccessful()){
                    BrainTreePostResponse brainTreePostResponse=response.body();
                    Log.i("mylog","" +response);
                    if (brainTreePostResponse!=null){
                    //    Toast.makeText(BrainTreeActivity.this, "success" , Toast.LENGTH_SHORT).show();
                        Bundle bundle=new Bundle();
                        if (brainTreePostResponse.getMsg()!=null && brainTreePostResponse.getMsg().equals("Success")){
                            bundle.putString("KEY_TRANSCATION", brainTreePostResponse.getId());
                            Intent intent=new Intent(BrainTreeActivity.this,BrainTreeTransactionSuccess.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                        else {
//                            bundle.putString("KEY_TRANSCATION", "Failed");
//                            Intent intent=new Intent(BrainTreeActivity.this,BrainTreeTransactionSuccess.class);
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                            finish();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<BrainTreePostResponse> call, Throwable t) {
                Toast.makeText(BrainTreeActivity.this, "Error :" +t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("mylog",t.getMessage());
//                Bundle bundle=new Bundle();
//                bundle.putString("KEY_TRANSCATION", "Sorry your transaction is failed");
//                Intent intent=new Intent(BrainTreeActivity.this,BrainTreeTransactionSuccess.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                finish();

            }
        });
    }




    public void getBrainsTreeData(){
        Call<BrainTreeResponse> call= RetrofitBrainTreeClient.sdaemonApi().getBrainTreeData();
        Log.e(getClass().getName(), "===" + call.request().url());

        call.enqueue(new Callback<BrainTreeResponse>() {
            @Override
            public void onResponse(Call<BrainTreeResponse> call, Response<BrainTreeResponse> response) {
                if (response.isSuccessful()){
                    llprogress.setVisibility(View.GONE);
                    btnpay.setVisibility(View.VISIBLE);

                    BrainTreeResponse brainTreeResponse=response.body();
                   token=brainTreeResponse.getClientIdToken();
                    Log.i("mylog","" +response);
                    Toast.makeText(BrainTreeActivity.this, "get request success", Toast.LENGTH_SHORT).show();
                    try {
                        mBraintreeFragment = BraintreeFragment.newInstance(BrainTreeActivity.this, token);
                        // mBraintreeFragment is ready to use!
                    } catch (InvalidArgumentException e) {
                        // There was an issue with your authorization string.
                    }
                    GooglePayment.isReadyToPay(mBraintreeFragment, new BraintreeResponseListener<Boolean>() {
                        @Override
                        public void onResponse(Boolean isReadyToPay) {
                            if (isReadyToPay) {
                                Log.i("mylog","" +isReadyToPay);
                                googleplay=isReadyToPay;
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<BrainTreeResponse> call, Throwable t) {
                llprogress.setVisibility(View.GONE);
                Toast.makeText(BrainTreeActivity.this, "Error :" +t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });

    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                llprogress.setVisibility(View.GONE);
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                if (data!=null) {
                    GooglePayment.tokenize(mBraintreeFragment, PaymentData.getFromIntent(data));
                }

//                CardBuilder cardbuilder=new CardBuilder();
//                        .cardNumber(result.getPaymentMethodType().toString())
//                        .expirationDate("12/20")
//                        .validate(true);
//
////                ThreeDSecure.performVerification(mBraintreeFragment,builder,TEST_AMOUNT);
//
//                Card.tokenize(mBraintreeFragment,builder);

//
//                CardBuilder cardBuilder = new CardBuilder();
//                cardBuilder.cardNumber(getCardNumber());
//                cardBuilder.expirationMonth(getExpirationMonth());
//                cardBuilder.expirationYear(getExpirationYear());
//                cardBuilder.cvv(getCvv());
//// get nonce token here.
//                nonce = mBraintreeApi.tokenize(cardBuilder);


                Log.d("mylog", "Result: " + stringNonce);
                Log.d("mylog", "amount name: " + result.getPaymentMethodType().name());
                Log.d("mylog", "amount google name: " + result.getPaymentMethodType().getCanonicalName());

                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server


                if (!Amount1.getText().toString().isEmpty()) {
//                    if (!edamount.getText().toString().isEmpty()){
                 //  Double amount = Double.valueOf(edamount.getText().toString());
                    Double amount = totalamount;
//                    getBrainsTreeData();
                    getBrainTree(amount,stringNonce);
  //                   paramHash = new HashMap<>();
//                   paramHash.put("amount", String.valueOf(amount));
//                    paramHash.put("nonce", stringNonce);
//                   sendPaymentDetails();

                } else
                    Toast.makeText(BrainTreeActivity.this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();

            }



            else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            } else {
                // handle errors here, an exception may be available in
                //GooglePayment.requestPayment(mBraintreeFragment, googlePaymentRequest);

                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("mylog", "Error : " + error.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    private void sendPaymentDetails() {
//        RequestQueue queue = Volley.newRequestQueue(BrainTreeActivity.this);
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.14/OakStudio_Services/api/BrainTreePayment/Create/",
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if(response.contains("Successful"))
//                        {
//                            Toast.makeText(BrainTreeActivity.this, "Transaction successful", Toast.LENGTH_LONG).show();
//                        }
//                        else Toast.makeText(BrainTreeActivity.this, "Transaction failed", Toast.LENGTH_LONG).show();
//                        Log.d("mylog", "Final Response: " + response.toString());
//                    }
//                }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("mylog", "Volley error : " + error.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                if (paramHash == null)
//                    return null;
//                Map<String, String> params = new HashMap<>();
//                for (String key : paramHash.keySet()) {
//                    params.put(key, paramHash.get(key));
//                    Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
//                }
//
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        queue.add(stringRequest);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPay:

                onBraintreeSubmit();
                break;
        }
    }


//https://www.codefactor.io/repository/github/square/retrofit/source/master/retrofit/src/test/java/retrofit2/RetrofitTest.java
//    https://stackoverflow.com/questions/47745364/how-to-make-button-overlap-cardview-at-the-bottom/47747179
}
