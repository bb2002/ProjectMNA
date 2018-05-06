package kr.saintdev.projectmna.views.staff.fragments.auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.HashMap;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.constant.HttpURLDefines;
import kr.saintdev.projectmna.modules.dbm.Authme;
import kr.saintdev.projectmna.modules.modules.BackgroundWork;
import kr.saintdev.projectmna.modules.modules.OnBackgroundWorkListener;
import kr.saintdev.projectmna.modules.modules.http.HttpRequester;
import kr.saintdev.projectmna.modules.modules.http.HttpResponseObject;
import kr.saintdev.projectmna.views.common.SuperFragment;
import kr.saintdev.projectmna.views.common.dialogs.main.DialogManager;
import kr.saintdev.projectmna.views.common.dialogs.main.clicklistener.OnYesClickListener;
import kr.saintdev.projectmna.views.staff.activitys.StaffAuthActivity;
import kr.saintdev.projectmna.views.staff.activitys.StaffMainActivity;

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

    DialogManager dm = null;

    public LoadingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmn_staff_auth_loading, container, false);

        // control 객체를 얻습니다.
        this.control = (StaffAuthActivity) getActivity();
        this.dm = new DialogManager(control);
        this.dm.setOnYesButtonClickListener(new DialogButtonClickHandler(), "OK");

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Authme me = Authme.getInstance(control);
        String userPin = me.getUserPin();

        if(userPin == null) {
            // 로그인이 필요합니다.
            control.switchFragment(new LoginFragment());
            return;
        }

        // 사용자를 자동로그인 한 후, MainActivity 를 실행합니다.
        HashMap<String, Object> args = new HashMap<>();
        args.put("user-uuid", userPin);
        args.put("user-permiss", 0);    // Staff 계정으로 로그인 합니다.

        HttpRequester requester = new HttpRequester(HttpURLDefines.AUTO_LOGIN, args, 0, new OnBackgroundWorkerHandler());
        requester.execute();
    }

    class OnBackgroundWorkerHandler implements OnBackgroundWorkListener {
        @Override
        public void onSuccess(int requestCode, BackgroundWork worker) {
            HttpResponseObject respObj = (HttpResponseObject) worker.getResult();
            Authme me = Authme.getInstance(control);

            if(respObj.getResponseResultCode() == 200) {
                // 자동 로그인 성공
                // 사용자 계정 정보를 업데이트 합니다.
                JSONObject body = respObj.getBody();

                if(body == null) {
                    // body 가 없습니다.
                    dm.setTitle("Fatal error");
                    dm.setDescription("Body 를 전달받지 못했습니다.");
                    dm.show();
                    return;
                }

                try {
                    Authme.UserPermission permiss = Authme.UserPermission.STAFF;

                    // 서버로 부터 받은 데이터에 대한 사용자 계정 정보
                    Authme.AuthObject authObj = new Authme.AuthObject(
                            body.getString("user-name"),
                            body.getString("user-tel"),
                            permiss
                            );
                    me.setAuthObject(authObj);  // 계정 정보값을 저장합니다.

                    // MainActivity 를 실행합니다.
                    Intent main = new Intent(control, StaffMainActivity.class);
                    startActivity(main);
                    control.finish();
                } catch(Exception ex) {
                    dm.setTitle("Fatal error");
                    dm.setDescription("JSONException " + ex.getMessage());
                    dm.show();
                }
            } else if(respObj.getResponseResultCode() == 401){
                // 인증서 필드가 잘못되었습니다.
                dm.setTitle("Fatal error");
                dm.setDescription("인증서가 잘못되었습니다.\n다시 로그인 해 주세요.");
                dm.show();

                me.clear();
            } else {
                // 내부 서버 오류
                dm.setTitle("Fatal error");
                dm.setDescription("Internal server error. 500");
                dm.show();
            }
        }

        @Override
        public void onFailed(int requestCode, Exception ex) {
            // 클라이언트 요청 실패
            dm.setTitle("Http 요청 실패");
            dm.setDescription("Http 요청에 실패하였습니다.\n" + ex.getMessage());
            dm.show();
        }
    }

    class DialogButtonClickHandler implements OnYesClickListener {
        @Override
        public void onClick(DialogInterface dialog) {
            dialog.dismiss();
            control.finish();
        }
    }
}
