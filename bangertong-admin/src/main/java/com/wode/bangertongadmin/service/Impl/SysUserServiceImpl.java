package com.wode.bangertongadmin.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wode.bangertong.common.constants.RedisContants;
import com.wode.bangertong.common.dto.SysUserDTO;
import com.wode.bangertong.common.entity.SysUser;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.common.utils.JwtUtil;
import com.wode.bangertong.common.utils.PassWordUtil;
import com.wode.bangertongadmin.mapper.SysUserMapper;
import com.wode.bangertongadmin.service.SysUserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    private static final String KEY = "Bang-Er-Tong";

    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public Result login(SysUser sysUser) {
        SysUser query = new SysUser();
        query.setUserName(sysUser.getUserName());
        List<SysUser> sysUsers = sysUserMapper.selectList(Wrappers.query(query));
        if (!sysUsers.isEmpty()) {
            if (PassWordUtil.matches(sysUser.getPassword(),sysUsers.get(0).getPassword())) {
                String token = "";
                // 生成token
                try {
                    token = JwtUtil.createJWT(sysUsers.get(0).getId());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                redisTemplate.opsForValue().set(RedisContants.SYS_USER_TOKEN + token, sysUsers.get(0), 30, TimeUnit.DAYS);
                return new Result(Result.RESULT_OK, token, new Date().getTime());
            }
            return new Result(Result.RESULT_ERROR, "密码错误", new Date().getTime());
        }
        return new Result(Result.RESULT_ERROR, "账号不存在", new Date().getTime());
    }

    @Override
    public Result updatePassWord(SysUserDTO sysUserDTO) {
        SysUser query = new SysUser();
        query.setUserName(sysUserDTO.getUserName());
        List<SysUser> sysUsers = sysUserMapper.selectList(Wrappers.query(query));
        if (!sysUsers.isEmpty()) {
            if (PassWordUtil.matches(sysUserDTO.getOldPassword(),sysUsers.get(0).getPassword())) {
                //TODO 开始修改密码

                //对新密码加密
                String newPasswordEncode = PassWordUtil.encode(sysUserDTO.getNewPassword());

                //保存新密码
                SysUser sysUser = sysUsers.get(0);
                sysUser.setPassword(newPasswordEncode);
                sysUserMapper.updateById(sysUser);

                //移除redis登录记录
                redisTemplate.delete(RedisContants.SYS_USER_TOKEN + sysUser.getToken());

                return new Result(Result.RESULT_OK, "密码更新成功请重新登录", new Date().getTime());
            }
            return new Result(Result.RESULT_ERROR, "初始密码错误", new Date().getTime());
        }
        return new Result(Result.RESULT_ERROR, "用户不存在", new Date().getTime());
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete(RedisContants.SYS_USER_TOKEN + token);
        return new Result(Result.RESULT_OK, "登出成功！", new Date().getTime());
    }
}
