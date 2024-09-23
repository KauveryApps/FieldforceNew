package com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Resultadmin implements Serializable {
    @SerializedName("result")
    @Expose
    private Result_admin result;
    private final static long serialVersionUID = -7691001539827547897L;

    public Result_admin getResult() {
        return result;
    }

    public void setResult(Result_admin result) {
        this.result = result;
    }
}
