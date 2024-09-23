package com.kauveryhospital.fieldforce.workstartserviceuseronly.getdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Result_ implements Serializable
{

    @SerializedName("row")
    @Expose
    private List<Row> row = null;
    @SerializedName("status")
    @Expose
    private String status;

    private final static long serialVersionUID = -5523913236721107172L;

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}