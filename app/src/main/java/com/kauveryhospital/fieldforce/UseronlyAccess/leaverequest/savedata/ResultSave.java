package com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultSave implements Serializable
{

    @SerializedName("message")
    @Expose
    private List<Message> message = null;

    @SerializedName("error")
    @Expose
    private Error error = null;
    private final static long serialVersionUID = -4856244032777071550L;

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

}