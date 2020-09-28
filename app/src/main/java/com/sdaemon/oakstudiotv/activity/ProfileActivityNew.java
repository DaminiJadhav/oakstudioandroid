package com.sdaemon.oakstudiotv.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.sdaemon.oakstudiotv.BuildConfig;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.PostProfileDTO;
import com.sdaemon.oakstudiotv.dto.ProfileDTO;
import com.sdaemon.oakstudiotv.fragment.FragmentAccountSetting;
import com.sdaemon.oakstudiotv.fragment.FragmentBalance_details;
import com.sdaemon.oakstudiotv.fragment.FragmentNotification;
import com.sdaemon.oakstudiotv.fragment.FragmentPayment_details;
import com.sdaemon.oakstudiotv.fragment.FragmentYourPlanAPI;
import com.sdaemon.oakstudiotv.model.Profile_categories;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivityNew extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ArrayList<Profile_categories> profile_categories = new ArrayList<>();
    private ImageView iv_tabback,ivphoto;
    TabLayout tabLayout;
    ViewPager viewPager;
    File file=null;

    CircularImageView circularImageView;
    ImageView circularImageView1;
    AppSession appSession;
    String  getprofile;
    Dialog dialog;
    String userFirstname,emailid,place,userid,fileurl="https://oakstudio.azurewebsites.net/Images/Home/",filename="IndexWithUser",fileext="jpg";
    TextView tvname,tvemailid,tvplace;
    String strProfileImage = "", strProfileImageExtension = "";
    String profilepicture;
    Uri uri;
    Utilities utilities;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialization();
    }

    private void initialization() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        ivphoto = (ImageView) findViewById(R.id.iv_photo);
        tvname = (TextView) findViewById(R.id.tv_name);
        tvemailid = (TextView) findViewById(R.id.tv_emailid);
        tvplace = (TextView) findViewById(R.id.tv_place);

        circularImageView1 = findViewById(R.id.circular_profile_iv);
        circularImageView1.setOnClickListener(this::onClick);
        appSession=AppSession.getInstance(this);

        try {
            if (appSession != null) {
                userFirstname = appSession.getUserDTO().getResult().getFirstName();
                emailid = appSession.getUserDTO().getResult().getCustEmail();
                place = appSession.getUserDTO().getResult().getCurrencyCode();
                userid = appSession.getUserDTO().getResult().getUniqueIdentifire();



                if (userFirstname==null){
                    tvname.setText(getResources().getString(R.string.profile_names));
                }else{
                    tvname.setText(userFirstname);
                }


                if (emailid==null){
                            tvemailid.setText(getResources().getString(R.string.profile_emailid));
                } else{
                    tvemailid.setText(emailid);
                }

                if (place == null) {
                    tvplace.setText(getResources().getString(R.string.profile_place));
                }else {
                        tvplace.setText(place);
                }

                Log.i("Profile Detail ","Name :"+userFirstname+" Emailid :"+emailid+" place :"+place);


//                Toast.makeText(ProfileActivityNew.this, "username " + userFirstname, Toast.LENGTH_SHORT).show();
//            Log.i("Profile name",""+userFirstname);

            }
        }catch (Exception e){
            e.printStackTrace();
        }


//        convertBase64();

//        getProfile();
//        postProfile();


//            if (appSession.getProfileImage()!=""){
        utilities= Utilities.getInstance(this);
        if (!utilities.isNetworkAvailable()) {
            getprofile = appSession.getProfileImage();
//
            Glide.with(this)
                    .load(getprofile)
                    .into(circularImageView1);
        }else {

            getProfile();
        }
//                Log.i("Profile image get",""+getprofile);
//
//            }else if (appSession.getProfileImage()==""){



        ivphoto.setOnClickListener(this::onClick);

        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.tab_viewpager);


        tabLayout.addTab(tabLayout.newTab().setText(""));
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.setIcon(R.mipmap.bell);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.account_setting)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.payment_detail)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.balance_detail)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.Your_Plan)));

