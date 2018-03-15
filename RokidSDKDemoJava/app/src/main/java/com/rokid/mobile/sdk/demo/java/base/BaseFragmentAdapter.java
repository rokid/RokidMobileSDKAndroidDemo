package com.rokid.mobile.sdk.demo.java.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by tt on 2018/2/28.
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private List<Node> data;

    public BaseFragmentAdapter(FragmentManager fm, List<Node> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position).fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).name;
    }

    public static class Node {
        String name;
        Fragment fragment;

        public Node(String name, Fragment fragment) {
            this.name = name;
            this.fragment = fragment;
        }
    }
}
