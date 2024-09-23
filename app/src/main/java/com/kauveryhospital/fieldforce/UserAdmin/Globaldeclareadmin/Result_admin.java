package com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Result_admin implements Serializable {
    @SerializedName("row")
    @Expose
    private List<Rowadmin> row = null;
    @SerializedName("status")
    @Expose
    private String status;
    private final static long serialVersionUID = -5523913236721107172L;

    public List<Rowadmin> getRow() {
        return row;
    }

    public void setRow(List<Rowadmin> row) {
        this.row = row;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
