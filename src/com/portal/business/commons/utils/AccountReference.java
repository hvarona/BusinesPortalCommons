package com.portal.business.commons.utils;

import java.io.Serializable;

/**
 *
 * @author lromero
 */
public class AccountReference implements Serializable {

    String login;
    String password;
    String ipRemoteAddress;

    public AccountReference() {
    }

    public AccountReference(String login, String password,String ipRemoteAddress) {
        this.login = login;
        this.password = password;
        this.ipRemoteAddress = ipRemoteAddress;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpRemoteAddress() {
        return ipRemoteAddress;
    }

    public void setIpRemoteAddress(String ipRemoteAddress) {
        this.ipRemoteAddress = ipRemoteAddress;
    }
}
