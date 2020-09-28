package com.sdaemon.oakstudiotv.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.CareerResponse;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.CareerListAdapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.model.RealPathUtil;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sdaemon.oakstudiotv.utils.AppConstants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

public class CareersActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG_FRAGMENT = "fragment";
    Button btnSelectImage, btnsend, btnclear;
    TextView txtCareer,tvcareerdetail;
    public static String resume_detail="";
    Spinner spinner;
    ScrollView scrollView;
    CareerListAdapter careerListAdapter;
    List<CareerResponse> list = new ArrayList<>();
    private ArrayList<CareerResponse.TblCareerDetails> tblCareerDetailsList=new ArrayList<>();
    private ArrayList<String> positinName = new ArrayList<String>();
    EditText edfirstName, edMessage,edposition,edfile,edemail,edphone;
    String firstName,email,position,phone,message,selectfile;
    ExpandableListView expandableListView;
    LinearLayout lluserInformation,llcareerDetail,llprogress;
    ProgressBar progressBar;
    ImageView imageView,iv_back;
    public static Uri uriFromPath;
    private static final String phone_no_pattern = "[789][0-9]{9}";
    private static final String email_pattern = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(email_pattern);

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careers);


        btnsend = findViewById(R.id.btn_send);
      //  btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
//        btnclear=(Button) findViewById(R.id.btn_clear);
//        txtRealPath = (TextView) findViewById(R.id.txtRealPath);
//        edfirstName=(EditText) findViewById(R.id.ed_firstname);

//        edMessage = (EditText) findViewById(R.id.ed_message);
//        edfile = (EditText) findViewById(R.id.ed_file);
//        edemail=(EditText) findViewById(R.id.ed_email);
//        edposition=(EditText) findViewById(R.id.ed_position);
//        edphone=(EditText) findViewById(R.id.ed_phone);
//        imageView = (ImageView) findViewById(R.id.imgView);
//        txtCareer=(TextView) findViewById(R.id.tv_career);
//        tvcareerdetail=(TextView) findViewById(R.id.tv_careerdetail);
//        spinner=(Spinner) findViewById(R.id.spinner_position);
        lluserInformation=(LinearLayout) findViewById(R.id.ll_mainuserInformation);
        llcareerDetail=(LinearLayout) findViewById(R.id.ll_careerDetail);
        expandableListView = (ExpandableListView) findViewById(R.id.elv_careerDetail);
        progressBar=(ProgressBar) findViewById(R.id.ll_progress);
//        scrollView=findViewById(R.id.scrollable);

      //  btnSelectImage.setOnClickListener(this);

        progressBar.setVisibility(View.VISIBLE);
        initialize();
        getTblCareers();

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_career);

//        ScrollView scroll = new ScrollView(this);
//        scroll.setBackgroundColor(getResources().getColor(R.color.transparent));
//        scroll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        if(llcareerDetail.getParent() != null) {
//            ((ViewGroup)llcareerDetail.getParent()).removeView(llcareerDetail); // <- fix
//        }
//        scroll.addView(llcareerDetail);

//        ScrollView scrollView = new ScrollView(this);
//        scrollView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//
//        LinearLayout linearLayout = new LinearLayout(this);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.setGravity(Gravity.RIGHT);
//        linearLayout.addView(relativeLayout);
//        scrollView.addView(linearLayout);
//
//

        //        getExpandableListView().setGroupIndicator(getResources().getDrawable(R.drawable.iv_plus));
//        ScrollView sv = (ScrollView)findViewById(R.id.scrollview);
//        sv.scrollTo(0, sv.getBottom());
//        sv.pageScroll(View.FOCUS_DOWN);

