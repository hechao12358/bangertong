package com.wode.bangertongadmin.service;

import com.wode.bangertong.common.entity.UserAudio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserAudioService {

    /**
     * 初始化用户默认音频列表
     * @param userId
     * @return
     */
    boolean initUserAudio(String userId);

    List<Integer> getAudioIds(String userId, String type);

    List<Integer> getAllAudioIds(String userId);

    List<UserAudio> getUserAudioAll(String userId);

    UserAudio getUserAudioByType(String userId, String type);

    boolean updateUserAudio(UserAudio userAudio);


}
