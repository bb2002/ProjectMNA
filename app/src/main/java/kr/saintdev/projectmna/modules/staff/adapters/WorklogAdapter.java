package kr.saintdev.projectmna.modules.staff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.staff.lib.objects.WorklogObject;
import kr.saintdev.projectmna.views.staff.fragments.main.WorkLogFragment;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-09
 */

public class WorklogAdapter extends BaseAdapter {
    private ArrayList<WorklogObject> works = null;

    public WorklogAdapter(ArrayList<WorklogObject> workObjects) {
        this.works = workObjects;
    }

    @Override
    public int getCount() {
//        return this.works.size();
        return 8;
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

        return convertView;
    }
}
