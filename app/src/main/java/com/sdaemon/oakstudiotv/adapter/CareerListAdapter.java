package com.sdaemon.oakstudiotv.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.CareerResponse;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.OnItemSendClickListner;
import com.sdaemon.oakstudiotv.utils.DateUtils;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.text.DateFormat;
import java.util.List;

public class CareerListAdapter  extends BaseExpandableListAdapter {
    int i=0,j=0;
    public  List<CareerResponse> careerResponses;
    public  List<String> childList;
    private OnItemSendClickListner.OnClicksendCallback onClickCallback;
    private OnItemClickListner.OnClickCallback onClickCallback1;
    Context context;
    boolean btnclick=false;
    LinearLayout llUserInformation;
    Button btnsend,btnselectfile;
    String firstName,jobposition,message,email,phone,selectfile,sktype,linkdinprofile;
    EditText edfirstName, edMessage,edposition,edfile,edemail,edphone,edsktype,edlinkdin;
    String candidate_position="";
    Uri uriFromPath;

    private static final String email_pattern = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static final String phone_no_pattern = "[789][0-9]{9}";




    public CareerListAdapter(Context context,List<CareerResponse> careerResponses,OnItemSendClickListner.OnClicksendCallback onClickCallback,OnItemClickListner.OnClickCallback onClickCallback1){
        this.context=context;
        this.careerResponses=careerResponses;
        this.onClickCallback=onClickCallback;
        this.onClickCallback1=onClickCallback1;
//        this.childList=childList;
    }



    @Override
    public int getGroupCount() {
        return careerResponses.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return careerResponses.get(groupPosition).getTblCareerDetails().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.careerResponses.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return careerResponses.get(groupPosition).getTblCareerDetails().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.row_career_items,parent,false);

        }
        TextView jobVacancy=convertView.findViewById(R.id.tv_jobvacancy);
//       if (groupPosition!=0){
//           jobVacancy.setText("View Job Detail");
//       }
//       if (groupPosition>=0) {

           jobVacancy.setText("Job Vaccancy :" + careerResponses.get(groupPosition).getTblCareerDetails().get(0).getJobDetId());
           candidate_position=careerResponses.get(groupPosition).getTblCareerDetails().get(0).getPosition();

//       }
        ImageView imageView=convertView.findViewById(R.id.iv_plus1);
        if (isExpanded){

            imageView.setImageResource(R.drawable.iv_minus);
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
//            jobVacancy.setTextColor(context.getResources().getColor(R.color.black_text));
            imageView.setSelected(isExpanded);

        } else {
            imageView.setImageResource(R.drawable.iv_plus);
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
//            jobVacancy.setTextColor(context.getResources().getColor(R.color.gray_text));
        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        TextView tvposition,tvexp,tvsalary,jobDescription,tvdate;
//      String careerDescription=careerResponses.get(groupPosition).getTblCareerDetails().get(groupPosition).getJobDescription();
//         CareerResponse getpos=careerResponses.get(groupPosition);
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.row_career_group,parent,false );
            i++;
            Log.i("validation", "getChildView: "+i);
        }
        tvposition=convertView.findViewById(R.id.tv_position);
        tvexp=convertView.findViewById(R.id.tv_jobexp);
        tvsalary=convertView.findViewById(R.id.tv_salary);
        tvdate=convertView.findViewById(R.id.tv_lastdate);
        jobDescription=convertView.findViewById(R.id.tv_jobDescription);


        tvposition.setText("Position        : "+careerResponses.get(groupPosition).getTblCareerDetails().get(0).getPosition());
        tvexp.setText("Experience   : "+careerResponses.get(groupPosition).getTblCareerDetails().get(0).getExperiance());
        tvsalary.setText("Salary            : "+careerResponses.get(groupPosition).getTblCareerDetails().get(0).getSalary());
        jobDescription.setText("Description   : " +careerResponses.get(groupPosition).getTblCareerDetails().get(0).getJobDescription());
        tvdate.setText("Last Date      : " + DateFormat.getDateInstance().format(DateUtils.getUTCToLocalDate(careerResponses.get(groupPosition).getLastDate())));



     btnsend = convertView.findViewById(R.id.btn_send);
     btnselectfile=convertView.findViewById(R.id.btnSelectImage);

        edfirstName=(EditText) convertView.findViewById(R.id.ed_firstname);
       // edposition=(EditText) convertView.findViewById(R.id.ed_position);
