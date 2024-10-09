package com.wode.bangertong.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wode.bangertong.service.AudioDefaultService;
import com.wode.bangertong.common.constants.RedisContants;
import com.wode.bangertong.common.entity.AudioDefault;
import com.wode.bangertong.mapper.AudioDefaultMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AudioDefaultServiceImpl implements AudioDefaultService {
    @Resource
    AudioDefaultMapper audioDefaultMapper;
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public List<AudioDefault> getAudioDefaultAll() {
        List<AudioDefault> audioDefaults = (List<AudioDefault>) redisTemplate.opsForValue().get(RedisContants.AUDIO_DEDAULT);
        if(audioDefaults == null){
            audioDefaults = audioDefaultMapper.selectList(Wrappers.query(new AudioDefault()));
            redisTemplate.opsForValue().set(RedisContants.AUDIO_DEDAULT,audioDefaults);
        }
        return audioDefaults;
    }

    @Override
    public String getDefaultAudioIdsByType(String type) {
        AudioDefault audioDefault = new AudioDefault();
        audioDefault.setAudioType(type);
        AudioDefault defaultOne = audioDefaultMapper.selectOne(Wrappers.query(audioDefault));

        return defaultOne.getIds();
    }
}
