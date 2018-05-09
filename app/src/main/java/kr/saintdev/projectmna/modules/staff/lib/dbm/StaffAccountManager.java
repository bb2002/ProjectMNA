package kr.saintdev.projectmna.modules.staff.lib.dbm;

import android.content.Context;
import android.database.Cursor;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-08
 * Staff Account 에 대한 정보를 저장합니다.
 */

public class StaffAccountManager {
    private static StaffAccountManager instance = null;

    DBHelper dbHelper = null;

    public static StaffAccountManager getInstance(Context context) {
        if(StaffAccountManager.instance == null) {
            StaffAccountManager.instance = new StaffAccountManager(context);
        }

        return StaffAccountManager.instance;
    }

    public StaffAccountManager(Context context) {
        this.dbHelper = new DBHelper(context);
        this.dbHelper.open();
    }

    public void setValue(String k, String value) {
        String sql;

        if(getValue(k) == null) {
            sql = "INSERT INTO mna_account_info (data_key, data_value) VALUES('"+k+"', '"+value+"')";
        } else {
            sql = "UPDATE mna_account_info SET data_key = '"+k+"', data_value = '"+value+"' WHERE data_key = '"+k+"'";
        }

        this.dbHelper.writeQuery(sql);
    }

    public String getValue(String k) {
        String sql = "SELECT * FROM mna_account_info WHERE data_key = '"+k+"'";
        Cursor c = this.dbHelper.readQuery(sql);

        String value = null;
        if(c.moveToNext()) {
            value = c.getString(2);
        }

        c.close();
        return value;
    }
}
