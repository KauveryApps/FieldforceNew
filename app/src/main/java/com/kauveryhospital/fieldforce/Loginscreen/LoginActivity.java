package com.kauveryhospital.fieldforce.Loginscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.Loginscreen.getdata.APIClientimeiPlan;
import com.kauveryhospital.fieldforce.Loginscreen.getdata.APIInterfaceimeiPlan;
import com.kauveryhospital.fieldforce.Loginscreen.getdata.Getimei;
import com.kauveryhospital.fieldforce.NetworkChangeCallback;
import com.kauveryhospital.fieldforce.NetworkChangeReceiver;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.SharedPrefManager;
import com.kauveryhospital.fieldforce.TabbedActivity;
import com.kauveryhospital.fieldforce.User;


import org.json.JSONArray;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements NetworkChangeCallback {
    Button btnLogin;
    APIInterface apiInterface;
    TextView frgtpswd;
    boolean status;
    String myuniqueID;
    EditText etUserName, etpassword;
    APIInterfaceimeiPlan apiInterfaceplan;
    List<Result> list = new ArrayList<Result>();
    String username, usrname, password, MD5_Hash_String, message, is_status, is_ohc, sessions, nickname, location, imeino;
    public static final String PREFS_NAME = "loginpref";
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadIMEI1();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        frgtpswd = findViewById(R.id.frgtpswd);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, TabbedActivity.class));
        }
        btnLogin = findViewById(R.id.btnLogin);
        etUserName = findViewById(R.id.etUserName);
        etpassword = findViewById(R.id.etpassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected()) {
                    validation();
                } else {
                    Toast.makeText(getApplicationContext(), "check Internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        frgtpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Please Contact To Admin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validation() {
        username = etUserName.getText().toString();
        password = etpassword.getText().toString();
        MD5_Hash_String = md5(password);
        if (etUserName.getText().toString().trim().length() == 0) {
            etUserName.setError("Please enter username");
            etUserName.requestFocus();
            return;
        }
        if (etpassword.getText().toString().trim().length() == 0) {
            etpassword.setError("Please enter password");
            etpassword.requestFocus();
            return;
        }
        else
            user();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    public void onNetworkChanged(boolean status) {
        Log.e("MainActivity", "Status: " + status);
        if (status == false)
            Toast.makeText(LoginActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
    }

    public void user() {
        final ProgressDialog loading = new ProgressDialog(LoginActivity.this);
        loading.setMessage("Loading Login...");
        loading.setCancelable(false);
        loading.show();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        OkHttpClient httpClient = new OkHttpClient();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        JsonObject jsonParams = new JsonObject();
        jsonParams.addProperty("axpapp", "fieldforce");
        jsonParams.addProperty("username", username);
        jsonParams.addProperty("password", MD5_Hash_String);
        jsonParams.addProperty("other", "Chrome");
        array.put(jsonParams);
        try {
            //jsonParams.put("parameters",array.put(jsonParams).toString());
            jsonObject6.add("login", jsonParams);
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
                if (response.body().getResult().get(0).getError() != null) {
                    message = response.body().getResult().get(0).getError().getMsg();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    imeino = response.body().getResult().get(0).getResult().getImei_no();
                    usrname = response.body().getResult().get(0).getResult().getUSERNAME();
                    if (imeino.isEmpty()) {
                        loadIMEI();
                    } else if (myuniqueID.equals(imeino)) {
                        usrname = response.body().getResult().get(0).getResult().getUSERNAME();
                        message = response.body().getResult().get(0).getResult().getStatus();
                        is_status = response.body().getResult().get(0).getResult().getIs_admin();
                        sessions = response.body().getResult().get(0).getResult().getS();
                        is_ohc = response.body().getResult().get(0).getResult().getIs_ohc();
                        nickname = response.body().getResult().get(0).getResult().getNICKNAME();
                        location = response.body().getResult().get(0).getResult().getLocation();
                        User user = new User(usrname, MD5_Hash_String, is_status, sessions, is_ohc, nickname, location);
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("username", usrname);
                        editor.putString("password", MD5_Hash_String);
                        editor.putString("uaa", is_status);
                        editor.putString("session", sessions);
                        editor.putString("isohc", is_ohc);
                        editor.putString("location", location);
                        editor.putString("nickname", nickname);
                        editor.apply();
                        //starting the profile activity//
                        finish();
                        Toast.makeText(getApplicationContext(), "Login " + message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, TabbedActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Please login in to registered device", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("====", "Something gone wrong");
            }
        });
    }

    public String md5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }
    public void loadIMEI() {
        int myversion = Integer.valueOf(android.os.Build.VERSION.SDK);
        Log.d("TAG", "loadversion: " + myversion);
        if (myversion < 23) {
            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            myuniqueID = info.getMacAddress();
            if (myuniqueID == null) {
                TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                myuniqueID = mngr.getDeviceId();
                postimeinum(myuniqueID);

            }
        } else if (myversion > 23 && myversion < 29) {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            myuniqueID = mngr.getDeviceId();
            postimeinum(myuniqueID);
        } else {
            String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            myuniqueID = androidId;
            postimeinum(myuniqueID);
        }

    }

    public void loadIMEI1() {
        int myversion = Integer.valueOf(android.os.Build.VERSION.SDK);
        Log.d("TAG", "loadversion: " + myversion);
        if (myversion < 23) {
            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            myuniqueID = info.getMacAddress();
            if (myuniqueID == null) {

                TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                myuniqueID = mngr.getDeviceId();
            }
        } else if (myversion > 23 && myversion < 29) {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            myuniqueID = mngr.getDeviceId();
        } else {
            String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            myuniqueID = androidId;
        }
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(false);//
        return;
    }

    private void postimeinum(String myuniqueID) {
        final ProgressDialog loading = new ProgressDialog(LoginActivity.this);
        loading.setMessage("Checking Device...");
        loading.setCancelable(false);
        loading.show();
        apiInterfaceplan = APIClientimeiPlan.getClient().create(APIInterfaceimeiPlan.class);
        JsonObject jsonObject5 = new JsonObject();
        JsonObject jsonObject6 = new JsonObject();
        JsonObject jsonObject7 = new JsonObject();
        JSONArray array = new JSONArray();
        jsonObject5.addProperty("axpapp", "fieldforce");
        jsonObject5.addProperty("username", username);
        jsonObject5.addProperty("password", MD5_Hash_String);
        jsonObject5.addProperty("s", "");
        jsonObject5.addProperty("sql", "update axusers set imei_no='" + myuniqueID + "'  where username='" + usrname + "'");
        array.put(jsonObject5);
        try {
            jsonObject6.add("getchoices", jsonObject5);
            //array.put(jsonObject6);
            JsonArray array1 = new JsonArray();
            array1.add(jsonObject6);
            jsonObject7.add("_parameters", array1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Call<Getimei> call4 = apiInterfaceplan.getResult(jsonObject7);
        call4.enqueue(new Callback<Getimei>() {
            @Override
            public void onResponse(Call<Getimei> call, Response<Getimei> response) {
                loading.dismiss();
                try {
                    if (response.body().getResult().get(0).getResult().getStatus().contains("Success"))
                    {
                        startActivity(new Intent(LoginActivity.this, TabbedActivity.class));
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "server problem", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception ex)
                {
                        ex.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Getimei> call, Throwable t)
            {
                   loading.dismiss();
            }
        });
    }
}
