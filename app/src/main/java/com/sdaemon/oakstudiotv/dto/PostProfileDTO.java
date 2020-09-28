package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostProfileDTO {

    @SerializedName("UniqueIdentifier")
    @Expose
    private String UniqueIdentifier;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("fileext")
    @Expose
    private String fileext;

    public String getUniqueIdentifier() {
        return UniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        UniqueIdentifier = uniqueIdentifier;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileext() {
        return fileext;
    }

    public void setFileext(String fileext) {
        this.fileext = fileext;
    }
}
