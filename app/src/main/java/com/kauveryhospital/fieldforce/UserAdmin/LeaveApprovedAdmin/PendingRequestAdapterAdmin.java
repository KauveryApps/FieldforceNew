package com.kauveryhospital.fieldforce.UserAdmin.LeaveApprovedAdmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.APIInterfaceadmin;
import com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin.Resultadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PendingRequestAdapterAdmin extends RecyclerView.Adapter<PendingRequestAdapterAdmin.MyPendingHolder> implements View.OnClickListener {
    Context context;
    EditText input;
    APIInterfaceadmin apiInterface;
    String message, inputval, statusresult;
    List<LeaveItem> lists = new ArrayList<>();
    int position;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<>();
    String uname, pswd, name, address, city, fromdate, la_leaveids, pincode, cancelremarks;
    List<Resultadmin> list = new ArrayList<Resultadmin>();
    ArrayAdapter<String> dataAdapter;



    public PendingRequestAdapterAdmin(FragmentActivity context, ArrayList<LeaveItem> mExampleList) {
        this.context=context;
        this.lists=mExampleList;
    }



    @NonNull
    @Override
    public PendingRequestAdapterAdmin.MyPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.pending_activity_tasksadmin, parent, false);
        v.setOnClickListener(this);
        PendingRequestAdapterAdmin.MyPendingHolder myHolder = new PendingRequestAdapterAdmin.MyPendingHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PendingRequestAdapterAdmin.MyPendingHolder holder, final int position) {
        final LeaveItem fruits = lists.get(position);
//        resultp = data.get(position);
        holder.name.setText(fruits.getMnames());
        holder.fromdate.setText(fruits.getMtodate());
        holder.address.setText(fruits.getMstatus());
       holder.city.setText(fruits.getMfromdate());
        holder.pincode.setText(fruits.getMemployeeid());
        holder.ones.setText(fruits.getMla_leaveid());
       // String one = resultp.get("func");
        //Log.d(TAG, "onBindViewHolder: " + one);
        holder.checkBox_select.setChecked(fruits.isSelected);
        holder.checkBox_select.setTag(lists.get(position));
        holder.checkBox_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LeaveItem fruits1 = (LeaveItem) holder.checkBox_select.getTag();

                fruits1.setSelected(holder.checkBox_select.isChecked());

                lists.get(position).setSelected(holder.checkBox_select.isChecked());
            }
        });
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class MyPendingHolder extends RecyclerView.ViewHolder {
        TextView name, address, city, fromdate, pincode, ones, texts;
        Button reasoncancelbutt;
        CheckBox checkBox_select;

        public MyPendingHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            fromdate = itemView.findViewById(R.id.state);
            pincode = itemView.findViewById(R.id.pincode);
            ones = itemView.findViewById(R.id.ones);
            checkBox_select = itemView.findViewById(R.id.checkBox_select);


        }
    }

    public List<LeaveItem> getFruitsList(){
        return lists;
    }
    private Context getContext() {
        return context;
    }
}





