package com.kauveryhospital.fieldforce.UseronlyAccess.planned.getdatas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result_ implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    private final static long serialVersionUID = -1538473674803247182L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}