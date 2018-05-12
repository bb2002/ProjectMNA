package kr.saintdev.projectmna.modules.staff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.common.utils.ConstConverter;
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
        workLogSignTime.setText(
                ConstConverter.dateConverter(accountManager.getValue("staff-sign-workstart")) +
                        " ~ " + ConstConverter.dateConverter(accountManager.getValue("staff-sign-workend")));
        workLogRealTime.setText(
                "출근 " + work.getWorkStartTime() +
                        (work.isNowWorking() ? "~ 근무중 " : "~ 퇴근 " + work.getWorkEndTime())
        );

        if(!work.isNowWorking()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.KOREA);
                Date startTime = sdf.parse(work.getWorkStartTime());
                Date endTime = sdf.parse(work.getWorkEndTime());

                long diff = (endTime.getTime() - startTime.getTime()) / 1000;

                // 인정된 근무시간을 구합니다.
                String workTime = ConstConverter.getHMS((int) diff);
                workLogOkTime.setText("인정됨 : " + workTime);

                // 급여를 구합니다.
                int tenMinPerMoney = Integer.parseInt(accountManager.getValue("staff-money"));
                int length = (int) ((diff/600) * tenMinPerMoney);

                workMoney.setText(length + " 원");
            } catch (ParseException pex) {
                pex.printStackTrace();
            }
        } else {
            workLogOkTime.setText("퇴근하지 않음.");
            workMoney.setText("퇴근안함");
        }

        return convertView;
    }
}
