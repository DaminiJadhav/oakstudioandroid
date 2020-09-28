package com.sdaemon.oakstudiotv.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.Select_PaymentMethodActivity;
import com.sdaemon.oakstudiotv.activity.Select_PriceActivity;
import com.sdaemon.oakstudiotv.activity.paypal.PaypalActivity;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPayment_details extends Fragment implements View.OnClickListener {
    private View rootView;
    private TextView tv_selectPaymentMethod, tv_monthlyPrice;
    private LinearLayout ll_creditCard, ll_bankAcc, ll_Paypal, signUP_Plan;
    private String stringValue;
    private TextView tv_PaympleMsg;
    private TextView tv_number;
    private Button paypalbutton;
    public static String strEditText;



    public FragmentPayment_details() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentPayment_details(String s) {
        this.stringValue = s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_payment_details, container, false);

        initialize();
        return rootView;
    }

    private void initialize() {

        tv_selectPaymentMethod = (TextView) rootView.findViewById(R.id.tv_selectPaymentMethod);
        tv_monthlyPrice = (TextView) rootView.findViewById(R.id.tv_monthlyPrice);
        tv_PaympleMsg = (TextView) rootView.findViewById(R.id.tv_PaympleMsg);

        ll_creditCard = (LinearLayout) rootView.findViewById(R.id.ll_creditCard);
        ll_bankAcc = (LinearLayout) rootView.findViewById(R.id.ll_bankAcc);
        ll_Paypal = (LinearLayout) rootView.findViewById(R.id.ll_Paypal);
        signUP_Plan = (LinearLayout) rootView.findViewById(R.id.signUP_Plan);
        paypalbutton=(Button)rootView.findViewById(R.id.paypal);

        paypalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaypalActivity.class);
                startActivityForResult(intent, 1);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
        tv_number = (TextView)rootView.findViewById(R.id.tv_number);
        String text = "<font color=#9AEB3D>3</font> <font color=#505053>/3</font>";
        tv_number.setText(Html.fromHtml(text));

      //  SignUp_Plan_Activity.rl_plan.setVisibility(View.GONE);

        tv_selectPaymentMethod.setOnClickListener(this);
        tv_monthlyPrice.setOnClickListener(this);

        if (stringValue!=null) {
            if (stringValue.equals("1")) {
                signUP_Plan.setVisibility(View.VISIBLE);
            } else {
                signUP_Plan.setVisibility(View.GONE);
            }
        }

        String text1 = "<font color=#FFFFFF>Click the</font> <font color=#9AEB3D>Continue to PayPal</font> <font color=#FFFFFF>button and log in to Paypal using your email and password</font>";;
        tv_PaympleMsg.setText(Html.fromHtml(text1));
    }

    public static CharSequence trim(CharSequence s, int start, int end) {
        while (start < end && Character.isWhitespace(s.charAt(start))) {
            start++;
        }

        while (end > start && Character.isWhitespace(s.charAt(end - 1))) {
            end--;
        }

        return s.subSequence(start, end);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_selectPaymentMethod:
                Intent intent = new Intent(getActivity(), Select_PaymentMethodActivity.class);
                startActivityForResult(intent, 1);
                ((Activity) getActivity()).overridePendingTransition(0, 0);

                break;

            case R.id.tv_monthlyPrice:
                Intent intent1 = new Intent(getActivity(), Select_PriceActivity.class);
                startActivity(intent1);
                ((Activity) getActivity()).overridePendingTransition(0, 0);

                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                strEditText = data.getStringExtra("editTextValue");

                // Toast.makeText(getContext(), ""+strEditText, Toast.LENGTH_SHORT).show();

                if (strEditText.equals(getResources().getString(R.string.credit_card))) {
                    ll_creditCard.setVisibility(View.VISIBLE);
                    ll_Paypal.setVisibility(View.GONE);
                    ll_bankAcc.setVisibility(View.GONE);
                    tv_selectPaymentMethod.setText(strEditText);
                } else if (strEditText.equals(getResources().getString(R.string.bank_account))) {
                    ll_Paypal.setVisibility(View.GONE);
                    ll_creditCard.setVisibility(View.GONE);
                    ll_bankAcc.setVisibility(View.VISIBLE);
                    tv_selectPaymentMethod.setText(strEditText);
                } else {
                    ll_creditCard.setVisibility(View.GONE);
                    ll_bankAcc.setVisibility(View.GONE);
                    ll_Paypal.setVisibility(View.VISIBLE);
                    tv_selectPaymentMethod.setText(strEditText);
                }



            }
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getContext(), "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(getContext(), "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
