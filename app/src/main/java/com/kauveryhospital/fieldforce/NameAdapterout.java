package com.kauveryhospital.fieldforce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.DatabaseHelper;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.Name;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIClientSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.APIInterfaceSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ExampleSave;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.savedata.ResultSave;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NameAdapterout extends ArrayAdapter<Name> implements NetworkChangeCallback{

    //storing all the names in the list
    private List<Name> names;
    String message,inputval,currentDateTimeString,contact,customer,contactout,customerout,checkintimeout,checkouttimeout,systemidout,latitudeout,longitudeout,addressout,employeeout,visitpurposeout,pswdout,statusout;
    EditText input;
    private DatabaseHelper db;
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    Button buttonsave;
    APIInterfaceSave apiInterfaceSave;
    //context object
    TextView txtvcontype,txtvchkintime,txtvcustomer,txtvvisitpurpose;
    private Context context;
    List<ResultSave> listsave = new ArrayList<>();
    private ArrayAdapter nameAdapterout;
    ImageView imageViewStatus;
    public static final String DATA_SAVED_BROADCAST = "com.kauveryhospitaldev.fieldforce";
    //constructor
    public NameAdapterout(Context context, int resource, List<Name> names, String latitude, String longitude, String employee, String pswd) {
        super(context, resource, names);
        this.context = context;
        this.names = names;
        this.latitudeout=latitude;
        this.longitudeout=longitude;
        this.employeeout=employee;
        this.pswdout=pswd;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        View listViewItem = inflater.inflate(R.layout.namesout, null, true);
        db = new DatabaseHelper(context);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        currentDateTimeString = formatter.format(todayDate);
        txtvcontype = (TextView) listViewItem.findViewById(R.id.txtcontype);
        txtvchkintime = (TextView) listViewItem.findViewById(R.id.txtvchkintime);
        txtvcustomer=(TextView)listViewItem.findViewById(R.id.txtvcustomer);
        txtvvisitpurpose=(TextView)listViewItem.findViewById(R.id.txtvvisitpurpose);
        imageViewStatus = (ImageView) listViewItem.findViewById(R.id.imageViewStatus);
        buttonsave=listViewItem.findViewById(R.id.buttonsave);
        //getting the current name
        Name name = names.get(position);

        //setting the name to textview
        txtvcontype.setText(name.getName());
        txtvcustomer.setText(name.getCustomer());
        txtvchkintime.setText(name.getCheckintime());
        txtvvisitpurpose.setText(name.getVisitpurpose());
        contactout=name.getName();
        customerout=name.getCustomer();
        checkintimeout=name.getCheckintime();
        checkouttimeout=currentDateTimeString;
        systemidout=name.getSystemid();
        addressout=name.getAddress();
        employeeout=name.getEmployee();
        visitpurposeout=name.getVisitpurpose();
        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                withEditText(v);
            }
        });
        //if the synced status is 0 displaying
        //queued icon
        //else displaying synced icon
        if (name.getStatus() == 0) {
            imageViewStatus.setBackgroundResource(R.drawable.closes);
        }
        else {
            imageViewStatus.setBackgroundResource(R.drawable.synced);
        }
        if (name.getCheckouttimestatus().equals("false"))
        {
           buttonsave.setEnabled(true);
        }
       //imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        else
        {
          buttonsave.setEnabled(false);
          buttonsave.setBackgroundResource(R.color.grey);
        }
          //imageViewStatus.setBackgroundResource(R.drawable.success);
        return listViewItem;
    }
    public void withEditText(View view) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("");
        builder.setIcon(R.mipmap.kauveryslogos);
        builder.setMessage("Are You Want To Checkout?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            postData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });




        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public void postData() throws JSONException {

            final ProgressDialog loading = new ProgressDialog(context);
            loading.setCancelable(false);
            loading.setMessage("Save...");
            loading.show();

            //RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//
            apiInterfaceSave = APIClientSave.getClient().create(APIInterfaceSave.class);
            JsonObject object = new JsonObject();
            JsonObject jsonObject = new JsonObject();
            JsonObject jsonObject2 = new JsonObject();
            JsonArray array2 = new JsonArray();
            JsonArray array5 = new JsonArray();
            JsonArray array = new JsonArray();
            JsonObject jsonParams1 = new JsonObject();
            JsonObject jsonParams2 = new JsonObject();
            JsonObject jsonParams3 = new JsonObject();
            jsonParams1.addProperty("contype", contactout);
            jsonParams1.addProperty("employee", "admin");
            jsonParams1.addProperty("customer_name", customerout);
            jsonParams1.addProperty("visit_purpose", visitpurposeout);
            jsonParams1.addProperty("address", addressout);
            jsonParams1.addProperty("checkin", checkintimeout);
            jsonParams1.addProperty("systemid",systemidout);
            jsonParams1.addProperty("checkouttime",checkouttimeout);
            jsonParams1.addProperty("c_latitude", latitudeout);
            jsonParams1.addProperty("c_longitude", longitudeout);
            object.addProperty("rowno", "001");
            object.addProperty("text", "0");
            object.add("columns", jsonParams1);
            jsonParams2.addProperty("axpapp", "fieldforce");
            jsonParams2.addProperty("s", "");
            jsonParams2.addProperty("username", employeeout);
            jsonParams2.addProperty("password", pswdout);
            jsonParams2.addProperty("transid", "ochot");
            jsonParams2.addProperty("recordid", "0");
            array5.add(object);
            jsonParams3.add("axp_recid1", array5);
            array.add(jsonParams3);
            jsonParams2.add("recdata", array);
            jsonObject.add("savedata", jsonParams2);
            array2.add(jsonObject);
            jsonObject2.add("_parameters", array2);
            // Enter the correct url for your api service site//

            // Enter the correct url for your api service site//
            apiInterfaceSave = APIClientSave.getClient().create(APIInterfaceSave.class);
            Call<ExampleSave> call2 = apiInterfaceSave.getResult(jsonObject2);
            loading.dismiss();
            call2.enqueue(new Callback<ExampleSave>() {
                @Override
                public void onResponse(Call<ExampleSave> call, Response<ExampleSave> response) {
                    listsave = response.body().getResult();
                    try {
                        if (response.body().getResult().get(0).getError() != null) {
                            message = listsave.get(0).getError().getMsg();
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        } else if (response.body().getResult().get(0).getMessage() != null) {
                            message = listsave.get(0).getMessage().get(0).getMsg();
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(CorporateActivity.this, TabbedActivity.class));//
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ExampleSave> call, Throwable t) {
                    loading.dismiss();
                    //  loadNames();
                    //  savecorpname = editTextName.getText().toString().trim();
                    saveNameToLocalStorage(contactout, customerout, checkintimeout,checkouttimeout,systemidout, latitudeout, longitudeout, addressout,employeeout, visitpurposeout, NAME_NOT_SYNCED_WITH_SERVER);
                    Toast.makeText(context, "Data saved in Local Storage!!!", Toast.LENGTH_LONG).show();
                }
            });
        }
    public void postData1(final int id, final String contactout,final String customerout,final  String checkintimeout,final String checkouttimeout,final String systemidout,final  String latitudeout,final String longitudeout,final  String addressout,final  String employeeout,final String visitpurposeout) throws JSONException {
        refreshList();
        final ProgressDialog loading = new ProgressDialog(context);
        loading.setCancelable(false);
        loading.setMessage("Save Corporate...");
        loading.show();

        // RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());//
        apiInterfaceSave =  APIClientSave.getClient().create(APIInterfaceSave.class);
        JsonObject object = new JsonObject();
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        JsonArray array2 = new JsonArray();
        JsonArray array5 = new JsonArray();
        JsonArray array = new JsonArray();
        JsonObject jsonParams1 = new JsonObject();
        JsonObject jsonParams2 = new JsonObject();
        JsonObject jsonParams3 = new JsonObject();
        jsonParams1.addProperty("contype", contactout);
        jsonParams1.addProperty("employee", employeeout);
        jsonParams1.addProperty("customer_name", customerout);
        jsonParams1.addProperty("visit_purpose", visitpurposeout);
        jsonParams1.addProperty("systemid",systemidout);
        jsonParams1.addProperty("checkouttime",checkouttimeout);
        jsonParams1.addProperty("address", addressout);
        jsonParams1.addProperty("remarks", employeeout);
        jsonParams1.addProperty("checkin", checkintimeout);
        jsonParams1.addProperty("c_latitude", latitudeout);
        jsonParams1.addProperty("c_longitude", longitudeout);
        object.addProperty("rowno", "001");
        object.addProperty("text", "0");
        object.add("columns", jsonParams1);
        jsonParams2.addProperty("axpapp", "fieldforce");
        jsonParams2.addProperty("s", "");
        jsonParams2.addProperty("username", employeeout);
        jsonParams2.addProperty("password", pswdout);
        jsonParams2.addProperty("transid", "ochin");
        jsonParams2.addProperty("recordid", "0");
        array5.add(object);
        jsonParams3.add("axp_recid1", array5);
        array.add(jsonParams3);
        jsonParams2.add("recdata", array);
        jsonObject.add("savedata", jsonParams2);
        array2.add(jsonObject);
        jsonObject2.add("_parameters", array2);

        // Enter the correct url for your api service site//
        apiInterfaceSave = APIClientSave.getClient().create(APIInterfaceSave.class);
        Call<ExampleSave> call2 = apiInterfaceSave.getResult(jsonObject2);
        loading.dismiss();
        call2.enqueue(new Callback<ExampleSave>() {
            @Override
            public void onResponse(Call<ExampleSave> call, Response<ExampleSave> response) {

                try {
                    if (response.body().getResult().get(0).getError() != null) {
                        message = response.body().getResult().get(0).getError().getMsg();
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResult().get(0).getMessage() != null) {
                        message = response.body().getResult().get(0).getMessage().get(0).getMsg();
                        refreshList();
                        db.updateNameStatus(id, NameAdapterout.NAME_SYNCED_WITH_SERVER);
                        imageViewStatus.setBackgroundResource(R.drawable.synced);
                       // loadNames();
                        refreshList();
                        //sending the broadcast to refresh the list
                        context.sendBroadcast(new Intent(NameAdapterout.DATA_SAVED_BROADCAST));
                       // loadNames();
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(CorporateActivity.this, TabbedActivity.class));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ExampleSave> call, Throwable t) {
                loading.dismiss();

                Toast.makeText(context, "server problem!!!", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity", "Status: " + status);
        if (status == false) {

            Toast.makeText(context, "check Internet connection", Toast.LENGTH_LONG).show();
        } else {
            refreshList();
            Cursor cursor = db.getUnsyncedNames2();
            if (cursor.moveToFirst()) {
                do {

                    //calling the method to save the unsynced name to MySQL//
                    try {
                        postData1(
                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTACTOUT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CUSTOMEROUT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKINTIMEOUT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CHECKOUTTIMEOUT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SYSTEMIDOUT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDEOUT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDEOUT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESSOUT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMPLOYEEOUT)),
                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VISITPURPOSEOUT))
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());
            }

        }
    }


    private void saveNameToLocalStorage(String contactout, String customerout, String checkintimeout,String checkouttimeout,String systemidout, String latitudeout, String longitudeout, String addressout, String employeeout,String visitpurposeout, int status) {
        //  editTextName.setText("");
        db.addNamechkout(contactout, customerout, checkintimeout,checkouttimeout,systemidout, latitudeout, longitudeout, addressout,employeeout, visitpurposeout,status);
        Name n = new Name(contactout,customerout,checkintimeout,checkouttimeout,systemidout, latitudeout, longitudeout, addressout,employeeout, visitpurposeout,visitpurposeout, status);
        names.add(n);
        refreshList();
    }
    private void refreshList()
    {
      //nameAdapterout.notifyDataSetChanged();//
    }
}