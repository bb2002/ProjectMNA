package kr.saintdev.projectmna.views.staff.fragments.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.views.common.SuperFragment;
import kr.saintdev.projectmna.views.staff.activitys.StaffAuthActivity;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-02
 */

public class SignupFragment extends SuperFragment {
    StaffAuthActivity control = null;

    public SignupFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmn_staff_auth_signup, container, false);

        // control 을 구합니다
        this.control = (StaffAuthActivity) getActivity();
        this.control.setActionBarTitle("회원가입");

        return v;
    }
}
