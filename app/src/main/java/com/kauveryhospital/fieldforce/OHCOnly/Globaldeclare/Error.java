package com.kauveryhospital.fieldforce.OHCOnly.Globaldeclare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Error implements Serializable
{

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("save")
    @Expose
    private String save;

    @SerializedName("status")
    @Expose
    private String status;
    private final static long serialVersionUID = -1116046018159625305L;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}