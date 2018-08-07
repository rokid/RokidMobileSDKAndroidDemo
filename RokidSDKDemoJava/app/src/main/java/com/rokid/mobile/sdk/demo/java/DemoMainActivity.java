package com.rokid.mobile.sdk.demo.java;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;
import android.widget.Toast;

import com.rokid.mobile.sdk.demo.java.account.AccountIndexFragment;
import com.rokid.mobile.sdk.demo.java.base.BaseActivity;
import com.rokid.mobile.sdk.demo.java.device.DeviceIndexFragment;
import com.rokid.mobile.sdk.demo.java.other.OtherIndexFragment;
import com.rokid.mobile.sdk.demo.java.skill.SkillIndexFragment;
import com.rokid.mobile.sdk.demo.java.view.BottomNavigationViewEx;
import com.rokid.mobile.sdk.demo.java.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class DemoMainActivity extends BaseActivity {

    public static boolean ISLOGIN = false;

    public static String UID = "";

    private NoScrollViewPager mViewPager;

    private BottomNavigationViewEx mBottomNavigationView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables(@Nullable Bundle savedInstanceState) {
        initView();
        initFragment();
    }

    @Override
    protected void initListeners() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (!ISLOGIN && !item.getTitle().equals(getString(R.string.bottom_navigation_account))) {
                            Toast.makeText(DemoMainActivity.this,
                                    getString(R.string.activity_main_not_login_tip),
                                    Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        return true;
                    }
                });
    }

    private void initView() {
        mViewPager = (NoScrollViewPager) findViewById(R.id.activity_main_viewpager);
        mBottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.activity_main_bottom_navigation_view);
        mBottomNavigationView.enableAnimation(true);
        mBottomNavigationView.enableShiftingMode(false);
        mBottomNavigationView.enableItemShiftingMode(false);
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>(4);

        // add to fragments for adapter
        fragments.add(new AccountIndexFragment());
        fragments.add(new DeviceIndexFragment());
        fragments.add(new SkillIndexFragment());
        fragments.add(new OtherIndexFragment());

        // set adapter
        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);

        // binding with ViewPager
        mBottomNavigationView.setupWithViewPager(mViewPager);
    }

    /**
     * viewpager adapter
     */
    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }

}
