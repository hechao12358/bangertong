package com.wode.bangertong.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.wode.bangertong.common.DictContants;
import com.wode.bangertong.entity.Dicts;
import com.wode.bangertong.mapper.DictDao;
import com.wode.bangertong.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典配置表(Dict)表服务实现类
 *
 * @author makejava
 * @since 2024-07-05 17:02:13
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictDao, Dicts> implements DictService {

    @Autowired
    private DictDao dictDao;

    /**
     * 查询音乐分类
     *
     * @return
     */
    @Override
    public List<Dicts> selectMusic() {
        List<Dicts> list = this.list(Wrappers.<Dicts>lambdaQuery()
                .eq(Dicts::getType, DictContants.MUSIC_TYPE)
                .eq(Dicts::getIdDelete, 0));
        if ( null != list && !list.isEmpty()) {
            list = list.stream()
                    .sorted(Comparator.comparing(Dicts::getOrder)).collect(Collectors.toList());
        }
        return list;
    }
}

