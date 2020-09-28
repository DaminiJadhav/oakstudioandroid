package com.sdaemon.oakstudiotv.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdaemon.oakstudiotv.dto.BaseUrlDTO;
import com.sdaemon.oakstudiotv.dto.ContentDTO;
import com.sdaemon.oakstudiotv.dto.FilterDTO;
import com.sdaemon.oakstudiotv.dto.NationalParkDTO;
import com.sdaemon.oakstudiotv.dto.UserStatusDTO;
import com.sdaemon.oakstudiotv.dto.UsersDTO;
import com.sdaemon.oakstudiotv.model.AboutUsResponse;
import com.sdaemon.oakstudiotv.model.VideoDTO;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class AppSession {
    private static final String APP_DEFAULT_LANGUAGE = "en";
    private static SharedPreferences sharedPref;
    private static Editor editor;
    private static AppSession appSession = null;
    public static AppSession getInstance(Context context) {
        if (appSession == null) {
            appSession = new AppSession();
            try {
                sharedPref = context.getSharedPreferences(context.getPackageName() + ".AppSession", Context.MODE_PRIVATE);
                editor = sharedPref.edit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return appSession;
    }

     //////// START SESSION FOR LOGIN CREDENTIALS //////////////////

   public void setOfflineDownloadList(List<VideoDTO> list) {
        Gson gson = new Gson();
        String jsonList = gson.toJson(list);
        editor.putString("getOfflineDownloadList", jsonList);
        editor.commit();
    }

    public ArrayList<VideoDTO> getOfflineDownloadList() {
        ArrayList<VideoDTO> id = new ArrayList<VideoDTO>();
        try {
            String userJSONString = sharedPref.getString("getOfflineDownloadList", null);
            if (userJSONString == null)
                return id;
            Type type = new TypeToken<ArrayList<VideoDTO>>() {
            }.getType();
            id = new Gson().fromJson(userJSONString, type);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        }
    }


    public void setUserStatusList(List<UserStatusDTO.Result> list) {
        Gson gson = new Gson();
        String jsonList = gson.toJson(list);
        editor.putString("getUserStatusList", jsonList);
        editor.commit();
    }

    public ArrayList<UserStatusDTO.Result> getUserStatusList() {
        ArrayList<UserStatusDTO.Result> id = new ArrayList<UserStatusDTO.Result>();
        try {
            String userJSONString = sharedPref.getString("getUserStatusList", null);
            if (userJSONString == null)
                return id;
            Type type = new TypeToken<ArrayList<UserStatusDTO.Result>>() {
            }.getType();
            id = new Gson().fromJson(userJSONString, type);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        }
    }












//Filtre

    public void setFilterList(List<FilterDTO> list) {
        Gson gson = new Gson();
        String jsonList = gson.toJson(list);
        editor.putString("getFilterList", jsonList);
        editor.commit();
    }

    public ArrayList<FilterDTO> getFilterList() {
        ArrayList<FilterDTO> id = new ArrayList<FilterDTO>();
        try {
            String userJSONString = sharedPref.getString("getFilterList", null);
            if (userJSONString == null)
                return id;
            Type type = new TypeToken<ArrayList<FilterDTO>>() {
            }.getType();
            id = new Gson().fromJson(userJSONString, type);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        }
    }

    public void setOfflineDate(String date) {
        editor.putString("getDate", date);
        editor.commit();
    }

    public String getOfflineDate() {
        return sharedPref.getString("getDate", "");
    }

    public void setContentID(Integer contentID) {
        editor.putString("ContentID", String.valueOf(contentID));
        editor.commit();
    }

    public String getContentID() {
        return sharedPref.getString("ContentID", "");
    }



    //MovieDetails

    public void setMovieDetail(ContentDTO contentDTO) {
        editor.putString("getMovieDetail", new Gson().toJson(contentDTO));
        editor.commit();
    }

    public ContentDTO getMovieDetail() {
        try {
            String movieJSONString = sharedPref.getString("getMovieDetail", null);
            if (movieJSONString == null)
                return null;
            Type type = new TypeToken<ContentDTO>() {
            }.getType();
            ContentDTO contentDTO= new Gson().fromJson(movieJSONString, type);
            return contentDTO;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ContentDTO();
        }
    }



    // Genre ID


  public void setGenreID(String GenreID) {
        editor.putString("getGenreID", GenreID);
        editor.commit();
    }

    public String getGenreID() {
        return sharedPref.getString("getGenreID", "");
    }


    public void setTVshowGenreID(String GenreID) {
        editor.putString("getTvGenreID", GenreID);
        editor.commit();
    }

    public String getTVshowGenreID() {
        return sharedPref.getString("getTvGenreID", "");
    }



    public void setvalueID(String GenreID) {
        editor.putString("setvalueID", GenreID);
        editor.commit();
    }

    public String getvalueID() {
        return sharedPref.getString("setvalueID", "");
    }

    public Integer getGenreIDposition() {
        return sharedPref.getInt("getGenreIDposition", 0);
    }

    public void setGenreIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getGenreIDposition", Position);
        editor.commit();
    }


    public Integer getTVGenreIDposition() {
        return sharedPref.getInt("getTVGenreIDposition", 0);
    }

    public void setTVGenreIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getTVGenreIDposition", Position);
        editor.commit();
    }

    //Review ID


    public void setReviewID(String yearID) {
        editor.putString("getReviewID", yearID);
        editor.commit();
    }

    public String getReviewID() {
        return sharedPref.getString("getReviewID", "");
    }


    public void setTVshowReviewD(String GenreID) {
        editor.putString("getTvReviewID", GenreID);
        editor.commit();
    }

    public String getTVshowReviewID() {
        return sharedPref.getString("getTvReviewID", "");
    }


    public Integer getReviewIDposition() {
        return sharedPref.getInt("getReviewIdposition", 0);
    }

    public void setReviewIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getReviewIdposition", Position);
        editor.commit();
    }

    public Integer getTVReviewIDposition() {
        return sharedPref.getInt("getTVReviewIdposition", 0);
    }

    public void setTVReviewIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getTVReviewIdposition", Position);
        editor.commit();
    }

    //Year ID


    public void setYearID(String yearID) {
        editor.putString("getYearID", yearID);
        editor.commit();
    }

    public String getYearID() {
        return sharedPref.getString("getYearID", "");
    }

    public void setTVshowYearID(String GenreID) {
        editor.putString("getTvYearID", GenreID);
        editor.commit();
    }

    public String getTVshowYearID() {
        return sharedPref.getString("getTvYearID", "");
    }


    public Integer getYearIDposition() {
        return sharedPref.getInt("getYearIdposition",0);
    }

    public void setYearIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getYearIdposition", Position);
        editor.commit();
    }

    public Integer getTVYearIDposition() {
        return sharedPref.getInt("getTVYearIdposition",0);
    }

    public void setTVYearIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getTVYearIdposition", Position);
        editor.commit();
    }

    //Studio ID

    public void setStudioID(String studioID) {
        editor.putString("getStudioID", studioID);
        editor.commit();
    }

    public String getStudioID() {
        return sharedPref.getString("getStudioID", "");
    }

    public void setTVshowStudioID(String GenreID) {
        editor.putString("getTvStudioID", GenreID);
        editor.commit();
    }

    public String getTVshowStudioID() {
        return sharedPref.getString("getTvStudioID", "");
    }


    public Integer getStudioIDposition() {
        return sharedPref.getInt("getStudioIdposition", 0);
    }

    public void setStudioIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getStudioIdposition", Position);
        editor.commit();
    }

    public Integer getTVStudioIDposition() {
        return sharedPref.getInt("getTVStudioIdposition", 0);
    }

    public void setTVStudioIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getTVStudioIdposition", Position);
        editor.commit();
    }


    //Feature ID

    public void setFeatureID(String studioID) {
        editor.putString("getFeatureID", studioID);
        editor.commit();
    }

    public String getFeatureID() {
        return sharedPref.getString("getFeatureID", "");
    }



    public void setTVshowFeatureID(String GenreID) {
        editor.putString("getTvFeatureID", GenreID);
        editor.commit();
    }

    public String getTVshowFeatureID() {
        return sharedPref.getString("getTvFeatureID", "");
    }

    public Integer getFeatureIDposition() {
        return sharedPref.getInt("getFeatureIdposition", 0);
    }

    public void setFeatureIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getFeatureIdposition", Position);
        editor.commit();
    }

    public Integer getTVFeatureIDposition() {
        return sharedPref.getInt("getTVFeatureIdposition", 0);
    }

    public void setTVFeatureIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getTVFeatureIdposition", Position);
        editor.commit();
    }


