package com.sdaemon.oakstudiotv.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sdaemon.oakstudiotv.CareerResponse;
import com.sdaemon.oakstudiotv.dto.AccountSettingDTO;
import com.sdaemon.oakstudiotv.dto.BaseUrlDTO;
import com.sdaemon.oakstudiotv.dto.BlogDetailDTO;
import com.sdaemon.oakstudiotv.dto.BlogDto;
import com.sdaemon.oakstudiotv.dto.CategoryDTO;
import com.sdaemon.oakstudiotv.dto.CategoryFilterDTO;
import com.sdaemon.oakstudiotv.dto.ContactDTO;
import com.sdaemon.oakstudiotv.dto.ContentDTO;
import com.sdaemon.oakstudiotv.dto.ContentViewHistoryDTO;
import com.sdaemon.oakstudiotv.dto.ContentViewTimeDTO;
import com.sdaemon.oakstudiotv.dto.DeleteContentViewHistoryDTO;
import com.sdaemon.oakstudiotv.dto.EpisodeDTO;
import com.sdaemon.oakstudiotv.dto.NotificationDTO;
import com.sdaemon.oakstudiotv.dto.PlanDescriptionDTO;
import com.sdaemon.oakstudiotv.dto.PlanFinalDTO;
import com.sdaemon.oakstudiotv.dto.PlanMasterDTO;
import com.sdaemon.oakstudiotv.dto.PlanNewDTO;
import com.sdaemon.oakstudiotv.dto.PlantestDTO;
import com.sdaemon.oakstudiotv.dto.PostProfileDTO;
import com.sdaemon.oakstudiotv.dto.ProfileDTO;
import com.sdaemon.oakstudiotv.dto.PromoCodeDTO;
import com.sdaemon.oakstudiotv.dto.RecentelyAddedDTO;
import com.sdaemon.oakstudiotv.dto.RecentlyWatchDTO;
import com.sdaemon.oakstudiotv.dto.SeasonDTO;
import com.sdaemon.oakstudiotv.dto.SignInPageSliderDTO;
import com.sdaemon.oakstudiotv.dto.SupportDTO;
import com.sdaemon.oakstudiotv.dto.TransactionDTO;
import com.sdaemon.oakstudiotv.dto.UserDTO;
import com.sdaemon.oakstudiotv.dto.UserStatusDTO;
import com.sdaemon.oakstudiotv.dto.UsersDTO;
import com.sdaemon.oakstudiotv.dto.WishListDTO;
import com.sdaemon.oakstudiotv.model.AboutUsResponse;
import com.sdaemon.oakstudiotv.model.BrainTreePostResponse;
import com.sdaemon.oakstudiotv.model.BrainTreeResponse;
import com.sdaemon.oakstudiotv.model.CompanyProfileResponse;
import com.sdaemon.oakstudiotv.model.CountryListResponse;
import com.sdaemon.oakstudiotv.model.Payment;
import com.sdaemon.oakstudiotv.model.PostContentReviews;
import com.sdaemon.oakstudiotv.model.PutContentReview;


import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by ${Deven} on 9/14/18.
 */
public interface SdaemonApi {
    //   public static final String BASEPATH = "http://192.168.1.47:9999";
    //  public static final String BASEPATH = "http://oakapi.sdaemon.co.in/api/";

    //  public static final String BASEPATH = "http://192.168.1.15:80/api/";
//      public static final String BASEPATH = "https://oakstudio.azurewebsites.net/services/api/";
    //          public static final String BASEPATH="http://www.kalpesh.com/OakServices/api/";
//    public static final String BASEPATH="http://192.168.0.119/OakServices/api/";


//    public static final String BASEPATH="http://192.168.0.118/OakStudio_Services/api/";
    public static final String BASEPATH="https://oakstudio.azurewebsites.net/Services/api/";

//    public static final String BASEPATH_BRAINTREE="http://192.168.0.118/OakStudio_Services/api/";

    public static final String BASEPATH_BRAINTREE="https://oakstudio.azurewebsites.net/Services/api/";


//      public static final String BASEPATH = "https://api.jsonbin.io/b/";


