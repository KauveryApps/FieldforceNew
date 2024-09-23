package com.kauveryhospital.fieldforce.UseronlyAccess.unplanned;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIInterfaceSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ResultSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIClientSave;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.DatabaseHelper;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.Name;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.NameAdapter;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfflineActivity extends AppCompatActivity implements NetworkChangeCallback {
    ListView listView;
    String[] number;
    long systemidlng;
    ImageView backarrow;
    final static int REQUEST_LOCATION = 199;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;
    int vposition, cposition;
    APIInterfaceSave apiInterfaceSave;
    private ListView listViewNames;
    private NetworkChangeReceiver networkChangeReceiver;
    boolean first = true, second = true;
    private String[] fruits = {"Select Contact Type", "Ambulance Driver", "Corporate Name", "Doctor", "Others"};
    private String[] vistpurp = {"Select Visit purpose", "CME Initation Given", "Give Patient Feedback", "Monthly Review Meeting", "MOU", "Office Visit", "OHC", "Refferal Visit", "Routine or Monthly Visit", "To give Dairy and Calendar", "To give patient Feedback", "To Give Ref Fee", "To give sweets", "To Invite", "To Receive lapse cheque"};
    private Spinner spFruit, visit_purpose;
    List<ResultSave> listsave = new ArrayList<>();
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    EditText edtcustomer, edchevkintime, edlatitude, edlongitude, edaddresss;
    String customer, systemid, contact, visitpurpose, latitudes, longitudes, checkouttimestatus, employee, pswd, latitude, longitude, message, conttype, checkintime, currentDateTimeString, address;
    public static final String PREFS_NAME = "loginpref";
    int j = 0;
    Context mContext;
    LocationManager locationManager;
    private DatabaseHelper db;
    Button btnLogin;
    private NameAdapter nameAdapter;
    private List<Name> names;
    static final String PREFS_NAMES = "preferenceName";
    public static final String DATA_SAVED_BROADCAST = "com.kauveryhospital.fieldforce";
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    private static final int REQUEST_CHECK_SETTINGS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        mContext = this;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListenerGPS);
        isLocationEnabled();
        db = new DatabaseHelper(this);
        backarrow=findViewById(R.id.backarrow);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        currentDateTimeString = formatter.format(todayDate);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        employee = set.getString("username", "");
        pswd = set.getString("password", "");
