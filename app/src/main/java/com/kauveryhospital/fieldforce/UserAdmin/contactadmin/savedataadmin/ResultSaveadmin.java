package com.kauveryhospital.fieldforce.UserAdmin.contactadmin.savedataadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultSaveadmin implements Serializable
{

    @SerializedName("message")
    @Expose
    private List<Messageadmin> message = null;

    @SerializedName("error")
    @Expose
    private Erroradmin error = null;
    private final static long serialVersionUID = -4856244032777071550L;

    public List<Messageadmin> getMessage() {
        return message;
    }

    public void setMessage(List<Messageadmin> message) {
        this.message = message;
    }


    public Erroradmin getError() {
        return error;
    }

    public void setError(Erroradmin error) {
        this.error = error;
    }


}