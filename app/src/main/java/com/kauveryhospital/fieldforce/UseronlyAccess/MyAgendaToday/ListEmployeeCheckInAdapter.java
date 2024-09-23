package com.kauveryhospital.fieldforce.UseronlyAccess.MyAgendaToday;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.APIClientOhc;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.APIInterfaceOhc;
import com.kauveryhospital.fieldforce.OHCOnly.getdata.GetcheckoutOhc;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.APIInterface;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListEmployeeCheckInAdapter extends RecyclerView.Adapter<ListEmployeeCheckInAdapter.ExampleViewHolder> {
    private ArrayList<CheckInItem> mExampleList;
    String message,mname,mpswd,currentDateString2,currentDateTimeString1,mlatitude,mlongitude;
    CheckInItem currentItem;
    Context mcontext;
    int position;
    APIInterfaceOhc apiInterfaceplan;
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        Button btnchkin,btnchkout;
        LinearLayout chkoutlayout;
        public TextView mTextView2,mTextView3,mTextView4,mTextView5,mTextView6,mTextView7,mTextView8;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            chkoutlayout=itemView.findViewById(R.id.chkoutlayout);
            mTextView2 = itemView.findViewById(R.id.tcusname);
            mTextView3 =itemView.findViewById(R.id.tcorporate);
            mTextView4=itemView.findViewById(R.id.taddress);
            mTextView5=itemView.findViewById(R.id.tvisitdate);
            mTextView6=itemView.findViewById(R.id.tpriority);

            btnchkin=itemView.findViewById(R.id.btnchkin);
            btnchkout=itemView.findViewById(R.id.btnchkout);
        }}
    public ListEmployeeCheckInAdapter(PlannedCheckInActivity context, ArrayList<CheckInItem> exampleList, String uname, String pswd, String latitude, String longitude) {
        mExampleList = exampleList;
        mcontext=context;
        mname=uname;
        mpswd=pswd;
        mlatitude=latitude;
        mlongitude=longitude;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plannedcheckincont, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        Date todaydate2= Calendar.getInstance().getTime();
        Date todaydate1=Calendar.getInstance().getTime();
        SimpleDateFormat formatter2=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
        currentDateString2=formatter2.format(todaydate2);
        currentDateTimeString1=formatter1.format(todaydate1);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, final int position) {
       currentItem = mExampleList.get(position);

        holder.mTextView2.setText(currentItem.getMcontact_type());
        holder.mTextView3.setText(currentItem.getMcorporate());
        holder.mTextView4.setText(currentItem.getMportfolio());
        holder.mTextView5.setText(currentItem.getMvisitdate());
        holder.mTextView6.setText(currentItem.getMstatus_cl());
        if(currentItem.getMstatus_cl().equals("Pending")){
         holder.btnchkin.setVisibility(View.VISIBLE);
        }
        if(currentItem.getMstatus_cl().equals("Checked In")){
            holder.btnchkout.setVisibility(View.VISIBLE);
        }
        if(currentItem.getMstatus_cl().equals("Visited")){

            holder.btnchkin.setVisibility(View.INVISIBLE);
            holder.btnchkin.setVisibility(View.INVISIBLE);
            holder.chkoutlayout.setVisibility(View.GONE);
        }
        holder.btnchkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, PlannedvisitchkinActivity.class);
                intent.putExtra("cont_corpid", mExampleList.get(position).getMcont_corpid());
                mcontext.startActivity(intent);
            }
        });
        holder.btnchkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, PlannedvisitchkoutActivity.class);
                mcontext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
    public void filterList(ArrayList<CheckInItem> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }

    private void Checkingdatain1()
    {
        final ProgressDialog loading = new ProgressDialog(mcontext);
        loading.setMessage("Loading ...");
        loading.show();
        apiInterfaceplan = APIClientOhc.getClient().create(APIInterfaceOhc.class);
        JsonObject jsonObject5=new JsonObject();
        JsonObject jsonObject6=new JsonObject();
        JsonObject jsonObject7=new JsonObject();
        JSONArray array=new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", mname);
        jsonObject5.addProperty("password", mpswd);

        jsonObject5.addProperty("s","");
        jsonObject5.addProperty("sql","select count(*)cnt,work_startid from (select row_number() over ( PARTITION by USERNAME order by CREATEDON desc ) sno,WORK_STARTid,CREATEDON,STATUS,STATUSFLG from  WORK_START  where USERNAME='"+mname+"') where sno=1 and STATUS=1 and STATUSFLG=1 group by work_startid");

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


                try {
                    if(response.body().getResult().get(0).getResult().getRow().get(0).getCnt().equals("0"))
                    {
                      //  btn_checkin.setVisibility(View.VISIBLE);
                      //  postdatastate1();
                    }
                    else
                    {
                       // btn_checkout.setVisibility(View.VISIBLE);
                       // postdatastate1();
                    }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetcheckoutOhc> call, Throwable t) {
                loading.dismiss();
               // btn_checkin.setVisibility(View.VISIBLE);

            }
        });
    }

}