//Rating ID

    public void setRatingID(String studioID) {
        editor.putString("getRatingID", studioID);
        editor.commit();
    }

    public String getRatingID() {
        return sharedPref.getString("getRatingID", "");
    }

    public void setTVshowRatingID(String GenreID) {
        editor.putString("getTvRatingID", GenreID);
        editor.commit();
    }

    public String getTVshowRatingID() {
        return sharedPref.getString("getTvRatingID", "");
    }


    public Integer getRatingIDposition() {
        return sharedPref.getInt("getRatingIdposition", 0);
    }

    public void setRatingIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getRatingIdposition", Position);
        editor.commit();
    }


    public Integer getTVRatingIDposition() {
        return sharedPref.getInt("getTVRatingIdposition", 0);
    }

    public void setTVRatingIDposition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("getTVRatingIdposition", Position);
        editor.commit();
    }

//    MovieType


    public void setMovieType(Boolean ismovie) {
        editor.putBoolean("KEY_MovieType", ismovie);
        editor.commit();
    }

    public boolean getMovieType() {
        return sharedPref.getBoolean("KEY_MovieType",false);
    }


    public void setSeasonNo(Integer season_no) {
        editor.putString("KEY_SEASEON_NO", String.valueOf(season_no));
        editor.commit();
    }

    public String getSeasonNo() {
        return sharedPref.getString("KEY_SEASEON_NO", "");
    }



    //About Us

    public void setAbout(List<AboutUsResponse> aboutUsResponse) {
        editor.putString("aboutUsData", new Gson().toJson(aboutUsResponse));
        editor.commit();
    }

    public List<AboutUsResponse> getAbout() {
        try {
            String userJSONString = sharedPref.getString("aboutUsData", null);
            if (userJSONString == null)
                return null;
            Type type = new TypeToken<List<AboutUsResponse>>() {
            }.getType();
            List<AboutUsResponse> aboutUsResponse = new Gson().fromJson(userJSONString, type);
            return aboutUsResponse;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList<AboutUsResponse>();

        }
    }


