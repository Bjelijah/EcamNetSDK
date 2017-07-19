package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/6.
 */

public class UserMethodValidation {
    String userName;
    String sessionID;
    String method;
    String url;
    String methodVerifySession;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethodVerifySession() {
        return methodVerifySession;
    }

    public void setMethodVerifySession(String methodVerifySession) {
        this.methodVerifySession = methodVerifySession;
    }

    public UserMethodValidation(String userName, String sessionID, String method, String url, String methodVerifySession) {
        this.userName = userName;
        this.sessionID = sessionID;
        this.method = method;
        this.url = url;
        this.methodVerifySession = methodVerifySession;
    }

    @Override
    public String toString() {
        return "UserMethodValidation{" +
                "userName='" + userName + '\'' +
                ", sessionID='" + sessionID + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", methodVerifySession='" + methodVerifySession + '\'' +
                '}';
    }
}
