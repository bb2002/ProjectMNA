package kr.saintdev.projectmna.views.staff.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import kr.saintdev.projectmna.views.common.SuperFragment;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-06
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    SuperFragment[] pages = null;

    public MainViewPagerAdapter(FragmentManager fm, SuperFragment[] pages) {
        super(fm);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return pages[position];
    }

    @Override
    public int getCount() {
        return pages.length;
    }
}
