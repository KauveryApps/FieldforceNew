package com.kauveryhospital.fieldforce.UseronlyAccess.MyAgendaToday;

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
import com.kauveryhospital.fieldforce.Globaldeclare.Result;
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIClientadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIInterfaceadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Exampleadmin;
import com.kauveryhospital.fieldforce.UseronlyAccess.EmployeeMapping.EmployeeItem;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.ListTravelExpensesActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlannedCheckInActivity extends AppCompatActivity implements NetworkChangeCallback {
    RecyclerView cusappoint;
    EditText edittextsrc;
    int j=0;
    ImageView backarrow,viewlist;
    APIInterfaceadmin apiInterfaceadm;
    List<Result> list = new ArrayList<>();
    ArrayList<HashMap<String, String>> arraylist;
    private NetworkChangeReceiver networkChangeReceiver;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CheckInItem> mExampleList;
    private ListEmployeeCheckInAdapter mAdapter;
    String   uname,portfolio,pswd,purpose,status_cl,cont_corpid,priority,visitdate,cusid,latitude,longitude,contact_type,customer_name,corporate,address,jointcall1,customer,visit_purposeid,visitplandtlid;
    public static final String PREFS_NAME = "loginpref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_check_in);
        backarrow=findViewById(R.id.backarrow);
        viewlist=findViewById(R.id.viewlist);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        SharedPreferences pref = getSharedPreferences("preferenceName", 0);
        latitude=pref.getString("curlatitude","");
        longitude=pref.getString("curlongitude","");
        cusappoint=findViewById(R.id.recyclerViewcontact);

        networkChangeReceiver = new NetworkChangeReceiver(this);
        RetrieveData();

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlannedCheckInActivity.this, TabbedActivity.class);
                startActivity(intent);
            }
        });
        viewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlannedCheckInActivity.this, ViewPlannedVisits.class);
                startActivity(intent);
            }
        });
    }

    private void RetrieveData() {
        final ProgressDialog loading = new ProgressDialog(PlannedCheckInActivity.this);
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
        jsonObject5.addProperty("sql","select  a.customername customer_name,a.contactname contact_pt, d.cont_corpid,cc.name corporate, d.portfolio, a.visitdate,a.STATUS visitstatus, case when a.STATUS=1 then 'Checked In' when a.status=2 then 'Visited' else 'Pending' end status_cl,case when a.STATUS=1 then 'tcout' when a.status=2 then null else 'tpchek' end tid,case when a.status=0 then 'Check In' else null end as cin_st,case when a.status=1 then 'Check Out' else null end as cout_ds from visitdetail a left join cont_corp d on A.CONTACTNAME =d.cont_corpid left join corporate cc on A.contactname=cc.corporateid  where customername is not null and a.visitdate = trunc(sysdate) and A.employee= '"+uname+"' order by 1");
      //  jsonObject5.addProperty("sql","select  a.customername customer_name,a.contactname contact_pt, d.cont_corpid,cc.name corporate, d.portfolio, a.visitdate,a.STATUS visitstatus, case when a.STATUS=1 then 'Checked In' when a.status=2 then 'Visited' else 'Pending' end status_cl,case when a.STATUS=1 then 'tcout' when a.status=2 then null else 'tpchek' end tid,case when a.status=0 then 'Check In' else null end as cin_st,case when a.status=1 then 'Check Out' else null end as cout_ds from visitdetail a left join cont_corp d on A.CONTACTNAME =d.cont_corpid left join corporate cc on A.contactname=cc.corporateid where a.visitdate = trunc(sysdate) and A.employee= '"+uname+"' order by 1");
     //   jsonObject5.addProperty("sql","select  a.contactname contact_pt, d.cont_corpid,cc.name corporate, d.portfolio, a.visitdate,a.STATUS visitstatus, case when a.STATUS=1 then 'Checked In' when a.status=2 then 'Visited' else 'Pending' end status_cl,case when a.STATUS=1 then 'tcout' when a.status=2 then null else 'tpchek' end tid,case when a.status=0 then 'Check In' else null end as cin_st,case when a.status=1 then 'Check Out' else null end as cout_ds from visitdetail a left join cont_corp d on A.CONTACTNAME =d.cont_corpid left join corporate cc on A.contactname=cc.corporateid where a.visitdate = trunc(sysdate) and A.employee= '"+uname+"' order by 1");
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
                mExampleList = new ArrayList<CheckInItem>();
                try {
                    for (int i = 0; i <response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        contact_type= response.body().getResult().get(0).getResult().getRow().get(i).getCustomer_name();
                        cont_corpid=response.body().getResult().get(0).getResult().getRow().get(i).getContact_pt();
                        corporate =response.body().getResult().get(0).getResult().getRow().get(i).getCorporate();
                        portfolio=response.body().getResult().get(0).getResult().getRow().get(i).getPortfolio();
                        visitdate = response.body().getResult().get(0).getResult().getRow().get(i).getVisitdate();
                        status_cl= response.body().getResult().get(0).getResult().getRow().get(i).getStatus_cl();
                        mExampleList.add(new CheckInItem(contact_type, cont_corpid,corporate,portfolio,visitdate,status_cl));
                    }
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mAdapter = new ListEmployeeCheckInAdapter(PlannedCheckInActivity.this,mExampleList,uname,pswd,latitude,longitude);
                    cusappoint.setLayoutManager(mLayoutManager);
                    cusappoint.setAdapter(mAdapter);
                }
                catch (Exception e)
                { e.printStackTrace(); } }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t)
            {
                loading.dismiss();
                Toast.makeText(PlannedCheckInActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
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
            Toast.makeText(PlannedCheckInActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }
}