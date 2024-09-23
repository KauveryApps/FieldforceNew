package com.kauveryhospital.fieldforce.UseronlyAccess.planned;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.NameAdapterout;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.OfflineActivity;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.DatabaseHelper;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.Name;

import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIClientSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIInterfaceSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfflineCheckoutActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private NetworkChangeReceiver networkChangeReceiver;
    private ListView listViewNames;
    private List<Name> names;
    public static final String PREFS_NAME = "loginpref";
    APIInterfaceSave apiInterfaceSave;
    ImageView backarrow;
    static final String PREFS_NAMES = "preferenceName";
    String message,employee,pswd,latitude,longitude;
    private NameAdapterout nameAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_checkout);
        db = new DatabaseHelper(this);
        backarrow=findViewById(R.id.backarrow);
        // networkChangeReceiver = new NetworkChangeReceiver(this);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        employee = set.getString("username", "");
        pswd = set.getString("password", "");
        SharedPreferences settings = getSharedPreferences(PREFS_NAMES, 0);
        latitude = settings.getString("curlatitude", "");
        longitude = settings.getString("curlongitude", "");
        listViewNames =  findViewById(R.id.listViewNames);
        names = new ArrayList<Name>();
        loadNames();
        //postData1
    }

    //    @Override
//    public void onNetworkChanged(boolean status) {
//        Log.e("MainActivity", "Status: " + status);
//        if (status == false) {
//
//            Toast.makeText(OfflineCheckoutActivity.this, "check Internet connection", Toast.LENGTH_LONG).show();
//        } else {
//            refreshList();
//            Cursor cursor = db.getUnsyncedNames();
//            if (cursor.moveToFirst()) {
//                do {
//
//                    //calling the method to save the unsynced name to MySQL//
//                    try {
//                        postData1(
//                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTACT)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CUSTOMER)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKINTIME)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKOUTTIMESTATUS)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SYSTEMID)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VISITPURPOSE))
//                        );
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } while (cursor.moveToNext());
//            }
//            Toast.makeText(OfflineCheckoutActivity.this, "Internet connection", Toast.LENGTH_LONG).show();
//        }
//
//    }
    public void postData1(final int id, final String contact,final String customer,final  String checkintime,final String checkouttimestatus,final String systemid,final  String latitude,final String longitude,final  String address,final  String employee,final String visitpurpose) throws JSONException {
        refreshList();
        final ProgressDialog loading = new ProgressDialog(OfflineCheckoutActivity.this);
        loading.setCancelable(false);
        loading.setMessage("Save Corporate...");
        loading.show();

        // RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//
        apiInterfaceSave =  APIClientSave.getClient().create(APIInterfaceSave.class);
        JsonObject object = new JsonObject();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        JsonArray array2 = new JsonArray();
        JsonArray array5 = new JsonArray();
        JsonArray array = new JsonArray();
        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams3 = new JsonObject();
        jsonParams1.addProperty("contype", contact);
        jsonParams1.addProperty("employee", employee);
        jsonParams1.addProperty("customer_name", customer);
        jsonParams1.addProperty("visit_purpose", visitpurpose);
        jsonParams1.addProperty("systemid",systemid);
        jsonParams1.addProperty("coutstatus",checkouttimestatus);
        jsonParams1.addProperty("address", address);
        jsonParams1.addProperty("remarks", employee);
        jsonParams1.addProperty("checkin", checkintime);
        jsonParams1.addProperty("c_latitude", latitude);
        jsonParams1.addProperty("c_longitude", longitude);
        object.addProperty("rowno", "001");
        object.addProperty("text", "0");
        object.add("columns", jsonParams1);
        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username", employee);
        jsonParams2.addProperty("password", pswd);
        jsonParams2.addProperty("transid", "ochot");
        jsonParams2.addProperty("recordid", "0");
        array5.add(object);
        jsonParams3.add("axp_recid1", array5);
        array.add(jsonParams3);
        jsonParams2.add("recdata", array);
        jsonObject.add("savedata", jsonParams2);
        array2.add(jsonObject);
        jsonObject2.add("_parameters", array2);

        // Enter the correct url for your api service site//
        apiInterfaceSave = APIClientSave.getClient().create(APIInterfaceSave.class);
        Call<ExampleSave> call2 = apiInterfaceSave.getResult(jsonObject2);
        loading.dismiss();
        call2.enqueue(new Callback<ExampleSave>() {
            @Override
            public void onResponse(Call<ExampleSave> call, Response<ExampleSave> response) {

                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(OfflineCheckoutActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        refreshList();
                        db.updateNameStatus(id, OfflineActivity.NAME_SYNCED_WITH_SERVER);
                        loadNames();
                        refreshList();
                        //sending the broadcast to refresh the list
                        getApplicationContext().sendBroadcast(new Intent(OfflineActivity.DATA_SAVED_BROADCAST));
                        loadNames();
                        Toast.makeText(OfflineCheckoutActivity.this, message, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(CorporateActivity.this, TabbedActivity.class));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ExampleSave> call, Throwable t) {
                loading.dismiss();

                Toast.makeText(OfflineCheckoutActivity.this, "server problem!!!", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loadNames() {
        names.clear();
        Cursor cursor = db.getUnsyncedNames1();
        if (cursor.moveToFirst()) {
            do {
                Name name = new Name(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTACT)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CUSTOMER)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKINTIME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKOUTTIMESTATUS)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SYSTEMID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VISITPURPOSE)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS))
                );
                names.add(name);
            }
            while (cursor.moveToNext());
        }
        nameAdapter = new NameAdapterout(this, R.layout.namesout, names,latitude,longitude,employee,pswd);
        listViewNames.setAdapter(nameAdapter);
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
    private void refreshList()
    {
        nameAdapter.notifyDataSetChanged();
    }

}