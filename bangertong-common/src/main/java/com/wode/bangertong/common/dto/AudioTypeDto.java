package com.wode.bangertong.common.dto;

import lombok.Data;

/**
 * @Author：lilulu
 * @Date：2024/7/5 22:27
 * @Filename：AudioTypeDto
 * @Description：
 */
@Data
public class AudioTypeDto {
    private String type;
    private Integer page=1;
    private Integer size=10;
}
