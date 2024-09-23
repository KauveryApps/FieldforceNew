package com.kauveryhospital.fieldforce.UseronlyAccess.MyAgendaToday;

public class CheckInItem {
    String mcustomer,mstatus_cl, mportfolio,mcont_corpid,mpurpose,mvisit_purposeid,mpriority,mvisitdate,mcontact_type,mcustomer_name,mcorporate,maddress,mjointcall1,mvisitplandtlid,mcusid;


    public CheckInItem(String contact_type, String cont_corpid, String corporate, String portfolio, String visitdate, String status_cl) {
        mcontact_type=contact_type;
        mcont_corpid=cont_corpid;
        mcorporate=corporate;
        mportfolio=portfolio;
        mvisitdate=visitdate;
        mstatus_cl=status_cl;
    }



    public String getMcontact_type() {
        return mcontact_type;
    }

    public String getMcont_corpid() {
        return mcont_corpid;
    }

    public String getMcorporate() {
        return mcorporate;
    }

    public String getMportfolio() {
        return mportfolio;
    }


    public String getMvisitdate() {
        return mvisitdate;
    }

    public String getMstatus_cl() {
        return mstatus_cl;
    }
}
