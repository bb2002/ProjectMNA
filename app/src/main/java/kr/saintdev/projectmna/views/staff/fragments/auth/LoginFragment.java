package kr.saintdev.projectmna.views.staff.fragments.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.views.common.SuperFragment;
import kr.saintdev.projectmna.views.staff.activitys.StaffAuthActivity;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-02
 */

public class LoginFragment extends SuperFragment {
    StaffAuthActivity control = null;

    EditText userIdEditor = null;
    EditText userPasswdEditor = null;
    Button loginButton = null;
    TextView gotoAdminPage = null;
    TextView gotoSignup = null;

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmn_staff_auth_login, container, false);

        // control 을 구합니다
        this.control = (StaffAuthActivity) getActivity();
        this.control.setActionBarTitle("MNA");

        // 개체를 찾습니다
        this.userIdEditor = v.findViewById(R.id.staff_auth_userid);
        this.userPasswdEditor = v.findViewById(R.id.staff_auth_userpass);
        this.loginButton = v.findViewById(R.id.staff_auth_submit);
        this.gotoAdminPage = v.findViewById(R.id.staff_auth_goto_admin);
        this.gotoSignup = v.findViewById(R.id.staff_auth_goto_signup);

        // 이벤트를 등록한다.
        OnItemClickHandler handler = new OnItemClickHandler();
        this.gotoSignup.setOnClickListener(handler);

        return v;
    }

    class OnItemClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.staff_auth_goto_signup:
                    // 보고있는 Fragment 를 SignupFragment 으로 변경한다.
                    control.switchFragment(new SignupFragment());
                    break;
            }
        }
    }
}
