package kr.saintdev.projectmna.views.staff.fragments.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.saintdev.projectmna.R;
import kr.saintdev.projectmna.modules.staff.lib.dbm.StaffAccountManager;
import kr.saintdev.projectmna.views.common.SuperFragment;
import kr.saintdev.projectmna.views.common.dialogs.main.InputTextDialog;
import kr.saintdev.projectmna.views.staff.activitys.StaffAuthActivity;
import kr.saintdev.projectmna.views.staff.activitys.StaffMainActivity;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-06
 */

public class SettingsFragment extends SuperFragment {
    TextView telView = null;
    TextView nameView = null;

    RelativeLayout[] items = null;
    CircleImageView profileView = null;
    InputTextDialog nameDialog = null;
    InputTextDialog telDialog = null;

    StaffMainActivity control = null;
    StaffAccountManager staffAccount = null;

    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmn_staff_main_settings, container, false);
        this.control = (StaffMainActivity) getActivity();

        this.telView = v.findViewById(R.id.main_settings_content_tel_view);
        this.nameView = v.findViewById(R.id.main_settings_content_name_view);
        this.profileView = v.findViewById(R.id.main_settings_profile_icon);

        this.nameDialog= new InputTextDialog(control, "");
        this.telDialog = new InputTextDialog(control, "");
        this.nameDialog.setOnDismissListener(new OnDialogDismissHandler(R.id.main_settings_content_name));
        this.telDialog.setOnDismissListener(new OnDialogDismissHandler(R.id.main_settings_content_tel));
        this.nameDialog.setText("이름 변경");
        this.telDialog.setText("전화번호 변경");

        this.items = new RelativeLayout[]{
                v.findViewById(R.id.main_settings_content_name),
                v.findViewById(R.id.main_settings_content_tel),
                v.findViewById(R.id.main_settings_content_passwd)
        };

        this.staffAccount = StaffAccountManager.getInstance(control);

        OnButtonClickHandler handler = new OnButtonClickHandler();
        for(RelativeLayout i : this.items) {
            i.setOnClickListener(handler);
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.nameView.setText(this.staffAccount.getValue("staff-name"));
        this.telView.setText(this.staffAccount.getValue("staff-tel"));
    }

    class OnButtonClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.main_settings_content_tel:
                    telDialog.setText(staffAccount.getValue("staff-tel"));
                    telDialog.show();
                    break;
                case R.id.main_settings_content_name:
                    nameDialog.setText(staffAccount.getValue("staff-name"));
                    nameDialog.show();
                    break;
                case R.id.main_settings_content_passwd:
                    break;
            }
        }
    }

    class OnDialogDismissHandler implements Dialog.OnDismissListener {
        int id = 0;

        public OnDialogDismissHandler(int id) {
            this.id = id;
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            if(id == R.id.main_settings_content_tel) {
                staffAccount.setValue("staff-tel", telDialog.getData());
                telView.setText(telDialog.getData());
            } else if(id == R.id.main_settings_content_name) {
                staffAccount.setValue("staff-name", nameDialog.getData());
                nameView.setText(nameDialog.getData());
            }
        }
    }
}
