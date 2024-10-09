package com.wode.bangertong.controller;

import com.wode.bangertong.common.entity.User;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.service.UserService;
import com.wode.bangertong.service.WxMIniProgramService;
import com.wode.bangertong.common.utils.IPUtil;
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
@RequestMapping("/bangertong/user/api")
public class UserController {

    @Resource
    UserService userServer;
    @Resource
    WxMIniProgramService wxMIniProgramServer;

    /**
     * 登录接口
     *
     * @param request
     * @return
     */

    @RequestMapping("/mobileLogin")
    public Result mobileLogin(HttpServletRequest request, String phone, String code, String clientId) {
        String ip = IPUtil.getIp(request);
        return userServer.mobileLogin(0, phone, code, "", clientId, 0, ip, 0, 0, 0, 0);
    }

    /**
     * 微信小程序获取手机号接口
     *
     * @param code
     * @return
     */

    @RequestMapping("/getUserPhonenumber")
    public Result getUserPhonenumber(String code){
        return userServer.getUserPhonenumber(code);
    }

    /**
     * 微信小程序获取手机号接口(新)
     *
     * @param code
     * @return
     */

    @RequestMapping("/getUserPhonenumberNew")
    public Result getUserPhonenumberNew(String code,Integer type){
        return wxMIniProgramServer.getUserPhonenumber(code, type);
    }


    /**
     * 微信小程序快捷登录接口
     *
     * @param request
     * @return
     */

    @RequestMapping("/miniprogramQuickLogin")
    public Result miniprogramQuickLogin(HttpServletRequest request, String phone, Integer source) {
        String ip = IPUtil.getIp(request);
        return userServer.mobileLogin(1, phone, "", "", "", 0, ip, 0, 0, 0, source);
    }

    /**
     * 微信小程序登录
     *
     * @param request
     * @param source -> 0:小程序注册用户
     * @return
     */

    @RequestMapping("/miniprogramLogin")
    public Result miniprogramLogin(HttpServletRequest request, String phone, String code, String clientId, Integer source) {
        String ip = IPUtil.getIp(request);
        return userServer.mobileLogin(0, phone, code, "", clientId, 0, ip, 0, 0, 0, source);
    }

    /**
     * 【渠道】登录接口
     *
     * @param request
     * @return
     */

    @RequestMapping("/channelMobileLogin")
    public Result channelMobileLogin(HttpServletRequest request, String phone, String code, String clientId, Integer source) {
        String ip = IPUtil.getIp(request);
        return userServer.mobileLogin(0, phone, code, "", clientId, 0, ip, 0, 0, 0, source);
    }


    /**
     * 登出接口
     *
     * @param Authorization
     * @return
     */
    @RequestMapping("/mobileLogout")
    public Result mobileLogout(@RequestHeader String Authorization) {
        return userServer.mobileLogout(Authorization);
    }

    /**
     * 设置用户CID
     *
     * @param Authorization
     * @return Result
     */
    @RequestMapping("/setClientId")
    public Result setClientId(@RequestHeader String Authorization, String clientId) {
        return userServer.setClientId(Authorization, clientId);
    }

    /**
     * 获取用户信息
     *
     * @param Authorization
     * @return Result
     */
    @RequestMapping("/getInfo")
    public Result getInfo(@RequestHeader String Authorization) {
        return userServer.getInfo(Authorization);
    }

    /**
     * 更新用户信息
     *
     * @param Authorization
     * @return Result
     */
    @RequestMapping("/update")
    public Result update(@RequestHeader String Authorization, @RequestBody User user) {
        return userServer.update(Authorization, user);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return Result
     */
    @RequestMapping("/matches")
    public Result matches(String password) {
        return userServer.matches(password);
    }
}
