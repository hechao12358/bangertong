package com.wode.bangertongadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wode.bangertong.common.dto.AudioDTO;
import com.wode.bangertong.common.dto.AudioTypeDto;
import com.wode.bangertong.common.entity.Audio;
import com.wode.bangertong.common.model.Result;

import java.util.List;


/**
 * 音乐表(Audio)表服务接口
 *
 * @author makejava
 * @since 2024-07-05 17:02:13
 */
public interface AudioService {

    /**
     * 查询各分类的音乐
     * @param dto
     * @return
     */
    List<Audio> selectMusicByType(AudioTypeDto dto);

    Result selectAudioByType(String token, String type);

    /**
     * 查询所有音乐、
     * @return
     */
    Result selectMusicAllByUserId(String userId);

    /**
     * 音药调整
     * @return
     */
    Result updateAudioStatus(AudioDTO dto);
}

