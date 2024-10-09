package com.wode.bangertong.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.wode.bangertong.common.entity.ListenCustomer;
import com.wode.bangertong.common.model.Result;
import com.wode.bangertong.common.utils.DateTimeUtils;
import com.wode.bangertong.mapper.ListenCustomerMapper;
import com.wode.bangertong.service.ListenCustomerService;
import com.wode.bangertong.vo.ListenCustomerVO;
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
        customer.setStatus(1);
        Page<ListenCustomer> res = customerMapper.selectPage(page, Wrappers.query(customer));
        if (res.getRecords().size() > 0) {
            List<ListenCustomer> records = res.getRecords();

            for (ListenCustomer record : records) {
                ListenCustomerVO listenCustomerVO = new ListenCustomerVO();
                listenCustomerVO.setImageUrl(record.getImageUrl());
                listenCustomerVO.setUrl("https://warminggaoke.com/bangertong-h5/#/customer?id="+record.getId());
                list.add(listenCustomerVO);
            }
        }

        page.setRecords(list);
        page.setTotal(res.getTotal());

        return new Result(Result.RESULT_OK, page, new Date().getTime());
    }

    @Override
    public Result getListenCustomer(String id) {
        ListenCustomerVO listenCustomerVO = new ListenCustomerVO();

        ListenCustomer listenCustomer = customerMapper.selectById(id);
        if(null != listenCustomer){
            BeanUtil.copyProperties(listenCustomer, listenCustomerVO);

        }
        return new Result(Result.RESULT_OK, listenCustomerVO, new Date().getTime());
    }

}
