package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseUrlDTO {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    @SerializedName("app_version")
    @Expose
    private String appVersion;
    @SerializedName("image_urls")
    @Expose
    private ImageUrls imageUrls;
    @SerializedName("pages")
    @Expose
    private Pages pages;
    @SerializedName("contact_info")
    @Expose
    private ContactInfo contactInfo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public ImageUrls getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ImageUrls imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public class ImageUrls {

        @SerializedName("user_image")
        @Expose
        private String userImage;
        @SerializedName("flag_image")
        @Expose
        private String flagImage;
        @SerializedName("tutorial_image")
        @Expose
        private String tutorialImage;
        @SerializedName("requirement_image")
        @Expose
        private String requirementImage;

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getFlagImage() {
            return flagImage;
        }

        public void setFlagImage(String flagImage) {
            this.flagImage = flagImage;
        }

        public String getTutorialImage() {
            return tutorialImage;
        }

        public void setTutorialImage(String tutorialImage) {
            this.tutorialImage = tutorialImage;
        }

        public String getRequirementImage() {
            return requirementImage;
        }

        public void setRequirementImage(String requirementImage) {
            this.requirementImage = requirementImage;
        }
    }

    public class Pages {

    }

    public class ContactInfo {

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone_no")
        @Expose
        private String phoneNo;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

    }
}
