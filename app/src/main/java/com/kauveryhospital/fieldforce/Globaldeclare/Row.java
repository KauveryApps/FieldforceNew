package com.kauveryhospital.fieldforce.Globaldeclare;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Row implements Serializable
{

    @SerializedName("state_name")
    @Expose
    private String state_name;

    @SerializedName("stateid")
    @Expose
    private String stateid;

     @SerializedName("cityid")
     @Expose
     private String cityid;

     @SerializedName("city_name")
     @Expose
     private String city_name;

     @SerializedName("area_name")
     @Expose
     private  String area_name;

     @SerializedName("area_masterid")
     @Expose
     private String area_masterid;

     @SerializedName("pin_code")
     @Expose
     private  String pin_code;

     @SerializedName("pincodeid")
     @Expose
     private  String pincodeid;

     @SerializedName("dt")
     @Expose
     private  String dt;

     @SerializedName("title")
     @Expose
     private String title;

     @SerializedName("corporateid")
     @Expose
     private String corporateid;

     @SerializedName("name")
     @Expose
     private  String name;

     @SerializedName("visitpurpose")
     @Expose
     private String visitpurpose;

     @SerializedName("contactname")
     @Expose
     private String contactname;

     @SerializedName("employee")
     @Expose
     private String employee;

     @SerializedName("unplanvisitid")
     @Expose
     private String unplanvisitid;

     @SerializedName("checkin")
     @Expose
     private String checkin;



    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("area")
    @Expose
    private String area;

    @SerializedName("pincode")
    @Expose
    private String pincode;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("contype")
    @Expose
    private  String contype;

    @SerializedName("salutation")
    @Expose
    private String salutation;



    @SerializedName("corporate")
    @Expose
    private String corporate;
    @SerializedName("ambulance")
    @Expose
    private String ambulance;

    @SerializedName("specialization")
    @Expose
    private String specialization;



    @SerializedName("portfolio")
    @Expose
    private  String portfolio;

    @SerializedName("phone")
    @Expose
    private  String phone;

    @SerializedName("uname")
    @Expose
    private String uname;

    @SerializedName("reasonforleave")
    @Expose
    private String reasonforleave;

    @SerializedName("fromdate")
    @Expose
    private  String fromdate;

    @SerializedName("todate")
    @Expose
    private String todate;

    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("employeeid")
    @Expose
    private String employeeid;

    @SerializedName("la_leaveid")
    @Expose
    private String la_leaveid;

    @SerializedName("createdon")
    @Expose
    private  String createdon;

    @SerializedName("visitdate")
    @Expose
    private String visitdate;

    @SerializedName("purpose")
    @Expose
    private String purpose;

@SerializedName("customer")
@Expose
private String customer;

    @SerializedName("fromsdate")
    @Expose
    private String fromsdate;
    @SerializedName("tosdate")
    @Expose
    private String tosdate;

    @SerializedName("cont_corpid")
    @Expose
    private String cont_corpid;

    @SerializedName("checkouttime")
@Expose
    private String checkouttime;
    @SerializedName("cont_corp_name")
    @Expose
    private  String cont_corp_name;
    @SerializedName("contact_name")
    @Expose
    private  String contact_name;
    @SerializedName("customername")
    @Expose
    private  String customername;

    @SerializedName("jointcall1")
    @Expose
    private String jointcall1;

    @SerializedName("jointcall2")
    @Expose
    private String jointcall2;

    @SerializedName("jointcall3")
    @Expose
    private String jointcall3;
    @SerializedName("checkintime")
    @Expose
    private  String checkintime;

    public String getCheckintime() {
        return checkintime;
    }

    public void setCheckintime(String checkintime) {
        this.checkintime = checkintime;
    }

    private final static long serialVersionUID = -4685829478022589245L;

    public String getJointcall1() {
        return jointcall1;
    }

    public void setJointcall1(String jointcall1) {
        this.jointcall1 = jointcall1;
    }

    public String getJointcall2() {
        return jointcall2;
    }

    public void setJointcall2(String jointcall2) {
        this.jointcall2 = jointcall2;
    }

    public String getJointcall3() {
        return jointcall3;
    }

    public void setJointcall3(String jointcall3) {
        this.jointcall3 = jointcall3;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getstate_name() {
        return state_name;
    }

    public void setstate_name(String state_name) {
        this.state_name = state_name;
    }

    public String getstateid() {
        return stateid;
    }

    public void setstateid(String stateid) {
        this.stateid = stateid;
    }

   public String getCityid(){
        return cityid;
   }
   public void setCityid(String cityid){
        this.cityid=cityid;
   }
  public  String getCity_name(){
        return city_name;
  }
  public void setCity_name(String city_name){
        this.city_name=city_name;
  }
  public  String getArea_name(){
        return  area_name;
  }
  public  void setArea_name(String area_name){
        this.area_name=area_name;
  }
  public String getArea_masterid(){
        return  area_masterid;

  }
  public  void setArea_masterid(String area_masterid)
  {
      this.area_masterid=area_masterid;
  }
public  String getPin_code(){
        return  pin_code;

}
public  void setPin_code(String pin_code){
        this.pin_code=pin_code;
}
public  String getPincodeid(){
        return  pincodeid;
}
public void setPincodeid(String pincodeid){
        this.pincodeid=pincodeid;
}
    public  String getDt(){
        return  dt;
    }
    public void setDt(String dt){
        this.dt=dt;
    }
    public String getTitle(){
        return  title;
    }
    public  void setTitle(String title){
        this.title=title;
    }
    public String getCorporateid(){
        return  corporateid;
    }
    public  void setCorporateid(String corporateid){
        this.corporateid=corporateid;
    }
    public String getName(){
        return  name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getVisitpurpose(){
        return visitpurpose;
    }
    public void  setVisitpurpose(String visitpurpose){
        this.visitpurpose=visitpurpose;
    }

    public String getEmployee(){
        return  employee;
    }
    public  void setEmployee(String employee){
        this.employee=employee;
    }
    public  String getunplanvisitid(){
        return  unplanvisitid;
    }
    public  void setUnplanvisitid(String unplanvisitid){
        this.unplanvisitid=unplanvisitid;
    }
    public String getCheckin(){
        return  checkin;
    }
    public void setCheckin(String checkin){
        this.checkin=checkin;
    }

    public String getState(){
        return state;
    }
    public void setState(String state ){
        this.state=state;
    }

    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city=city;
    }

    public String getArea(){
        return area;
    }
    public void setArea(String area ){
        this.area=area;
    }

    public String getPincode(){
        return pincode;
    }
    public void setPincode(String pincode){
        this.pincode=pincode;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public  String getContype(){
        return contype ;
    }
    public void setContype(String contype){
        this.contype=contype;
    }
    public  String getSalutation(){
        return  salutation;
    }
    public void setSalutation(String salutation){
        this.salutation=salutation;
    }
    public  String getContactname(){
        return contactname ;
    }
    public void setContactname(String contactname){
        this.contactname=contactname;
    }
    public  String getCorporate(){
        return  corporate;
    }
    public void setCorporate(String corporate){
        this.corporate=corporate;

    }

    public  String getAmbulance(){
        return ambulance ;
    }
    public void setAmbulance(String ambulance){
        this.ambulance=ambulance;
    }
    public  String getSpecialization(){
        return  specialization;
    }
    public void setSpecialization(String specialization){
        this.specialization=specialization;
    }
    public  String getPortfolio(){
        return  portfolio;
    }
    public void setPortfolio(String portfolio){
        this.portfolio=portfolio;
    }
    public  String getPhone(){
        return phone ;
    }
    public void setPhone(String phone){
        this.phone=phone;
    }
public  String getuname(){
        return  uname;
}
public  void setUname(String uname){
        this.uname=uname;
}


    public  String getReasonforleave(){
        return  reasonforleave;
    }
    public  void setReasonforleave(String reasonforleave){
        this.reasonforleave=reasonforleave;
    }


    public  String getFromdate(){
        return  fromdate;
    }
    public  void setFromdate(String fromdate){
        this.fromdate=fromdate;
    }


    public  String getTodate(){
        return  todate;
    }
    public  void setTodate(String todate){
        this.todate=todate;
    }


    public  String getStatus(){
        return  status;
    }
    public  void setStatus(String status){
        this.status=status;
    }


    public  String getEmployeeid(){
        return  employeeid;
    }
    public  void setEmployeeid(String employeeid){
        this.employeeid=employeeid;
    }
public String getLa_leaveid(){
        return  la_leaveid;
}
public void setLa_leaveid(String la_leaveid){
        this.la_leaveid=la_leaveid;
}

public String getCreatedon(){
        return createdon;
}
public void setCreatedon(String createdon){
        this.createdon=createdon;
}

public String getVisitdate(){
        return visitdate;
}
public  void setVisitdate(String visitdate){
        this.visitdate=visitdate;
}
public String getPurpose(){
        return  purpose;
}
public void  setPurpose(String purpose){
        this.purpose=purpose;

}

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getCustomer(){
        return  customer;
}
public void setCustomer(String customer){
        this.customer=customer;
}

    public String getFromsdate() {
        return fromsdate;
    }

    public void setFromsdate(String fromsdate) {
        this.fromsdate = fromsdate;
    }
    public String getTosdate() {
        return tosdate;
    }

    public void setTosdate(String tosdate) {
        this.tosdate = tosdate;
    }

    public String getCont_corpid(){
        return cont_corpid;
    }
    public  void setCont_corpid(String cont_corpid){
        this.cont_corpid=cont_corpid;
    }

    public String getCheckouttime(){
        return  checkouttime;
    }
    public  void setCheckouttime(String checkouttime){
        this.checkouttime=checkouttime;
    }

    public String getCont_corp_name() {
        return cont_corp_name;
    }

    public void setCont_corp_name(String cont_corp_name) {
        this.cont_corp_name = cont_corp_name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}