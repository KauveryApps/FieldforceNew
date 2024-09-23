package com.kauveryhospital.fieldforce.UseronlyAccess.planned.getdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Getcheckout implements Serializable
{

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    private final static long serialVersionUID = 4021582937768570623L;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
