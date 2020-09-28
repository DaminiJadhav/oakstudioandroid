package com.sdaemon.oakstudiotv.activity;

import android.content.Context;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.ContactDTO;
import com.sdaemon.oakstudiotv.model.CompanyProfileResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.VERSION.SDK_INT;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG_FRAGMENT = "fragment";
    TextView tvaddress,tvemail,tvphone;
    EditText edFirstname,edLastname,edAddress,edEmail,edPhone,edCompany,edMessage,edisocode;
    private static final String phone_no_pattern = "[789][0-9]{9}";
    private static final String email_pattern = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    String firstName,lastName,email,message,phone,address,company;
    Button btnsend;
    LinearLayout llprogres;
    ImageView iv_back;
    String edcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        edFirstname=findViewById(R.id.ed_firstname);
        edLastname=findViewById(R.id.ed_lastname);
        edAddress=findViewById(R.id.ed_address);
        edEmail=findViewById(R.id.ed_email);
        edPhone=findViewById(R.id.ed_phone);
        edCompany=findViewById(R.id.ed_company);
        edMessage=findViewById(R.id.ed_message);
        tvaddress=findViewById(R.id.tv_address);
        tvemail=findViewById(R.id.tv_email);
        tvphone=findViewById(R.id.tv_phone);
        edisocode=(EditText)findViewById(R.id.ed_ios_code);
        llprogres=(LinearLayout) findViewById(R.id.ll_progress);

