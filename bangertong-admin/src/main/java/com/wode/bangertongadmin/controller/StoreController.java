package com.wode.bangertongadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.entity.Banner;
import com.wode.bangertong.common.entity.Store;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertongadmin.service.BannerService;
import com.wode.bangertongadmin.service.StoreService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *门店管理
 */
@RestController
@RequestMapping("/bangertong-admin/store/api")
public class StoreController {
    @Resource
    StoreService storeService;

    /**
     * 查询门店分页列表
     *
     * @param page
     * @param store
     * @return Result
     */
    @RequestMapping("/getPage")
    public Result getPage(Page page, Store store) {
        return storeService.getPage(page, store);
    }

    /**
     * 添加门店
     * @param store
     * @return Result
     */
    @PostMapping("/add")
    public Result addStore(@RequestBody Store store) {
        return storeService.addStore(store);
    }


    /**
     * 编辑门店
     * @param store
     * @return Result
     */
    @PostMapping("/edit")
    public Result editStore(@RequestBody Store store) {
        return storeService.editStore(store);
    }

}
