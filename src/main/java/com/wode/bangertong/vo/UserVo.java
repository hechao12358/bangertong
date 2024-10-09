package com.wode.bangertong.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserVo {
    private String nickname;

    private Integer age;

    private Integer gander;

    private BigDecimal height;

    private BigDecimal weight;
}
