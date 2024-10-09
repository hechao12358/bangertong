package com.wode.bangertong.common.entity;

import java.util.Date;

public class UserAudio {

    private String id;
    private String userId;

    private String audioIds;

    private String audioType;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAudioIds() {
        return audioIds;
    }

    public void setAudioIds(String audioIds) {
        this.audioIds = audioIds == null ? null : audioIds.trim();
    }

    public String getAudioType() {
        return audioType;
    }

    public void setAudioType(String audioType) {
        this.audioType = audioType == null ? null : audioType.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}