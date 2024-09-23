package com.kauveryhospital.fieldforce.UseronlyAccess.Globaldeclareadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rowadmin implements Serializable
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

     @SerializedName("name")
     @Expose
     private String name;

    @SerializedName("address")
    @Expose
    private String address;

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

    @SerializedName("active")
    @Expose
    private String active;

    @SerializedName("contype")
    @Expose
    private  String contype;

    @SerializedName("salutation")
    @Expose
    private String salutation;

    @SerializedName("contactname")
    @Expose
    private String contactname;

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

    @SerializedName("cont_corpid")
    @Expose
    private  String cont_corpid;

    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;


    @SerializedName("username")
    @Expose
    private  String username;

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

    @SerializedName("reasonforleave")
    @Expose
    private String reasonforleave;

    @SerializedName("uname")
    @Expose
    private String uname;

    @SerializedName("employee")
    @Expose
    private String employee;

    @SerializedName("visitdate")
    @Expose
    private String visitdate;

    @SerializedName("checkin")
    @Expose
    private String checkin;

    @SerializedName("checkouttime")
    @Expose
    private  String checkouttime;

    @SerializedName("cancelremarks")
    @Expose
    private String cancelremarks;

@SerializedName("nickname")
@Expose
private  String nickname;
 @SerializedName("createdon")
 @Expose
 private  String createdon;
    private final static long serialVersionUID = -4685829478022589245L;

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

    public String getName(){
        return  name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
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

    public String getActive(){
        return active;
    }
    public void setActive(String active ){
        this.active=active;
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

    public String getCont_corpid(){
        return  cont_corpid;
    }
    public void setCont_corpid(String cont_corpid){
        this.cont_corpid=cont_corpid;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUsername(){
        return  username;

    }
    public void setUsername(String username){
        this.username=username;
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

    public String getEmployee(){
        return  employee;
    }
    public  void setEmployee(String employee){
        this.employee=employee;
    }
    public  String getVisitdate(){
        return  visitdate;
    }
    public void setVisitdate(String visitdate){
        this.visitdate=visitdate;
    }
    public String getCheckin(){
        return  checkin;
    }
    public  void setCheckin(String checkin){
        this.checkin=checkin;
    }
    public  String getCheckouttime(){
        return checkouttime;
    }
    public void setCheckouttime(String checkouttime){
        this.checkouttime=checkouttime;
    }

    public String getRemarks(){
        return  remarks;
    }
    public  void  setRemarks(String remarks){
        this.remarks=remarks;
    }

    public String getCancelremarks(){
        return  cancelremarks;
    }
    public  void setCancelremarks(String cancelremarks){
        this.cancelremarks=cancelremarks;
    }

    public String getNickname(){
        return  nickname;
    }
    public  void setNickname(String nickname){
        this.nickname=nickname;
    }

    public String getCreatedon(){
        return  createdon;
    }
    public  void setCreatedon(String createdon){
        this.createdon=createdon;
    }
}