//        edposition=(EditText) convertView.findViewById(R.id.ed_position);
        edMessage=(EditText) convertView.findViewById(R.id.ed_message);
        edemail=(EditText) convertView.findViewById(R.id.ed_email);
        edphone=(EditText) convertView.findViewById(R.id.ed_phone);
        edfile=(EditText) convertView.findViewById(R.id.ed_file);
        edsktype=convertView.findViewById(R.id.ed_skype);
        edlinkdin=convertView.findViewById(R.id.ed_linkedinProfile);
        llUserInformation=convertView.findViewById(R.id.ll_mainuserInformation);



        btnselectfile.setOnClickListener(new OnItemClickListner(groupPosition, onClickCallback1, "selectfile"));
//        edfile.setText(CareersActivity.resume_detail);

        if (validation1()) {

//
//            btnsend.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    btnclick = true;
////                if (Validation()) {
//                    Activity origin = (Activity) context;
//                    final Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                    emailIntent.setType("plain/text");
//                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"daminijadhav1213@gmail.com"});
////                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, txtUriPath);
////                    emailIntent.putExtra(Intent.EXTRA_TEXT)
//                    if (uriFromPath != null) {
//                        emailIntent.putExtra(Intent.EXTRA_STREAM, CareersActivity.uriFromPath);
//                    }
////                    String subject = edMessage.getText().toString();
////                    String s = edposition.getText().toString();
//                    String text = "Applied for the position " + jobposition;
//                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, text);
//                    emailIntent.putExtra(Intent.EXTRA_TEXT, message);
//                    emailIntent.putExtra(Intent.EXTRA_STREAM, CareersActivity.uriFromPath);
//
//                    origin.startActivity(Intent.createChooser(emailIntent, "Sending Email......"));
//
//                }
////            }
//            });




//        if (Validation()){
//            btnselectfile.setOnClickListener(new OnItemClickListner(groupPosition,onClickCallback1,"selectfile"));

//        if (onClickCallback.equals(btnsend.callOnClick())) {
//            if (btnsend.isClickable()){
//            if (Validation()){

            btnsend.setOnClickListener(new OnItemSendClickListner(groupPosition, onClickCallback, "send", firstName, jobposition, message, email, phone, selectfile));

//            edfirstName.setText("");
//            edMessage.setText("");
//            edphone.setText("");
////            edfile.setText("");
//            edemail.setText("");
//            edsktype.setText("");
//            edlinkdin.setText("");
//        }
        }


