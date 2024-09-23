package com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savedata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExampleSave implements Serializable
{

    @SerializedName("result")
    @Expose
    private List<ResultSave> result = null;
    private final static long serialVersionUID = -8960018075369253545L;

    public List<ResultSave> getResult() {
        return result;
    }

    public void setResult(List<ResultSave> result) {
        this.result = result;
    }

}