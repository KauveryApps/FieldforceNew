package com.kauveryhospital.fieldforce.UseronlyAccess.corporate;

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
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCorporateActivity extends AppCompatActivity implements NetworkChangeCallback {
    RecyclerView cusappoint;
    ListCorporateAdapter adapter;
    HashMap<String, String> map;
    APIInterface apiInterface;
    public static  final String PREFS_NAME="loginpref";
    int j=0;
    List<Result> list=new ArrayList<>();
    ArrayList<HashMap<String, String>> arraylist;
    ImageView backarrow;
    String name,address,city,pincode,state,currentDateTimeString1,uname,pswd;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_corporate);
        Date todaydate1= Calendar.getInstance().getTime();
        SimpleDateFormat formatter1=new SimpleDateFormat("dd-MMM-yyyy");
        currentDateTimeString1=formatter1.format(todaydate1);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        SharedPreferences set=getSharedPreferences(PREFS_NAME,0);
        uname=set.getString("username","");
        pswd=set.getString("password","");
        backarrow=findViewById(R.id.backarrow);
        cusappoint= findViewById(R.id.recyclerView);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListCorporateActivity.this, TabbedActivity.class));
            }
        });
        if(isConnected()){
            postdatastate();
        }
        else{
            Toast.makeText(getApplicationContext(),"Please Check the Internet Connection!!", Toast.LENGTH_SHORT).show();
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
    private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(ListCorporateActivity.this);
        loading.setMessage("Loading Corporate...");
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
        jsonObject5.addProperty("sql","select state_name,address,city_name,pin_code,a.name from CORPORATE a LEFT join STATE b on b.stateid = a.state LEFT join CITY c on c.CITYID = a.CITY LEFT join PINCODE d on d.pincodeid= a.PINCODE where a.username='"+uname+"' and trunc(a.createdon)='"+currentDateTimeString1+"' order by a.CREATEDON desc,a.NAME desc");
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



                arraylist = new ArrayList<HashMap<String, String>>();

                try {
                    for (int i = 0; i <response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        name = response.body().getResult().get(0).getResult().getRow().get(i).getName();
                        state=  response.body().getResult().get(0).getResult().getRow().get(i).getstate_name();
                        address = response.body().getResult().get(0).getResult().getRow().get(i).getAddress();
                        city= response.body().getResult().get(0).getResult().getRow().get(i).getCity_name();
                        pincode= response.body().getResult().get(0).getResult().getRow().get(i).getPin_code();
                        map = new HashMap<String, String>();
                        map.put("name",name);
                        map.put("state",state);
                        map.put("city",city);
                        map.put("address",address);
                        map.put("pincode",pincode);
                        arraylist.add(map);
                    }
                    adapter = new ListCorporateAdapter(ListCorporateActivity.this, arraylist);
                    cusappoint.setLayoutManager(new LinearLayoutManager(ListCorporateActivity.this));
                    cusappoint.setAdapter(adapter);
                    loading.dismiss();
                } catch (Exception e)
                { e.printStackTrace(); }

            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
            loading.dismiss();
                Toast.makeText(ListCorporateActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity","Status: " + status);
        if(status==false)
            Toast.makeText(ListCorporateActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show(); }
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
