package com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Exam implements Serializable
{

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    private final static long serialVersionUID = -8960018075369253545L;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}