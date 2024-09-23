package com.kauveryhospital.fieldforce.UserAdmin.contactadmin;

public class ContactItem {
private  String mcontype,msalutation,mcontactname,mcorporate,mambulance,mspecialization,mportfolio,mphone,mcont_corpid,maddress,mcity_name,marea,mpincode,mstate_name,mstateid,mcityid;
    public ContactItem(String contype, String salutation, String contactname, String corporate, String ambulance, String specialization, String portfolio, String phone, String cont_corpid, String address, String city_name, String area, String pincode, String state_name, String stateid, String cityid) {
   mcontype=contype;
   msalutation=salutation;
   mcontactname=contactname;
   mcorporate=corporate;
   mambulance=ambulance;
   mspecialization=specialization;
   mportfolio=portfolio;
   mphone=phone;
   mcont_corpid=cont_corpid;
   maddress=address;
   mcity_name=city_name;
   marea=area;
   mpincode=pincode;
   mstate_name=state_name;
   mstateid=stateid;
   mcityid=cityid;
    }
    public  String getMcontype(){
        return  mcontype;
    }
    public String getMsalutation()
    {
        return msalutation;
    }
    public  String getMcontactname(){
        return mcontactname;
    }
    public  String getMcorporate(){
        return mcorporate;
    }
    public String getMambulance(){
        return mambulance;
    }
    public String getMspecialization(){
        return  mspecialization;
    }
    public  String getMportfolio(){
        return  mportfolio;
    }
    public String getMphone(){
        return mphone;
    }
    public  String getMcont_corpid(){
        return  mcont_corpid;
    }
    public String getMaddress(){
        return maddress;
    }
    public String getMcity_name(){
        return  mcity_name;
    }
    public String getMarea(){
        return  marea;
    }
    public  String getMpincode(){
        return  mpincode;
    }
    public  String getMstate_name(){
        return  mstate_name;
    }
    public  String getMstateid(){
        return  mstateid;
    }
    public  String getMcityid(){
        return  mcityid;
    }
}
