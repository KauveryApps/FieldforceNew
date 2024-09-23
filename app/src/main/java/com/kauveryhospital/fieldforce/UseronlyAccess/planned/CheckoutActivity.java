package com.kauveryhospital.fieldforce.UseronlyAccess.planned;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.getdata.APIClientPlan;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.getdata.APIInterfacePlan;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.getdata.Getcheckout;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.getdata.Result;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.APIClientvisit;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.APIInterfacevisit;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.UnPlannedActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata.APIInterfaceSave;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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

public class CheckoutActivity extends AppCompatActivity implements NetworkChangeCallback {
    APIInterfacePlan apiInterfaceplan;
    int j = 0;
    APIInterfacevisit apiInterfacevisit;


    List<String> PartNameVisitpurpose;
    List<String> PartIdVisitpurpose;
    List<Result> list = new ArrayList<Result>();
    List<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Result>listvisit=new ArrayList<>();
    static final String PREFS_NAMES = "preferenceName";

    List<Result> listsaves = new ArrayList<>();
    List<Result>listsupdate=new ArrayList<>();
    public static final String PREFS_NAME = "loginpref";
    ImageView backarrow,viewlist;
    APIInterfaceSave apiInterfaceSave;
    private NetworkChangeReceiver networkChangeReceiver;
    Button btnLogin,btnLogina;
    EditText input;
    SearchableSpinner PartSpinnerVisitpurpose;
    TextView getemployee, getcustomer;
    String GetEmployeename, uname,latitude,longitude, pswd,inputval, currentDateTimeString, Getvisitpurpose, Getunplanvisitid, Getcheckin, Getcheckouttime, VisitpurrposeName, GetCustomer, message;
    TextView unplanvisitid, checkintime, checkout_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        viewlist=findViewById(R.id.viewlist);
        btnLogina=findViewById(R.id.btnLogina);
        backarrow = findViewById(R.id.backarrow);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        currentDateTimeString = formatter.format(todayDate);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        getemployee = findViewById(R.id.getemployee);
        getcustomer = findViewById(R.id.getcustomer);
        unplanvisitid = findViewById(R.id.unplanvisitid);
        checkintime = findViewById(R.id.checkintime);
        checkout_time = findViewById(R.id.checkout_time);
        btnLogin = findViewById(R.id.btnLogin);
        PartSpinnerVisitpurpose = findViewById(R.id.visitpurpose);
        PartNameVisitpurpose = new ArrayList<>();
        PartIdVisitpurpose = new ArrayList<>();
        PartSpinnerVisitpurpose.setTitle("Select VisitPurpose");
        SharedPreferences settings = getSharedPreferences(PREFS_NAMES, 0);
        latitude = settings.getString("curlatitude", "");
        longitude = settings.getString("curlongitude", "");
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckoutActivity.this, TabbedActivity.class));
            }
        });

        postdatacontactname();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()) {
                    try {
                        UpdateCout();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT);
                }
            }
        });
        btnLogina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetEmployeename.trim().length()==0){
                    Toast.makeText(CheckoutActivity.this, "No employee to checkout early" , Toast.LENGTH_SHORT).show();
                }
                else{
                    withEditText(v);

                }
            }
        });
        viewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckoutActivity.this, OfflineCheckoutActivity.class));
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


    private void postdatacontactname() {
        final ProgressDialog loading = new ProgressDialog(CheckoutActivity.this);
        loading.setMessage("Loading checkout...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceplan = APIClientPlan.getClient().create(APIInterfacePlan.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select count(*)cnt,visit_purpose,customer,employee,unplanvisitid,checkin,checkouttime from unplanvisit where employee = '" + uname + "' and checkouttime is null group by visit_purpose,employee,unplanvisitid,checkin,checkouttime,customer");
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

        Call<Getcheckout> call4 = apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckout>() {
            @Override
            public void onResponse(Call<Getcheckout> call, Response<Getcheckout> response) {
                loading.dismiss();


                //numbers = response.body().getResult().get(j).getResult().getRow().size();//

                try {
                    if (response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0")) {

                    } else if (response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("1")) {
                        getemployee.clearFocus();
                        unplanvisitid.clearFocus();
                        checkintime.clearFocus();
                        checkout_time.clearFocus();
                        getcustomer.clearFocus();
                        GetEmployeename = response.body().getResult().get(0).getResult().getRow().get(0).getEmployee();
                        Getunplanvisitid = response.body().getResult().get(0).getResult().getRow().get(0).getUnplanvisitid();
                        Getvisitpurpose = response.body().getResult().get(0).getResult().getRow().get(0).getVisitPurpose();
                        Getcheckin = response.body().getResult().get(0).getResult().getRow().get(0).getCheckin();
                        Getcheckouttime = response.body().getResult().get(0).getResult().getRow().get(0).getCheckouttime();
                        GetCustomer = response.body().getResult().get(0).getResult().getRow().get(0).getCustomer();

                        getemployee.setText(GetEmployeename);
                        unplanvisitid.setText(Getunplanvisitid);
                        checkintime.setText(Getcheckin);
                        checkout_time.setText(currentDateTimeString);
                        getcustomer.setText(GetCustomer);
                        postdataVisitpurpose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Getcheckout> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(CheckoutActivity.this, "No Records Found" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postdataVisitpurpose() {
        PartNameVisitpurpose.clear();
        PartNameVisitpurpose.add(Getvisitpurpose);
        final ProgressDialog loading = new ProgressDialog(CheckoutActivity.this);
        loading.setMessage("Loading Visit Purpose...");
        loading.setCancelable(false);
        loading.show();
        apiInterfacevisit = APIClientvisit.getClient().create(APIInterfacevisit.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select visit_purposeid,visitpurpose from visit_purpose where active = 'T'");
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

        Call<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Example> call4 = apiInterfacevisit.getResult(jsonObject7);
        call4.enqueue(new Callback<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Example>() {
            @Override
            public void onResponse(Call<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Example> call, Response<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Example> response) {
                loading.dismiss();

                int numbers = response.body().getResult().get(j).getResult().getRow().size();
                try {
                    for (int i = 0; i <= numbers; i++) {

                        VisitpurrposeName = response.body().getResult().get(0).getResult().getRow().get(i).getVisitpurpose();
                        //  ContacttypeId = list.get(0).getResult().getRow().get(i).getPincodeid();
                        PartNameVisitpurpose.add(VisitpurrposeName);
                        //  PartIdContacttype.add(ContacttypeId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CheckoutActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameVisitpurpose);
                PartSpinnerVisitpurpose.setAdapter(adapter);

                PartSpinnerVisitpurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        VisitpurrposeName = PartNameVisitpurpose.get(position);

                        //   Toast.makeText(getApplicationContext(),part_IdPincode,Toast.LENGTH_SHORT);//
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void UpdateCout() throws JSONException {
        final ProgressDialog loading = new ProgressDialog(CheckoutActivity.this);
        loading.setMessage("Loading checkout...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceplan = APIClientPlan.getClient().create(APIInterfacePlan.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);

        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "update unplanvisit SET  checkouttime='" + currentDateTimeString + "',c_latitude='"+latitude+"',c_longitude='"+longitude+"' where unplanvisitid='" + Getunplanvisitid + "'");

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

        Call<Getcheckout> call4 = apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckout>() {
            @Override
            public void onResponse(Call<Getcheckout> call, Response<Getcheckout> response) {
                loading.dismiss();


                //numbers = response.body().getResult().get(j).getResult().getRow().size();//

                try {
                    if (response.body().getResult().get(0).getResult().getStatus() != null) {
                        message = response.body().getResult().get(0).getResult().getStatus();
                        postData1();
                        Toast.makeText(CheckoutActivity.this, "Checkout" + message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CheckoutActivity.this, TabbedActivity.class));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Getcheckout> call, Throwable t) {
            loading.dismiss();
                Toast.makeText(CheckoutActivity.this, "server problem!!!", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity", "Status: " + status);
        if (status == false)
            Toast.makeText(CheckoutActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }
    public void withEditText(View v) {

        AlertDialog.Builder builders = new AlertDialog.Builder(CheckoutActivity.this);
        builders.setTitle("Reason for Early checkout");
        input = new EditText(CheckoutActivity.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builders.setView(input);
        builders.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                inputval=input.getText().toString();
                if (!inputval.isEmpty()) {

                    try {
                        UpdateEarlycheckout();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(CheckoutActivity.this, "Reason for Earlycheckout cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builders.show();
    }

    private  void UpdateEarlycheckout()throws JSONException
    {
        final ProgressDialog loading = new ProgressDialog(CheckoutActivity.this);
        loading.setMessage("Loading checkout...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceplan = APIClientPlan.getClient().create(APIInterfacePlan.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "update unplanvisit SET  remarks='"+inputval+"',checkouttime='" + currentDateTimeString + "',c_latitude='"+latitude+"',c_longitude='"+longitude+"' where employee='" + GetEmployeename + "' and unplanvisitid='" + Getunplanvisitid + "'");

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

        Call<Getcheckout> call4 = apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckout>() {
            @Override
            public void onResponse(Call<Getcheckout> call, Response<Getcheckout> response) {
                loading.dismiss();


                //numbers = response.body().getResult().get(j).getResult().getRow().size();//

                try {
                    if (response.body().getResult().get(0).getResult().getStatus() != null) {
                        message = response.body().getResult().get(0).getResult().getStatus();
                        postData1();
                        Toast.makeText(CheckoutActivity.this, "Early Checkout" + message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CheckoutActivity.this, TabbedActivity.class));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Getcheckout> call, Throwable t) {
             loading.dismiss();
                Toast.makeText(CheckoutActivity.this, "Server problem!!!", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void postData1() throws JSONException {
        final ProgressDialog loading = new ProgressDialog(CheckoutActivity.this);
        loading.setMessage("Saving...");
        loading.setCancelable(false);
        loading.show();
        // RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        apiInterfaceSave =  com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata.APIClientSave.getClient().create(com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata.APIInterfaceSave.class);
        JsonObject object=new JsonObject();


        JsonObject jsonObject=new JsonObject();

        JsonObject jsonObject2=new JsonObject();

        JsonArray array2=new JsonArray();

        JsonArray array5=new JsonArray();
        JsonArray array=new JsonArray();

        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams3 = new JsonObject();


        jsonParams1.addProperty("employee",uname);

        jsonParams1.addProperty("latitude", latitude);
        jsonParams1.addProperty("longitude", longitude);
        jsonParams1.addProperty("status",2);
        jsonParams1.addProperty("checkInTime",Getcheckin);
        jsonParams1.addProperty("checkOutTime",currentDateTimeString);
        jsonParams1.addProperty("visit_purpose",Getvisitpurpose);
        jsonParams1.addProperty("customer_name",GetCustomer);
        object.addProperty("rowno","001");
        object.addProperty("text","0");
        object.add("columns",jsonParams1);
        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username",uname);
        jsonParams2.addProperty("password",pswd);

        jsonParams2.addProperty("transid","ucout");
        jsonParams2.addProperty("recordid","0");
        array5.add(object);
        jsonParams3.add("axp_recid1",array5);
        array.add(jsonParams3);
        jsonParams2.add("recdata",array);
        jsonObject.add("savedata",jsonParams2);
        array2.add(jsonObject);
        jsonObject2.add("_parameters",array2 );


        // Enter the correct url for your api service site//

        apiInterfaceSave = (APIInterfaceSave) com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata.APIClientSave.getClient().create(com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata.APIInterfaceSave.class);
        Call<com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata.ExampleSave> call2 = apiInterfaceSave.getResult(jsonObject2);
        call2.enqueue(new Callback<com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata.ExampleSave>() {
            @Override
            public void onResponse(Call<com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata.ExampleSave> call, Response<com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata.ExampleSave> response) {
                loading.dismiss();

                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                       //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    else if (response.body().getResult().get(0).getMessage() != null)
                    {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(UnPlannedActivity.this, TabbedActivity.class));
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<com.kauveryhospital.fieldforce.UseronlyAccess.planned.savedata.ExampleSave> call, Throwable t)
            {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}