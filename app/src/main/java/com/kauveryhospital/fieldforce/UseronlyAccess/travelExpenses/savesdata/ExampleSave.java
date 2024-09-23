package com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExampleSave implements Serializable
{

    @SerializedName("result")
    @Expose
    private List<ResultSaves> result = null;
    private final static long serialVersionUID = -8960018075369253545L;

    public List<ResultSaves> getResult() {
        return result;
    }

    public void setResult(List<ResultSaves> result) {
        this.result = result;
    }

}