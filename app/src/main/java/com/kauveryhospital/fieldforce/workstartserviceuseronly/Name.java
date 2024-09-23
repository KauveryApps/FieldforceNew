package com.kauveryhospital.fieldforce.workstartserviceuseronly;

public class Name {
    private String contact,customer,checkintime,latitude,longitude,address,visitpurpose,checkouttimestatus,systemid,employee;
    private  String contactout,customerout,checkintimeout,checkouttimeout,systemidout,latitudeout,longitudeout,addressout,employeeout,visitpurposeout,visitpurposeout1;
    private int status;

    public Name(String contact, String customer, String checkintime, String checkouttimestatus, String systemid, String latitude, String longitude, String address, String employee, String visitpurpose, int status) {
        this.contact = contact;
        this.customer=customer;
        this.checkintime=checkintime;
        this.checkouttimestatus=checkouttimestatus;
        this.systemid=systemid;
        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;
        this.employee=employee;
        this.visitpurpose=visitpurpose;
        this.status = status;
    }

    public Name(String contactout, String customerout, String checkintimeout, String checkouttimeout, String systemidout, String latitudeout, String longitudeout, String addressout, String employeeout, String visitpurposeout, String visitpurposeout1, int status) {
    this.contactout=contactout;
    this.customerout=customerout;
    this.checkintimeout=checkintimeout;
    this.checkouttimeout=checkouttimeout;
    this.systemidout=systemidout;
    this.latitudeout=latitudeout;
    this.longitudeout=longitudeout;
    this.addressout=addressout;
    this.employeeout=employeeout;
    this.visitpurposeout=visitpurposeout;
    this.visitpurposeout1=visitpurposeout1;
    this.status=status;
    }

    public String getName() {
        return contact;
    }

    public int getStatus() {
        return status;
    }

    public String getCustomer() {
        return customer;
    }
    public  String getCheckouttimestatus(){
        return checkouttimestatus;
    }
    public String getSystemid(){
        return  systemid;
    }
    public String getCheckintime(){
        return  checkintime;
    }
    public String getEmployee(){
        return employee;
    }
    public  String getVisitpurpose(){
        return  visitpurpose;
    }
    public  String getAddress(){
        return address;
    }
    public String getContactout(){
        return  contactout;
    }
    public  String getCustomerout(){
        return customerout;
    }
    public  String getCheckintimeout(){
        return  checkintimeout;
    }
    public  String getCheckouttimeout(){
        return  checkouttimeout;
    }
    public String getSystemidout(){
        return  systemidout;
    }
    public  String getLatitudeout(){
        return  latitudeout;
    }
    public String getLongitudeout(){
        return  longitudeout;
    }
    public  String getAddressout(){
        return addressout;
    }
    public  String getEmployeeout(){
        return employeeout;
    }
    public String getVisitpurposeout(){
        return visitpurposeout;
    }

}
