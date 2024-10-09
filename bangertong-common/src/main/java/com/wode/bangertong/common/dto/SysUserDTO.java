package com.wode.bangertong.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SysUserDTO {

    private String userName;

    private String newPassword;

    private String oldPassword;
}