//  Profile image

    public void setProfileImage(String profileimg) {
        editor.putString("ProfilePhoto", profileimg);
        editor.commit();
    }

    public String getProfileImage() {
        return sharedPref.getString("ProfilePhoto", "");
    }


// Languages

    public void setLanguage(String language) {
        editor.putString("KEY_LANGUAGE", language);
        editor.commit();
    }

    public String getLanguage() {
        return sharedPref.getString("KEY_LANGUAGE", "");
    }


    public void setSelectedLanguage(String language) {
        editor.putString("KEY_SELECTED_LANGUAGE", language);
        editor.commit();
    }

    public String getSelectedLanguage() {
        return sharedPref.getString("KEY_SELECTED_LANGUAGE", "");
    }
//------------------------------------
    public Integer getPosition() {
        return sharedPref.getInt("Position", 0);
    }

    public void setPosition(int Position) {
        editor = sharedPref.edit();
        editor.putInt("Position", Position);
        editor.commit();
    }
    public ArrayList<NationalParkDTO> getNotificationList() {
        Gson gson = new Gson();
        String json = sharedPref.getString("NotificationList", null);
        Type type = new TypeToken<ArrayList<NationalParkDTO>>() {}.getType();
        ArrayList<NationalParkDTO> notificationList = new ArrayList<>();
        notificationList = gson.fromJson(json, type);
        return notificationList;
    }

    public void setNotificationList(ArrayList<NationalParkDTO> notificationList) {
        Gson gson = new Gson();
        String json = gson.toJson(notificationList);
        editor = sharedPref.edit();
        editor.putString("NotificationList", json);
        editor.commit();
    }


    public void setUrlDto(BaseUrlDTO urlDto){
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String urls = gson.toJson(urlDto);
        editor.putString("urls",urls);
        editor.commit();
//        Log.e(getClass().getName(),"data Save''''''''''''''''''''''''''");
    }

    public BaseUrlDTO getUrlDto (){
        BaseUrlDTO urlDto = new BaseUrlDTO();
        String urls = sharedPref.getString("urls",null);
        if (urlDto == null)
            return null;
        Gson gson = new Gson();
        urlDto = gson.fromJson(urls,BaseUrlDTO.class);
        return urlDto;
    }

    public void setUserDTO(UsersDTO userDTO) {
        editor.putString("userDTO", new Gson().toJson(userDTO));
        editor.commit();
    }

    public UsersDTO getUserDTO() {
        try {
            String userJSONString = sharedPref.getString("userDTO", null);
            if (userJSONString == null)
                return null;
            Type type = new TypeToken<UsersDTO>() {
            }.getType();
            UsersDTO dto = new Gson().fromJson(userJSONString, type);
            return dto;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new UsersDTO();
        }
    }

//----------------------plan status--------------------------------------------

    public void setPlanStatus(String plan) {
        editor.putString("KEY_PLANSTATUS",plan);
        editor.commit();
    }

    public String getPlanStatus() {
        return sharedPref.getString("KEY_PLANSTATUS","");

      }



    public void setOffline(String date) {
        editor.putString("getkey", date);
        editor.commit();
    }
    public String getOfflinekey() {
        return sharedPref.getString("getkey", "");
    }



    public void logout(){
        editor.remove("getGenreID");
        editor.remove("getYearID");
        editor.remove("getReviewID");
        editor.remove("getStudioID");
        editor.remove("getFeatureID");
        editor.remove("getRatingID");
        editor.remove("KEY_PLANSTATUS");
        editor.remove("Position");
        editor.remove("getRatingIdposition");
        editor.remove("getFeatureIdposition");
        editor.remove("getStudioIdposition");
        editor.remove("getYearIdposition");
        editor.remove("getReviewIdposition");
        editor.remove("getGenreIDposition");
        editor.remove("ContentID");
        editor.remove("aboutUsData");


        editor.clear();
        editor.commit();
    }

}