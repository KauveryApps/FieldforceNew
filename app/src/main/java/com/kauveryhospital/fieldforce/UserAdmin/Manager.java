package com.kauveryhospital.fieldforce.UserAdmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UserAdmin.LeaveApprovedAdmin.LeaveTabActivity;
import com.kauveryhospital.fieldforce.UserAdmin.Mapdata.EmployeeContactMapping;
import com.kauveryhospital.fieldforce.UserAdmin.checkoutadmin.CheckoutActivityadmin;
import com.kauveryhospital.fieldforce.UserAdmin.contactadmin.ContactActivityadmin;
import com.kauveryhospital.fieldforce.UserAdmin.corporateadmin.CorporateActivityadmin;
import com.kauveryhospital.fieldforce.UserAdmin.unplanvisitAdmin.unplannedvisit;


public class Manager extends Fragment {
    public static  final String PREFS_NAME="loginpref";
    String uname,pswd;
    ImageView loggedout;
    CardView corporate,contact,worktracker,leaverequest,selfplanned,checkout,expenseapproved,employeemapping,ohctrack;
    static Manager instance;

    public Manager() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_manager, container, false);
        final SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        uname=settings.getString("username","");
        pswd=settings.getString("password","");
        loggedout=root.findViewById(R.id.loggedout);
        selfplanned=root.findViewById(R.id.selfplanned);
        corporate=root.findViewById(R.id.corporate);
        contact=root.findViewById(R.id.contact);
        worktracker=root.findViewById(R.id.worktracker);
        leaverequest=root.findViewById(R.id.leaverequest);
        checkout=root.findViewById(R.id.checkout);
        expenseapproved=root.findViewById(R.id.expenseapproved);
        ohctrack=root.findViewById(R.id.ohctrack);

        corporate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CorporateActivityadmin.class);
                startActivity(intent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ContactActivityadmin.class);
                startActivity(intent);
            }
        });
        worktracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MapsActivityAdmin.class);
                startActivity(intent);
            }
        });
        leaverequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LeaveTabActivity.class);
                startActivity(intent);
            }
        });
        selfplanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), unplannedvisit.class);
                startActivity(intent);
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CheckoutActivityadmin.class);
                startActivity(intent);
            }
        });
        expenseapproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //Intent intent=new Intent(getActivity(), CheckoutActivityadmin.class);
               // startActivity(intent);
            }
        });
        ohctrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent intent=new Intent(getActivity(), MapsActivityOHC.class);
                 startActivity(intent);
            }
        });
        return root;
    }
    public static Manager getInstance() {
        return instance;
    }
}