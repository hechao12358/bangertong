package com.wode.bangertongadmin.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.wode.bangertong.common.entity.Banner;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertongadmin.mapper.BannerMapper;
import com.wode.bangertongadmin.service.BannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BannerServiceImpl implements BannerService {

    @Resource
    BannerMapper bannerMapper;

    @Override
    public Result getPage(Page page, Banner banner) {
        Page res = bannerMapper.selectPage(page, Wrappers.query(banner));
        return new Result(Result.RESULT_OK, res, new Date().getTime());
    }

    @Override
    public Result insertBanner(Banner banner) {
        int insert = bannerMapper.insert(banner);
        return new Result(Result.RESULT_OK, SqlHelper.retBool(insert) ? "成功" : "失败", new Date().getTime());
    }

    @Override
    public Result updateBanner(Banner banner) {
        int insert = bannerMapper.updateById(banner);
        return new Result(Result.RESULT_OK, SqlHelper.retBool(insert) ? "成功" : "失败", new Date().getTime());
    }

    @Override
    public Result delBanner(String id) {
        int delete = bannerMapper.deleteById(id);
        return new Result(Result.RESULT_OK, SqlHelper.retBool(delete) ? "成功" : "失败", new Date().getTime());
    }
}
