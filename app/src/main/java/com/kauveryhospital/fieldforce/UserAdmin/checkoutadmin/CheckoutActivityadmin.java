package com.kauveryhospital.fieldforce.UserAdmin.checkoutadmin;

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

public class CheckoutActivityadmin extends AppCompatActivity implements NetworkChangeCallback {
    RecyclerView cusappoint;
    EditText edittextsrc;

    HashMap<String, String> map;
    APIInterfaceadmin apiInterface;
    List<String> PartNameState;
    private ArrayList<CheckoutItem> mExampleList;
    List<String> PartIdState;
    public static  final String PREFS_NAME="loginpref";
    int j=0;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Resultadmin> list=new ArrayList<>();
    private ListCheckoutAdminAdapter mAdapter;
    ArrayList<HashMap<String, String>> arraylist;
    ImageView backarrow,loggedout;
    String employee,checkout,address,area,remarks,checkin,nickname,uname,pswd;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_activityadmin);
        loggedout=findViewById(R.id.loggedout);
        edittextsrc=findViewById(R.id.edittextsrc);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        SharedPreferences set=getSharedPreferences(PREFS_NAME,0);
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
                startActivity(new Intent(CheckoutActivityadmin.this, TabbedActivity.class));
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
    private void filter(String text) {
        ArrayList<CheckoutItem> filteredList = new ArrayList<>();
        for (CheckoutItem item : mExampleList) {
            if (item.getMemployee().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
            if(item.getMaddress().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            if(item.getMremarks().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            if(item.getMnickname().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
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
        final ProgressDialog loading = new ProgressDialog(CheckoutActivityadmin.this);
        loading.setMessage("Loading Checkoutlist...");
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
        jsonObject5.addProperty("sql","select a.employee,a.checkin,a.checkouttime,a.address,a.remarks,b.nickname from unplanvisit a join axusers b on a.employee=b.username where remarks is not null");
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
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        employee = response.body().getResult().get(0).getResult().getRow().get(i).getEmployee();
                        checkin=  response.body().getResult().get(0).getResult().getRow().get(i).getCheckin();
                        checkout = response.body().getResult().get(0).getResult().getRow().get(i).getCheckouttime();
                        address= response.body().getResult().get(0).getResult().getRow().get(i).getAddress();
                        remarks= response.body().getResult().get(0).getResult().getRow().get(i).getRemarks();
                        nickname=response.body().getResult().get(0).getResult().getRow().get(i).getNickname();
                        mExampleList.add(new CheckoutItem(employee,checkin,checkout,address,remarks,nickname));
                    }
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mAdapter = new ListCheckoutAdminAdapter(mExampleList);
                    cusappoint.setLayoutManager(mLayoutManager);
                    cusappoint.setAdapter(mAdapter);

                    loading.dismiss();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(CheckoutActivityadmin.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity","Status: " + status);
        if(status==false)
            Toast.makeText(CheckoutActivityadmin.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
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
