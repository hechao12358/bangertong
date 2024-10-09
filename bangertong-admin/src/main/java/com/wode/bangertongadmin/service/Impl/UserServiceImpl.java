package com.wode.bangertongadmin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.dto.UserDTO;
import com.wode.bangertong.common.entity.User;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertongadmin.mapper.UserMapper;
import com.wode.bangertongadmin.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public Result getPage(Page page, UserDTO userDTO) {
        QueryWrapper<User> query = Wrappers.query(new User());

        if(null != userDTO.getPhone()){
            query.eq("phone",userDTO.getPhone());
        }

        if(null != userDTO.getNickname()){
            query.eq("nickname",userDTO.getPhone());
        }

        if (null != userDTO.getStartTime()) {
            Date startTime = userDTO.getStartTime();
            startTime.setHours(0);
            startTime.setMinutes(0);
            startTime.setSeconds(0);
            query.ge("sys_create_date", startTime);
        }

        if (null != userDTO.getEndTime()) {
            Date endTime = userDTO.getEndTime();
            endTime.setHours(0);
            endTime.setMinutes(0);
            endTime.setSeconds(0);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(endTime);
            calendar.add(calendar.DATE, 1);
            endTime = calendar.getTime();
            query.lt("sys_create_date", endTime);
        }
        Page<User> res = userMapper.selectPage(page, query);

        return new Result(Result.RESULT_OK, res, new Date().getTime());
    }
}
