package kr.saintdev.projectmna.modules.dbm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-03
 * 사용자 계정 정보에 대해 rw 작업을 실행한다.
 */

public class Authme {
    private static Authme instance = null;

    SharedPreferences sharedPrep = null;        // 저장소 (읽기)
    SharedPreferences.Editor editor = null;     // 저장소 (쓰기)

    /**
     *
     * @param context SharedPrep 생성에 필요합니다.
     * @return Authme 객체
     */
    public static Authme getInstance(Context context) {
        if(instance == null) {
            instance = new Authme(context);
        }

        return instance;
    }

    private Authme(Context context) {
        this.sharedPrep = context.getSharedPreferences("mna.authme", Context.MODE_PRIVATE);
        this.editor = this.sharedPrep.edit();
    }

    public String getUserPin() {
        return this.sharedPrep.getString("user-pin", null);
    }

    public void setUserPin(String pin) {
        this.editor.putString("user-pin", pin);
    }
}
