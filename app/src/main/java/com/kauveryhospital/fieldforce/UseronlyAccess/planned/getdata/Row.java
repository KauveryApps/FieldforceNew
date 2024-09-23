package com.kauveryhospital.fieldforce.UseronlyAccess.planned.getdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Row implements Serializable
{

    @SerializedName("visitplanhdrid")
    @Expose
    private String visitplanhdrid;
    @SerializedName("visitplandtlrow")
    @Expose
    private String visitplandtlrow;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("cusid")
    @Expose
    private String cusid;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("visitstatus")
    @Expose
    private String visitstatus;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("visitdate")
    @Expose
    private String visitdate;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("employee")
    @Expose
    private String employee;
    @SerializedName("unplanvisitid")
    @Expose
    private String unplanvisitid;
    @SerializedName("checkin")
    @Expose
    private String checkin;
    @SerializedName("portfolio")
    @Expose
    private String portfolio;
    @SerializedName("specialization")
    @Expose
    private String specialization;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("checkouttime")
    @Expose
    private String checkouttime;
    @SerializedName("sdate")
    @Expose
    private String sdate;
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("dupchk")
    @Expose
    private String dupchk;
    @SerializedName("contype")
    @Expose
    private String contype;
    @SerializedName("jointcall")
    @Expose
    private String jointcall;
    @SerializedName("c_latitude")
    @Expose
    private String cLatitude;
    @SerializedName("c_longitude")
    @Expose
    private String cLongitude;
    @SerializedName("visit_purpose")
    @Expose
    private String visitPurpose;
    @SerializedName("chout")
    @Expose
    private String chout;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    @SerializedName("cnt")
    @Expose
    private  String cnt;
    @SerializedName("customer")
    @Expose
    private String customer;
    private final static long serialVersionUID = -1115710044781218643L;

    public String getVisitplanhdrid() {
        return visitplanhdrid;
    }

    public void setVisitplanhdrid(String visitplanhdrid) {
        this.visitplanhdrid = visitplanhdrid;
    }

    public String getVisitplandtlrow() {
        return visitplandtlrow;
    }

    public void setVisitplandtlrow(String visitplandtlrow) {
        this.visitplandtlrow = visitplandtlrow;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVisitstatus() {
        return visitstatus;
    }

    public void setVisitstatus(String visitstatus) {
        this.visitstatus = visitstatus;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getUnplanvisitid() {
        return unplanvisitid;
    }

    public void setUnplanvisitid(String unplanvisitid) {
        this.unplanvisitid = unplanvisitid;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckouttime() {
        return checkouttime;
    }

    public void setCheckouttime(String checkouttime) {
        this.checkouttime = checkouttime;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getDupchk() {
        return dupchk;
    }

    public void setDupchk(String dupchk) {
        this.dupchk = dupchk;
    }

    public String getContype() {
        return contype;
    }

    public void setContype(String contype) {
        this.contype = contype;
    }

    public String getJointcall() {
        return jointcall;
    }

    public void setJointcall(String jointcall) {
        this.jointcall = jointcall;
    }

    public String getCLatitude() {
        return cLatitude;
    }

    public void setCLatitude(String cLatitude) {
        this.cLatitude = cLatitude;
    }

    public String getCLongitude() {
        return cLongitude;
    }

    public void setCLongitude(String cLongitude) {
        this.cLongitude = cLongitude;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    public String getChout() {
        return chout;
    }

    public void setChout(String chout) {
        this.chout = chout;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
   public String getCnt(){
        return  cnt;
   }
   public void setCnt(String cnt){
        this.cnt=cnt;
   }
   public  String getCustomer(){
        return  customer;
   }
   public void setCustomer(String customer){
        this.customer=customer;
   }
}
