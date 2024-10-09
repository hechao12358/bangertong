package com.wode.bangertong.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.entity.ListenCustomer;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.service.ListenCustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bangertong/customer/api")
public class ListenCustomerController {
    @Resource
    ListenCustomerService customerService;

    /**
     * 查询听顾客说分页列表
     *
     * @param page
     * @param customer
     * @return Result
     */
    @RequestMapping("/getPage")
    public Result getPage(Page page, ListenCustomer customer) {
        return customerService.getPage(page, customer);
    }


    /**
     * 查询听顾客说分页列表
     *
     * @param id
     * @return Result
     */
    @RequestMapping("/getListenCustomer")
    public Result getListenCustomer(String id) {
        return customerService.getListenCustomer(id);
    }


}
