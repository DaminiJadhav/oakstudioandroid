package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContentInfo implements Serializable{
    @SerializedName("RowNo")
    @Expose
    private Integer RowNo;
    @SerializedName("ContentID")
    @Expose
    private Integer contentID;
    @SerializedName("SeasonId")
    @Expose
    private Integer SeasonId;
    @SerializedName("ContentTitle")
    @Expose
    private String contentTitle;
    @SerializedName("ContentDescription")
    @Expose
    private String contentDescription;
    @SerializedName("ContentURL")
    @Expose
    private String contentURL;
    @SerializedName("ReleaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("Year")
    @Expose
    private Integer year;
    @SerializedName("ContentType")
    @Expose
    private String contentType;
    @SerializedName("ContentTitle1")
    @Expose
    private String ContentTitle1;
    @SerializedName("ProviderFirstName")
    @Expose
    private String ProviderFirstName;
    @SerializedName("ProviderLastName")
    @Expose
    private String ProviderLastName;
    @SerializedName("Laguageame")
    @Expose
    private String Laguageame;
    @SerializedName("StudioDescription")
    @Expose
    private String StudioDescription;
    @SerializedName("FeaturesDescription")
    @Expose
    private String FeaturesDescription;
    @SerializedName("RatingDescription")
    @Expose
    private String RatingDescription;
    @SerializedName("CategoryDescription")
    @Expose
    private String CategoryDescription;
    @SerializedName("ContentBanerImg")
    @Expose
    private String ContentBanerImg;
    @SerializedName("SmallDescription")
    @Expose
    private String SmallDescription;
    @SerializedName("MovieLength")
    @Expose
    private String MovieLength;
    @SerializedName("cc")
    @Expose
    private String cc;
    @SerializedName("CreaterName")
    @Expose
    private String CreaterName;
    @SerializedName("CountryName")
    @Expose
    private String CountryName;
    @SerializedName("ContentGenre")
    @Expose
    private String ContentGenre;
    @SerializedName("ViewCount")
    @Expose
    private String ViewCount;
//    ----------------------------------
    @SerializedName("Poster_Image")
    @Expose
    private String Poster_Image;
    @SerializedName("Trailer_Image")
    @Expose
    private String Trailer_Image;
    @SerializedName("Creators")
    @Expose
    private String Creators;
//    ----------------------------------
    @SerializedName("Ratings")
    @Expose
    private Integer Ratings;
    @SerializedName("SqImage")
    @Expose
    private String SqImage;
    @SerializedName("BannerImage")
    @Expose
    private String BannerImage;
    @SerializedName("TrailerImage")
    @Expose
    private String TrailerImage;


//    protected ContentInfo(Parcel in) {
//        contentID = in.readInt();
//        contentTitle = in.readString();
//        contentDescription = in.readString();
//        contentURL = in.readString();
//        releaseDate = in.readString();
//        year = in.readInt();
//        ContentTitle1 = in.readString();
//        ProviderFirstName = in.readString();
//        ProviderLastName = in.readString();
//        Laguageame = in.readString();
//        StudioDescription = in.readString();
//        FeaturesDescription = in.readString();
//        RatingDescription = in.readString();
//        CategoryDescription = in.readString();
//        ContentBanerImg = in.readString();
//        SmallDescription = in.readString();
//        MovieLength = in.readString();
//        cc = in.readString();
//        CreaterName = in.readString();
//        CountryName = in.readString();
//        ContentGenre = in.readString();
//        ViewCount = in.readString();
//        Poster_Image = in.readString();
//        Trailer_Image = in.readString();
//        Creators = in.readString();
//
//    }
//
//    public static final Creator<ContentInfo> CREATOR = new Creator<ContentInfo>() {
//        @Override
//        public ContentInfo createFromParcel(Parcel in) {
//            return new ContentInfo(in);
//        }
//
//        @Override
//        public ContentInfo[] newArray(int size) {
//            return new ContentInfo[size];
//        }
//    };


    public Integer getRowNo() {
        return RowNo;
    }

    public void setRowNo(Integer rowNo) {
        RowNo = rowNo;
    }

    public Integer getSeasonId() {
        return SeasonId;
    }

    public void setSeasonId(Integer seasonId) {
        SeasonId = seasonId;
    }

    public Integer getContentID() {
        return contentID;
    }

    public void setContentID(Integer contentID) {
        this.contentID = contentID;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentTitle1() {
        return ContentTitle1;
    }

    public void setContentTitle1(String contentTitle1) {
        ContentTitle1 = contentTitle1;
    }

    public String getProviderFirstName() {
        return ProviderFirstName;
    }

    public void setProviderFirstName(String providerFirstName) {
        ProviderFirstName = providerFirstName;
    }

    public String getProviderLastName() {
        return ProviderLastName;
    }

    public void setProviderLastName(String providerLastName) {
        ProviderLastName = providerLastName;
    }

    public String getLaguageame() {
        return Laguageame;
    }

    public void setLaguageame(String laguageame) {
        Laguageame = laguageame;
    }

    public String getStudioDescription() {
        return StudioDescription;
    }

    public void setStudioDescription(String studioDescription) {
        StudioDescription = studioDescription;
    }

    public String getFeaturesDescription() {
        return FeaturesDescription;
    }

    public void setFeaturesDescription(String featuresDescription) {
        FeaturesDescription = featuresDescription;
    }

    public String getRatingDescription() {
        return RatingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        RatingDescription = ratingDescription;
    }

    public String getCategoryDescription() {
        return CategoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        CategoryDescription = categoryDescription;
    }

    public String getContentBanerImg() {
        return ContentBanerImg;
    }

    public void setContentBanerImg(String contentBanerImg) {
        ContentBanerImg = contentBanerImg;
    }

    public String getSmallDescription() {
        return SmallDescription;
    }

    public void setSmallDescription(String smallDescription) {
        SmallDescription = smallDescription;
    }

    public String getMovieLength() {
        return MovieLength;
    }

    public void setMovieLength(String movieLength) {
        MovieLength = movieLength;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCreaterName() {
        return CreaterName;
    }

    public void setCreaterName(String createrName) {
        CreaterName = createrName;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getContentGenre() {
        return ContentGenre;
    }

    public void setContentGenre(String contentGenre) {
        ContentGenre = contentGenre;
    }

    public String getViewCount() {
        return ViewCount;
    }

    public void setViewCount(String viewCount) {
        ViewCount = viewCount;
    }
//    ---------------------------------------------------------------------

    public String getPoster_Image() {
        return Poster_Image;
    }

    public void setPoster_Image(String poster_Image) {
        Poster_Image = poster_Image;
    }

    public String getTrailer_Image() {
        return Trailer_Image;
    }

    public void setTrailer_Image(String trailer_Image) {
        Trailer_Image = trailer_Image;
    }

    public String getCreators() {
        return Creators;
    }

    public void setCreators(String creators) {
        Creators = creators;
    }

//    --------------------------------------------------------------------

    public Integer  getRatings() {
        return Ratings;
    }

    public void setRatings(Integer ratings) {
        Ratings = ratings;
    }

    public String getSqImage() {
        return SqImage;
    }

    public void setSqImage(String sqImage) {
        SqImage = sqImage;
    }

    public String getBannerImage() {
        return BannerImage;
    }

    public void setBannerImage(String bannerImage) {
        BannerImage = bannerImage;
    }

    public String getTrailerImage() {
        return TrailerImage;
    }

    public void setTrailerImage(String trailerImage) {
        TrailerImage = trailerImage;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeInt(RowNo);
//        parcel.writeInt(contentID);
//        parcel.writeInt(SeasonId);
//        parcel.writeString(contentTitle);
//        parcel.writeString(contentDescription);
//        parcel.writeString(contentURL);
//        parcel.writeString(releaseDate);
//        parcel.writeInt(year);
//        parcel.writeString(contentType);
//        parcel.writeString(ContentTitle1);
//        parcel.writeString(ProviderFirstName);
//        parcel.writeString(ProviderLastName);
//        parcel.writeString(Laguageame);
//        parcel.writeString(StudioDescription);
//        parcel.writeString(FeaturesDescription);
//        parcel.writeString(RatingDescription);
//        parcel.writeString(CategoryDescription);
//        parcel.writeString(ContentBanerImg);
//        parcel.writeString(SmallDescription);
//        parcel.writeString(MovieLength);
//        parcel.writeString(cc);
//        parcel.writeString(CreaterName);
//        parcel.writeString(CountryName);
//        parcel.writeString(ContentGenre);
//        parcel.writeString(ViewCount);
//        parcel.writeString(Poster_Image);
//        parcel.writeString(Trailer_Image);
//
//    }
}
