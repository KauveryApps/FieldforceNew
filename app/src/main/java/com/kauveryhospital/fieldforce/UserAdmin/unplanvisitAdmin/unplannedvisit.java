package com.kauveryhospital.fieldforce.UserAdmin.unplanvisitAdmin;

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
import com.kauveryhospital.fieldforce.UserAdmin.corporateadmin.CorporateItem;
import com.kauveryhospital.fieldforce.UserAdmin.corporateadmin.ListCorporateAdminAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class unplannedvisit extends AppCompatActivity implements NetworkChangeCallback {
    RecyclerView cusappoint;
    HashMap<String, String> map;
    EditText edittextsrc;
    private ArrayList<UnplanItem> mExampleList;
    APIInterfaceadmin apiInterface;
    List<String> PartNameState;
    List<String> PartIdState;
    public static  final String PREFS_NAME="loginpref";
    private ListUnplanAdminAdapter mAdapter;

    int j=0;
    List<Resultadmin> list=new ArrayList<>();
    ArrayList<HashMap<String, String>> arraylist;
    private RecyclerView.LayoutManager mLayoutManager;
    ImageView backarrow,loggedout;
    String employee,address,checkin,area,checkout,visitdate,jsonString,uname,pswd,nickname;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unplannedvisit);
        loggedout=findViewById(R.id.loggedout);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        SharedPreferences set=getSharedPreferences(PREFS_NAME,0);


        uname=set.getString("username","");
        pswd=set.getString("password","");
        edittextsrc=findViewById(R.id.edittextsrc);
        backarrow=findViewById(R.id.backarrow);
        loggedout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
        cusappoint=(RecyclerView) findViewById(R.id.recyclerView);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(unplannedvisit.this, TabbedActivity.class));
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
        ArrayList<UnplanItem> filteredList = new ArrayList<>();
        for (UnplanItem item : mExampleList) {
            if (item.getMemployee().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }

        }
        mAdapter.filterList(filteredList);
    }
    private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(unplannedvisit.this);
        loading.setMessage("Loading UnplannedVisit...");
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
        jsonObject5.addProperty("sql","select a.employee,a.address,a.visitdate,a.checkin,a.checkouttime,b.nickname from unplanvisit a join axusers b on a.employee=b.username");
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


                mExampleList = new ArrayList<>();

                try {
                    for (int i = 0; i <response.body().getResult().get(j).getResult().getRow().size(); i++)
                    {
                        employee =  response.body().getResult().get(0).getResult().getRow().get(i).getEmployee();
                        nickname= response.body().getResult().get(0).getResult().getRow().get(i).getNickname();
                        address =  response.body().getResult().get(0).getResult().getRow().get(i).getAddress();
                        visitdate=   response.body().getResult().get(0).getResult().getRow().get(i).getVisitdate();
                        checkin=  response.body().getResult().get(0).getResult().getRow().get(i).getCheckin();
                        checkout=  response.body().getResult().get(0).getResult().getRow().get(i).getCheckouttime();
                        mExampleList.add(new UnplanItem(employee,nickname,address,visitdate,checkin,checkout));

                    }
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mAdapter = new ListUnplanAdminAdapter(mExampleList);
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
                Toast.makeText(unplannedvisit.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity","Status: " + status);
        if(status==false)
            Toast.makeText(unplannedvisit.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (networkChangeReceiver != null)
        {
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