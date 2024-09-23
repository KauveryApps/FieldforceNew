package com.kauveryhospital.fieldforce.Loginscreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Result_ implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("ugroup")
    @Expose
    private String ugroup;
    @SerializedName("uroles")
    @Expose
    private String uroles;
    @SerializedName("USERNAME")
    @Expose
    private String uSERNAME;
    @SerializedName("USERGROUP")
    @Expose
    private String uSERGROUP;
    @SerializedName("GROUPNO")
    @Expose
    private String gROUPNO;
    @SerializedName("BUILD")
    @Expose
    private String bUILD;
    @SerializedName("MANAGE")
    @Expose
    private String mANAGE;
    @SerializedName("TOOLS")
    @Expose
    private String tOOLS;
    @SerializedName("EMAIL")
    @Expose
    private String eMAIL;
    @SerializedName("PAGEACCESS")
    @Expose
    private String pAGEACCESS;
    @SerializedName("ACTIVE")
    @Expose
    private String aCTIVE;
    @SerializedName("REPORTINGTO")
    @Expose
    private String rEPORTINGTO;
    @SerializedName("ISFIRSTTIME")
    @Expose
    private String iSFIRSTTIME;
    @SerializedName("WORKFLOW")
    @Expose
    private String wORKFLOW;
    @SerializedName("WEBSKIN")
    @Expose
    private String wEBSKIN;
    @SerializedName("SEND")
    @Expose
    private String sEND;
    @SerializedName("ACTFLAG")
    @Expose
    private String aCTFLAG;
    @SerializedName("LOGINTRY")
    @Expose
    private String lOGINTRY;
    @SerializedName("PWDEXPDAYS")
    @Expose
    private String pWDEXPDAYS;
    @SerializedName("IMPORTSTRUCT")
    @Expose
    private String iMPORTSTRUCT;
    @SerializedName("LANGUAGE_ACCESS")
    @Expose
    private String lANGUAGEACCESS;
    @SerializedName("LANGUAGE")
    @Expose
    private String lANGUAGE;
    @SerializedName("EXPORTSTRUCT")
    @Expose
    private String eXPORTSTRUCT;
    @SerializedName("AXPERTMANAGER")
    @Expose
    private String aXPERTMANAGER;
    @SerializedName("PWD")
    @Expose
    private String pWD;
    @SerializedName("ACCESSCODE")
    @Expose
    private String aCCESSCODE;
    @SerializedName("APPPARAMS")
    @Expose
    private String aPPPARAMS;
    @SerializedName("APPMANAGER")
    @Expose
    private String aPPMANAGER;
    @SerializedName("NICKNAME")
    @Expose
    private String nICKNAME;
    @SerializedName("USERTYPE")
    @Expose
    private String uSERTYPE;
    @SerializedName("MOBILE")
    @Expose
    private String mOBILE;
    @SerializedName("ALLUSERGROUP")
    @Expose
    private String aLLUSERGROUP;
    @SerializedName("GLOBAL_DISPLAYTEXT")
    @Expose
    private String gLOBALDISPLAYTEXT;
    @SerializedName("MCOMPANY")
    @Expose
    private String mCOMPANY;
    @SerializedName("MBRANCH")
    @Expose
    private String mBRANCH;
    @SerializedName("MGLOBALTEXT")
    @Expose
    private String mGLOBALTEXT;
    @SerializedName("DOCTOR_NAME")
    @Expose
    private String dOCTORNAME;
    @SerializedName("DEPARTMENT")
    @Expose
    private String dEPARTMENT;
    @SerializedName("APPID")
    @Expose
    private String aPPID;

    @SerializedName("IS_ADMIN")
    @Expose
    private String is_admin;
    @SerializedName("SUB_DEPARTMENT")
    @Expose
    private String sUBDEPARTMENT;
    @SerializedName("EMPCNAME")
    @Expose
    private String eMPCNAME;
    @SerializedName("EMPCODE")
    @Expose
    private String eMPCODE;
    @SerializedName("ISIP")
    @Expose
    private String iSIP;
    @SerializedName("IPADDR")
    @Expose
    private String iPADDR;
    @SerializedName("REPORT_DOWNLOAD")
    @Expose
    private String rEPORTDOWNLOAD;
    @SerializedName("DEBUG")
    @Expose
    private String dEBUG;
    @SerializedName("HOMEPAGE")
    @Expose
    private String hOMEPAGE;
    @SerializedName("globalvars")
    @Expose
    private Globalvars globalvars;
    @SerializedName("uservars")
    @Expose
    private Uservars uservars;
    @SerializedName("axmmenu")
    @Expose
    private Axmmenu axmmenu;

    @SerializedName("IS_OHC")
    @Expose
    private  String is_ohc;

    @SerializedName("LOCATION")
    @Expose
    private String location;

    @SerializedName("IMEI_NO")
    @Expose
    private String imei_no;
    private final static long serialVersionUID = 2019859486778221660L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Result_() {
    }

    public String getImei_no() {
        return imei_no;
    }

    public void setImei_no(String imei_no) {
        this.imei_no = imei_no;
    }

    public  String getIs_admin(){
        return is_admin;
}
public void setIs_admin(String is_admin){
        this.is_admin=is_admin;
}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public  String getIs_ohc(){
        return  is_ohc;
}
public void setIs_ohc(String is_ohc){
        this.is_ohc=is_ohc;
}
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getUgroup() {
        return ugroup;
    }

    public void setUgroup(String ugroup) {
        this.ugroup = ugroup;
    }

    public String getUroles() {
        return uroles;
    }

    public void setUroles(String uroles) {
        this.uroles = uroles;
    }

    public String getUSERNAME() {
        return uSERNAME;
    }

    public void setUSERNAME(String uSERNAME) {
        this.uSERNAME = uSERNAME;
    }

    public String getUSERGROUP() {
        return uSERGROUP;
    }

    public void setUSERGROUP(String uSERGROUP) {
        this.uSERGROUP = uSERGROUP;
    }

    public String getGROUPNO() {
        return gROUPNO;
    }

    public void setGROUPNO(String gROUPNO) {
        this.gROUPNO = gROUPNO;
    }

    public String getBUILD() {
        return bUILD;
    }

    public void setBUILD(String bUILD) {
        this.bUILD = bUILD;
    }

    public String getMANAGE() {
        return mANAGE;
    }

    public void setMANAGE(String mANAGE) {
        this.mANAGE = mANAGE;
    }

    public String getTOOLS() {
        return tOOLS;
    }

    public void setTOOLS(String tOOLS) {
        this.tOOLS = tOOLS;
    }

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getPAGEACCESS() {
        return pAGEACCESS;
    }

    public void setPAGEACCESS(String pAGEACCESS) {
        this.pAGEACCESS = pAGEACCESS;
    }

    public String getACTIVE() {
        return aCTIVE;
    }

    public void setACTIVE(String aCTIVE) {
        this.aCTIVE = aCTIVE;
    }

    public String getREPORTINGTO() {
        return rEPORTINGTO;
    }

    public void setREPORTINGTO(String rEPORTINGTO) {
        this.rEPORTINGTO = rEPORTINGTO;
    }

    public String getISFIRSTTIME() {
        return iSFIRSTTIME;
    }

    public void setISFIRSTTIME(String iSFIRSTTIME) {
        this.iSFIRSTTIME = iSFIRSTTIME;
    }

    public String getWORKFLOW() {
        return wORKFLOW;
    }

    public void setWORKFLOW(String wORKFLOW) {
        this.wORKFLOW = wORKFLOW;
    }

    public String getWEBSKIN() {
        return wEBSKIN;
    }

    public void setWEBSKIN(String wEBSKIN) {
        this.wEBSKIN = wEBSKIN;
    }

    public String getSEND() {
        return sEND;
    }

    public void setSEND(String sEND) {
        this.sEND = sEND;
    }

    public String getACTFLAG() {
        return aCTFLAG;
    }

    public void setACTFLAG(String aCTFLAG) {
        this.aCTFLAG = aCTFLAG;
    }

    public String getLOGINTRY() {
        return lOGINTRY;
    }

    public void setLOGINTRY(String lOGINTRY) {
        this.lOGINTRY = lOGINTRY;
    }

    public String getPWDEXPDAYS() {
        return pWDEXPDAYS;
    }

    public void setPWDEXPDAYS(String pWDEXPDAYS) {
        this.pWDEXPDAYS = pWDEXPDAYS;
    }

    public String getIMPORTSTRUCT() {
        return iMPORTSTRUCT;
    }

    public void setIMPORTSTRUCT(String iMPORTSTRUCT) {
        this.iMPORTSTRUCT = iMPORTSTRUCT;
    }

    public String getLANGUAGEACCESS() {
        return lANGUAGEACCESS;
    }

    public void setLANGUAGEACCESS(String lANGUAGEACCESS) {
        this.lANGUAGEACCESS = lANGUAGEACCESS;
    }

    public String getLANGUAGE() {
        return lANGUAGE;
    }

    public void setLANGUAGE(String lANGUAGE) {
        this.lANGUAGE = lANGUAGE;
    }

    public String getEXPORTSTRUCT() {
        return eXPORTSTRUCT;
    }

    public void setEXPORTSTRUCT(String eXPORTSTRUCT) {
        this.eXPORTSTRUCT = eXPORTSTRUCT;
    }

    public String getAXPERTMANAGER() {
        return aXPERTMANAGER;
    }

    public void setAXPERTMANAGER(String aXPERTMANAGER) {
        this.aXPERTMANAGER = aXPERTMANAGER;
    }

    public String getPWD() {
        return pWD;
    }

    public void setPWD(String pWD) {
        this.pWD = pWD;
    }

    public String getACCESSCODE() {
        return aCCESSCODE;
    }

    public void setACCESSCODE(String aCCESSCODE) {
        this.aCCESSCODE = aCCESSCODE;
    }

    public String getAPPPARAMS() {
        return aPPPARAMS;
    }

    public void setAPPPARAMS(String aPPPARAMS) {
        this.aPPPARAMS = aPPPARAMS;
    }

    public String getAPPMANAGER() {
        return aPPMANAGER;
    }

    public void setAPPMANAGER(String aPPMANAGER) {
        this.aPPMANAGER = aPPMANAGER;
    }

    public String getNICKNAME() {
        return nICKNAME;
    }

    public void setNICKNAME(String nICKNAME) {
        this.nICKNAME = nICKNAME;
    }

    public String getUSERTYPE() {
        return uSERTYPE;
    }

    public void setUSERTYPE(String uSERTYPE) {
        this.uSERTYPE = uSERTYPE;
    }

    public String getMOBILE() {
        return mOBILE;
    }

    public void setMOBILE(String mOBILE) {
        this.mOBILE = mOBILE;
    }

    public String getALLUSERGROUP() {
        return aLLUSERGROUP;
    }

    public void setALLUSERGROUP(String aLLUSERGROUP) {
        this.aLLUSERGROUP = aLLUSERGROUP;
    }

    public String getGLOBALDISPLAYTEXT() {
        return gLOBALDISPLAYTEXT;
    }

    public void setGLOBALDISPLAYTEXT(String gLOBALDISPLAYTEXT) {
        this.gLOBALDISPLAYTEXT = gLOBALDISPLAYTEXT;
    }

    public String getMCOMPANY() {
        return mCOMPANY;
    }

    public void setMCOMPANY(String mCOMPANY) {
        this.mCOMPANY = mCOMPANY;
    }

    public String getMBRANCH() {
        return mBRANCH;
    }

    public void setMBRANCH(String mBRANCH) {
        this.mBRANCH = mBRANCH;
    }

    public String getMGLOBALTEXT() {
        return mGLOBALTEXT;
    }

    public void setMGLOBALTEXT(String mGLOBALTEXT) {
        this.mGLOBALTEXT = mGLOBALTEXT;
    }

    public String getDOCTORNAME() {
        return dOCTORNAME;
    }

    public void setDOCTORNAME(String dOCTORNAME) {
        this.dOCTORNAME = dOCTORNAME;
    }

    public String getDEPARTMENT() {
        return dEPARTMENT;
    }

    public void setDEPARTMENT(String dEPARTMENT) {
        this.dEPARTMENT = dEPARTMENT;
    }

    public String getAPPID() {
        return aPPID;
    }

    public void setAPPID(String aPPID) {
        this.aPPID = aPPID;
    }

    public String getSUBDEPARTMENT() {
        return sUBDEPARTMENT;
    }

    public void setSUBDEPARTMENT(String sUBDEPARTMENT) {
        this.sUBDEPARTMENT = sUBDEPARTMENT;
    }

    public String getEMPCNAME() {
        return eMPCNAME;
    }

    public void setEMPCNAME(String eMPCNAME) {
        this.eMPCNAME = eMPCNAME;
    }

    public String getEMPCODE() {
        return eMPCODE;
    }

    public void setEMPCODE(String eMPCODE) {
        this.eMPCODE = eMPCODE;
    }

    public String getISIP() {
        return iSIP;
    }

    public void setISIP(String iSIP) {
        this.iSIP = iSIP;
    }

    public String getIPADDR() {
        return iPADDR;
    }

    public void setIPADDR(String iPADDR) {
        this.iPADDR = iPADDR;
    }

    public String getREPORTDOWNLOAD() {
        return rEPORTDOWNLOAD;
    }

    public void setREPORTDOWNLOAD(String rEPORTDOWNLOAD) {
        this.rEPORTDOWNLOAD = rEPORTDOWNLOAD;
    }

    public String getDEBUG() {
        return dEBUG;
    }

    public void setDEBUG(String dEBUG) {
        this.dEBUG = dEBUG;
    }

    public String getHOMEPAGE() {
        return hOMEPAGE;
    }

    public void setHOMEPAGE(String hOMEPAGE) {
        this.hOMEPAGE = hOMEPAGE;
    }

    public Globalvars getGlobalvars() {
        return globalvars;
    }

    public void setGlobalvars(Globalvars globalvars) {
        this.globalvars = globalvars;
    }

    public Uservars getUservars() {
        return uservars;
    }

    public void setUservars(Uservars uservars) {
        this.uservars = uservars;
    }

    public Axmmenu getAxmmenu() {
        return axmmenu;
    }

    public void setAxmmenu(Axmmenu axmmenu) {
        this.axmmenu = axmmenu;
    }


}
