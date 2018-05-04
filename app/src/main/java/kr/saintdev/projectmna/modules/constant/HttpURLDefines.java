package kr.saintdev.projectmna.modules.constant;

/**
 * Copyright (c) 2015-2018 Saint software All rights reserved.
 *
 * @Date 2018-05-03
 */

public interface HttpURLDefines {
    String TARGET_SERVER = "http://saintdev.kr/mna/";

    String AUTH_SIGNUP = TARGET_SERVER + "auth/signup.php"; // 회원가입을 처리하는 서버 스크립트
    String AUTH_LOGIN = TARGET_SERVER + "auth/login.php";   // 로그인 처리
    String AUTH_AUTO = TARGET_SERVER + "auth/auth.php";     // 자동 로그인 처리
}
