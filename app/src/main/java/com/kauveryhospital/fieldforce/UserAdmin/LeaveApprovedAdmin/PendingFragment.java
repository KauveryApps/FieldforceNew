package com.kauveryhospital.fieldforce.UserAdmin.LeaveApprovedAdmin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendingFragment extends Fragment {

    RecyclerView cusappoint;
    PendingRequestAdapterAdmin adapter;
    HashMap<String, String> map;
    String datas;
    APIInterfaceadmin apiInterface;
    String message, inputval, statusresult;
    boolean func;
    ArrayList<LeaveItem> mExampleList;
    TextView norecords,textv3;
    CheckBox chk;
    Button btn_select_fruit;
    LeaveItem fruits2;
    List<String> PartNameState;
    List<String> PartIdState;
    public static final String PREFS_NAME = "loginpref";
    int j = 0;
    SwipeRefreshLayout swipeLayout;
    List<Resultadmin> list = new ArrayList<>();
    ImageView backarrow;
    Button btn_select;
    ArrayList<HashMap<String, String>> arraylist;
    String jsonString, pswd;
    List<LeaveItem> list_fruit;
    String uname, fromdate, todate, status, employeeid, la_leaveid, cancelremarks, currentDateTimeString, unames;

    private NetworkChangeReceiver networkChangeReceiver;

    public PendingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blank_test, container, false);
        chk = root.findViewById(R.id.chkAll);
        textv3=root.findViewById(R.id.textv3);
        btn_select = root.findViewById(R.id.btn_select_fruit);
        swipeLayout = root.findViewById(R.id.swipe_container);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        currentDateTimeString = formatter.format(todayDate);
        Log.d("TAGy", "onCreate: " + currentDateTimeString);
        backarrow = root.findViewById(R.id.backarrow);
        norecords = root.findViewById(R.id.norecords);
        SharedPreferences set = getActivity().getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");

        cusappoint = root.findViewById(R.id.recyclerView);
        postdatastate();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        if (Build.VERSION.SDK_INT >= 26) {
//            ft.setReorderingAllowed(false);
//        }
        //  ft.detach(this).attach(this).commit();

        refreshmethod();

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("");
                builder.setIcon(R.mipmap.kauveryslogos);
                builder.setMessage("Are You Approved the Leave?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                statusresult = "Approved";
                                list_fruit = adapter.getFruitsList();
                                for (int i = 0; i < list_fruit.size(); i++) {

                                    fruits2 = list_fruit.get(i);

                                    if (fruits2.isSelected() == true) {
                                        try {
                                            datas = fruits2.getMla_leaveid();
                                            postdata(datas, statusresult);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                statusresult = "Rejected";
                                list_fruit = adapter.getFruitsList();
                                for (int i = 0; i < list_fruit.size(); i++) {

                                    fruits2 = list_fruit.get(i);

                                    if (fruits2.isSelected() == true) {
                                        try {
                                            datas = fruits2.getMla_leaveid();
                                            postdata(datas, statusresult);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk.isChecked()) {
                    func = true;
                    swipeLayout.setRefreshing(false);
                    postdatastate();
                } else {
                    func = false;
                    swipeLayout.setRefreshing(false);
                    postdatastate();

                }
            }
        });
        return root;
    }

    public void refreshmethod() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);
                postdatastate();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        swipeLayout.setRefreshing(false);
    }

    private void postdatastate() {

        cusappoint.setAdapter(null);
        norecords.setVisibility(View.INVISIBLE);
        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Loading...");
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
        jsonObject5.addProperty("sql", "select uname,reasonforleave,fromdate,todate,status,la_leaveid,cancelremarks from la_leave where  status='pending' and trunc(createdon)='" + currentDateTimeString + "'");
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

                //  arraylist = new ArrayList<HashMap<String, String>>();
                mExampleList = new ArrayList<>();

                try {
                    for (int i = 0; i < response.body().getResult().get(j).getResult().getRow().size(); i++) {
                        unames = response.body().getResult().get(0).getResult().getRow().get(i).getuname();
                        fromdate = response.body().getResult().get(0).getResult().getRow().get(i).getFromdate();
                        todate = response.body().getResult().get(0).getResult().getRow().get(i).getTodate();
                        status = response.body().getResult().get(0).getResult().getRow().get(i).getStatus();
                        employeeid = response.body().getResult().get(0).getResult().getRow().get(i).getReasonforleave();
                        la_leaveid = response.body().getResult().get(0).getResult().getRow().get(i).getLa_leaveid();
                        cancelremarks = response.body().getResult().get(0).getResult().getRow().get(i).getCancelremarks();
//                        map = new HashMap<String,String>();
//                        map.put("name", uname);
//                        map.put("fromdate", fromdate);
//                        map.put("city", todate);
//                        map.put("address", status);
//                        map.put("pincode", employeeid);
//                        map.put("la_leaveid", la_leaveid);
//                        map.put("cancelremarks", cancelremarks);
//                        map.put("func", String.valueOf(func));
//                        arraylist.add(map);
                        mExampleList.add(new LeaveItem(unames, fromdate, todate, status, employeeid, la_leaveid, cancelremarks, func, uname, pswd));
                    }
                    adapter = new PendingRequestAdapterAdmin(getActivity(), mExampleList);
                    cusappoint.setLayoutManager(new LinearLayoutManager(getActivity()));
                    cusappoint.setAdapter(adapter);
                    loading.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                cusappoint.setAdapter(null);
                norecords.setVisibility(View.VISIBLE);
                chk.setVisibility(View.INVISIBLE);
                btn_select.setVisibility(View.INVISIBLE);
                textv3.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void postdata(String datas, String statusresult) throws JSONException {
        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Loading...");
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
        jsonObject5.addProperty("sql", "update la_leave SET status='" + statusresult + "' where la_leaveid='" + datas + "'");
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

                if (response.body().getResult().get(0).getResult().getStatus() != null) {
                    message = response.body().getResult().get(0).getResult().getStatus();
                    Toast.makeText(getActivity(), PendingFragment.this.statusresult + " " + message, Toast.LENGTH_SHORT).show();
                    refreshmethod();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Exampleadmin> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Server Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
}