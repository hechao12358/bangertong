package com.wode.bangertongadmin.service;

import com.wode.bangertong.common.dto.SysUserDTO;
import com.wode.bangertong.common.entity.SysUser;
import com.wode.bangertong.common.model.Result;
import org.springframework.stereotype.Service;

@Service
public interface SysUserService {

    /**
     * 后台用户登录
     * @param sysUser
     * @return
     */
    Result login(SysUser sysUser);

    /**
     * 修改后台用户密码
     * @param sysUserDTO
     * @return
     */
    Result updatePassWord(SysUserDTO sysUserDTO);

    /**
     * 后台用户登出
     * @param token
     * @return
     */
    Result logout(String token);

}
