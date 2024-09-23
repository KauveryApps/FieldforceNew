package com.kauveryhospital.fieldforce.workstartserviceuseronly;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.kauveryhospital.fieldforce.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.TabbedActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class MyBackgroundService extends Service implements LocationListener {
    private static final String CHANNEL_ID = "my_channel";
    private static final String EXTRA_STARTED_FROM_NOTIFICATION = "com.example.kmcfieldforceemp.workstartservice" + ".started_from_notification";
    private final IBinder mBinder = new LocalBinder();
    private static final long UPDATE_INTERVAL_IN_MIL = 1000000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MIL = UPDATE_INTERVAL_IN_MIL / 2;
    private static final int NOTI_ID = 1223;
    private boolean mChangingConfiguration = false;
    private NotificationManager mNotificationManager;
    public static final String PREFS_NAME = "loginpref";

    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private Handler mServiceHandler;
    double latitudes, longitudes;
    private Location mLocation;
    double mylatitude;
    LocationManager locationManager;
    double mylongtitude;
    Location location;


    public double track_lat = 0.0;
    public double track_lng = 0.0;
    String lat, lng, currentDateTimeString, message, uname, pswd;
    List<Result> list = new ArrayList<Result>();
    private static final String PREFS_NAMES = "loginpref";
    APIInterface apiInterface;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;

    public MyBackgroundService() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());
            }
        };
        createLocationRequest();
        getLastLocation();
        HandlerThread handlerThread = new HandlerThread("EDMTDEV");
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(mChannel);
            mChannel.enableVibration(false);
            mChannel.setSound(null, null);
        }
        requestLocationUpdates();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean startedFromNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false);
        if (startedFromNotification) {

            removeLocationUpdates();
            stopSelf();

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration = true;

    }

    public void removeLocationUpdates() {
        try {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            Common.setRequestingLocationUpdates(this, false);
            stopSelf();
        } catch (SecurityException e) {
            Common.setRequestingLocationUpdates(this, true);
            Log.e("", "Lost location permission could not remove updates" + e);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        try {

            fusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLocation = task.getResult();
                            } else {
                                Log.e("EDMT_DEV", "Failed to get location");
                            }
                        }
                    });
        } catch (Exception e) {

            Log.e("EDMT_DEV", "Failed to get location" + e);
        }
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MIL);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MIL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void onNewLocation(Location lastLocation) {
        mLocation = lastLocation;
        EventBus.getDefault().postSticky(new SendLocationToActivity(mLocation));
        if (serviceIsRunningInForeGround(this))
            mNotificationManager.notify(NOTI_ID, getNotification());
    }

    private Notification getNotification() {
        Intent intent = new Intent(this, MyBackgroundService.class);
        String text = Common.getLocationText(mLocation);
        if (mLocation == null) {
            Toast.makeText(getApplicationContext(), "unknown location", Toast.LENGTH_SHORT).show();

        }
        fn_getlocation();
     //   mylatitude = mLocation.getLatitude();
      //  mylongtitude = mLocation.getLongitude();

        try {
            lat = String.valueOf(track_lat);
            lng = String.valueOf(track_lng);
            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            currentDateTimeString = formatter.format(todayDate);

            postData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);
        // PendingIntent servicePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, TabbedActivity.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // .addAction(R.drawable.launch, "Launch", activityPendingIntent)
                // .addAction(R.drawable.close, "Remove", activityPendingIntent)
                .setContentText(text)
                .setContentTitle(Common.getLocationTitle(this))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.kauverys)
                .setTicker(text)
                .setSound(null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
                .setVibrate(null)
                .setWhen(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            builder.setChannelId(CHANNEL_ID);
        }
        return builder.build();
    }
    private void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {
            Log.e(TAG, "CAN'T GET LOCATION");
            stopSelf();
        } else {
            if (isNetworkEnable) {
                location = null;
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
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {

                        Log.e(TAG, "isNetworkEnable latitude" + location.getLatitude() + "\nlongitude" + location.getLongitude() + "");
                        latitudes = location.getLatitude();
                        longitudes = location.getLongitude();
                        track_lat = latitudes;
                        track_lng = longitudes;
//                        fn_update(location);
                    }
                }
            }

            if (isGPSEnable) {
                location = null;
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
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        Log.e(TAG, "isGPSEnable latitude" + location.getLatitude() + "\nlongitude" + location.getLongitude() + "");
                        latitudes = location.getLatitude();
                        longitudes = location.getLongitude();
                        track_lat = latitudes;
                        track_lng = longitudes;
//                        fn_update(location);
                    }
                }
            }

            Log.e(TAG, "START SERVICE");


        }
    }
    private  boolean serviceIsRunningInForeGround(Context context){
        ActivityManager manager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service:manager.getRunningServices(Integer.MAX_VALUE))
            if(getClass().getName().equals(service.service.getClassName()))
                if(service.foreground)
                    return true;
        return false;
    }

    public void requestLocationUpdates() {
        Common.setRequestingLocationUpdates(this,true);
        startService(new Intent(getApplicationContext(),MyBackgroundService.class));
        try {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
        }
        catch (SecurityException ex)
        {
            Log.e("","Lost location permission"+ex);
        }

    }

    public void bindService() {
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    public class LocalBinder extends Binder {
        public MyBackgroundService getService()
        {
            return MyBackgroundService.this;
        }
    }

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        stopForeground(true);
        mChangingConfiguration=false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        stopForeground(true);
        mChangingConfiguration=false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if(!mChangingConfiguration && Common.requestingLocationUpdates(this))
            startForeground(NOTI_ID,getNotification());

        return super.onUnbind(intent);
    }

    /*  @Override
      public void onDestroy() {
          mServiceHandler.removeCallbacks(null);
          super.onDestroy();
      }
      @Override
      public void onTaskRemoved(Intent rootIntent) {
          Log.e("ClearFromRecentService", "END");
          //Code here
       //   stopSelf();
      }*/
    public void postData() throws JSONException {

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

        jsonParams1.addProperty("emp_name",uname);
        jsonParams1.addProperty("userid",uname);
        jsonParams1.addProperty("latitude", lat);
        jsonParams1.addProperty("longitude", lng);
        jsonParams1.addProperty("location_date",currentDateTimeString);

        object.addProperty("rowno","001");
        object.addProperty("text","0");
        object.add("columns",jsonParams1);
        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username",uname);
        jsonParams2.addProperty("password",pswd);
        jsonParams2.addProperty("transid","cultr");
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
                try {
                    if(response.body().getResult().get(0).getError()!=null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    else if(response.body().getResult().get(0).getMessage()!=null){
                        message= response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        //  startActivity(new Intent(MyBackgroundService.this, MainActivity.class));
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