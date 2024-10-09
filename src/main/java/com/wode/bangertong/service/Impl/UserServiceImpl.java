package com.wode.bangertong.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.wode.bangertong.common.utils.*;
import com.wode.bangertong.service.UserService;
import com.wode.bangertong.common.constants.RedisContants;
import com.wode.bangertong.common.entity.Password;
import com.wode.bangertong.mapper.PasswordMapper;
import com.wode.bangertong.mapper.UserMapper;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.service.UserAudioService;
import com.wode.bangertong.common.entity.User;
import com.wode.bangertong.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户账号
 *
 * @author by hc
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    RedisTemplate redisTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordMapper passwordMapper;

    @Autowired
    LockUtil lockUtil;

    @Resource
    UserAudioService userAudioService;

    /**
     * 获取用户手机号
     *
     * @return
     */
    @Override
    public Result getUserPhonenumber(String code) {
        String access_token = (String) redisTemplate.opsForValue().get("WX_MINI_ACCESS_TOKEN");
        if (access_token == null) {
            return upadteAccessTokenAndGetPhonrnumberHandle(code);
        } else {
            return getPhonenumber(code, access_token);
        }
    }

    /**
     * 更新access_token并获取手机号
     *
     * @param code
     * @return
     */
    public Result upadteAccessTokenAndGetPhonrnumberHandle(String code) {
        String lock_key = "WX_MINI_ACCESS_TOKEN_LOCK";
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
                    if (retry){
                        access_token = (String) redisTemplate.opsForValue().get("WX_MINI_ACCESS_TOKEN");
                    }else {
                        access_token = updateAccessToken();
                    }
                    Result result = getPhonenumber(code, access_token);
                    wait = false;
                    lockUtil.deleteLock(lock_key);
                    return result;
                }else {
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
    public Result getPhonenumber(String code, String access_token) {
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
                redisTemplate.delete("WX_MINI_ACCESS_TOKEN");
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
    public String updateAccessToken() {
        redisTemplate.delete("WX_MINI_ACCESS_TOKEN");

        String access_token = "";
        //获取access_token，token有效期2小时
        String res = HttpsUtil.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxe6ab41c58d5f6688&secret=df550a006fc9bd558814997bfcd1f728");

        Date now = new Date();
        JSONObject jsonObject = JSONObject.parseObject(res);

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(now);
        rightNow.add(Calendar.HOUR, 2);
        access_token = jsonObject.getString("access_token");

        redisTemplate.opsForValue().set("WX_MINI_ACCESS_TOKEN", access_token);
        redisTemplate.expireAt("WX_MINI_ACCESS_TOKEN", rightNow.getTime());

        return access_token;
    }

    public Result mobileLogin(Integer loginType, String phone, String code, String deviceId, String clientId, Integer channel, String ip, Integer deviceType, Integer appType, Integer version, Integer source) {
        JSONObject result = new JSONObject();
        Date now = DateTimeUtils.getCurrentTimestamp();
        try {
//            if (loginType != 1) {
//                log.info("用户输入code：【" + code + "】");
//                //获取验证码
//                String vc = (String) redisTemplate.opsForValue().get(RedisContants.VERIFY_CODE_KEY + phone);
//                log.info("后台缓存code：【" + vc + "】");
//
//                // 是否获取过验证码
//                if (vc == null || !vc.equals(code)) {
//                    return new Result(Result.RESULT_ERROR, "验证码错误", new Date().getTime());
//                }
//            }

            // 生成token
            String token = TokenGenerator.getUUIDToken();

            // 获取账号信息
            User user = new User();
            user.setPhone(phone);
            user = userMapper.selectOne(Wrappers.query(user));

            if (null == user) {
                //创建账号
                String id = IdWorker.getIdStr();
                user = new User();
                user.setId(id);
                user.setPhone(phone);
                user.setAppToken(token);
                user.setSysCreateDate(now);
                user.setSysLastUpdate(now);
                int insert = userMapper.insert(user);
                if (SqlHelper.retBool(insert)) {
                    boolean b = userAudioService.initUserAudio(id);
                    log.info("保存user信息成功！");
                }
            } else {
                if (user.getAppToken() != null && !user.getAppToken().equals("")) {
                    token = user.getAppToken();
                } else {
                    user.setAppToken(token);
                }
                user.setClientId(clientId);
                user.setSysLastUpdate(now);

                userMapper.updateById(user);
            }

            redisTemplate.opsForValue().set(RedisContants.USER_TOKEN + token,user);

            result.put("id", user.getId());
            result.put("clientId", clientId);
            result.put("deviceType", deviceType);
            result.put("deviceId", deviceId);
            result.put("phone", user.getPhone());
            result.put("loginType", user.getLoginType());
            result.put("state", user.getState());
            result.put("userType", user.getUserType());
            result.put("channel", channel);
            result.put("first", user.getFirst());
            result.put("appToken", token);
            result.put("insider", user.getInsider());

            return new Result(Result.RESULT_OK, result, new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Result.RESULT_ERROR, "登录失败", new Date().getTime());
        }
    }

    /**
     * 登出
     *
     * @param token
     * @return
     */
    public Result mobileLogout(String token) {
        try {
            User userRedis = (User) redisTemplate.opsForValue().get(RedisContants.USER_TOKEN + token);

            if (userRedis == null) {
                return new Result("user not login");
            }
            redisTemplate.delete(RedisContants.USER_TOKEN + token);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return new Result(Result.RESULT_OK, "ok", new Date().getTime());
    }

    public Result setClientId(String token, String clientId) {

        User userinfoRedis = (User) redisTemplate.opsForValue().get(RedisContants.USER_TOKEN + token);

        if (userinfoRedis == null) {
            return new Result(Result.RESULT_NOT_LOGIN, "用户未登录", new Date().getTime());
        }

        User user = new User();
        user.setId(userinfoRedis.getId());
        user.setClientId(clientId);

        int i = userMapper.updateById(user);
        if (SqlHelper.retBool(i)) {
            return new Result(Result.RESULT_OK, "成功", new Date().getTime());
        }
        return new Result(Result.RESULT_ERROR, "失败", new Date().getTime());

    }


    public String getClientId(String userId) {

        User user = userMapper.selectById(userId);
        if (user == null) {
            return "";
        }
        return user.getClientId();
    }

    public String getPhone(String userId) {

        User user = userMapper.selectById(userId);
        if (user == null) {
            return "";
        }
        return user.getPhone();
    }

    public String getUserId(String phone) {

        User user = new User();
        user.setPhone(phone);
        List<User> users = userMapper.selectList(Wrappers.query(user));
        if(users.size() > 0 ){
            return users.get(0).getId();
        }
        return "";
    }

    @Override
    public Result getInfo(String token) {
        UserVo userVo = new UserVo();
        User userinfoRedis = (User) redisTemplate.opsForValue().get(RedisContants.USER_TOKEN + token);
        BeanUtil.copyProperties(userinfoRedis, userVo);
        return new Result(Result.RESULT_OK, userVo, new Date().getTime());
    }

    @Override
    public Result update(String token, User user) {
        User userinfoRedis = (User) redisTemplate.opsForValue().get(RedisContants.USER_TOKEN + token);
        userinfoRedis.setAge(user.getAge());
        userinfoRedis.setNickname(user.getNickname());
        userinfoRedis.setHeight(user.getHeight());
        userinfoRedis.setWeight(user.getWeight());
        userinfoRedis.setGander(user.getGander());

        int i = userMapper.updateById(userinfoRedis);
        if (SqlHelper.retBool(i)) {
            redisTemplate.opsForValue().set(RedisContants.USER_TOKEN + token,userinfoRedis);
            return new Result(Result.RESULT_OK, "保存成功", new Date().getTime());

        }
        return new Result(Result.RESULT_FAIL, "保存失败", new Date().getTime());
    }

    @Override
    public Result matches(String password) {
        List<Password> passwords = passwordMapper.selectList(Wrappers.query(new Password()));
        if(passwords.size() > 0){
            boolean matches = PassWordUtil.matches(password, passwords.get(0).getPassword());
            return new Result(Result.RESULT_OK, matches, new Date().getTime());
        }
        return new Result(Result.RESULT_FAIL, "错误", new Date().getTime());
    }

}
