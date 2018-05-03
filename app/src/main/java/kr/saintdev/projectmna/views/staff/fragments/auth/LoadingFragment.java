package kr.saintdev.projectmna.views.staff.fragments.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.dbm.Authme;
import kr.saintdev.projectmna.views.common.SuperFragment;
import kr.saintdev.projectmna.views.staff.activitys.StaffAuthActivity;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-03
 *
 * 여기서 사용자의 정보를 읽어 오고, user-pin 필드가 있다면 로그인을 처리하며
 * 없다면 SignupFragment 를 띄웁니다.
 */

public class LoadingFragment extends SuperFragment {
    StaffAuthActivity control = null;

    public LoadingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmn_staff_auth_loading, container, false);

        // control 객체를 얻습니다.
        this.control = (StaffAuthActivity) getActivity();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Authme me = Authme.getInstance(control);

        if(me.getUserPin() == null) {
            // 로그인이 필요합니다.
            control.switchFragment(new LoginFragment());
            Log.d("MNA", "로그인 하러 갑니다.");
            return;
        }

        // 사용자를 자동로그인 한 후, MainActivity 를 실행합니다.

    }
}
