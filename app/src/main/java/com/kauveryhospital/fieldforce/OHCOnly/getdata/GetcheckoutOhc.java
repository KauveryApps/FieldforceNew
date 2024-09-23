package com.kauveryhospital.fieldforce.OHCOnly.getdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kauveryhospital.fieldforce.Globaldeclare.Error;

import java.io.Serializable;
import java.util.List;

public class GetcheckoutOhc implements Serializable
{

    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("error")
    @Expose
    private Error error;
    private final static long serialVersionUID = 4021582937768570623L;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
