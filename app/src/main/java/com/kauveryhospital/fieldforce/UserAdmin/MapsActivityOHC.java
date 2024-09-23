package com.kauveryhospital.fieldforce.UserAdmin;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIClientadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIInterfaceadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Exampleadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Result_admin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Resultadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Mapdata.APIClientPlan;
import com.kauveryhospital.fieldforce.UserAdmin.Mapdata.APIInterfacePlan;
import com.kauveryhospital.fieldforce.UserAdmin.Mapdata.Getcheckout;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.CheckoutActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.ViewUnplannedvisits;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivityOHC extends FragmentActivity implements OnMapReadyCallback {
    public static final String PREFS_NAME = "loginpref";
    private GoogleMap mMap;

    ImageView backarrow;
    DatePickerDialog mDatePicker;
    ImageView fromdate,todate;
    float[] distance;
    double lat, lng,lat1,lng1;
    Calendar upDateFroms;
    TextView frmbtnDatePicker, tobtnDatePicker;
    Calendar upDateFrom;
    long tomindate,lngtosdate,lngfromsdate;
    String latitude, longitude, uname, pswd, chkinaddress,chkoutaddress,coutlng,coutlat, username,endtime, starttime,mtoDate,alterdates1,mfrmDate,alterdates,Statename,nickname,part_NameState;
    int j = 0,i=0;
    int year, month, day, days;
    APIInterfaceadmin apiInterface;
    List<String> PartNameState;
    List<String> PartIdState;
    int mDay,tmonths=0,tdd=0,tyer=0;
    private List<LatLngModelClass> latLngModelClassList;
    ArrayAdapter<String> adapter;
    SearchableSpinner PartSpinnerState;
    int fmonths = 0, fdd = 0, fyer = 0;
    CircleOptions circleOptions1,circleOptions2,circleOptions3,circleOptions4,circleOptions5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_o_h_c);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        PartSpinnerState = findViewById(R.id.empids);
        PartSpinnerState.setTitle("Select Employee");
        PartNameState = new ArrayList<>();
        PartIdState = new ArrayList<>();
        postdatastate();
        distance = new float[2];
        backarrow = findViewById(R.id.backarrow);
        frmbtnDatePicker = findViewById(R.id.frmbtnDatePicker);
        tobtnDatePicker=findViewById(R.id.tobtnDatePicker);
        fromdate = findViewById(R.id.fromdate);
        todate=findViewById(R.id.todate);
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDates = Calendar.getInstance();
                year = mcurrentDates.get(Calendar.YEAR);
                month = mcurrentDates.get(Calendar.MONTH);
                day = mcurrentDates.get(Calendar.DAY_OF_MONTH);
                mcurrentDates.set(Calendar.DAY_OF_MONTH, mDay);
                mDatePicker = new DatePickerDialog(MapsActivityOHC.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        upDateFrom = Calendar.getInstance();
                        upDateFroms = Calendar.getInstance();
                        year = selectedyear;
                        month = selectedmonth;
                        day = selectedday;
                        upDateFrom.set(year, month, day);
                        mfrmDate = convertDate(convertToMillis(day, month, year));
                        tomindate=convertToMillis(day,month,year);
                        alterdates = convertDate1(convertToMillis(day, month, year));
                        frmbtnDatePicker.setText(mfrmDate);
                    }
                }, year, month, day);
                mDatePicker.setTitle("Please select From Date");
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDates = Calendar.getInstance();
                year = mcurrentDates.get(Calendar.YEAR);
                month = mcurrentDates.get(Calendar.MONTH);
                day = mcurrentDates.get(Calendar.DAY_OF_MONTH);
                mcurrentDates.set(Calendar.DAY_OF_MONTH, mDay);
                mDatePicker = new DatePickerDialog(MapsActivityOHC.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        year = selectedyear;
                        month = selectedmonth;
                        day = selectedday;
                        mtoDate = convertDate(convertToMillis(day, month, year));
                        alterdates1=convertDate1(convertToMillis(day, month, year));
                        //   alterdates=alterdates1;
                        tobtnDatePicker.setText(mtoDate);

                       // postdatastate();
                        postdatamap();
                    }

                }, year, month, day);

                mDatePicker.setTitle("Please select To Date");
                // TODO Hide Future Date Here
                mDatePicker.getDatePicker().setMinDate(tomindate);

                // TODO Hide Past Date Here

                mDatePicker.show();
            }
        });
        latLngModelClassList = new ArrayList<>();
        drawCircle1(new LatLng(10.625547, 78.5620039));
        drawCircle2(new LatLng(10.794162,78.6853133));
        drawCircle3(new LatLng(10.6751603,78.7402987));
        drawCircle5(new LatLng(10.7746785,78.6691258));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private void drawCircle1(LatLng point){

        // Instantiating CircleOptions to draw a circle around the marker
       circleOptions1 = new CircleOptions();

        // Specifying the center of the circle
        circleOptions1.center(point);

        // Radius of the circle
        circleOptions1.radius(200);

        // Border color of the circle
        circleOptions1.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions1.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions1.strokeWidth(2);

        // Adding the circle to the GoogleMap

    }
    private void drawCircle2(LatLng point){
        // Instantiating CircleOptions to draw a circle around the marker
        circleOptions2 = new CircleOptions();
        // Specifying the center of the circle
        circleOptions2.center(point);
        // Radius of the circle
        circleOptions2.radius(200);
        // Border color of the circle
        circleOptions2.strokeColor(Color.BLACK);
        // Fill color of the circle
        circleOptions2.fillColor(0x30ff0000);
        // Border width of the circle
        circleOptions2.strokeWidth(2);
        // Adding the circle to the GoogleMap
    }
    private void drawCircle3(LatLng point){

        // Instantiating CircleOptions to draw a circle around the marker
        circleOptions3 = new CircleOptions();

        // Specifying the center of the circle
        circleOptions3.center(point);

        // Radius of the circle
        circleOptions3.radius(200);

        // Border color of the circle
        circleOptions3.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions3.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions3.strokeWidth(2);

        // Adding the circle to the GoogleMap

    }

    private void drawCircle5(LatLng point){

        // Instantiating CircleOptions to draw a circle around the marker
        circleOptions5 = new CircleOptions();

        // Specifying the center of the circle
        circleOptions5.center(point);

        // Radius of the circle
        circleOptions5.radius(200);

        // Border color of the circle
        circleOptions5.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions5.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions5.strokeWidth(2);

        // Adding the circle to the GoogleMap

    }
    public String convertDate1(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(mTime);
        return formattedDate;

    }
    public long convertToMillis(int day, int month, int year) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, year);
        calendarStart.set(Calendar.MONTH, month);
        calendarStart.set(Calendar.DAY_OF_MONTH, day);
        return calendarStart.getTimeInMillis();
    }

    public String convertDate(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        String formattedDate = df.format(mTime);
        return formattedDate;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addCircle(circleOptions1);
        mMap.addCircle(circleOptions2);
        mMap.addCircle(circleOptions3);

        mMap.addCircle(circleOptions5);
     /*   Circle circles = mMap.addCircle(new CircleOptions()
                .center(new LatLng(10.625547, 78.5620039))
                .radius(10000)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));*/
    }
    private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(MapsActivityOHC.this);
        loading.setMessage("Loading Employee Id...");
        loading.setCancelable(false);
        loading.show();
        apiInterface = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select username,nickname from axusers");
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

        Call<Exampleadmin> call4 = apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {
                loading.dismiss();
                //  String jsonString = response.body().toString();



                try {
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        Statename = response.body().getResult().get(0).getResult().getRow().get(i).getUsername();
                        nickname = response.body().getResult().get(0).getResult().getRow().get(i).getNickname();
                        // stateId = list.get(0).getResult().getRow().get(i).getstateid();
                        PartNameState.add(Statename);
                        //  PartIdState.add(stateId);//
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter = new ArrayAdapter<String>(MapsActivityOHC.this, android.R.layout.simple_spinner_dropdown_item, PartNameState);
                PartSpinnerState.setAdapter(adapter);
                PartSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //  part_IdState= PartIdState.get(position);
                        part_NameState = PartNameState.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(MapsActivityOHC.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void postdatamap() {
        mMap.clear();
        final ProgressDialog loading = new ProgressDialog(MapsActivityOHC.this);
        loading.setMessage("Loading Tracking...");
        loading.setCancelable(false);
        loading.show();
        apiInterface = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select a.uname,a.chkinaddress,b.chkoutaddress,a.starttime,b.endtime,a.latitude,a.longitude,b.latitude coutlat ,b.longitude coutlng from work_start a join work_end b on b.workstartid=a.work_startid   where a.uname='"+part_NameState+"' and trunc(a.createdon) between '"+alterdates+"' and '"+alterdates1+"'");
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

        Call<Exampleadmin> call4 = apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {
                //sourcePoints.clear();
                //jsonString=response.body().toString();
                loading.dismiss();
                latLngModelClassList = new ArrayList<>();
                try {
                    for (i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        latitude = response.body().getResult().get(0).getResult().getRow().get(i).getLatitude();
                        longitude = response.body().getResult().get(0).getResult().getRow().get(i).getLongitude();
                        username= response.body().getResult().get(0).getResult().getRow().get(i).getuname();
                        coutlat=response.body().getResult().get(0).getResult().getRow().get(i).getCoutlat();
                        coutlng=response.body().getResult().get(0).getResult().getRow().get(i).getCoutlng();
                        starttime=response.body().getResult().get(0).getResult().getRow().get(i).getStarttime();
                        endtime=response.body().getResult().get(0).getResult().getRow().get(i).getEndtime();
                        chkinaddress=response.body().getResult().get(0).getResult().getRow().get(i).getChkinaddress();
                        chkoutaddress=response.body().getResult().get(0).getResult().getRow().get(i).getChkoutaddress();
                        lat = Double.parseDouble(latitude);
                        lat1=Double.parseDouble(coutlat);
                        lng1=Double.parseDouble(coutlng);
                        lng = Double.parseDouble(longitude);
                        LatLng sydney = new LatLng(lat, lng);
                        LatLng sydney1=new LatLng(lat1,lng1);

                        getCompleteAddressString(lat,lng);
                        getCompleteAddressString(lat1,lng1);
                        mMap.addMarker(new MarkerOptions().position(sydney).title(username).snippet("CheckIn Time:"+starttime+" \n "+"Address :"+chkinaddress).icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.location)));
                        mMap.addMarker(new MarkerOptions().position(sydney1).title(username).snippet("CheckOut Time:"+endtime+"\n"+"Address :"+chkoutaddress).icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.locationend)));
                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                            @Override
                            public View getInfoWindow(Marker arg0) {
                                return null;
                            }

                            @Override
                            public View getInfoContents(Marker marker) {

                                LinearLayout info = new LinearLayout(getApplicationContext());
                                info.setOrientation(LinearLayout.VERTICAL);

                                TextView title = new TextView(getApplicationContext());
                                title.setTextColor(Color.BLACK);
                                title.setGravity(Gravity.CENTER);
                                title.setTypeface(null, Typeface.BOLD);
                                title.setText(marker.getTitle());

                                TextView snippet = new TextView(getApplicationContext());
                                snippet.setTextColor(Color.GRAY);
                                snippet.setText(marker.getSnippet());

                                info.addView(title);
                                info.addView(snippet);

                                return info;
                            }
                        });
                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(lat, lng))
                                .zoom(23)
                                .bearing(0)
                                .tilt(45)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 2500, null);
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(MapsActivityOHC.this, "Route Was Not Tracked", Toast.LENGTH_LONG).show();
            }
        });

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
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
    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable, int width, int height) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


}
