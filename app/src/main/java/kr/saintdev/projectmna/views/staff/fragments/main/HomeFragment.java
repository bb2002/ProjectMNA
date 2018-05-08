package kr.saintdev.projectmna.views.staff.fragments.main;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
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
import kr.saintdev.projectmna.views.staff.activitys.StaffMainActivity;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 * @Date 2018-05-06
 */

public class HomeFragment extends SuperFragment {
    TextView workspaceView = null;  // 근무지 값
    TextView workDateView = null;   // 작업 시간 로그
    Button gotoWork = null;     // 출근 한다
    Button gotoHome = null;     // 퇴근 한다

    StaffMainActivity control = null;
    DialogManager dm = null;
    OnBackgroundWorkHandler backgroundHandler = null;
    Authme me = null;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmn_staff_main_home, container, false);

        this.control = (StaffMainActivity) getActivity();

        this.workspaceView = v.findViewById(R.id.staff_home_workspace);
        this.workDateView = v.findViewById(R.id.staff_home_date);
        this.gotoWork = v.findViewById(R.id.staff_home_gowork);
        this.gotoHome = v.findViewById(R.id.staff_home_gohome);
        this.dm = new DialogManager(control);
        this.dm.setOnYesButtonClickListener(new OnDialogCloseHandler(), "OK");

        OnButtonClickHandler handler = new OnButtonClickHandler();
        this.backgroundHandler = new OnBackgroundWorkHandler();
        this.gotoWork.setOnClickListener(handler);
        this.gotoHome.setOnClickListener(handler);

        this.me = Authme.getInstance(control);
        String userPin = me.getUserPin();

        if(userPin == null) {
            // 계정 정보 불러오기 실패
            // 데이터 초기화
            me.clear();

            dm.setTitle("인증서 오류");
            dm.setDescription("인증서를 불러올 수 없습니다.\n다시 로그인 하세요.");
            dm.show();
        } else {
            // 계정 정보를 불러왔습니다.
            // Http 요청을 처리합니다.
            HashMap<String, Object> args = new HashMap<>();
            args.put("staff-uuid", userPin);

            HttpRequester requester
                    = new HttpRequester(HttpURLDefines.STAFF_REQEUST_INFO, args, 0x0, this.backgroundHandler);
            requester.execute();

            // 직원 상태를 불러옵니다.
            HttpRequester worklogRequester = new HttpRequester(HttpURLDefines.STAFF_NOW_STATUS, args, 0x3, this.backgroundHandler);
            worklogRequester.execute();
        }

        return v;
    }

    /**
        출근 또는 퇴근 버튼을 눌렀을 때 호출될 리스너
     */
    class OnButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            HttpRequester request = null;
            HashMap<String, Object> args = new HashMap<>();
            args.put("staff-uuid", me.getUserPin());

            switch (view.getId()) {
                case R.id.staff_home_gohome:
                    request = new HttpRequester(HttpURLDefines.STAFF_GO_HOME, args, 0x2, backgroundHandler);
                    break;
                case R.id.staff_home_gowork:
                    request = new HttpRequester(HttpURLDefines.STAFF_GO_WORK, args, 0x1, backgroundHandler);
                    break;
            }

            request.execute();
        }
    }

    class OnDialogCloseHandler implements OnYesClickListener {
        @Override
        public void onClick(DialogInterface dialog) {
            dialog.dismiss();
        }
    }

    class OnBackgroundWorkHandler implements OnBackgroundWorkListener {
        @Override
        public void onSuccess(int requestCode, BackgroundWork worker) {
            HttpResponseObject obj = (HttpResponseObject) worker.getResult();
            JSONObject body = obj.getBody();
            int responseCode = obj.getResponseResultCode();

            try {
                switch (requestCode) {
                    case 0x0:
                        // 사용자 계정 데이터를 불러옵니다.
                        if (responseCode == 200) {
                            // 처리 성공
                            String workspace = body.getString("staff-workspace");
                            workspaceView.setText(workspace);
                        } else {
                            // 처리 실패!
                            dm.setTitle("Fatal error");
                            dm.setDescription("An error occrred.\n" + obj.getResponseResultCode());
                            dm.show();
                        }
                    case 0x1:
                        // 직원이 출근합니다.
                        if (responseCode == 200) {
                            String msg = body.getString("is-success");
                            if (msg.equals("OK")) enableHome();
                        }
                    case 0x2:
                        // 직원이 퇴근합니다.
                        if (responseCode == 200) {
                            String msg = body.getString("is-success");
                            if (msg.equals("OK")) enableWork();
                        } else {
                            dm.setTitle("퇴근 실패");
                            dm.setDescription("서버 오류가 발생했습니다.\n오늘은 야근입니다.");
                            dm.show();
                        }
                    case 0x3:
                        // 직원 상태를 불러옵니다.
                        if (obj.getResponseResultCode() == 200) {
                            String msg = body.getString("staff-status");

                            // 현재 상태를 보고합니다.
                            if (msg.equals("WORK")) enableWork();
                            else enableHome();
                        }
                }
            } catch(JSONException jex) {
                dm.setTitle("JSONException");
                dm.setDescription("Runtime error.\n" + jex.getMessage());
                dm.show();
            }
        }

        @Override
        public void onFailed(int requestCode, Exception ex) {
            dm.setTitle("데이터 요청 실패!");
            dm.setDescription("데이터를 요청 할 수 없습니다.\n" + ex.getMessage());
            dm.show();

            ex.printStackTrace();
        }
    }

    private void enableWork() {
        gotoWork.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        gotoWork.setEnabled(true);
        gotoHome.setBackgroundColor(Color.WHITE);
        gotoHome.setEnabled(false);
    }

    private void enableHome() {
        gotoWork.setBackgroundColor(Color.WHITE);
        gotoWork.setEnabled(false);
        gotoHome.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        gotoHome.setEnabled(true);
    }
}
