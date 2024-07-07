package com.wode.bangertong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wode.bangertong.dto.AudioTypeDto;
import com.wode.bangertong.entity.Audio;

import java.util.List;


/**
 * 音乐表(Audio)表服务接口
 *
 * @author makejava
 * @since 2024-07-05 17:02:13
 */
public interface AudioService extends IService<Audio> {

    /**
     * 查询各分类的音乐
     * @param dto
     * @return
     */
    List<Audio> selectMusicByType(AudioTypeDto dto);
}

