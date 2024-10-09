package com.wode.bangertong.controller;

import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.service.BannerSercive;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 轮播图接口
 *
 * @author hc
 */
@RestController
@RequestMapping("/bangertong/banner/api")
public class BannerController {

    @Resource
    BannerSercive bannerServer;

    @RequestMapping("/list")
    public Result getBannerList(){
        return bannerServer.getBannerList();
    }

}
