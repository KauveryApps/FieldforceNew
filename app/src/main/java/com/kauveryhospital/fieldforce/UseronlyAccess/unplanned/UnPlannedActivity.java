package com.kauveryhospital.fieldforce.UseronlyAccess.unplanned;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.APIClientPlanchk;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.APIInterfacePlanschk;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.Getcheckoutchk;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIInterfaceSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ResultSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.APIInterfacevisit;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.APIClientvisit;
import com.kauveryhospital.fieldforce.Globaldeclare.Result;
import com.kauveryhospital.fieldforce.R;
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

public class UnPlannedActivity extends AppCompatActivity implements NetworkChangeCallback {
    ImageView backarrow, viewlist, viewlists;
    SearchableSpinner customer, PartSpinnerContacttype, PartSpinnerVisitpurpose;
    List<String> PartNameContacttype;
    List<String> PartIdContacttype;
    List<String> PartIdAddressType;
    APIInterfaceSave apiInterfaceSave;
    APIClientPlanchk apiClientPlanchk;
    Button btnlogin;
    List<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.Result> listchk = new ArrayList<>();
    List<String> PartNameVisitpurpose;
    List<String> PartIdVisitpurpose;
    EditText jointcall, chevkintime, edlatitude, edlongitude, portfolio, specialization, addresss, Remarks;
    List<String> PartNameContactName;
    List<String> PartIdContactName;
    List<ResultSave> listsave = new ArrayList<>();
    public static final String PREFS_NAME = "loginpref";
    int j = 0;
    static final String PREFS_NAMES = "preferenceName";
    List<Result> listaddress = new ArrayList<>();
    List<Result> listcontname = new ArrayList<>();
    APIInterfacePlanschk apiInterfacePlanschk;
    List<Result> list = new ArrayList<>();
    List<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Result> listvisit = new ArrayList<>();
    APIInterface apiInterface;
    APIInterfacevisit apiInterfacevisit;
    private NetworkChangeReceiver networkChangeReceiver;
    String statuscheckbox, settingaddress, settingcreatedon, currentDateTimeString, currentDateTimeString1, latitude, pswd, longitude, message, uname, uaddress, Statename, stateId, part_IdState, Cityname, cityId, part_IdCity, Areaname, areaId, part_IdArea, Pincodename, pincodeId, part_IdPincode, ContacttypeName, getaddressId, ContacttypeId, part_IdContacttype, SalutationName, SalutationId, VisitpurrposeName,VisitpurrposeNames, createdon, ContactName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_planned);
        viewlist = findViewById(R.id.viewlist);
        viewlists = findViewById(R.id.viewlists);
        Date todayDate = Calendar.getInstance().getTime();
        Date todaydate1 = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MMM/yyyy");
        currentDateTimeString = formatter.format(todayDate);
        currentDateTimeString1 = formatter1.format(todaydate1);
        Log.d("TAGs", "onCreate: " + currentDateTimeString);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        SharedPreferences settings = getSharedPreferences(PREFS_NAMES, 0);
        latitude = settings.getString("curlatitude", "");
        longitude = settings.getString("curlongitude", "");
        networkChangeReceiver = new NetworkChangeReceiver(this);
        btnlogin = findViewById(R.id.btnLogin);
        //name=findViewById(R.id.name);
        jointcall = findViewById(R.id.jointcall);
        chevkintime = findViewById(R.id.chevkintime);
        edlatitude = findViewById(R.id.edlatitude);
        edlongitude = findViewById(R.id.edlongitude);
        portfolio = findViewById(R.id.portfolio);
        specialization = findViewById(R.id.specialization);
        addresss = findViewById(R.id.addresss);
        Remarks = findViewById(R.id.Remarks);
        customer = findViewById(R.id.customer);
        customer.setTitle("Select Customer");
        PartNameContactName = new ArrayList<>();
        PartIdContactName = new ArrayList<>();
        PartSpinnerContacttype = findViewById(R.id.contacttype);
        PartSpinnerContacttype.setTitle("Select Contact Type");
        PartNameContacttype = new ArrayList<>();
        PartIdContacttype = new ArrayList<>();
        PartSpinnerVisitpurpose = findViewById(R.id.visitpurpose);
        PartSpinnerVisitpurpose.setTitle("Select Visit Purpose");
        PartNameVisitpurpose = new ArrayList<>();
        PartIdVisitpurpose = new ArrayList<>();
        postdataVisitpurpose();
        //name.setText(uname);
        edlatitude.setText(latitude);
        edlongitude.setText(longitude);
        chevkintime.setText(currentDateTimeString);
        Retrievingdata();
        backarrow = findViewById(R.id.backarrow);
        //toolbar.setSubtitle("Sub");
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnPlannedActivity.this, TabbedActivity.class);
                startActivity(intent);
            }
        });
        viewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnPlannedActivity.this, OfflineActivity.class);
                startActivity(intent);
            }
        });
        viewlists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnPlannedActivity.this, PlannedMainActivity.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    validation();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Check the Internet Connection!!", Toast.LENGTH_SHORT).show();
                }
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
            Toast.makeText(UnPlannedActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }

    private void Retrievingdata() {
        final ProgressDialog loading = new ProgressDialog(UnPlannedActivity.this);
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
        jsonObject5.addProperty("sql", "select count(*)cnt from work_start  where  username='" + uname + "' and trunc(starttime)='" + currentDateTimeString1 + "'");
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
                        Toast.makeText(getApplicationContext(), "Please CheckIn Before We Start The Work...", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("1")) {

                        Retrievingdata1();
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

    private void Retrievingdata1() {
        final ProgressDialog loading = new ProgressDialog(UnPlannedActivity.this);
        loading.setMessage("Checking ...");
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
        jsonObject5.addProperty("sql", "select status,statusflg from work_start  where  username='" + uname + "' and trunc(starttime)='" + currentDateTimeString1 + "'");
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
                    if (response.body().getResult().get(0).getResult().getRow().get(0).getStatus().equals("0")) {
                        Toast.makeText(getApplicationContext(), "Already The Work Is End For This Day..", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getResult().get(0).getResult().getRow().get(0).getStatus().equals("1")) {
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

    private void postdataContacttype() {
        list.clear();
        final ProgressDialog loading = new ProgressDialog(UnPlannedActivity.this);
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
                //    String jsonString = response.body().toString();
                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        ContacttypeName = response.body().getResult().get(0).getResult().getRow().get(i).getDt();
                        //  ContacttypeId = list.get(0).getResult().getRow().get(i).getPincodeid();
                        PartNameContacttype.add(ContacttypeName);
                        //  PartIdContacttype.add(ContacttypeId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UnPlannedActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameContacttype);
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
    private void postdataVisitpurpose() {
        final ProgressDialog loading = new ProgressDialog(UnPlannedActivity.this);
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
        jsonObject5.addProperty("sql", "select visitpurpose from visit_purpose where active = 'T' order by visitpurpose");
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
        Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Example> call4 = apiInterfacevisit.getResult(jsonObject7);
        call4.enqueue(new Callback<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Example>() {
            @Override
            public void onResponse(Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Example> call, Response<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Example> response) {
                loading.dismiss();
                try {
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        VisitpurrposeName = response.body().getResult().get(0).getResult().getRow().get(i).getVisitpurpose();
                        PartNameVisitpurpose.add(VisitpurrposeName);

                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UnPlannedActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameVisitpurpose);
                PartSpinnerVisitpurpose.setAdapter(adapter);

                PartSpinnerVisitpurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        VisitpurrposeNames = PartNameVisitpurpose.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(UnPlannedActivity.this, "Nothing is selected", Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onFailure(Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void postdatacontactname() {
        listcontname.clear();
        final ProgressDialog loading = new ProgressDialog(UnPlannedActivity.this);
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
                try {

                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        ContactName = response.body().getResult().get(0).getResult().getRow().get(i).getContactname();
                        PartNameContactName.add(ContactName);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UnPlannedActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameContactName);
                customer.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ContactName = PartNameContactName.get(position);
                        gettingcontactname();
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

    // input=ContactName
    private void getAddresses() {
        listaddress.clear();
        final ProgressDialog loading = new ProgressDialog(UnPlannedActivity.this);
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
                //String jsonString = response.body().toString();

                try {

                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        ContactName = response.body().getResult().get(0).getResult().getRow().get(i).getName();
                        // getaddressId=list.get(0).getResult().getRow().get(i).getAddress();
                        //  createdon=list.get(0).getResult().getRow().get(i).getCreatedon();
                        //  ContacttypeId = list.get(0).getResult().getRow().get(i).getPincodeid();
                        PartNameContactName.add(ContactName);
                        //   PartIdAddressType.add(getaddressId);
                        // chevkintime.setText(createdon);
                        //   PartIdContacttype.add(ContacttypeId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UnPlannedActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameContactName);
                customer.setAdapter(adapter);
                customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ContactName = PartNameContactName.get(position);
                        gettingaddress();

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

    private void gettingaddress() {
        final ProgressDialog loading = new ProgressDialog(UnPlannedActivity.this);
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
        jsonObject5.addProperty("sql", "select address,createdon from corporate where name='" + ContactName + "'");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);
            // array.put(jsonObject6);
            JsonArray array1 = new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters", array1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Call<Example> call4 = apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                loading.dismiss();
                //String jsonString = response.body().toString();
                //int numbers = response.body().getResult().get(j).getResult().getRow().size();
                try {
                    settingaddress = response.body().getResult().get(0).getResult().getRow().get(0).getAddress();
                    settingcreatedon = response.body().getResult().get(0).getResult().getRow().get(0).getCreatedon();
                    addresss.setText(settingaddress);
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void gettingcontactname() {
        final ProgressDialog loading = new ProgressDialog(UnPlannedActivity.this);
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
        jsonObject5.addProperty("sql", "select address,createdon from cont_corp where contactname='" + ContactName + "'");
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
                //String jsonString = response.body().toString();
                //  int numbers = response.body().getResult().get(j).getResult().getRow().size();//
                try {
                    settingaddress = response.body().getResult().get(0).getResult().getRow().get(0).getAddress();
                    settingcreatedon = response.body().getResult().get(0).getResult().getRow().get(0).getCreatedon();
                    addresss.setText(settingaddress);

                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void validation() {

        if(TextUtils.isEmpty(VisitpurrposeNames))
        {

            Toast.makeText(getApplicationContext(), "Please Select Visitpurpose", Toast.LENGTH_SHORT).show();
        }

       else{
            try {
                statuscheckbox = "active";
                postData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public void postData() throws JSONException {
        final ProgressDialog loading = new ProgressDialog(UnPlannedActivity.this);
        loading.setMessage("Saving...");
        loading.setCancelable(false);
        loading.show();
        // RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        apiInterfaceSave = (APIInterfaceSave) com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIClientSave.getClient().create(com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIInterfaceSave.class);
        JsonObject object = new JsonObject();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        JsonArray array2 = new JsonArray();
        JsonArray array5 = new JsonArray();
        JsonArray array = new JsonArray();
        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams3 = new JsonObject();
        jsonParams1.addProperty("contype", ContacttypeName);
        jsonParams1.addProperty("employee", uname);
        jsonParams1.addProperty("customer_name", ContactName);
        jsonParams1.addProperty("purpose", VisitpurrposeNames);
        jsonParams1.addProperty("address", settingaddress);
        jsonParams1.addProperty("checkin", currentDateTimeString);
        jsonParams1.addProperty("latitude", latitude);
        jsonParams1.addProperty("longitude", longitude);
        object.addProperty("rowno", "001");
        object.addProperty("text", "0");
        object.add("columns", jsonParams1);
        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username", uname);
        jsonParams2.addProperty("password", pswd);
        jsonParams2.addProperty("transid", "uplan");
        jsonParams2.addProperty("recordid", "0");
        array5.add(object);
        jsonParams3.add("axp_recid1", array5);
        array.add(jsonParams3);
        jsonParams2.add("recdata", array);
        jsonObject.add("savedata", jsonParams2);
        array2.add(jsonObject);
        jsonObject2.add("_parameters", array2);
        // Enter the correct url for your api service site//

        apiInterfaceSave = com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIClientSave.getClient().create(com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIInterfaceSave.class);
        Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave> call2 = apiInterfaceSave.getResult(jsonObject2);
        call2.enqueue(new Callback<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave>() {
            @Override
            public void onResponse(Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave> call, Response<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave> response) {
                loading.dismiss();

                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        postData1();
                        startActivity(new Intent(UnPlannedActivity.this, TabbedActivity.class));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postData1() throws JSONException {
        final ProgressDialog loading = new ProgressDialog(UnPlannedActivity.this);
        loading.setMessage("Saving...");
        loading.setCancelable(false);
        loading.show();
        // RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        apiInterfaceSave = (APIInterfaceSave) com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIClientSave.getClient().create(com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIInterfaceSave.class);
        JsonObject object = new JsonObject();

        JsonObject jsonObject = new JsonObject();

        JsonObject jsonObject2 = new JsonObject();

        JsonArray array2 = new JsonArray();

        JsonArray array5 = new JsonArray();

        JsonArray array = new JsonArray();

        JsonObject jsonParams1 = new JsonObject();

        JsonObject jsonParams2 = new JsonObject();

        JsonObject jsonParams3 = new JsonObject();

        jsonParams1.addProperty("emp_name", uname);

        jsonParams1.addProperty("userid", uname);

        jsonParams1.addProperty("latitude", latitude);

        jsonParams1.addProperty("longitude", longitude);

        jsonParams1.addProperty("location_date", currentDateTimeString);

        object.addProperty("rowno", "001");
        object.addProperty("text", "0");

        object.add("columns", jsonParams1);

        jsonParams2.addProperty("axpapp", "fieldforce");

        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username", uname);
        jsonParams2.addProperty("password", pswd);

        jsonParams2.addProperty("transid", "cultr");
        jsonParams2.addProperty("recordid", "0");
        array5.add(object);
        jsonParams3.add("axp_recid1", array5);
        array.add(jsonParams3);
        jsonParams2.add("recdata", array);
        jsonObject.add("savedata", jsonParams2);
        array2.add(jsonObject);
        jsonObject2.add("_parameters", array2);


        // Enter the correct url for your api service site//

        apiInterfaceSave = (APIInterfaceSave) com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIClientSave.getClient().create(com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIInterfaceSave.class);
        Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave> call2 = apiInterfaceSave.getResult(jsonObject2);
        call2.enqueue(new Callback<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave>() {
            @Override
            public void onResponse(Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave> call, Response<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave> response) {
                loading.dismiss();

                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                    } else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
