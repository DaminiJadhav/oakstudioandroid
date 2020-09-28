package com.sdaemon.oakstudiotv.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrainTreeResponse {
    @SerializedName("MerchantId")
    @Expose
    String MerchantId;
    @SerializedName("PublicKey")
    @Expose
    String PublicKey;
    @SerializedName("PrivateKey")
    @Expose
    String PrivateKey;
    @SerializedName("AdditionalFee")
    @Expose
    float AdditionalFee;
    @SerializedName("AdditionalFeePercentage")
    @Expose
    boolean AdditionalFeePercentage;
    @SerializedName("ClientIdToken")
    @Expose
    String ClientIdToken;

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }

    public String getPublicKey() {
        return PublicKey;
    }

    public void setPublicKey(String publicKey) {
        PublicKey = publicKey;
    }

    public String getPrivateKey() {
        return PrivateKey;
    }

    public void setPrivateKey(String privateKey) {
        PrivateKey = privateKey;
    }

    public float getAdditionalFee() {
        return AdditionalFee;
    }

    public void setAdditionalFee(float additionalFee) {
        AdditionalFee = additionalFee;
    }

    public boolean isAdditionalFeePercentage() {
        return AdditionalFeePercentage;
    }

    public void setAdditionalFeePercentage(boolean additionalFeePercentage) {
        AdditionalFeePercentage = additionalFeePercentage;
    }

    public String getClientIdToken() {
        return ClientIdToken;
    }

    public void setClientIdToken(String clientIdToken) {
        ClientIdToken = clientIdToken;
    }
}
