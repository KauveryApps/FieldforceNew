package com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Row implements Serializable
{

    @SerializedName("visit_purposeid")
    @Expose
    private String visitPurposeid;
    @SerializedName("visitpurpose")
    @Expose
    private String visitpurpose;
    private final static long serialVersionUID = -5776815502139443608L;

    public String getVisitPurposeid() {
        return visitPurposeid;
    }

    public void setVisitPurposeid(String visitPurposeid) {
        this.visitPurposeid = visitPurposeid;
    }

    public String getVisitpurpose() {
        return visitpurpose;
    }

    public void setVisitpurpose(String visitpurpose) {
        this.visitpurpose = visitpurpose;
    }

}