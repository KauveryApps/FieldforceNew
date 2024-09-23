package com.kauveryhospital.fieldforce.UserAdmin.corporateadmin;

public class CorporateItem {
    private String mName;
    private String mState,mAddress,mCity,mPincode;
    public CorporateItem(String name, String state, String address, String city, String pincode) {
        mName=name;
        mState=state;
        mAddress=address;
        mCity=city;
        mPincode=pincode;
    }
    public String getmName(){
        return mName;
    }
    public String getmState() {
        return mState;
    }
    public String getmAddress(){
        return  mAddress;
    }
    public  String getmCity(){
        return mCity;
    }
    public String getmPincode(){
        return mPincode;
    }
}