//        RelativeLayout relativeLayout=new RelativeLayout(R.id.)


       /* btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {

//                if (!firstName.isEmpty() && !position.isEmpty() && !message.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
                   if (Validation()){
                    final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"daminijadhav1213@gmail.com"});
//                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, txtUriPath);
//                    emailIntent.putExtra(Intent.EXTRA_TEXT)
                    if (uriFromPath != null) {
                        emailIntent.putExtra(Intent.EXTRA_STREAM, uriFromPath);
                    }
                    String message = edMessage.getText().toString();
                    String s = edposition.getText().toString();
                    String text = "Applied for the position " + s;
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, text);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, message);
                    emailIntent.putExtra(Intent.EXTRA_STREAM, uriFromPath);


                    startActivity(Intent.createChooser(emailIntent, "Sending Email......"));

//                } catch (Throwable throwable) {
//                    Toast.makeText(CareersActivity.this, "Request failed try again: " + throwable.toString(), Toast.LENGTH_LONG).show();
//
//                }
                }

        else {
//                Toast.makeText(CareersActivity.this, "Please fill all mandatory field", Toast.LENGTH_SHORT).show();
            }
            }
        });
*/


    }

    private void initialize(){
//        progressBar.setVisibility(View.GONE);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent careerIntent = new Intent(CareersActivity.this, HelpandInfoActivity.class);
//                startActivity(careerIntent);
//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new FragmentHelpandInfo()).commit();
                finish();
//                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
//        // 1. on Upload click call ACTION_GET_CONTENT intent
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        // 2. pick image only
//        intent.setType("image/*");
//        // 3. start activity
//        startActivityForResult(intent, 0);

//
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/pdf");

//        intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//        intent.setType("image/*|application/pdf|audio/*");
//            intent.setType("*/*");
//            startActivityForResult(intent, 0);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        careerListAdapter.onActivityResult(requestCode,resultCode,data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            String realPath;
            // SDK < API11
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                // SDK > 19 (Android 4.4)
            else
                checkPermissionREAD_EXTERNAL_STORAGE(this);
            data.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());

            if (realPath.equalsIgnoreCase("")) {

              //  realPath = data.getData().getPath().toString();

                realPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

            }
            String file_1 = realPath.substring(
                    realPath.lastIndexOf('/') + 1,
                    realPath.length());
            Log.i("File Name 1 ", file_1);


            setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(), realPath);





        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void setTextViews(int sdk, String uriPath, String realPath) {
//        txtUriPath.setText("URI Path: "+uriPath);
        File file = new File(uriPath);
        resume_detail=file.getName();
       // edfile.setText(" " + file.getName());

        uriFromPath = Uri.fromFile(new File(realPath));

        // you have two ways to display selected image

        // ( 1 ) imageView.setImageURI(uriFromPath);

        // ( 2 ) imageView.setImageBitmap(bitmap);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        imageView.setImageBitmap(bitmap);

        Log.d("HMKCODE", "Build.VERSION.SDK_INT:" + sdk);
        Log.d("HMKCODE", "URI Path:" + uriPath);
        Log.d("HMKCODE", "Real Path: " + realPath);
    }


    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    showDialog("External storage", context,
//                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void getTblCareers() {
//        progressBar.setVisibility(View.GONE);
        Call<List<CareerResponse>> call = RetroClient.sdaemonApi().getCareers();
        call.enqueue(new Callback<List<CareerResponse>>() {
            @Override
            public void onResponse(Call<List<CareerResponse>> call, Response<List<CareerResponse>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
//                Toast.makeText(CareersActivity.this, "Success", Toast.LENGTH_LONG).show();
//                String respone=response.body().toString();
                    List<CareerResponse> careerResponses = response.body();

//                int item_size = 50;  // 50px
//                int sub_item_size = 40;  // 40px

//                if (careerListAdapter!=null) {
                    careerListAdapter = new CareerListAdapter(CareersActivity.this, careerResponses, onClickCallback, onClickCallback1);
//                  expandableListView.getLayoutParams().height = item_size*careerListAdapter.getGroupCount();

                    list.addAll(response.body());
                    expandableListView.setAdapter(careerListAdapter);
                }
//                }

                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {

//                        int nb_children = careerListAdapter.getChildrenCount(groupPosition);
//                        expandableListView.getLayoutParams().height += sub_item_size*nb_children;

//                        int height=0;
//                        for (int i=0;i<expandableListView.getChildCount();i++){
//                            height=expandableListView.getChildAt(i).getMeasuredHeight()+height;
//                            height=expandableListView.getDividerHeight()+height;
//                        }
//                        expandableListView.getLayoutParams().height = (height+6)*10;

//                        groups.get(groupPosition).getChilds().size() - 1;
                      //  lluserInformation.setVisibility(View.VISIBLE);

//                        Toast.makeText(getApplicationContext(),
//                                list.get(groupPosition).getTblCareerDetails().get(0).getJobDetId() + " List Expanded.",
//                                Toast.LENGTH_SHORT).show();


                    }
                });

                expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                    @Override
                    public void onGroupCollapse(int groupPosition) {
//

//                        Toast.makeText(getApplicationContext(),
//                                list.get(groupPosition).getTblCareerDetails().get(0).getJobDetId() + " List Collapsed.",
//                                Toast.LENGTH_SHORT).show();

                    }
                });

                expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {

//                        setListViewHeight(expandableListView, groupPosition);
//                        Toast.makeText(getApplicationContext(), list.get(groupPosition) + " -> " + list.get(groupPosition).getTblCareerDetails().get(childPosition).getJobDescription(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                });


                expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int position, long id) {
//                        updateListViewHeight(parent);
//                        setListViewHeight(parent,position);
//                        setExpandableListViewHeight(parent, position);
                         return false;
                    }
                });

