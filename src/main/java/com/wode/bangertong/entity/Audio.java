package com.wode.bangertong.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 音乐表(Audio)表实体类
 *
 * @author makejava
 * @since 2024-07-05 17:02:12
 */
@Data
@TableName("audio")
public class Audio extends Model<Audio> {

    @TableId("id")
    private Integer id;
    //地址
    private String url;
    //名称
    private String name;
    //简介
    private String introduction;
    //类型（关联字典表code）
    private String type;
    //状态：1显示，0隐藏
    private Integer status;
    //作者
    private String author;
}

