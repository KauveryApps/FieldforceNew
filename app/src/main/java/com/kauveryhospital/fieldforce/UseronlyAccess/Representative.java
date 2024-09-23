package com.kauveryhospital.fieldforce.UseronlyAccess;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.APIClientOhc;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.APIInterfaceOhc;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.GetcheckoutOhc;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIClientadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIInterfaceadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Exampleadmin;
import com.kauveryhospital.fieldforce.UserAdmin.LatLngModelClass;
import com.kauveryhospital.fieldforce.UserAdmin.Mapdata.APIClientPlan;
import com.kauveryhospital.fieldforce.UserAdmin.Mapdata.APIInterfacePlan;
import com.kauveryhospital.fieldforce.UserAdmin.Mapdata.Getcheckout;
import com.kauveryhospital.fieldforce.UseronlyAccess.EmployeeMapping.EmpContactMapping;
import com.kauveryhospital.fieldforce.UseronlyAccess.MyAgendaToday.PlannedCheckInActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.contact.ContactActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.corporate.CorporateActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.LeaveRequesttesting;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.CheckoutActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.TravelExpActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.UnPlannedActivity;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.APIClient;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.APIInterface;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.Example;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.WorkStartActivity;

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

public class Representative extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,SharedPreferences.OnSharedPreferenceChangeListener{
    private NetworkChangeReceiver networkChangeReceiver;
    Button btn_checkout,btn_start_location_updates;
    APIInterfaceOhc apiInterfaceplan;
    static Representative instance;
    APIInterfacePlan apiInterfaceplans;
    double lat, lng, lats, lngs;
    TextView empname;
    APIInterface apiInterface;
    double sum = 0;
    ImageView refresh;
    private List<LatLngModelClass> latLngModelClassList;
    ArrayList points = new ArrayList();
    APIInterfaceadmin apiInterfaceadm;
    String latitude,longitude,currentDateString3,truncdates,truncdate,currentDateTimeString,pswd,workstartid,employeename,createdon,dateshrdcurrentdate,message,currentDateString2,currentDateTimeString1;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    int j = 0;
    PendingResult<LocationSettingsResult> result;
    final static int REQUEST_LOCATION = 199;
    double crlongitude,crlatitude;
    private GoogleApiClient googleApiClient;
    private static final String PREFS_NAME = "preferenceName";
    private static final String PREFS_NAMES = "loginpref";
    private static final String PREFS_NAMESS="punch";
    CardView corporate,chkoutact,contact,leaverequest,unplannedvisit,travelexp,employeemapping,plannedvisit;
    SimpleDateFormat formatter,formatter1,formatter2,formatter3;
    Date todayDate,todaydate1,todaydate2,todaydate3;
    public Representative() {
        // .Required empty public constructor//
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_representative, container, false);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
        }
        final SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        btn_checkout=root.findViewById(R.id.btn_checkout);
        latitude= settings.getString("curlatitude","curlatitude");
        longitude=settings.getString("curlongitude","curlongitude");
        SharedPreferences settings1 = getActivity().getSharedPreferences(PREFS_NAMES, 0);
        employeename=settings1.getString("username","");
        pswd=settings1.getString("password","");
        todaydate1=Calendar.getInstance().getTime();
        todaydate2=Calendar.getInstance().getTime();
        todaydate3=Calendar.getInstance().getTime();
        formatter1=new SimpleDateFormat("dd/MM/yyyy");
        formatter2=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            formatter3=new SimpleDateFormat("YYYY-MM-dd");
        }
        currentDateTimeString1=formatter1.format(todaydate1);
        currentDateString2=formatter2.format(todaydate2);
        currentDateString3=formatter3.format(todaydate3);
        empname=root.findViewById(R.id.empname);
        empname.setText(employeename);
        corporate=root.findViewById(R.id.corporate);
        chkoutact=root.findViewById(R.id.chkoutact);
        contact=root.findViewById(R.id.contact);
        leaverequest=root.findViewById(R.id.leaverequest);
        unplannedvisit=root.findViewById(R.id.unplanvisits);
        refresh=root.findViewById(R.id.refresh);
        travelexp=root.findViewById(R.id.travelexp);
        employeemapping=root.findViewById(R.id.employeemapping);
        plannedvisit=root.findViewById(R.id.plannedvisit);
        btn_start_location_updates=root.findViewById(R.id.btn_start_location_updates);
        Checkingdatain1();
        btn_start_location_updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latitude.isEmpty()) {
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    LocationListener locationListener = new MyLocationListener();
                    if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                        // Toast not showing
                        if(!latitude.isEmpty()){
                            try
                            {
                                postData();
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                            {
                            Toast.makeText(getContext(), "please move to one meter distance", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else  {

                    }
                }
                else if(!latitude.isEmpty()){
                    try
                    {
                        postData();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latitude.isEmpty()) {
                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                    LocationListener locationListener = new MyLocationListener();
                    if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);

                        if(!latitude.isEmpty()){
                           //Checkingdata();
                          //Checkingdatain2();
                            onBackPressed11();
                        }
                        else{
                            Toast.makeText(getContext(), "please move to one meter distance", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else  {

                    }
                }
                else if(!latitude.isEmpty()){
                    //Checkingdata();
                   // Checkingdatain2();
                    onBackPressed11();
                }
            }
        });
        corporate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CorporateActivity.class);
                startActivity(intent);

            }
        });
        chkoutact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), ContactActivity.class);
               startActivity(intent);
            }
        });
        leaverequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LeaveRequesttesting.class);
                startActivity(intent);
            }
        });
        unplannedvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UnPlannedActivity.class);
                startActivity(intent);
            }
        });
        travelexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TravelExpActivity.class);
                startActivity(intent);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), WorkStartActivity.class);
                startActivity(intent);
            }
        });
        employeemapping.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), EmpContactMapping.class);
                startActivity(intent);
            }
        });
        plannedvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlannedCheckInActivity.class);
                startActivity(intent);
            }
        });
        return root;

    }
    public void onBackPressed11() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do You Want to checkout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Checkingdatain2();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    { dialog.cancel();}
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public static Representative getInstance()
    { return instance; }
    @Override
    public void onResume()
    { super.onResume();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect(); }

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
                //final LocationSettingsStates state = result.getLocationSettingsStates();//
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
                            status.startResolutionForResult(getActivity(), REQUEST_LOCATION);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("onActivityResult()", Integer.toString(resultCode));

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode)
        {
            case REQUEST_LOCATION:
                switch (resultCode)
                {
                    case Activity.RESULT_OK:
                    {
                        // All required changes were successfully made
                        Toast.makeText(getActivity(), "Location enabled by user!", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case Activity.RESULT_CANCELED:
                    {
                        // The user was asked to change settings, but chose not to
                        Toast.makeText(getActivity(), "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    default:
                    {
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            crlongitude = loc.getLongitude();
            crlatitude = loc.getLatitude();
            latitude= String.valueOf(crlatitude);
            longitude= String.valueOf(crlongitude);
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
    private void Checkingdatain1()
    {
        final ProgressDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Loading ...");
        loading.show();
        apiInterfaceplan = APIClientOhc.getClient().create(APIInterfaceOhc.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", employeename);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s","");
        jsonObject5.addProperty("sql","select count(*)cnt,work_startid from (select row_number() over ( PARTITION by USERNAME order by CREATEDON desc ) sno,WORK_STARTid,CREATEDON,STATUS,STATUSFLG from  WORK_START  where USERNAME='"+employeename+"') where sno=1 and STATUS=1 and STATUSFLG=1 group by work_startid");
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

        Call<GetcheckoutOhc> call4=apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<GetcheckoutOhc>() {
            @Override
            public void onResponse(Call<GetcheckoutOhc> call, Response<GetcheckoutOhc> response) {
                loading.dismiss();


                try {
                    if(response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0"))
                    {
                        btn_start_location_updates.setVisibility(View.VISIBLE);
                        btn_checkout.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        btn_checkout.setVisibility(View.VISIBLE);
                        btn_start_location_updates.setVisibility(View.INVISIBLE);
                    }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetcheckoutOhc> call, Throwable t) {
                loading.dismiss();
                btn_checkout.setVisibility(View.INVISIBLE);
                btn_start_location_updates.setVisibility(View.VISIBLE);
            }
        });
    }
    private void Checkingdatain2()
    {
        final ProgressDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Loading ...");
        loading.show();
        apiInterfaceplan = APIClientOhc.getClient().create(APIInterfaceOhc.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", employeename);
        jsonObject5.addProperty("password", pswd);

        jsonObject5.addProperty("s","");
        jsonObject5.addProperty("sql","select count(*)cnt,work_startid,trunc(createdon)truncdate from (select row_number() over ( PARTITION by USERNAME order by CREATEDON desc ) sno,WORK_STARTid,CREATEDON,STATUS,STATUSFLG from  WORK_START  where USERNAME='"+employeename+"') where sno=1 and STATUS=1 and STATUSFLG=1 group by work_startid,createdon");

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

        Call<GetcheckoutOhc> call4=apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<GetcheckoutOhc>() {
            @Override
            public void onResponse(Call<GetcheckoutOhc> call, Response<GetcheckoutOhc> response) {
                loading.dismiss();


                try {
                    if(response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0"))
                    {
                      //  btn_start_location_updates.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        workstartid=response.body().getResult().get(0).getResult().getRow().get(0).getWork_startid();
                        truncdate=response.body().getResult().get(0).getResult().getRow().get(0).getTruncdate();
                        SimpleDateFormat formats1 = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat formats2 = new SimpleDateFormat("dd-MMM-yyyy");
                        Date date = formats1.parse(truncdate);
                        truncdates=formats2.format(date);
                        postData1(workstartid,truncdates);
                    }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetcheckoutOhc> call, Throwable t) {
                loading.dismiss();
                btn_start_location_updates.setVisibility(View.VISIBLE);
            }
        });
    }
    public void postData() throws JSONException {
        // RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        todaydate2=Calendar.getInstance().getTime();
        formatter2=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        currentDateString2=formatter2.format(todaydate2);
        final ProgressDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Loading Work start...");
        loading.show();
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
        jsonParams1.addProperty("username",employeename);
        jsonParams1.addProperty("latitude", latitude);
        jsonParams1.addProperty("longitude", longitude);

        object.addProperty("rowno","001");
        object.addProperty("text","0");
        object.add("columns",jsonParams1);

        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username",employeename);
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


        // Enter the correct url for your api service site
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Example> call2=apiInterface.getResult(jsonObject2);
        call2.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response)
            {
                loading.dismiss();

                try {
                    if(response.body().getResult().get(0).getError()!=null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        Checkingdatain1();
                    }
                    else if(response.body().getResult().get(0).getMessage()!=null){
                        message= response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getActivity(), WorkStartActivity.class));
                        Checkingdatain1();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(),"Server Problem ,Please Wait...", Toast.LENGTH_LONG).show();
                Checkingdatain1();
            }
        });
    }
  /*  private void Checkingdata()
    {
        todayDate = Calendar.getInstance().getTime();
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        currentDateTimeString = formatter.format(todayDate);
        final ProgressDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Loading work end...");
        loading.show();
        apiInterfaceplan = APIClientOhc.getClient().create(APIInterfaceOhc.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", employeename);
        jsonObject5.addProperty("password", pswd);

        jsonObject5.addProperty("s","");
        jsonObject5.addProperty("sql","select count(*)cnt,work_startid  from work_start where username='"+employeename+"' and  sdate='"+currentDateTimeString+"' group by work_startid ");

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

        Call<GetcheckoutOhc> call4=apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<GetcheckoutOhc>() {
            @Override
            public void onResponse(Call<GetcheckoutOhc> call, Response<GetcheckoutOhc> response) {
                loading.dismiss();
                try {
                    if(response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0"))
                    {

                    }
                    else
                    {
                        workstartid=response.body().getResult().get(0).getResult().getRow().get(0).getWork_startid();
                        postData1(workstartid, truncdate);
                    }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetcheckoutOhc> call, Throwable t) {
                loading.dismiss();
               // Toast.makeText(getContext(),"Please CheckIn Before We CheckOut ...", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    public void postData1(String workstartid, final String truncdates) throws JSONException {

        todaydate2=Calendar.getInstance().getTime();
        formatter2=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        currentDateString2=formatter2.format(todaydate2);
        final ProgressDialog loading = new ProgressDialog(getContext());
        loading.setMessage("Loading Work End...");
        loading.show();
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
        jsonParams1.addProperty("endtime",currentDateString2);
        jsonParams1.addProperty("username",employeename);
        jsonParams1.addProperty("latitude", latitude);
        jsonParams1.addProperty("longitude", longitude);
        jsonParams1.addProperty("workstartid", workstartid);
        object.addProperty("rowno","001");
        object.addProperty("text","0");
        object.add("columns",jsonParams1);

        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username",employeename);
        jsonParams2.addProperty("password",pswd);
        jsonParams2.addProperty("transid","woend");
        jsonParams2.addProperty("recordid","0");

        array5.add(object);
        jsonParams3.add("axp_recid1",array5);
        array.add(jsonParams3);
        jsonParams2.add("recdata",array);
        jsonObject.add("savedata",jsonParams2);
        array2.add(jsonObject);
        jsonObject2.add("_parameters",array2 );

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Example> call2=apiInterface.getResult(jsonObject2);
        call2.enqueue(new Callback<Example>()
        {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response)
            {
                loading.dismiss();
                try {
                    if(response.body().getResult().get(0).getError()!=null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                    else if(response.body().getResult().get(0).getMessage()!=null){
                        message= response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        postdatamap(truncdates);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(),"Server Problem ,Please Wait...", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void postdatamap(final String truncdates) {
        sum=0;
        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Loading Tracking...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceadm = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", employeename);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select latitude,longitude,userid,createdon from location_track where userid='" + employeename + "' and trunc(createdon)='" +truncdates + "' order by createdon");
        array.put(jsonObject5);
        try {
            jsonObject6.add("getchoices", jsonObject5);
            JsonArray array1 = new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters", array1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<Exampleadmin> call4 = apiInterfaceadm.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {
                loading.dismiss();
                latLngModelClassList = new ArrayList<>();


                try {
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        latitude = response.body().getResult().get(0).getResult().getRow().get(i).getLatitude();
                        longitude = response.body().getResult().get(0).getResult().getRow().get(i).getLongitude();
                        createdon = response.body().getResult().get(0).getResult().getRow().get(i).getCreatedon();
                        lat = Double.parseDouble(latitude);
                        lng = Double.parseDouble(longitude);
                        LatLngModelClass mapDataclass = new LatLngModelClass();
                        mapDataclass.setLatitude(lat);
                        mapDataclass.setLongitude(lng);
                        latLngModelClassList.add(mapDataclass);
                        double latitude1 = Double.parseDouble(response.body().getResult().get(0).getResult().getRow().get(i).getLatitude());
                        double longitude1 = Double.parseDouble(response.body().getResult().get(0).getResult().getRow().get(i).getLongitude());
                        double latitude2 = Double.parseDouble(response.body().getResult().get(0).getResult().getRow().get(i + 1).getLatitude());
                        double longitude2 = Double.parseDouble(response.body().getResult().get(0).getResult().getRow().get(i + 1).getLongitude());
                        //haversine(latitude1, longitude1, latitude2, longitude2);//
                        CalculationByDistance(latitude1, longitude1, latitude2, longitude2);
                    }
                } catch (IndexOutOfBoundsException e) {
                    try
                    {
                        Double sum1=sum/1000;
                        Updatekilometer(String.valueOf(sum1),truncdates);
                    }
                    catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Route Was Not Tracked", Toast.LENGTH_LONG).show();
                Checkingdatain1();
            }
        });
    }
   /* public void haversine(double lat1, double lon1, double lat2, double lon2) {
        double Rad = 6372.8;

        double dlat = Math.toRadians(lat2 - lat1);
        double dlon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.sin(dlon / 2) * Math.sin(dlon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double haverdistancekm = Rad * c;
        sum = sum + haverdistancekm;
        List<Double> newList = new ArrayList<Double>();
        newList.add(sum);

        Toast.makeText(getActivity(), "Total kilometers" + sum, Toast.LENGTH_LONG).show();

        Log.d("distanceee", "haversine: " + sum);
        try {
            Updatekilometer(String.valueOf(sum));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
   public double CalculationByDistance(double initialLat, double initialLong, double finalLat, double finalLong){
       /*PRE: All the input values are in radians!*/
       double latDiff = finalLat - initialLat;
       double longDiff = finalLong - initialLong;
       double earthRadius = 6371; //In Km if you want the distance in km
       double distance = 2*earthRadius*Math.asin(Math.sqrt(Math.pow(Math.sin(latDiff/2.0),2)+Math.cos(initialLat)*Math.cos(finalLat)*Math.pow(Math.sin(longDiff/2),2)));
       sum= sum+distance;
       return sum;
   }
    private void Updatekilometer(String sum, String truncdates) throws JSONException
    {
        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Fetching Kilometers...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceplans = APIClientPlan.getClient().create(APIInterfacePlan.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", employeename);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "update work_start SET kilometer='" + sum + "' where username='" + employeename + "' and  trunc(createdon)='" + truncdates + "'");
        array.put(jsonObject5);
        try {
            jsonObject6.add("getchoices", jsonObject5);
            JsonArray array1 = new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters", array1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Call<Getcheckout> call4 = apiInterfaceplans.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckout>() {
            @Override
            public void onResponse(Call<Getcheckout> call, Response<Getcheckout> response) {
                loading.dismiss();

                try {
                    if (response.body().getResult().get(0).getResult().getStatus() != null) {
                        message = response.body().getResult().get(0).getResult().getStatus();
                        Toast.makeText(getActivity(), "Updated Kilometers " + message, Toast.LENGTH_SHORT).show();
                        Checkingdatain1();
                    }
                    else if(response.body().getResult().get(0).getResult().getStatus()==null)
                    {
                        message = response.body().getResult().get(0).getResult().getStatus();
                        Toast.makeText(getActivity(), " Kilometers Not Updated" + message, Toast.LENGTH_SHORT).show();
                        Checkingdatain1();
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Getcheckout> call, Throwable t)
            {
                loading.dismiss();
                Toast.makeText(getActivity(), "server problem!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
