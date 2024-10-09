package com.wode.bangertong.common.entity;

public class AudioDefault {
    private String audioType;

    private String ids;

    public String getAudioType() {
        return audioType;
    }

    public void setAudioType(String audioType) {
        this.audioType = audioType == null ? null : audioType.trim();
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids == null ? null : ids.trim();
    }
}