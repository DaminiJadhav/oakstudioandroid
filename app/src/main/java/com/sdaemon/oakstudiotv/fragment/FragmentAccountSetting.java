package com.sdaemon.oakstudiotv.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.microsoft.identity.client.ILoggerCallback;
import com.microsoft.identity.client.Logger;
import com.microsoft.identity.client.MsalClientException;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.User;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.ChangeAccountPassActivity;
import com.sdaemon.oakstudiotv.activity.CheckInternetSpeedActivity;
import com.sdaemon.oakstudiotv.activity.ChooseShareActivity;
import com.sdaemon.oakstudiotv.activity.CommunicationPrefActivity;
import com.sdaemon.oakstudiotv.activity.Home_WithOut_UserActivity;
import com.sdaemon.oakstudiotv.activity.SelectLanguageActivity;
import com.sdaemon.oakstudiotv.activity.SelectMovieDetailsActivity;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.AccountSettingDTO;
import com.sdaemon.oakstudiotv.dto.ContactDTO;
import com.sdaemon.oakstudiotv.model.CountryListResponse;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAccountSetting extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private static final String TAG = "TasksSample";
    public  long startTime;
    public   long endTime;
    public  long fileSize;

    // bandwidth in kbps
    public  int POOR_BANDWIDTH = 150;
    public int AVERAGE_BANDWIDTH = 550;
    public  int GOOD_BANDWIDTH = 2000;
    private View rootView;
    private TextView tvchangePass,tv_language,tv_comunicationPref,tv_chooseShare;
    Button btn_speedtest1,btnlogout,btnsave;
    private Context mContext;
    Spinner countrySpinner,statespinner,cityspinner;
    EditText edname,edlname,edemail,edaddress,edpostcode;


    AppSession appSession;
    private PublicClientApplication sampleApp;
    AppSubClass state;
    private String[] scopes;
    String language="English";
    String uniqueId,cityid;
    int  countryid,stateid;
    List<String> countryName=new ArrayList<>();
    List<String> stateName=new ArrayList<>();
    List<String> cityName=new ArrayList<>();

    List<CountryListResponse> countryids=new ArrayList<>();

    List<CountryListResponse> stateids=new ArrayList<>();

    List<CountryListResponse> cityids=new ArrayList<>();


    public FragmentAccountSetting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_fragment_account_setting, container, false);
        mContext=getContext();
        intitialization();
        return rootView;
    }

    private void intitialization() {

        tvchangePass=(TextView)rootView.findViewById(R.id.tv_changePass);
        tv_language=(TextView)rootView.findViewById(R.id.tv_language);
        tv_comunicationPref=(TextView)rootView.findViewById(R.id.tv_comunicationPref);
        tv_chooseShare=(TextView)rootView.findViewById(R.id.tv_chooseShare);
        btn_speedtest1=rootView.findViewById(R.id.btn_speedtest);
        btnlogout=rootView.findViewById(R.id.btn_logout);

        btnsave=rootView.findViewById(R.id.btn_save);
        btnsave.setOnClickListener(this);


        countrySpinner=rootView.findViewById(R.id.country);
        countrySpinner.setOnItemSelectedListener(this);

        statespinner=rootView.findViewById(R.id.state);
        statespinner.setOnItemSelectedListener(this);

        cityspinner=rootView.findViewById(R.id.city);
        cityspinner.setOnItemSelectedListener(this);


        edname=rootView.findViewById(R.id.ed_name);
        edlname=rootView.findViewById(R.id.ed_lname);

        edemail=rootView.findViewById(R.id.ed_email);
        edaddress=rootView.findViewById(R.id.ed_address);
        edpostcode=rootView.findViewById(R.id.ed_postalcode);




        final TextView occupiedSpaceText = (TextView)rootView.findViewById(R.id.occupiedSpace);
        final TextView freeSpaceText = (TextView)rootView.findViewById(R.id.freeSpace);
        final ProgressBar progressIndicator = (ProgressBar)rootView.findViewById(R.id.indicator);
        final float totalSpace = DeviceMemory.getInternalStorageSpace();
        final float occupiedSpace = DeviceMemory.getInternalUsedSpace();
        final float freeSpace = DeviceMemory.getInternalFreeSpace();
        final DecimalFormat outputFormat = new DecimalFormat("#.##");




        tvchangePass.setOnClickListener(this);
        tv_language.setOnClickListener(this);
        tv_comunicationPref.setOnClickListener(this);
        tv_chooseShare.setOnClickListener(this);
        btn_speedtest1.setOnClickListener(this);
        btnlogout.setOnClickListener(this);


        appSession=AppSession.getInstance(mContext);
        uniqueId=appSession.getUserDTO().getResult().getUniqueIdentifire();
        language=appSession.getSelectedLanguage();
        Log.i("Language get" ,""+language);

        tv_language.setText(language);
        getUserProfileDetail();
        getCountrysList();
        if (null != occupiedSpaceText) {
            occupiedSpaceText.setText(outputFormat.format(occupiedSpace) + " MB");
        }

        if (null != freeSpaceText) {
            freeSpaceText.setText(outputFormat.format(freeSpace) + " MB");
        }

        if (null != progressIndicator) {
            progressIndicator.setMax((int) totalSpace);
            progressIndicator.setProgress((int)occupiedSpace);
        }


        state = (AppSubClass) this.getContext().getApplicationContext();
        scopes = Constants.SCOPES.split("\\s+");

        /* Initializes the app context using MSAL */
        sampleApp = state.getPublicClient();

        /* Initialize the MSAL App context */
        if (sampleApp == null) {
            sampleApp = new PublicClientApplication(
                    mContext,
                    Constants.CLIENT_ID,
                    String.format(Constants.AUTHORITY, Constants.TENANT, Constants.SISU_POLICY));
            state.setPublicClient(sampleApp);

            Logger.getInstance().setExternalLogger(new ILoggerCallback() {
                @Override
                public void log(String tag, Logger.LogLevel logLevel, String message, boolean containsPII) {
                    Log.d(tag, "MSAL_LOG: " + message);
                }
            });
        }
    }




    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public static class DeviceMemory {

        public static float getInternalStorageSpace() {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            //StatFs statFs = new StatFs("/data");
            float total = ((float)statFs.getBlockCount() * statFs.getBlockSize()) / 1048576;
            return total;
        }

        public static float getInternalFreeSpace() {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            //StatFs statFs = new StatFs("/data");
            float free  = ((float)statFs.getAvailableBlocks() * statFs.getBlockSize()) / 1048576;
            return free;
        }

        public static float getInternalUsedSpace() {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            //StatFs statFs = new StatFs("/data");
            float total = ((float)statFs.getBlockCount() * statFs.getBlockSize()) / 1048576;
            float free  = ((float)statFs.getAvailableBlocks() * statFs.getBlockSize()) / 1048576;
            float busy  = total - free;
            return busy;
        }
    }

    public void checkSpeedTest()
    {

        ConnectivityManager connectivityManager= (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected()==true)
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://homepages.cae.wisc.edu/~ece533/images/airplane.png")    .build();

            startTime = System.currentTimeMillis();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        Log.d(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    InputStream input = response.body().byteStream();
                    try {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        while (input.read(buffer) != -1) {
                            bos.write(buffer);
                        }
                        byte[] docBuffer = bos.toByteArray();
                        fileSize = bos.size();
                    } finally {
                        input.close();
                    }
                    endTime = System.currentTimeMillis();
                    // calculate how long it took by subtracting endtime from starttime
                    double timeTakenMills = Math.floor(endTime - startTime);  // time taken in milliseconds
                    double timeTakenSecs = timeTakenMills / 1000;  // divide by 1000 to get time in seconds
                    final int kilobytePerSec = (int) Math.round(1024 / timeTakenSecs);
                    if(kilobytePerSec <= POOR_BANDWIDTH){
                        // slow connection
                    }
                    // get the download speed by dividing the file size by time taken to download
                    double speed = fileSize / timeTakenMills;
                    Log.d(TAG, "Time taken in secs: " + timeTakenSecs);
                    Log.d(TAG, "kilobyte per sec: " + kilobytePerSec);
                    Log.d(TAG, "Download Speed: " + speed);
                    Log.d(TAG, "File size: " + fileSize);
                }
            });


        }
        else {
            Toast.makeText(mContext, "Network Not Available", Toast.LENGTH_SHORT).show();
        }

    }





    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_changePass:
                Intent i = new Intent(getActivity(), ChangeAccountPassActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0,0);

                break;

            case R.id.tv_language:

                Intent intent = new Intent(getActivity(), SelectLanguageActivity.class);
                startActivityForResult(intent, 1);
