package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/5.
 */

public class UserNonce {
    String nonce;
    String domain;

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

    public UserNonce(String nonce, String domain) {
        this.nonce = nonce;
        this.domain = domain;
    }

    public UserNonce() {
    }

    @Override
    public String toString() {
        return "UserNonce{" +
                "nonce='" + nonce + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
