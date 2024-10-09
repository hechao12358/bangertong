package com.wode.bangertong.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListenCustomerVO {

    private String id;

    private String imageUrl;

    private String videoUrl;

    private String city;

    private String title;

    private Integer orderNum;

    private Integer status;

    private String createTime;
}
