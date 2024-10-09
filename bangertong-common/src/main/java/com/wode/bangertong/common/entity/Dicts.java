package com.wode.bangertong.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 字典配置表(Dict)表实体类
 *
 * @author makejava
 * @since 2024-07-05 17:02:13
 */
@Data
@TableName("dict")
public class Dicts {
    @TableId("id")
    //主键
    private Integer id;
    //字典类型
    private String type;
    //字典code（唯一）
    private String code;
    //名称
    private String name;
    //排序
    private Integer sorted;
    //是否删除（1是 0 否）
    private Integer isDelete;
}

