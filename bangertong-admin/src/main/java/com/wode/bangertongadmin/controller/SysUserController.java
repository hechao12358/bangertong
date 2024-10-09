package com.wode.bangertongadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.dto.SysUserDTO;
import com.wode.bangertong.common.dto.UserDTO;
import com.wode.bangertong.common.entity.SysUser;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertongadmin.service.SysUserService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bangertong-admin/sysUser/api")
public class SysUserController {


    @Resource
    SysUserService sysUserService;

    /**
     * 登录接口
     * @param sysUser
     * @return Result
     */
    @RequestMapping("/login")
    public Result login(SysUser sysUser) {
        return sysUserService.login(sysUser);
    }

    /**
     * 修改密码
     * @param sysUserDTO
     * @return Result
     */
    @RequestMapping("/update")
    public Result updatePassWord(SysUserDTO sysUserDTO) {
        return sysUserService.updatePassWord(sysUserDTO);
    }

    /**
     * 登出接口
     * @param Authorization
     * @return Result
     */
    @RequestMapping("/logout")
    public Result logout(@RequestHeader String Authorization) {
        return sysUserService.logout(Authorization);
    }

}
