package com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.uploadfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExampleSaveupl implements Serializable
{

    @SerializedName("result")
    @Expose
    private List<ResultSavesupl> result = null;
    private final static long serialVersionUID = -8960018075369253545L;

    public List<ResultSavesupl> getResult() {
        return result;
    }

    public void setResult(List<ResultSavesupl> result) {
        this.result = result;
    }

}