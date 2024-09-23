package com.kauveryhospital.fieldforce.UseronlyAccess.MyAgendaToday;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UseronlyAccess.unplanned.ViewUnplannedvisits;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewplannedAdapter extends RecyclerView.Adapter<ViewplannedAdapter.MyPendingHolder> implements View.OnClickListener {
    Context context;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<>();
    ArrayAdapter<String> dataAdapter;
    int position;
    public ViewplannedAdapter(ViewPlannedVisits context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }
    @NonNull
    @Override
    public MyPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.plannview,parent,false);
        v.setOnClickListener(this);
        MyPendingHolder myHolder=new MyPendingHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPendingHolder holder, final int position)
    {
        resultp = data.get(position);
        holder.cusname.setText(resultp.get("customername"));
        holder.crpname.setText(resultp.get("corporate"));
        holder.status.setText(resultp.get("status"));
        holder.checkintime.setText(resultp.get("checkintime"));
        holder.checkouttime.setText(resultp.get("checkouttime"));
        holder.joint1.setText(resultp.get("joint1"));
        holder.joint2.setText(resultp.get("joint2"));
        holder.joint3.setText(resultp.get("joint3"));
        if(resultp.get("status").equals("2")){
            holder.status.setText("Visited");
        }
        if(resultp.get("status").equals("1")){
            holder.status.setText("Checked In");
        }
        if(resultp.get("status").equals("0")){
            holder.status.setText("Pending");
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {

    }
    public class MyPendingHolder extends RecyclerView.ViewHolder {
        TextView cusname,crpname,status,checkouttime,checkintime,joint1,joint2,joint3;

        public MyPendingHolder(@NonNull View itemView)
        {
            super(itemView);
            cusname = itemView.findViewById(R.id.cusname);
            crpname= itemView.findViewById(R.id.crpname);
            status = itemView.findViewById(R.id.status);
            checkintime =  itemView.findViewById(R.id.checkintime);
            checkouttime=itemView.findViewById(R.id.checkouttime);
            joint1  =itemView.findViewById(R.id.joint1);
            joint2 =itemView.findViewById(R.id.joint2);
            joint3  =itemView.findViewById(R.id.joint3);
        }
    }

}