//        if (viewPager != null) {
//            setupViewPager(viewPager);
//        }
//        tabLayout.setupWithViewPager(viewPager);
//        TabLayout.Tab tab = tabLayout.getTabAt(0);
//        tab.setIcon(R.mipmap.bell);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //   MyAdapter adapter = new MyAdapter(context, getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        ProfileActivityNew.MyAdapter adapter = new ProfileActivityNew.MyAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_photo:
//                Toast.makeText(ProfileActivityNew.this, "Click", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT>=23){
                    requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }
                AlertDialog();
                break;
            case R.id.circular_profile_iv:

                Log.i("getProfileImage",""+circularImageView1);
                loadPhoto(circularImageView1);

                break;
            default:
               break;
        }
    }


    public class MyAdapter extends FragmentPagerAdapter {
        private Context myContext;
        int totalTabs;

        public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);
            myContext = context;
            this.totalTabs = totalTabs;
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    FragmentNotification fragmentNotification = new FragmentNotification();
                    return fragmentNotification;

                case 1:
                    FragmentAccountSetting fragmentAccountSetting = new FragmentAccountSetting();
                    return fragmentAccountSetting;

                case 2:
                    FragmentPayment_details fragmentPayment_details = new FragmentPayment_details("");
                    return fragmentPayment_details;

                case 3:
                    FragmentBalance_details fragmentBalanceDetails = new FragmentBalance_details();
                    return fragmentBalanceDetails;

                case 4:
                    //  FragmentYourPlan()
                    FragmentYourPlanAPI fragmentYourPlanAPI = new FragmentYourPlanAPI();
                    return fragmentYourPlanAPI;


                default:
                    return null;
            }
        }// this counts total number of tabs

        @Override
        public int getCount() {
            return totalTabs;
        }
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
        menu.findItem(R.id.action_done).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }


    public void AlertDialog(){
//        CharSequence[] option={"Take photo","Gallery","Remove profile"};
//        CharSequence[] option={"Camera","Gallery","Remove profile"};
        CharSequence[] option={getResources().getString(R.string.camera),getResources().getString(R.string.gallery),getResources().getString(R.string.remove_profile)};


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.select_photo));
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (option[i].equals(getResources().getString(R.string.camera))){
                    String imagename=new SimpleDateFormat("yyyymmdd").format(new Date());
                    Log.i("Image Name",""+imagename);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//                    Log.i("ProfileBase64 filename ",""+f);

                    try {
                         file=File.createTempFile(imagename,".jpg", f);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                  uri = FileProvider.getUriForFile(ProfileActivityNew.this, BuildConfig.APPLICATION_ID + ".fileprovider",file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                    //                    intent.putExtra(MediaStore.EXTRA_OUTPUT,FileProvider.getUriForFile(ProfileActivityNew.this,getApplicationContext().getPackageName() +".provider",f));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent, 1);

                }else if (option[i].equals(getResources().getString(R.string.gallery))){
//                    pickFromGallery();
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }else if (option[i].equals(getResources().getString(R.string.remove_profile))){


                    appSession.setProfileImage("");
                    if (!utilities.isNetworkAvailable()) {
                        Toast.makeText(ProfileActivityNew.this, "Network unavailable.Please try again later", Toast.LENGTH_SHORT).show();

                    }else {

                        String defaultphoto = "https://oakstudio.azurewebsites.net/Images/SideMenu/profile.png";
                        file = new File(defaultphoto);

                        filename = "profile";

                        Log.i("ProfileBase64 filename1", "" + filename);


                        strProfileImage = readFileAsBase64DefaultString();

//                    strProfileImage=readFileAsBase64String(file.getAbsolutePath());
                        strProfileImageExtension = autoDetectMimeType(file.getAbsolutePath());


                        Log.i("ProfileBase64 Path ", "" + strProfileImage);
                        Log.i("ProfileBase64Extension", "" + strProfileImageExtension);

                        convertBase64ToBitmap(strProfileImage);


                        postProfile();
                    }
                    //encode image to base64 string



//

                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==1){
//                Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
//                circularImageView1.setImageBitmap(bitmap);

//                Uri selectedImage = data.getData();

//                file= new File(uri.getPath());


                if (!utilities.isNetworkAvailable()) {
                    Toast.makeText(ProfileActivityNew.this, "Network unavailable.Please try again later", Toast.LENGTH_SHORT).show();

                }else {

                    strProfileImage = readFileAsBase64String(file.getAbsolutePath());
                    strProfileImageExtension = autoDetectMimeType(file.getAbsolutePath());

                    convertBase64ToBitmap(strProfileImage);
                    filename = file.getName();

                    Log.i("ProfileBase64 filename ", "" + filename);

                    String[] arr = filename.split(".jpg", 0);
                    for (String w : arr) {
                        System.out.println(w);
                        Log.i("ProfileBase64 filename1", "" + w);
                        filename = w;

                    }


                    Log.i("ProfileBase64 Path ", "" + strProfileImage);
                    Log.i("ProfileBase64Extension", "" + strProfileImageExtension);

                    postProfile();

                }

//                appSession.setProfileImage(file.getAbsolutePath());

            }else if (requestCode == 2) {
                if (!utilities.isNetworkAvailable()) {
                    Toast.makeText(ProfileActivityNew.this, "Network unavailable.Please try again later", Toast.LENGTH_SHORT).show();

                }else {
                    Uri selectedImage = data.getData();

                    file = new File(selectedImage.getPath());

                    Log.i("ProfileBase64 filename1", "" + file.getName());
                    filename = file.getName();

                    String[] filePath = {MediaStore.Images.Media.DATA};

                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();

//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    Bitmap bitmap;
                    bitmap = BitmapFactory.decodeFile(picturePath);

//                String filename = (new File(picturePath)).getName();

                    Log.i("path of image", picturePath);
//                circularImageView1.setImageBitmap(bitmap);
                    Log.i("ProfileBase64 bitmap.. ", "" + bitmap);


                    strProfileImage = readFileAsBase64String(picturePath);
                    strProfileImageExtension = autoDetectMimeType(picturePath);

                    Log.i("ProfileBase64 Path ", "" + strProfileImage);
                    Log.i("ProfileBase64Extension", "" + strProfileImageExtension);


                    convertBase64ToBitmap(strProfileImage);

//                Log.i("ProfileBase64 filename ","");


                    postProfile();
                }



//                appSession.setProfileImage(picturePath);

            }
//            else if (requestCode==3){
//                Uri selectedImage = data.getData();
//                circularImageView.setImageURI(selectedImage);
//
//            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        Log.i("ProfileBase64 bitmap ",""+imageAsBytes);
        Log.i("ProfileBase64 bitmap 1",""+BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        circularImageView1.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }


    public String readFileAsBase64DefaultString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //decode base64 string to image
        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        Log.i("ProfileBase64 default" ," "+imageString);

        return imageString;
    }

    private void loadPhoto(ImageView imageView) {
        Log.i("ImageView","get " +imageView);

        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        imageDialog.setTitle("");

        View layout = inflater.inflate(R.layout.dialog_profile_photo,
                (ViewGroup) findViewById(R.id.ll_profilephoto));
        ImageView image = (ImageView) layout.findViewById(R.id.iv_setprofile);
//        TextView username = (TextView) layout.findViewById(R.id.tv_user_name);

        ImageView cancelImage=layout.findViewById(R.id.iv_cancel);
        image.setImageDrawable(imageView.getDrawable());
        Log.i("Imageview",""+imageView.getDrawable());
        imageDialog.setView(layout);
//        username.setText("Username"+userFirstname);

        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ProfileActivityNew.this,"Cancel",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
        }
        });


     /*   imageDialog.setPositiveButton("ok", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });*/


        dialog=imageDialog.create();
        dialog.show();

