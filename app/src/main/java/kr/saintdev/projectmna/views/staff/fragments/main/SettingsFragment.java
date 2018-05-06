package kr.saintdev.projectmna.views.staff.fragments.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.views.common.SuperFragment;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-06
 */

public class SettingsFragment extends SuperFragment {
    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmn_staff_main_settings, container, false);
        return v;
    }
}
