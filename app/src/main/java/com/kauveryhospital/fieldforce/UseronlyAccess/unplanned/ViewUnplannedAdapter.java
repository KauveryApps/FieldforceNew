package com.kauveryhospital.fieldforce.UseronlyAccess.unplanned;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UseronlyAccess.MyAgendaToday.CheckInItem;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewUnplannedAdapter extends RecyclerView.Adapter<ViewUnplannedAdapter.MyPendingHolder> implements View.OnClickListener {
    Context context;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<>();
    ArrayAdapter<String> dataAdapter;
    int position;
    private ArrayList<UnplannedvisitItem> mExampleList;
    UnplannedvisitItem currentItem;
    public ViewUnplannedAdapter(ViewUnplannedvisits context, ArrayList<UnplannedvisitItem> exampleList) {
        this.context = context;

        mExampleList=exampleList;
    }
    @NonNull
    @Override
    public MyPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.unplannview,parent,false);
        v.setOnClickListener(this);
        MyPendingHolder myHolder=new MyPendingHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPendingHolder holder, final int position)
    {
        currentItem=mExampleList.get(position);
     //   resultp = data.get(position);
        holder.name.setText(currentItem.getMempname());
        holder.state.setText(currentItem.getMcheckin());
        holder.address.setText(currentItem.getMaddress());
        holder.city.setText(currentItem.getMcustomer());
        holder.pincode.setText(currentItem.getMcheckouttime());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
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





