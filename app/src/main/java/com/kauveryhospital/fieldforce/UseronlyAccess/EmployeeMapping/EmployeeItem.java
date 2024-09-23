package com.kauveryhospital.fieldforce.UseronlyAccess.EmployeeMapping;

public class EmployeeItem {
    private  String memployee,musrname,mcont_type,mcont_corp_name,maddress;
    public EmployeeItem(String employee, String usrname, String cont_type, String cont_corp_name,String address) {
        memployee=employee;
        musrname=usrname;
        mcont_type=cont_type;
        mcont_corp_name=cont_corp_name;
        maddress=address;

    }

    public  String getMemployee(){
        return memployee;
    }
    public String getMusrname(){
        return musrname;
    }
    public String getMcont_type(){
        return mcont_type;
    }
    public String getMcont_corp_name(){
        return mcont_corp_name;
    }

    public String getMaddress() {
        return maddress;
    }
}
