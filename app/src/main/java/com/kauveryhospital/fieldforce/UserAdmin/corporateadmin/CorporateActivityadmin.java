package com.kauveryhospital.fieldforce.UserAdmin.corporateadmin;

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
import com.kauveryhospital.fieldforce.SharedPrefManager;
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

public class CorporateActivityadmin extends AppCompatActivity implements NetworkChangeCallback {
    RecyclerView cusappoint;

    HashMap<String, String> map;
    APIInterfaceadmin apiInterface;
    EditText edittextsrc;
    ArrayList<CorporateItem> mExampleList;
    List<String> PartNameState;
    List<String> PartIdState;

    public static  final String PREFS_NAME="loginpref";
    int j=0;
    List<Resultadmin> list=new ArrayList<>();
    ArrayList<HashMap<String, String>> arraylist;
    ImageView backarrow,loggedout;
    String name,address,city,area,pincode,state,jsonString,uname,pswd;
    private NetworkChangeReceiver networkChangeReceiver;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListCorporateAdminAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporate_activityadmin);
        loggedout=findViewById(R.id.loggedout);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        SharedPreferences set=getSharedPreferences(PREFS_NAME,0);

        edittextsrc=findViewById(R.id.edittextsrc);
        uname=set.getString("username","");
        pswd=set.getString("password","");
        backarrow=findViewById(R.id.backarrow);
        loggedout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
        cusappoint=findViewById(R.id.recyclerView);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CorporateActivityadmin.this, TabbedActivity.class));
            }
        });
        if(isConnected()){
            postdatastate();
        }
        else{
            Toast.makeText(getApplicationContext(),"Please Check the Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
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
    private void filter(String text) {
        ArrayList<CorporateItem> filteredList = new ArrayList<>();
        for (CorporateItem item : mExampleList) {
            if (item.getmName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }

        }
        mAdapter.filterList(filteredList);
    }
    private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(CorporateActivityadmin.this);
        loading.setMessage("Loading Corporate...");
        loading.setCancelable(false);
        loading.show();
        apiInterface = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s"," ");
        jsonObject5.addProperty("sql","select state_name,address,city_name,pincode,a.name from CORPORATE a join STATE b on b.stateid = a.state join CITY c on c.CITYID = a.CITY where a.ACTIVE= 'T' order by a.NAME");
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

        Call<Exampleadmin> call4=apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {
                loading.dismiss();

                arraylist = new ArrayList<HashMap<String, String>>();
                mExampleList = new ArrayList<>();

                try {
                    for (int i = 0; i <response.body().getResult().get(j).getResult().getRow().size(); i++)
                    {
                        name = response.body().getResult().get(0).getResult().getRow().get(i).getName();
                        state=  response.body().getResult().get(0).getResult().getRow().get(i).getstate_name();
                        address = response.body().getResult().get(0).getResult().getRow().get(i).getAddress();
                        city= response.body().getResult().get(0).getResult().getRow().get(i).getCity_name();
                        pincode= response.body().getResult().get(0).getResult().getRow().get(i).getPincode();
                        mExampleList.add(new CorporateItem(name, state,address,city,pincode));
                    }
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mAdapter = new ListCorporateAdminAdapter(mExampleList);
                    cusappoint.setLayoutManager(mLayoutManager);
                    cusappoint.setAdapter(mAdapter);

                    loading.dismiss();
                } catch (Exception e)
                {
                    e.printStackTrace(); }

            }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(CorporateActivityadmin.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity","Status: " + status);
        if(status==false)
            Toast.makeText(CorporateActivityadmin.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }
}