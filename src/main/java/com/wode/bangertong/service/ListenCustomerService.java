package com.wode.bangertong.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.entity.ListenCustomer;
import com.wode.bangertong.common.model.Result;
import org.springframework.stereotype.Service;

@Service
public interface ListenCustomerService {

    Result getPage(Page page, ListenCustomer customer);

    Result getListenCustomer(String id);

}
