package kr.saintdev.projectmna.views.staff.fragments.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.views.common.SuperFragment;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-06
 */

public class SettingsFragment extends SuperFragment {
    TextView telView = null;
    TextView nameView = null;
    CircleImageView profileView = null;

    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmn_staff_main_settings, container, false);

        this.telView = v.findViewById(R.id.main_settings_content_tel_view);
        this.nameView = v.findViewById(R.id.main_settings_content_name_view);
        this.profileView = v.findViewById(R.id.main_settings_profile_icon);

        return v;
    }
}
