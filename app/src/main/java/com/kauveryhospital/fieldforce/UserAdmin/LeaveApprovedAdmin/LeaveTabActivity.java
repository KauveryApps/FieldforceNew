package com.kauveryhospital.fieldforce.UserAdmin.LeaveApprovedAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.kauveryhospital.fieldforce.R;
import com.kauveryhospital.fieldforce.TabbedActivity;

import java.util.ArrayList;
import java.util.List;

public class LeaveTabActivity extends AppCompatActivity {
    ViewPager viewPager;
    public static  final String PREFS_NAME="loginpref";
    TabLayout tabs;
    static TabbedActivity instance;
    String uname,pswd,uaa;
    ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_tab);
        SharedPreferences set=getSharedPreferences(PREFS_NAME,0);
        uname=set.getString("username","");
        pswd=set.getString("password","");
        uaa=set.getString("uaa","");
        backarrow=findViewById(R.id.backarrow);

        viewPager = findViewById(R.id.view_pager);


        tabs= findViewById(R.id.tabs);
        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeaveTabActivity.this, TabbedActivity.class));
            }
        });

    }
    private void setupViewPager(ViewPager viewPager) {
        LeaveTabActivity.ViewPagerAdapter adapter = new LeaveTabActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PendingFragment(), "PENDING");
        adapter.addFragment(new CancellingFragment(), "REAPPROVAL");

        viewPager.setAdapter(adapter);
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

}