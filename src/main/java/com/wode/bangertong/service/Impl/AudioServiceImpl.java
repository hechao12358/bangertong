package com.wode.bangertong.service.Impl;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wode.bangertong.common.entity.UserAudio;
import com.wode.bangertong.common.constants.RedisContants;
import com.wode.bangertong.common.dto.AudioTypeDto;
import com.wode.bangertong.common.entity.Audio;
import com.wode.bangertong.common.entity.User;
import com.wode.bangertong.mapper.AudioDao;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.service.AudioService;
import com.wode.bangertong.service.UserAudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 音乐表(Audio)表服务实现类
 *
 * @author makejava
 * @since 2024-07-05 17:02:13
 */
@Service
public class AudioServiceImpl extends ServiceImpl<AudioDao, Audio> implements AudioService {


    @Autowired
    private AudioDao audioDao;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    UserAudioService userAudioService;
    /**
     * 查询各分类的音乐
     *
     * @param dto
     * @return
     */
    @Override
    public List<Audio> selectMusicByType(AudioTypeDto dto) {
        IPage<Audio> iPage = new Page<>(dto.getPage(),dto.getSize());
        iPage = audioDao.selectPage(iPage, Wrappers.<Audio>lambdaQuery()
                .eq(Audio::getType, dto.getType())
                .eq(Audio::getStatus,1));
        return iPage.getRecords();
    }

    @Override
    public Result selectAudioByType(String token, String type) {
        List<Audio> audios = new ArrayList<>();
        User user = (User) redisTemplate.opsForValue().get(RedisContants.USER_TOKEN + token);
        if(user == null){
            return new Result(Result.RESULT_NOT_LOGIN, "用户未登录", new Date().getTime());
        }
        List<Integer> audioIds = userAudioService.getAudioIds(user.getId(), type);
        if(audioIds.size() > 0){
           audios = audioDao.selectBatchIds(audioIds);
        }
        return new Result(Result.RESULT_OK, audios, new Date().getTime());
    }

    @Override
    public Result selectMusicAll(String token) {
        User user = (User) redisTemplate.opsForValue().get(RedisContants.USER_TOKEN + token);
        if(user == null){
            return new Result(Result.RESULT_NOT_LOGIN, "用户未登录", new Date().getTime());
        }

        List<Integer> allAudioIds = userAudioService.getAllAudioIds(user.getId());
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
    public Result updateAudioStatus(String token, List<Audio> audios) {
        String type = audios.get(0).getType();
        User user = (User) redisTemplate.opsForValue().get(RedisContants.USER_TOKEN + token);
        if(user == null){
            return new Result(Result.RESULT_NOT_LOGIN, "用户未登录", new Date().getTime());
        }

        String userId = user.getId();
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

