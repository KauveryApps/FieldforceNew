package com.kauveryhospital.fieldforce.UseronlyAccess.unplanned;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.Globaldeclare.APIClient;
import com.kauveryhospital.fieldforce.Globaldeclare.APIInterface;
import com.kauveryhospital.fieldforce.Globaldeclare.Example;
import com.kauveryhospital.fieldforce.Globaldeclare.Result;
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUnplannedvisits extends AppCompatActivity implements NetworkChangeCallback {
    RecyclerView cusappoint;
    ViewUnplannedAdapter adapter;
    HashMap<String, String> map;
    APIInterface apiInterface;
    Calendar upDateFroms;
    Button but1;
    TextView frmbtnDatePicker, tobtnDatePicker;
    Calendar upDateFrom;
    int mDay;
    List<String> PartNameState;
    String mfrmDate, message, pswd, alterdates;
    private int year, month, day, days;
    List<String> PartIdState;
    public static  final String PREFS_NAME="loginpref";
    int j=0;
    List<Result> list=new ArrayList<>();
    ArrayList<HashMap<String, String>> arraylist;
    Button btn_fromdate, btn_todate, savebtn, cancelbtn;
    long mfrmDate1, tomindate;
    ImageView backarrow;
    DatePickerDialog mDatePicker;
    ImageView fromdate,todate;
    private ArrayList<UnplannedvisitItem> mExampleList;
    String appp_no;
    String empname,address,customer,mtoDate,area,pincode,alterdates1,visitdate,jsonString,checkin,dayOfWeek,uname,checkouttime;
    private NetworkChangeReceiver networkChangeReceiver;
    private static String JSON_URL = "https://kforce.kauveryhospital.com/fieldforcescripts/asbmenurest.dll/datasnap/rest/Tasbmenurest/getchoices";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_unplannedvisits);

        networkChangeReceiver = new NetworkChangeReceiver(this);
        SharedPreferences set=getSharedPreferences(PREFS_NAME,0);
        uname=set.getString("username","");
        pswd=set.getString("password","");
        backarrow=findViewById(R.id.backarrow);

        cusappoint= findViewById(R.id.recyclerView);
        frmbtnDatePicker = findViewById(R.id.frmbtnDatePicker);
        tobtnDatePicker=findViewById(R.id.tobtnDatePicker);
        fromdate = findViewById(R.id.fromdate);
        todate=findViewById(R.id.todate);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewUnplannedvisits.this, UnPlannedActivity.class));
            }
        });

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    lngfromsdate=convertToMillis2(fdd,fmonths,fyer);
                //    lngtosdate=convertToMillis1(tdd,tmonths,tyer);
                Calendar mcurrentDates = Calendar.getInstance();
                year = mcurrentDates.get(Calendar.YEAR);
                month = mcurrentDates.get(Calendar.MONTH);
                day = mcurrentDates.get(Calendar.DAY_OF_MONTH);
                mcurrentDates.set(Calendar.DAY_OF_MONTH, mDay);
                mDatePicker = new DatePickerDialog(ViewUnplannedvisits.this, new DatePickerDialog.OnDateSetListener() {
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

                    }

                }, year, month, day);

                mDatePicker.setTitle("Please select From Date");
                //   mDatePicker.getDatePicker().setMinDate(lngfromsdate);
                //  mDatePicker.getDatePicker().setMaxDate(lngtosdate);

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
                mDatePicker = new DatePickerDialog(ViewUnplannedvisits.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        year = selectedyear;
                        month = selectedmonth;
                        day = selectedday;


                        mtoDate = convertDate(convertToMillis(day, month, year));

                        alterdates1=convertDate1(convertToMillis(day, month, year));
                        //   alterdates=alterdates1;

                        tobtnDatePicker.setText(mtoDate);

                        postdatastate();

                    }

                }, year, month, day);

                mDatePicker.setTitle("Please select To Date");
                // TODO Hide Future Date Here
                mDatePicker.getDatePicker().setMinDate(tomindate);

                // TODO Hide Past Date Here
                // mDatePicker.getDatePicker().setMaxDate(lngtosdate);
                mDatePicker.show();
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
        }
        catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
    public long convertToMillis(int day, int month, int year) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, year);
        calendarStart.set(Calendar.MONTH, month);
        calendarStart.set(Calendar.DAY_OF_MONTH, day);
        return calendarStart.getTimeInMillis();
    }

    public String convertDate(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        String formattedDate = df.format(mTime);
        return formattedDate;

    }

    public String convertDate1(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-YYYY");
        String formattedDate = df.format(mTime);
        return formattedDate;

    }
    private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(ViewUnplannedvisits.this);
        loading.setMessage("Loading ...");
        loading.setCancelable(false);
        loading.show();


        apiInterface = APIClient.getClient().create(APIInterface.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s"," ");
        jsonObject5.addProperty("sql","select employee,address,purpose,visitdate,customer,checkin,checkouttime from unplanvisit where visitdate between '"+alterdates+"' and '"+alterdates1+"' and employee='"+uname+"' and expense_status is NULL ");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);
            // array.put(jsonObject6);
            JsonArray array1=new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters",array1);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Call<Example> call4=apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                loading.dismiss();

                arraylist = new ArrayList<HashMap<String, String>>();

                try {
                    for (int i = 0; i <response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        empname = response.body().getResult().get(0).getResult().getRow().get(i).getEmployee();
                        visitdate=  response.body().getResult().get(0).getResult().getRow().get(i).getVisitdate();
                        address = response.body().getResult().get(0).getResult().getRow().get(i).getAddress();
                        customer= response.body().getResult().get(0).getResult().getRow().get(i).getCustomer();
                        checkin=response.body().getResult().get(0).getResult().getRow().get(i).getCheckin();
                        checkouttime=response.body().getResult().get(0).getResult().getRow().get(i).getCheckouttime();
                        map = new HashMap<String, String>();
                        map.put("empname",empname);
                        map.put("visitdate",visitdate);
                        map.put("address",address);
                        map.put("purpose",customer);
                        map.put("checkin",checkin);
                        map.put("checkout",checkouttime);
                        arraylist.add(map);
                    }
                    adapter = new ViewUnplannedAdapter(ViewUnplannedvisits.this, mExampleList);
                    cusappoint.setLayoutManager(new LinearLayoutManager(ViewUnplannedvisits.this));
                    cusappoint.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                cusappoint.setAdapter(null);
                loading.dismiss();
                Toast.makeText(ViewUnplannedvisits.this, "No Records Found", Toast.LENGTH_LONG).show();
            }

        });

    }

    public static void convertJsonToCsv(List<JSONObject> infoList) throws IOException, JSONException {

        FileWriter csvWriter = new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/FieldForceTravelExpenses"+"fromJSONss.csv");
        List<String> header= new ArrayList<String>();
        List<String> values = new ArrayList<String>();

        for(int i=0;i<infoList.size();i++) {

            JSONObject info_obj=infoList.get(i);
            Iterator<String> info_keys=info_obj.keys();
            while(info_keys.hasNext()) {
                String key = info_keys.next();

                if (info_obj.get(key) instanceof JSONObject) {

                    JSONObject obj=info_obj.getJSONObject(key);
                    Iterator<String> obj_keys=obj.keys();
                    while(obj_keys.hasNext()) {
                        String k=obj_keys.next();
                        if(i==0)
                            header.add(k);
                        values.add(obj.getString(k));
                    }

                }
                if (info_obj.get(key) instanceof JSONArray) {


                    JSONArray item_array=info_obj.getJSONArray(key);
                    for(int j=0;j<item_array.length();j++) {

                        JSONObject item=item_array.getJSONObject(j);
                        Iterator<String> item_keys=item.keys();
                        while(item_keys.hasNext()) {
                            String k=item_keys.next();
                            if(i==0)
                                header.add(k);
                            values.add(item.getString(k));
                        }
                    }

                }

            }


        }

        for(String head : header) {

            csvWriter.append(head);
            csvWriter.append(",");
        }
        csvWriter.append("\n");

        for(int m=0;m<values.size();m++) {


            csvWriter.append(values.get(m));
            if((m+1)%(header.size())==0) {

                csvWriter.append("\n");
            }
            else {
                csvWriter.append(",");
            }

        }
        csvWriter.flush();


    }
    private void createAppointmentsList() {

        RequestQueue requestQueue = Volley.newRequestQueue(ViewUnplannedvisits.this);
        JSONObject jsonObj = new JSONObject();
        try {
            //JSON form
            JSONObject getiview = new JSONObject();

            getiview.put("axpapp","fieldforce");
            getiview.put("username",uname);
            getiview.put("password",pswd);
            getiview.put("s"," ");
            getiview.put( "sql","select employee,address,purpose,visitdate,customer,checkin,checkouttime from unplanvisit where visitdate between '01-aug-2021' and '27-aug-2021' and employee='admin' and expense_status is NULL ");



            JSONObject getivwroot = new JSONObject();
            getivwroot.put("getchoices",getiview);

            JSONArray paraarr = new JSONArray();
            paraarr.put(getivwroot);

            jsonObj.put("_parameters",paraarr);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Enter the correct url for your api service site url parameter
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, JSON_URL, jsonObj,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonobject = new JSONObject(response.toString());
                            JSONArray jsonarray = jsonobject.getJSONArray("result");

                            File file=new File(  Environment.getExternalStorageDirectory().getAbsolutePath() + "/FieldForceTravelExpenses"+"fromJSONss.csv");
                          //  File file = new File(Environment.getExternalStorageDirectory()+"/FieldForceTravelExpenses"+System.currentTimeMillis()+".csv");
                            String csv = CDL.toString(jsonarray);
                            FileUtils.writeStringToFile(file, csv);
                           /* Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(file),"application/vnd.ms-excel");
                            startActivity(intent);*/
                            /*for(int i=0;i<=jsonarray.length();i++)
                            {
                                JSONArray jsonarray2 = jsonobject.getJSONArray("result");
                                JSONObject jsonObject2=jsonarray2.getJSONObject(i);
                             //   JSONObject json1 = jsonarray.getJSONObject(i).getJSONObject("row");
                                // JsonObject vv=new JsonObject();
                               // JSONObject nnc=new JSONObject("result");
                               // JSONArray bb=nnc.getJSONArray("row");
                              // String bbc=json1.getString("employee");
                                JSONArray jsonarray1 = jsonObject2.getJSONArray("row");
                                Log.d("TAG", "onResponsevvv: "+jsonarray1);
                                *//*for(int j=0;j>=jsonarray1.length();j++)
                                {
                                    JSONObject json2 = jsonarray1.getJSONObject(j);
                                   // JSONArray jsonarray2 = json2.getJSONArray("row");
                                    //Toast.makeText(getContext(),json2.toString(),Toast.LENGTH_LONG).show();



                                //  appp_no = json2.getString("employee");
                                  String app_date = json2.getString("employee");



                                }*//*
                              //  Toast.makeText(ViewUnplannedvisits.this, jsonarray1, Toast.LENGTH_LONG).show();

                            }*/
                        } catch (JSONException | IOException e) {e.printStackTrace();}

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               /* Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), error.getMessage(), Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                snackbar.show();
                spinner.setVisibility(View.GONE);*/
            }
        });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }
    private void createAppointmentsList1() {

        RequestQueue requestQueue = Volley.newRequestQueue(ViewUnplannedvisits.this);
        JSONObject jsonObj = new JSONObject();
        try {
            //JSON form
            JSONObject getiview = new JSONObject();

            getiview.put("axpapp","fieldforce");
            getiview.put("username",uname);
            getiview.put("password",pswd);
            getiview.put("s"," ");
            getiview.put( "sql","select employee,address,purpose,visitdate,customer,checkin,checkouttime from unplanvisit where visitdate between '01-aug-2021' and '27-aug-2021' and employee='admin' and expense_status is NULL ");



            JSONObject getivwroot = new JSONObject();
            getivwroot.put("getchoices",getiview);

            JSONArray paraarr = new JSONArray();
            paraarr.put(getivwroot);

            jsonObj.put("_parameters",paraarr);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Enter the correct url for your api service site url parameter
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, JSON_URL, jsonObj,
                new com.android.volley.Response.Listener<JSONObject>() {
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonobject = new JSONObject(response.toString());
                           JSONArray jsonarray = jsonobject.getJSONArray("result");
                            for(int i=0;i<=jsonarray.length();i++)
                            {
                                JSONArray jsonarray2 = jsonobject.getJSONArray("result");
                                for(int j=1;j>=jsonarray2.length();j++)
                                {
                                    JSONObject jsonObject2=jsonarray2.getJSONObject(i);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();}

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               /* Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), error.getMessage(), Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getResources().getColor(R.color.colorBackground));
                snackbar.show();
                spinner.setVisibility(View.GONE);*/
            }
        });

        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity","Status: " + status);
        if(status==false)
            Toast.makeText(ViewUnplannedvisits.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

}