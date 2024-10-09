package com.wode.bangertongadmin.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wode.bangertong.common.constants.RedisContants;
import com.wode.bangertong.common.dto.AudioDTO;
import com.wode.bangertong.common.dto.AudioTypeDto;
import com.wode.bangertong.common.entity.Audio;
import com.wode.bangertong.common.entity.User;
import com.wode.bangertong.common.entity.UserAudio;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertongadmin.mapper.AudioDao;
import com.wode.bangertongadmin.service.AudioService;
import com.wode.bangertongadmin.service.UserAudioService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AudioServiceImpl implements AudioService {

    @Resource
    UserAudioService userAudioService;
    @Resource
    AudioDao audioDao;
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public List<Audio> selectMusicByType(AudioTypeDto dto) {
        return null;
    }

    @Override
    public Result selectAudioByType(String token, String type) {
        return null;
    }

    @Override
    public Result selectMusicAllByUserId(String userId) {
        List<Integer> allAudioIds = userAudioService.getAllAudioIds(userId);
        List<Audio> audios = audioDao.selectList(Wrappers.query(new Audio()));

        audios.stream().forEach(item ->{
            if(allAudioIds.contains(item.getId())){
                item.setStatus(1);
            }else {
                item.setStatus(0);
            }
        });
        return new Result(Result.RESULT_OK, audios, new Date().getTime());
    }

    @Override
    public Result updateAudioStatus(AudioDTO audioDTO) {
        List<Audio> audios = audioDTO.getAudios();
        String type = audios.get(0).getType();
        String userId = audioDTO.getUserId();

        UserAudio userAudioByType = userAudioService.getUserAudioByType(userId, type);
        if (null != userAudioByType) {
            String idsStr = "";
            List<Integer> idsList = new ArrayList<>();
            audios.stream().filter(item -> item.getStatus() == 1)
                    .forEach(item ->{
                        idsList.add(item.getId());
                    });
            idsStr = idsList.toString().replace(" ","").replace("[","").replace("]","");
            userAudioByType.setAudioIds(idsStr);
            boolean b = userAudioService.updateUserAudio(userAudioByType);
            redisTemplate.opsForValue().set(RedisContants.AUDIO_USER + userId + ":" + userAudioByType.getAudioType(),idsStr);

            return new Result(Result.RESULT_OK, "保存成功", new Date().getTime());
        }

        return new Result(Result.RESULT_FAIL, "错误", new Date().getTime());
    }
}
