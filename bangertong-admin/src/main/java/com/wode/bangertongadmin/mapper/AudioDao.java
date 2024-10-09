package com.wode.bangertongadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wode.bangertong.common.entity.Audio;
import org.springframework.stereotype.Repository;


/**
 * 音乐表(Audio)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-05 17:02:12
 */
@Repository
public interface AudioDao extends BaseMapper<Audio> {

}

