package com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest;

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
import com.kauveryhospital.fieldforce.UseronlyAccess.corporate.ListCorporateActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelRequestActivity extends AppCompatActivity implements NetworkChangeCallback {
    RecyclerView cusappoint;
    CancelRequestAdapter adapter;
    HashMap<String, String> map;
    APIInterface apiInterface;
    List<String> PartNameState;
    List<String> PartIdState;
    public static  final String PREFS_NAME="loginpref";
    int j=0;
    List<Result> list=new ArrayList<>();
    ImageView backarrow;
    ArrayList<HashMap<String, String>> arraylist;
    String jsonString,pswd;
    String uname,fromdate,todate,status,employeeid,la_leaveid;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_request);
        backarrow=findViewById(R.id.backarrow);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        SharedPreferences set=getSharedPreferences(PREFS_NAME,0);
        uname=set.getString("username","");
        pswd=set.getString("password","");

        cusappoint=(RecyclerView) findViewById(R.id.recyclerView);
        if(isConnected()){
            postdatastate();
        }
        else{
            Toast.makeText(CancelRequestActivity.this, "check Internet connection", Toast.LENGTH_LONG).show();
        }

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CancelRequestActivity.this, LeaveRequesttesting.class));
            }
        });
    }
    private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(CancelRequestActivity.this);
        loading.setMessage("Loading Leave...");
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
        jsonObject5.addProperty("s","");
        jsonObject5.addProperty("sql","select uname,reasonforleave,fromdate,todate,status,la_leaveid from la_leave where uname='"+uname+"' and status='pending'");
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

        Call<Example> call4=apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {



                arraylist = new ArrayList<HashMap<String, String>>();

                try {
                    for (int i = 0; i <response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        uname = response.body().getResult().get(0).getResult().getRow().get(i).getuname();
                        fromdate=  response.body().getResult().get(0).getResult().getRow().get(i).getFromdate();
                        todate = response.body().getResult().get(0).getResult().getRow().get(i).getTodate();
                        status= response.body().getResult().get(0).getResult().getRow().get(i).getStatus();
                        employeeid= response.body().getResult().get(0).getResult().getRow().get(i).getReasonforleave();
                        la_leaveid=response.body().getResult().get(0).getResult().getRow().get(i).getLa_leaveid();
                        map = new HashMap<String, String>();
                        map.put("name",uname);
                        map.put("state",fromdate);
                        map.put("city",todate);
                        map.put("address",status);
                        map.put("pincode",employeeid);
                        map.put("la_leaveid",la_leaveid);
                        arraylist.add(map);
                    }
                    adapter = new CancelRequestAdapter(CancelRequestActivity.this, arraylist,uname,pswd);
                    cusappoint.setLayoutManager(new LinearLayoutManager(CancelRequestActivity.this));
                    cusappoint.setAdapter(adapter);
                    loading.dismiss();
                } catch (Exception e)
                { e.printStackTrace(); }

            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(CancelRequestActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
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
    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity","Status: " + status);
        if(status==false)
            Toast.makeText(CancelRequestActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
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