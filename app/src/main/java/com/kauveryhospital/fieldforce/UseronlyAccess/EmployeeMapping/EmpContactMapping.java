package com.kauveryhospital.fieldforce.UseronlyAccess.EmployeeMapping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.Globaldeclare.APIClient;
import com.kauveryhospital.fieldforce.Globaldeclare.APIInterface;
import com.kauveryhospital.fieldforce.Globaldeclare.Example;
import com.kauveryhospital.fieldforce.Globaldeclare.Result;
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;

import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIClientadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIInterfaceadmin;


import com.kauveryhospital.fieldforce.UseronlyAccess.EmployeeMapping.getdata.APIInterfacePlaned;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata.APIClientSavetrv;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata.APIInterfaceSavetrv;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata.ExampleSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.APIInterfacePlanschk;

import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Exampleadmin;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.getdata.APIClientPlans;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.getdata.Getcheckout;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpContactMapping extends AppCompatActivity implements NetworkChangeCallback {
    ImageView backarrow,viewlist;
    SearchableSpinner customer, PartSpinnerContacttype, PartSpinnerState;
    List<String> PartNameContacttype;
    int rannumber;
    RecyclerView cusappoint;
    EditText edittextsrc;
    List<String> PartIdContacttype;
    APIInterfacePlaned apiInterfaceplan;
    APIInterfaceSavetrv apiInterfaceSavetrv;
    List<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.Result> listchk = new ArrayList<>();
    APIInterfaceadmin apiInterfaceadm;
    List<String> PartNameContactName;
    List<String> PartIdContactName;

    public static final String PREFS_NAME = "loginpref";
    int j = 0;
    private ArrayList<EmployeeItem> mExampleList;
    private ListEmployeeAdapter mAdapter;

    List<com.kauveryhospital.fieldforce.Globaldeclare.Result> listaddress = new ArrayList<>();
    List<com.kauveryhospital.fieldforce.Globaldeclare.Result> listcontname = new ArrayList<>();
    APIInterfacePlanschk apiInterfacePlanschk;
    List<Result> list = new ArrayList<>();
    ArrayList<HashMap<String, String>> arraylist;
    APIInterface apiInterface;
    Random rand;
    Button btnLogina;

    ArrayAdapter<String> adapter;
    List<String> PartNameState;
    List<String> PartIdState;
    LinearLayout layoutone;
    String employee,cont_type,address,usrname,nickname,uname,pswd,cont_corp_name;
    private NetworkChangeReceiver networkChangeReceiver;
    private RecyclerView.LayoutManager mLayoutManager;
    String  uaa,emp_contactmappingid,message, getempmappingid,sessions, ContactId,ContacttypeName,part_IdContactName, part_NameState,ContactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_contact_mapping);

        layoutone=findViewById(R.id.layoutone);
        btnLogina=findViewById(R.id.btnLogin);
        cusappoint=findViewById(R.id.recyclerViewcontact);
        edittextsrc=findViewById(R.id.edittextsrc);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        viewlist=findViewById(R.id.viewlist);
         rand = new Random();

        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        sessions = set.getString("session", "");
        uaa = set.getString("uaa", "");
        nickname=set.getString("nickname","");
        PartSpinnerState = findViewById(R.id.empids);
        PartSpinnerState.setTitle("Select Employee");
        PartNameState = new ArrayList<>();
        PartIdState = new ArrayList<>();
        RetrieveData();
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
                Intent intent = new Intent(EmpContactMapping.this, TabbedActivity.class);
                startActivity(intent);
            }
        });

        btnLogina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkingdata();
            }
        });
        viewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpContactMapping.this, VisitPlanActivity.class);
                startActivity(intent);
            }
        });
        edittextsrc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString()); }
        });
    }
    private void filter(String text) {
        ArrayList<EmployeeItem> filteredList = new ArrayList<>();
        for (EmployeeItem item : mExampleList) {
            if (item.getMemployee().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item); }
            if(item.getMusrname().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item); }
            if(item.getMcont_type().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item); }
            if(item.getMcont_corp_name().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item); } }
        mAdapter.filterList(filteredList);
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
            Toast.makeText(EmpContactMapping.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }

    private void RetrieveData() {
        final ProgressDialog loading = new ProgressDialog(EmpContactMapping.this);
        loading.setMessage("Loading...");
        loading.setCancelable(false);
        loading.show();
        cusappoint.setAdapter(null);
        apiInterfaceadm = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s"," ");
        jsonObject5.addProperty("sql","select a.employee,a.usrname,b.contact_type,b.cont_corp_name,c.ADDRESS from emp_mapping a join emp_contactmapping b on  a.emp_mappingid =b.emp_mappingid join CONT_CORP c on b.CONTACT_NAME=c.CONT_CORPID where a.usrname ='"+uname+"' union select a.employee,a.usrname,b.contact_type,b.cont_corp_name,ADDRESS from emp_mapping a join emp_contactmapping b on  a.emp_mappingid =b.emp_mappingid join CORPORATE c on b.CONTACT_NAME=c.CORPORATEid where a.usrname = '"+uname+"'");
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

        Call<Exampleadmin> call4=apiInterfaceadm.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {
                loading.dismiss();
                arraylist = new ArrayList<HashMap<String, String>>();
                mExampleList = new ArrayList<>();
                try {
                    for (int i = 0; i <response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        employee = response.body().getResult().get(0).getResult().getRow().get(i).getEmployee();
                        usrname=  response.body().getResult().get(0).getResult().getRow().get(i).getUsrname();
                        cont_type = response.body().getResult().get(0).getResult().getRow().get(i).getContact_type();
                        cont_corp_name= response.body().getResult().get(0).getResult().getRow().get(i).getCont_corp_name();
                        address=response.body().getResult().get(0).getResult().getRow().get(i).getAddress();
                        mExampleList.add(new EmployeeItem(employee,usrname,cont_type,cont_corp_name,address));
                    }
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mAdapter = new ListEmployeeAdapter(mExampleList);
                    cusappoint.setLayoutManager(mLayoutManager);
                    cusappoint.setAdapter(mAdapter);
                }
                catch (Exception e)
                { e.printStackTrace(); } }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(EmpContactMapping.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        }); }

    private void postdataContacttype() {
        list.clear();
        listaddress.clear();
        final ProgressDialog loading = new ProgressDialog(EmpContactMapping.this);
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
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        ContacttypeName = response.body().getResult().get(0).getResult().getRow().get(i).getDt();
                        PartNameContacttype.add(ContacttypeName);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EmpContactMapping.this, android.R.layout.simple_spinner_dropdown_item, PartNameContacttype);
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
        listaddress.clear();
        final ProgressDialog loading = new ProgressDialog(EmpContactMapping.this);
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
        jsonObject5.addProperty("s"," ");
        jsonObject5.addProperty("sql", "select contactname,address,createdon,cont_corpid from cont_corp where contype='" + ContacttypeName + "'order by contactname");
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
                        ContactId=response.body().getResult().get(0).getResult().getRow().get(i).getCont_corpid();
                        PartNameContactName.add(ContactName);
                        PartIdContactName.add(ContactId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EmpContactMapping.this, android.R.layout.simple_spinner_dropdown_item, PartNameContactName);
                customer.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ContactName = PartNameContactName.get(position);
                        part_IdContactName=PartIdContactName.get(position);
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
        listcontname.clear();
        final ProgressDialog loading = new ProgressDialog(EmpContactMapping.this);
        loading.setMessage("Loading Corporate Name...");
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
        jsonObject5.addProperty("sql", "select address,createdon,name,corporateid from corporate");
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
                        ContactId=response.body().getResult().get(0).getResult().getRow().get(i).getCorporateid();

                        PartNameContactName.add(ContactName);
                        PartIdContactName.add(ContactId);

                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EmpContactMapping.this, android.R.layout.simple_spinner_dropdown_item, PartNameContactName);
                customer.setAdapter(adapter);
                customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ContactName = PartNameContactName.get(position);
                        part_IdContactName = PartIdContactName.get(position);
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
    private void Checkingdata()
    {
        final ProgressDialog loading = new ProgressDialog(EmpContactMapping.this);
        loading.setMessage("Loading...");
        loading.show();
        apiInterfaceplan = APIClientPlans.getClient().create(APIInterfacePlaned.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);

        jsonObject5.addProperty("s","");
        jsonObject5.addProperty("sql","select count(*)cnt  from emp_mapping where employee='"+nickname+"' ");

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

        Call<Getcheckout> call4=apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckout>() {
            @Override
            public void onResponse(Call<Getcheckout> call, Response<Getcheckout> response) {
                loading.dismiss();

                //   listss = response.body().getResult();
                try {
                    if(response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0"))
                    {
                        Insertdata();
                    }
                    else
                    {
                        gettingdatas();

                    }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Getcheckout> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Server Problem ,Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void gettingdatas()
    {
        final ProgressDialog loading = new ProgressDialog(EmpContactMapping.this);
        loading.setMessage("Please wait...");
        loading.show();
        apiInterfaceplan = APIClientPlans.getClient().create(APIInterfacePlaned.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);

        jsonObject5.addProperty("s"," ");
        jsonObject5.addProperty("sql","select emp_mappingid  from emp_mapping where employee='"+nickname+"' ");

        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);
            // array.put(jsonObject6);//
            JsonArray array1=new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters",array1);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Call<Getcheckout> call4=apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckout>() {
            @Override
            public void onResponse(Call<Getcheckout> call, Response<Getcheckout> response) {
                loading.dismiss();

                //   listss = response.body().getResult();

                try {
                    getempmappingid=response.body().getResult().get(0).getResult().getRow().get(0).getEmp_mappingid();
                    emp_contactmappingid=getempmappingid;
                    rannumber = rand.nextInt(2000) + 1;
                    BulkInsert();
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Getcheckout> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Server Problem ,Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Insertdata() throws JSONException {

        final ProgressDialog loading = new ProgressDialog(EmpContactMapping.this);
        loading.setMessage("Saving ...");
        loading.setCancelable(false);
        loading.show();
        // RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        apiInterfaceSavetrv = APIClientSavetrv.getClient().create(APIInterfaceSavetrv.class);
        JsonObject object = new JsonObject();
        JsonObject object1 = new JsonObject();
        JsonObject object2 = new JsonObject();

        JsonObject jsonObject = new JsonObject();

        JsonObject jsonObject2 = new JsonObject();
         JsonObject jsonObject3=new JsonObject();
        JsonObject jsonObject4=new JsonObject();
        JsonArray array2 = new JsonArray();

        JsonArray array5 = new JsonArray();
        JsonArray array = new JsonArray();
        JsonArray array6 = new JsonArray();
        JsonArray array7=new JsonArray();

        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams4= new JsonObject();
        JsonObject jsonParams5= new JsonObject();

        jsonParams1.addProperty("contact_type",ContacttypeName);
        jsonParams5.addProperty("id",part_IdContactName);
        jsonParams5.addProperty("text",ContactName);
        jsonParams1.add("contact_name",jsonParams5);
        //   object2.add("contact_name",jsonParams5);
        jsonParams1.addProperty("cont_corp_name",ContactName);
        object.addProperty("rowno", "002");
        object.addProperty("text", "0");
        object.add("columns", jsonParams1);
        jsonParams4.addProperty("employeename",nickname);
        jsonParams4.addProperty("active","T");
        object1.addProperty("rowno","001");
        object1.addProperty("text","0");
        object1.add("columns",jsonParams4);

        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", " ");
        jsonParams2.addProperty("username",uname);
        jsonParams2.addProperty("password",pswd);
        jsonParams2.addProperty("transid", "ctcrm");
        jsonParams2.addProperty("recordid", "0");
        array5.add(object);
        array6.add(object1);
        jsonObject3.add("axp_recid1",array6);
        jsonObject4.add("axp_recid2", array5);
        array.add(jsonObject3);
        array.add(jsonObject4);
        jsonParams2.add("recdata", array);
        jsonObject.add("savedata", jsonParams2);
        array2.add(jsonObject);
        jsonObject2.add("_parameters", array2);


        // Enter the correct url for your api service site
        apiInterfaceSavetrv =  APIClientSavetrv.getClient().create(APIInterfaceSavetrv.class);
        Call<ExampleSave> call2 = apiInterfaceSavetrv.getResult(jsonObject2);
        call2.enqueue(new Callback<ExampleSave>() {
            @Override
            public void onResponse(Call<ExampleSave> call, Response<ExampleSave> response) {
                loading.dismiss();

                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EmpContactMapping.this, TabbedActivity.class));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ExampleSave> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(EmpContactMapping.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void BulkInsert() {
        list.clear();
        final ProgressDialog loading = new ProgressDialog(EmpContactMapping.this);
        loading.setMessage("Loading ...");
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
        jsonObject5.addProperty("sql", "Insert INTO emp_contactmapping(contact_type,contact_name,emp_mappingid,emp_contactmappingid,cont_corp_name)values('"+ContacttypeName+"','"+part_IdContactName+"','"+getempmappingid+"','"+rannumber+"','"+ContactName+"')");
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
                    if (response.body().getResult().get(0).getError() != null) {

                        message = response.body().getResult().get(0).getError().getMsg();
                        RetrieveData();
                        Toast.makeText(getApplicationContext(),"Contact Type or Customer Name is Not Empty", Toast.LENGTH_SHORT).show(); }
                    else if (response.body().getResult().get(0).getResult().getStatus() != null)
                    {
                        message =response.body().getResult().get(0).getResult().getStatus() ;
                        RetrieveData();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
