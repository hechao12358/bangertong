package com.wode.bangertongadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.entity.ListenCustomer;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertongadmin.service.ListenCustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bangertong-admin/customer/api")
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
     * 添加听顾客说
     * @param customer
     * @return Result
     */
    @RequestMapping("/add")
    public Result insertCustomer(ListenCustomer customer) {
        return customerService.insertCustomer(customer);
    }


    /**
     * 编辑听顾客说
     * @param customer
     * @return Result
     */
    @RequestMapping("/edit")
    public Result updateCustomer(ListenCustomer customer) {
        return customerService.updateCustomer(customer);
    }


    /**
     * 删除听顾客说
     * @param id
     * @return Result
     */
    @RequestMapping("/del")
    public Result delCustomer(String id) {
        return customerService.delCustomer(id);
    }

}
