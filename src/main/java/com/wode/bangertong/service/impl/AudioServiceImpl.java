package com.wode.bangertong.service.Impl;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wode.bangertong.dto.AudioTypeDto;
import com.wode.bangertong.entity.Audio;
import com.wode.bangertong.mapper.AudioDao;
import com.wode.bangertong.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
}

