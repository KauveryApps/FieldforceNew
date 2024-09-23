package com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Message implements Serializable
{

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("recordid")
    @Expose
    private String recordid;

    @SerializedName("save")
    @Expose
    private String save;
    private final static long serialVersionUID = -5433637476526539590L;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }
    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }
}