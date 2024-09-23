package com.kauveryhospital.fieldforce.UserAdmin.unplanvisitAdmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.UserAdmin.corporateadmin.CorporateItem;

import java.util.ArrayList;

public class ListUnplanAdminAdapter extends RecyclerView.Adapter<ListUnplanAdminAdapter.ExampleViewHolder> {
    private ArrayList<UnplanItem> mExampleList;
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
    public ListUnplanAdminAdapter(ArrayList<UnplanItem> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.unplan_activity_tasksadmin, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        UnplanItem currentItem = mExampleList.get(position);

        holder.mTextView1.setText(currentItem.getMemployee()+" "+currentItem.getMnickname());
        holder.mTextView2.setText(currentItem.getMaddress());
        holder.mTextView3.setText(currentItem.getMvisitdate());
        holder.mTextView4.setText(currentItem.getMcheckin());
        holder.mTextView5.setText(currentItem.getMcheckout());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
    public void filterList(ArrayList<UnplanItem> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}