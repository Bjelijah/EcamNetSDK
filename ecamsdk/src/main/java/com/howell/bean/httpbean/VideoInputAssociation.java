package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class VideoInputAssociation {
    String fromId;
    String url;
    String userName;
    String password;
    boolean TCPTransport;

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTCPTransport() {
        return TCPTransport;
    }

    public void setTCPTransport(boolean TCPTransport) {
        this.TCPTransport = TCPTransport;
    }

    public VideoInputAssociation(String fromId, String url, String userName, String password, boolean TCPTransport) {
        this.fromId = fromId;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.TCPTransport = TCPTransport;
    }

    public VideoInputAssociation() {
    }

    @Override
    public String toString() {
        return "VideoInputAssociation{" +
                "fromId='" + fromId + '\'' +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", TCPTransport=" + TCPTransport +
                '}';
    }
}