//                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0,0);

                break;

            case R.id.tv_comunicationPref:

                Intent intent1 = new Intent(getActivity(), CommunicationPrefActivity.class);
                startActivity(intent1);
                ((Activity) getActivity()).overridePendingTransition(0,0);

                break;

            case R.id.tv_chooseShare:

                Intent intent2 = new Intent(getActivity(), ChooseShareActivity.class);
                startActivity(intent2);
                ((Activity) getActivity()).overridePendingTransition(0,0);

                break;

           case R.id.btn_logout:
                //  onSignOutClicked();
                dialogLogout(getResources().getString(R.string.are_you_sure_you_want_to_logout));
                break;

          case R.id.btn_speedtest:
//              checkSpeedTest();
              Intent intent3=new Intent(mContext, CheckInternetSpeedActivity.class);
              startActivity(intent3);
              ((getActivity())).overridePendingTransition(0, 0);

              break;

            case R.id.btn_save:

               postUserDetail();
                break;


        }
    }


    public void dialogLogout(String message) {
        final Dialog dialog = new Dialog(getContext());
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_box_yes_no);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.logout));
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText(Html.fromHtml("" + message));
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                appSession.logout();
                Log.i("AppSession Genre",""+appSession.getGenreIDposition());
                Log.i("AppSession Review",""+appSession.getReviewIDposition());
                Log.i("AppSession Feature",""+appSession.getFeatureIDposition());
                Log.i("AppSession Studio",""+appSession.getStudioIDposition());
                Log.i("AppSession Rating",""+appSession.getRatingIDposition());
                Log.i("AppSession Year",""+appSession.getYearIDposition());




