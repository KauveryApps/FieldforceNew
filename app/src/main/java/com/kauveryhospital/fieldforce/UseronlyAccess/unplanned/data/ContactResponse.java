package com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.data;

import java.util.List;

public class
ContactResponse {
    private String id;
    private String name;
    private String empname,visitdate,address,customer,checkin,checkouttime,remarks,visit_purpose;
    private List<PhoneNumber> phoneNumberList;
   /* // Constructors
    public ContactResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }*/
    public ContactResponse(String empname, String visitdate, String address, String customer, String checkin, String checkouttime,String remarks,String visit_purpose) {
        this.empname=empname;
        this.visitdate=visitdate;
        this.address=address;
        this.customer=customer;
        this.checkin=checkin;
        this.checkouttime=checkouttime;
        this.remarks=remarks;
        this.visit_purpose=visit_purpose;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getVisit_purpose() {
        return visit_purpose;
    }

    public String getAddress() {
        return address;
    }

    public String getEmpname() {
        return empname;
    }

    public String getVisitdate() {
        return visitdate;
    }

    public String getCustomer() {
        return customer;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckouttime() {
        return checkouttime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhoneNumber> getPhoneNumberList() {
        return phoneNumberList;
    }

    public void setPhoneNumberList(List<PhoneNumber> phoneNumberList) {
        this.phoneNumberList = phoneNumberList;
    }

    // Static Inner Class
    public static class PhoneNumber {
        private String number;

        // Constructor
        public PhoneNumber(String phone) {
            this.number = phone;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }

}
