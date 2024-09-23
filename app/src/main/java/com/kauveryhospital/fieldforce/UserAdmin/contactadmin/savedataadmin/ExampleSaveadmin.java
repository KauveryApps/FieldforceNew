package com.kauveryhospital.fieldforce.UserAdmin.contactadmin.savedataadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExampleSaveadmin implements Serializable
{

    @SerializedName("result")
    @Expose
    private List<ResultSaveadmin> result = null;
    private final static long serialVersionUID = -8960018075369253545L;

    public List<ResultSaveadmin> getResult() {
        return result;
    }

    public void setResult(List<ResultSaveadmin> result) {
        this.result = result;
    }

}