//                spinnerJson(respone);


            }

            @Override
            public void onFailure(Call<List<CareerResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(CareersActivity.this, "Fail" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    OnItemClickListner.OnClickCallback onClickCallback1 = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
//            progressBar.setVisibility(View.GONE);
            if(type.equalsIgnoreCase("selectfile")){
                Toast.makeText(CareersActivity.this, "Click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("application/pdf");
 //      intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//        intent.setType("image/*|application/pdf|audio/*");
               intent.setType("*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(intent, 0);
                }catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(CareersActivity.this, "Please install a File Manager.",
                            Toast.LENGTH_SHORT).show();
                }

            }
            else {

            }
        }
    };



    OnItemSendClickListner.OnClicksendCallback onClickCallback = new OnItemSendClickListner.OnClicksendCallback() {
        @Override
        public void onClicked(View view, int position, String type, String name,String jobposition,String message,String email,String phone,String file) {
            if (type.equalsIgnoreCase("send")) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
//                progressBar.setVisibility(View.GONE);
//                if (Validation()){
                    final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"daminijadhav1213@gmail.com"});
//                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, txtUriPath);
//                    emailIntent.putExtra(Intent.EXTRA_TEXT)
                    if (uriFromPath != null) {
                        emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        emailIntent.putExtra(Intent.EXTRA_STREAM, uriFromPath);
                    }
//                    String subject = edMessage.getText().toString();
//                    String s = edposition.getText().toString();
                    String text = "Applied for the position " + jobposition;
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, text);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, message);
//                    emailIntent.putExtra(Intent.EXTRA_STREAM, uriFromPath);


                    startActivity(Intent.createChooser(emailIntent, "Sending Email......"));


                }

                else {
//                Toast.makeText(CareersActivity.this, "Please fill all mandatory field", Toast.LENGTH_SHORT).show();
                }
            }
