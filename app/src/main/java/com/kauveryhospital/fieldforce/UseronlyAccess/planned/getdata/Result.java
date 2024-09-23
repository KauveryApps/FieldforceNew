package com.kauveryhospital.fieldforce.UseronlyAccess.planned.getdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable
{

    @SerializedName("result")
    @Expose
    private Result_ result;
    private final static long serialVersionUID = -7691001539827547897L;

    public Result_ getResult() {
        return result;
    }

    public void setResult(Result_ result) {
        this.result = result;
    }

}
