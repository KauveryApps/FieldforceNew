package com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Result_ implements Serializable
{

    @SerializedName("row")
    @Expose
    private List<Row> row = null;
    private final static long serialVersionUID = -5523913236721107172L;

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }

}