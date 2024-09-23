package com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdatas.APIClient;
import com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdatas.APIInterface;
import com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdatas.Example;
import com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdatas.Result;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CancelRequestAdapter extends RecyclerView.Adapter<CancelRequestAdapter.MyPendingHolder> implements View.OnClickListener {
    Context context;
    EditText input;
    APIInterface apiInterface;
    String message,inputval;
    int position;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<>();
    String uname, pswd,name,address,city,state,la_leaveids,pincode;
    List<Result> list = new ArrayList<Result>();
    ArrayAdapter<String> dataAdapter;

    public CancelRequestAdapter(CancelRequestActivity context, ArrayList<HashMap<String, String>> arraylist, String uname, String pswd) {
        this.context = context;
        data = arraylist;
        this.uname = uname;
        this.pswd = pswd;

    }


    //    private final OnClickListener mOnClickListener = new MyOnClickListener();
    @NonNull
    @Override
    public MyPendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cancel_activity_tasks, parent, false);
        v.setOnClickListener(this);
        MyPendingHolder myHolder = new MyPendingHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPendingHolder holder, final int position) {
        resultp = data.get(position);
        holder.name.setText(resultp.get("name"));
        holder.state.setText(resultp.get("city"));
        holder.address.setText(resultp.get("address"));
        holder.city.setText(resultp.get("state"));
        holder.pincode.setText(resultp.get("pincode"));
        holder.ones.setText(resultp.get("la_leaveid"));

        holder.reasoncancelbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name= data.get(position).get("name");
                state=data.get(position).get("state");
                address=data.get(position).get("address");
                city=data.get(position).get("city");
                pincode=data.get(position).get("pincode");
                la_leaveids=data.get(position).get("la_leaveid");

                withEditText(v);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View view) {

    }

    public class MyPendingHolder extends RecyclerView.ViewHolder {
        TextView name, address, city, state, pincode,ones;
        Button reasoncancelbutt;

        public MyPendingHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            city = itemView.findViewById(R.id.city);
            state = itemView.findViewById(R.id.state);
            pincode = itemView.findViewById(R.id.pincode);
            ones=itemView.findViewById(R.id.ones);
            reasoncancelbutt = itemView.findViewById(R.id.reasoncancelbutt);
        }
    }

    public void withEditText(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Reason for Cancelling");
        input = new EditText(getContext());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                inputval=input.getText().toString();
                if (!inputval.isEmpty()) {

                    try {
                        postdata();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Reason cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.show();
    }

    private Context getContext() {
        return context;
    }

    private void postdata() throws JSONException {
        final ProgressDialog loading = new ProgressDialog(context);
        loading.setMessage("Loading...");
        loading.setCancelable(false);
        loading.show();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", uname);
        jsonObject5.addProperty("password", pswd);
        jsonObject5.addProperty("s", " ");
        jsonObject5.addProperty("sql", "update la_leave SET status='cancel', cancelremarks='" + inputval + "' where la_leaveid='" + la_leaveids + "'");
        array.put(jsonObject5);
        try {

            jsonObject6.add("getchoices", jsonObject5);
            // array.put(jsonObject6);
            JsonArray array1 = new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters", array1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<Example> call4 = apiInterface.getResult(jsonObject7);
        call4.enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                loading.dismiss();


                if (response.body().getResult().get(0).getResult().getStatus() != null)
                {
                    message = response.body().getResult().get(0).getResult().getStatus();
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, TabbedActivity.class);
                    context.startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

}





