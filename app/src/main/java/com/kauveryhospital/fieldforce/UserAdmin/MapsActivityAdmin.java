package com.kauveryhospital.fieldforce.UserAdmin;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivityAdmin extends FragmentActivity implements OnMapReadyCallback {

    public static final String PREFS_NAME = "loginpref";
    private GoogleMap mMap;
    ArrayAdapter<String> adapter;
    ImageView backarrow;
    Button choosedate;
    float meter;
    private DatePicker datePicker;
    Button empid;
    double startPoint;
    double lat, lng, lats, lngs;
    String currentdate, Statename, message, alterdates1,stateId, nickname, currentdate1;
    private Calendar calendar;
    APIInterfacePlan apiInterfaceplan;
    String checkintime, checkintimes;
    TextView selctdate;
    String latitude, longitude, uname, pswd, part_IdState, createdon, part_NameState, latitudes, longitudes;
    int j = 0;
    double sum = 0.0;
    APIInterfaceadmin apiInterface;
    List<Resultadmin> list = new ArrayList<>();
    List<Result_admin> list1 = new ArrayList<>();
    private List<LatLngModelClass> latLngModelClassList;
    List<LatLng> sourcePoints;
    SearchableSpinner PartSpinnerState;
    TextView dateView;
    int year, month, day;
    int i = 0;
    List<String> PartNameState;
    List<String> PartIdState;
    private int mYear, mMonth, mDay, mHour, mMinute, date;

    ArrayList points = new ArrayList();
    Double distance;
    PolylineOptions lineOptions;
    double lat_list, lng_list;
    LatLng singlelatlang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_admin);
        backarrow = findViewById(R.id.backarrow);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        PartSpinnerState = findViewById(R.id.empids);
        PartSpinnerState.setTitle("Select Employee");
        PartNameState = new ArrayList<>();
        PartIdState = new ArrayList<>();
        latLngModelClassList = new ArrayList<>();
        postdatastate();
        selctdate = findViewById(R.id.selctdate);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
        currentdate = formatter.format(todayDate);
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MMM-YYYY");
        currentdate1 = formatter1.format(todayDate);
        points = new ArrayList();

        choosedate = findViewById(R.id.choosedate);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
     /*   mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }
            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.mapinfowindow,null);
                LatLng latLng =marker.getPosition();
                TextView tvLat = (TextView)findViewById(R.id.tvLat);
                TextView tvLng = (TextView)findViewById(R.id.tvLng);
                tvLat.setText(String.valueOf(latLng.latitude));
                tvLng.setText(String.valueOf(latLng.longitude));
                return v;
            }
        });*/
        choosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MapsActivityAdmin.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        choosedate.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
                        currentdate = choosedate.getText().toString();
                        mYear=year;
                        mMonth=monthOfYear;
                        mDay=dayOfMonth;
                        alterdates1=convertDate1(convertToMillis(mDay, mMonth, mYear));
                        mMap.clear();
                        postdatamap();
                        postdata1();

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapsActivityAdmin.this, TabbedActivity.class));
            }
        });
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
    private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(MapsActivityAdmin.this);
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
                adapter = new ArrayAdapter<String>(MapsActivityAdmin.this, android.R.layout.simple_spinner_dropdown_item, PartNameState);
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
                Toast.makeText(MapsActivityAdmin.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void postdatamap() {
        sum=0;
        final ProgressDialog loading = new ProgressDialog(MapsActivityAdmin.this);
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
        jsonObject5.addProperty("sql", "select latitude,longitude,userid,createdon from location_track where userid='"+part_NameState+"' and trunc(createdon)='"+alterdates1+"' order by createdon asc");
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
                // sourcePoints.clear();
                // String jsonString=response.body().toString();
                loading.dismiss();
                latLngModelClassList = new ArrayList<>();


                try {
                    for (i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        latitude = response.body().getResult().get(0).getResult().getRow().get(i).getLatitude();
                        longitude = response.body().getResult().get(0).getResult().getRow().get(i).getLongitude();
                        createdon = response.body().getResult().get(0).getResult().getRow().get(i).getCreatedon();
                        lat = Double.parseDouble(latitude);

                        lng = Double.parseDouble(longitude);

                        LatLngModelClass mapDataclass = new LatLngModelClass();
                        mapDataclass.setLatitude(lat);
                        mapDataclass.setLongitude(lng);
                        latLngModelClassList.add(mapDataclass);
                        LatLng sydney = new LatLng(lat, lng);

                        mMap.addMarker(new MarkerOptions().position(sydney).title("Tracking Point").snippet(createdon+" "+latitude+" "+longitude));
                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(lat, lng))
                                .zoom(23)
                                .bearing(0)
                                .tilt(45)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 2500, null);

                        double latitude1 = Double.parseDouble(response.body().getResult().get(0).getResult().getRow().get(i).getLatitude());
                        double longitude1 = Double.parseDouble(response.body().getResult().get(0).getResult().getRow().get(i).getLongitude());

                        double latitude2 = Double.parseDouble(response.body().getResult().get(0).getResult().getRow().get(i + 1).getLatitude());
                        double longitude2 = Double.parseDouble(response.body().getResult().get(0).getResult().getRow().get(i + 1).getLongitude());


                        LatLng origin = new LatLng(latitude1, longitude1);
                        LatLng destination = new LatLng(latitude2, longitude2);

                        haversine(latitude1, longitude1, latitude2, longitude2);
                      //  CalculationByDistance(latitude1, longitude1, latitude2, longitude2);
                     //   distance = SphericalUtil.computeDistanceBetween(sydney, Brisbane);
                        String url = getDirectionsUrl(origin, destination);
                        DownloadTask downloadTask = new DownloadTask();
                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
                    Log.d("latLngModelClassSize", "onResponse: " + latLngModelClassList.size());
                    if (latLngModelClassList.size() > 0) {
                        LatLng sydney1 = new LatLng(latLngModelClassList.get(0).getLatitude(), latLngModelClassList.get(0).getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney1).title("start"));

                        LatLng sydney2 = new LatLng(latLngModelClassList.get(latLngModelClassList.size() - 1).getLatitude(), latLngModelClassList.get(latLngModelClassList.size() - 1).getLongitude());
                        mMap.addMarker(new MarkerOptions().position(sydney2).title("end"));

                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(latLngModelClassList.get(latLngModelClassList.size() - 1).getLatitude(), latLngModelClassList.get(latLngModelClassList.size() - 1).getLongitude()))
                                .zoom(23)
                                .bearing(0)
                                .tilt(45)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 2500, null);
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    try
                    {
                        Double sum1=sum/1000;
                        Updatekilometer(String.valueOf(sum1));
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
                Toast.makeText(MapsActivityAdmin.this, "Route Was Not Tracked", Toast.LENGTH_LONG).show();
            }
        });

    }

   public void haversine(double lat1, double lon1, double lat2, double lon2) {
      /*  double Rad = 6371;

        double dlat = Math.toRadians(lat2 - lat1);
        double dlon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.sin(dlon / 2) * Math.sin(dlon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c= 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
       // double c = 2 * Math.asin(Math.sqrt(1-a));

        double haverdistancekm = Rad * c;
        sum = sum + haverdistancekm;*/

       Location endPoint = new Location("locationA");
       endPoint.setLatitude(lat1);
       endPoint.setLongitude(lon1);
    //   double distance = startPoint.distanceTo(endPoint);
       Location cityPoint = new Location("locationB");
       cityPoint.setLatitude(lat2);
       cityPoint.setLongitude(lon2);
       double cityDistance = endPoint.distanceTo(cityPoint);
        sum=sum+cityDistance;
      /* if ((float) (cityDistance * 0.001) < 20) {
           meter = (float) (meter + distance);
          // totalAmount = (float) (normalDistanceAmount * meter);
       } else {
           meter = (float) (meter + distance);
          // totalAmount = (float) (outOfDistanceAmount * meter);
       }*/


    }
    public double CalculationByDistance(double initialLat, double initialLong, double finalLat, double finalLong){
        /*PRE: All the input values are in radians!*/

        double latDiff = finalLat - initialLat;
        double longDiff = finalLong - initialLong;
        double earthRadius = 6371; //In Km if you want the distance in km
        double distance = 2*earthRadius*Math.asin(Math.sqrt(Math.pow(Math.sin(latDiff/2.0),2)+Math.cos(initialLat)*Math.cos(finalLat)*Math.pow(Math.sin(longDiff/2),2)));
        sum= sum+distance;
        return sum;

    }
    private void Updatekilometer(String sum) throws JSONException {
        final ProgressDialog loading = new ProgressDialog(MapsActivityAdmin.this);
        loading.setMessage("Fetching Kilometers...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceplan = APIClientPlan.getClient().create(APIInterfacePlan.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);

        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "update work_start SET  kilometer='" + sum + "' where username='"+part_NameState+"' and  trunc(createdon)='"+alterdates1+"'");

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

        Call<Getcheckout> call4 = apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckout>() {
            @Override
            public void onResponse(Call<Getcheckout> call, Response<Getcheckout> response) {
                loading.dismiss();

                try {
                    if (response.body().getResult().get(0).getResult().getStatus() != null) {
                        message = response.body().getResult().get(0).getResult().getStatus();

                        Toast.makeText(MapsActivityAdmin.this, "Updated kilometers" + message, Toast.LENGTH_SHORT).show();
                    }

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Getcheckout> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(MapsActivityAdmin.this, "server problem!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {

                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result)
        {
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    lat_list = Double.parseDouble(Objects.requireNonNull(point.get("lat")));
                    lng_list = Double.parseDouble(Objects.requireNonNull(point.get("lng")));
                    final LatLng position = new LatLng(lat_list, lng_list);
                    singlelatlang = new LatLng(lat_list, lng_list);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);
            }
            if (points.size() != 0) {
                mMap.addPolyline(lineOptions);
            }
        }

    }

    private void postdata1() {
        final ProgressDialog loading = new ProgressDialog(MapsActivityAdmin.this);
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
        jsonObject5.addProperty("sql", "select latitude,longitude,checkin from unplanvisit where employee='" + part_NameState + "' and visitdate='" + currentdate + "' ");
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
                try {
                    for (i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++)
                    {
                        latitudes = response.body().getResult().get(0).getResult().getRow().get(i).getLatitude();
                        longitudes = response.body().getResult().get(0).getResult().getRow().get(i).getLongitude();
                        checkintimes = response.body().getResult().get(0).getResult().getRow().get(i).getCheckin();
                        lats = Double.parseDouble(latitudes);
                        lngs = Double.parseDouble(longitudes);
                        LatLng sydney = new LatLng(lats, lngs);
                        addCircleMarker(sydney);

                        mMap.addMarker(new MarkerOptions().position(sydney).title("CheckinTime").snippet(checkintimes));
                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(lats, lngs))
                                .zoom(23)
                                .bearing(0)
                                .tilt(45)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 2500, null);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(MapsActivityAdmin.this, "No CheckIn Records Found", Toast.LENGTH_LONG).show();
            }
        });

    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        //setting transportation mode
        String mode = "mode=driving";
        // Sensor enabled
        String sensor = "sensor=true";

        String key = "key=" + getResources().getString(R.string.google_maps_key);
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable, int width, int height) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void addCircleMarker(LatLng latLng) {
        Drawable circleDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_blue_circle);
        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable, 30, 30);

        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .anchor(0.5f, 0.5f)
                .icon(markerIcon)

        );
    }
}
