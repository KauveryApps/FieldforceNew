package com.kauveryhospital.fieldforce.workstartserviceuseronly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kauveryhospital.fieldforce.R;

import java.util.List;

public class NameAdapter extends ArrayAdapter<Name> {

    //storing all the names in the list
    private List<Name> names;

    //context object
    private Context context;
TextView txtvcontype,txtvchkintime,txtvcustomer,txtvvisitpurpose;
    //constructor
    public NameAdapter(Context context, int resource, List<Name> names) {
        super(context, resource, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        View listViewItem = inflater.inflate(R.layout.names, null, true);
         txtvcontype = (TextView) listViewItem.findViewById(R.id.txtvcontype);
        txtvchkintime = (TextView) listViewItem.findViewById(R.id.txtvchkintime);
        txtvcustomer=(TextView)listViewItem.findViewById(R.id.txtvcustomer);
        txtvvisitpurpose=(TextView)listViewItem.findViewById(R.id.txtvvisitpurpose);
        ImageView imageViewStatus = (ImageView) listViewItem.findViewById(R.id.imageViewStatus);

        //getting the current name
        Name name = names.get(position);

        //setting the name to textview
        txtvcontype.setText(name.getName());
       txtvchkintime.setText(name.getCheckintime());
       txtvcustomer.setText(name.getCustomer());
       txtvvisitpurpose.setText(name.getVisitpurpose());

        //if the synced status is 0 displaying
        //queued icon
        //else displaying synced icon

        if (name.getStatus() == 0)
            imageViewStatus.setBackgroundResource(R.drawable.closes);
        else
            imageViewStatus.setBackgroundResource(R.drawable.synced);
            return listViewItem;
    }
}
