package kr.saintdev.projectmna.modules.common.modules.http.constant;

/**
 * Created by 5252b on 2018-03-23.
 */

public interface HttpUrlDefines {
    String TARGET_SERVER = "http://saintdev.kr/screentran/";

    String SECURE_CREATE_ACCOUNT = TARGET_SERVER + "secure/join.php";
    String SECURE_AUTHME_ACCOUNT = TARGET_SERVER + "secure/auth.php";

    String DEFAULT_PROFILE_ICON = TARGET_SERVER + "cdn/default.png";
}
