package kr.saintdev.projectmna.views.staff.fragments.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.staff.adapters.WorklogAdapter;
import kr.saintdev.projectmna.views.common.SuperFragment;
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

        // worklog 를 띄운다.
        WorklogAdapter adapter = new WorklogAdapter(null);
        this.worklog.setAdapter(adapter);

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
}