//                appSession.setGenreID("");
                onSignOutClicked();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void onSignOutClicked() {

        /* Attempt to get a account and remove their cookies from cache */
        List<User> accounts = null;

        try {
            accounts = sampleApp.getUsers();

            if (accounts == null) {
                /* We have no accounts */
                Log.d("HelpandInfo", "0000000  We have no accounts");

            } else if (accounts.size() == 1) {
                /* We have 1 account */
                /* Remove from token cache */
                //  sampleApp.removeAccount(accounts.get(0));
                Log.d("HelpandInfo", "0000000 accounts.get(0): " + accounts.get(0));
                sampleApp.remove(accounts.get(0));

                //  updateSignedOutUI();

                Toast.makeText(mContext, "updateSignedOutUI", Toast.LENGTH_SHORT)
                        .show();

                Log.d("HelpandInfo", "0000000 updateSignedOutUI");

            } else {
                /* We have multiple accounts */
                for (int i = 0; i < accounts.size(); i++) {
                    //  sampleApp.removeAccount(accounts.get(i));
                    Log.d("", "0000000 accounts.get(i): " + accounts.get(i));
                    sampleApp.remove(accounts.get(i));
                }
            }

            Toast.makeText(mContext, "Signed Out!", Toast.LENGTH_SHORT)
                    .show();

            Log.d("HelpandInfo", "0000000 Signed Out!");

            Intent intent = new Intent(mContext, Home_WithOut_UserActivity.class);
            //  intent.putExtra(PN_TYPE, LOGOUT_APP);
            startActivity(intent);
            getActivity().finish();


        } catch (MsalClientException e) {
            Log.d("HelpandInfo", "User at this position does not exist: " + e.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1){
            if (resultCode==RESULT_OK){
                language = data.getStringExtra("Key_Language");
                tv_language.setText(language);
                Log.i("Language get" ,""+language);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void getUserProfileDetail(){
        Log.i("Unique id",""+uniqueId);
       retrofit2.Call<AccountSettingDTO> call = RetroClient.sdaemonApi().getUserDetail(uniqueId);
       call.enqueue(new retrofit2.Callback<AccountSettingDTO>() {
           @Override
           public void onResponse(retrofit2.Call<AccountSettingDTO> call, retrofit2.Response<AccountSettingDTO> response) {
               if (response.isSuccessful()){
                   AccountSettingDTO settingDTO=response.body();
                   edname.setText(settingDTO.getFirstName());
                   edlname.setText(settingDTO.getLastName());
                   edemail.setText(settingDTO.getEmail());
                   edaddress.setText(settingDTO.getAddress());
                   edpostcode.setText(""+settingDTO.getPostalCode());

//                   Toast.makeText(getContext(), "success"+response.message(), Toast.LENGTH_SHORT).show();
               }else {
//                   Toast.makeText(getContext(), "unsuccess"+response.message(), Toast.LENGTH_SHORT).show();

               }

           }

           @Override
           public void onFailure(retrofit2.Call<AccountSettingDTO> call, Throwable t) {
//               Toast.makeText(getContext(), "error" +t.getMessage(), Toast.LENGTH_SHORT).show();

           }
       });
    }

    public void postUserDetail(){
//        userAccountDetail();
        Log.i(" Values"," name "+edname.getText().toString()+" lname "+edlname.getText().toString()+" email  "+edemail.getText().toString()+" address "+edaddress.getText().toString()+" country "+countryid+" state"+stateid+" city "+cityid+" postcode "+edpostcode.getText().toString());

        retrofit2.Call<AccountSettingDTO> call=RetroClient.sdaemonApi().postUserDetail(userAccountDetail());
        call.enqueue(new retrofit2.Callback<AccountSettingDTO>() {
            @Override
            public void onResponse(retrofit2.Call<AccountSettingDTO> call, retrofit2.Response<AccountSettingDTO> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(getContext(), "success "+response.message(), Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(getContext(), "unsuccess"+response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(retrofit2.Call<AccountSettingDTO> call, Throwable t) {
//                Toast.makeText(getContext(), "error " +t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getCountrysList(){
        retrofit2.Call<List<CountryListResponse>> call=RetroClient.sdaemonApi().getCountryList();
        call.enqueue(new retrofit2.Callback<List<CountryListResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<CountryListResponse>> call, retrofit2.Response<List<CountryListResponse>> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(getContext(), "success"+response.message(), Toast.LENGTH_SHORT).show();

                    countryids=response.body();

                    for (int i=0;i<response.body().size();i++) {
                        countryName.add(response.body().get(i).getText());
//                        String countryName = response.body().get(i).getText();
                    }
                    if (getActivity()!=null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_items, countryName);
//                    ivdownarrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countrySpinner.setAdapter(adapter);
                    }


                }else {
//                    Toast.makeText(getContext(), "unsuccess"+response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<CountryListResponse>> call, Throwable t) {
//                Toast.makeText(getContext(), "error" +t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void getstatesList(int countryid){
        retrofit2.Call<List<CountryListResponse>> call=RetroClient.sdaemonApi().getStateList(countryid);
        call.enqueue(new retrofit2.Callback<List<CountryListResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<CountryListResponse>> call, retrofit2.Response<List<CountryListResponse>> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(getContext(), "success"+response.message(), Toast.LENGTH_SHORT).show();

                    stateids=response.body();
                    stateName.clear();
                    for (int i=0;i<response.body().size();i++) {
                        stateName.add(response.body().get(i).getText());
//                        String countryName = response.body().get(i).getText();
                    }
                    if (getActivity()!=null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_items, stateName);
//                    ivdownarrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        statespinner.setAdapter(adapter);
                    }


                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<CountryListResponse>> call, Throwable t) {
//                Toast.makeText(getContext(), "error" +t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void getCityList(int stateID){
        retrofit2.Call<List<CountryListResponse>> call=RetroClient.sdaemonApi().getcityList(stateID);
        call.enqueue(new retrofit2.Callback<List<CountryListResponse>>() {
            @Override
            public void onResponse(retrofit2.Call<List<CountryListResponse>> call, retrofit2.Response<List<CountryListResponse>> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(getContext(), "success"+response.message(), Toast.LENGTH_SHORT).show();

                    cityids=response.body();

                    cityName.clear();
                    for (int i=0;i<response.body().size();i++) {
                        cityName.add(response.body().get(i).getText());
//                        String countryName = response.body().get(i).getText();
                    }
                    if (getActivity()!=null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_items, cityName);
//                    ivdownarrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cityspinner.setAdapter(adapter);
                    }

                }else {
//                    Toast.makeText(getContext(), "unsuccess"+response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<CountryListResponse>> call, Throwable t) {
//                Toast.makeText(getContext(), "error" +t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.country) {
//              Toast.makeText(adapterView.getContext(),
//                    "Selected Item : " + adapterView.getItemAtPosition(i).toString(),
//                    Toast.LENGTH_SHORT).show();

            countryid=countryids.get(i).getValue();
              Log.i("Country id",""+countryid);
            getstatesList(countryid);
//            edaddress.requestFocus();


        }
        if(adapterView.getId() == R.id.state) {
//            Toast.makeText(adapterView.getContext(),
//                    "Selected Item : " + adapterView.getItemAtPosition(i).toString(),
//                    Toast.LENGTH_SHORT).show();

            if (stateids.size()!=0) {

               stateid = stateids.get(i).getValue();
                Log.i("state id", "" + stateid);
                getCityList(stateid);
            }

        }
        if (adapterView.getId() == R.id.city){
            if (cityids.size()!=0) {
                cityid = cityids.get(i).getValue().toString();
            }

        }
    }

    AccountSettingDTO userAccountDetail(){
        AccountSettingDTO dto=new AccountSettingDTO();
        try {


            dto.setUniqueIdentifire(uniqueId);
            dto.setFirstName(edname.getText().toString());
            dto.setLastName(edlname.getText().toString());
            dto.setEmail(edemail.getText().toString());
            dto.setAddress(edaddress.getText().toString());
            dto.setCountryId(countryid);
            dto.setStateId(stateid);
            dto.setCity(cityid);
            dto.setPostalCode(Integer.valueOf(edpostcode.getText().toString()));

//            dto.setUniqueIdentifire("35971c47-d1ab-45f9-b9a9-b96c4a8535c4");
//            dto.setFirstName("Riya");
//            dto.setLastName("Sharma");
//            dto.setEmail("riya@gmail.com");
//            dto.setAddress("pune");
//            dto.setCountryId(1);
//            dto.setStateId(1152);
//            dto.setCity("1");
//            dto.setPostalCode(423123);









            Log.i(getClass().getName(), "=========== DTO dd :" + dto);
        }catch (Exception e){
            e.printStackTrace();
        }







































        return dto;
    }



}

