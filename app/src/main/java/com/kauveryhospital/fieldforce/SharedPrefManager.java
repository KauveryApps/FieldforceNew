package com.kauveryhospital.fieldforce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.kauveryhospital.fieldforce.Loginscreen.LoginActivity;


public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "loginpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
    private static final String KEY_PASSWORD="keypassword";
    private static final String KEY_PASS="keypass";
    private static final String KEY_PAS="keypas";
    private static SharedPrefManager mInstance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

         editor.putString(KEY_USERNAME, user.getName());
         editor.putString(KEY_PASSWORD,user.getPassword());
         editor.putString(KEY_EMAIL,user.getIs_status());
         editor.putString(KEY_GENDER,user.getSessions());
         editor.putString(KEY_ID,user.getIs_ohc());
         editor.putString(KEY_PASS,user.getNickname());
         editor.putString(KEY_PAS,user.getLocation());

        // editor.putString(KEY_EMAIL,);
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(

                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_EMAIL,null),
                sharedPreferences.getString(KEY_GENDER,null),
               sharedPreferences.getString(KEY_ID,null),
                sharedPreferences.getString(KEY_PASS,null),
                sharedPreferences.getString(KEY_PAS,null));
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME,null);
        editor.putString(KEY_PASSWORD,null);
        editor.putString(KEY_EMAIL,null);
        editor.putString(KEY_GENDER,null);
        editor.putString(KEY_ID,null);
        editor.putString(KEY_PASS,null);
        editor.putString(KEY_PAS,null);
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));

    }
}
