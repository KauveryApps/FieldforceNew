package com.kauveryhospital.fieldforce.workstartserviceuseronly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Constants for Database name, table name, and column names
    public static final String DB_NAME = "Offlinecheckin";
    public static final String TABLE_NAME = "checkin";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_CUSTOMER= "customer";
    public static final String COLUMN_CHECKINTIME="checkintime";
    public static final String COLUMN_CHECKOUTTIMESTATUS="checkouttimestatus";
    public static final String COLUMN_SYSTEMID="systemid";
    public static final String COLUMN_LATITUDE="latitude";
    public static final String COLUMN_LONGITUDE="longitude";
    public static final String COLUMN_ADDRESS="address";
    public static final String COLUMN_EMPLOYEE="employee";
    public static final String COLUMN_VISITPURPOSE="visitpurpose";
    public static final String COLUMN_STATUS = "status";


    public static final String TABLE_NAMECHECKOUT = "checkout";
    public static final String COLUMN_IDOUT = "idout";
    public static final String COLUMN_CONTACTOUT = "contactout";
    public static final String COLUMN_CUSTOMEROUT= "customerout";
    public static final String COLUMN_CHECKINTIMEOUT="checkintimeout";
    public static final String COLUMN_CHECKOUTTIMEOUT="checkouttimeout";
    public static final String COLUMN_SYSTEMIDOUT= "systemidout";
    public static final String COLUMN_LATITUDEOUT="latitudeout";
    public static final String COLUMN_LONGITUDEOUT="longitudeout";
    public static final String COLUMN_ADDRESSOUT="addressout";
    public static final String COLUMN_EMPLOYEEOUT="employeeout";
    public static final String COLUMN_VISITPURPOSEOUT="visitpurposeout";
    public static final String COLUMN_STATUSOUT = "statusout";
    private Context appContext;
    //database version
    private static final int DB_VERSION = 3;
    String sqls = "DROP TABLE IF EXISTS checkin";
    String sqls1 = "DROP TABLE IF EXISTS checkout";
    //Constructor
    private static final  String sql1 = "CREATE TABLE " + TABLE_NAMECHECKOUT + "(" + COLUMN_IDOUT + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CONTACTOUT + " VARCHAR," + COLUMN_CUSTOMEROUT + " VARCHAR ," + COLUMN_CHECKINTIMEOUT + " VARCHAR , " + COLUMN_CHECKOUTTIMEOUT + " VARCHAR ," + COLUMN_SYSTEMIDOUT + " VARCHAR ," + COLUMN_LATITUDEOUT + " VARCHAR," + COLUMN_LONGITUDEOUT + " VARCHAR , " + COLUMN_ADDRESSOUT + " VARCHAR ," + COLUMN_EMPLOYEEOUT +" VARCHAR ," + COLUMN_VISITPURPOSEOUT + " VARCHAR," + COLUMN_STATUSOUT + " TINYINT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CONTACT + " VARCHAR," + COLUMN_CUSTOMER + " VARCHAR ," + COLUMN_CHECKINTIME + " VARCHAR , " + COLUMN_CHECKOUTTIMESTATUS + " VARCHAR ," + COLUMN_SYSTEMID + " VARCHAR ," + COLUMN_LATITUDE + " VARCHAR," + COLUMN_LONGITUDE + " VARCHAR , " + COLUMN_ADDRESS + " VARCHAR ," + COLUMN_EMPLOYEE +" VARCHAR ," + COLUMN_VISITPURPOSE + " VARCHAR," + COLUMN_STATUS + " TINYINT);";
        db.execSQL(sql);
        db.execSQL(sql1);
    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(sqls);
        db.execSQL(sqls1);
        onCreate(db);
    }

    public boolean addName(String contact, String customer, String checkintime, String checkouttimestatus,String systemid, String latitude, String longitude, String address, String employee,String visitpurpose, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CONTACT, contact);
        contentValues.put(COLUMN_CUSTOMER,customer);
        contentValues.put(COLUMN_CHECKINTIME,checkintime);
        contentValues.put(COLUMN_CHECKOUTTIMESTATUS,checkouttimestatus);
        contentValues.put(COLUMN_SYSTEMID,systemid);
        contentValues.put(COLUMN_LATITUDE,latitude);
        contentValues.put(COLUMN_LONGITUDE,longitude);
        contentValues.put(COLUMN_ADDRESS,address);
        contentValues.put(COLUMN_EMPLOYEE,employee);
        contentValues.put(COLUMN_VISITPURPOSE,visitpurpose);
        contentValues.put(COLUMN_STATUS, status);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }


    public boolean addNamechkout(String contactout, String customerout, String checkintimeout, String checkouttimeout,String systemidout, String latitudeout, String longitudeout, String addressout, String employeeout,String visitpurposeout, int statusout) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CONTACT, contactout);
        contentValues.put(COLUMN_CUSTOMER,customerout);
        contentValues.put(COLUMN_CHECKINTIME,checkintimeout);
        contentValues.put(COLUMN_CHECKOUTTIMEOUT,checkouttimeout);
        contentValues.put(COLUMN_SYSTEMID,systemidout);

        contentValues.put(COLUMN_LATITUDE,latitudeout);
        contentValues.put(COLUMN_LONGITUDE,longitudeout);
        contentValues.put(COLUMN_ADDRESS,addressout);
        contentValues.put(COLUMN_EMPLOYEE,employeeout);
        contentValues.put(COLUMN_VISITPURPOSE,visitpurposeout);
        contentValues.put(COLUMN_STATUS, statusout);
        db.insert(TABLE_NAMECHECKOUT, null, contentValues);
        db.close();
        return true;
    }
    /*
     * This method taking two arguments
     * first one is the id of the name for which
     * we have to update the sync status
     * and the second one is the status that will be changed
     * */
    public boolean updateNameStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }
    public boolean updateNameStatus1(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_NAMECHECKOUT, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }
    public Cursor getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " ASC;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getchkinNames(String employee) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMPLOYEE + " = "+employee+";";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /*
     * this method is for getting all the unsynced name
     * so that we can sync it with database
     * */

    public Cursor getUnsyncedNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getUnsyncedNames2() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAMECHECKOUT + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getUnsyncedNames1()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAMECHECKOUT;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public Cursor getUnsyncedcheckout() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAMECHECKOUT ;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

}