package com.wode.bangertongadmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wode.bangertong.common.entity.Store;
import com.wode.bangertong.common.model.Result;
import org.springframework.stereotype.Service;

@Service
public interface StoreService {

    Result getPage(Page page, Store store);

    Result addStore(Store store);

    Result editStore(Store store);

}
