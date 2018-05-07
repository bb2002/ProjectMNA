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
    String STAFF_GO_WORK = TARGET_SERVER + "staff/staff-go-work.php";   // 출근 처리 스크립트
    String STAFF_GO_HOME = TARGET_SERVER + "staff/staff-go-home.php";   // 퇴근 처리 스크립트
}
