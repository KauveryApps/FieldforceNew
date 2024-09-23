package com.kauveryhospital.fieldforce.UserAdmin.contactadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIClientadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIInterfaceadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Exampleadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Resultadmin;
import com.kauveryhospital.fieldforce.UserAdmin.contactadmin.savedataadmin.ResultSaveadmin;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivityadmin extends AppCompatActivity implements NetworkChangeCallback {
    String address, ContacttypeName, contype, uname, pswd, CorporateName, cont_corpid, city, area, pincode, state, salutation, contactname, corporate, ambulance, specialization, portfolio, phone, active, SalutationName;
    int j = 0;
    List<Resultadmin> list = new ArrayList<>();
    ImageView backarrow;
TextView tkcontacttype,tksalutation,tkstate,tkcity;
    String statuscheckbox, latitude, longitude, message, Statename, stateId, part_IdState, part_NameState, Cityname, cityId, part_IdCity, part_NameCity, Areaname, areaId, part_IdArea, part_NameArea, Pincodename, pincodeId, part_IdPincode, part_NamePincode, ContacttypeId, part_IdContacttype, SalutationId, CorporateId;
    List<String> PartNameContacttype, PartNameCorporateName;
    ArrayAdapter<String> adapter;
    List<ResultSaveadmin> listsave = new ArrayList<>();
    APIInterfaceadmin apiInterface;
    int spinnerPosition;
    List<String> PartNameState;
    List<String> PartIdState;

    List<String> PartNameCity;
    List<String> PartIdCity;

    List<String> PartNameArea;
    List<String> PartIdArea;

    List<String> PartNamePincode;
    List<String> PartIdPincode;

    SearchableSpinner PartSpinnerContacttype, PartSpinnerCorporatename, PartSpinnerState;
    SearchableSpinner PartSpinnerSalutation, PartSpinnerCity, PartSpinnerArea, PartSpinnerPincode;
    EditText edcontactname, edambulancename, edspecialization, edportfolio,  edaddress, edphoneno;
    public static final String PREFS_NAME = "loginpref";
    List<String> PartNameSalutation;
    Button btnLogin;
    private NetworkChangeReceiver networkChangeReceiver;
    String svcontactname,state_name, svambulancename,svphone,pcity, svspecialization, svportfolio, stateid,cityid,areaid,pincodeid,svaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_activityadmin);
        tkcontacttype=findViewById(R.id.tkcontacttype);
        tksalutation=findViewById(R.id.tksalutation);
        tkstate=findViewById(R.id.tkstate);
        tkcity=findViewById(R.id.tkcity);
        backarrow=findViewById(R.id.backarrow);
        edcontactname = findViewById(R.id.edcontactname);
        edambulancename = findViewById(R.id.edambulancename);
        edspecialization = findViewById(R.id.edspecialization);
        edportfolio = findViewById(R.id.edportfolio);

        edaddress = findViewById(R.id.edaddress);
        edphoneno = findViewById(R.id.edphoneno);
        btnLogin = findViewById(R.id.btnLogin);
        networkChangeReceiver = new NetworkChangeReceiver(this);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        PartSpinnerContacttype = findViewById(R.id.contacttype);
        PartNameContacttype = new ArrayList<>();
        PartSpinnerSalutation =  findViewById(R.id.salutation);
        PartNameSalutation = new ArrayList<>();
        PartSpinnerCorporatename = findViewById(R.id.corporatename);
        PartNameCorporateName = new ArrayList<>();
        PartSpinnerState = findViewById(R.id.state);
        PartNameState = new ArrayList<>();
        PartIdState = new ArrayList<>();

        postdataCorporatename();

        PartSpinnerCity =  findViewById(R.id.city);
        PartNameCity = new ArrayList<>();
        PartIdCity = new ArrayList<>();
        PartSpinnerArea =  findViewById(R.id.area);
        PartNameArea = new ArrayList<>();
        PartIdArea = new ArrayList<>();

        PartSpinnerPincode = findViewById(R.id.pincode);
        PartNamePincode = new ArrayList<>();
        PartIdPincode = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        address = bundle.getString("address");
        cityid = bundle.getString("cityid");
        stateid=bundle.getString("stateid");
        Log.d("dddTAG", "onCreate: "+stateid+"  "+cityid);

      //  active = bundle.getString("active");
       // state = bundle.getString("stateid");

        contype = bundle.getString("contype");
        salutation = bundle.getString("salutation");
        contactname = bundle.getString("contactname");
        corporate = bundle.getString("corporate");
        ambulance = bundle.getString("ambulance");
        specialization = bundle.getString("specialization");
        portfolio = bundle.getString("portfolio");
        phone = bundle.getString("phone");
        cont_corpid = bundle.getString("cont_corpid");
        state_name=bundle.getString("state_name");
        pcity=bundle.getString("city");
        tkcontacttype.setText(contype);
        tksalutation.setText(salutation);
        tkstate.setText(state_name);
        tkcity.setText(pcity);
        edcontactname.setText(contactname);
        edambulancename.setText(ambulance);
        edspecialization.setText(specialization);
        edportfolio.setText(portfolio);
        edaddress.setText(address);
        edphoneno.setText(phone);
        postdataContacttype();
        postdataSalutation();
          postdatastate();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivityadmin.this, TabbedActivity.class));
            }
        });
    }

    private void validation() {
        svcontactname=edcontactname.getText().toString();
        svambulancename=edambulancename.getText().toString();
        svportfolio=edportfolio.getText().toString();
        svspecialization=edspecialization.getText().toString();
        svaddress=edaddress.getText().toString();
          svphone=edphoneno.getText().toString();


            try {

                gettingdatas();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    private void postdataContacttype() {

        // PartNameContacttype=contype;

        final ProgressDialog loading = new ProgressDialog(UpdateActivityadmin.this);
        loading.setMessage("Loading ContactType...");
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
        jsonObject5.addProperty("sql", "select 'Doctor' dt from dual union all select 'Ambulance Driver' dt from dual union all select 'Others' dt from dual order by dt");
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
        //      String jsonString = response.body().toString();
              //  list = response.body().getResult();


                try {
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        ContacttypeName = response.body().getResult().get(0).getResult().getRow().get(i).getDt();
                        //  ContacttypeId = list.get(0).getResult().getRow().get(i).getPincodeid();
                        PartNameContacttype.add(ContacttypeName);
                        //  PartIdContacttype.add(ContacttypeId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                adapter = new ArrayAdapter<String>(UpdateActivityadmin.this, android.R.layout.simple_spinner_dropdown_item, PartNameContacttype);

                PartSpinnerContacttype.setAdapter(adapter);
                PartSpinnerContacttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ContacttypeName = PartNameContacttype.get(position);
                        Toast.makeText(getApplicationContext(), ContacttypeName, Toast.LENGTH_SHORT);//
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_SHORT);
            }
        });
    }

    private void postdataSalutation() {
     //   PartNameSalutation.clear();
      //  PartNameSalutation.add(salutation);
        final ProgressDialog loading = new ProgressDialog(UpdateActivityadmin.this);
        loading.setMessage("Loading Salutation...");
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
        jsonObject5.addProperty("sql", "select 'Mr. ' title from dual union all select 'Mrs. ' title from dual union all select 'Ms. ' title from dual union all select 'Dr. ' title from dual union all select 'Prof. ' title from dual order by title");
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
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        SalutationName = response.body().getResult().get(0).getResult().getRow().get(i).getTitle();
                        //ContacttypeId = list.get(0).getResult().getRow().get(i).getPincodeid();
                        PartNameSalutation.add(SalutationName);
                        //  PartIdContacttype.add(ContacttypeId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateActivityadmin.this, android.R.layout.simple_spinner_dropdown_item, PartNameSalutation);
                PartSpinnerSalutation.setAdapter(adapter);
                PartSpinnerSalutation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {
                        SalutationName = PartNameSalutation.get(position);
                        //Toast.makeText(getApplicationContext(),part_IdPincode,Toast.);//
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_SHORT);
            }
        });

    }

    private void postdataCorporatename() {
        final ProgressDialog loading = new ProgressDialog(UpdateActivityadmin.this);
        loading.setMessage("Loading ContactType...");
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
        jsonObject5.addProperty("sql", "select a1.corporateid, a1.name from corporate a1 order by a1.name");
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
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        CorporateName = response.body().getResult().get(0).getResult().getRow().get(i).getName();
                        // ContacttypeId = list.get(0).getResult().getRow().get(i).getPincodeid();
                        PartNameCorporateName.add(CorporateName);
                        //  PartIdContacttype.add(ContacttypeId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateActivityadmin.this, android.R.layout.simple_spinner_dropdown_item, PartNameCorporateName);
                PartSpinnerCorporatename.setAdapter(adapter);
                PartSpinnerCorporatename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CorporateName = PartNameCorporateName.get(position);
                        //   Toast.makeText(getApplicationContext(),part_IdPincode,Toast.);//
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_SHORT);
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
            Toast.makeText(UpdateActivityadmin.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
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

    private void postdatastate() {
        PartNameState.clear();
      //  PartNameState.add(state);

        final ProgressDialog loading = new ProgressDialog(UpdateActivityadmin.this);
        loading.setMessage("Loading State...");
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

        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "select state_name,stateid from STATE  order by state_name");
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
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        Statename = response.body().getResult().get(0).getResult().getRow().get(i).getstate_name();
                        stateId = response.body().getResult().get(0).getResult().getRow().get(i).getstateid();
                        PartNameState.add(Statename);
                        PartIdState.add(stateId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateActivityadmin.this, android.R.layout.simple_spinner_dropdown_item, PartNameState);
                PartSpinnerState.setAdapter(adapter);
                PartSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        part_IdState = PartIdState.get(position);
                        part_NameState = PartNameState.get(position);
                        postdatacity();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        part_IdState = stateid;
                        part_IdCity=cityid;
                    }
                });
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                PartSpinnerState.setAdapter(null);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "No State Found", Toast.LENGTH_SHORT);
            }
        });

    }

    private void postdatacity() {
           PartNameCity.clear();
          // PartNameCity.add(city);
        final ProgressDialog loading = new ProgressDialog(UpdateActivityadmin.this);
        loading.setMessage("Loading City...");
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

        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "select cityid,city_name from city where STATEID = '" + part_IdState + "' order by city_name");
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
                // String jsonString = response.body().toString();



                try {
                    for (int i = 0; i <=  response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        Cityname = response.body().getResult().get(0).getResult().getRow().get(i).getCity_name();
                        cityId = response.body().getResult().get(0).getResult().getRow().get(i).getCityid();
                        PartNameCity.add(Cityname);
                        PartIdCity.add(cityId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateActivityadmin.this, android.R.layout.simple_spinner_dropdown_item, PartNameCity);
                PartSpinnerCity.setAdapter(adapter);
                PartSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        part_IdCity = PartIdCity.get(position);
                        part_NameCity = PartNameCity.get(position);
                        postdataArea();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        part_IdCity=cityid;
                    }
                });
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                PartSpinnerCity.setAdapter(null);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "No City Found", Toast.LENGTH_SHORT);
            }
        });

    }

    private void postdataArea() {
          PartNameArea.clear();
        // PartNameArea.add(area);
        final ProgressDialog loading = new ProgressDialog(UpdateActivityadmin.this);
        loading.setMessage("Loading Area...");
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
        jsonObject5.addProperty("sql", "select area_name,area_masterid from AREA_MASTER  where CITY = '" + part_IdCity + "' order by area_name");
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
                // String jsonString = response.body().toString();
                try {
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        Areaname = response.body().getResult().get(0).getResult().getRow().get(i).getArea_name();
                        areaId = response.body().getResult().get(0).getResult().getRow().get(i).getArea_masterid();
                        PartNameArea.add(Areaname);
                        PartIdArea.add(areaId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateActivityadmin.this, android.R.layout.simple_spinner_dropdown_item, PartNameArea);
                PartSpinnerArea.setAdapter(adapter);
                PartSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        part_IdArea = PartIdArea.get(position);
                        part_NameArea = PartNameArea.get(position);
                        // Toast.makeText(getApplicationContext(),part_IdArea,Toast.LENGTH_SHORT);
                        postdataPincode();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                PartSpinnerArea.setAdapter(null);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "No Area Found", Toast.LENGTH_SHORT);
            }
        });
    }

    private void postdataPincode() {
        // PartNamePincode.clear();
        //  PartNamePincode.add(pincode);
        final ProgressDialog loading = new ProgressDialog(UpdateActivityadmin.this);
        loading.setMessage("Loading pincode...");
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
        jsonObject5.addProperty("sql", "select pin_code from PINCODE  where AREA = '" + part_IdArea + "'");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);
            // array.put(jsonObject6);//
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
                //   String jsonString = response.body().toString();

                try {
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        Pincodename = response.body().getResult().get(0).getResult().getRow().get(i).getPin_code();
                        //   pincodeId = list.get(0).getResult().getRow().get(i).getPincodeid();
                        PartNamePincode.add(Pincodename);
                        //  PartIdPincode.add(pincodeId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateActivityadmin.this, android.R.layout.simple_spinner_dropdown_item, PartNamePincode);
                PartSpinnerPincode.setAdapter(adapter);
                PartSpinnerPincode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //  part_IdPincode = PartIdPincode.get(position);
                        part_NamePincode = PartNamePincode.get(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                PartSpinnerPincode.setAdapter(null);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "No pincode Found", Toast.LENGTH_SHORT);
            }
        });

    }



    private void gettingdatas() throws JSONException {
        final ProgressDialog loading = new ProgressDialog(UpdateActivityadmin.this);
        loading.setMessage("Loading Updated Datas...");
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
        jsonObject5.addProperty("sql", "update cont_corp SET salutation='"+SalutationName+"',state="+part_IdState+",city="+part_IdCity+",area="+part_IdArea+",pincode="+part_IdPincode+", contype='" + ContacttypeName + "',contactname='" + svcontactname + "',specialization='" + svspecialization + "',portfolio='" + svportfolio + "',address='" + svaddress + "',phone='"+svphone+"',ambulance='"+svambulancename+"'  where cont_corpid='" + cont_corpid + "'");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);
            // array.put(jsonObject6);//
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
                //   String jsonString = response.body().toString();


                //  int numbers = response.body().getResult().get(j).getResult().getRow().size();
                if (response.body().getResult().get(0).getResult().getStatus() != null) {
                    message = response.body().getResult().get(0).getResult().getStatus();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateActivityadmin.this, TabbedActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Server Problem!!!...", Toast.LENGTH_SHORT);
            }
        });
    }
}