package kr.saintdev.projectmna.views.staff.fragments.auth;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.constant.HttpURLDefines;
import kr.saintdev.projectmna.modules.modules.BackgroundWork;
import kr.saintdev.projectmna.modules.modules.OnBackgroundWorkListener;
import kr.saintdev.projectmna.modules.modules.http.HttpRequester;
import kr.saintdev.projectmna.modules.modules.http.HttpResponseObject;
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

public class SignupFragment extends SuperFragment {
    StaffAuthActivity control = null;

    EditText nameEditor = null;
    EditText telEditor = null;
    EditText passwdEditor = null;
    Button okButton = null;
    Button cancelButton = null;

    DialogManager infoDialog = null;        // 오류 발생시 사용자에게 표시할 팝업
    ProgressManager proManager = null;      // 로딩 프로그레스 팝업

    public SignupFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmn_staff_auth_signup, container, false);

        // control 을 구합니다
        this.control = (StaffAuthActivity) getActivity();
        this.control.setActionBarTitle("회원가입");

        // 개체를 찾습니다.
        this.nameEditor = v.findViewById(R.id.staff_signup_field_name);
        this.telEditor = v.findViewById(R.id.staff_signup_field_tel);
        this.passwdEditor = v.findViewById(R.id.staff_signup_field_passwd);
        this.okButton = v.findViewById(R.id.staff_signup_button_commit);
        this.cancelButton = v.findViewById(R.id.staff_signup_button_cancel);

        this.infoDialog = new DialogManager(this.control);
        this.infoDialog.setOnYesButtonClickListener(new OnDialogClickHandler(), "OK");
        this.proManager = new ProgressManager(this.control);

        // 이벤트를 등록합니다.
        OnButtonClickHandler handler = new OnButtonClickHandler();
        this.okButton.setOnClickListener(handler);
        this.cancelButton.setOnClickListener(handler);
        return v;
    }

    class OnButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(v.getId()== R.id.staff_signup_button_commit) {
                // 확인 버튼을 눌렀을 경우
                String name = nameEditor.getText().toString();
                String tel = telEditor.getText().toString();
                String passwd = passwdEditor.getText().toString();

                if(name.length() == 0 || tel.length() == 0 || passwd.length() == 0) {
                    // 입력되지 않은 값이 있습니다.
                    infoDialog.setTitle("오류");
                    infoDialog.setDescription("모든 정보를 입력해주세요.");
                    infoDialog.show();
                } else {
                    // 모두 입력하셨습니다.
                    HashMap<String, Object> args = new HashMap<>();
                    args.put("user-name", name);
                    args.put("user-tel", tel);
                    args.put("user-passwd", passwd);
                    args.put("user-permiss", 0);        // 현재 생성하는 계정은 직원을 위한 계정

                    proManager.setMessage("Loading...");
                    proManager.enable();
                    HttpRequester request = new HttpRequester(HttpURLDefines.AUTH_SIGNUP, args, 0, new OnBackgroundTaskHandler());
                    request.execute();
                }

            } else if(v.getId() == R.id.staff_signup_button_cancel) {
                // 닫기 버튼을 눌렀을 경우
                control.switchFragment(new LoginFragment());
            }
        }
    }

    /*
        백그라운드 작업이 끝났을 경우 Callback
     */
    class OnBackgroundTaskHandler implements OnBackgroundWorkListener {
        @Override
        public void onSuccess(int requestCode, BackgroundWork worker) {
            proManager.disable();

            // Http 응답값을 가져옵니다.
            HttpResponseObject respObj = (HttpResponseObject) worker.getResult();

            if(respObj.getResponseResultCode() == 200) {
                try {
                    if (!respObj.getBody().getString("is-success").equals("ALREADY")) {
                        infoDialog.setTitle("가입되었습니다.");
                        infoDialog.setDescription("관리자가 이 계정을 승인하면 로그인하세요.");
                    } else {
                        infoDialog.setTitle("이미 가입된 계정입니다.");
                        infoDialog.setDescription("로그인 해 보세요.");
                    }
                } catch(JSONException jex){
                    jex.printStackTrace();
                }
            } else {
                infoDialog.setTitle("Error");
                infoDialog.setDescription("Internal server error.\n" + respObj.getResponseResultCode());
            }

            infoDialog.show();
            control.switchFragment(new LoginFragment());
        }

        @Override
        public void onFailed(int requestCode, Exception ex) {
            if(requestCode == 0) {
                // 회원가입 처리 관련 오류
                infoDialog.setTitle("Internal server error.");
                infoDialog.setDescription("An error occrred.\n" + ex.getMessage());
                infoDialog.show();
            }


            proManager.disable();
        }
    }

    /*
        Dialog 의 OK 버튼을 클릭할 경우
     */
    class OnDialogClickHandler implements OnYesClickListener {
        @Override
        public void onClick(DialogInterface dialog) {
            dialog.dismiss();
        }
    }
}
