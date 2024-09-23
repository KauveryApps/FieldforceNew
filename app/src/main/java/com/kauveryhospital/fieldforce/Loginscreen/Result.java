package com.kauveryhospital.fieldforce.Loginscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result implements Serializable {
    @SerializedName("result")
    @Expose
    private Result_ result;

    @SerializedName("error")
    @Expose
    private Error error;
    private final static long serialVersionUID = -6828404921508834716L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Result() {
    }

    /**
     *
     * @param result
     */
    public Result(Result_ result) {
        super();
        this.result = result;
    }

    public Result_ getResult() {
        return result;
    }

    public void setResult(Result_ result) {
        this.result = result;
    }
    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

}
