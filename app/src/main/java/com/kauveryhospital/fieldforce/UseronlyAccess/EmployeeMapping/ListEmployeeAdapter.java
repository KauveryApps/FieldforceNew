package com.kauveryhospital.fieldforce.UseronlyAccess.EmployeeMapping;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UserAdmin.checkoutadmin.CheckoutItem;

import java.util.ArrayList;

public class ListEmployeeAdapter extends RecyclerView.Adapter<ListEmployeeAdapter.ExampleViewHolder> {
    private ArrayList<EmployeeItem> mExampleList;
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2,mTextView3,mTextView4,mTextView5;
        public ExampleViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.name);
            mTextView2 = itemView.findViewById(R.id.empid);
            mTextView3 =itemView.findViewById(R.id.contype);
            mTextView4=itemView.findViewById(R.id.conconttype);
            mTextView5=itemView.findViewById(R.id.conaddress);
        }}
    public ListEmployeeAdapter(ArrayList<EmployeeItem> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.employeecont, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        EmployeeItem currentItem = mExampleList.get(position);
        holder.mTextView1.setText(currentItem.getMemployee());
        holder.mTextView2.setText(currentItem.getMusrname());
        holder.mTextView3.setText(currentItem.getMcont_type());
        holder.mTextView4.setText(currentItem.getMcont_corp_name());
        holder.mTextView5.setText(currentItem.getMaddress());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
    public void filterList(ArrayList<EmployeeItem> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}