package kr.saintdev.projectmna.modules.staff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.staff.lib.dbm.StaffAccountManager;
import kr.saintdev.projectmna.modules.staff.lib.objects.WorklogObject;
import kr.saintdev.projectmna.views.staff.fragments.main.WorkLogFragment;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-09
 */

public class WorklogAdapter extends BaseAdapter {
    private ArrayList<WorklogObject> works = null;
    private StaffAccountManager accountManager = null;

    public WorklogAdapter(ArrayList<WorklogObject> workObjects, Context context) {
        this.works = workObjects;
        this.accountManager = StaffAccountManager.getInstance(context);
    }

    @Override
    public int getCount() {
        return this.works.size();
    }

    @Override
    public Object getItem(int position) {
        return works.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_worklog, parent, false);
        }

        TextView workLogDate = convertView.findViewById(R.id.layout_worklog_date);          // 날짜
        TextView workLogSignTime = convertView.findViewById(R.id.layout_worklog_time);      // 계약상 근무 시간
        TextView workMoney = convertView.findViewById(R.id.layout_worklog_money);           // 급여
        TextView workLogRealTime = convertView.findViewById(R.id.layout_worklog_realwork);  // 실제 근무 시간
        TextView workLogOkTime = convertView.findViewById(R.id.layout_worklog_okwork);      // 인정된 근무 시간

        WorklogObject work = works.get(position);

        workLogDate.setText(work.getDate());
        workLogSignTime.setText(accountManager.getValue("staff-sign-workstart") + " ~ " + accountManager.getValue("staff-sign-workend"));
        workMoney.setText((Integer.parseInt(accountManager.getValue("staff-money")) * 100)+"");
        workLogRealTime.setText(
                "출근 " + work.getWorkStartTime() +
                        "퇴근 " + work.getWorkEndTime()
        );
        workLogOkTime.setText("0000 분");
        return convertView;
    }
}
