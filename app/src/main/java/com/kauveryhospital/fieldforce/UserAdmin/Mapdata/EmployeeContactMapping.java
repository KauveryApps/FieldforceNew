package com.kauveryhospital.fieldforce.UserAdmin.Mapdata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIClientadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIInterfaceadmin;

import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.UnPlannedActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.APIClientPlanchk;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.APIInterfacePlanschk;

import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Exampleadmin;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.Getcheckoutchk;
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

public class EmployeeContactMapping extends AppCompatActivity  implements NetworkChangeCallback {
    ImageView backarrow,viewlist;
    SearchableSpinner customer, PartSpinnerContacttype, PartSpinnerState;
    List<String> PartNameContacttype;
    List<String> PartIdContacttype;

    List<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.Result> listchk = new ArrayList<>();

    APIInterfaceadmin apiInterfaceadm;
    List<String> PartNameContactName;
    List<String> PartIdContactName;

    public static final String PREFS_NAME = "loginpref";
    int j = 0;
    Button btnLogin;
    static final String PREFS_NAMES = "preferenceName";
    List<com.kauveryhospital.fieldforce.Globaldeclare.Result> listaddress = new ArrayList<>();
    List<com.kauveryhospital.fieldforce.Globaldeclare.Result> listcontname = new ArrayList<>();
    APIInterfacePlanschk apiInterfacePlanschk;
    List<Result> list = new ArrayList<>();

    APIInterface apiInterface;

    ArrayAdapter<String> adapter;
    List<String> PartNameState;
    List<String> PartIdState;
    private NetworkChangeReceiver networkChangeReceiver;
    String  pswd, uname,  ContacttypeName,Statename, part_NameState,ContactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_contact_mapping);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        btnLogin=findViewById(R.id.btnLogin);
        viewlist=findViewById(R.id.viewlist);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        PartSpinnerState = findViewById(R.id.empids);
        PartNameState = new ArrayList<>();
        PartIdState = new ArrayList<>();
        postdatastate();
        customer = findViewById(R.id.customer);
        customer.setTitle("Select Customer");
        PartNameContactName = new ArrayList<>();
        PartIdContactName = new ArrayList<>();
        PartSpinnerContacttype = findViewById(R.id.contacttype);
        PartSpinnerContacttype.setTitle("Select Contact Type");
        PartNameContacttype = new ArrayList<>();
        PartIdContacttype = new ArrayList<>();
        postdataContacttype();
        backarrow = findViewById(R.id.backarrow);



        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeContactMapping.this, TabbedActivity.class);
                startActivity(intent);
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
            Toast.makeText(EmployeeContactMapping.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }

    private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(EmployeeContactMapping.this);
        loading.setMessage("Loading Employee Id...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceadm = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select nickname from axusers");
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

        Call<Exampleadmin> call4 = apiInterfaceadm.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {
                loading.dismiss();
                //  String jsonString = response.body().toString();

                try {
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        Statename =  response.body().getResult().get(0).getResult().getRow().get(i).getNickname();
                        // stateId = list.get(0).getResult().getRow().get(i).getstateid();
                        PartNameState.add(Statename);
                        //  PartIdState.add(stateId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter = new ArrayAdapter<String>(EmployeeContactMapping.this, android.R.layout.simple_spinner_dropdown_item, PartNameState);
                PartSpinnerState.setAdapter(adapter);
                PartSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //  part_IdState= PartIdState.get(position);
                        part_NameState = PartNameState.get(position);

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(EmployeeContactMapping.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postdataContacttype() {
        list.clear();
        final ProgressDialog loading = new ProgressDialog(EmployeeContactMapping.this);
        loading.setMessage("Loading ContactType...");
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

        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select 'Doctor' dt from dual union all select 'Ambulance Driver' dt from dual union all select 'Others' dt from dual union all select 'Corporate Name' dt from dual order by dt");
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
                loading.dismiss();




                try {
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        ContacttypeName = response.body().getResult().get(0).getResult().getRow().get(i).getDt();
                        PartNameContacttype.add(ContacttypeName);

                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EmployeeContactMapping.this, android.R.layout.simple_spinner_dropdown_item, PartNameContacttype);
                PartSpinnerContacttype.setAdapter(adapter);
                PartSpinnerContacttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ContacttypeName = PartNameContacttype.get(position);
                        listcontname.clear();
                        PartNameContactName.clear();
                        if (ContacttypeName.equals("Corporate Name")) {
                            getAddresses();
                        } else {

                            postdatacontactname();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void postdatacontactname() {
        listcontname.clear();
        final ProgressDialog loading = new ProgressDialog(EmployeeContactMapping.this);
        loading.setMessage("Loading ContactName...");
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
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select contactname,address,createdon from cont_corp where contype='" + ContacttypeName + "'order by contactname");
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
                loading.dismiss();



                int numbers = response.body().getResult().get(j).getResult().getRow().size();
                try {

                    for (int i = 0; i <= numbers; i++) {

                        ContactName = response.body().getResult().get(0).getResult().getRow().get(i).getContactname();

                        PartNameContactName.add(ContactName);

                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EmployeeContactMapping.this, android.R.layout.simple_spinner_dropdown_item, PartNameContactName);
                customer.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ContactName = PartNameContactName.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }); }
                @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAddresses() {
        listaddress.clear();
        final ProgressDialog loading = new ProgressDialog(EmployeeContactMapping.this);
        loading.setMessage("Loading Address...");
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
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select address,createdon,name from corporate");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);

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
                loading.dismiss();

                try {

                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        ContactName = response.body().getResult().get(0).getResult().getRow().get(i).getName();

                        PartNameContactName.add(ContactName);

                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EmployeeContactMapping.this, android.R.layout.simple_spinner_dropdown_item, PartNameContactName);
                customer.setAdapter(adapter);
                customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ContactName = PartNameContactName.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void Retrievingdata() {
        final ProgressDialog loading = new ProgressDialog(EmployeeContactMapping.this);
        loading.setMessage("Checking Work Start...");
        loading.setCancelable(false);
        loading.show();
        apiInterfacePlanschk = (APIInterfacePlanschk) APIClientPlanchk.getClient().create(APIInterfacePlanschk.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);

        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select count(*)cnt from emp_mapping  where  username='" + uname + "' ");
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

        Call<Getcheckoutchk> call4 = apiInterfacePlanschk.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckoutchk>() {
            @Override
            public void onResponse(Call<Getcheckoutchk> call, Response<Getcheckoutchk> response) {
                loading.dismiss();


                try {
                    if (response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0")) {
                        //  postData();
                     //   Toast.makeText(getApplicationContext(), "Please CheckIn Before We Start The Work...", Toast.LENGTH_SHORT).show();

                    } else if(response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("1")){
                        postdataContacttype();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Getcheckoutchk> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Server Problem ,Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });
    }




}

