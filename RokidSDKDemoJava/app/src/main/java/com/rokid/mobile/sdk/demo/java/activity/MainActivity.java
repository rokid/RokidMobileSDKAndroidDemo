package com.rokid.mobile.sdk.demo.java.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.rokid.mobile.sdk.demo.java.R;
import com.rokid.mobile.sdk.demo.java.fragment.BindFragment;
import com.rokid.mobile.sdk.demo.java.fragment.DeviceFragment;
import com.rokid.mobile.sdk.demo.java.fragment.LoginFragment;
import com.rokid.mobile.sdk.demo.java.fragment.MessageFragment;
import com.rokid.mobile.sdk.demo.java.fragment.SkillFragment;
import com.rokid.mobile.sdk.demo.java.view.BottomNavigationViewEx;
import com.rokid.mobile.sdk.demo.java.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static boolean ISLOGIN = false;

    public static String UID = "";

    private NoScrollViewPager mViewPager;

    private BottomNavigationViewEx mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        initListener();
    }

    private void initView() {
        mViewPager = findViewById(R.id.activity_main_viewpager);
        mBottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view);
        mBottomNavigationView.enableAnimation(true);
        mBottomNavigationView.enableShiftingMode(false);
        mBottomNavigationView.enableItemShiftingMode(false);
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>(5);

        // add to fragments for adapter
        fragments.add(new LoginFragment());
        fragments.add(new DeviceFragment());
        fragments.add(new BindFragment());
        fragments.add(new SkillFragment());
        fragments.add(new MessageFragment());

        // set adapter
        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);

        // binding with ViewPager
        mBottomNavigationView.setupWithViewPager(mViewPager);
    }

    private void initListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (!ISLOGIN && !item.getTitle().equals(getString(R.string.bottom_navigation_login))) {
                            Toast.makeText(MainActivity.this,
                                    getString(R.string.activity_main_not_login_tip),
                                    Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        return true;
                    }
                });
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
