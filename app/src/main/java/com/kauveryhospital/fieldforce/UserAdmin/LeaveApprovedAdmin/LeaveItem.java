package com.kauveryhospital.fieldforce.UserAdmin.LeaveApprovedAdmin;

public class LeaveItem {
    private String mnames,mfromdate,mtodate,mstatus,memployeeid,mla_leaveid,mcancelremarks;
    boolean isSelected;
    public LeaveItem(String unames, String fromdate, String todate, String status, String employeeid, String la_leaveid, String cancelremarks, boolean isSelected, String uname, String pswd) {
        this.mnames=unames;
        this.mfromdate=fromdate;
        this.mtodate=todate;
        this.mstatus=status;
        this.memployeeid=employeeid;
        this.mla_leaveid=la_leaveid;
        this.mcancelremarks=cancelremarks;
        this.isSelected = isSelected;
    }
    public String getMnames(){
        return mnames;
    }
    public String getMfromdate(){
        return  mfromdate;
    }
    public String getMtodate(){
        return mtodate;
    }
    public String getMstatus(){
        return mstatus;
    }
    public  String getMemployeeid(){
        return  memployeeid;
    }
    public String getMla_leaveid(){
        return mla_leaveid;
    }
    public String getMcancelremarks(){
        return  mcancelremarks;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
