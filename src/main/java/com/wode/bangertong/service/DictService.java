package com.wode.bangertong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wode.bangertong.entity.Dicts;

import java.util.List;


/**
 * 字典配置表(Dict)表服务接口
 *
 * @author makejava
 * @since 2024-07-05 17:02:13
 */
public interface DictService extends IService<Dicts> {

    /**
     * 查询音乐分类
     * @return
     */
    List<Dicts> selectMusic();

}