//        llprogres.setVisibility(View.VISIBLE);
        initialize();
        getContactDetail();

        btnsend=findViewById(R.id.btn_send);
        btnsend.setOnClickListener(this);


    }

    private void initialize(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Fragment bFragment = new FragmentHelpandInfo();
//                fm.beginTransaction()
//                        .remove(fm.findFragmentById(R.id.content)) // resolves to A_Fragment instance
//                        .add(R.id.content, bFragment, "fragment-b")
//                        .addToBackStack("a")
//                        .commit();
//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new FragmentHelpandInfo()).commit();
                finish();
//                onBackPressed();
            }
        });


        boolean test=isSimSupport(this);
        Log.d(TAG_FRAGMENT, "test: "+test);

        if (test==true) {
            try {
                Log.d(TAG_FRAGMENT, "countryvalue: " + GetCountryZipCode());
                edisocode.setText(GetCountryZipCode());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                if (SDK_INT >8){
                    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    JSONObject result=getJSONObjectFromURL("http://ip-api.com/json");
                    String date=result.getString("countryCode");
                    edisocode.setText(getCountrycode(date));

                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                if (validation()){
                    PostContactData();
//                        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                        emailIntent.setType("plain/text");
//                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"daminijadhav1213@gmail.com"});
////                        String message = edMessage.getText().toString();
//                        String text = "Contact Detail " ;
//                        String contactDetail="First Name      :" +firstName+  "\nPhone No.  :" +edcode.concat(" ").concat(phone)+ "\nAddress   :" +address;
//                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, text);
//                        emailIntent.putExtra(Intent.EXTRA_TEXT, contactDetail);
////                        emailIntent.putExtra(Intent.EXTRA_STREAM, email);
////                        emailIntent.putExtra(Intent.EXTRA_TITLE,message);
//                        startActivity(Intent.createChooser(emailIntent, "Send mail"));

                }
        }

    }



    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader reader=new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder builder=new StringBuilder();

        String line;
        while ((line=reader.readLine())!=null){
            builder.append(line+"\n");

        }
        reader.close();

        String jsonString=builder.toString();

        return new JSONObject(jsonString);
    }


    public void getContactDetail(){
        Call<List<CompanyProfileResponse>> call= RetroClient.sdaemonApi().getContactUs();
        call.enqueue(new Callback<List<CompanyProfileResponse>>() {
            @Override
            public void onResponse(Call<List<CompanyProfileResponse>> call, Response<List<CompanyProfileResponse>> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(ContactActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    List<CompanyProfileResponse> companyProfileResponse=response.body();
                    if (companyProfileResponse!=null){
                        tvaddress.setVisibility(View.VISIBLE);
                        tvemail.setVisibility(View.VISIBLE);
                        tvaddress.setText(getResources().getString(R.string.address)+"   : " +companyProfileResponse.get(0).getAddress());
                        tvemail.setText(getResources().getString(R.string.contact_email_id)+"   : " +companyProfileResponse.get(0).getEmail());
//                    tvphone.setText("" +companyProfileResponse.get(0).getPhone);

                    }

                }
            }

            @Override
            public void onFailure(Call<List<CompanyProfileResponse>> call, Throwable t) {
//                Toast.makeText(ContactActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void PostContactData(){
        Call<ContactDTO> call=  RetroClient.sdaemonApi().PostContactDetail(contatcData());
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<ContactDTO>() {
            @Override
            public void onResponse(Call<ContactDTO> call, Response<ContactDTO> response) {
                if (response.isSuccessful()){
                    Log.i("Contact data",""+contatcData());
                    Toast.makeText(ContactActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ContactDTO> call, Throwable t) {
                Toast.makeText(ContactActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    ContactDTO  contatcData(){
        ContactDTO dto=new ContactDTO();
        try {
            dto.setFirstName(firstName);
            dto.setLastName(lastName);
            dto.setAddress(address);
            dto.setEmail(email);
            dto.setPhoneNo(edcode.concat(phone));
            dto.setCompany(company);
            dto.setMessage(message);
            Log.i(getClass().getName(), "=========== DTO dd :" + dto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dto;
    }



    public static boolean isSimSupport(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);
    }


    public String GetCountryZipCode() {
        String CountryID = "";
        String CountryZipCode = "";
//​
        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID = manager.getSimCountryIso().toUpperCase();
        Log.v(TAG_FRAGMENT, "CountryID" + CountryID);
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                CountryZipCode = g[0];
                break;
            }
        }
        return CountryZipCode;
    }



    public String getCountrycode(String s)
    {
        String CountryID = "";
        String CountryZipCode = "";
        //getNetworkCountryIso
        CountryID = s;
        Log.v(TAG_FRAGMENT, "CountryID" + CountryID);
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");
            if (g[1].trim().equals(CountryID.trim())) {
                CountryZipCode = g[0];
                break;
            }
        }
        return CountryZipCode;
    }


    public boolean validation(){
        firstName=edFirstname.getText().toString();
        lastName=edLastname.getText().toString();
        address=edAddress.getText().toString();
        email=edEmail.getText().toString();
        message=edMessage.getText().toString();
        phone=edPhone.getText().toString();
        company=edCompany.getText().toString();



//        if (TextUtils.isEmpty(firstName)  &&  TextUtils.isEmpty(lastName)  && TextUtils.isEmpty(email) && TextUtils.isEmpty(message) && TextUtils.isEmpty(phone)){
//            Toast.makeText(this, "Field Cannot Be Empty", Toast.LENGTH_SHORT).show();
//            return false;
//
//        }

        if (TextUtils.isEmpty(firstName)){
            edFirstname.setError(getResources().getString(R.string.field_cannot_be_empty));
            return false;
        }

        if (TextUtils.isEmpty(lastName)){
            edLastname.setError(getResources().getString(R.string.field_cannot_be_empty));
            return false;
        }

        if (TextUtils.isEmpty(address)){
            edAddress.setError(getResources().getString(R.string.field_cannot_be_empty));
            return false;
        }


            if (TextUtils.isEmpty(email)){
            edEmail.setError(getResources().getString(R.string.field_cannot_be_empty));
            return false;
        }
         if (!email.matches(email_pattern)){
            edEmail.setError(getResources().getString(R.string.enter_valid_email_id));
             return false;

        }

        if (TextUtils.isEmpty(phone)){
            edPhone.setError(getResources().getString(R.string.field_cannot_be_empty));
            return false;
        }
        if (!phone.matches(phone_no_pattern)){
            edPhone.setError(getResources().getString(R.string.enter_valid_phone_number));
            return false;

        }


        if(edisocode.getText().toString()!=null)
        {
            edcode=edisocode.getText().toString();
        }
        else
        {
            edPhone.setError(getResources().getString(R.string.enter_country_code));

//            Toast.makeText(this, "Enter the country  code ", Toast.LENGTH_SHORT).show();
        }




      /*  if (TextUtils.isEmpty(message)){
            edMessage.setError("Field Cannot Be Empty");
            return false;
        }*/




        return true;
    }

    @Override
    public void onBackPressed() {
//        final FragmentHelpandInfo fragment = (FragmentHelpandInfo) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);

        super.onBackPressed();
//        fragment.onBackPressed();


//        FragmentManager fm=getSupportFragmentManager();
//        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                if(getSupportFragmentManager().getBackStackEntryCount() == 0) finish();
//            }
//        });
           }
}
