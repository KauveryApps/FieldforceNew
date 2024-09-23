package com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.uploadfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultSavesupl implements Serializable
{

    @SerializedName("message")
    @Expose
    private List<Messageupl> message = null;

    @SerializedName("error")
    @Expose
    private Errorupl error = null;
    private final static long serialVersionUID = -4856244032777071550L;

    public List<Messageupl> getMessage() {
        return message;
    }

    public void setMessage(List<Messageupl> message) {
        this.message = message;
    }

    public Errorupl getError() {
        return error;
    }

    public void setError(Errorupl error) {
        this.error = error;
    }

}