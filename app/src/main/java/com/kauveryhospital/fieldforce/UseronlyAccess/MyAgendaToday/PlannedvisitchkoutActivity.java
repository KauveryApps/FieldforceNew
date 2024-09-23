package com.kauveryhospital.fieldforce.UseronlyAccess.MyAgendaToday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
import com.kauveryhospital.fieldforce.workstartserviceuseronly.APIClient;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.APIInterface;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.Example;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kauveryhospital.fieldforce.UseronlyAccess.MyAgendaToday.PlannedvisitchkinActivity.PREFS_NAMES;

public class PlannedvisitchkoutActivity extends AppCompatActivity {
TextView getchkintime,tcusname,chkouttime,visitpurpose;
ImageView backarrow;
String message,uname,pswd,latitude,longitude,contactname,checkinid,checkintime,visitdetailid,visit_purpose,currentDateTimeString;
    public static final String PREFS_NAME = "loginpref";
    APIInterface apiInterface;
    APIInterfaceOhc apiInterfaceplan;
    static final String PREFS_NAMES = "preferenceName";
    Button btnpchkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plannedvisitchkout);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        currentDateTimeString = formatter.format(todayDate);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        SharedPreferences settings = getSharedPreferences(PREFS_NAMES, 0);
        latitude = settings.getString("curlatitude", "");
        longitude = settings.getString("curlongitude", "");
        getchkintime =findViewById(R.id.getchkintime);
        tcusname =findViewById(R.id.tcusname);
        chkouttime  =findViewById(R.id.chkouttime);
        visitpurpose=findViewById(R.id.visitpurpose);
        backarrow  =findViewById(R.id.backarrow);
        btnpchkout =findViewById(R.id.btnpchkout);
        Checkingdatain1();
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlannedvisitchkoutActivity.this, PlannedCheckInActivity.class);
                startActivity(intent);
            }
        });
        btnpchkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postDatachkout();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void Checkingdatain1()
    {
        final ProgressDialog loading = new ProgressDialog(PlannedvisitchkoutActivity.this);
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
        jsonObject5.addProperty("sql","select b.contactname, c.checkinid, c.checkintime, a.visitdetailid,c.visit_purpose from visitdetail a join checkin c on a.visitdetailid = c.visitdetailid join cont_corp b on a.contactname=b.cont_corpid left join cout1 o on c.checkinid=o.checkinid where a.employee='"+uname+"' and o.checkinid is null union select b.name contactname, c.checkinid, c.checkintime, a.visitdetailid,c.visit_purpose from visitdetail a join checkin c on a.visitdetailid = c.visitdetailid join corporate b on a.contactname=b.corporateid left join cout1 o on c.checkinid=o.checkinid where a.employee='"+uname+"' and o.checkinid is null order by contactname");

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
                checkinid=  response.body().getResult().get(0).getResult().getRow().get(0).getCheckinid();
                contactname=response.body().getResult().get(0).getResult().getRow().get(0).getContactname();
                visitdetailid  =response.body().getResult().get(0).getResult().getRow().get(0).getVisitdetailid();
                checkintime=response.body().getResult().get(0).getResult().getRow().get(0).getCheckintime();
                visit_purpose=response.body().getResult().get(0).getResult().getRow().get(0).getVisitPurpose();
               //jointcall3=response.body().getResult().get(0).getResult().getRow().get(0).getJointcall3(); //
                tcusname.setText(contactname);
                getchkintime.setText(checkintime);
                chkouttime.setText(currentDateTimeString);
                visitpurpose.setText(visit_purpose);
            }
            @Override
            public void onFailure(Call<GetcheckoutOhc> call, Throwable t) {
                loading.dismiss();

            }
        });
    }
    public void postDatachkout() throws JSONException {
        final ProgressDialog loading = new ProgressDialog(PlannedvisitchkoutActivity.this);
        loading.setMessage("Loading Check Out...");
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



        jsonParams1.addProperty("longitude",longitude);

        jsonParams1.addProperty("latitude",latitude);

        jsonParams1.addProperty("checkouttime", currentDateTimeString);
        jsonParams1.addProperty("remarks",visit_purpose);

        // jsonParams1.addProperty("visit_purpose",visit_purpose);//

        jsonParams1.addProperty("checkintime",checkintime);
        jsonParams1.addProperty("visitdetailid",visitdetailid);
        jsonParams1.addProperty("checkinid",checkinid);
        jsonParams1.addProperty("customername",contactname);
        jsonParams1.addProperty("usrname", uname);

        object.addProperty("rowno","001");
        object.addProperty("text","0");
        object.add("columns",jsonParams1);

        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username",uname);
        jsonParams2.addProperty("password",pswd);
        jsonParams2.addProperty("transid","cout");
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
                        Toast.makeText(PlannedvisitchkoutActivity.this, message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PlannedvisitchkoutActivity.this, TabbedActivity.class));
                    }
                    else if(response.body().getResult().get(0).getMessage()!=null){
                        message= response.body().getResult().get(0).getMessage().get(0).getMsg();
                        Toast.makeText(PlannedvisitchkoutActivity.this, message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PlannedvisitchkoutActivity.this, TabbedActivity.class));
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(PlannedvisitchkoutActivity.this,"Server Problem ,Please Wait...", Toast.LENGTH_LONG).show();
            }
        });
    }
}
