package com.bwf.qingdan.gui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by LG on 2016/10/14.
 * Tips:
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public MainFragmentPagerAdapter(List<Fragment> fragments,FragmentManager fm) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
