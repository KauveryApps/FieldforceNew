package com.kauveryhospital.fieldforce.OHCOnly;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.ListTravelExpensesActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.TravelExpActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ListOhcAdapter extends RecyclerView.Adapter<ListOhcAdapter.MyPendingHolder> implements View.OnClickListener {
    Context context;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<>();
    ArrayAdapter<String> dataAdapter;
    int position;
    public ListOhcAdapter( FragmentActivity activity, ArrayList<HashMap<String, String>> arraylist) {
        this.context = activity;
        data = arraylist;
    }
    //    private final OnClickListener mOnClickListener = new MyOnClickListener();
    @NonNull
    @Override
    public MyPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.ohcstrtend,parent,false);
        v.setOnClickListener(this);
        MyPendingHolder myHolder=new MyPendingHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPendingHolder holder, final int position)
    {
        resultp = data.get(position);
        holder.name.setText(resultp.get("empname"));
        holder.checkint.setText(resultp.get("starttime"));
        holder.checkoutt.setText(resultp.get("endtime"));
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {

    }
    public class MyPendingHolder extends RecyclerView.ViewHolder {
        TextView name,checkint,checkoutt;
        Button buttononereason;
        public MyPendingHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            checkint = itemView.findViewById(R.id.checkint);
            checkoutt = itemView.findViewById(R.id.checkoutt);
        }
    }
}





                                    