package com.kauveryhospital.fieldforce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.widget.Toast;


import com.google.android.gms.location.LocationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MyLocationService extends BroadcastReceiver {
    public static final String ACCESS_PROCESS_UPDATE = "com.example.kmcfieldforceemp.UPDATE_LOCATION";
    String location_string,lat,lng;



    public static long TIME = 60000;
    @Override
    public void onReceive( Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(ACCESS_PROCESS_UPDATE.equals(action)){
                LocationResult result= LocationResult.extractResult(intent);
                if(result!=null){
                    Location location=result.getLastLocation();
                     location_string=new StringBuilder(""+location.getLatitude())
                            .append("/change/")
                            .append(location.getLongitude())
                            .toString();
               lat= String.valueOf(location.getLatitude());
               lng=String.valueOf(location.getLongitude());
                    try {
                        WelcomeActivity.getInstance().updateTextView(location_string);
                   // MainActivity.getInstance().updateTextView(location_string);
                    }
                    catch (Exception ex)
                    {
                       Toast.makeText(context,location_string, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
