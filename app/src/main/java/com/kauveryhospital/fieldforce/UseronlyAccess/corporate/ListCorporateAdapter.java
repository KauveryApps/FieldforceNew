package com.kauveryhospital.fieldforce.UseronlyAccess.corporate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kauveryhospital.fieldforce.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListCorporateAdapter extends RecyclerView.Adapter<ListCorporateAdapter.MyPendingHolder> implements View.OnClickListener {
    Context context;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<>();
    ArrayAdapter<String> dataAdapter;
    public ListCorporateAdapter(ListCorporateActivity context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }
    //    private final OnClickListener mOnClickListener = new MyOnClickListener();
    @NonNull
    @Override
    public MyPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.corporate_activity_tasks,parent,false);
        v.setOnClickListener(this);
        MyPendingHolder myHolder=new MyPendingHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPendingHolder holder, int position)
    {
        resultp = data.get(position);
        holder.name.setText(resultp.get("name"));
        holder.state.setText(resultp.get("state"));
        holder.address.setText(resultp.get("address"));
        holder.city.setText(resultp.get("city"));
        holder.pincode.setText(resultp.get("pincode"));
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
        public MyPendingHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address= itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            state =  itemView.findViewById(R.id.state);
            pincode=itemView.findViewById(R.id.pincode);
        }
    }
}





