package kr.saintdev.projectmna.modules.constant;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-03
 */

public interface HttpURLDefines {
    String TARGET_SERVER = "http://saintdev.kr/mna/";

    String STAFF_AUTH_SIGNUP = TARGET_SERVER + "auth/staff-signup.php";
    String AUTH_LOGIN = TARGET_SERVER + "auth/login.php";
    String AUTO_LOGIN = TARGET_SERVER + "auth/autologin.php";

    String STAFF_REQEUST_INFO = TARGET_SERVER + "staff/staff-info.php";
}
