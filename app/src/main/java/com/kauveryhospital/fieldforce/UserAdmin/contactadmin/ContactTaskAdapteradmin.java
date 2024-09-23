package com.kauveryhospital.fieldforce.UserAdmin.contactadmin;

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
import com.kauveryhospital.fieldforce.UserAdmin.corporateadmin.CorporateItem;
import com.kauveryhospital.fieldforce.UserAdmin.unplanvisitAdmin.UnplanItem;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactTaskAdapteradmin extends RecyclerView.Adapter<ContactTaskAdapteradmin.MyPendingHolder> implements View.OnClickListener {
    Context context;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<>();
    private ArrayList<ContactItem> mExampleList;
    ArrayAdapter<String> dataAdapter;
    int position;
    public ContactTaskAdapteradmin(ContactActivityadmin context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    public ContactTaskAdapteradmin(ArrayList<ContactItem> exampleList) {
        mExampleList = exampleList;
    }


    //    private final OnClickListener mOnClickListener = new MyOnClickListener();//
    @NonNull
    @Override
    public ContactTaskAdapteradmin.MyPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.contact_activity_tasksadmin,parent,false);
        v.setOnClickListener(this);
        ContactTaskAdapteradmin.MyPendingHolder myHolder=new ContactTaskAdapteradmin.MyPendingHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactTaskAdapteradmin.MyPendingHolder holder, final int position) {

        final ContactItem contactItem = mExampleList.get(position);

    //    resultp = data.get(position);

        holder.contype.setText(contactItem.getMcontype());
        holder.salutation.setText(contactItem.getMspecialization());
        holder.contactname.setText(contactItem.getMcontactname());
        holder.specialization.setText(contactItem.getMspecialization());
        holder.portfolio.setText(contactItem.getMportfolio());
        holder.address.setText(contactItem.getMaddress());
        holder.city.setText(contactItem.getMcity_name());
        holder.state.setText(contactItem.getMstate_name());
        holder.phonenum.setText(contactItem.getMphone());

         holder.updatecontact.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent intent=new Intent(context, UpdateActivityadmin.class);
                 intent.putExtra("address", contactItem.getMaddress());
                 intent.putExtra("city", contactItem.getMcity_name());
                 intent.putExtra("area", contactItem.getMarea());
                 intent.putExtra("pincode", contactItem.getMpincode());
                 intent.putExtra("state_name", contactItem.getMstate_name());
                 intent.putExtra("contype", contactItem.getMcontype());
                 intent.putExtra("salutation", contactItem.getMsalutation());
                 intent.putExtra("contactname", contactItem.getMcontactname());
                 intent.putExtra("corporate", contactItem.getMcorporate());
                 intent.putExtra("ambulance", contactItem.getMambulance());
                 intent.putExtra("specialization", contactItem.getMspecialization());
                 intent.putExtra("portfolio", contactItem.getMportfolio());
                 intent.putExtra("phone", contactItem.getMphone());
                 intent.putExtra("cont_corpid",contactItem.getMcont_corpid());
                 intent.putExtra("stateid",contactItem.getMstateid());
                 intent.putExtra("cityid",contactItem.getMcityid());

                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class MyPendingHolder extends RecyclerView.ViewHolder {

        TextView   contype,salutation,contactname,specialization,portfolio,address,city,state,phonenum;
        Button updatecontact;
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
            updatecontact=itemView.findViewById(R.id.updatecontact);
        }
    }
    public void filterList(ArrayList<ContactItem> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}
