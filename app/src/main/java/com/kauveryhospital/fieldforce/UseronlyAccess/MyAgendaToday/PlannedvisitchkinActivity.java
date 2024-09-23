package com.kauveryhospital.fieldforce.UseronlyAccess.MyAgendaToday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.APIClientOhc;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.APIInterfaceOhc;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.GetcheckoutOhc;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.APIClientvisit;
import com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.APIInterfacevisit;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.TravelExpActivity;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.APIClient;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.APIInterface;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.Example;
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

public class PlannedvisitchkinActivity extends AppCompatActivity {
    String cont_corpid,uname,visitdetailid,pswd,message,contactname,jointcall1,jointcall2,jointcall3,currentDateTimeString,VisitpurrposeName,VisitpurrposeNames,latitude,longitude;
    APIInterface apiInterface;
    APIInterfaceOhc apiInterfaceplan;
    public static final String PREFS_NAME = "loginpref";
    TextView getcustmer,getchkintime,getjoint1,getjoint2,getjoint3;
    SearchableSpinner PartSpinnerVisitpurpose;
    List<String> PartNameVisitpurpose;
    List<String> PartIdVisitpurpose;
    Button btnpchkin;
    ImageView backarrow;
    static final String PREFS_NAMES = "preferenceName";
    APIInterfacevisit apiInterfacevisit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plannedvisit);
        btnpchkin=findViewById(R.id.btnpchkin);
        getcustmer=findViewById(R.id.getcustmer);
        getchkintime=findViewById(R.id.getchkintime);
        getjoint1=findViewById(R.id.getjoint1);
        getjoint2=findViewById(R.id.getjoint2);
        getjoint3=findViewById(R.id.getjoint3);
        backarrow=findViewById(R.id.backarrow);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        currentDateTimeString = formatter.format(todayDate);
        PartSpinnerVisitpurpose = findViewById(R.id.visitpurpose);
        PartSpinnerVisitpurpose.setTitle("Select Visit Purpose");
        PartNameVisitpurpose = new ArrayList<>();
        PartIdVisitpurpose = new ArrayList<>();
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        SharedPreferences settings = getSharedPreferences(PREFS_NAMES, 0);
        latitude = settings.getString("curlatitude", "");
        longitude = settings.getString("curlongitude", "");
        Bundle bundle = getIntent().getExtras();
        cont_corpid = bundle.getString("cont_corpid");
        postdataVisitpurpose();
        Checkingdatain1();
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlannedvisitchkinActivity.this, PlannedCheckInActivity.class);
                startActivity(intent);
            }
        });
        btnpchkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               validation();
            }
        });
    }

    private void validation() {
        if(TextUtils.isEmpty(VisitpurrposeNames))
        {
            Toast.makeText(getApplicationContext(), "Select Visit Purpose", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                postDatachkin();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void Checkingdatain1()
    {
        final ProgressDialog loading = new ProgressDialog(PlannedvisitchkinActivity.this);
        loading.setMessage("Loading ...");
        loading.show();
        apiInterfaceplan = APIClientOhc.getClient().create(APIInterfaceOhc.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s","");
        jsonObject5.addProperty("sql","select b.cont_corpid,b.contactname, a.visitdetailid,A.Jointcall1,A.Jointcall2,A.Jointcall3 from cont_corp b join visitdetail a  on a.contactname=b.cont_corpid where a.status=0  and A.employee='"+uname+"' and b.cont_corpid='"+cont_corpid+"' and a.visitdate=trunc(sysdate) union all select b.corporateid cont_corpid,b.name contactname,a.visitdetailid,A.Jointcall1,A.Jointcall2,A.Jointcall3 from corporate b join visitdetail a on a.contactname=b.corporateid where a.status=0  and A.employee='"+uname+"' and b.corporateid='"+cont_corpid+"' and a.visitdate=trunc(sysdate)order by 2");
       // jsonObject5.addProperty("sql","select b.cont_corpid,b.contactname, a.visitdetailid,A.Jointcall1,A.Jointcall2,A.Jointcall3 from    cont_corp b join visitdetail a  on a.contactname=b.cont_corpid where a.status=0  and A.employee='"+uname+"' and b.cont_corpid='"+cont_corpid+"' and a.visitdate=trunc(sysdate)order by b.contactname");

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
                cont_corpid=  response.body().getResult().get(0).getResult().getRow().get(0).getCont_corpid();
                contactname=response.body().getResult().get(0).getResult().getRow().get(0).getContactname();
                visitdetailid=response.body().getResult().get(0).getResult().getRow().get(0).getVisitdetailid();
                jointcall1=response.body().getResult().get(0).getResult().getRow().get(0).getJointcall1();
                jointcall2=response.body().getResult().get(0).getResult().getRow().get(0).getJointcall2();
                jointcall3=response.body().getResult().get(0).getResult().getRow().get(0).getJointcall3();
                getcustmer.setText(contactname);
                getchkintime.setText(currentDateTimeString);
                getjoint1.setText(jointcall1);
                getjoint2.setText(jointcall2);
                getjoint3.setText(jointcall3);


            }
            @Override
            public void onFailure(Call<GetcheckoutOhc> call, Throwable t) {
                loading.dismiss();
                // btn_checkin.setVisibility(View.VISIBLE);

            }
        });
    }
    private void postdataVisitpurpose() {
        PartNameVisitpurpose.clear();

        final ProgressDialog loading = new ProgressDialog(PlannedvisitchkinActivity.this);
        loading.setMessage("Loading Visit Purpose...");
        loading.setCancelable(false);
        loading.show();
        apiInterfacevisit = APIClientvisit.getClient().create(APIInterfacevisit.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "select visit_purposeid,visitpurpose from visit_purpose where active = 'T'");
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

        Call<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Example> call4 = apiInterfacevisit.getResult(jsonObject7);
        call4.enqueue(new Callback<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Example>() {
            @Override
            public void onResponse(Call<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Example> call, Response<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Example> response) {
                loading.dismiss();




                try {
                    for (int i = 0; i <response.body().getResult().get(0).getResult().getRow().size(); i++) {

                        VisitpurrposeName = response.body().getResult().get(0).getResult().getRow().get(i).getVisitpurpose();
                        //  ContacttypeId = list.get(0).getResult().getRow().get(i).getPincodeid();
                        PartNameVisitpurpose.add(VisitpurrposeName);
                        //  PartIdContacttype.add(ContacttypeId);
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PlannedvisitchkinActivity.this, android.R.layout.simple_spinner_dropdown_item, PartNameVisitpurpose);
                PartSpinnerVisitpurpose.setAdapter(adapter);

                PartSpinnerVisitpurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        VisitpurrposeNames = PartNameVisitpurpose.get(position);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onFailure(Call<com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit.Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "OOPS server problem... ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void postDatachkin() throws JSONException {
        final ProgressDialog loading = new ProgressDialog(PlannedvisitchkinActivity.this);
        loading.setMessage("Loading CheckIn...");
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
        jsonParams1.addProperty("visit_purpose",VisitpurrposeNames);
        jsonParams1.addProperty("purpose",VisitpurrposeNames);
        jsonParams1.addProperty("longitude",longitude);
        jsonParams1.addProperty("latitude",latitude);
        jsonParams1.addProperty("jointcall3",jointcall3);
        jsonParams1.addProperty("jointcall2",jointcall2);
        jsonParams1.addProperty("jointcall1",jointcall1);
        jsonParams1.addProperty("checkouttime","");
        jsonParams1.addProperty("checkintime",currentDateTimeString);
        jsonParams1.addProperty("visitdetailid",visitdetailid);
        jsonParams1.addProperty("customername",contactname);
    //    jsonParams1.addProperty("currdate", currentDateTimeString); //
        jsonParams1.addProperty("usrname", uname);
        jsonParams1.addProperty("ctype", "planned");

        object.addProperty("rowno","001");
        object.addProperty("text","0");
        object.add("columns",jsonParams1);

        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username",uname);
        jsonParams2.addProperty("password",pswd);
        jsonParams2.addProperty("transid","pchek");
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
        call2.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response)
            {
                loading.dismiss();
                try {
                    if(response.body().getResult().get(0).getError()!=null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(PlannedvisitchkinActivity.this, message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PlannedvisitchkinActivity.this, PlannedCheckInActivity.class));
                    }
                    else if(response.body().getResult().get(0).getMessage()!=null){
                        message= response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(PlannedvisitchkinActivity.this, message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PlannedvisitchkinActivity.this, PlannedCheckInActivity.class));
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(PlannedvisitchkinActivity.this,"Server Problem ,Please Wait...", Toast.LENGTH_LONG).show();
            }
        });
    }
}
