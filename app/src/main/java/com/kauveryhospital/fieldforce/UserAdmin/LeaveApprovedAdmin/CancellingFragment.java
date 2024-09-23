package com.kauveryhospital.fieldforce.UserAdmin.LeaveApprovedAdmin;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIClientadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIInterfaceadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Exampleadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Resultadmin;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CancellingFragment extends Fragment {

    RecyclerView cusappoint;
    CancellingRequestAdapterAdmin adapter;
    HashMap<String, String> map;
    APIInterfaceadmin apiInterface;
    List<String> PartNameState;
    List<String> PartIdState;
    public static  final String PREFS_NAME="loginpref";
    int j=0;
    List<Resultadmin> list=new ArrayList<>();
    ImageView backarrow;
    TextView norecords;
    SwipeRefreshLayout swipeLayout;
    ArrayList<HashMap<String, String>> arraylist;
    String jsonString,pswd;
    String uname,fromdate,todate,status,employeeid,la_leaveid,cancelremarks,currentDateTimeString;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_cancelling, container, false);
        swipeLayout = root.findViewById(R.id.swipe_container);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        currentDateTimeString = formatter.format(todayDate);
        Log.d("TAGy", "onCreate: "+currentDateTimeString);
        backarrow=root.findViewById(R.id.backarrow);
        norecords=root.findViewById(R.id.norecords);
        SharedPreferences set=getActivity().getSharedPreferences(PREFS_NAME,0);
        uname=set.getString("username","");
        pswd=set.getString("password","");

        cusappoint=root.findViewById(R.id.recyclerView);
        postdatastate1();
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeLayout.setRefreshing(false);
                postdatastate1();
            }
        });


        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        swipeLayout.setRefreshing(false);
      //  postdatastate1();
    }
    public CancellingFragment() {
        // Required empty public constructor

    }
    private void postdatastate1() {
        norecords.setVisibility(View.INVISIBLE);
        cusappoint.setAdapter(null);
        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Loading...");
        loading.setCancelable(false);
        loading.show();
        apiInterface = APIClientadmin.getClient().create(APIInterfaceadmin.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s","");
        jsonObject5.addProperty("sql","select uname,reasonforleave,fromdate,todate,status,la_leaveid,cancelremarks from la_leave where status='Rejected' ");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);
            // array.put(jsonObject6);//
            JsonArray array1=new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters",array1);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Call<Exampleadmin> call4=apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Exampleadmin>() {
            @Override
            public void onResponse(Call<Exampleadmin> call, Response<Exampleadmin> response) {

                arraylist = new ArrayList<HashMap<String, String>>();
                int numbers = response.body().getResult().get(j).getResult().getRow().size();
                try {
                    for (int i = 0; i <numbers; i++) {
                        uname = response.body().getResult().get(0).getResult().getRow().get(i).getuname();
                        fromdate=  response.body().getResult().get(0).getResult().getRow().get(i).getFromdate();
                        todate = response.body().getResult().get(0).getResult().getRow().get(i).getTodate();
                        status= response.body().getResult().get(0).getResult().getRow().get(i).getStatus();
                        employeeid= response.body().getResult().get(0).getResult().getRow().get(i).getReasonforleave();
                        la_leaveid=response.body().getResult().get(0).getResult().getRow().get(i).getLa_leaveid();
                        cancelremarks=response.body().getResult().get(0).getResult().getRow().get(i).getCancelremarks();
                        map = new HashMap<String, String>();
                        map.put("name",uname);
                        map.put("fromdate",fromdate);
                        map.put("city",todate);
                        map.put("address",status);
                        map.put("pincode",employeeid);
                        map.put("la_leaveid",la_leaveid);
                        map.put("cancelremarks",cancelremarks);
                        arraylist.add(map);
                    }
                    adapter = new CancellingRequestAdapterAdmin(getActivity(), arraylist,uname,pswd);
                    cusappoint.setLayoutManager(new LinearLayoutManager(getActivity()));
                    cusappoint.setAdapter(adapter);
                   // adapter.notifyDataSetChanged();
                    loading.dismiss();
                } catch (Exception e)
                { e.printStackTrace(); }

            }
            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
             //   adapter.notifyDataSetChanged();//
                cusappoint.setAdapter(null);
                norecords.setVisibility(View.VISIBLE);
            }
        });
    }
}