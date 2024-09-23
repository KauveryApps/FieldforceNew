package com.kauveryhospital.fieldforce.UserAdmin.checkoutadmin;

public class CheckoutItem {
    private  String memployee,mcheckin,mcheckout,maddress,mremarks,mnickname;
    public CheckoutItem(String employee, String checkin, String checkout, String address, String remarks,String nickname) {
        memployee=employee;
        mcheckin=checkin;
        mcheckout=checkout;
        maddress=address;
        mremarks=remarks;
        mnickname=nickname;
    }
    public String getMnickname(){
        return mnickname;
    }
    public  String getMemployee(){
        return memployee;
    }
    public String getMcheckin(){
        return mcheckin;
    }
    public String getMcheckout(){
        return mcheckout;
    }
    public String getMaddress(){
        return maddress;
    }
    public String getMremarks(){
        return mremarks;
    }
}
