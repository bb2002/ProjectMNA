package kr.saintdev.projectmna.modules.utils;

import kr.saintdev.projectmna.modules.dbm.Authme;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-04
 * 특정 상수 값을 String 형으로 변환 하여 SharedPreperence 에서 사용 할 수 있도록 한다.
 * String 값을 특정 상수로 변환 한다.
 */

public class ConstConverter {
    public static final String getStringFromUserPermission(Authme.UserPermission permiss) {
        switch(permiss) {
            case ADMIN:
                return "permiss.admin";
            case STAFF:
                return "permiss.staff";
            default: return null;
        }
    }

    public static final Authme.UserPermission getUserPermissionFromString(String permiss) {
        switch(permiss) {
            case "permiss.admin":
                return Authme.UserPermission.ADMIN;
            case "permiss.staff":
                return Authme.UserPermission.STAFF;
            default: return null;
        }
    }
}
