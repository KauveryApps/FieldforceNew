package com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.viewModel;

import android.app.Application;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.Globaldeclare.APIClient;
import com.kauveryhospital.fieldforce.Globaldeclare.APIInterface;
import com.kauveryhospital.fieldforce.Globaldeclare.Example;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.UnplannedvisitItem;

import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.common.Constants;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.common.ExcelUtils;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.common.FileShareUtils;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.contract.IMainActivityContract;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.data.ContactResponse;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.data.response.BooleanResponse;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.data.response.DataResponse;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.data.response.ErrorData;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.data.response.StateDefinition;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel implements IMainActivityContract.ViewModel {
    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private final List<ContactResponse> contactResponseList;
    private List<ContactResponse> parsedExcelDataList;
    APIInterface apiInterface;
    DataResponse<ContactResponse> responses;
    int j = 0;
    private ArrayList<UnplannedvisitItem> mExampleList;
    ArrayList<HashMap<String, String>> arraylist;
    private final MutableLiveData<DataResponse<ContactResponse>> contactsMLD;
    private final MutableLiveData<BooleanResponse> generateExcelMLD;
    private final MutableLiveData<DataResponse<ContactResponse>> excelContactsDataMLD;
    String customername,username,password, empname,visit_purpose, remarks,joint1, joint2, joint3, checkintime, status, corporate, address, customer, mtoDate, area, pincode, alterdates1, visitdate, jsonString, checkin, dayOfWeek, uname, checkouttime;

    // Constructor
    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        contactResponseList = new ArrayList<>();
        parsedExcelDataList = new ArrayList<>();
        contactsMLD = new MutableLiveData<>();
        generateExcelMLD = new MutableLiveData<>();
        excelContactsDataMLD = new MutableLiveData<>();
    }
    @Override
    public void initiateImport(String uname, String pswd) {
        Log.e(TAG, "initiateImport: ");
        username=uname;
        password=pswd;

        // Initially setting Status as 'LOADING' and set/post value to contactsMLD
        responses = new DataResponse(StateDefinition.State.LOADING, null, null);
        setContactsMLD(responses);
        // queryContactsContentProvider();
        postdatastate1();
        Log.e(TAG, "initiateImport SIZE: " + contactResponseList.size());
    }
    @Override
    public void initiateExport(List<ContactResponse> dataList) {
        Log.e(TAG, "initiateExport: ");
        BooleanResponse response;

        // Initially setting Status as 'LOADING' and set/post value to generateExcelMLD
        response = new BooleanResponse(StateDefinition.State.LOADING, false, null);
        setGenerateExcelMLD(response);

        boolean isExcelGenerated = ExcelUtils.exportDataIntoWorkbook(getApplication(), Constants.EXCEL_FILE_NAME, dataList);

        if (isExcelGenerated) {
            response = new BooleanResponse(StateDefinition.State.SUCCESS, true, null);
        } else {
            response = new BooleanResponse(StateDefinition.State.ERROR, false,
                    new ErrorData(StateDefinition.ErrorState.EXCEL_GENERATION_ERROR, "Excel not generated"));
        }

        setGenerateExcelMLD(response);
    }
    @Override
    public void initiateRead() {
        Log.e(TAG, "initiateRead: ");
        DataResponse<ContactResponse> response;
        // Initially setting Status as 'LOADING' and set/post value to excelContactsDataMLD
        response = new DataResponse(StateDefinition.State.LOADING, null, null);
        readExcelMLD(response);
        parsedExcelDataList = ExcelUtils.readFromExcelWorkbook(getApplication(), Constants.EXCEL_FILE_NAME);
        if (parsedExcelDataList.size() > 0) {
            response = new DataResponse(StateDefinition.State.SUCCESS, parsedExcelDataList, null);
        } else {
            response = new DataResponse(StateDefinition.State.ERROR, null, new ErrorData(StateDefinition.ErrorState.FILE_NOT_FOUND_ERROR, "Error reading data from excel"));
        }
        readExcelMLD(response);
    }

    @Override
    public Uri initiateSharing() {
        Log.e(TAG, "initiateSharing: ");
        return FileShareUtils.accessFile(getApplication(), Constants.EXCEL_FILE_NAME);
    }

    /**
     * Live Data for Querying of Content Provider
     */
    public LiveData<DataResponse<ContactResponse>> getContactsFromCPLiveData() {
        return contactsMLD;
    }

    /**
     * Live Data for status of Excel Workbook Generation
     */
    public LiveData<BooleanResponse> isExcelGeneratedLiveData() {
        return generateExcelMLD;
    }

    /**
     * Live Data for Reading Excel Workbook data
     */
    public LiveData<DataResponse<ContactResponse>> readContactsFromExcelLiveData() {
        return excelContactsDataMLD;
    }

    /**
     * Set/ Post Value for Contacts MLD
     */
    private void setContactsMLD(DataResponse<ContactResponse> response) {
        if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
            // Post data in Main-Thread
            contactsMLD.setValue(response);
        } else {
            // Post data in BG-Thread
            contactsMLD.postValue(response);
        }
    }

    /**
     * Set/ Post Value for Generate Excel MLD
     */
    private void setGenerateExcelMLD(BooleanResponse response) {
        if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
            // Post data in Main-Thread
            generateExcelMLD.setValue(response);
        } else {
            // Post data in BG-Thread
            generateExcelMLD.postValue(response);
        }
    }

    /**
     * Set/ Post Value for Read Excel MLD
     */
    private void readExcelMLD(DataResponse<ContactResponse> response) {
        if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
            // Post data in Main-Thread
            excelContactsDataMLD.setValue(response);
        } else {
            // Post data in BG-Thread
            excelContactsDataMLD.postValue(response);
        }
    }
    private void postdatastate1() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", username);
        jsonObject5.addProperty("password", password);
        jsonObject5.addProperty("s", " ");
         //jsonObject5.addProperty("sql", "select employee,address,purpose,visitdate,customer,checkin,checkouttime from unplanvisit where visitdate between '01-aug-2021' and '27-aug-2021' and employee='admin' and expense_status is NULL");
        jsonObject5.addProperty("sql","select employee,address,purpose,visitdate,customer,checkin,checkouttime,remarks,visit_purpose from unplanvisit where visitdate>trunc(sysdate-40) and employee='"+username+"' and expense_status is NULL  order by visitdate desc");
        //jsonObject5.addProperty("sql","select employee,address,purpose,visitdate,customer,checkin,checkouttime from unplanvisit where visitdate between '"+alterdates+"' and '"+alterdates1+"' and employee='"+uname+"' and expense_status is NULL ");
        array.put(jsonObject5);
        try {
            jsonObject6.add("getchoices", jsonObject5);
            //array.put(jsonObject6);
            JsonArray array1 = new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters", array1);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        Call<Example> call4 = apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                arraylist = new ArrayList<HashMap<String, String>>();
                mExampleList = new ArrayList<UnplannedvisitItem>();
                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        empname = response.body().getResult().get(0).getResult().getRow().get(i).getEmployee();
                        visitdate = response.body().getResult().get(0).getResult().getRow().get(i).getVisitdate();
                        address = response.body().getResult().get(0).getResult().getRow().get(i).getAddress();
                        customer = response.body().getResult().get(0).getResult().getRow().get(i).getCustomer();
                        checkin = response.body().getResult().get(0).getResult().getRow().get(i).getCheckin();
                        remarks= response.body().getResult().get(0).getResult().getRow().get(i).getRemarks();
                        checkouttime = response.body().getResult().get(0).getResult().getRow().get(i).getCheckouttime();
                        visit_purpose=response.body().getResult().get(0).getResult().getRow().get(i).getVisitpurpose();
                        // mExampleList.add(new UnplannedvisitItem(empname, visitdate,address,customer,checkin,checkouttime));
                        contactResponseList.add(new ContactResponse(empname, visitdate,address,customer,checkin,checkouttime,remarks,visit_purpose));

                        if (contactResponseList.size() > 0)
                        {
                            responses = new DataResponse(StateDefinition.State.SUCCESS, contactResponseList, null);
                        }
                        else
                            {
                            responses = new DataResponse(StateDefinition.State.ERROR, null, new ErrorData(StateDefinition.ErrorState.INTERNAL_ERROR, "please wait"));
                            }
                        setContactsMLD(responses);
                    }
                /* adapter = new ViewUnplannedAdapter(ViewUnplannedvisits.this, mExampleList);
                    cusappoint.setLayoutManager(new LinearLayoutManager(ViewUnplannedvisits.this));
                    cusappoint.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t)
            {
                // cusappoint.setAdapter(null);
                //   Toast.makeText(MainActivityViewModel.this.getClass(), "No Records Found", Toast.LENGTH_LONG).show();
            }

        });

    }
}
