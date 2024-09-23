package com.kauveryhospital.fieldforce.Globaldeclare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    @SerializedName("result")
    @Expose
    private Result_ result;

    @SerializedName("message")
    @Expose
    private List<Message> message = null;
    @SerializedName("error")
    @Expose
    private Error error;
    @SerializedName("status")
    @Expose
    private String status;
    private final static long serialVersionUID = -7691001539827547897L;

    public Result_ getResult() {
        return result;
    }

    public void setResult(Result_ result) {
        this.result = result;
    }



    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }
    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
