package com.wode.bangertongadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.dto.UserDTO;
import com.wode.bangertong.common.entity.User;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertongadmin.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户相关接口
 *
 * @author hc
 */
@RestController
@RequestMapping("/bangertong-admin/user/api")
public class UserController {

    @Resource
    UserService userServer;


//    /**
//     * 登出接口
//     *
//     * @param Authorization
//     * @return
//     */
//    @RequestMapping("/mobileLogout")
//    public Result mobileLogout(@RequestHeader String Authorization) {
//        return userServer.mobileLogout(Authorization);
//    }


    /**
     * 查询用户分页列表
     *
     * @param page
     * @param userDTO
     * @return Result
     */
    @RequestMapping("/getPage")
    public Result getPage(Page page, UserDTO userDTO) {
        return userServer.getPage(page, userDTO);
    }
}