//        imageDialog.show();
    }


    public void getProfile(){

//        Call<ProfileDTO> profile=RetroClient.sdaemonApi().getProfile("35971c47-d1ab-45f9-b9a9-b96c4a8535c4");
        Call<ProfileDTO> profile=RetroClient.sdaemonApi().getProfile(userid);


        profile.enqueue(new Callback<ProfileDTO>() {
           @Override
           public void onResponse(Call<ProfileDTO> call, retrofit2.Response<ProfileDTO> response) {
               if (response.isSuccessful()){

//                Toast.makeText(ProfileActivityNew.this, "success"+response.message() ,Toast.LENGTH_SHORT).show();

                //               profilepicture=response.body().getProfilePhoto();
               Log.i("GetProfilepicture",""+response.body().getProfilePhoto());
               appSession.setProfileImage(response.body().getProfilePhoto());

                   Glide.with(ProfileActivityNew.this)
                           .load(response.body().getProfilePhoto())
                           .into(circularImageView1);
//
               }else {
//                    Toast.makeText(ProfileActivityNew.this, "Unsuccess "+response.message(), Toast.LENGTH_SHORT).show();
//                   Log.i("GetProfilepicture",""+response.body().getProfilePhoto());

               }

               Bitmap bitmap;
//               bitmap=BitmapFactory.decodeFile(profilepicture);
//               circularImageView1.setImageBitmap(bitmap);

       }

           @Override
           public void onFailure(Call<ProfileDTO> call, Throwable t) {
               Toast.makeText(ProfileActivityNew.this, "error "+t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
    }


    public void postProfile(){
        Log.i("ProfileBase64 filename2","  "+filename+" "+userid+" "+strProfileImageExtension);

        Call<ProfileDTO> call=RetroClient.sdaemonApi().postProfile(profileDTO());
       call.enqueue(new Callback<ProfileDTO>() {
           @Override
           public void onResponse(Call<ProfileDTO> call, Response<ProfileDTO> response) {

               if (response.isSuccessful()){
//                   Toast.makeText(ProfileActivityNew.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   Log.i("PostProfile success",""+response.body().getProfilePhoto());

               }else {
                   Log.i("PostProfile unsuccess",""+response.message());
//                   Toast.makeText(ProfileActivityNew.this, ""+response.message(), Toast.LENGTH_SHORT).show();
               }


           }

           @Override
           public void onFailure(Call<ProfileDTO> call, Throwable t) {
//               Toast.makeText(ProfileActivityNew.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
//
               Log.i("PostProfile error",""+t.getMessage());

           }
       });
    }

    PostProfileDTO profileDTO(){
        PostProfileDTO dto=new PostProfileDTO();
        try {

            dto.setUniqueIdentifier(userid);
            dto.setFile(strProfileImage);
            dto.setFilename(filename);
            dto.setFileext(strProfileImageExtension);

            Log.i(getClass().getName(), "=========== DTO dd :" + dto);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dto;
    }




    public String  readFileAsBase64String(String path){
        InputStream inputStream=null;
        try{
            inputStream=new FileInputStream(path);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        Base64OutputStream b64os=new Base64OutputStream(outputStream, Base64.DEFAULT);
        byte[] buffer=new byte[8179];
        int byteread;
        try{
            while ((byteread=inputStream.read(buffer))>-1){
                b64os.write(buffer,0,byteread);
            }
            return outputStream.toString();
        }catch (IOException e) {
            Log.i("PROFILE", "Cannot read file " + path, e);
            // Or throw if you prefer
            return "";
        }
        finally {
            closeQuietly(inputStream);
            closeQuietly(b64os); // This also closes baos
        }
    }


    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
        }
    }


    private String autoDetectMimeType(String image) {
            String extension=null;
            File file=new File(image);
            String absolutepath=file.getAbsolutePath();
//            int index=absolutepath.lastIndexOf(".")+1;
            int index=absolutepath.lastIndexOf(".");


        if (index>=0 && index<=absolutepath.length()){
                extension=absolutepath.substring(index);
                    Log.i("extension",""+extension);
            }

            if (extension != null || !extension.isEmpty()) {
                return extension;
            }

            String mimeType= MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
            if (mimeType==null){
                return "octet-stream";
            }
            return mimeType;
    }




















    public void convertBase64(){
        String imageUrl = "http://www.avajava.com/images/avajavalogo.jpg";
        String destinationFile = "image.jpg";

        try{
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();

            FileInputStream imageInFile = new FileInputStream(is.toString());
            byte imageData[] = new byte[2048];
            imageInFile.read(imageData);

            // Converting Image byte array into Base64 String
            String imageDataString = encodeImage(imageData);
            System.out.println("imageDataString : " + imageDataString);




            System.out.println("Image Successfully Manipulated!");
        }catch (Exception  e){
            System.out.println("Image Exception");

//            Log.d("Error", e.getMessage());
        }
    }

    public static String encodeImage(byte[] imageByteArray) {
        return android.util.Base64.encodeToString(imageByteArray, android.util.Base64.DEFAULT);
//        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }


    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,3);
    }

}
