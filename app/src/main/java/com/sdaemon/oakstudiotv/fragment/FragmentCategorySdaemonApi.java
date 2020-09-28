package com.sdaemon.oakstudiotv.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.MovieDetailsActivity;
import com.sdaemon.oakstudiotv.adapter.MovieAdapterSdaemon;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.CategoryDTO;
import com.sdaemon.oakstudiotv.dto.CategoryFilterDTO;
import com.sdaemon.oakstudiotv.dto.JobDTO;
import com.sdaemon.oakstudiotv.dto.ResponseDTO;
import com.sdaemon.oakstudiotv.dto.ResultDTO;
import com.sdaemon.oakstudiotv.model.ContentInfo;
import com.sdaemon.oakstudiotv.model.ContentInfoFilter;
import com.sdaemon.oakstudiotv.model.RetroPhoto;

import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategorySdaemonApi extends Fragment implements AppConstants {

    StaggeredGridLayoutManager mLayoutManager;
    GridLayoutManager gridLayoutManager;
    public View rootView;
    public MovieAdapterSdaemon categoryAdapter;
 //   private MovieAdapter categoryAdapter;
 public Retrofit retrofit;
    public Context context;
    int position;
    String Cat="0";
    String GenresID="0",  YearID="2019",  ReviewsID="0",  StudiosID="0", FeaturesID="0",  MPaRatindID="0", ContentTypeID="0",  CategoryID;
    public ProgressDialog progressDialog;
    public  RecyclerView rv;
    public ArrayList<RetroPhoto> trailersByCategory = new ArrayList<>();
    public List<Object> mRecyclerViewItems = new ArrayList<>();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    AppSession appSession;
    public ResponseDTO responseDTO;
   ContentInfo contentDetails;
    private List<ContentInfoFilter> getcontentInfos = new ArrayList<>();
//    private ArrayList<ContentInfo> getcontentInfos = new ArrayList<>();

    int userid;
    String uniqueId;
    int MovieSeason;

    public FragmentCategorySdaemonApi() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public FragmentCategorySdaemonApi(String CategoryID,int MovieSeason)
    {
        this.MovieSeason=MovieSeason;
       this.CategoryID=CategoryID;
    }

       public static FragmentCategorySdaemonApi newInstance(int sectionNumber,String CategoryID) {
        FragmentCategorySdaemonApi fragment = new FragmentCategorySdaemonApi();
        Bundle args = new Bundle();
          String Cat=CategoryID;
          args.putString("v",Cat);
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fragment_category, container, false);
        if (getArguments()!=null){
            sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            CategoryID=getArguments().getString("v");
        }

        Intent intent=getActivity().getIntent();
        appSession = AppSession.getInstance(context);
        setData();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getActivity(), "resume", Toast.LENGTH_SHORT).show();
    }

    public void setData() {

        rv = (RecyclerView) rootView.findViewById(R.id.recycler_categoryTrailers);

//        gridLayoutManager=new GridLayoutManager(context,2);
//        rv.setLayoutManager(gridLayoutManager);
//        rv.setItemAnimator(new DefaultItemAnimator());


        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

/*
        trailersByCategory.add(new MovieDetails(R.drawable.view, "EXPANDABLE 2", "(2016)", 3, 123));
        trailersByCategory.add(new MovieDetails(R.drawable.view1, "ANT-MAN", "(2016)", 4, 1021));
        trailersByCategory.add(new MovieDetails(R.drawable.view2, "JUSTICE LEAGUE", "(2016)", 5, 500));
        trailersByCategory.add(new MovieDetails(R.drawable.view3, "BLACK PANTHER", "(2016)", 3, 123));
        trailersByCategory.add(new MovieDetails(R.drawable.view4, "MISSION IMPOSSIBLE", "(2016)", 5, 854));*/

      // getCategory("0","0","0","0","0","0","0","0");
      // GenresID= String.valueOf(GenreActivity.gentre_position);

        if (MovieSeason==1) {

            GenresID = String.valueOf(appSession.getGenreIDposition());
            Log.i("=========G", GenresID);
            YearID = String.valueOf(appSession.getYearID());
            Log.i("=========Y", YearID);
            ReviewsID = String.valueOf(appSession.getReviewIDposition());
            Log.i("Review ID 1", "" + ReviewsID);
            StudiosID = String.valueOf(appSession.getStudioIDposition());
            Log.i("Studio ID 1", "" + StudiosID);
            FeaturesID = String.valueOf(appSession.getFeatureIDposition());
            Log.i("Feature ID 1", "" + FeaturesID);
            MPaRatindID = String.valueOf(appSession.getRatingIDposition());
            Log.i("Rating ID 1", "" + MPaRatindID);

            ContentTypeID = String.valueOf(0);
            Log.i("Category Parameters", "" + CategoryID + "" + GenresID + "" + YearID + "" + ReviewsID + "" + StudiosID + "" + FeaturesID + "" + MPaRatindID + "" + ContentTypeID);
            getAllCategoryFilterData(GenresID, YearID, ReviewsID, StudiosID, FeaturesID, MPaRatindID, ContentTypeID, CategoryID, MovieSeason);
//        getAllCategory();
//            getCategory(GenresID,YearID,ReviewsID,StudiosID,FeaturesID,MPaRatindID,ContentTypeID,CategoryID);

        }else if (MovieSeason==2){
            GenresID = String.valueOf(appSession.getTVGenreIDposition());
            Log.i("=========G", GenresID);
            YearID = String.valueOf(appSession.getTVshowYearID());
            Log.i("=========Y", YearID);
            ReviewsID = String.valueOf(appSession.getTVReviewIDposition());
            Log.i("Review ID 1", "" + ReviewsID);
            StudiosID = String.valueOf(appSession.getTVStudioIDposition());
            Log.i("Studio ID 1", "" + StudiosID);
            FeaturesID = String.valueOf(appSession.getTVFeatureIDposition());
            Log.i("Feature ID 1", "" + FeaturesID);
            MPaRatindID = String.valueOf(appSession.getTVRatingIDposition());
            Log.i("Rating ID 1", "" + MPaRatindID);

            ContentTypeID = String.valueOf(0);
            Log.i("Category Parameters", "" + CategoryID + "" + GenresID + "" + YearID + "" + ReviewsID + "" + StudiosID + "" + FeaturesID + "" + MPaRatindID + "" + ContentTypeID);
            getAllCategoryFilterData(GenresID, YearID, ReviewsID, StudiosID, FeaturesID, MPaRatindID, ContentTypeID, CategoryID, MovieSeason);
        }
    }

    OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if(type.equalsIgnoreCase("image")){
               //Toast.makeText(context,"Image Click",Toast.LENGTH_LONG).show();
                /*if (getArguments() != null) {
                    String mParam1 = getArguments().getString("params");
                    Toast.makeText(getActivity(),mParam1,Toast.LENGTH_LONG).show();
                }*/
                Bundle bundle=new Bundle();
                bundle.putInt("KEY_CONTENTIDS",getcontentInfos.get(position).getContentID());
                appSession.setContentID(getcontentInfos.get(position).getContentID());
                Log.i("Content Id",""+getcontentInfos.get(position).getContentID());


                Intent intent=new Intent(context, MovieDetailsActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);


//             context.startActivity(new Intent(context, MovieDetailsActivity.class));

            //  context.startActivity(new Intent(context, ButtonActivity.class));

            }
            else {

            }
        }
    };

   // public  void getCategory(String GenresID,String YearID,String ReviewsID,String StudiosID,String FeaturesID,String MPaRatindID,String ContentTypeID,String CategoryID){
