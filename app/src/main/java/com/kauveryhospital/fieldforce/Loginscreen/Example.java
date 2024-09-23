package com.kauveryhospital.fieldforce.Loginscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class Example implements Serializable {
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    private final static long serialVersionUID = 136279387005681803L;


    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
