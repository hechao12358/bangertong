package com.wode.bangertongadmin.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.wode.bangertong.common.entity.UserAudio;
import com.wode.bangertongadmin.mapper.UserAudioMapper;
import com.wode.bangertongadmin.service.UserAudioService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAudioServiceImpl implements UserAudioService {
    @Resource
    UserAudioMapper userAudioMapper;

    @Override
    public boolean initUserAudio(String userId) {
        return false;
    }

    @Override
    public List<Integer> getAudioIds(String userId, String type) {
        return null;
    }

    @Override
    public List<Integer> getAllAudioIds(String userId) {
        boolean success = false; //是否成功获取数据
        int tryNum = 2; //尝试初始化次数
        List<Integer> list = new ArrayList<>();

        while (!success && tryNum >= 0){
            UserAudio userAudio = new UserAudio();
            userAudio.setUserId(userId);
            List<UserAudio> userAudios = userAudioMapper.selectList(Wrappers.query(userAudio));
            if(userAudios.size() > 0){
                userAudios.stream().forEach(item -> {
                    if(!item.getAudioIds().equals("")){
                        String idsStr = item.getAudioIds();
                        String[] split = idsStr.split(",");
                        for (String s : split) {
                            list.add(Integer.valueOf(s));
                        }
                    }
                });
                success = true;
            }else {
                boolean init = initUserAudio(userId); //先初始化
                tryNum--;
            }
        }

        return list;    }

    @Override
    public List<UserAudio> getUserAudioAll(String userId) {
        return null;
    }

    @Override
    public UserAudio getUserAudioByType(String userId, String type) {
        UserAudio userAudio = new UserAudio();
        userAudio.setUserId(userId);
        userAudio.setAudioType(type);
        List<UserAudio> userAudios = userAudioMapper.selectList(Wrappers.query(userAudio));
        if(userAudios.size() > 0){
            return userAudios.get(0);
        }
        return null;
    }

    @Override
    public boolean updateUserAudio(UserAudio userAudio) {
        return SqlHelper.retBool(userAudioMapper.updateById(userAudio));
    }
}
