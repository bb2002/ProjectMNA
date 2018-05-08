package kr.saintdev.projectmna.views.staff.lib.dbm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-08
 */

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase readable = null;
    private SQLiteDatabase writeable = null;

    public DBHelper(Context context) {
        super(context, "mna", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // DB 를 생성합니다.
        // Key-Value 로 구성된 저장 테이블을 생성합니다.
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE `mna_account_info` (");
        sb.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        sb.append("data_key TEXT,");
        sb.append("data_value TEXT)");

        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void open() {
        this.readable = getReadableDatabase();
        this.writeable = getWritableDatabase();
    }

    public Cursor readQuery(String sql) {
        return this.readable.rawQuery(sql, null);
    }

    public void writeQuery(String sql) {
        this.writeable.execSQL(sql);
    }
}
