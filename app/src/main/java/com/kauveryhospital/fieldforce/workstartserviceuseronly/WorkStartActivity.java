package com.kauveryhospital.fieldforce.workstartserviceuseronly;


import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kauveryhospital.fieldforce.AutoDownloadService;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;

import com.kauveryhospital.fieldforce.workstartserviceuseronly.getdata.APIClientPlans;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.getdata.APIInterfacePlans;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.getdata.Getcheckout;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.getdata.Result;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WorkStartActivity extends FragmentActivity implements OnMapReadyCallback, SharedPreferences.OnSharedPreferenceChangeListener {

    private GoogleMap mMap;
    Context mContext;
    LocationManager locationManager;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000000;
    static WorkStartActivity instance;
    TextView txtLocationResult, txtUpdatedOn, txtview;
    APIInterface apiInterface;
    List<com.kauveryhospital.fieldforce.workstartserviceuseronly.Result> listsave = new ArrayList<com.kauveryhospital.fieldforce.workstartserviceuseronly.Result>();
    private String mLastUpdateTime;
    List<Result> listss = new ArrayList<com.kauveryhospital.fieldforce.workstartserviceuseronly.getdata.Result>();
    APIInterfacePlans apiInterfaceplan;
    private static final String PREFS_NAMES = "loginpref";
    String message, currentDateString2,currentDateTimeString, currentDateTimeString1, uname, pswd, curlatitudes, curlongitudes;
    double lat, lng;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    private static final String TAG = WorkStartActivity.class.getSimpleName();
    // bunch of location related apis
    Button workstart, workstop, homebutton;

    MyBackgroundService mService = null;
    boolean mBound = false;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyBackgroundService.LocalBinder binder = (MyBackgroundService.LocalBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_start);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SharedPreferences settings1 = getSharedPreferences(PREFS_NAMES, 0);
        uname = settings1.getString("username", "");
        pswd=settings1.getString("password","");
        txtLocationResult = findViewById(R.id.location_result);
        txtview = findViewById(R.id.txtview);
        Date todaydate2=Calendar.getInstance().getTime();
        SimpleDateFormat formatter2=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        currentDateString2=formatter2.format(todaydate2);
        Log.d(TAG, "onCreate: "+currentDateTimeString);
        homebutton = findViewById(R.id.homebutton);
        txtUpdatedOn = findViewById(R.id.updated_on);
        workstart = findViewById(R.id.workstarted);
        Checkingdata();
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        workstop = findViewById(R.id.workstop);
                        workstart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mService.requestLocationUpdates();
//                                workstart.setBackgroundColor(Color.GRAY);
//                                Utils.preventTwoClick(view);

                            }
                        });
                        workstop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mService.removeLocationUpdates();
                            }
                        });
                        setButtonState(Common.requestingLocationUpdates(WorkStartActivity.this));
                        bindService(new Intent(WorkStartActivity.this, AutoDownloadService.class), mServiceConnection, Context.BIND_AUTO_CREATE);

                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    }
                }).check();
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WorkStartActivity.this, TabbedActivity.class));
            }
        });
    }
    LocationListener locationListenerGPS=new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location)
        {
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            curlatitudes= String.valueOf(latitude);
            curlongitudes=String.valueOf(longitude);
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
    private void Checkingdata()
    {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        currentDateTimeString = formatter.format(todayDate);
        final ProgressDialog loading = new ProgressDialog(WorkStartActivity.this);
        loading.setMessage("Loading Map...");
        loading.show();
        apiInterfaceplan = APIClientPlans.getClient().create(APIInterfacePlans.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s"," ");
        jsonObject5.addProperty("sql","select count(*)cnt  from work_start where username='"+uname+"' and trunc(createdon)= '"+currentDateTimeString+"'");

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
                try {
                    if(response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0"))
                    {
                        workstart.setVisibility(View.VISIBLE);
                        workstart.setEnabled(true);
                        workstart.setText("To Start Work");
                        postData();
                    }
                    else
                    {
                        workstart.setVisibility(View.VISIBLE);
                        workstart.setBackgroundColor(Color.GRAY);
                        workstart.setEnabled(false);
                        workstart.setText("Work Started Today");
                    }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Getcheckout> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Server Problem ,Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;

        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(Common.KEY_REQUESTING_LOCATION_UPDATES)) {
            setButtonState(sharedPreferences.getBoolean(Common.KEY_REQUESTING_LOCATION_UPDATES, false));
        }
    }


    @SuppressLint("ResourceAsColor")
    private void setButtonState(boolean isRequestEnable) {
        if (isRequestEnable) {
            workstart.setEnabled(true);
            workstart.setBackgroundColor(Color.RED);
            //  workstop.setEnabled(true);
            //workstop.setBackgroundColor(Color.GREEN);
        } else {
            workstart.setEnabled(false);
            workstart.setBackgroundColor(Color.GRAY);
            // workstop.setEnabled(false);
        }

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onListenLocation(SendLocationToActivity event) {
        if (event != null) {
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
            Toast.makeText(mService, data, Toast.LENGTH_SHORT).show();
            lat = event.getLocation().getLatitude();
            lng = event.getLocation().getLongitude();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera//
        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12.0f));

    }

    public void postData() throws JSONException {
        Date todaydate2=Calendar.getInstance().getTime();
        SimpleDateFormat formatter2=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        currentDateString2=formatter2.format(todaydate2);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        JsonObject object=new JsonObject();


        JsonObject jsonObject=new JsonObject();

        JsonObject jsonObject2=new JsonObject();

        JsonArray array2=new JsonArray();

        JsonArray array5=new JsonArray();
        JsonArray array=new JsonArray();

        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams3 = new JsonObject();

        jsonParams1.addProperty("starttime",currentDateString2);
        jsonParams1.addProperty("username",uname);
        jsonParams1.addProperty("latitude", lat);
        jsonParams1.addProperty("longitude", lng);

        object.addProperty("rowno","001");
        object.addProperty("text","0");
        object.add("columns",jsonParams1);

        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username",uname);
        jsonParams2.addProperty("password",pswd);
        jsonParams2.addProperty("transid","start");
        jsonParams2.addProperty("recordid","0");

        array5.add(object);
        jsonParams3.add("axp_recid1",array5);
        array.add(jsonParams3);
        jsonParams2.add("recdata",array);
        jsonObject.add("savedata",jsonParams2);
        array2.add(jsonObject);
        jsonObject2.add("_parameters",array2 );

        // Enter the correct url for your api service site//
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Example> call2=apiInterface.getResult(jsonObject2);
        call2.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response)
            {
                try {
                    if(response.body().getResult().get(0).getError()!=null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    else if(response.body().getResult().get(0).getMessage()!=null){
                        message= response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Server Problem ,Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


