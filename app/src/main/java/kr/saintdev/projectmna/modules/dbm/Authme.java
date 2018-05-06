package kr.saintdev.projectmna.modules.dbm;

import android.content.Context;
import android.content.SharedPreferences;

import kr.saintdev.projectmna.modules.utils.ConstConverter;

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
        commit();
    }

    public void setAuthObject(AuthObject obj) {
        this.editor.putString("user-tel", obj.getTel());
        this.editor.putString("user-name", obj.getName());
        this.editor.putString("user-permiss",
                ConstConverter.getStringFromUserPermission(obj.getPermiss()));
        this.editor.commit();
    }



    public void commit() {
        this.editor.commit();
    }

    public void clear() {
        setUserPin(null);
        this.editor.putString("user-tel", null);
        this.editor.putString("user-name", null);
        this.editor.putString("user-permiss", null);
        this.editor.commit();
    }

    public static class AuthObject {
        private String name = null;
        private String tel = null;
        private UserPermission permiss = null;

        public AuthObject(String name, String tel, UserPermission permiss) {
            this.name = name;
            this.tel = tel;
            this.permiss = permiss;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public UserPermission getPermiss() {
            return permiss;
        }

        public void setPermiss(UserPermission permiss) {
            this.permiss = permiss;
        }
    }

    public enum UserPermission {
        STAFF,  // 직원을 의미 합니다.
        ADMIN   // 운영자를 의미합니다.
    }
}
