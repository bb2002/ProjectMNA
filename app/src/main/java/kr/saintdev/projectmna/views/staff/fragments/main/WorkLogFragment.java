package kr.saintdev.projectmna.views.staff.fragments.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.common.constant.HttpURLDefines;
import kr.saintdev.projectmna.modules.common.dbm.Authme;
import kr.saintdev.projectmna.modules.common.modules.BackgroundWork;
import kr.saintdev.projectmna.modules.common.modules.OnBackgroundWorkListener;
import kr.saintdev.projectmna.modules.common.modules.http.HttpRequester;
import kr.saintdev.projectmna.modules.common.modules.http.HttpResponseObject;
import kr.saintdev.projectmna.modules.common.utils.ConstConverter;
import kr.saintdev.projectmna.modules.staff.adapters.WorklogAdapter;
import kr.saintdev.projectmna.modules.staff.lib.objects.WorklogObject;
import kr.saintdev.projectmna.views.common.SuperFragment;
import kr.saintdev.projectmna.views.common.dialogs.main.DialogManager;
import kr.saintdev.projectmna.views.staff.activitys.StaffMainActivity;
import kr.saintdev.projectmna.modules.staff.lib.dbm.StaffAccountManager;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-06
 */

public class WorkLogFragment extends SuperFragment {
    TextView workspace = null;      // 작업영역 뷰어
    TextView staffName = null;      // 직원 이름
    Button timeSection = null;     // 구역 선택
    TextView workTime = null;       // 근무 시간
    ListView worklog = null;        // 근무 기록

    StaffMainActivity control = null;
    StaffAccountManager staffInfo = null;       // 직원 정보
    Authme me = null;
    ArrayList<WorklogObject> works = null;


    public WorkLogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmn_staff_main_worklog, container, false);

        this.control = (StaffMainActivity) getActivity();

        this.workspace = v.findViewById(R.id.main_worklog_header_workspace);
        this.staffName = v.findViewById(R.id.main_worklog_header_name);
        this.timeSection = v.findViewById(R.id.main_worklog_section);
        this.workTime = v.findViewById(R.id.main_worklog_section_time);
        this.worklog = v.findViewById(R.id.main_worklog_listview);
        this.me = Authme.getInstance(control);

        OnButtonClickHandler handler = new OnButtonClickHandler();
        this.timeSection.setOnClickListener(handler);

        this.staffInfo = StaffAccountManager.getInstance(control);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        // 직원 정보를 보여준다.
        this.workspace.setText(staffInfo.getValue("staff-workspace"));
        this.staffName.setText(staffInfo.getValue("staff-name"));

        // 근무 기록을 서버로 부터 불러옵니다
        HashMap<String, Object> args = new HashMap<>();
        args.put("staff-uuid", this.me.getUserPin());
        HttpRequester worklogRequester =
                new HttpRequester(HttpURLDefines.STAFF_WORKLOG, args, 0x0, new OnBackgroundWorkHandler());
        worklogRequester.execute();

    }

    class OnButtonClickHandler implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.main_worklog_section:
                    Calendar now = Calendar.getInstance();

                    DatePickerDialog datePicker = new DatePickerDialog(control, this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);
                    datePicker.show();
                    break;
            }
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            timeSection.setText(year + "년 " + month + "월");
        }
    }

    class OnBackgroundWorkHandler implements OnBackgroundWorkListener {
        @Override
        public void onSuccess(int requestCode, BackgroundWork worker) {
            HttpResponseObject resp = (HttpResponseObject) worker.getResult();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREA);

            int workingTime = 0;

            try {
                if (resp.getResponseResultCode() == 200) {
                    JSONObject body = resp.getBody();

                    JSONArray worklog = body.getJSONArray("worklog");
                    ArrayList<WorklogObject> workLogs = new ArrayList<>();

                    for(int i = 0; i < worklog.length(); i ++) {
                        JSONObject obj = worklog.getJSONObject(i);

                        boolean isWorking =
                                obj.getString("staff_go_home").equals("null");

                        String home =
                                isWorking ? "근무중" : obj.getString("staff_go_home");

                        WorklogObject workObj = new WorklogObject(
                                obj.getString("work_id"),
                                obj.getString("staff_date"),
                                ConstConverter.dateConverter(obj.getString("staff_go_work")),
                                home,
                                isWorking
                        );

                        workLogs.add(workObj);

                        // 해당 근무에 대한 시간 을 구합니다.
                        if(!isWorking) {
                            try {
                                Date startTime = sdf.parse(obj.getString("staff_go_work"));
                                Date endTime = sdf.parse(obj.getString("staff_go_home"));

                                // 작업 시간 (초) 단위로 가져옵니다.
                                workingTime += ((endTime.getTime() - startTime.getTime()) / 1000);
                            } catch(Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    works = workLogs;

                    // 총 근무시간을 불러옵니다.
                    workTime.setText(ConstConverter.getHMS(workingTime));
                    Log.d("mna", workingTime + "초 근무함");
                } else {
                    Toast.makeText(control, "데이터를 불러올 수 없습니다..", Toast.LENGTH_LONG).show();
                    return;
                }

                // Worklog 를 띄웁니다.
                WorklogAdapter adapter = new WorklogAdapter(works, control);
                worklog.setAdapter(adapter);
            } catch(JSONException jex) {
                jex.printStackTrace();
            }
        }

        @Override
        public void onFailed(int requestCode, Exception ex) {
            DialogManager dm = new DialogManager(control);
            dm.setTitle("Fatal error");
            dm.setDescription(ex.getMessage());
            dm.show();
        }
    }
}
