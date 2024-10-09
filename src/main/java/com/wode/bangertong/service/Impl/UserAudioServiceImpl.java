package com.wode.bangertong.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.wode.bangertong.common.entity.UserAudio;
import com.wode.bangertong.service.AudioDefaultService;
import com.wode.bangertong.common.constants.RedisContants;
import com.wode.bangertong.common.entity.AudioDefault;
import com.wode.bangertong.mapper.UserAudioMapper;
import com.wode.bangertong.service.UserAudioService;
import com.wode.bangertong.common.utils.DateTimeUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAudioServiceImpl implements UserAudioService {
    @Resource
    UserAudioMapper userAudioMapper;
    @Resource
    AudioDefaultService audioDefaultService;
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public boolean initUserAudio(String userId) {
        List<AudioDefault> audioDefaultAll = audioDefaultService.getAudioDefaultAll();
        if(audioDefaultAll == null){
            return false;
        }
        audioDefaultAll.stream().forEach(audioDefault ->{
            UserAudio userAudio = new UserAudio();
            userAudio.setUserId(userId);
            userAudio.setAudioIds(audioDefault.getIds());
            userAudio.setAudioType(audioDefault.getAudioType());
            userAudio.setUpdateTime(DateTimeUtils.getCurrentDate());
            userAudioMapper.insert(userAudio);
            redisTemplate.opsForValue().set(RedisContants.AUDIO_USER + userId + ":" + audioDefault.getAudioType(),audioDefault.getIds());
        });

        return true;
    }

    @Override
    public List<Integer> getAudioIds(String userId, String type) {
        boolean success = false; //是否成功获取数据
        int tryNum = 2; //尝试初始化次数
        List<Integer> list = new ArrayList<>();
        String idsStr = (String) redisTemplate.opsForValue().get(RedisContants.AUDIO_USER + userId + ":" + type);
        if(idsStr == null){
            while (!success && tryNum >= 0){
                UserAudio userAudio = new UserAudio();
                userAudio.setUserId(userId);
                userAudio.setAudioType(type);
                List<UserAudio> userAudios = userAudioMapper.selectList(Wrappers.query(userAudio));
                if(userAudios.size() > 0){
                    idsStr = userAudios.get(0).getAudioIds();
                    success = true;
                }else {
                    boolean init = initUserAudio(userId); //先初始化
                    tryNum--;

                }
            }
           redisTemplate.opsForValue().set(RedisContants.AUDIO_USER + userId + ":" + type,idsStr);

       }
        if (!idsStr.equals("")) {
            String[] split = idsStr.split(",");
            for (String s : split) {
                list.add(Integer.valueOf(s));
            }
        }

        return list;
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

        return list;
    }

    @Override
    public List<UserAudio> getUserAudioAll(String userId) {
        UserAudio userAudio = new UserAudio();
        userAudio.setUserId(userId);
        List<UserAudio> userAudios = userAudioMapper.selectList(Wrappers.query(userAudio));

        return userAudios;
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
