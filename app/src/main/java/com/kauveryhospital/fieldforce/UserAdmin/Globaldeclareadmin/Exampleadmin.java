package com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Exampleadmin implements Serializable {
    @SerializedName("result")
    @Expose
    private List<Resultadmin> result = null;
    private final static long serialVersionUID = -8960018075369253545L;

    public List<Resultadmin> getResult() {
        return result;
    }

    public void setResult(List<Resultadmin> result) {
        this.result = result;
    }
}
