package kr.saintdev.projectmna.modules.modules.http.constant;

/**
 * Created by yuuki on 18. 4. 22.
 */

public interface HttpCodes {
    int HTTP_OK = 200;      // 요청 성공

    int HTTP_CLIENT_MISSING_ARGUMENT = 400;     // 인자 값이 부족하다.
    int HTTP_CLIENT_AUTH_ERROR = 401;           // 해당 pin 또는 kakaoid 값이 잘못되었다.
    int HTTP_CLIENT_NO_REQUEST = 0;             // 클라이언트가 요청을 하지 않았다. (대부분 안드로이드 오류)
    int HTTP_INTERNAL_SERVER_ERROR = 500;       // 내부 서버 오류

    int CLIENT_REQUEST_EXCEPTION = 600;         // 클라이언트에서 요청 도중 오류가 발생했다.
    int CLIENT_NOTCONNECT_INTERNET = 601;       // 디바이스가 인터넷에 연결되어 있지 않다.
}
