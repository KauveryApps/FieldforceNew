package com.kauveryhospital.fieldforce.UserAdmin.unplanvisitAdmin;

public class UnplanItem {
    private String memployee,maddress,mvisitdate,mcheckin,mcheckout,mnickname;

    public UnplanItem(String employee, String nickname,String address, String visitdate, String checkin, String checkout) {
        memployee=employee;
        mnickname=nickname;
        maddress=address;
        mvisitdate=visitdate;
        mcheckin=checkin;
        mcheckout=checkout;
    }
    public String getMemployee(){
        return memployee;
    }
    public  String getMnickname(){
        return  mnickname;
    }
    public String getMaddress(){
        return  maddress;
    }
    public String getMvisitdate(){
        return  mvisitdate;
    }
    public String getMcheckin(){
        return mcheckin;
    }
    public String getMcheckout(){
        return  mcheckout;
    }
}
