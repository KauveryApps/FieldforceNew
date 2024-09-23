package com.kauveryhospital.fieldforce.UseronlyAccess.EmployeeMapping;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
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

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.Globaldeclare.APIClient;
import com.kauveryhospital.fieldforce.Globaldeclare.APIInterface;
import com.kauveryhospital.fieldforce.Globaldeclare.Example;
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIClientadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIInterfaceadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Exampleadmin;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata.APIClientSavetrv;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata.APIInterfaceSavetrv;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.savesdata.ExampleSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.UnPlannedActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.APIClientPlanchk;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.APIInterfacePlanschk;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.getdata.Getcheckoutchk;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.APIClientvisit;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.APIInterfacevisit;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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

public class VisitPlanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, NetworkChangeCallback {
    ImageView backarrow, fromdate;
    private int year, month, day;
    int mDay;
    Spinner spinner;
    APIInterfacePlanschk apiInterfacePlanschk;
    APIInterfaceSavetrv apiInterfaceSavetrv;
    ArrayAdapter<String> adapter;
    List<String> PartNameState;
    List<String> PartIdState;
    APIInterfaceadmin apiInterfaceadm;
    APIInterface apiInterface;
    APIInterfacevisit apiInterfacevisit;
    DatePickerDialog mDatePicker;
    String cusid,currentDateTimeString1,item,mfrmDate,message,usersname, corporatename,pswd, getscorpname,gettaddress,alterdates,uname,part_IdState,ContacttypeId,ContacttypeName,ContacttypeNames,cont_corp_name,cont_corp_names,sessions,uaa,nickname,Statename,stateId,Joint1name,Joint1username,Joint2username,Joint3username,Joint2name,Joint3name,VisitpurrposeName,VisitpurrposeNames, Joint1names, part_IdJoint1, part_IdJoint2, part_IdJoint3, Joint2names, Joint3names;
    long tomindate;
    public static final String PREFS_NAME = "loginpref";
    TextView frmbtnDatePicker,addressofselect,corp_name,viewlist;
    Calendar upDateFroms, upDateFrom;
    SearchableSpinner  PartSpinnerCustomer,PartSpinnerContacttype, PartSpinnerState,PartSpinnerJoint1,PartSpinnerJoint2,PartSpinnerJoint3,PartSpinnerVisitpurpose;
    int j = 0;
    private NetworkChangeReceiver networkChangeReceiver;
    List<String> PartNameVisitpurpose;
    List<String> PartIdVisitpurpose;
    List<String> PartNameContacttype;
    List<String> PartIdContacttype;
    List<String> PartNameCustomer;
    List<String> PartNameCustomerid;
    List<String> PartNameJoint1;
    List<String> PartIdJoint1;
    List<String> PartNameJoint2;
    List<String> PartIdJoint2;
    List<String> PartNameJoint3;
    List<String> PartIdJoint3;
   List<String> Partcorpsname;
    Button btnSaveContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_plan);

        networkChangeReceiver = new NetworkChangeReceiver(this);
        backarrow = findViewById(R.id.backarrow);
        fromdate = findViewById(R.id.fromdate);
        frmbtnDatePicker=findViewById(R.id.frmbtnDatePicker);
        addressofselect=findViewById(R.id.addressofselect);
        corp_name=findViewById(R.id.corp_name);

        btnSaveContact=findViewById(R.id.btnSaveContact);
        viewlist=findViewById(R.id.viewlist);


        Date todaydate1 = Calendar.getInstance().getTime();

        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MMM/yyyy");

        currentDateTimeString1 = formatter1.format(todaydate1);

        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        sessions = set.getString("session", "");
        uaa = set.getString("uaa", "");
        nickname=set.getString("nickname","");
        usersname=set.getString("username","");
        Retrievingdata();

        PartSpinnerJoint2 = findViewById(R.id.employeejointtwo);
        PartSpinnerJoint3 = findViewById(R.id.employeejointthree);

      //  PartSpinnerState = findViewById(R.id.employeelist);
      //  PartSpinnerState.setTitle("Select Employee");
      //  PartNameState = new ArrayList<>();
      //  PartIdState = new ArrayList<>();

        PartSpinnerVisitpurpose = findViewById(R.id.visitpurpose);
        PartSpinnerVisitpurpose.setTitle("Select Visit Purpose");
        PartNameVisitpurpose = new ArrayList<>();
        PartIdVisitpurpose = new ArrayList<>();
        
        PartSpinnerContacttype = findViewById(R.id.contacttype);
        PartSpinnerContacttype.setTitle("Select Contact Type");
        PartNameContacttype = new ArrayList<>();
        PartIdContacttype = new ArrayList<>();

        PartSpinnerCustomer=findViewById(R.id.customer);
        PartSpinnerCustomer.setTitle("Select Customer");
        PartNameCustomer=new ArrayList<>();
        PartNameCustomerid=new ArrayList<>();
        Partcorpsname=new ArrayList<>();
        PartSpinnerJoint1 = findViewById(R.id.employeejointone);
        PartSpinnerJoint1.setTitle("Select Joint Call 1");
        PartNameJoint1=new ArrayList<>();
        PartIdJoint1=new ArrayList<>();
        postdataJoint1();

        PartSpinnerJoint2 = findViewById(R.id.employeejointtwo);
        PartSpinnerJoint2.setTitle("Select Joint Call 2");
        PartNameJoint2=new ArrayList<>();
        PartIdJoint2=new ArrayList<>();
        postdataJoint2();


        PartSpinnerJoint3 = findViewById(R.id.employeejointthree);
        PartSpinnerJoint3.setTitle("Select Joint Call 3");
        PartNameJoint3=new ArrayList<>();
        PartIdJoint3=new ArrayList<>();
        postdataJoint3();

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VisitPlanActivity.this, EmpContactMapping.class));
            }
        });
        btnSaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validation();
                 //   Insertdata();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        viewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(dataAdapter);
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDates = Calendar.getInstance();
                year = mcurrentDates.get(Calendar.YEAR);
                month = mcurrentDates.get(Calendar.MONTH);
                day = mcurrentDates.get(Calendar.DAY_OF_MONTH);
                mcurrentDates.set(Calendar.DAY_OF_MONTH, mDay);
                mDatePicker = new DatePickerDialog(VisitPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        upDateFrom = Calendar.getInstance();
                        upDateFroms = Calendar.getInstance();
                        year = selectedyear;
                        month = selectedmonth;
                        day = selectedday;
                        upDateFrom.set(year, month, day);
                        mfrmDate = convertDate(convertToMillis(day, month, year));
                        tomindate = convertToMillis(day, month, year);
                        alterdates = convertDate1(convertToMillis(day, month, year));
                        frmbtnDatePicker.setText(mfrmDate);
                    }
                }, year, month, day);

                mDatePicker.setTitle("Please select From Date");
                   mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis());
                   mDatePicker.show();
            }
        });
    }

    private void validation() {
        if(TextUtils.isEmpty(mfrmDate)){
            Toast.makeText(getApplicationContext(), "Select Date!", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(ContacttypeNames)){
            Toast.makeText(getApplicationContext(), "Select Contact Type!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cont_corp_names))
        {
            Toast.makeText(getApplicationContext(), "Select Customer!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(VisitpurrposeNames))
        {
            Toast.makeText(getApplicationContext(), "Select Visit Purpose!", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                Insertdata();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String convertDate(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
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

    public String convertDate1(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-YYYY");
        String formattedDate = df.format(mTime);
        return formattedDate;
    }
    private void Retrievingdata() {
        final ProgressDialog loading = new ProgressDialog(VisitPlanActivity.this);
        loading.setMessage("Checking Work Start...");
        loading.setCancelable(false);
        loading.show();
        apiInterfacePlanschk = (APIInterfacePlanschk) APIClientPlanchk.getClient().create(APIInterfacePlanschk.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select count(*)cnt from work_start  where  username='" + uname + "' and trunc(starttime)='" + currentDateTimeString1 + "'");
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

        Call<Getcheckoutchk> call4 = apiInterfacePlanschk.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckoutchk>() {
            @Override
            public void onResponse(Call<Getcheckoutchk> call, Response<Getcheckoutchk> response) {
                loading.dismiss();
                try {
                    if (response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0")) {
                        //  postData();
                        Toast.makeText(getApplicationContext(), "Please CheckIn Before We Start The Work...", Toast.LENGTH_SHORT).show();

                    } else if(response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("1")){
                       Retrievingdata1();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Getcheckoutchk> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Server Problem ,Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Retrievingdata1() {
      //  final ProgressDialog loading = new ProgressDialog(VisitPlanActivity.this);
       // loading.setMessage("Checking ...");
      //  loading.setCancelable(false);
       // loading.show();
        apiInterfacePlanschk = (APIInterfacePlanschk) APIClientPlanchk.getClient().create(APIInterfacePlanschk.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select status,statusflg from work_start  where  username='" + uname + "' and trunc(starttime)='" + currentDateTimeString1 + "'");
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
        Call<Getcheckoutchk> call4 = apiInterfacePlanschk.getResult(jsonObject7);
        call4.enqueue(new Callback<Getcheckoutchk>() {
            @Override
            public void onResponse(Call<Getcheckoutchk> call, Response<Getcheckoutchk> response) {
               // loading.dismiss();
                try {
                    if (response.body().getResult().get(0).getResult().getRow().get(0).getStatus().equals("0")) {
                        Toast.makeText(getApplicationContext(), "Already The Work Is End For This Day..", Toast.LENGTH_SHORT).show();
                    } else if(response.body().getResult().get(0).getResult().getRow().get(0).getStatus().equals("1")){
                      //  postdatastate();
                        postdataContacttype();
                        postdataVisitpurpose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Getcheckoutchk> call, Throwable t) {
              //  loading.dismiss();
                Toast.makeText(getApplicationContext(), "Server Problem ,Please Wait...", Toast.LENGTH_SHORT).show();
            }
        });
    }

  /*  private void postdatastate() {
        final ProgressDialog loading = new ProgressDialog(VisitPlanActivity.this);
        loading.setMessage("Loading Employee Id...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceadm = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "select nickname,username from axusers where active='T'");
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

        Call<Exampleadmin> call4 = apiInterfaceadm.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {
                loading.dismiss();
                //  String jsonString = response.body().toString();

                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        Statename =  response.body().getResult().get(0).getResult().getRow().get(i).getNickname();
                         stateId = response.body().getResult().get(0).getResult().getRow().get(i).getUsername();
                        PartNameState.add(Statename);
                          PartIdState.add(stateId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter = new ArrayAdapter<String>(VisitPlanActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameState);
                PartSpinnerState.setAdapter(adapter);
                PartSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        part_IdState = PartIdState.get(position);
                        nickname = PartNameState.get(position);

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(VisitPlanActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            Toast.makeText(VisitPlanActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }

    private void postdataVisitpurpose() {
        // final ProgressDialog loading = new ProgressDialog(VisitPlanActivity.this);
       // loading.setMessage("Loading Visit Purpose...");
       // loading.setCancelable(false);
       // loading.show();
        apiInterfacevisit = APIClientvisit.getClient().create(APIInterfacevisit.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select visitpurpose from visit_purpose where active = 'T' order by visitpurpose");
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

        Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Example> call4 = apiInterfacevisit.getResult(jsonObject7);
        call4.enqueue(new Callback<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Example>() {
            @Override
            public void onResponse(Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Example> call, Response<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Example> response) {
               // loading.dismiss();

                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        VisitpurrposeName = response.body().getResult().get(0).getResult().getRow().get(i).getVisitpurpose();

                        PartNameVisitpurpose.add(VisitpurrposeName);

                    }
                } catch (Exception e) {
                   // loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(VisitPlanActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameVisitpurpose);
                PartSpinnerVisitpurpose.setAdapter(adapter);

                PartSpinnerVisitpurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        VisitpurrposeNames = PartNameVisitpurpose.get(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(VisitPlanActivity.this, "Nothing is selected", Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onFailure(Call<com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.retrievevisit.Example> call, Throwable t) {
              //  loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void postdataContacttype() {
        PartNameContacttype.clear();
        addressofselect.setText("");
        corp_name.setText(" ");
        final ProgressDialog loading = new ProgressDialog(VisitPlanActivity.this);
        loading.setMessage("Loading ContactType...");
        loading.setCancelable(false);
        loading.show();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");

        jsonObject5.addProperty("sql", "select 'Doctor' dt from dual union all select 'Ambulance Driver' dt from dual union all select 'Others' dt from dual union all select 'Corporate Name' dt from dual order by dt");
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

        Call<Example> call4 = apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                loading.dismiss();
//


                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {

                        ContacttypeName = response.body().getResult().get(0).getResult().getRow().get(i).getDt();
                        //ContacttypeId = list.get(0).getResult().getRow().get(i).getPincodeid();//
                        PartNameContacttype.add(ContacttypeName);
                        //PartIdContacttype.add(ContacttypeId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(VisitPlanActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameContacttype);
                PartSpinnerContacttype.setAdapter(adapter);
                PartSpinnerContacttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ContacttypeNames = PartNameContacttype.get(position);
                        postdataContacttype1();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(VisitPlanActivity.this, "No Contacttype  Found", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void postdataContacttype1() {
        PartNameCustomer.clear();
        PartNameCustomerid.clear();
        PartIdContacttype.clear();
        Partcorpsname.clear();
        addressofselect.setText("");
        corp_name.setText(" ");
        final ProgressDialog loading = new ProgressDialog(VisitPlanActivity.this);
        loading.setMessage("Loading Customer...");
        loading.setCancelable(false);
        loading.show();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql","select b.contact_name,b.cont_corp_name,c.address,d.name from emp_mapping a join emp_contactmapping b on  a.emp_mappingid =b.emp_mappingid join CONT_CORP c on b.CONTACT_NAME=c.CONT_CORPID left join CORPORATE d on c.CORPORATE=d.CORPORATEid where a.usrname ='"+usersname+"' and b.contact_type='"+ContacttypeNames+"' union select b.contact_name,b.cont_corp_name,c.address,c.name from emp_mapping a join emp_contactmapping b on  a.emp_mappingid =b.emp_mappingid join CORPORATE c on b.CONTACT_NAME=c.CORPORATEid where a.usrname = '"+usersname+"' and b.contact_type='"+ContacttypeNames+"'");
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

        Call<Example> call4 = apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                loading.dismiss();

                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        cont_corp_name = response.body().getResult().get(0).getResult().getRow().get(i).getCont_corp_name();
                        cusid=response.body().getResult().get(0).getResult().getRow().get(i).getContact_name();
                        ContacttypeId = response.body().getResult().get(0).getResult().getRow().get(i).getAddress();
                        corporatename=response.body().getResult().get(0).getResult().getRow().get(i).getName();
                        PartNameCustomer.add(cont_corp_name);
                        PartNameCustomerid.add(cusid);
                        PartIdContacttype.add(ContacttypeId);
                        Partcorpsname.add(corporatename);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(VisitPlanActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameCustomer);
                PartSpinnerCustomer.setAdapter(adapter);
                PartSpinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        cont_corp_names = PartNameCustomer.get(position);
                        cusid=PartNameCustomerid.get(position);
                        gettaddress=PartIdContacttype.get(position);
                        getscorpname=Partcorpsname.get(position);
                        addressofselect.setText(gettaddress);
                        corp_name.setText(getscorpname);

                        //   Toast.makeText(getApplicationContext(),part_IdPincode,Toast.LENGTH_SHORT);//
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                PartNameCustomer.clear();
                PartSpinnerCustomer.setAdapter(null);
                addressofselect.setText(" ");
                Toast.makeText(VisitPlanActivity.this, "No Customer Found", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postdataJoint1() {
        apiInterfaceadm = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "select nickname,username,axusersid from axusers where active='T' order by nickname");
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

        Call<Exampleadmin> call4 = apiInterfaceadm.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {

                try {
                    for (int i = 0; i <= response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        Joint1name =  response.body().getResult().get(0).getResult().getRow().get(i).getNickname();
                        Joint1username = response.body().getResult().get(0).getResult().getRow().get(i).getAxusersid();
                        PartNameJoint1.add(Joint1name);
                        PartIdJoint1.add(Joint1username);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter = new ArrayAdapter<String>(VisitPlanActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameJoint1);
                PartSpinnerJoint1.setAdapter(adapter);
                PartSpinnerJoint1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        part_IdJoint1 = PartIdJoint1.get(position);
                        Joint1names = PartNameJoint1.get(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {

                Toast.makeText(VisitPlanActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void postdataJoint2() {
        apiInterfaceadm = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "select nickname,username,axusersid from axusers where active='T' order by nickname");
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

        Call<Exampleadmin> call4 = apiInterfaceadm.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {

                //  String jsonString = response.body().toString();

                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        Joint2name =  response.body().getResult().get(0).getResult().getRow().get(i).getNickname();
                        Joint2username = response.body().getResult().get(0).getResult().getRow().get(i).getAxusersid();
                        //  stateId = response.body().getResult().get(0).getResult().getRow().get(i).getUsername();
                        PartNameJoint2.add(Joint2name);
                        PartIdJoint2.add(Joint2username);
                        //   PartIdState.add(stateId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter = new ArrayAdapter<String>(VisitPlanActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameJoint2);
                PartSpinnerJoint2.setAdapter(adapter);
                PartSpinnerJoint2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //  part_IdState = PartIdState.get(position);
                        part_IdJoint2 = PartIdJoint2.get(position);
                        Joint2names = PartNameJoint2.get(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {

                Toast.makeText(VisitPlanActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void postdataJoint3() {

        apiInterfaceadm = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "select nickname,username,axusersid from axusers where active='T' order by nickname");
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

        Call<Exampleadmin> call4 = apiInterfaceadm.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {

                //  String jsonString = response.body().toString();

                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        Joint3name =  response.body().getResult().get(0).getResult().getRow().get(i).getNickname();
                        Joint3username = response.body().getResult().get(0).getResult().getRow().get(i).getAxusersid();
                        //  stateId = response.body().getResult().get(0).getResult().getRow().get(i).getUsername();
                        PartNameJoint3.add(Joint3name);
                        PartIdJoint3.add(Joint3username);
                        //   PartIdState.add(stateId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter = new ArrayAdapter<String>(VisitPlanActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameJoint3);
                PartSpinnerJoint3.setAdapter(adapter);
                PartSpinnerJoint3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //  part_IdState = PartIdState.get(position);
                        Joint3names = PartNameJoint3.get(position);
                        part_IdJoint3 = PartIdJoint3.get(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {

                Toast.makeText(VisitPlanActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void Insertdata() throws JSONException {

        final ProgressDialog loading = new ProgressDialog(VisitPlanActivity.this);
        loading.setMessage("Saving ...");
        loading.setCancelable(false);
        loading.show();
        // RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//
        apiInterfaceSavetrv = APIClientSavetrv.getClient().create(APIInterfaceSavetrv.class);
        JsonObject object = new JsonObject();
        JsonObject object1 = new JsonObject();
        JsonObject object2 = new JsonObject();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        JsonObject jsonObject3=new JsonObject();
        JsonObject jsonObject4=new JsonObject();
        JsonArray array2 = new JsonArray();
        JsonArray array5 = new JsonArray();
        JsonArray array = new JsonArray();
        JsonArray array6 = new JsonArray();
        JsonArray array7=new JsonArray();
        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams4= new JsonObject();
        JsonObject jsonParams5= new JsonObject();
        JsonObject jsonParams6= new JsonObject();
        JsonObject jsonParams7= new JsonObject();
        jsonParams1.addProperty("contact_type",ContacttypeNames);
        jsonParams1.addProperty("customer_name",cont_corp_names);
        jsonParams1.addProperty("cusid",cusid);
        jsonParams1.addProperty("corporate",getscorpname);
        jsonParams1.addProperty("address",gettaddress);
      //  jsonParams5.addProperty("id",part_IdJoint1);
      //  jsonParams5.addProperty("Text",Joint1names);
        jsonParams1.addProperty("jointcall1",Joint1names);
       // jsonParams6.addProperty("id",part_IdJoint2);
       // jsonParams6.addProperty("Text",Joint2names);
        jsonParams1.addProperty("jointcall2",Joint2names);
      //  jsonParams7.addProperty("id",part_IdJoint3);
      //  jsonParams7.addProperty("Text",Joint3names);
        jsonParams1.addProperty("jointcall3",Joint3names);
        object.addProperty("rowno", "002");
        object.addProperty("text", "0");
        object.add("columns", jsonParams1);
        jsonParams4.addProperty("usr",nickname);
        jsonParams4.addProperty("employee",nickname);
        jsonParams4.addProperty("employee_usr",usersname);
        jsonParams4.addProperty("purpose",VisitpurrposeNames);
        jsonParams4.addProperty("priority","High");
        jsonParams4.addProperty("visitdate",mfrmDate);
        jsonParams4.addProperty("active","T");
        object1.addProperty("rowno","001");
        object1.addProperty("text","0");
        object1.add("columns",jsonParams4);

        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", " ");
        jsonParams2.addProperty("username",uname);
        jsonParams2.addProperty("password",pswd);
        jsonParams2.addProperty("transid", "vplan");
        jsonParams2.addProperty("recordid", "0");

        array5.add(object);
        array6.add(object1);
        array7.add(object2);


        jsonObject3.add("axp_recid1",array6);
        jsonObject4.add("axp_recid2", array5);

        array.add(jsonObject3);
        array.add(jsonObject4);
        jsonParams2.add("recdata", array);

        jsonObject.add("savedata", jsonParams2);

        array2.add(jsonObject);
        jsonObject2.add("_parameters", array2);


        // Enter the correct url for your api service site
        apiInterfaceSavetrv =  APIClientSavetrv.getClient().create(APIInterfaceSavetrv.class);
        Call<ExampleSave> call2 = apiInterfaceSavetrv.getResult(jsonObject2);
        call2.enqueue(new Callback<ExampleSave>() {
            @Override
            public void onResponse(Call<ExampleSave> call, Response<ExampleSave> response) {
                loading.dismiss();
                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(VisitPlanActivity.this, TabbedActivity.class));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ExampleSave> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(VisitPlanActivity.this, "No Records Found", Toast.LENGTH_LONG).show();
            }
        });
    }
}