    @GET("baseUrl")
    Call<BaseUrlDTO> getBaseUrl(@Query("app_version") String app_version,
                                @Query("device_type") String device_type);

    @POST("user/verification")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<UserDTO> verification(
            @Field("email") String emial,
            @Field("country_code") String countryCode,
            @Field("mobile_number") String mobileNumber,
            @Field("user_type") String userType);


    @POST("NativeSubscriptionPlanMaster/PaymentSuccess")
//    @FormUrlEncoded
//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/x-www-form-urlencoded"})
    Call<PlanFinalDTO> transaction(
            @Query("SubscriptionID") String SubscriptionID,
            @Query("CustID") String CustID,
            @Query("PlanID") String PlanID,
            @Query("StatusID") String StatusID,
            @Query("CreateDate") String CreateDate,
            @Query("CreatedBy") String CreatedBy,
            @Query("LastModifiedDate") String LastModifiedDate,
            @Query("LastModifiedBy") String LastModifiedBy);


    @POST("NativeSubscriptionPlanMaster/PaymentSuccess")
//    @FormUrlEncoded
//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/x-www-form-urlencoded"})
    Call<TransactionDTO> transactionObj(
            @Body TransactionDTO dto);

    @POST("NativeSubscriptionPlanMaster/PaymentSuccess")
//    @FormUrlEncoded
//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/x-www-form-urlencoded"})
    Call<Payment> transactionObj(
            @Body Payment dto);


    @POST("user/registration")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<UserDTO> Registration(
            @Field("name") String fullName,
            @Field("email") String Email,
            @Field("mobile_number") String mobile_number,
            @Field("country_code") String country_code,
            @Field("password") String password,
            @Field("user_type") String user_type,
            @Field("device_type") String device_type,
            @Field("app_version") String app_version,
            @Field("notification_key") String notification_key,
            @Field("device_key") String device_key,
            @Field("verification_code") String verification_code);

/*
    @POST("user/login")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<UserDTO> login(
            @Field("email") String Email,
            @Field("password") String password,
            @Field("device_type") String device_type,
            @Field("device_key") String deviceKey,
            @Field("notification_key") String notificationKey,
            @Field("app_version") String appVersion);
    */

//    @POST("user/login")
//    @FormUrlEncoded
//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/x-www-form-urlencoded"})
//    Call<UserDTO> login(
//            @Field("email") String Email,
//            @Field("password") String password);

//
//     @POST("NativeAccountMgmt/GetLoginDetails")
//      @FormUrlEncoded
//      @Headers({
//              "Accept: application/json",
//              "Content-Type: application/x-www-form-urlencoded"})
//      Call<UserDTO> login(
//            @Field("UniqueId") String UniqueId);
//UniqueId

    @Headers({"application-id: MY-APPLICATION-ID",
            "secret-key: MY-SECRET-KEY",
            "application-type: REST"})
    @GET("NativeAccountMgmt/GetLoginDetails")
    Call<UsersDTO> login(@Query("UniqueId") String UniqueId);


    @GET("NativeAccountMgmt/GetUserStatusAndOtherDetails")
    Call<UserStatusDTO> loginStatus(@Query("UniqueId") String UniqueId);



  @GET("SubPlanMasters/GetCheckuserForFreePlans")
    Call<JsonArray> planStatus(@Query("UserProfileId") String UserProfileId);


    @GET("NativeSubscriptionPlanMaster/GetPlanDetails")
    Call<PlanDescriptionDTO> getPlanDescription(@Query("CorePlanId") String CorePlanId);


    @GET("NativeSubscriptionPlanMaster/SubPlanDetails")
    Call<PlanNewDTO> getPlanNewDesc();


    @GET("NativeSubscriptionPlanMaster/GetAllSubPlanDetails")
    Call<PlanFinalDTO> getPlanFinalDesc();


    @GET("NativeSubscriptionPlanMaster/GetValidateCoupan")
    Call<PromoCodeDTO> getCoupanCode(@Query("UniqueId") String UniqueId,
                                     @Query("CoupanCode") String CoupanCode,
                                     @Query("CorePlanId") String CorePlanId,
                                     @Query("PeriodId") String PeriodId);


    @GET("5cc92df3451a1f7c58eb965c")
    Call<PlanFinalDTO> testrr(
            @Header("secret-key") String secretkey);



