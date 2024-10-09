package com.wode.bangertong.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wode.bangertong.common.entity.Banner;
import com.wode.bangertong.mapper.BannerMapper;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.service.BannerSercive;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BannerSerciveImpl implements BannerSercive {

    @Resource
    BannerMapper bannerMapper;

    @Override
    public Result getBannerList() {
        List<Banner> banners = bannerMapper.selectList(Wrappers.<Banner>query().orderByAsc("order_num"));
        return new Result(Result.RESULT_OK, banners, new Date().getTime());
    }
}
