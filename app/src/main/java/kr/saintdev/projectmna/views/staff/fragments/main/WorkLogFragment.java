package kr.saintdev.projectmna.views.staff.fragments.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.dbm.Authme;
import kr.saintdev.projectmna.views.common.SuperFragment;
import kr.saintdev.projectmna.views.staff.activitys.StaffMainActivity;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-06
 */

public class WorkLogFragment extends SuperFragment {
    TextView workspace = null;      // 작업영역 뷰어
    TextView staffName = null;      // 직원 이름
    Spinner timeSection = null;     // 구역 선택
    TextView workTime = null;       // 근무 시간
    ListView worklog = null;        // 근무 기록

    StaffMainActivity control = null;


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

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();


    }
}
