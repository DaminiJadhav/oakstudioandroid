package com.sdaemon.oakstudiotv.dto;

/**
 * Created by linux122 on 19/3/16.
 */
public class ResponseDTO<OBJ1, OBJ2, OBJ3, OBJ4, L1, L2, L3, L4, L5, L6> {

    private String message = "", profileImage = "", idProofImage = "", accountId = "", content = "", verificationCode = "", appVersion = "", currency = "", date = "",  time = "",startDate = "",verifiedStatus = "";
    private int missionVersion = 0, statusCode, skillVersion = 0, interactionVersion = 0, freeUserLevel = 0, id = 0;

    private double doctorPergraft = 0.0, leadPergraft = 0.0, assistantPergraft = 0.0, minDepositCharge = 0.0, maxDepositCharge = 0.0;

    private ResultDTO<OBJ1, OBJ2, OBJ3, OBJ4, L1, L2, L3, L4, L5, L6> resultDTO;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getVerifiedStatus() {
        return verifiedStatus;
    }

    public void setVerifiedStatus(String verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    public String getIdProofImage() {
        return idProofImage;
    }

    public void setIdProofImage(String idProofImage) {
        this.idProofImage = idProofImage;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public ResultDTO<OBJ1, OBJ2, OBJ3, OBJ4, L1, L2, L3, L4, L5, L6> getResultDTO() {
        return resultDTO;
    }

    public void setResultDTO(ResultDTO<OBJ1, OBJ2, OBJ3, OBJ4, L1, L2, L3, L4, L5, L6> resultDTO) {
        this.resultDTO = resultDTO;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMissionVersion() {
        return missionVersion;
    }

    public void setMissionVersion(int missionVersion) {
        this.missionVersion = missionVersion;
    }

    public int getSkillVersion() {
        return skillVersion;
    }

    public void setSkillVersion(int skillVersion) {
        this.skillVersion = skillVersion;
    }

    public int getInteractionVersion() {
        return interactionVersion;
    }

    public void setInteractionVersion(int interactionVersion) {
        this.interactionVersion = interactionVersion;
    }

    public int getFreeUserLevel() {
        return freeUserLevel;
    }

    public void setFreeUserLevel(int freeUserLevel) {
        this.freeUserLevel = freeUserLevel;
    }


    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getDoctorPergraft() {
        return doctorPergraft;
    }

    public void setDoctorPergraft(double doctorPergraft) {
        this.doctorPergraft = doctorPergraft;
    }

    public double getLeadPergraft() {
        return leadPergraft;
    }

    public void setLeadPergraft(double leadPergraft) {
        this.leadPergraft = leadPergraft;
    }

    public double getAssistantPergraft() {
        return assistantPergraft;
    }

    public void setAssistantPergraft(double assistantPergraft) {
        this.assistantPergraft = assistantPergraft;
    }

    public double getMinDepositCharge() {
        return minDepositCharge;
    }

    public void setMinDepositCharge(double minDepositCharge) {
        this.minDepositCharge = minDepositCharge;
    }

    public double getMaxDepositCharge() {
        return maxDepositCharge;
    }

    public void setMaxDepositCharge(double maxDepositCharge) {
        this.maxDepositCharge = maxDepositCharge;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