    @GET("NativeContent/ContentListByUser")
    Call<ContentDTO> getSearchContent(@Query("userId") Integer userId,
                                  @Query("SearchString") String SearchString,
                                  @Query("LastId") Integer LastId,
                                  @Query("count") Integer count);


    @GET("NativeContent/HomePageSlider")
    Call<CategoryFilterDTO> getHomePageContent(@Query("userId") Integer userId,
                                      @Query("UserUniqueIdentifire") String UserUniqueIdentifire);


    @GET("NativeUserHome/GetContentWithoutUser")
    Call<SignInPageSliderDTO> getSliderImages();


    @GET("NativeContent/RecentlyWatched")
    Call<RecentlyWatchDTO> getRecentlyWatchConetnt(@Query("userId") Integer userId,
                                              @Query("UserUniqueIdentifire") String UserUniqueIdentifire);





    @GET("NativeContent/DeleteContentViewedHistotyByContentID")
    Call<DeleteContentViewHistoryDTO> deleteContentViewHistory(@Query("userId") Integer userId,
                                                              @Query("contentID") Integer contentID);

    @GET("NativeContent/RecentlyAdded")
    Call<RecentelyAddedDTO> getRecentlyAddedContent(@Query("userId") Integer userId,
                                                    @Query("UserUniqueIdentifire") String UserUniqueIdentifire);


    @GET("AboutUs/GettblBlogs")
    Call<List<BlogDto>> blog();

    @GET("AboutUs/GetBlogDetails")
    Call<List<BlogDetailDTO>> blogdetails(@Query("BlogId") String offset);

    @GET("NativeContent/ContentListByUser")
    Call<ContentDTO> getAllContent(@Query("userId") Integer userId,
                                    @Query("SearchString") String SearchString,
                                    @Query("LastId") Integer LastId,
                                    @Query("count") Integer count);



    @GET("5dae9cb35813b3142ae969b9")
    Call<ContentDTO> getsearchdata(@Header("secret-key") String secretkey);












/*
    @GET("Category/GetContent")
   Call<ArrayList<CategoryDTO>> getCategory(@Query("GenresID") String GenresID,
                                           @Query("Year") String Year,
                                           @Query("ReviewsId") String ReviewsId,
                                           @Query("StudioId") String StudioId,
                                           @Query("FeaturesId") String FeaturesId,
                                           @Query("MPAARatingId") String MPAARatingId,
                                           @Query("ContentTypeID") String ContentTypeID ,
                                           @Query("CategoryId") String CategoryId );*/


    @GET("Category/GetContent")
    Call<ArrayList<CategoryDTO>> getCategory(@Query("GenresID") String GenresID,
                                             @Query("Year") String Year,
                                             @Query("ReviewsId") String ReviewsId,
                                             @Query("StudioId") String StudioId,
                                             @Query("FeaturesId") String FeaturesId,
                                             @Query("MPAARatingId") String MPAARatingId,
                                             @Query("ContentTypeID") String ContentTypeID,
                                             @Query("CategoryId") String CategoryId);


    @GET("NativeContent/ContentListForFilterInHome")
    Call<CategoryFilterDTO> getContentFilterData(@Query("userId") Integer userId,
                                                 @Query("UserUniqueIdentifire") String UserUniqueIdentifire,
                                                 @Query("CategoryID") String CategoryID,
                                                 @Query("GenreID") String GenresID,
                                                 @Query("Year") String Year,
                                                 @Query("ReviewsID") String ReviewsId,
                                                 @Query("StudioID") String StudioId,
                                                 @Query("FeaturesID") String FeaturesId,
                                                 @Query("MPARatingID") String MPAARatingId,
                                                 @Query("ContentTypeID") String ContentTypeID,
                                                 @Query("Count") Integer Count,
                                                 @Query("LastId") Integer LastId,
                                                 @Query("MovieSeason") Integer MovieSeason);


    @Headers({"application-id: MY-APPLICATION-ID",
            "secret-key: MY-SECRET-KEY",
            "application-type: REST"})
    @GET("NativeContent/ContentDetailsByID")
    Call<ContentDTO> getAllMovieDetails(@Query("ContentID") Integer ContentID , @Query("UserID") Integer UserID);




