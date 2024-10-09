package com.wode.bangertongadmin.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.wode.bangertong.common.entity.ListenCustomer;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.common.utils.DateTimeUtils;
import com.wode.bangertong.common.vo.ListenCustomerVO;
import com.wode.bangertongadmin.mapper.ListenCustomerMapper;
import com.wode.bangertongadmin.service.ListenCustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ListenCustomerServiceImpl implements ListenCustomerService {

    @Resource
    ListenCustomerMapper customerMapper;

    @Override
    public Result getPage(Page page, ListenCustomer customer) {
        List<ListenCustomerVO> list = new ArrayList<>();

        Page<ListenCustomer> res = customerMapper.selectPage(page, Wrappers.query(customer));
        if (res.getRecords().size() > 0) {
            List<ListenCustomer> records = res.getRecords();

            for (ListenCustomer record : records) {
                ListenCustomerVO listenCustomerVO = new ListenCustomerVO();
                BeanUtil.copyProperties(record, listenCustomerVO);
                listenCustomerVO.setCreateTime(DateTimeUtils.getDateStr(record.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                list.add(listenCustomerVO);
            }
        }

        page.setRecords(list);
        page.setTotal(res.getTotal());

        return new Result(Result.RESULT_OK, page, new Date().getTime());
    }

    @Override
    public Result insertCustomer(ListenCustomer customer) {
        customer.setCreateTime(DateTimeUtils.getCurrentDate());
        customer.setUpdateTime(DateTimeUtils.getCurrentDate());

        int insert = customerMapper.insert(customer);
        return new Result(Result.RESULT_OK, SqlHelper.retBool(insert) ? "成功" : "失败", new Date().getTime());
    }

    @Override
    public Result updateCustomer(ListenCustomer customer) {
        customer.setUpdateTime(DateTimeUtils.getCurrentDate());
        int update = customerMapper.updateById(customer);
        return new Result(Result.RESULT_OK, SqlHelper.retBool(update) ? "成功" : "失败", new Date().getTime());
    }

    @Override
    public Result delCustomer(String id) {
        int delete = customerMapper.deleteById(id);
        return new Result(Result.RESULT_OK, SqlHelper.retBool(delete) ? "成功" : "失败", new Date().getTime());
    }
}
