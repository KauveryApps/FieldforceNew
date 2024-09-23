package com.kauveryhospital.fieldforce;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kauveryhospital.fieldforce.UserAdmin.Manager;
import com.kauveryhospital.fieldforce.UseronlyAccess.Representative;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Manager footballFragment = new Manager();
                return footballFragment;
            case 1:
                Representative cricketFragment = new Representative();
                return cricketFragment;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {

        return totalTabs;
    }
}
