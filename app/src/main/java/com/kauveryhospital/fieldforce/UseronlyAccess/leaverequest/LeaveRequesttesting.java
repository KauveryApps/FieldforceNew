package com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.Globaldeclare.APIClient;
import com.kauveryhospital.fieldforce.Globaldeclare.APIInterface;
import com.kauveryhospital.fieldforce.Globaldeclare.Example;
import com.kauveryhospital.fieldforce.Globaldeclare.Result;
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdata.APIClientPlanleave;
import com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdata.APIInterfacePlansleav;
import com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdata.Getcheckoutleave;
import com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata.APIInterfaceSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata.ResultSave;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveRequesttesting extends AppCompatActivity implements NetworkChangeCallback {
ImageView fromdate,todate;
TextView frmbtnDatePicker,tobtnDatePicker;
    DatePickerDialog mDatePicker;
    Calendar upDateFroms;
    List<ResultSave> listsave = new ArrayList<>();
    APIInterfaceSave apiInterfaceSave;
    APIInterfacePlansleav apiInterfaceplanleav;
    List<com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdata.Result> listleave = new ArrayList<com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdata.Result>();
    APIInterface apiInterface;
    List<Result> list = new ArrayList<>();
    Calendar upDateFrom;
    ImageView backarrow;
    EditText eaddress;
    int year,month,day,days,mDay;
    private NetworkChangeReceiver networkChangeReceiver;
    String mtoDate,message;
    Button savebtn, cancelbtn;
    String mfrmDate,reason,dayOfWeek,uname,pswd,tkfromsdate,tktosdate,alterdates,alterdates1;
    long mfrmDate1,tomindate,lngfromsdate,lngtosdate;
    public static final String PREFS_NAME = "loginpref";
    int fmonths = 0, fdd = 0, fyer = 0,tmonths=0,tdd=0,tyer=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_requesttesting);
        backarrow = findViewById(R.id.backarrow);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        fromdate=findViewById(R.id.fromdate);
        todate=findViewById(R.id.todate);
        frmbtnDatePicker=findViewById(R.id.frmbtnDatePicker);
        tobtnDatePicker=findViewById(R.id.tobtnDatePicker);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        eaddress = findViewById(R.id.eaddress);
        savebtn = findViewById(R.id.savebtn);
        cancelbtn = findViewById(R.id.cancelbtn);
        postdatastate();
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaveRequesttesting.this, TabbedActivity.class);
                startActivity(intent);
            }
        });
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lngfromsdate=convertToMillis2(fdd,fmonths,fyer);
                lngtosdate=convertToMillis1(tdd,tmonths,tyer);
                Calendar mcurrentDates = Calendar.getInstance();
                year = mcurrentDates.get(Calendar.YEAR);
                month = mcurrentDates.get(Calendar.MONTH);
                day = mcurrentDates.get(Calendar.DAY_OF_MONTH);
                mcurrentDates.set(Calendar.DAY_OF_MONTH, mDay);
                mDatePicker = new DatePickerDialog(LeaveRequesttesting.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        upDateFrom = Calendar.getInstance();
                        upDateFroms = Calendar.getInstance();
                        year = selectedyear;
                        month = selectedmonth;
                        day = selectedday;
                        upDateFrom.set(year, month, day);
                        mfrmDate = convertDate(convertToMillis(day, month, year));
                        tomindate=convertToMillis(day,month,year);
                        alterdates = convertDate1(convertToMillis(day, month, year));
                        frmbtnDatePicker.setText(mfrmDate);
                        Retrievingdata();
                    }

                }, year, month, day);

                mDatePicker.setTitle("Please select From Date");
                mDatePicker.getDatePicker().setMinDate(lngfromsdate);
                mDatePicker.getDatePicker().setMaxDate(lngtosdate);

                mDatePicker.show();

            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDates = Calendar.getInstance();
                year = mcurrentDates.get(Calendar.YEAR);
                month = mcurrentDates.get(Calendar.MONTH);
                day = mcurrentDates.get(Calendar.DAY_OF_MONTH);
                mcurrentDates.set(Calendar.DAY_OF_MONTH, mDay);
                mDatePicker = new DatePickerDialog(LeaveRequesttesting.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        year = selectedyear;
                        month = selectedmonth;
                        day = selectedday;


                        mtoDate = convertDate(convertToMillis(day, month, year));

                        alterdates1=convertDate1(convertToMillis(day, month, year));
                         alterdates=alterdates1;

                        tobtnDatePicker.setText(mtoDate);
                        Retrievingdata();
                    }

                }, year, month, day);

                mDatePicker.setTitle("Please select To Date");
                // TODO Hide Future Date Here
                mDatePicker.getDatePicker().setMinDate(tomindate);

                // TODO Hide Past Date Here
                mDatePicker.getDatePicker().setMaxDate(lngtosdate);
                mDatePicker.show();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();

            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaveRequesttesting.this, CancelRequestActivity.class);
                startActivity(intent);
            }
        });
    }
    private void validation() {

        if (isConnected()) {
            try {
                postData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please Check the Internet Connection!!", Toast.LENGTH_SHORT).show();
        }


    }
    public void postData() throws JSONException {
        savebtn.setEnabled(true);
        reason = eaddress.getText().toString();
        if (TextUtils.isEmpty(mfrmDate)) {
            frmbtnDatePicker.setError("Please Choose From Date");
            frmbtnDatePicker.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mtoDate)) {
            tobtnDatePicker.setError("Please Choose To Date");
            tobtnDatePicker.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(reason)) {
            eaddress.setError("Please enter reason", null);
            eaddress.requestFocus();
            return;
        }
        reason = eaddress.getText().toString();
        final ProgressDialog loading = new ProgressDialog(LeaveRequesttesting.this);
        loading.setMessage("Applying Leave...");
        loading.setCancelable(false);
        loading.show();

        apiInterfaceSave = com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata.APIClientSave.getClient().create(com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata.APIInterfaceSave.class);
        JsonObject object = new JsonObject();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        JsonArray array2 = new JsonArray();
        JsonArray array5 = new JsonArray();
        JsonArray array = new JsonArray();
        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams3 = new JsonObject();

        jsonParams1.addProperty("uname", uname);
        jsonParams1.addProperty("fromdate", mfrmDate);
        jsonParams1.addProperty("todate", mtoDate);
        jsonParams1.addProperty("reasonforleave", reason);
        jsonParams1.addProperty("remarks", "");
        jsonParams1.addProperty("status", "pending");
        jsonParams1.addProperty("employeeid", uname);


        object.addProperty("rowno", "001");
        object.addProperty("text", "0");
        object.add("columns", jsonParams1);
        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username", uname);
        jsonParams2.addProperty("password", pswd);
        jsonParams2.addProperty("transid", "lve");
        jsonParams2.addProperty("recordid", "0");
        array5.add(object);
        jsonParams3.add("axp_recid1", array5);
        array.add(jsonParams3);
        jsonParams2.add("recdata", array);
        jsonObject.add("savedata", jsonParams2);
        array2.add(jsonObject);
        jsonObject2.add("_parameters", array2);
        // Enter the correct url for your api service site
        apiInterfaceSave = com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata.APIClientSave.getClient().create(APIInterfaceSave.class);
        Call<com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata.ExampleSave> call2 = apiInterfaceSave.getResult(jsonObject2);
        call2.enqueue(new Callback<com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata.ExampleSave>() {
            @Override
            public void onResponse(Call<com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata.ExampleSave> call, Response<com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata.ExampleSave> response) {
                loading.dismiss();

                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LeaveRequesttesting.this, TabbedActivity.class));
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.savedata.ExampleSave> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(LeaveRequesttesting.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
    public String convertDate1(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(mTime);
        return formattedDate;

    }
    public String convertDate(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(mTime);
        return formattedDate;

    }
    public long convertToMillis(int day, int month, int year) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, year);
        calendarStart.set(Calendar.MONTH, month);
        calendarStart.set(Calendar.DAY_OF_MONTH, day);
        return calendarStart.getTimeInMillis();
    }
    public long convertToMillis1(int tdd, int tmonths, int tyer) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, tyer);
        calendarStart.set(Calendar.MONTH, tmonths);
        calendarStart.set(Calendar.DAY_OF_MONTH, tdd);
        return calendarStart.getTimeInMillis();
    }
    public long convertToMillis2(int fdd, int fmonths, int fyer) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, fyer);
        calendarStart.set(Calendar.MONTH, fmonths);
        calendarStart.set(Calendar.DAY_OF_MONTH, fdd);
        return calendarStart.getTimeInMillis();
    }
    private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(LeaveRequesttesting.this);
        loading.setMessage("Loading...");
        loading.setCancelable(false);
        loading.show();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "select  case when trunc(sysdate,'mm')+24<trunc(sysdate) then trunc(trunc(LAST_DAY(sysdate)+1,'mm')-1,'mm')+25 else trunc(trunc(sysdate,'mm')-1,'mm')+25 end fromsdate,case when trunc(sysdate,'mm')+24<trunc(sysdate) then trunc(LAST_DAY(sysdate)+1,'mm')+24 else trunc(sysdate,'mm')+24  end tosdate from dual");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);
            // array.put(jsonObject6);
            JsonArray array1 = new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters", array1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<Example> call4 = apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                tkfromsdate = response.body().getResult().get(0).getResult().getRow().get(0).getFromsdate();
                tktosdate= response.body().getResult().get(0).getResult().getRow().get(0).getTosdate();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date fdate = sdf.parse(tkfromsdate);
                    Date tdate=sdf.parse(tktosdate);
                    Calendar tcal = Calendar.getInstance();
                    Calendar fcal = Calendar.getInstance();
                    fcal.setTime(fdate);
                    tcal.setTime(tdate);
                    fmonths = fcal.get(Calendar.MONTH);
                    fdd = fcal.get(Calendar.DATE);
                    fyer = fcal.get(Calendar.YEAR);
                    tmonths=tcal.get(Calendar.MONTH);
                    tdd=tcal.get(Calendar.DATE);
                    tyer=tcal.get(Calendar.YEAR);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                loading.dismiss();
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(LeaveRequesttesting.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void Retrievingdata() {
        final ProgressDialog loading = new ProgressDialog(LeaveRequesttesting.this);
        loading.setMessage("Checking Leave...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceplanleav = (APIInterfacePlansleav) APIClientPlanleave.getClient().create(APIInterfacePlansleav.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);

        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select count(*)cnt from la_leave where status not in('cancel') and username='"+uname+"' and '" + alterdates + "' between fromdate and todate ");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);
            // array.put(jsonObject6);
            JsonArray array1 = new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters", array1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<Getcheckoutleave> call4 = apiInterfaceplanleav.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckoutleave>() {
            @Override
            public void onResponse(Call<Getcheckoutleave> call, Response<Getcheckoutleave> response) {
                loading.dismiss();


                try {
                    if (response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0")) {

                        //  postData();
                        savebtn.setEnabled(true);
                    } else {
                        Toast.makeText(getApplicationContext(), "Leave Already Applied this date...", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Getcheckoutleave> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Server Problem ,Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity", "Status: " + status);
        if (status == false)
            Toast.makeText(LeaveRequesttesting.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }
}