    @GET("NativeContent/PostContentReview")
    Call<PostContentReviews> postAllContentReview(@Query("userId") Integer userId,@Query("UserUniqueIdentifire") String UserUniqueIdentifire,@Query("Msg") String Msg,@Query("Stars") Integer Stars,@Query("contentId") Integer contentId,@Query("details") Integer details);


    @PUT("NativeContent/PutContentReview")
    Call<PutContentReview> putContentReview(@Query("userId") Integer userId, @Query("UserUniqueIdentifire") String UserUniqueIdentifire, @Query("Msg") String Msg, @Query("Stars") Integer Stars, @Query("contentId") Integer contentId, @Query("details") Integer details, @Query("reviewId") Integer reviewId);

    @GET("SubscriptionPlanMasters/GetDisplayAllInnerList")
    Call<ArrayList<PlanMasterDTO>> getPlanMaster();

    @GET("SubscriptionPlanMasters/GetDisplayAllInnerList")
    Call<ArrayList<PlanMasterDTO>> getBasic();


    @GET("SubscriptionPlanMasters/GetDisplayAllPlanList")
    Call<ArrayList<PlanMasterDTO>> getMonthYearly();


//    @GET("user/tutorials")
//    Call<TutorialDTO> getTutorial();
//
//    @GET("user/all/lists")
//    Call<CategoryDTO> getCategoryList(
//            @Header("token") String token);


    @POST("user/password/forgot")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> forgotPassword(
            @Field("email") String Email);

    @POST("user/password/reset")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> resetPassword(
            @Field("email") String Email,
            @Field("password") String password,
            @Field("verification_code") String verificationCode);


    @PUT("user/password/change")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> changePassword(
            @Header("token") String token,
            @Field("old_password") String oldPassword,
            @Field("new_password") String newPassword);

    @PUT("customer/profile/edit")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> customerEditProfile(
            @Header("token") String token,
            @Field("name") String fullName,
            @Field("email") String Email,
            @Field("mobile_number") String mobile_number,
            @Field("country_code") String country_code,
            @Field("address") String address,
            @Field("about_me") String user_type,
            @Field("profile_image") String profileImage,
            @Field("image_extension") String imageExtension,
            @Field("company") String company);

    @PUT("vendor/profile/edit")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> vendorEditProfile(
            @Header("token") String token,
            @Field("name") String fullName,
            @Field("email") String Email,
            @Field("mobile_number") String mobile_number,
            @Field("country_code") String country_code,
            @Field("address") String address,
            @Field("about_me") String user_type,
            @Field("profile_image") String profileImage,
            @Field("image_extension") String imageExtension,
            @Field("company") String company,
            @Field("company_type_id") String companyType,
            @Field("ssn_number") String ssnNumber);


    @PUT("intermediate/profile/edit")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> intermediateEditProfile(
            @Header("token") String token,
            @Field("name") String fullName,
            @Field("email") String Email,
            @Field("mobile_number") String mobile_number,
            @Field("country_code") String country_code,
            @Field("address") String address,
            @Field("about_me") String user_type,
            @Field("profile_image") String profileImage,
            @Field("image_extension") String imageExtension);


//    @GET("user/content/{type}")
//    Call<AboutUsDTO> getAboutApp(
//            @Path("type") String type
//    );


    @POST("user/resend_otp")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> ResendOTP(
            @Field("mobile_number") String mobile_number,
            @Field("country_code") String country_code);


    @POST("vendor/skills")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> addSkill(
            @Header("token") String token,
            @Field("skill_ids") String skillIds);


    @GET("vendor/skills")
    Call<ResponseBody> getVendorSkillList(
            @Header("token") String token);

    @POST("customer/document/add")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> customerDocumentAdd(
            @Header("token") String token,
            @Field("document_image") String documentImage,
            @Field("document_extention") String documentExtention);

    @POST("customer/document/delete")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> customerDocumentDelete(
            @Header("token") String token,
            @Field("document_name") String documentImage);

