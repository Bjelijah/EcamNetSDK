package com.howell.bean.httpbean;

import com.howell.protocol.utils.MD5;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/4/6.
 */

public class UserClientCredential {
    String userName;
    String nonce;
    String domain;
    String clientNonce;
    String verifySession;
    String physicalAddress;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getClientNonce() {
        return clientNonce;
    }

    public void setClientNonce(String clientNonce) {
        this.clientNonce = clientNonce;
    }

    public String getVerifySession() {
        return verifySession;
    }

    public void setVerifySession(String verifySession) {
        this.verifySession = verifySession;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }





    public UserClientCredential() {
    }

    public UserClientCredential(String domain,String userName,String password,String serverNonce,String clientNonce) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.userName = userName;
        this.domain = domain;
        this.nonce = serverNonce;
        this.clientNonce = clientNonce;
        String md5 = MD5.getMD5(password);
        String password2 = userName+"@"+domain+":"+serverNonce+":"+clientNonce+":"+md5;
        this.verifySession = MD5.getMD5(password2);
    }

    public UserClientCredential(String domain,String userName,String password,String serverNonce,String clientNonce,String physicalAddress) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.userName = userName;
        this.domain = domain;
        this.nonce = serverNonce;
        this.clientNonce = clientNonce;
        String md5 = MD5.getMD5(password);
        String password2 = userName+"@"+domain+":"+serverNonce+":"+clientNonce+":"+md5;
        this.verifySession = MD5.getMD5(password2);
        this.physicalAddress = physicalAddress;
    }

    @Override
    public String toString() {
        return "UserClientCredential{" +
                "userName='" + userName + '\'' +
                ", nonce='" + nonce + '\'' +
                ", domain='" + domain + '\'' +
                ", clientNonce='" + clientNonce + '\'' +
                ", verifySession='" + verifySession + '\'' +
                ", physicalAddress='" + physicalAddress + '\'' +
                '}';
    }
}
