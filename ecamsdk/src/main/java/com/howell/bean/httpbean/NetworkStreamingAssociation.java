package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class NetworkStreamingAssociation {
    int No;
    String url;
    String userName;
    String password;
    boolean TCPTransport;

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
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

    public NetworkStreamingAssociation(int no, String url, String userName, String password, boolean TCPTransport) {
        No = no;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.TCPTransport = TCPTransport;
    }

    public NetworkStreamingAssociation() {
    }

    @Override
    public String toString() {
        return "NetworkStreamingAssociation{" +
                "No=" + No +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", TCPTransport=" + TCPTransport +
                '}';
    }
}
