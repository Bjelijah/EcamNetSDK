package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/6.
 */

public class UserTeardownCredential {
    String userName;
    String sessionID;
    String teardownReason;

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

    public String getTeardownReason() {
        return teardownReason;
    }

    public void setTeardownReason(String teardownReason) {
        this.teardownReason = teardownReason;
    }

    public UserTeardownCredential(String userName, String sessionID, String teardownReason) {
        this.userName = userName;
        this.sessionID = sessionID;
        this.teardownReason = teardownReason;
    }

    public UserTeardownCredential(String userName, String sessionID) {
        this.userName = userName;
        this.sessionID = sessionID;
    }

    @Override
    public String toString() {
        return "UserTeardownCredential{" +
                "userName='" + userName + '\'' +
                ", sessionID='" + sessionID + '\'' +
                ", teardownReason='" + teardownReason + '\'' +
                '}';
    }
}