//        }
    };



    private void spinnerJson(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);

        if (jsonObject.optString("status").equals("true")){
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++){
               CareerResponse.TblCareerDetails tblCareerDetails=new CareerResponse.TblCareerDetails();
                JSONObject object=jsonArray.getJSONObject(i);

                tblCareerDetails.setPosition(object.getString("Position"));

              //  list.add(tblCareerDetails);
            }
            for (int i=0;i<list.size();i++){
                positinName.add(list.get(i).getTblCareerDetails().get(0).getPosition());
                ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,positinName);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }



        }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public boolean Validation(){

        Log.i("firstname", firstName);

     //   firstName=edfirstName.getText().toString();
        email=edemail.getText().toString();
//        phone=edphone.getText().toString();
        message=edMessage.getText().toString();
        position=edposition.getText().toString();
        selectfile=edfile.getText().toString();

        if (TextUtils.isEmpty(firstName))
        {
            edfirstName.setError(getResources().getString(R.string.enter_first_name));
            return false;
        }
        else if (!firstName.matches("[a-zA-Z ]+"))
        {
            edfirstName.setError(getResources().getString(R.string.enter_alphabet_character));
        }
        else {

        }


        if (TextUtils.isEmpty(position))
        {
            edposition.setError(getResources().getString(R.string.enter_position));
            return false;
        }
        else {

        }


        if (TextUtils.isEmpty(message))
        {
            edMessage.setError(getResources().getString(R.string.enter_message));
            return false;
        }
        else {

        }


        if (TextUtils.isEmpty(email))
        {
            edemail.setError(getResources().getString(R.string.enter_email));
            return false;
        }
        if (!email.matches(email_pattern))
        {
            edemail.setError(getResources().getString(R.string.enter_valid_email_id));
        }
        else {

        }

        if (TextUtils.isEmpty(phone)) {
            edphone.setError(getResources().getString(R.string.enter_phone_number));
            return false;
        } else if (!phone.matches(phone_no_pattern)) {
            edphone.setError(getResources().getString(R.string.enter_valid_phone_number));
            return false;
        } else {

        }



        if (TextUtils.isEmpty(selectfile))
        {
            edfile.setError(getResources().getString(R.string.please_select_pdf_file));
            return false;
        }
        else {

        }
        return true;
    }

    private void setListViewHeight(final ExpandableListView listView, final int groupPosition) {
//        final ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
//        int totalHeight = 0;
//        // be careful with unspecified width measure spec, it will ignore all layout params and even
//        // screen dimensions, so to get correct height, first get maximum width View can use and
//        // call measure() this way
//        final int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
//        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
//            final View groupItem = listAdapter.getGroupView(i, false, null, listView);
//            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//
//            totalHeight += groupItem.getMeasuredHeight();
//            // count only expanded other groups or the clicked collapsed one
//            if (((listView.isGroupExpanded(i)) && (i != groupPosition))
//                    || ((!listView.isGroupExpanded(i)) && (i == groupPosition))) {
//                for (int j = 0, childrenNumber = listAdapter.getChildrenCount(i); j < childrenNumber; j++) {
//
//                    final View listItem = listAdapter.getChildView(i, j, j == childrenNumber - 1, null, listView);
//                    listItem.measure(desiredWidth, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//                    totalHeight += listItem.getMeasuredHeight();
//                }
//            }
//        }
//        final ViewGroup.LayoutParams params = listView.getLayoutParams();
//        final int height = totalHeight
//                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
//        params.height = height;
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//

        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != groupPosition))
                    || ((!listView.isGroupExpanded(i)) && (i == groupPosition))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
                //Add Divider Height
                totalHeight += listView.getDividerHeight() * (listAdapter.getChildrenCount(i) - 1);
            }
        }
        //Add Divider Height
        totalHeight += listView.getDividerHeight() * (listAdapter.getGroupCount() - 1);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onBackPressed() {
//        final FragmentHelpandInfo fragment = (FragmentHelpandInfo) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
//        fragment.onBackPressed();
//        progressBar.setVisibility(View.GONE);
        super.onBackPressed();


//        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
//
//            getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new FragmentHelpandInfo()).commit();
//
//        }


//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
    }


}
