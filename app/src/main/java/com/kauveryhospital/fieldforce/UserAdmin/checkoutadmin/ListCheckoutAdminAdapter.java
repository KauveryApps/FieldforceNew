package com.kauveryhospital.fieldforce.UserAdmin.checkoutadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kauveryhospital.fieldforce.R;


import java.util.ArrayList;

public class ListCheckoutAdminAdapter extends RecyclerView.Adapter<ListCheckoutAdminAdapter.ExampleViewHolder> {
    private ArrayList<CheckoutItem> mExampleList;
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView1;
        public TextView mTextView2,mTextView3,mTextView4,mTextView5;
        public ExampleViewHolder(View itemView) {
            super(itemView);
          //  mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.name);
            mTextView2 = itemView.findViewById(R.id.address);
            mTextView3 =itemView.findViewById(R.id.city);
            mTextView4=itemView.findViewById(R.id.state);
            mTextView5=itemView.findViewById(R.id.pincode);
        }
    }
    public ListCheckoutAdminAdapter(ArrayList<CheckoutItem> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_activity_tasksadmin, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        CheckoutItem currentItem = mExampleList.get(position);
        holder.mTextView1.setText(currentItem.getMemployee()+" "+currentItem.getMnickname());
        holder.mTextView2.setText(currentItem.getMaddress());
        holder.mTextView3.setText(currentItem.getMcheckin());
        holder.mTextView4.setText(currentItem.getMcheckout());
        holder.mTextView5.setText(currentItem.getMremarks());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
    public void filterList(ArrayList<CheckoutItem> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}
