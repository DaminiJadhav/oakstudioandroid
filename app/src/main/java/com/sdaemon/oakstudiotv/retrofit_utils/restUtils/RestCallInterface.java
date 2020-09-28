package com.sdaemon.oakstudiotv.retrofit_utils.restUtils;


import com.sdaemon.oakstudiotv.model.Category;
import com.sdaemon.oakstudiotv.model.GetFeaturesResp;
import com.sdaemon.oakstudiotv.model.GetGenreResp;
import com.sdaemon.oakstudiotv.model.GetMpaaRatingResp;
import com.sdaemon.oakstudiotv.model.GetReviewResp;
import com.sdaemon.oakstudiotv.model.GetStudioResp;
import com.sdaemon.oakstudiotv.model.GetYearResp;
import com.sdaemon.oakstudiotv.model.RetroPhoto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rahul Patil on 20-04-2018 .
 */
public interface RestCallInterface {

    @GET("MasterData/GetGenresList")
    Call<ArrayList<GetGenreResp>> getGenresList();

    @GET("MasterData/GetYearList")
    Call<ArrayList<GetYearResp>> getYearList();

    @GET("MasterData/GetReviewsList")
    Call<ArrayList<GetReviewResp>> GetReviewsList();

    @GET("MasterData/GetStudioList")
    Call<ArrayList<GetStudioResp>> GetStudioList();

    @GET("MasterData/GetFeaturesList")
    Call<ArrayList<GetFeaturesResp>> GetFeaturesList();

    @GET("MasterData/GetMPAARatingList")
    Call<ArrayList<GetMpaaRatingResp>> GetMPAARatingList();

//    @GET("Category/GetContent?GenresID=1&Year=0&ReviewsId=0&StudioId=0&FeaturesId=0&MPAARatingId=0&ContentTypeID=0&CategoryId=0")
//    Call<ArrayList<RetroPhoto>> getAllPhotos();




   // https://oakapi.sdaemon.co.in/api/Category/GetContent?GenresID=1&Year=5&ReviewsId=1&StudioId=1&FeaturesId=1&MPAARatingId=1&ContentTypeID=1&CategoryId=1

    @GET("Category/GetContent")
    Call<ArrayList<RetroPhoto>> getAllPhotos(@Query("GenresID") String GenresID,
                                             @Query("Year") String Year,
                                             @Query("ReviewsId") String ReviewsId,
                                             @Query("StudioId") String StudioId,
                                             @Query("FeaturesId") String FeaturesId,
                                             @Query("MPAARatingId") String MPAARatingId,
                                             @Query("ContentTypeID") String ContentTypeID ,
                                             @Query("CategoryId") String CategoryId );





    @GET("Category/GetCategory")
    Call<ArrayList<Category>>getAllCategories(@Query("ismovie") Boolean ismovie);



    @POST("NativeAccountMgmt/GetLoginDetails")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<RetroPhoto> test(
            @Field("UniqueId") String UniqueId);



    @POST("NativeSubscriptionPlanMaster/PaymentSuccess")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ArrayList<RetroPhoto>> transaction(
            @Field("SubscriptionID") String SubscriptionID,
            @Field("CustID") String CustID,
            @Field("PlanID") String PlanID,
            @Field("StatusID") String StatusID,
            @Field("CreateDate") String CreateDate,
            @Field("CreatedBy") String CreatedBy,
            @Field("LastModifiedDate") String LastModifiedDate,
            @Field("LastModifiedBy") String LastModifiedBy);

}