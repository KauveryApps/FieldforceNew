package com.kauveryhospital.fieldforce;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.kauveryhospital.fieldforce.OHCOnly.OHCEmployee;
import com.kauveryhospital.fieldforce.UserAdmin.Manager;
import com.kauveryhospital.fieldforce.UseronlyAccess.Representative;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOCUMENTS;

public class TabbedActivity extends AppCompatActivity {
    ViewPager viewPager;
    public static final String PREFS_NAME = "loginpref";
    private static final int REQUEST_WRITE_STORAGE = 12;
    TabLayout tabs;
    static TabbedActivity  instance;
    String uname, pswd, uaa, isohc;
    private int[] tabIcons =
            {
            R.drawable.persons,
            R.drawable.admin
            };
    ImageView loggedout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        } else {
            createFolder();
        }
        loggedout = findViewById(R.id.loggedout);
        SharedPreferences set = getSharedPreferences(PREFS_NAME, 0);
        uname = set.getString("username", "");
        pswd = set.getString("password", "");
        uaa = set.getString("uaa", "");
        isohc=set.getString("isohc","");
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        if (uaa.equals("T")) {
            setupViewPager(viewPager);
            tabs.setupWithViewPager(viewPager);
            setupTabIcons();
        }
        else if(isohc.equals("T"))
        {
               setupViewPager2(viewPager);
               tabs.setupWithViewPager(viewPager);
               setupTabIcons1();
        }
        else {
              setupViewPager1(viewPager);
              tabs.setupWithViewPager(viewPager);
              setupTabIcons1();
              }
        loggedout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPrefManager.getInstance(getBaseContext()).logout();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Representative(), "REPRESENTATIVES");
        adapter.addFragment(new Manager(), "REGIONAL MANAGER");

        viewPager.setAdapter(adapter);
    }
     public void createFolder()
     {
        final File f = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOCUMENTS).getPath() + "/FieldForceTravelExpenses");
        if (!f.exists()) {
            Toast.makeText(this, "Folder doesn't exist, creating it...", Toast.LENGTH_SHORT).show();
            boolean rv = f.mkdir();
            Toast.makeText(this, "Folder creation " + (rv ? "success" : "failed"), Toast.LENGTH_SHORT).show();
        } else {
          //Toast.makeText(this, "Folder already exists.", Toast.LENGTH_SHORT).show();
        }
    }
    private void setupTabIcons()
    {
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager1(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Representative(), "MEDICAL REPRESENTATIVES");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons1()
    {
        tabs.getTabAt(0).setIcon(tabIcons[0]);
    }
    private void setupViewPager2(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OHCEmployee(), "OHC EMPLOYEES");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons2() {
        tabs.getTabAt(1).setIcon(tabIcons[1]);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static TabbedActivity getInstance() {
        return instance;
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TabbedActivity.this);
        builder.setTitle("");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do You Want To Exit FieldForce?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