    @POST("customer/requirements")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"})
    Call<ResponseBody> customerAddRequirement(
            @Header("token") String token,
            @Field("category_id") String categoryId,
            @Field("sub_category_id") String subCategoryId,
            @Field("title") String title,
            @Field("description") String description,
            @Field("team_size") String teamSize,
            @Field("per_person_amount") String perPersonAmount,
            @Field("duration") String duration,
            @Field("referral_code") String referralCode,
            @Field("team_country") String teamCountry,
            @Field("country_id") String countryId,
            @Field("project_files") String projectFiles);

    @GET("customer/teams")
    Call<ResponseBody> customerFindTeam(
            @Header("token") String token,
            @Query("offset") String offset,
            @Query("limit") String limit,
            @Query("country_id") String countryId,
            @Query("category_id") String categoryId,
            @Query("sub_category_id") String subCategoryId,
            @Query("skill_ids") String skillIds,
            @Query("team_size") String teamSize,
            @Query("rate_range") String rateRange);



    @GET("AboutUs/GettblAboutUs")
    Call<List<AboutUsResponse>> aboutUs();

    @GET("Careers/GettblCareers")
    Call<List<CareerResponse>> getCareers();


    @GET("NativeUserHome/GetThumbnail")
    Call<ProfileDTO> getProfile(@Query("uniqueIdentifier") String  uniqueIdentifier);

    @POST("NativeUserHome/PostProfile")
    Call<ProfileDTO> postProfile(@Body PostProfileDTO dto);

//    @GET("Careers/GettblCareers")
//    Call<List<CareerResponse>> post();


    @GET("CompanyProfiles/GettblCompanyProfiles")
    Call<List<CompanyProfileResponse>> getContactUs();


    @POST("tblContactUs/PosttblContactU")
//    @FormUrlEncoded
//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/x-www-form-urlencoded"})
    Call<ContactDTO> PostContactDetail(
            @Body ContactDTO dto);




//    @FormUrlEncoded
//    @POST("BrainTreePayment/Create?amount=2&paymentMethodnonce=tokencc_bh_z8wkqk_ncgw6y_hx2md4_gcgx6j_7d4")
//    @Headers({
//            "Accept: application/json",
//            "Content-Type: application/x-www-form-urlencoded"})
//    Call<BrainTreePostResponse> getBrainTree1();


//    @FormUrlEncoded
    @POST("BrainTreePayment/Create")
    Call<BrainTreePostResponse> getBrainTree(@Query("amount") Double amount, @Query("paymentMethodnonce") String paymentMethodnonce);
//    Call<BrainTreePostResponse> getBrainData(@Field("amount") Double amount, @Field("paymentMethodnonce") String paymentMethodnonce, Callback<BrainTreePostResponse> callback);



    @GET("BrainTreePayment/GetBrainTreeCredentials")
    Call<BrainTreeResponse> getBrainTreeData();

    @GET("NativeSubscriptionPlanMaster/GetSubscriptionPlanAllDetails")
    Call<PlantestDTO> getPlanDetail();

    @GET("NativeContent/GetAddToWishList")
    Call<JsonObject> getaddtofavouritewishlist(@Query("userId") Integer userId, @Query("ContentId") Integer ContentId);

    @GET("NativeContent/GetWishList")
    Call<WishListDTO> getFavouriteListData(@Query("userId") Integer userId,@Query("other") Integer other);

    @GET("NativeContent/getSeasons")
    Call<SeasonDTO> getSeason(@Query("seriesID") Integer seriesID);

    @GET("NativeContent/getEpisodes")
    Call<EpisodeDTO> getEpisode(@Query("seasonID") Integer seasonID);

    @POST("NativeContent/PostContentViewHistory")
    Call<ContentViewHistoryDTO> postContentViewHistory(@Query("contentID") Integer contentID,@Query("userId") Integer userId);


    @POST("NativeContent/PutContentViewedTime")
    Call<ContentViewTimeDTO> postContentViewTime(@Query("userId") Integer userId, @Query("contetId") Integer contetId,@Query("Time") long Time);


    @GET("UserNotificaiton/GetNotification")
    Call<List<NotificationDTO>> getNotification(@Query("UserUniqueIdentifire") String  uniqueIdentifier);