//        edfirstName.addTextChangedListener(new TextWatcher() {
//            private static final int PIN_LENGTH = 2;
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if(edfirstName.getText().toString().trim().length()>=1){
//                    edemail.requestFocus();
//                }
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//               if (s.length()==PIN_LENGTH){
//
//               }
//            }
//        });

//        edphone.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start,int before, int count)
//            {
//                // TODO Auto-generated method stub
//                if(edphone.getText().toString().length()==4)     //size as per your requirement
//                {
//                    edMessage.requestFocus();
//                }
//            }
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//                // TODO Auto-generated method stub
//
//            }
//
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//            }
//
//        });

//        edphone.setActivated(true);
//        edMessage.setActivated(true);


//        edemail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if(edemail.hasFocus()){
//
//                    //et1.setCursorVisible(true);
//                    edMessage.setActivated(true);
//                    edMessage.setPressed(true);
//
//                }
//            }
//        });



//       llUserInformation.setOnTouchListener(new View.OnTouchListener() {
//           @Override
//           public boolean onTouch(View v, MotionEvent event) {

//               if(edemail.isFocused()){
//                   if(event.getY() >= 72){
//                       //Will only enter this if the EditText already has focus
//                       //And if a touch event happens outside of the EditText
//                       //Which in my case is at the top of my layout
//                       //and 72 pixels long
//                       edemail.clearFocus();
//                       InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                       imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                   }
//               }
//               Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

//               if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                   if (edfirstName.isFocused()  && edphone.isFocused()  && edemail.isFocused()) {
//                       Rect outRect = new Rect();
//                       edfirstName.getGlobalVisibleRect(outRect);
//                       edphone.getGlobalVisibleRect(outRect);
//                       edMessage.getGlobalVisibleRect(outRect);
//                       edemail.getGlobalVisibleRect(outRect);
//                       edfile.getGlobalVisibleRect(outRect);
//                       edsktype.getGlobalVisibleRect(outRect);
//                       edlinkdin.getGlobalVisibleRect(outRect);
//
//
//                       if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
//                           edfirstName.clearFocus();
//                           edphone.clearFocus();
//                           edMessage.clearFocus();
//                           edemail.clearFocus();
//                           edfile.clearFocus();
//                           edsktype.clearFocus();
//                           edlinkdin.clearFocus();
//
//
//
//                           InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                           imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                       }
//                   }
//               }
//               return false;
//           }
//       });

//        jobDescription.setText("" +careerDescription);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public boolean Validation(){
         j++;
        Log.i("val", "Van: "+j);
        firstName = edfirstName.getText().toString().trim();
      //  jobposition = edposition.getText().toString().trim();
//        edposition.setText(candidate_position);
        jobposition = candidate_position;
        message = edMessage.getText().toString().trim();
        email = edemail.getText().toString().trim();
        phone = edphone.getText().toString().trim();
        selectfile = edfile.getText().toString().trim();


        if (TextUtils.isEmpty(firstName)){
            edfirstName.setError(context.getResources().getString(R.string.enter_first_last_name));
            return false;
        }


         if (TextUtils.isEmpty(message))
        {
            edMessage.setError(context.getResources().getString(R.string.enter_message));
            return false;
        }

        if (TextUtils.isEmpty(email))
        {
            edemail.setError(context.getResources().getString(R.string.enter_email));
            return false;
        }
        if (!email.matches(email_pattern))
        {
            edemail.setError(context.getResources().getString(R.string.enter_valid_email_id));
        }

        if (TextUtils.isEmpty(phone)) {
            edphone.setError(context.getResources().getString(R.string.enter_phone_number));
            return false;
        }
        else if (!phone.matches(phone_no_pattern)) {
            edphone.setError(context.getResources().getString(R.string.enter_valid_phone_number));

        }
//        if (TextUtils.isEmpty(selectfile)){
//            edfile.setError("Please select pdf or doc file");
//            return false;
//        }





//        if ((CareersActivity.resume_detail)=="")
//        {
//            edfile.setError("Please select pdf or doc file");
//            return false;
//        }
//        else {
//            edfile.setText(CareersActivity.resume_detail);
//                   }

        return true;
    }

    public boolean validation1(){
        firstName = edfirstName.getText().toString();
        jobposition = candidate_position;
        message = edMessage.getText().toString();
        email = edemail.getText().toString();
        phone = edphone.getText().toString();
        selectfile = edfile.getText().toString();

//        if (TextUtils.isEmpty(firstName)  &&  TextUtils.isEmpty(lastName)  && TextUtils.isEmpty(email) && TextUtils.isEmpty(message) && TextUtils.isEmpty(phone)){
//            Toast.makeText(this, "Field Cannot Be Empty", Toast.LENGTH_SHORT).show();
//            return false;
//
//        }

        if (TextUtils.isEmpty(firstName)){
            edfirstName.setError(context.getResources().getString(R.string.field_cannot_be_empty));
            return false;
        }
        else if (TextUtils.isEmpty(phone)){
            edphone.setError(context.getResources().getString(R.string.field_cannot_be_empty));
            return false;
        }
        else  if (!phone.matches(phone_no_pattern)){
            edphone.setError(context.getResources().getString(R.string.enter_valid_phone_number));
            return false;
        }
        else if (TextUtils.isEmpty(message)){
            edMessage.setError(context.getResources().getString(R.string.field_cannot_be_empty));
            return false;
        }
        else if (TextUtils.isEmpty(email)){
            edemail.setError(context.getResources().getString(R.string.field_cannot_be_empty));
            return false;
        }
        else if (!email.matches(email_pattern)){
            edemail.setError(context.getResources().getString(R.string.enter_valid_email_id));
            return false;

        }


        return true;
    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
   //    https://stackoverflow.com/questions/48516189/how-to-set-submenu-in-expandablelistview-android-using-retrofit-2
}
