//package com.wode.bangertong.common.entity;
//
//import java.util.Date;
//
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.baomidou.mybatisplus.extension.activerecord.Model;
//import lombok.Data;
//
///**
// * 用户下载表(UserUpload)表实体类
// *
// * @author makejava
// * @since 2024-07-06 17:50:46
// */
//
//@Data
//@TableName("user_upload")
//public class UserUpload extends Model<UserUpload> {
//    @TableId("id")
//    //主键
//    private String id;
//    //关联用户id
//    private String userId;
//    //下载音乐名称
//    private String musicName;
//    //音乐文件类型
//    private String fileType;
//    //音乐路径
//    private String musicUrl;
//    //简介
//    private String introduction;
//    //作者
//    private String author;
//    //是否下载成功（0成功 1 失败）
//    private Integer isSucceed;
//    //创建时间
//    private Date createTime;
//    //创建人
//    private String createBy;
//    //修改时间
//    private Date updateTime;
//    //修改人
//    private String updateBy;
//}
//