//        SharedPreferences settings = getSharedPreferences(PREFS_NAMES, 0);
//        latitude = settings.getString("curlatitude", "");
//        longitude = settings.getString("curlongitude", "");
        edtcustomer = findViewById(R.id.edtcustomer);
        btnLogin=findViewById(R.id.btnLogin);
        spFruit = findViewById(R.id.conttype);
        edchevkintime = findViewById(R.id.edchevkintime);
        edlatitude = findViewById(R.id.edlatitude);
        edlongitude = findViewById(R.id.edlongitude);
        edaddresss = findViewById(R.id.edaddresss);
        visit_purpose = findViewById(R.id.visitpurpose);
        listViewNames = findViewById(R.id.listViewNames);
        names = new ArrayList<Name>();
        edchevkintime.setText(currentDateTimeString);


        loadNames();
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfflineActivity.this, UnPlannedActivity.class);
                startActivity(intent);
            }
        });
        spFruit.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, fruits));
        spFruit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int cposition, long id) {
                if (first) {
                    first = false;
                } else {
                    if (cposition == 0) {
                        //  Toast.makeText(OfflineActivitty.this, "Please select appropriate option!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //   Toast.makeText(OfflineActivitty.this, fruits[cposition] + " Selected !", Toast.LENGTH_SHORT).show();
                        contact=fruits[cposition];
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Toast.makeText(OfflineActivitty.this, "Please select appropriate option!", Toast.LENGTH_SHORT).show();
            }

        });
        visit_purpose.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,vistpurp));
        visit_purpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int vposition, long id)
            {
                if (second) {
                    second = false;
                } else {
                    if (vposition == 0) {
                        // Toast.makeText(OfflineActivitty.this, "Please select appropriate option!", Toast.LENGTH_SHORT).show();
                    } else {
                        //  Toast.makeText(OfflineActivitty.this, vistpurp[vposition] + " Selected !", Toast.LENGTH_SHORT).show();
                        visitpurpose=vistpurp[vposition];
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                //  Toast.makeText(OfflineActivitty.this, "Please select appropriate optionrrr!", Toast.LENGTH_SHORT).show();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }

        });
    }
    LocationListener locationListenerGPS=new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            latitudes= String.valueOf(latitude);
            longitudes=String.valueOf(longitude);
            edlatitude.setText(latitudes);
            edlongitude.setText(longitudes);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }

    private void isLocationEnabled() {


        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(OfflineActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(OfflineActivity.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                }
            }
        });
    }

    private void validation() {
        try {
            customer = edtcustomer.getText().toString().trim();
            address = edaddresss.getText().toString().trim();
            checkintime = currentDateTimeString;
            checkouttimestatus="false";
            systemidlng=System.currentTimeMillis();
            systemid= String.valueOf(systemidlng);
            postData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);


    }
    private void loadNames() {
        names.clear();
        Cursor cursor = db.getNames();
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
        nameAdapter = new NameAdapter(this, R.layout.names, names);
        listViewNames.setAdapter(nameAdapter);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        isLocationEnabled();
    }

    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity", "Status: " + status);
        if (status == false) {
            isLocationEnabled();
            Toast.makeText(OfflineActivity.this, "You are in Offline!We are store the entry in local", Toast.LENGTH_LONG).show();
        } else {
            isLocationEnabled();
            refreshList();
            Cursor cursor = db.getUnsyncedNames();
            if (cursor.moveToFirst()) {
                do {

                    //calling the method to save the unsynced name to MySQL//
                    try {
                        postData1(
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTACT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CUSTOMER)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKINTIME)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKOUTTIMESTATUS)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SYSTEMID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEE)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VISITPURPOSE))
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }

        }
    }
    public void postData() throws JSONException {
        if (TextUtils.isEmpty(customer)) {
            edtcustomer.setError("Please enter customer name", null);
            edtcustomer.requestFocus();
            return;
        }

        else   if(TextUtils.isEmpty(visitpurpose))
        {
            visit_purpose.getPrompt();
            visit_purpose.requestFocus();
            Toast.makeText(OfflineActivity.this, "Please select visit purpose!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(contact)){
            spFruit.getPrompt();
            spFruit.requestFocus();
            Toast.makeText(OfflineActivity.this, "Please select contact type!", Toast.LENGTH_SHORT).show();
        }
        else {
            final ProgressDialog loading = new ProgressDialog(OfflineActivity.this);
            loading.setCancelable(false);
            loading.setMessage("Saving....");
            loading.show();

            //RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//
            apiInterfaceSave = APIClientSave.getClient().create(APIInterfaceSave.class);
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
            jsonParams1.addProperty("remarks",employee);
            jsonParams1.addProperty("address", address);
            jsonParams1.addProperty("checkin", checkintime);
            jsonParams1.addProperty("systemid",systemid);
            jsonParams1.addProperty("coutstatus",checkouttimestatus);
            jsonParams1.addProperty("c_latitude", latitudes);
            jsonParams1.addProperty("c_longitude", longitudes);
            object.addProperty("rowno", "001");
            object.addProperty("text", "0");
            object.add("columns", jsonParams1);
            jsonParams2.addProperty("axpapp", "fieldforce");
            jsonParams2.addProperty("s", "");
            jsonParams2.addProperty("username", employee);
            jsonParams2.addProperty("password", pswd);
            jsonParams2.addProperty("transid", "ochin");
            jsonParams2.addProperty("recordid", "0");
            array5.add(object);
            jsonParams3.add("axp_recid1", array5);
            array.add(jsonParams3);
            jsonParams2.add("recdata", array);
            jsonObject.add("savedata", jsonParams2);
            array2.add(jsonObject);
            jsonObject2.add("_parameters", array2);
            // Enter the correct url for your api service site//
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
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } else if (response.body().getResult().get(0).getMessage() != null) {
                            message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(CorporateActivity.this, TabbedActivity.class));//
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ExampleSave> call, Throwable t) {
                    loading.dismiss();
                    //  loadNames();
                    //  savecorpname = editTextName.getText().toString().trim();
                    saveNameToLocalStorage(contact, customer, checkintime,checkouttimestatus,systemid, latitudes, longitudes, address,employee, visitpurpose, NAME_NOT_SYNCED_WITH_SERVER);
                    Toast.makeText(OfflineActivity.this, "Data saved in Local Storage!!!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void postData1(final int id, final String contact,final String customer,final  String checkintime,final String checkouttimestatus,final String systemid,final  String latitude,final String longitude,final  String address,final  String employee,final String visitpurpose) throws JSONException {
        refreshList();
        final ProgressDialog loading = new ProgressDialog(OfflineActivity.this);
        loading.setCancelable(false);
        loading.setMessage("Saving ...");
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
        jsonParams2.addProperty("transid", "ochin");
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
                        Toast.makeText(OfflineActivity.this, message, Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        refreshList();
                        db.updateNameStatus(id, OfflineActivity.NAME_SYNCED_WITH_SERVER);
                        loadNames();
                        refreshList();
                        //sending the broadcast to refresh the list
                        getApplicationContext().sendBroadcast(new Intent(OfflineActivity.DATA_SAVED_BROADCAST));
                        loadNames();
                        Toast.makeText(OfflineActivity.this, message, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(CorporateActivity.this, TabbedActivity.class));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ExampleSave> call, Throwable t) {
                loading.dismiss();

                Toast.makeText(OfflineActivity.this, "server problem!!!", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void saveNameToLocalStorage(String contact, String customer, String checkintime,String checkouttimestatus,String systemid, String latitude, String longitude, String address, String employee,String visitpurpose, int status) {
        //  editTextName.setText("");
        edtcustomer.setText("");
        edaddresss.setText(" ");
        db.addName(contact,customer,checkintime,checkouttimestatus,systemid,latitude,longitude,address,employee,visitpurpose,status);
        db.addNamechkout(contact,customer,checkintime,checkouttimestatus,systemid,latitude,longitude,address,employee,visitpurpose,status);
        Name n = new Name(contact,customer,checkintime,checkouttimestatus,systemid,latitude,longitude,address,employee,visitpurpose, status);
        names.add(n);
        refreshList();
    }
    private void refreshList() {
        nameAdapter.notifyDataSetChanged();
    }
}
