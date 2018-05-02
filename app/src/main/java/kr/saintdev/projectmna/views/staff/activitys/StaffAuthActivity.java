package kr.saintdev.projectmna.views.staff.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.views.common.SuperFragment;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 * @Date 2015-05-02.
 */

public class StaffAuthActivity extends AppCompatActivity {
    SuperFragment nowView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_auth);
        setActionBarTitle(null);

    }

    /**
       안드로이드 상단 액션바를 수정합니다.
        @date 05.02 2018
        @param title null 일경우 액션바를 숨깁니다.
     */
    public void setActionBarTitle(@Nullable  String title) {
        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            if(title == null) {
                bar.hide();
            } else {
                bar.show();
                bar.setTitle(title);
                bar.setElevation(0);        // 액션바의 그림자를 없앱니다.
            }
        }
    }

    /**
     * 현재 보고있는 Fragment 뷰를 교체합니다.
     * @date 05.02 2018
     */
    public void switchFragment(SuperFragment view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.staff_auth_container, view);
        ft.commit();

        this.nowView = view;    // 현재 뷰는 교체된 뷰 입니다.
    }
}
