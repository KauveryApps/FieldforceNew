package com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kauveryhospital.fieldforce.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListTravelAdapter extends RecyclerView.Adapter<ListTravelAdapter.MyPendingHolder> implements View.OnClickListener {
    Context context;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<>();
    ArrayAdapter<String> dataAdapter;
int position;
    public ListTravelAdapter(ListTravelExpensesActivity context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }



    //    private final OnClickListener mOnClickListener = new MyOnClickListener();
    @NonNull
    @Override
    public MyPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.travel_activity_tasks,parent,false);
        v.setOnClickListener(this);
        MyPendingHolder myHolder=new MyPendingHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPendingHolder holder, final int position)
    {
        resultp = data.get(position);
        holder.name.setText(resultp.get("empname"));
        holder.state.setText(resultp.get("visitdate"));
        holder.address.setText(resultp.get("address"));
        holder.city.setText(resultp.get("purpose"));
      holder.buttononereason.setOnClickListener(new View.OnClickListener()
      {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context, TravelExpActivity.class);
                intent.putExtra("empname", data.get(position).get("empname"));
                intent.putExtra("visitdate", data.get(position).get("visitdate"));
                intent.putExtra("address", data.get(position).get("address"));
                intent.putExtra("customer", data.get(position).get("purpose"));
                intent.putExtra("checkin",data.get(position).get("checkin"));
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {

    }
    public class MyPendingHolder extends RecyclerView.ViewHolder {
        TextView name,address,city,state,pincode;
        Button buttononereason;
        public MyPendingHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address= itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            state =  itemView.findViewById(R.id.state);
            pincode=itemView.findViewById(R.id.pincode);
            buttononereason=itemView.findViewById(R.id.buttononereason);
        }
    }

}





