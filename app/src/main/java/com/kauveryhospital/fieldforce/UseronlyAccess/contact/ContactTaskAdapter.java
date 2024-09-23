package com.kauveryhospital.fieldforce.UseronlyAccess.contact;

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

public class ContactTaskAdapter extends RecyclerView.Adapter<ContactTaskAdapter.MyPendingHolder> implements View.OnClickListener {
    Context context;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<>();
    ArrayAdapter<String> dataAdapter;
    public ContactTaskAdapter(ListContactActivity context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }
    //private final OnClickListener mOnClickListener = new MyOnClickListener();//
    @NonNull
    @Override
    public ContactTaskAdapter.MyPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.contact_activity_tasks,parent,false);
        v.setOnClickListener(this);
        ContactTaskAdapter.MyPendingHolder myHolder=new ContactTaskAdapter.MyPendingHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactTaskAdapter.MyPendingHolder holder, final int position) {
        resultp = data.get(position);

        holder.contype.setText(resultp.get("contype"));
        holder.salutation.setText(resultp.get("salutation"));
        holder.contactname.setText(resultp.get("contactname"));
        holder.specialization.setText(resultp.get("specialization"));
        holder.portfolio.setText(resultp.get("portfolio"));
        holder.address.setText(resultp.get("address"));
        holder.city.setText(resultp.get("city"));
        holder.state.setText(resultp.get("state"));
        holder.phonenum.setText(resultp.get("phone"));


//         holder.statuss.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 Intent intent=new Intent(context, UpdateActivity.class);
//                 intent.putExtra("address", data.get(position).get("address"));
//                 intent.putExtra("city", data.get(position).get("city"));
//                 intent.putExtra("area", data.get(position).get("area"));
//                 intent.putExtra("pincode", data.get(position).get("pincode"));
//                 intent.putExtra("active", data.get(position).get("active"));
//                 intent.putExtra("state", data.get(position).get("state"));
//                 intent.putExtra("contype", data.get(position).get("contype"));
//                 intent.putExtra("salutation", data.get(position).get("salutation"));
//                 intent.putExtra("contactname", data.get(position).get("contactname"));
//                 intent.putExtra("corporate", data.get(position).get("corporate"));
//                 intent.putExtra("ambulance", data.get(position).get("ambulance"));
//                 intent.putExtra("specialization", data.get(position).get("specialization"));
//                 intent.putExtra("portfolio", data.get(position).get("portfolio"));
//                 intent.putExtra("phone", data.get(position).get("phone"));
//                 context.startActivity(intent);
//             }
//         });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class MyPendingHolder extends RecyclerView.ViewHolder {

        TextView contype,salutation,contactname,specialization,portfolio,address,city,state,phonenum;

        public MyPendingHolder(@NonNull View itemView) {
            super(itemView);
            contype =itemView.findViewById(R.id.contype);
            salutation=itemView.findViewById(R.id.salutation);
            contactname =itemView.findViewById(R.id.contactname);
            specialization = itemView.findViewById(R.id.specialization);
            portfolio= itemView.findViewById(R.id.portfolio);
            address=itemView.findViewById(R.id.address);
            city=itemView.findViewById(R.id.city);
            state=itemView.findViewById(R.id.state);
            phonenum=itemView.findViewById(R.id.phonenum);
        }
    }
}
