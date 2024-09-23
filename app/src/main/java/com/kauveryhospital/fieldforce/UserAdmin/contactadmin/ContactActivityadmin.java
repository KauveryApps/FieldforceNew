package com.kauveryhospital.fieldforce.UserAdmin.contactadmin;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIClientadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIInterfaceadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Exampleadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Resultadmin;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivityadmin extends AppCompatActivity implements NetworkChangeCallback {
    RecyclerView cusappoint;

    EditText edittextsrc;
    HashMap<String, String> map;
    APIInterfaceadmin apiInterface;
    public static  final String PREFS_NAME="loginpref";
    int j = 0;
    List<Resultadmin> list = new ArrayList<>();
    ArrayList<HashMap<String, String>> arraylist;
    ImageView backarrow,loggedout;
   ContactTaskAdapteradmin mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ContactItem> mExampleList;
    private NetworkChangeReceiver networkChangeReceiver;
    String address, uname,pswd,cityid,areaid,pincodeid,city_name, area, pincode,stateid, state_name, cont_corpid,contype, salutation, contactname, corporate, ambulance, specialization, portfolio, phone, active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_activityadmin);

        edittextsrc=findViewById(R.id.edittextsrc);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        SharedPreferences set=getSharedPreferences(PREFS_NAME,0);
        uname=set.getString("username","");
        pswd=set.getString("password","");
        backarrow=findViewById(R.id.backarrow);
        edittextsrc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        cusappoint = (RecyclerView) findViewById(R.id.recyclerView);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactActivityadmin.this, TabbedActivity.class));
            }
        });
        if(isConnected()){
            postdatastate();
        }
        else{
            Toast.makeText(getApplicationContext(),"Please Check the Internet Connection!!", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }
    private void filter(String text) {
        ArrayList<ContactItem> filteredList = new ArrayList<>();
        for (ContactItem item : mExampleList) {
            if (item.getMcontactname().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
            if(item.getMaddress().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
           if(item.getMambulance().toLowerCase().contains(text.toLowerCase())){
               filteredList.add(item);
           }
           if(item.getMarea().toLowerCase().contains(text.toLowerCase())){
               filteredList.add(item);
           }
           if(item.getMcity_name().toLowerCase().contains(text.toLowerCase())){
               filteredList.add(item);
           }
            if(item.getMcontype().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            if(item.getMphone().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            if(item.getMstate_name().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            if(item.getMportfolio().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }

        }
        mAdapter.filterList(filteredList);
    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity","Status: " + status);
        if(status==false)
            Toast.makeText(ContactActivityadmin.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
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
    private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(ContactActivityadmin.this);
        loading.setMessage("Loading ContactList...");
        loading.setCancelable(false);
        loading.show();
        apiInterface = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select a.cont_corpid,a.contype,a.salutation,a.contactname,a.corporate,a.ambulance,a.specialization,a.portfolio,a.address,b.state_name,b.stateid,c.city_name,c.cityid,a.area,a.pincode,a.phone,a.active,d.nickname from cont_corp a LEFT join state b on a.state=b.stateid LEFT join city c on a.city=c.cityid join axusers d on a.username=d.username");
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

        Call<Exampleadmin> call4 = apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {
                arraylist = new ArrayList<HashMap<String, String>>();
                mExampleList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        contype = response.body().getResult().get(0).getResult().getRow().get(i).getContype();
                        salutation = response.body().getResult().get(0).getResult().getRow().get(i).getSalutation();
                        contactname = response.body().getResult().get(0).getResult().getRow().get(i).getContactname();
                        corporate = response.body().getResult().get(0).getResult().getRow().get(i).getCorporate();
                        ambulance = response.body().getResult().get(0).getResult().getRow().get(i).getAmbulance();
                        specialization = response.body().getResult().get(0).getResult().getRow().get(i).getSpecialization();
                        portfolio = response.body().getResult().get(0).getResult().getRow().get(i).getPortfolio();
                        phone = response.body().getResult().get(0).getResult().getRow().get(i).getPhone();
                        cont_corpid=response.body().getResult().get(0).getResult().getRow().get(i).getCont_corpid();
                        address = response.body().getResult().get(0).getResult().getRow().get(i).getAddress();
                        city_name = response.body().getResult().get(0).getResult().getRow().get(i).getCity_name();
                        area = response.body().getResult().get(0).getResult().getRow().get(i).getArea();
                        pincode = response.body().getResult().get(0).getResult().getRow().get(i).getPin_code();
                        state_name = response.body().getResult().get(0).getResult().getRow().get(i).getstate_name();
                        stateid=response.body().getResult().get(0).getResult().getRow().get(i).getstateid();
                        cityid=response.body().getResult().get(0).getResult().getRow().get(i).getCityid();
                        mExampleList.add(new ContactItem(contype,salutation,contactname,corporate,ambulance,specialization,portfolio,phone,cont_corpid,address,city_name,area,pincode,state_name,stateid,cityid));
                        map = new HashMap<String, String>();
                        map.put("contype", contype);
                        map.put("salutation", salutation);
                        map.put("contactname", contactname);
                        map.put("corporate", corporate);
                        map.put("ambulance", ambulance);
                        map.put("specialization", specialization);
                        map.put("portfolio", portfolio);
                        map.put("phone", phone);
                        map.put("cont_corpid",cont_corpid);
                        map.put("address", address);
                        map.put("city", city_name);
                        map.put("area", area);
                        map.put("pincode", pincode);
                        map.put("state_name", state_name);
                        map.put("stateid",stateid);
                        map.put("cityid",cityid);
                        arraylist.add(map);
                    }
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mAdapter= new ContactTaskAdapteradmin(mExampleList);
                    cusappoint.setLayoutManager(mLayoutManager);
                    cusappoint.setAdapter(mAdapter);
                    loading.dismiss();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t)
            {
                loading.dismiss();
                Toast.makeText(ContactActivityadmin.this, "No records Found!!!", Toast.LENGTH_LONG).show();
            }
        });

    }
}