//    public  void getCategory(String GenresID,String YearID,String ReviewsID,String StudiosID,String FeaturesID,String MPaRatindID,String ContentTypeID,String CategoryID){
//  //  private void getCategory() {
//        // progressBar.show();
//       Call<ArrayList<CategoryDTO>> call =  RetroClient.sdaemonApi().getCategory(GenresID,YearID,ReviewsID,StudiosID,FeaturesID,MPaRatindID,ContentTypeID,CategoryID);
//      // Call<ArrayList<CategoryDTO>> call =  RetroClient.sdaemonApi().getCategory();
//
//        Log.e(getClass().getName(), "===" + call.request().url());
//        call.enqueue(new Callback<ArrayList<CategoryDTO>>() {
//            @Override
//            public void onResponse(Call<ArrayList<CategoryDTO>> call, Response<ArrayList<CategoryDTO>> response) {
//               // Toast.makeText(context, "login successfully", Toast.LENGTH_SHORT).show();
//                if (response.isSuccessful()) {
//          //   jobList(response.body().toString());
//
//
//                    Log.i(getClass().getName(), "=========RESPONSE: " + response.body());
//                    ArrayList<CategoryDTO> list = new ArrayList<>();
//                    list.addAll(response.body());
//                    categoryAdapter =new MovieAdapterSdaemon(getActivity(), list,onClickCallback);
//
//                    rv.setAdapter(categoryAdapter);
//               }
//                else if (response.code() == 409) {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Log.i(getClass().getName(),"=========RESPONSE: 409 ");
//                        String msg = jObjError.getString("message");
//                        showDialoge(context, "", msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Log.i(getClass().getName(),"=========RESPONSE: else ");
//                        String msg = jObjError.getString("message");
//                        showDialoge(context, "", msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<CategoryDTO>> call, Throwable t) {
//                //    progressBar.dismiss();
//
//                Log.i(getClass().getName(),"--------------- onFailure "+ t.getMessage());
//                showDialoge(context, "", "" + t.getMessage());
//            }
//        });
//    }


    public void getAllCategoryFilterData(String GenresID,String YearID,String ReviewsID,String StudiosID,String FeaturesID,String MPaRatindID,String ContentTypeID,String CategoryID,int MovieSeason){
        try {
            if (appSession!=null) {
                userid = appSession.getUserDTO().getResult().getCustomerId();
                uniqueId = appSession.getUserDTO().getResult().getUniqueIdentifire();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.i("MovieSeason "," "+MovieSeason);
//        Call<CategoryFilterDTO> call= RetroClient.sdaemonApi().getContentFilterData(13335,"25250892-17ab-47d8-b809-a6494f69a342","0","0","","0","0","0","0","0",50,0);
        Call<CategoryFilterDTO> call= RetroClient.sdaemonApi().getContentFilterData(userid,uniqueId,CategoryID,GenresID,YearID,ReviewsID,StudiosID,FeaturesID,MPaRatindID,ContentTypeID,60,0,MovieSeason);

        Log.e(getClass().getName(), "===" + call.request().url());

        call.enqueue(new Callback<CategoryFilterDTO>() {
            @Override
            public void onResponse(Call<CategoryFilterDTO> call, Response<CategoryFilterDTO> response) {
                if (response.isSuccessful()){
                    Log.i(getClass().getName(), "=========RESPONSE: " + response.body());

                    CategoryFilterDTO  filterDTO=response.body();
                    List<ContentInfoFilter> contentInfoFilters=filterDTO.getContentDetail().getContentInfoFilters();
                    getcontentInfos.clear();
                    getcontentInfos.addAll(contentInfoFilters);

                    categoryAdapter =new MovieAdapterSdaemon(getActivity(), getcontentInfos,onClickCallback);


                    rv.setAdapter(categoryAdapter);
                    Log.i("Show Category Filter",""+contentInfoFilters);
//                    Toast.makeText(context, "Success"  +filterDTO.getContentDetail().getContentInfoFilters().get(0).getContentTitle() , Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i("Filter Unsuccess",""+response.message());

//                    Toast.makeText(context, "Unsuccess"+response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CategoryFilterDTO> call, Throwable t) {
                Log.i("Error Category Filter",""+t.getMessage());

                Toast.makeText(context, "Error : " +t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


//    private void getAllCategory() {
//        userid=appSession.getUserDTO().getResult().getCustomerId();
//        Call<ContentDTO> call= RetroClient.sdaemonApi().getAllContent(userid,"",0,5);
//        Log.e(getClass().getName(), "===" + call.request().url());
//        call.enqueue(new Callback<ContentDTO>() {
//            @Override
//            public void onResponse(Call<ContentDTO> call, Response<ContentDTO> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//                    Log.i(getClass().getName(), "=========RESPONSE: " + response.body());
////                    ArrayList<ContentInfo> list = new ArrayList<>();
////                    list.addAll(response.body().getContentInfos());
//
//
//                    ContentDTO contentDTO=response.body();
//                    contentDetails = (ContentInfo) contentDTO.getContentDetail().getContentInfos();
//                    getcontentInfos = (ArrayList<ContentInfo>) response.body().getContentDetail().getContentInfos();
////                    list.addAll(getcontentInfos);
//
//                    categoryAdapter =new MovieAdapterSdaemon(getActivity(), getcontentInfos,onClickCallback);
//                    rv.setAdapter(categoryAdapter);
//                }
//                else if (response.code() == 409) {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Log.i(getClass().getName(),"=========RESPONSE: 409 ");
//                        String msg = jObjError.getString("message");
//                        showDialoge(context, "", msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Log.i(getClass().getName(),"=========RESPONSE: else ");
//                        String msg = jObjError.getString("message");
//                        showDialoge(context, "", msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ContentDTO> call, Throwable t) {
//                Toast.makeText(context, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }



    public void showDialoge(Context context, String title, String msg) {
        try {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context, R.style.dialoge);
            builder.setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })

                    .show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public ResponseDTO parseList(String response) {
        responseDTO = new ResponseDTO();
        ResultDTO resultDTO = null;
        List<JobDTO> list = null;

        try {
            Object object = new JSONTokener(response).nextValue();
            if (object instanceof JSONObject) {
                JSONObject resultObj = getResultJson(response);
                if (resultObj != null) {
                    resultDTO = new ResultDTO();
                    Gson gson = new Gson();
                    if (resultObj.optJSONArray("result") != null) {
                      //  list = gson.fromJson(resultObj.optJSONArray("result").toString(), new TypeToken<ArrayList<JobDTO>>() {
                        list = gson.fromJson(resultObj.optJSONArray("result").toString(), new TypeToken<ArrayList<CategoryDTO>>() {
                        }.getType());
                    }
                    resultDTO.setList1(list);

                }
                responseDTO.setResultDTO(resultDTO);


            } else {
                responseDTO.setStatusCode(0);
                responseDTO.setMessage(UNEXPECTED_RESPONSE + "\nResponse : "
                        + response);
                return responseDTO;
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setStatusCode(0);
            responseDTO.setMessage(PARSING_ERROR + "\n" + e.getMessage()
                    + "\nResponse : " + response);
            return responseDTO;
        }

        return responseDTO;
    }


    private JSONObject getResultJson(String response) {
        try {
            Object object = new JSONTokener(response).nextValue();
            if (object instanceof JSONObject) {
                final JSONObject mJSONObject = new JSONObject(response);
                responseDTO.setMessage(mJSONObject.optString("message"));
                responseDTO.setStatusCode(mJSONObject.optInt("status"));
                responseDTO.setProfileImage(mJSONObject.optString("profile_image"));
                responseDTO.setId(mJSONObject.optInt("id"));
                responseDTO.setTime(mJSONObject.optString("time"));
//                if (!TextUtils.isEmpty("" + mJSONObject.optInt("status")) && (mJSONObject.optInt("status") == 4 || mJSONObject.optInt("status") == 3)) {
//                    checkForStatus(mJSONObject.optString("message"));
//                }

                return mJSONObject;
            } else {
                responseDTO.setStatusCode(0);
                responseDTO.setMessage(UNEXPECTED_RESPONSE + "\nResponse : "
                        + response);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setStatusCode(0);
            responseDTO.setMessage(PARSING_ERROR + "\n" + e.getMessage()
                    + "\nResponse : " + response);
            return null;
        }
    }


    public ResponseDTO jobList(String res) {
     String response = "";
        try {
          response = res;

        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setStatusCode(0);
            responseDTO.setMessage(EXCEPTION + "\n" + e.getMessage());
            return responseDTO;
        }
        if (response == null || response.equals(""))
            return null;
        return parseList(response);
    }



}


