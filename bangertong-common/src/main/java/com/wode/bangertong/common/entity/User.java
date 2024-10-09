package com.wode.bangertong.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class User {
    private String id;

    private String phone;

    private String password;

    private String clientId;

    private String deviceId;

    private Integer loginType;

    private Integer userType;

    private Integer state;

    private String appToken;

    private Long paterId;

    private Date sysCreateDate;

    private Date sysLastUpdate;

    private Integer first;

    private Integer deviceType;

    private Integer source;

    private Integer insider;

    private String nickname;

    private Integer age;

    private Integer gander;

    private BigDecimal height;

    private BigDecimal weight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken == null ? null : appToken.trim();
    }

    public Long getPaterId() {
        return paterId;
    }

    public void setPaterId(Long paterId) {
        this.paterId = paterId;
    }

    public Date getSysCreateDate() {
        return sysCreateDate;
    }

    public void setSysCreateDate(Date sysCreateDate) {
        this.sysCreateDate = sysCreateDate;
    }

    public Date getSysLastUpdate() {
        return sysLastUpdate;
    }

    public void setSysLastUpdate(Date sysLastUpdate) {
        this.sysLastUpdate = sysLastUpdate;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getInsider() {
        return insider;
    }

    public void setInsider(Integer insider) {
        this.insider = insider;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGander() {
        return gander;
    }

    public void setGander(Integer gander) {
        this.gander = gander;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}