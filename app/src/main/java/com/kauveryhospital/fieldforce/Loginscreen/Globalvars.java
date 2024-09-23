package com.kauveryhospital.fieldforce.Loginscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Globalvars implements Serializable {
    @SerializedName("m_finyr")
    @Expose
    private String mFinyr;
    @SerializedName("isbranchmandatory")
    @Expose
    private String isbranchmandatory;
    @SerializedName("m_hosite")
    @Expose
    private String mHosite;
    @SerializedName("axp_dataexchange")
    @Expose
    private String axpDataexchange;
    @SerializedName("report_download")
    @Expose
    private String reportDownload;
    @SerializedName("responsibilies")
    @Expose
    private String responsibilies;
    @SerializedName("rolename")
    @Expose
    private String rolename;
    @SerializedName("ax_evalcopy")
    @Expose
    private String axEvalcopy;
    @SerializedName("is_ohc")
    @Expose
    private  String is_ohc;
    private final static long serialVersionUID = -3736055061667198172L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Globalvars() {
    }




    public String getMFinyr() {
        return mFinyr;
    }

    public void setMFinyr(String mFinyr) {
        this.mFinyr = mFinyr;
    }

    public String getIsbranchmandatory() {
        return isbranchmandatory;
    }

    public void setIsbranchmandatory(String isbranchmandatory) {
        this.isbranchmandatory = isbranchmandatory;
    }

    public String getMHosite() {
        return mHosite;
    }

    public void setMHosite(String mHosite) {
        this.mHosite = mHosite;
    }

    public String getAxpDataexchange() {
        return axpDataexchange;
    }

    public void setAxpDataexchange(String axpDataexchange) {
        this.axpDataexchange = axpDataexchange;
    }

    public String getReportDownload() {
        return reportDownload;
    }

    public void setReportDownload(String reportDownload) {
        this.reportDownload = reportDownload;
    }

    public String getResponsibilies() {
        return responsibilies;
    }

    public void setResponsibilies(String responsibilies) {
        this.responsibilies = responsibilies;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getAxEvalcopy() {
        return axEvalcopy;
    }

    public void setAxEvalcopy(String axEvalcopy) {
        this.axEvalcopy = axEvalcopy;
    }

public  String getIs_ohc(){
        return  is_ohc;
}
public  void setIs_ohc(String is_ohc){
        this.is_ohc=is_ohc;
}
}
