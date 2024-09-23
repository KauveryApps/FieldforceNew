package com.kauveryhospital.fieldforce.OHCOnly;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.kauveryhospital.fieldforce.OHCOnly.Globaldeclare.APIClientgohc;
import com.kauveryhospital.fieldforce.OHCOnly.Globaldeclare.APIInterfacegohc;
import com.kauveryhospital.fieldforce.OHCOnly.Globaldeclare.Examplegohc;

import com.kauveryhospital.fieldforce.OHCOnly.getdata.APIClientOhc;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.APIInterfaceOhc;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.GetcheckoutOhc;
import com.kauveryhospital.fieldforce.R;

import com.kauveryhospital.fieldforce.workstartserviceuseronly.APIClient;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.APIInterface;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.Example;

import com.kauveryhospital.fieldforce.workstartserviceuseronly.MyBackgroundService;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.Result;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.WorkStartActivity;


import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OHCEmployee extends Fragment  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,SharedPreferences.OnSharedPreferenceChangeListener {
    Button btn_checkin, btn_checkout;
    long tomindate;
    String mfrmDate, pswd, alterdates;
    String strAdd = "";
    double crlongitude, crlatitude;
    int j = 0;
    HashMap<String, String> map;
    APIInterfacegohc apiInterfacegohc;
    APIInterface apiInterface;
    ArrayList<HashMap<String, String>> arraylist;
    ListOhcAdapter adapter;
    String message, currentDateTimeString, currentDateTimeString1, uname, latitude, longitude, workstartid, currentDateString2, username, starttime, endtime;
    List<Result> listsave = new ArrayList<Result>();
    APIInterfaceOhc apiInterfaceplan;
    ImageView fromdate, refresh;
    Calendar upDateFroms;
    TextView frmbtnDatePicker, tobtnDatePicker, empname;
    Calendar upDateFrom;
    DatePickerDialog mDatePicker;
    int mDay;
    MyBackgroundService mService = null;

    boolean mBound = false;
    String myquery;
    RecyclerView recyclerViewohc;
    private int year, month, day, days;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    PendingResult<LocationSettingsResult> result;
    final static int REQUEST_LOCATION = 199;
    SimpleDateFormat formatter2, formatter1;
    Date todaydate2, todaydate1;
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
    public OHCEmployee() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_o_h_c_employee, container, false);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        if ((ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
        }
        empname = root.findViewById(R.id.empname);
        btn_checkin = root.findViewById(R.id.btn_checkin);
        refresh = root.findViewById(R.id.refresh);
        recyclerViewohc = root.findViewById(R.id.recyclerViewohc);
        btn_checkout = root.findViewById(R.id.btn_checkout);
        SharedPreferences pref = getActivity().getSharedPreferences("preferenceName", 0);
        latitude = pref.getString("curlatitude", "");
        longitude = pref.getString("curlongitude", "");
        SharedPreferences preference = getActivity().getSharedPreferences("loginpref", 0);
        uname = preference.getString("username", "");
        pswd = preference.getString("password", "");
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        currentDateTimeString = formatter.format(todayDate);
        fromdate = root.findViewById(R.id.fromdate);
        frmbtnDatePicker = root.findViewById(R.id.frmbtnDatePicker);
        empname.setText(uname);
        recyclerViewohc.setAdapter(null);
        postdatastate1();
        Checkingdatain1();
        btn_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latitude.isEmpty()) {
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    LocationListener locationListener = new MyLocationListener();
                    if ((ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                        if (!latitude.isEmpty()) {
                            Checkingdatain();
                        } else {
                            Toast.makeText(getContext(), "please move to one meter distance", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {

                    }
                } else if (!latitude.isEmpty()) {
                    double lats = Double.parseDouble(latitude);
                    double lngss = Double.parseDouble(longitude);
                    getCompleteAddressString(lats, lngss);
                    Checkingdatain();
                }
            }
        });
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (latitude.isEmpty()) {
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    LocationListener locationListener = new MyLocationListener();
                    if ((ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                        // Toast not showing
                        if (!latitude.isEmpty()) {
                            //  Checkingdataout();
                            try {

                                double lats = Double.parseDouble(latitude);
                                double lngss = Double.parseDouble(longitude);
                                getCompleteAddressString(lats, lngss);
                                postData1(workstartid, strAdd);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getContext(), "please move to one meter distance", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                    }
                } else if (!latitude.isEmpty()) {
                    try {

                        double lats = Double.parseDouble(latitude);
                        double lngss = Double.parseDouble(longitude);
                        getCompleteAddressString(lats, lngss);
                        postData1(workstartid, strAdd);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WorkStartOHC.class));
                postdatastate1();
            }
        });
        return root;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            //  Toast.makeText(getContext(), "Click CheckIn One More Time", Toast.LENGTH_SHORT).show();   // Toast not showing
            crlongitude = loc.getLongitude();
            crlatitude = loc.getLatitude();
            latitude = String.valueOf(crlatitude);
            longitude = String.valueOf(crlongitude);
            getCompleteAddressString(crlatitude, crlongitude);
        }

        @Override
        public void onProviderDisabled(String provider) {
            //  Toast.makeText(getContext(), "onProviderDisabled", Toast.LENGTH_SHORT).show();  // Toast not showing
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Toast.makeText(getContext(), "on ProviderEnabled", Toast.LENGTH_SHORT).show();  // Toast not showing
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Toast.makeText(getContext(), "onStatusChanged", Toast.LENGTH_SHORT).show();  // Toast not showing
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.d("ss", strReturnedAddress.toString());
            } else {
                Log.d("My", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Msss", "Cannot get Address!");
        }
        return strAdd;
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                //final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        //...
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    getActivity(),
                                    REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        //...
                        break;
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult()", Integer.toString(resultCode));

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);//

        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        // All required changes were successfully made
                        Toast.makeText(getActivity(), "Location enabled by user!", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case Activity.RESULT_CANCELED: {
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(getActivity(), "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void postData(String strAdd) throws JSONException {
        todaydate2 = Calendar.getInstance().getTime();
        formatter2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        currentDateString2 = formatter2.format(todaydate2);
        //RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//
        final ProgressDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Loading Work start...");
        loading.show();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        JsonObject object = new JsonObject();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        JsonArray array2 = new JsonArray();

        JsonArray array5 = new JsonArray();
        JsonArray array = new JsonArray();

        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams3 = new JsonObject();

        jsonParams1.addProperty("starttime", currentDateString2);
        jsonParams1.addProperty("username", uname);
        jsonParams1.addProperty("latitude", latitude);
        jsonParams1.addProperty("longitude", longitude);
        jsonParams1.addProperty("chkinaddress", strAdd);
        object.addProperty("rowno", "001");
        object.addProperty("text", "0");
        object.add("columns", jsonParams1);
        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username", uname);
        jsonParams2.addProperty("password", pswd);
        jsonParams2.addProperty("transid", "start");
        jsonParams2.addProperty("recordid", "0");

        array5.add(object);
        jsonParams3.add("axp_recid1", array5);
        array.add(jsonParams3);
        jsonParams2.add("recdata", array);
        jsonObject.add("savedata", jsonParams2);
        array2.add(jsonObject);
        jsonObject2.add("_parameters", array2);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Example> call2 = apiInterface.getResult(jsonObject2);
        call2.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                loading.dismiss();
                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        postdatastate1();
                    } else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        postdatastate1();
                        btn_checkout.setVisibility(View.VISIBLE);
                        btn_checkin.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), WorkStartActivity.class));

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Server Problem ,Please Wait...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindService(Intent intent, ServiceConnection mServiceConnection, int bindAutoCreate) {
    }

    private void Checkingdatain() {
        final ProgressDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Loading ...");
        loading.show();
        apiInterfaceplan = APIClientOhc.getClient().create(APIInterfaceOhc.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);

        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "select count(*)cnt,work_startid from (select row_number() over ( PARTITION by USERNAME order by CREATEDON desc ) sno,WORK_STARTid,CREATEDON,STATUS,STATUSFLG from  WORK_START  where USERNAME='" + uname + "') where sno=1 and STATUS=1 and STATUSFLG=1 group by work_startid");

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

        Call<GetcheckoutOhc> call4 = apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<GetcheckoutOhc>() {
            @Override
            public void onResponse(Call<GetcheckoutOhc> call, Response<GetcheckoutOhc> response) {
                loading.dismiss();
                try {
                    if (response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0")) {

                        double lats = Double.parseDouble(latitude);
                        double lngss = Double.parseDouble(longitude);
                        getCompleteAddressString(lats, lngss);
                        postData(strAdd);
                    } else {

                        Toast.makeText(getContext(), "Please Checkout The Previous CheckIn...", Toast.LENGTH_SHORT).show();
                        workstartid = response.body().getResult().get(0).getResult().getRow().get(0).getWork_startid();
                        btn_checkout.setEnabled(true);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetcheckoutOhc> call, Throwable t) {
                loading.dismiss();
                try {

                    double lats = Double.parseDouble(latitude);
                    double lngss = Double.parseDouble(longitude);
                    getCompleteAddressString(lats, lngss);
                    postData(strAdd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void Checkingdatain1() {
        final ProgressDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Loading ...");
        loading.show();
        apiInterfaceplan = APIClientOhc.getClient().create(APIInterfaceOhc.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "select count(*)cnt,work_startid from (select row_number() over ( PARTITION by USERNAME order by CREATEDON desc ) sno,WORK_STARTid,CREATEDON,STATUS,STATUSFLG from  WORK_START  where USERNAME='" + uname + "') where sno=1 and STATUS=1 and STATUSFLG=1 group by work_startid");
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

        Call<GetcheckoutOhc> call4 = apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<GetcheckoutOhc>() {
            @Override
            public void onResponse(Call<GetcheckoutOhc> call, Response<GetcheckoutOhc> response) {
                loading.dismiss();
                try {
                    if (response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0")) {
                        btn_checkin.setVisibility(View.VISIBLE);
                        postdatastate1();
                    } else {
                        btn_checkout.setVisibility(View.VISIBLE);
                        postdatastate1();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetcheckoutOhc> call, Throwable t) {
                loading.dismiss();
                btn_checkin.setVisibility(View.VISIBLE);
            }
        });
    }
    public void postData1(String workstartid, String strAdd) throws JSONException {
        todaydate1 = Calendar.getInstance().getTime();
        formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        currentDateTimeString1 = formatter1.format(todaydate1);
        final ProgressDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Loading Work End...");
        loading.show();

        apiInterface = APIClient.getClient().create(APIInterface.class);

        JsonObject object = new JsonObject();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();


        JsonArray array2 = new JsonArray();
        JsonArray array5 = new JsonArray();
        JsonArray array = new JsonArray();

        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams3 = new JsonObject();

        jsonParams1.addProperty("endtime", currentDateTimeString1);
        jsonParams1.addProperty("username", uname);
        jsonParams1.addProperty("latitude", latitude);

        jsonParams1.addProperty("longitude", longitude);
        jsonParams1.addProperty("chkoutaddress", strAdd);
        jsonParams1.addProperty("workstartid", workstartid);

        object.addProperty("rowno", "001");
        object.addProperty("text", "0");
        object.add("columns", jsonParams1);

        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username", uname);
        jsonParams2.addProperty("password", pswd);

        jsonParams2.addProperty("transid", "woend");
        jsonParams2.addProperty("recordid", "0");

        array5.add(object);
        jsonParams3.add("axp_recid1", array5);
        array.add(jsonParams3);

        jsonParams2.add("recdata", array);
        jsonObject.add("savedata", jsonParams2);
        array2.add(jsonObject);

        jsonObject2.add("_parameters", array2);
        // Enter the correct url for your api service site
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Example> call2 = apiInterface.getResult(jsonObject2);
        call2.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                loading.dismiss();
                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    } else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        postdatastate1();
                        btn_checkin.setVisibility(View.VISIBLE);
                        btn_checkout.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Server Problem ,Please Wait...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postdatastate1() {
        apiInterfacegohc = APIClientgohc.getClient().create(APIInterfacegohc.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select a.USERNAME,to_char(a.STARTTIME,'dd-mm-yyyy  hh24:mi:ss') starttime,to_char(b.ENDTIME,'dd-mm-yyyy  hh24:mi:ss') endtime  from work_start a left join WORK_END b on a.WORK_STARTID=b.WORKSTARTID where trunc(a.createdon)>=trunc(sysdate-15) and a.USERNAME='" + uname + "' order by a.CREATEDON desc ");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);

            JsonArray array1 = new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters", array1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<Examplegohc> call4 = apiInterfacegohc.getResult(jsonObject7);
        call4.enqueue(new Callback<Examplegohc>() {

            @Override
            public void onResponse(Call<Examplegohc> call, Response<Examplegohc> response) {


                arraylist = new ArrayList<HashMap<String, String>>();

                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        username = response.body().getResult().get(0).getResult().getRow().get(i).getUsername();
                        starttime = response.body().getResult().get(0).getResult().getRow().get(i).getStarttime();
                        endtime = response.body().getResult().get(0).getResult().getRow().get(i).getEndtime();
                        map = new HashMap<String, String>();
                        map.put("empname", username);
                        map.put("starttime", starttime);
                        map.put("endtime", endtime);
                        arraylist.add(map);
                    }
                    adapter = new ListOhcAdapter(getActivity(), arraylist);
                    recyclerViewohc.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerViewohc.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Examplegohc> call, Throwable t) {
                recyclerViewohc.setAdapter(null);
                Toast.makeText(getActivity(), "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }
}





                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             