package com.wode.bangertong.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserDTO {
    private String phone;

    private String nickname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
