package kr.saintdev.projectmna.views.staff.fragments.auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.constant.HttpURLDefines;
import kr.saintdev.projectmna.modules.modules.BackgroundWork;
import kr.saintdev.projectmna.modules.modules.OnBackgroundWorkListener;
import kr.saintdev.projectmna.modules.modules.http.HttpRequester;
import kr.saintdev.projectmna.modules.modules.http.HttpResponseObject;
import kr.saintdev.projectmna.views.admin.AdminAuthActivity;
import kr.saintdev.projectmna.views.common.SuperFragment;
import kr.saintdev.projectmna.views.common.dialogs.main.DialogManager;
import kr.saintdev.projectmna.views.common.dialogs.main.clicklistener.OnYesClickListener;
import kr.saintdev.projectmna.views.common.dialogs.progress.ProgressManager;
import kr.saintdev.projectmna.views.staff.activitys.StaffAuthActivity;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-02
 */

public class LoginFragment extends SuperFragment {
    StaffAuthActivity control = null;

    EditText userTelEditor = null;
    EditText userPasswdEditor = null;
    Button loginButton = null;
    TextView gotoAdminPage = null;
    TextView gotoSignup = null;

    DialogManager dialog = null;        // 오류 발생시 띄울 창
    ProgressManager pm = null;          // 프로그래스 다이얼로그

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
        this.userTelEditor = v.findViewById(R.id.staff_auth_userid);
        this.userPasswdEditor = v.findViewById(R.id.staff_auth_userpass);
        this.loginButton = v.findViewById(R.id.staff_auth_submit);
        this.gotoAdminPage = v.findViewById(R.id.staff_auth_goto_admin);
        this.gotoSignup = v.findViewById(R.id.staff_auth_goto_signup);

        // 이벤트를 등록한다.
        OnItemClickHandler handler = new OnItemClickHandler();
        this.gotoSignup.setOnClickListener(handler);
        this.loginButton.setOnClickListener(handler);
        this.gotoAdminPage.setOnClickListener(handler);

        // dialog 정의
        dialog = new DialogManager(control);
        dialog.setOnYesButtonClickListener(new OnYesClickListener() {   // OK 를 클릭하면 리스닝
            @Override
            public void onClick(DialogInterface dialog) {
                dialog.dismiss();
            }
        }, "OK");
        pm = new ProgressManager(control);

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
                case R.id.staff_auth_goto_admin:
                    Intent intent = new Intent(control, AdminAuthActivity.class);
                    startActivity(intent);
                    control.finish();
                    break;
                case R.id.staff_auth_submit:
                    doLogin();  // 사용자가 입력한 정보로 로그인을 시도합니다.
                    break;
            }
        }

        private void doLogin() {
            String tel = userTelEditor.getText().toString();
            String pass = userPasswdEditor.getText().toString();

            // 전화번호와 비밀번호를 입력받고 검사한다.
            if(tel.length() == 0 || pass.length() == 0) {
                dialog.setTitle("오류");
                dialog.setDescription("입력되지 않은 값이 있습니다.");
                dialog.show();
                return;
            }

            // 서버에 로그인 요청을 한다.
            HashMap<String, Object> args = new HashMap<>();
            args.put("user-tel", tel);
            args.put("user-passwd", pass);
            HttpRequester request = new HttpRequester(HttpURLDefines.AUTH_LOGIN, args, 0, new OnBackgroundWorkHandler());
            request.execute();

            pm.setMessage("Now loading...");
            pm.enable();
        }
    }

    class OnBackgroundWorkHandler implements OnBackgroundWorkListener {
        @Override
        public void onSuccess(int requestCode, BackgroundWork worker) {
            pm.disable();

            HttpResponseObject respObj = (HttpResponseObject) worker.getResult();

            // 우선 계정이 있는지 확인합니다.
            try {
                if (respObj.getResponseResultCode() == 200) {
                    // 서버측 처리 성공
                    JSONObject body = respObj.getBody();

                    if (body.getString("is-success").equals("yes")) {
                        // 처리 성공
                        // 계정 정보를 Authme 를 통해 저장하고, 이후로는 자동로그인을 사용합니다.
                        Toast.makeText(control, "SUCCCESS", Toast.LENGTH_SHORT).show();
                    } else {
                        // 승인되지 않은 계정입니다.
                        dialog.setTitle("Auth error");
                        dialog.setDescription("승인되지 않은 계정입니다.\n관리자에게 문의하세요.");
                        dialog.show();
                    }
                } else if (respObj.getResponseResultCode() == 401) {
                    // 로그인 정보 오류
                    dialog.setTitle("Auth error");
                    dialog.setDescription("휴대폰 번호 또는 비밀번호가 잘못되었습니다.");
                    dialog.show();
                } else {
                    // 내부 서버 오류
                    dialog.setTitle("Internal server error");
                    dialog.setDescription("An error occurred. 500");
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailed(int requestCode, Exception ex) {
            pm.disable();
            dialog.setTitle("Fatal error");
            dialog.setDescription("An error occurred.\n" + ex.getMessage());
            dialog.show();
        }
    }
}
