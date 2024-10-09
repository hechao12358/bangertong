package com.wode.bangertong.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.wode.bangertong.common.constants.WxMiniProgramEnum;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.service.WxMIniProgramService;
import com.wode.bangertong.common.utils.HttpsUtil;
import com.wode.bangertong.common.utils.LockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * 微信小程序服务
 *
 * @author by hc
 */
@Slf4j
@Service
public class WxMIniProgramServiceImpl implements WxMIniProgramService {

    @Resource
    RedisTemplate redisTemplate;
    @Autowired
    LockUtil lockUtil;

    /**
     * 获取用户手机号
     *
     * @return
     */
    @Override
    public Result getUserPhonenumber(String code, Integer type) {
        String access_token = (String) redisTemplate.opsForValue().get("WX_MINI_ACCESS_TOKEN:" + type);
        if (access_token == null) {
            return upadteAccessTokenAndGetPhonrnumberHandle(code, type);
        } else {
            return getPhonenumber(code, access_token, type);
        }
    }

    /**
     * 更新access_token并获取手机号
     *
     * @param code
     * @return
     */
    public Result upadteAccessTokenAndGetPhonrnumberHandle(String code, Integer type) {
        String lock_key = "WX_MINI_ACCESS_TOKEN_LOCK:" + type;
        String access_token = "";
        boolean b = lockUtil.lock(lock_key);
        try {
            //自旋限制，如果锁获取失败，重试10次后返回失败
            int SPIN_LOCK_NUM = 10;
            int i = 0;
            boolean wait = true;
            /**
             * @param retry 记录是否一次性直接获取到锁，如果不是说明已经有人正在更新access_token，
             * 稍后获取到锁后先进行重试
             */
            boolean retry = false;
            do {
                if (b) {
                    if (retry) {
                        access_token = (String) redisTemplate.opsForValue().get("WX_MINI_ACCESS_TOKEN:" + type);
                    } else {
                        access_token = updateAccessToken(type);
                    }
                    Result result = getPhonenumber(code, access_token, type);
                    wait = false;
                    lockUtil.deleteLock(lock_key);
                    return result;
                } else {
                    retry = true;
                }
            } while (wait && (++i) < SPIN_LOCK_NUM);
        } catch (Exception e) {
            System.out.println("---------------");
            System.out.println(e);
        } finally {
            if (b) {
                //删除锁；
                lockUtil.deleteLock(lock_key);
            }
        }
        return new Result(Result.RESULT_OK, "系统繁忙，请稍后再试", new Date().getTime());
    }


    /**
     * 获取手机号
     *
     * @param code
     * @param access_token
     * @return
     */
    public Result getPhonenumber(String code, String access_token, Integer type) {
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("code", code);
        String res = null;
        try {
            res = HttpsUtil.doPost("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + access_token, data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(res);

        log.info(jsonObject.toString());
        if (!jsonObject.getString("errcode").equals("0")) {
            if (jsonObject.getString("errcode").equals("42001")) {
                redisTemplate.delete("WX_MINI_ACCESS_TOKEN:" + type);
            }
            return new Result(Result.RESULT_FAIL, "获取手机号失败", new Date().getTime());
        }

        JSONObject jsonObject1 = (JSONObject) jsonObject.get("phone_info");
        result.put("phone", jsonObject1.getString("phoneNumber"));

        return new Result(Result.RESULT_OK, result, new Date().getTime());
    }

    /**
     * 更新access_token
     *
     * @return
     */
    public String updateAccessToken(Integer type) {
        String appId = WxMiniProgramEnum.values()[type - 1].getAppId();
        String appSecret = WxMiniProgramEnum.values()[type - 1].getAppSecret();
        redisTemplate.delete("WX_MINI_ACCESS_TOKEN:" + type);

        String access_token = "";
        //获取access_token，token有效期2小时
        String res = HttpsUtil.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);

        Date now = new Date();
        JSONObject jsonObject = JSONObject.parseObject(res);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(now);
        rightNow.add(Calendar.HOUR, 2);
        access_token = jsonObject.getString("access_token");

        redisTemplate.opsForValue().set("WX_MINI_ACCESS_TOKEN:" + type, access_token);
        redisTemplate.expireAt("WX_MINI_ACCESS_TOKEN:" + type, rightNow.getTime());

        return access_token;
    }


}
