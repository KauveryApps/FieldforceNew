package com.kauveryhospital.fieldforce.UseronlyAccess.unplanned;

public class UnplannedvisitItem {
    String mempname,mvisitdate,maddress,mcustomer,mcheckin,mcheckouttime;

    public UnplannedvisitItem(String empname, String visitdate, String address, String customer, String checkin, String checkouttime) {
        mempname=empname;
        mvisitdate=visitdate;
        maddress=address;
        mcustomer=customer;
        mcheckin=checkin;
        mcheckouttime=checkouttime;
    }

    public String getMempname() {
        return mempname;
    }

    public void setMempname(String mempname) {
        this.mempname = mempname;
    }

    public String getMvisitdate() {
        return mvisitdate;
    }

    public String getMaddress() {
        return maddress;
    }

    public String getMcustomer() {
        return mcustomer;
    }

    public String getMcheckin() {
        return mcheckin;
    }

    public String getMcheckouttime() {
        return mcheckouttime;
    }
}
