package com.wode.bangertong.common.entity;

import java.util.Date;

public class SysUser {
    private String id;

    private String userName;

    private String password;

    private String token;

    private Date sysCreateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getSysCreateTime() {
        return sysCreateTime;
    }

    public void setSysCreateTime(Date sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
    }
}