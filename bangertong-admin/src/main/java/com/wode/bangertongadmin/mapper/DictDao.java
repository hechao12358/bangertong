package com.wode.bangertongadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wode.bangertong.common.entity.Dicts;
import org.springframework.stereotype.Repository;


/**
 * 字典配置表(Dict)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-05 17:02:13
 */
@Repository
public interface DictDao extends BaseMapper<Dicts> {

}

