package com.wode.bangertongadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.entity.Banner;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertongadmin.service.BannerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bangertong-admin/banner/api")
public class BannerController {

    @Resource
    BannerService bannerService;

    /**
     * 查询banner分页列表
     *
     * @param page
     * @param banner
     * @return Result
     */
    @RequestMapping("/getPage")
    public Result getPage(Page page, Banner banner) {
        return bannerService.getPage(page, banner);
    }

    /**
     * 添加banner
     * @param banner
     * @return Result
     */
    @PostMapping("/add")
    public Result insertBanner(@RequestBody Banner banner) {
        return bannerService.insertBanner(banner);
    }


    /**
     * 编辑banner
     * @param banner
     * @return Result
     */
    @PostMapping("/edit")
    public Result updateBanner(@RequestBody Banner banner) {
        return bannerService.updateBanner(banner);
    }


    /**
     * 删除banner
     * @param id
     * @return Result
     */
    @GetMapping("/del")
    public Result delCustomer(String id) {
        return bannerService.delBanner(id);
    }


}