    @GET("SupportQandAns/getAllQuestionsAndAnswers")
    Call<SupportDTO> getSupportQandA();

    @GET("UserProfile/GetUserProfileDetails")
    Call<AccountSettingDTO> getUserDetail(@Query("UniqueIdentifire") String  uniqueIdentifier);

    @POST("UserProfile/PutUserProfileDetails")
    Call<AccountSettingDTO> postUserDetail(@Body AccountSettingDTO accountSettingDTO);

    @GET("UserProfile/GetCountryList")
    Call<List<CountryListResponse>> getCountryList();

    @GET("UserProfile/GetStateList")
    Call<List<CountryListResponse>> getStateList(@Query("CountryID") int CountryID);

    @GET("UserProfile/GetCityListForSuggesions")
    Call<List<CountryListResponse>> getcityList(@Query("StateId") int StateId);


//    @GET("customer/requirements")
//    Call<CustomerRequirementDTO> customerRequirements(
//            @Header("token") String token);
//
//    @GET("customer/requirements/all")
//    Call<ResponseBody> customerRequirementsAll(
//            @Header("token") String token,
//            @Query("offset") String offset,
//            @Query("limit") String limit,
//            @Query("search") String search,
//            @Query("category_id") String category_id,
//            @Query("sub_category_id") String sub_category_id,
//            @Query("type") String type);
//
//    @GET("customer/requirements/{requirement_key}")
//    Call<CustomerRequirementDetailDTO> customerRequirementsDetail(
//            @Header("token") String token,
//            @Path("requirement_key") String requirement_key);
//
//    @POST("user/support")
////    @FormUrlEncoded
////    @Headers({
////            "Accept: application/json",
////            "Content-Type: application/x-www-form-urlencoded"})
////    Call<ResponseBody> userSupport(
////            @Field("email") String Email,
////            @Field("message") String message);
//
//    @GET("vendor/requirements")
//    Call<CustomerRequirementDTO> vendorRequirements(
//            @Header("token") String token);

   /* @POST("login")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"
    })
    Call<UserDTO> login(
            @Field("email") String Email,
            @Field("password") String password,
            @Field("device_key") String device_key,
            @Field("notification_key") String notification_key,
            @Field("app_version") String app_version,
            @Field("device_type") String device_type);

    @POST("registration")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"
    })
    Call<UserDTO> Registration(
            @Field("user_id") String userID,
            @Field("email") String Email,
            @Field("password") String password,
            @Field("device_key") String device_key,
            @Field("notification_key") String notification_key,
            @Field("app_version") String app_version,
            @Field("device_type") String device_type);


    @POST("add_car_details")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"
    })
    Call<ResponseBody> AddCarDetails(
            @Header("token") String token,
            @Field("make_id") String car_make,
            @Field("model_id") String car_model,
            @Field("trim_id") String trim_id,
            @Field("color_id") String color_id,
            @Field("car_year") String car_year,
            @Field("plug_ids") String plugId);


    @GET("models")
    Call<CarModelsDTO> getModels(@Query("make_id") String model_id);

    @GET("trims_colors")
    Call<CarModelsDTO> getTrimColor(@Query("model_id") String model_id);

    @GET("stations")
    Call<NearbyDTO> getNearbyStations(@Query("latitude") String latitude,
                                      @Query("longitude") String longitude,
                                      @Query("range") String range,
                                      @Query("plug_type") String plugType,
                                      @Query("network_type") String networkType,
                                      @Query("location_type") String locationType,
                                      @Query("public_location") String publicLocation);

    @GET("stations_detail/{id}")
    Call<StationDetailDTO> getStationsDetail(
            @Path("id") String id);

    @POST("forgot_password")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"
    })
    Call<ResponseBody> forgotPassword(
            @Field("email") String Email);

    @POST("add_report")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"
    })
    Call<ResponseBody> reportProblem(
            @Header("token") String token,
            @Field("report_id") String reportId,
            @Field("station_id") String stationId);

    @POST("edit_profile")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"
    })
    Call<UserDTO> editProfile(
            @Header("token") String token,
            @Field("email") String Email,
            @Field("user_id") String user_id,
            @Field("profile_image") String profile_image,
            @Field("image_extension") String image_extension);*/

}
